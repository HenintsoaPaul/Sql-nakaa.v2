package tools;

import composants.relations.RelationOperator;

import java.util.Vector;

@SuppressWarnings("rawtypes")
public class Funct {
    // int functions
    public static boolean isInTabInt( int[] tab, int myElmt ) {
        for ( int elmt: tab )
            if ( myElmt == elmt ) return true;
        return false;
    }



    // String functions
    public static String takeOffComma(String str) {
        return str.split(",")[0];
    }



    // Query
    /**
     * @param commands: SQL commands entered by the client
     * @param where: can only be "AME" or "AGNATY"
     * @return The name of the relation in the commands.
     */
    public static String getNomRelation(String[] commands, String where) {
        int index = 0;
        while ( !commands[index].equalsIgnoreCase(where) ) index ++;
        return commands[ index+1 ];
    }


    // Vectors
    public static boolean isInVectorOfVector(Vector<Vector> vectVect, Vector elmt ) {
        for ( Vector v: vectVect )
            if ( RelationOperator.isLignesIdentiques( v, elmt ) )
                return true;
        return false;
    }
    @SuppressWarnings("unchecked")
    public static Vector<Vector> unionSansRepetition(Vector<Vector> data1, Vector<Vector> data2 ) {
        Vector<Vector> results = new Vector( data1 );
        for ( Vector ligne: data2 ) {
            if ( !isInVectorOfVector( results, ligne ) )
                results.add( ligne );
        }
        return results;
    }
}
