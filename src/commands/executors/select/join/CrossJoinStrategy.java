package commands.executors.select.join;

import composants.relations.Relation;

import java.util.List;
import java.util.Vector;

public abstract class CrossJoinStrategy {
    public static Relation processCrossJoin(List<Relation> relations)
            throws Exception {
        Relation rel = relations.get(0);
        for (int i = 1; i < relations.size(); i++) {
            rel = crossJoin( rel, relations.get(i));
        }
        return rel;
    }
    private static Relation crossJoin(Relation rel1, Relation rel2)
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
    private static Vector<Vector> getLines(Vector<Vector> lines1, Vector<Vector> lines2) {
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
