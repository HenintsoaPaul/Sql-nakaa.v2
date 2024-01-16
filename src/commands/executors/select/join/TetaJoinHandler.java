package commands.executors.select.join;

import composants.Predicat;
import composants.relations.Relation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;

public class TetaJoinHandler extends JoinHandler {

    @SuppressWarnings("rawtypes")
    @Override
    public Relation joinRelations(Relation relation1, Relation relation2, String tetaCondition)
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
