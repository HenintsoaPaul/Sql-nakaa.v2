package commands.executors.create;

import commands.IExecutor;
import commands.executors.create.createFactory.CreateFactory;
import exe.Interpreter;

import java.lang.reflect.Method;

public class Create implements IExecutor {

    public Create() {}



    @SuppressWarnings("rawtypes")
    @Override
    public void execute(String[] commands, Interpreter interpreter)
            throws Exception {

        Create caller = CreateFactory.getCreateType(commands[1]);

        Class[] params = new Class[2];
        params[0] = String[].class;
        params[1] = Interpreter.class;

        Method execute = caller.getClass()
                .getDeclaredMethod("create", params);

        execute.invoke(caller, commands, interpreter);
    }
}
