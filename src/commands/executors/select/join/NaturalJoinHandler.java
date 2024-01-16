package commands.executors.select.join;

import composants.relations.Attribut;
import composants.relations.Relation;

import java.util.*;

@SuppressWarnings("rawtypes")
public class NaturalJoinHandler extends JoinHandler {
    @Override
    public Relation handle(List<Relation> relations, List<String> splitQuery)
            throws Exception {
        /*
         query be like:
            aboay aby ame t1 , t2 , t3 , ...
         splitQuery be like:
            [aboay, aby, ame, t1, ,, t2, ,, t3, ...]
         */

        Relation relCrossJoined = new CrossJoinHandler().processCrossJoin(relations);
        return processNaturalJoin(relCrossJoined);
    }

    @Override
    List<String> getRelationsName(List<String> splitQuery) {
        return getRelationsNames(splitQuery, ",");
    }

    @Override
    public Relation joinTwoRelations(Relation relation1, Relation relation2, String tetaCondition)
            throws Exception {
        List<Relation> relations = Arrays.asList(relation1, relation2);
        Relation relXJoined = new CrossJoinHandler().processCrossJoin(relations);
        return processNaturalJoin(relXJoined);
    }

    private Relation processNaturalJoin(Relation rel)
            throws Exception {

        // on prend les noms de tous les attributs
        List<String> atbNamesList = getAttributesNames(rel);

        // on regroupe les noms des attributs qui sont identiques
        Map<String, List<String>> groupedAttributes = groupAttributesNames(atbNamesList);

        // on parcourt chaque groupe d'attributs de noms identiques
        Set<String> keys = groupedAttributes.keySet();
        Vector<Vector> rows = new Vector<>();
        for (String key: keys) {

            // on prend les index des attributs dans le groupe d'attribut actuel
            List<String> attributes = groupedAttributes.get(key);
            Map<String, Integer> attributAndIndex = getIndexOfEachAttribute(attributes, rel);

            // on va parcourir toutes les rows pour chercher les rows a garder la relation result
            Vector<Vector> rowsToHold = getRowsToHold(rel, attributAndIndex);

            // on ajoute les lignes correspondantes au result
            if (!rowsToHold.isEmpty()) {
                rows.addAll(rowsToHold);
                rel.setLignes(rows);
            }
        }

        return rel;
    }

    private List<String> getAttributesNames(Relation relationCrossJoined) {
        List<String> atbNamesList = new ArrayList<>();
        Attribut[] attributs = relationCrossJoined.getAttributs();
        for (Attribut attribut : attributs) {
            String atbName = attribut.getNomAttribut();
            atbNamesList.add(atbName);
        }
        return atbNamesList;
    }

    private Map<String, List<String >> groupAttributesNames(List<String> attributesNames) {
        Map<String, List<String>> groupedAttributes = new HashMap<>();
        for (String attributeName : attributesNames) {
            if (attributeName.contains(".")) {
                String nameAfterDot = attributeName.split("\\.")[1];
                if (!groupedAttributes.containsKey(nameAfterDot)) {
                    groupedAttributes.put(nameAfterDot, new ArrayList<>());
                }
                groupedAttributes.get(nameAfterDot).add(attributeName);
            }
        }
        return groupedAttributes;
    }

    private Map<String, Integer> getIndexOfEachAttribute(List<String> attributes, Relation rel)
            throws Exception {
        Map<String, Integer> attributAndIndex = new HashMap<>();
        for (String  atbName : attributes) {
            int index = rel.getIndexAttrib(atbName);
            attributAndIndex.put(atbName, index);
        }
        return attributAndIndex;
    }

    private Vector<Vector> getRowsToHold(Relation rel, Map<String, Integer> attributAndIndex) {
        Vector<Vector> rowsToHold = new Vector<>();
        for (Vector ligne : rel.getLignes()) {

            Set<String> keysAtb = attributAndIndex.keySet();
            // on va verifier que tous les attributs du key actuel contiennent la meme valeur
            for (int i = 0; i < keysAtb.size() - 1; i++) {
                String currentKey = new ArrayList<>(keysAtb).get(i),
                        nextKey = i + 1 >= keysAtb.size() ?
                                null : new ArrayList<>(keysAtb).get(i + 1);

                Object curObj = ligne.get(attributAndIndex.get(currentKey)),
                        nextObj = ligne.get(attributAndIndex.get(nextKey));

                if (curObj.equals(nextObj))
                    rowsToHold.add(ligne);
            }
        }
        return rowsToHold;
    }
}
