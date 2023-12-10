package commands.executors;

import composants.relations.Relation;
import exe.Affichage;
import exe.Interpreter;
import tools.Funct;
import tools.loaders.RelationLoader;
import tools.verifier.RelationVerifier;

import java.util.Vector;

@SuppressWarnings("ALL")
public class Projection {
    public static void project(String[] commands, Interpreter inter)
            throws Exception {

        String nomRelation = Funct.getNomRelation(commands, "AME");
        String dbPath = inter.getDbPath();

        new RelationVerifier(dbPath).verifyExisting(nomRelation);

        Relation relation = selectColumns(
                commands,
                new RelationLoader().loadRelation(nomRelation, dbPath)
        );
        Affichage.afficherDonnees(relation);
    }

    static Relation selectColumns(String[] commands, Relation rel) throws Exception {
        /**
         * Retourne une relation dont les attributs sont les colonnes
         * specifies dans la commande.
         */
        int mode = 2;
        String[][] attribs = getAttribs( commands, rel, mode );
        Relation nvRel = new Relation( String.join(" ",commands), attribs );

        Vector<Vector> donnees = getDonnees( rel, getColumnsName(commands, mode));
        nvRel.setLignes( donnees );
        return nvRel;
    }

    public static String[][] getAttribs( String[] commande, Relation rel, int mode ) throws Exception {
        String[] columnsName = getColumnsName( commande, mode );
        String[][] results = new String[ columnsName.length ][2];
        for( int i = 0; i < results.length; i++ ) {
            results[i][0] = columnsName[i];
            results[i][1] = rel.getTypeAttribut( columnsName[i] );
        }
        return results;
    }
    public static String[] getColumnsName( String[] commande, int mode ) {
        /**
         * mode == 1: select
         * mode == 2: projection
         */
        Vector v = new Vector();
        int index = 0;
        while ( commande[index].compareTo("AME") != 0 ) index ++;
        switch ( mode ) {
            case 1:
                for ( int i = 1; i < index; i++ )
                    if ( !commande[i].equals("(") && !commande[i].equals(")") )
                        v.add( Funct.takeOffComma( commande[i] ) );
                break;
            case 2:
                for ( int i = 1; i < index; i++ )
                    if ( !commande[i].equals("]") )
                        v.add( Funct.takeOffComma( commande[i] ) );
                break;
        }

        String[] results = new String[ v.size() ];
        for( int k = 0; k < v.size(); k++ ) results[k] = (String) v.get(k);
        return results;
    }

    public static Vector<Vector> getDonnees( Relation rel, String[] columnsName ) throws Exception {
        Vector<Vector> lignes = new Vector();
        int[] indexAttribs = rel.getIndexAttribs(columnsName);

        Vector<Vector> dataOrg = rel.getLignes();
        for ( Vector ligneOrg: dataOrg ) {

            Vector ligne = new Vector(); // ajout des donnees dont les colonnes ont ete specificiees
            for ( int i = 0; i < indexAttribs.length; i++ )
                ligne.add( ligneOrg.get(indexAttribs[i]) );

            lignes.add( ligne );
        }
        return lignes;
    }
}
