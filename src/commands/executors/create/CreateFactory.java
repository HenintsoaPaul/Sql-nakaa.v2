package commands.executors.create;

import commands.IExecutor;
import exe.Interpreter;

import java.util.HashMap;
import java.util.Map;

public class CreateFactory implements IExecutor {

    private static final Map<String, Create> builders = new HashMap<>();

    static {
        builders.put("TABLE", new CreateRelation());
        builders.put("BASE", new CreateBase());
        builders.put("SEQUENCE", new CreateSequence());
    }

    public static Create getCreateType(String keyWord) throws Exception {
        Create createType = builders.get(keyWord.toUpperCase());
        if (createType == null) {
            throw new Exception("CREATE ERROR: Tsy mety e commande create " + keyWord + " nama!");
        }
        return createType;
    }


    @Override
    public void execute(String[] commands, Interpreter interpreter)
            throws Exception {
        String keyWord = commands[1];
        Create createType = getCreateType(keyWord);
        createType.execute(commands, interpreter);
    }
}
