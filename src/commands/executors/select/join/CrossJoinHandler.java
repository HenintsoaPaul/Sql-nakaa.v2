package commands.executors.select.join;

import composants.relations.Attribut;
import composants.relations.Relation;
import tools.loaders.RelationLoader;
import tools.verifier.RelationVerifier;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

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

        List<Relation> relations = verifyExistanceAndLoadRelations(relationsName, dbPath, relationWhere);

        renameIdenticalAttributs(relations);

        return CrossJoinStrategy.processCrossJoin(relations);
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

    private List<Relation> verifyExistanceAndLoadRelations(List<String> relationsName, String dbPath, Relation relationWhere) {
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

        return relations;
    }

    private void renameIdenticalAttributs(List<Relation> relations) {
        // look for attribs with the same names in each Relation, then rename those with similar names
        int numberOfRelations = relations.size();
        for (int i = 0; i < numberOfRelations - 1; i++) {

            List<Attribut> currentAttributs = Arrays.asList(relations.get(i).getAttributs());
            List<List<Attribut>> otherAttributs = new ArrayList<>();
            for (int j = i + 1; j < numberOfRelations; j++) {
                List<Attribut> atb = Arrays.asList(relations.get(j).getAttributs());
                otherAttributs.add(atb);
            }

            int finalI = i;
            currentAttributs.forEach(curAtb -> {

                String nomCurAtb = curAtb.getNomAttribut();
                AtomicBoolean thereIsSimilar = new AtomicBoolean(false);
                otherAttributs.forEach(listAtb -> {

                    listAtb.forEach(othAtb -> {

                        String nomOthAtb = othAtb.getNomAttribut();
                        if (nomCurAtb.equals(nomOthAtb)) {

                            thereIsSimilar.set(true);
                            try {
                                int index = otherAttributs.indexOf(listAtb) + 1;
                                othAtb.setNomAttribut("R" + index + "." + nomOthAtb);
                            } catch (Exception e) {
                                throw new RuntimeException(e);
                            }
                        }
                    });
                });
                if (thereIsSimilar.get() == true) {
                    try {
                        curAtb.setNomAttribut("R" + finalI + "." + nomCurAtb);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
            });
        }
    }
}
