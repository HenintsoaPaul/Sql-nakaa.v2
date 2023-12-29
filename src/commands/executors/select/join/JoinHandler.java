package commands.executors.select.join;

import composants.relations.Relation;

import java.util.List;

public abstract class JoinHandler {
    public abstract Relation handle(List<String> splitQuery, String dbPath, Relation relationWhere)
            throws Exception;
}
