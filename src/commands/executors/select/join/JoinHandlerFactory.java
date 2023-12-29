package commands.executors.select.join;

import java.util.HashMap;
import java.util.List;

public abstract class JoinHandlerFactory {
    static HashMap<String, JoinHandler> joinHandlers = new HashMap<>();
    static {
        joinHandlers.put("x", new CrossJoinHandler());
        joinHandlers.put("no", new NoJoinHandler());
    }

    public static JoinHandler build(List<String> splitQuery) {
        if (splitQuery.contains("x")) {
            return joinHandlers.get("x");
        }
        return joinHandlers.get("no");
    }
}
