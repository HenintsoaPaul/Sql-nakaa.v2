package commands.executors.insert;

import composants.relations.Attribut;
import composants.relations.Relation;
import tools.Funct;

import java.util.Arrays;

public abstract class InsertColumnsHandler {

    /**
     * It takes the names of the columns in a Client query. Then,
     * it returns it in String[].
     * @param commands Array of split query where " " was the separator.
     * @return An array containing the name of the specified columns
     * in the query.
     */
    static String[] getColumnsName(String[] commands) {
        // get specified columns number
        int index = 2, count = 0, k = 0;
        while ( commands[ index ].compareTo( ")" ) != 0 ) {
            count ++;
            index ++;
        }

        // get specified columns name
        String[] results = new String[ count ];
        index = 2;
        while ( commands[ index ].compareTo( ")" ) != 0 ) {
            results[ k ] = Funct.takeOffComma(commands[ index ]);
            k ++;
            index ++;
        }
        return results;
    }


    /**
     * It returns an int[] containing the index of each columnName in
     * String[] columns Name. Or, an Exception whether a columnName is
     * not in the Relation.
     * @param relation A Relation object.
     * @param columnsName Array of String supposed to be names of columns
     *                    in the Relation.
     * @throws RuntimeException whether a columnName in columnsName is not found
     * in the columns name list of the Relation.
     */
    static int[] getColumnsIndexes(Relation relation, String[] columnsName)
        throws RuntimeException {

        Attribut[] attributs = relation.getAttributs();
        return Arrays.stream(columnsName)
                .mapToInt(columnName -> {

                    for (int k = 0; k < attributs.length; k++) {
                        if (columnName.equals(attributs[k].getNomAttribut())) {
                            return k;
                        }
                    }

                    try {
                        throw new Exception("La colonne " + columnName + " n'existe pas dans la Relation " + relation.getNomRelation() + "!");
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                })
                .toArray();
    }

}
