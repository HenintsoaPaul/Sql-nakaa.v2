package commands;

import exe.Interpreter;

public interface IExecutor {

    void execute(String[] commands, Interpreter interpreter) throws Exception;
}