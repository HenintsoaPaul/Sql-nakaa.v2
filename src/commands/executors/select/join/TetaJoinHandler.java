package commands.executors.select.join;

import composants.Predicat;
import composants.relations.Relation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;

public class TetaJoinHandler extends JoinHandler {
    @Override
    public Relation handle(List<Relation> relations, List<String> splitQuery)
            throws Exception {
        /*
         query be like:
            aboay aby ame t1 teta[ t1.A == t2.B ] t2 teta[ t2.V <= t3.K ] t3 ...
         splitQuery be like:
            { aboay, aby, ame, t1, teta[, t1.A, ==, t2.B, ], t2, teta[, t2.V, <=, t3.K, ], t3, ... }
         */

        return processTetaJoin(relations, splitQuery);
    }

    @SuppressWarnings("rawtypes")
    private Relation processTetaJoin(List<Relation> relations, List<String> splitQuery)
            throws Exception {

        // on prend toutes les tetaConditions dans la query
        List<Predicat> predicates = getAllPredicates(splitQuery);

        // on réalise les tetaConditions 2 à 2 de la gauche vers la droite
        /*
            a- (t1 x t2) as tab
            b- select * from tab where t1.A == t2.B      <|> == : cond1
            c- on remplace ensuite les 2 premieres relations(t1, t2) par tab et on supprime cond1
            - (tab x t3) as tab2
            - select * from tab2 where tab.V <= t3.K    <|> <= : cond2
            - on remplace tab et t3 par tab2 et on supprime cond2
            - ...
         */
        CrossJoinHandler xHandler = new CrossJoinHandler();
        while (relations.size() > 1) {
            // a
            List<Relation> tmp = new ArrayList<>();
            tmp.add(relations.get(0));
            tmp.add(relations.get(1));
            Relation tab = xHandler.processCrossJoin(tmp);

            // b
            Vector<Vector> lignes = predicates.get(0).getMatchingRows(tab);
            tab.setLignes(lignes);

            // c
            predicates.remove(0);
            relations.remove(0);
            relations.remove(0);
            relations.add(0, tab);
        }

        return relations.get(0);
    }

    private List<Predicat> getAllPredicates(List<String> splitQuery) {
        List<Predicat> list = new ArrayList<>();
        for (int i = 0; i < splitQuery.size(); i++) {
            String str = splitQuery.get(i);
            if (str.equals("teta[")) {
                list.add(getPredicate(splitQuery, i));
            }
        }
        return list;
    }
    private Predicat getPredicate(List<String> splitQuery, int beginIndex) {
        // beginIndex : index of "teta["

        List<String> result = new ArrayList<>();
        for (int i = beginIndex + 1; !splitQuery.get(i).equals("]"); i++) {
            result.add(splitQuery.get(i));
        }
        return new Predicat(result);
    }

    @Override
    List<String> getRelationsName(List<String> splitQuery) {
        return getRelationsNames(splitQuery, "]");
    }

    @SuppressWarnings("rawtypes")
    @Override
    public Relation joinTwoRelations(Relation relation1, Relation relation2, String tetaCondition)
            throws Exception {
        List<Relation> relations = Arrays.asList(relation1, relation2);
        Relation xJoined = new CrossJoinHandler().processCrossJoin(relations);

        String[] split = tetaCondition.split(" ");
        List<String> ktr = new ArrayList<>(Arrays.asList(split).subList(1, split.length - 1));

        Vector<Vector> lignes = new Predicat(ktr).getMatchingRows(xJoined);
        xJoined.setLignes(lignes);

        return xJoined;
    }
}
