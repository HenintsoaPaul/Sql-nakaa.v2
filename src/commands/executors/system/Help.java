package commands.executors.system;

import commands.IExecutor;
import exe.Interpreter;

public class Help implements IExecutor {
    @Override
    public void execute(String[] commands, Interpreter interpreter) {
        showHelpMenu();
    }

    private void showHelpMenu() {
        String helpScript = "#LOG OUT: veloma";

        // todo: load helpScript from README.md in the root folder of the project
        // ...

        System.out.println(helpScript);
    }
}
