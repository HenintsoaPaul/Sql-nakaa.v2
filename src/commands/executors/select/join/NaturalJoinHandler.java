package commands.executors.select.join;

import composants.relations.Attribut;
import composants.relations.Relation;

import java.util.*;

public class NaturalJoinHandler extends JoinHandler {
    @Override
    public Relation handle(List<Relation> relations)
            throws Exception {
        /*
         query be like:
            aboay aby ame t1 , t2 , t3 , ...
         splitQuery be like:
            [aboay, aby, ame, t1, ,, t2, ,, t3, ...]
         */

        return processNaturalJoin(relations);
    }

    @Override
    List<String> getRelationsName(List<String> splitQuery) {
        List<String> relationsName = new ArrayList<>();
        int ind = splitQuery.indexOf(",");

        for (int i = ind; i < splitQuery.size(); i++) {
            String str = splitQuery.get(i);
            if (str.equals(",")) {
                relationsName.add(splitQuery.get(i+1));
            }
        }

        return relationsName;
    }

    private Relation processNaturalJoin(List<Relation> relations)
            throws Exception {

        Relation rel = new CrossJoinHandler()
                .processCrossJoin(relations);

        // on prend les noms de tout les attributs
        List<String> atbNamesList = new ArrayList<>();
        List<Attribut> attributs = Arrays.asList(rel.getAttributs());
        for (int i = 0; i < attributs.size(); i++) {
            String atbName = attributs.get(i).getNomAttribut();
            atbNamesList.add(atbName);
        }

        // on regroupe les noms des attributs qui sont identiques
        Map<String, List<String>> groupedAttributes = new HashMap<>();
        for (String attributeName : atbNamesList) {
            if (attributeName.contains(".")) {
                String nameAfterDot = attributeName.split("\\.")[1];
                if (!groupedAttributes.containsKey(nameAfterDot)) {
                    groupedAttributes.put(nameAfterDot, new ArrayList<>());
                }
                groupedAttributes.get(nameAfterDot).add(attributeName);
            }
        }

        Set<String> keys = groupedAttributes.keySet();
        Vector<Vector> rows = new Vector<>();
        for (String key: keys) {

            // get the index of each attrib
            List<String> attributes = groupedAttributes.get(key);
            Map<String, Integer> attributAndIndex = new HashMap<>();
            for (String  atbName : attributes) {
                int index = Integer.valueOf(rel.getIndexAttrib(atbName));
                attributAndIndex.put(atbName, index);
            }

            // on va parcourir toutes les rows
            Vector<Vector> rowsToHold = new Vector<>();
            for (Vector ligne : rel.getLignes()) {

                boolean areEquals = true;
                Set<String> keysAtb = attributAndIndex.keySet();
                // on va verifier que tous les attributs du key actuel contiennent la meme valeur
                for (int i = 0; i < keysAtb.size() - 1; i++) {
                    String  currentKey = new ArrayList<>(keysAtb).get(i);
                    String  nextKey = i + 1 >= keysAtb.size() ? null : new ArrayList<>(keysAtb).get(i + 1);

                    Object curObj = ligne.get(attributAndIndex.get(currentKey).intValue()),
                            nextObj = ligne.get(attributAndIndex.get(nextKey).intValue());

                    System.out.println("curKey: " + currentKey + ", val: " + curObj);
                    System.out.println("nextKey: " + nextKey + ", val: " + nextObj);
                    System.out.println("equality: " + curObj.equals(nextObj));
                    System.out.println("---");

                    // des le moment ou on trouve 2 differents, on skip la ligne
//                    if (!curObj.equals(nextObj)) {
//                        areEquals = false;
//                        break;
//                    }
                    if (curObj.equals(nextObj))
                        rowsToHold.add(ligne);
                }
//                if (areEquals)
//                    rowsToHold.add(ligne);, index: " + nextIndex +
            }
            if (rowsToHold.size() > 0) {
                rows.addAll(rows);
                rel.setLignes(rows);
            }
        }
//        keys.forEach(key -> {
//
//            System.out.println("key -> " + key);
//            List<String> attributes = groupedAttributes.get(key);
//
//            Map<String, Integer> attributeAndIndex = new HashMap<>();
//            for (String  atbName : attributes) {
//                try {
//                    attributeAndIndex.put( atbName, Integer.valueOf(rel.getIndexAttrib(atbName)) );
//                } catch (Exception e) {
//                    throw new RuntimeException(e);
//                }
//            }
//
//            Vector<Vector> rowsToHold = new Vector<>();
//            for (Vector row : rel.getLignes()) {
//                boolean areEqual = IntStream.range(0, attributeAndIndex.size() - 1)
//                        .allMatch(i -> row.get(attributeAndIndex
//                                        .get(new ArrayList<>(attributeAndIndex.keySet())
//                                                .get(i)))
//                                .equals(row.get(attributeAndIndex
//                                        .get(new ArrayList<>(attributeAndIndex.keySet())
//                                                .get(i + 1)))));
//
//                if (areEqual) {
//                    rowsToHold.add(row);
//                }
//            }
//
//            if (!rowsToHold.isEmpty()) {
//                rows.addAll(rowsToHold);
//                rel.setLignes(rows);
//            }
//        });
        return rel;
    }
}
