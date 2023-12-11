package commands.executors.select.conditions;

import java.util.Arrays;
import java.util.HashMap;

public abstract class ConditionProcessor {

    static String[] getConditionInverse( String[] condition ) {

        HashMap<String, String> operators = new HashMap<>();
        operators.put("==", "<>");
        operators.put("<>", "==");
        operators.put(">", "<=");
        operators.put("<=", ">");
        operators.put("<", ">=");
        operators.put(">=", "<");

        String[] result = new String[3];
        result[0] = condition[1];
        result[1] = operators.get(condition[2]);
        result[2] = condition[3];

        return result;
    }

    public static void inverseAllTsyConditions(String[][] conditions) {

        for ( int i = 0; i < conditions.length; i++ )
            if ( conditions[i].length == 4 && conditions[i][0].equalsIgnoreCase("tsy") )
                conditions[i] = getConditionInverse( conditions[i] );
    }

}
