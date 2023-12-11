package commands.executors.select.conditions;

import tools.Funct;

import java.util.Arrays;

public abstract class ConditionGetter {

    static final String[] SEPARATORS = {"refa", "na", "ary"};

    public static String[][] getConditions(String[] commands, int indexRefa) {

        int nbConditions = countConditions(commands, indexRefa);
        if (nbConditions <= 0)
            throw new IllegalArgumentException("Invalid nbConditions");
//        System.out.println("nbConditions: "+ nbConditions);

        String[][] conditions = new String[nbConditions][];

        for (int ind = indexRefa, i = 0; ind < commands.length; ind++) {

            String word = commands[ind];
            if (Arrays.asList(SEPARATORS).contains(word.toLowerCase())) {

                int indexSeparator = ind;
                String[] condition = getCondition(commands, indexSeparator+1);
                conditions[i] = condition;

                i++;
                ind += condition.length;
            }
        }
        return conditions;
    }

    private static int countConditions(String[] commands, int indexRefa){

        int countConditions = 1,
                k = indexRefa + 1;
        while (k < commands.length) {
            if (commands[k].equalsIgnoreCase("ARY")
                    || commands[k].equalsIgnoreCase("NA")) {
                countConditions++;
            }
            k++;
        }
        return countConditions;
    }

    static String[] getCondition(String[] commands, int startConditionIndex) {

        int endIndex = findEndIndex(commands, startConditionIndex);
//        System.out.println("endIndex: "+endIndex);
        return Arrays.copyOfRange(commands, startConditionIndex, endIndex);
    }

    private static int findEndIndex(String[] commands, int startConditionIndex) {
        int endIndex = -1;
        for (String separator : SEPARATORS) {
            int tempIndex = Arrays.asList(commands).subList(startConditionIndex, commands.length)
                    .indexOf(separator);
            if (tempIndex != -1 && (endIndex == -1 || tempIndex < endIndex)) {
                endIndex = tempIndex;
            }
        }

        if (endIndex == -1) {
            endIndex = commands.length;
        } else {
            endIndex += startConditionIndex;
        }
        return endIndex;
    }


    /**
     * It returns all the "ary" and "na" in the commands
     * following their apparition order.
     */
    public static String[] getConditionSeparator(String[] commands) {

        String[] separators = {"ARY", "NA"};
        int startIndex = Arrays.asList(commands).indexOf("REFA") + 1;

        return Arrays.stream(commands)
                .skip(startIndex)
                .filter(command -> Arrays.asList(separators).contains(command))
                .toArray(String[]::new);
    }
}
