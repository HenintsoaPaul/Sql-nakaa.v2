package commands.executors.drop;

import commands.IExecutor;
import exe.Interpreter;

import java.util.HashMap;
import java.util.Map;

public class DropFactory implements IExecutor {

    private static final Map<String, Drop> builders = new HashMap<>();

    static {
        builders.put("TABLE", new DropRelation());
        builders.put("BASE", new DropBase());
        builders.put("SEQUENCE", new DropSequence());
    }

    public static Drop getDropType(String keyWord) throws Exception {
        Drop dropType = builders.get(keyWord.toUpperCase());
        if (dropType == null) {
            throw new Exception("CREATE ERROR: Tsy mety e commande drop \""
                    + keyWord + "\" nama!");
        }
        return dropType;
    }

    @Override
    public void execute(String[] commands, Interpreter interpreter)
            throws Exception {
        String keyWord = commands[1];
        Drop dropType = getDropType(keyWord);
        dropType.execute(commands, interpreter);
    }
}
