package tools;

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
}
