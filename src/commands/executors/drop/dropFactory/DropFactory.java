package commands.executors.drop.dropFactory;

import commands.executors.drop.Drop;

import java.util.HashMap;
import java.util.Map;

public abstract class DropFactory {

    private static final Map<String, IDropBuilder> builders = new HashMap<>();

    static {
        builders.put("TABLE", new DropRelationBuilder());
        builders.put("BASE", new DropBaseBuilder());
        builders.put("SEQUENCE", new DropSequenceBuilder());
    }

    public static Drop getDropType(String keyWord) throws Exception {
        IDropBuilder builder = builders.get(keyWord.toUpperCase());
        if (builder == null) {
            throw new Exception("CREATE ERROR: Tsy mety e commande drop \""
                    + keyWord + "\" nama!");
        }
        return builder.build();
    }
}
