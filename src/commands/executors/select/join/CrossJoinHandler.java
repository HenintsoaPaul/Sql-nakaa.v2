package commands.executors.select.join;

import composants.relations.Relation;

import java.util.List;
import java.util.Vector;

@SuppressWarnings({"rawtypes", "unchecked"})
public class CrossJoinHandler extends JoinHandler {
    @Override
    public Relation handle(List<Relation> relations, List<String> splitQuery)
            throws Exception {
        /*
         query be like:
            aboay aby ame t1 x t2 x t3 x ...
         splitQuery be like:
            [aboay, aby, ame, t1, x, t2, x, t3, ...]
         */

        return processCrossJoin(relations);
    }

     List<String> getRelationsName(List<String> splitQuery) {
        return getRelationsNames(splitQuery, "x");
    }

    public Relation processCrossJoin(List<Relation> relations)
            throws Exception {
        Relation rel = relations.get(0);
        for (int i = 1; i < relations.size(); i++) {
            rel = crossJoin( rel, relations.get(i));
        }
        return rel;
    }
    private Relation crossJoin(Relation rel1, Relation rel2)
            throws Exception {
        Relation result = new Relation(
                rel1.getAttributs(),
                rel2.getAttributs()
        );
        result.setLignes(
                getLines(rel1.getLignes(), rel2.getLignes())
        );
        return result;
    }
    private Vector<Vector> getLines(Vector<Vector> lines1, Vector<Vector> lines2) {
        Vector<Vector> lines = new Vector<>();
        for ( Vector li1: lines1 )
            for ( Vector li2: lines2 ) {
                Vector line = new Vector( li1 );
                line.addAll( li2 );
                lines.add( line );
            }
        return lines;
    }
}
