package commands.executors;

import commands.IExecutor;
import composants.relations.Relation;
import exe.Interpreter;
import tools.Funct;
import tools.loaders.RelationLoader;
import tools.savers.RelationSaver;
import tools.verifier.RelationVerifier;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.Vector;

public class Insert implements IExecutor {
    /**
     * INSERT ( etu, nom ) INTO étudiants VALUES ( 11_int, Sharon_String )
     * -> APIDIRO ( etu, nom ) AGNATY étudiants VALUES ( 11, Sharon_String )
     */

    @Override
    public void execute(String[] commands, Interpreter interpreter)
            throws Exception {

        Relation relation = insert(commands, interpreter);
        updateRelation(interpreter, relation);
    }



    Relation insert(String[] commands, Interpreter interpreter)
            throws Exception {

        String nomRelation = Funct.getNomRelation(commands, "AGNATY");

        new RelationVerifier(interpreter.getDbPath())
                .verifyExisting(nomRelation);

        String[] columns = getColumnsInsert( commands );
        Vector<?> values  = getValuesInsert( commands );

        Relation rel = RelationLoader.getRelation(nomRelation, interpreter);

        // insert in all columns
        if ( rel.getAttributs().length == columns.length )
            rel.addLigne( values );

        // in specific columns
        else
            rel.addLigne( getIndexColumns( nomRelation, columns, interpreter ), values );

        System.out.println("Yesss, tafiditra ao anaty Relation "
                + nomRelation + " ireo data rht!");
        return rel;
    }




    static String[] getColumnsInsert( String[] commands ) {
        int index = 2, count = 0, k = 0;
        while ( commands[ index ].compareTo( ")" ) != 0 ) {
            count ++;
            index ++;
        }
        String[] results = new String[ count ];
        index = 2;
        while ( commands[ index ].compareTo( ")" ) != 0 ) {
            results[ k ] = Funct.takeOffComma(commands[ index ]);
            k ++;
            index ++;
        }
        return results;
    }

    static Vector<Object> getValuesInsert( String[] commande ) throws Exception {
        Vector<Object> result = new Vector<>();
        int index = commande.length-2;

        while ( commande[ index ].compareTo( "(" ) != 0 ) index --;

        for ( int i = index+1; commande[ i ].compareTo( ")" ) != 0; i++ ) {
            String[] val = Funct.takeOffComma(commande[i]).split( "_" );

            // insert d'une valeur de type dateHeure
            if ( val.length == 3 ) {
                String valera = val[0]+ " "+ val[1];
                String typa = val[2];
                val = new String[2];
                val[0] = valera;
                val[1] = typa;
            }

            Class<?> clazz = getClassOfValue( val[1] );
            Object value;
            try {
                value = clazz.cast( val[0] );
            }
            catch ( ClassCastException cce ) {
                try {
                    switch ( clazz.getSimpleName() ) {
                        case "Double":
                            value = Double.parseDouble( val[0] ); break;
                        case "Integer":
                            value = Integer.parseInt( val[0] ); break;
                        case "Boolean":
                            value = Boolean.parseBoolean( val[0] ); break;
                        default:
                            throw new NumberFormatException();
                    }
                }
                catch ( NumberFormatException nfe ) {
                    String format = "dd-MM-yyyy";
                    try {
                        value = LocalDate.parse( val[0], DateTimeFormatter.ofPattern(format) );
                    }
                    catch (DateTimeParseException dte) {
                        format += " HH:mm:ss";
                        value = LocalDateTime.parse( val[0], DateTimeFormatter.ofPattern(format));
                    }
                }
            }
            result.add( value );
        }
        return result;
    }
    static Class<?> getClassOfValue(String valueType) throws Exception {
        HashMap<String, Class<?>> map = new HashMap<>();
        map.put( "String",  Class.forName("java.lang.String") );
        map.put( "int",     Class.forName("java.lang.Integer") );
        map.put( "double",  Class.forName("java.lang.Double") );
        map.put( "boolean", Class.forName("java.lang.Boolean") );
        map.put( "date",    LocalDate.class );
        map.put( "dateHeure", LocalDateTime.class );

        return map.get( valueType );
    }

    static int[] getIndexColumns( String nomRelation, String[] columns, Interpreter inter ) throws Exception {
        /*
         * Retourne un int[] contenant les indices des attributs dans String[] columns
         * par rapport aux attributs de la relation.
         */
        Relation rel = RelationLoader.getRelation( nomRelation, inter );
        int[] indexAttribs = new int[ columns.length ];
        for ( int j = 0, ind = 0; j < columns.length; j++ ) {

            boolean isIn = false;
            for ( int k = 0; k < rel.getAttributs().length; k++ ) {
                if ( columns[j].compareTo( rel.getAttributs()[k].getNomAttribut() ) == 0 ) {
                    indexAttribs[ ind ] = k;
                    ind ++;
                    isIn = true;
                    break;
                }
            }

            if ( !isIn ) throw new Exception( "La colonne "+columns[j]+
                    " n'existe pas dans la Relation "+nomRelation+"!" );
        }
        return indexAttribs;
    }


    // Update
     void updateRelation(Interpreter inter, Relation rel) throws IOException {

        new RelationSaver().save(rel, inter);
    }
}
