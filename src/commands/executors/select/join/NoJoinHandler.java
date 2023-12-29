package commands.executors.select.join;

import composants.relations.Relation;

import java.util.List;

public class NoJoinHandler extends JoinHandler{
    @Override
    public Relation handle(List<String> splitQuery, String dbPath, Relation relationWhere) {
        return relationWhere;
    }
}
