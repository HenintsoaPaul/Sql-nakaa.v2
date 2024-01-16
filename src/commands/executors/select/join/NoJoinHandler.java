package commands.executors.select.join;

import composants.relations.Relation;

import java.util.List;

public class NoJoinHandler extends JoinHandler{
    @Override
    public Relation joinTwoRelations(Relation relation1, Relation relation2, String tetaCondition)
            throws Exception {
        return null;
    }

    final Relation relationWhere;

    public NoJoinHandler(Relation relationWhere) {
        this.relationWhere = relationWhere;
    }

    @Override
    public Relation handle(List<Relation> relations, List<String> splitQuery) {
        return this.relationWhere;
    }

    @Override
    List<String> getRelationsName(List<String> splitQuery) {
        return null;
    }
}
