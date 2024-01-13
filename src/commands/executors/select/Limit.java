package commands.executors.select;

import composants.relations.Relation;

import java.util.List;
import java.util.Vector;

public abstract class Limit {
    static final String lim = "limit";

    @SuppressWarnings("rawtypes")
    public static void handleLimit(List<String> splitQuery, Relation relation)
            throws Exception {

        if (splitQuery.contains(lim) || splitQuery.contains(lim.toUpperCase())) {
            int indexLimit = splitQuery.contains(lim) ?
                    splitQuery.indexOf(lim) : splitQuery.indexOf(lim.toUpperCase());
            int limit = Integer.parseInt(splitQuery.get(indexLimit + 1));

            Vector<Vector> rows = new Vector<>();
            for (int i = 0; i < limit; i++)
                rows.add(relation.getLignes().get(i));
            relation.setLignes(rows);
        }
    }
}
