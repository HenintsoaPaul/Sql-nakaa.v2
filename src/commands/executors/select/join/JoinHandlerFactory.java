package commands.executors.select.join;

import composants.relations.Relation;

import java.util.HashMap;
import java.util.List;

public abstract class JoinHandlerFactory {
    static final HashMap<String, JoinHandler> joinHandlers = new HashMap<>();
    static {
        joinHandlers.put("x", new CrossJoinHandler());
        joinHandlers.put(",", new NaturalJoinHandler());
        joinHandlers.put("teta[", new TetaJoinHandler());
    }

    public static JoinHandler build(List<String> splitQuery, Relation relationWhere) {
        if (splitQuery.contains("x")) {
            return joinHandlers.get("x");
        } else if (splitQuery.contains(",")) {
            return joinHandlers.get(",");
        } else if (splitQuery.contains("teta[")) {
            return joinHandlers.get("teta[");
        }
        return new NoJoinHandler(relationWhere);
    }
}
