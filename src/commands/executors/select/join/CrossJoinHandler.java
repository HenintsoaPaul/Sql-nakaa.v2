package commands.executors.select.join;

import composants.relations.Relation;

import java.util.Arrays;
import java.util.List;
import java.util.Vector;

@SuppressWarnings({"rawtypes", "unchecked"})
public class CrossJoinHandler extends JoinHandler {

    @Override
    public Relation joinRelations(Relation relation1, Relation relation2, String tetaCondition)
            throws Exception {
        List<Relation> relations = Arrays.asList(relation1, relation2);
        return processCrossJoin(relations);
    }

    public Relation processCrossJoin(List<Relation> relations)
            throws Exception {
        Relation rel = relations.get(0);
        for (int i = 1; i < relations.size(); i++) {
            Relation rel2 = relations.get(i);
            rel = crossJoin( rel, rel2);
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
