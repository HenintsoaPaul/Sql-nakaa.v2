package commands.executors.select.join;

import composants.relations.Relation;

import java.util.List;

public class NaturalJoinHandler extends JoinHandler {
    @Override
    public Relation handle(List<String> splitQuery, String dbPath, Relation relationWhere) {
        return null;
    }
}
