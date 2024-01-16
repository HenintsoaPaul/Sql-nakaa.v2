package commands.executors.select;

import composants.relations.Relation;

import java.util.Arrays;
import java.util.List;
import java.util.Vector;

@SuppressWarnings("rawtypes")
public abstract class Limit {
    static final String lim = "limit";

    public static void handleLimit(Relation relation, String[] selectQuery)
            throws Exception {
        List<String> list = Arrays.asList(selectQuery);
        int indexLimit = Select2.getIndexZeroLevelParentheses(list, lim);

        if (indexLimit != -1) {
            int limit = Integer.parseInt(selectQuery[indexLimit + 1]);
            Vector<Vector> rows = new Vector<>();
            for (int i = 0; i < limit; i++)
                rows.add(relation.getLignes().get(i));
            relation.setLignes(rows);
        }
    }
}
