package commands.executors.select.join;

import composants.relations.Relation;

import java.util.List;

public class NoJoinHandler extends JoinHandler{
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
