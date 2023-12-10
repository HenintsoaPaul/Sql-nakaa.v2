package commands.executors.select;

import commands.IExecutor;
import commands.executors.Projection;
import composants.relations.Relation;
import exe.Affichage;
import exe.Interpreter;
import tools.Funct;
import tools.loaders.RelationLoader;
import tools.verifier.RelationVerifier;

import java.util.Vector;

public class Select implements IExecutor {
    public static void select(String[] commands, Interpreter inter) throws Exception {
        String nomRelation = Funct.getNomRelation( commands, "AME" );

        String dbPath = inter.getDbPath();

        new RelationVerifier(dbPath)
                .verifyExisting( nomRelation );

        Relation rel = RelationLoader.loadRelation( nomRelation, dbPath );


        // jointures
//        rel = Joint.joints(rel, commands, inter);

        // Lignes -- WHERE
        rel = selectWhere( commands, rel );

        // Colonnes
        Relation result = null;
        // select *
        if ( commands[1].compareToIgnoreCase("ABY") == 0 )
            result = rel;
        else // col1, col2, ...
            result = selectColumns( commands, rel );

//        Debug.showRelation(result);
        Affichage.afficherDonnees(result);
    }

    static Relation selectColumns( String[] commande, Relation rel ) throws Exception {
        /**
         * Retourne une relation dont les attributs sont les colonnes
         * specifies dans la commande.
         */
        int mode = 1;
        String[][] attribs = Projection.getAttribs( commande, rel, mode );
        Relation result = new Relation(
                String.join( " ", commande ) ,
                attribs
        );

        Vector<Vector> donnees = Projection.getDonnees(
                rel,
                Projection.getColumnsName(commande, mode)
        );
        result.setLignes( donnees );
        return result;
    }

    /**
     * WHERE ...
     */
    static Relation selectWhere( String[] commande, Relation rel ) throws Exception {
        int index = 0;
        while ( (index < commande.length) && (commande[index].compareTo("REFA") != 0) )
            index ++;

        if ( index != commande.length ) { // raha misy REFA ilay requete
            String[][] conditions = getConditions( commande, index );
            conditions = inverseAllTsyConditions( conditions );
            verifyAllConditions( conditions, rel );

            Vector<Vector> lignes = rel.getLignes();
            lignes = getLignesWhere( rel, conditions[0], lignes );

            if ( conditions.length > 1 ) {
                String[] operateurs = getConditionSeparator( commande );
                for( int i = 1; i < conditions.length; i++ ) {

                    int m = i - 1;
                    switch ( operateurs[m] ) {
                        case "ARY":
                            lignes = getLignesWhere( rel, conditions[i], lignes );
                            break;
                        case "NA":
                            Vector<Vector> nvResults = getLignesWhere( rel, conditions[i], rel.getLignes() );
                            lignes = Funct.unionSansRepetition( lignes, nvResults );
                            break;
                        default:
                            throw new Exception("Operateur logique inconnu!");
                    }
                }
            }
            rel.setLignes( lignes );
        }
        return rel;
    }

    static Vector<Vector> getLignesWhere ( Relation rel, String[] condition, Vector<Vector> dataOrg )
            throws Exception {
        Vector<Vector> result = new Vector();
        int indexColumn = rel.getIndexAttrib( condition[0] );
        Class<?> classColumn = rel.getClassOfAttrib( condition[0] );

        for( Vector ligneOrg: dataOrg )
            if ( isLigneVerifiant( ligneOrg, condition, indexColumn, classColumn ) )
                result.add( ligneOrg );
        return result;
    }
    static boolean isLigneVerifiant( Vector ligne, String[] condition, int indexAttrib, Class clazz )
            throws Exception {
        String type = clazz.getSimpleName();
        double value = 0, verif = 0;
        switch ( type ) {
            case "Double":
                value = (double) ligne.get( indexAttrib );
                verif = Double.parseDouble( condition[2] );
                break;
            case "Integer":
                value = (int) ligne.get( indexAttrib );
                verif = Integer.parseInt( condition[2] );
                break;
            default:
                throw new Exception("Les types de valeur numerique pendant les conditions sont "
                        +"soit int, soit double!");
        }

        boolean test = false;
        switch ( condition[1] ) {
            case "==":
                if ( value == verif ) test = true; break;
            case "<>":
                if ( value != verif ) test = true; break;
            case "<":
                if ( value < verif ) test = true; break;
            case ">":
                if ( value > verif ) test = true; break;
            case "<=":
                if ( value <= verif ) test = true; break;
            case ">=":
                if ( value >= verif ) test = true; break;
        }
        return test;
    }
    static String[] getConditionSeparator( String[] commands ) {
        /**
         * Retourne tout les operateurs logiques dans la commande
         * cad: [ AND, OR, AND, NOT, AND, ... ]
         */
        int count = 0, ind = 0, i = 0;
        while ( commands[ind].compareTo("REFA") != 0 ) ind ++;
        ind ++; // on se deplace jusqu'a "REFA" ...

        int k = ind;
        String[] seps = { "ARY", "NA" };
        while ( ind < commands.length ) {
            if ( Funct.isInTabString(seps, commands[ind]) ) count ++;
            ind ++;
        }

        String[] results = new String[ count ];
        while ( k < commands.length ) {
            if ( Funct.isInTabString(seps, commands[k]) ) {
                results[i] = commands[k];
                i ++;
            }
            k ++;
        }
        return results;
    }


    // Verifications
    static void verifyPossibleCondition( String[] condition, Relation rel ) throws Exception {
        String nomAttrib = condition[0];
        String valueVerif = condition[2];

        if ( !rel.isMyAttrib( nomAttrib ) )
            throw new Exception("L'attribut "+nomAttrib
                    +" n'existe pas dans la Relation "+rel.getNomRelation()+"!");

        // verification des conditions numeriques: == ou < ou > ou >= ou <= ou <>
        String[] operationPossibles = { "==", "<", ">", ">=", "<=", "<>" };
        if ( !Funct.isInTabString( operationPossibles, condition[1] ) )
            throw new Exception("L'operation "+condition[1]+" est inconnue!"+
                    "Les operations possibles sont: "+String.join(", ", operationPossibles));
        else if ( !rel.isNumericAttrib( nomAttrib) )
            throw new Exception("L'operation "+condition[1]+" est impossible a realiser "+
                    "sur l'attribut "+ nomAttrib+ " de la Relation '"+ rel.getNomRelation()+ "'!");
        else {
            try {
                double test = Double.parseDouble( valueVerif );
            }
            catch ( NumberFormatException nfe ) {
                try {
                    int test = Integer.parseInt( valueVerif );
                }
                catch ( NumberFormatException nfee ) {
                    throw new Exception( "ERROR: Tokony ho nombre ny condition ampesaina "+
                            "rehefa mampiasa operateur numerique: column (cond_numerique) number" );
                } catch ( Exception ee ) {
                    throw ee;
                }
            } catch ( Exception e ) {
                throw e;
            }
        }
    }
    static void verifyAllConditions( String[][] conditions, Relation rel ) throws Exception {
        for ( String[] cond : conditions )
            verifyPossibleCondition(cond, rel);
    }


    // About the conditions...
    static String[][] getConditions( String[] commands, int index ) {
        int count = 0;
        index ++; // elmt suivant "REFA"
        int k = index;

        while ( index < commands.length ) {
            if ( commands[index].compareTo("(") == 0 ) count ++;
            index ++;
        } // count ~ le nombre des conditions
        String[][] cond = new String[ count ][];
        for ( int ind = k, i = 0; ind < commands.length; ind ++ ) {

            if ( commands[ind].compareTo("ARY") == 0 ) {}
            else if ( commands[ind].compareTo("(") == 0 ) {
                cond[i] = getCond( commands, ind );
                i ++;
                ind += cond[i-1].length+1;
            }
        }
        return cond;
    }
    static String[] getCond( String[] commands, int indexParath ) {
        indexParath ++;
        int count = 0;
        for ( int ind = indexParath; commands[ind].compareTo(")") != 0; ind++, count ++ ) {}
        String[] results = new String[ count ];
        for ( int ind = indexParath, k = 0; commands[ind].compareTo(")") != 0; ind++, k++ )
            results[k] = commands[ind];
        return results;
    }

    /**
     * inversion de condition: TSY ...
     */
    static String[] getConditionInverse( String[] condition ) {
        String[][] correspondances = {
                { "==", "<>" },
                { "<>", "==" },
                { ">", "<=" },
                { "<=", ">" },
                { "<", ">=" },
                { ">=", "<" }
        };
        String[] result = new String[3];
        result[0] = condition[1];
        for ( int i = 0; i < correspondances.length; i++ ) {
            if ( correspondances[i][0].equals( condition[2] ) ) {
                result[1] = correspondances[i][1];
                break;
            }
        }
        result[2] = condition[3];
        return result;
    }

    static String[][] inverseAllTsyConditions( String[][] conditions ) {
        for ( int i = 0; i < conditions.length; i++ )
            if ( conditions[i].length == 4 && conditions[i][0].compareTo("TSY") == 0 )
                conditions[i] = getConditionInverse( conditions[i] );
        return conditions;
    }

    @Override
    public void execute(String[] commands, Interpreter interpreter) throws Exception {

        select(commands, interpreter);
    }
}
