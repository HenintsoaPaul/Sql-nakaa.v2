package exe;

import commands.IExecutor;
import commands.executors.ExecutorBuilder;
import tools.verifier.BaseVerifier;

@SuppressWarnings("unused")
public class Interpreter {
    DataStorageManager dataStorageManager;

    public DataStorageManager getDataStorageManager() {
        return dataStorageManager;
    }

    public void setDataStorageManager(DataStorageManager dataStorageManager) {
        this.dataStorageManager = dataStorageManager;
    }

    // Constructor
    public Interpreter(DataStorageManager dataStorageManager) {
        setDataStorageManager(dataStorageManager);
    }




    // Getters
    public String getDbName() {
        return this.getDataStorageManager().getDbName();
    }
    public String getDbPath() {
        return this.getDataStorageManager().getDbPath();
    }



    // Setters
    public void changeDb(String baseName) throws Exception {

        if ( getDbName().compareTo( baseName ) == 0 )
            throw new Exception("Efa amn'io Base io anao zao nama!");

        new BaseVerifier(this.getDbPath()).verifyExisting(baseName);

        dataStorageManager.changeDb(baseName);
    }



    /**
     * @param input: the last command entered by the user in the cmd
     *  -> It translates the input into something that the
     *     program can understand, and execute.
     * @return - bool telling the app whether to stop or continue running
     */
    public boolean translate(String input) {

        boolean toContinue = true;
        String[] commands = input.split(" ");

        String firstWord = commands[0].toUpperCase();
        try {
            IExecutor executor = ExecutorBuilder.build(firstWord);

            executor.execute(commands, this);
        }
        catch (NullPointerException e) { // firstWord not in executorsList

            if (firstWord.equalsIgnoreCase("VELOMA")) {
                toContinue = false;
            }
        }
        catch (Exception e) {

            throw new RuntimeException(e);
        }

        return toContinue;
    }
}
