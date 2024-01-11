package commands.executors.select.conditions;

import java.util.HashMap;
import java.util.List;

public abstract class ConditionInverser {
    static String[] getConditionInverse(String[] condition) {

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

    public static void inverseAllTsyConditions(List<String[]> conditions) {
        for ( int i = 0; i < conditions.size(); i++ ) {
            String[] cond = conditions.get(i);
            if (cond.length == 4 && cond[0].equalsIgnoreCase("tsy"))
                conditions.set(i, getConditionInverse(cond));
        }
    }
}
