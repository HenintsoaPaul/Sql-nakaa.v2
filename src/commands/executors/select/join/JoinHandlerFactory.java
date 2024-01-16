package commands.executors.select.join;

import java.util.HashMap;

public abstract class JoinHandlerFactory {
    static final HashMap<String, JoinHandler> joinHandlers = new HashMap<>();
    static {
        joinHandlers.put("x", new CrossJoinHandler());
        joinHandlers.put(",", new NaturalJoinHandler());
        joinHandlers.put("teta[", new TetaJoinHandler());
    }

    public static JoinHandler build(String joinIdentifier) {
        return joinHandlers.get(joinIdentifier);
    }
}
