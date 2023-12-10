package commands.executors.create.createFactory;

import commands.executors.create.Create;

import java.util.HashMap;
import java.util.Map;

public abstract class CreateFactory {

    private static final Map<String, ICreateBuilder> builders = new HashMap<>();

    static {
        builders.put("TABLE", new CreateRelationBuilder());
        builders.put("BASE", new CreateBaseBuilder());
        builders.put("SEQUENCE", new CreateSequenceBuilder());
    }

    public static Create getCreateType(String keyWord) throws Exception {
        ICreateBuilder builder = builders.get(keyWord.toUpperCase());
        if (builder == null) {
            throw new Exception("CREATE ERROR: Tsy mety e commande create " + keyWord + " nama!");
        }
        return builder.build();
    }
}
