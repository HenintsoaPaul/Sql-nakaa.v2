package commands.executors.select.join;

import composants.relations.Relation;
import tools.loaders.RelationLoader;
import tools.verifier.RelationVerifier;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

@SuppressWarnings("rawtypes")
public class CrossJoinHandler extends JoinHandler {
    @Override
    public Relation handle(List<String> splitQuery, String dbPath, Relation relationWhere)
            throws Exception {
        /*
         query be like:
            aboay aby ame t1 x t2 x t3 x ...
         splitQuery be like:
            [aboay, aby, ame, t1, x, t2, t3, ...]
         */
        // take the names of all relations, unless t1 because t1 == relationWhere
        List<String> relationsName = getRelationsName(splitQuery);

        // verify whether those relations exist in the current db, then load them
        RelationVerifier verifier = new RelationVerifier(dbPath);
        RelationLoader loader = new RelationLoader(dbPath);
        List<Relation> relations = new ArrayList<>();
        relations.add(relationWhere);
        relationsName.forEach(name -> {
            try {
                verifier.verifyExisting(name);
                Relation rel = loader.loadRelation(name);
                relations.add(rel);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        // process to cross join
        return processCrossJoin(relations);
    }

    private Relation processCrossJoin(List<Relation> relations)
            throws Exception {
        Relation rel = relations.get(0);
        for (int i = 1; i < relations.size(); i++) {
            rel = crossJoin( rel, relations.get(i));
        }
        return rel;
    }
    public static Relation crossJoin(Relation rel1, Relation rel2)
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

    private List<String> getRelationsName(List<String> splitQuery) {
        List<String> relationsName = new ArrayList<>();
        int ind = splitQuery.contains("x") ?
                splitQuery.indexOf("x") : splitQuery.indexOf("X");

        for (int i = ind; i < splitQuery.size(); i++) {
            String str = splitQuery.get(i);
            if (str.equalsIgnoreCase("x")) {
                relationsName.add(splitQuery.get(i+1));
            }
        }

        return relationsName;
    }
}
