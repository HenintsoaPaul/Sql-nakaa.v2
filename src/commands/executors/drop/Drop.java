package commands.executors.drop;

import commands.IExecutor;
import exe.Interpreter;

public class Drop implements IExecutor {

    public Drop() {}

    @Override
    public void execute(String[] commands, Interpreter interpreter) {

        drop(commands, interpreter);
    }

    public void drop(String[] commands, Interpreter interpreter) {}
}