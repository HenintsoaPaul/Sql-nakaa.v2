package commands.executors.drop;

import commands.IExecutor;
import commands.executors.drop.dropFactory.DropFactory;
import exe.Interpreter;

import java.lang.reflect.Method;

public class Drop implements IExecutor {

    public Drop() {}



    @SuppressWarnings("rawtypes")
    @Override
    public void execute(String[] commands, Interpreter interpreter)
            throws Exception {

        Drop caller = DropFactory.getDropType(commands[1]);

        Class[] params = new Class[2];
        params[0] = String[].class;
        params[1] = Interpreter.class;

        Method execute = caller.getClass()
                .getDeclaredMethod("drop", params);

        execute.invoke(caller, commands, interpreter);
    }
}