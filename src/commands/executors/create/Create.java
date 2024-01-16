package commands.executors.create;

import commands.IExecutor;
import exe.Interpreter;

public class Create implements IExecutor {

    public Create() {}

    @Override
    public void execute(String[] commands, Interpreter interpreter)
            throws Exception {

        createRelationalModel(commands, interpreter);
    }

    public void createRelationalModel(String[] commands, Interpreter interpreter)
            throws Exception {}
}
