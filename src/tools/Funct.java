package tools;

import composants.relations.Relation;
import exe.Sql_nakaa;

import java.util.Vector;

public class Funct {

    Sql_nakaa sql_nakaa;



    // Constructor
    public Funct(Sql_nakaa sql_nakaa) {
        this.sql_nakaa = sql_nakaa;
    }



    // Getters
    public Sql_nakaa getSql_nakaa() {
        return this.sql_nakaa;
    }




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
    public static boolean isInTabString( String[] tab, String myElmt ) {
        for ( String elmt: tab )
            if ( myElmt.compareTo( elmt ) == 0 ) return true;
        return false;
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
            if ( Relation.isLignesIdentiques( v, elmt ) )
                return true;
        return false;
    }
    public static Vector<Vector> unionSansRepetition(Vector<Vector> data1, Vector<Vector> data2 ) {
        Vector<Vector> results = new Vector( data1 );
        for ( Vector ligne: data2 ) {
            if ( !isInVectorOfVector( results, ligne ) )
                results.add( ligne );
        }
        return results;
    }
}
