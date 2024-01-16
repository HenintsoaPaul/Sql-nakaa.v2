package commands.executors.select.join;

import composants.relations.Attribut;
import composants.relations.Relation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public abstract class JoinHandler {

    public abstract Relation joinRelations(Relation relation1, Relation relation2, String tetaCondition)
    throws Exception;

    public static void renameIdenticalAttributs(List<Relation> relations) {
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
                otherAttributs.forEach(listAtb -> listAtb.forEach(othAtb -> {

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
                }));
                if (thereIsSimilar.get()) {
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
