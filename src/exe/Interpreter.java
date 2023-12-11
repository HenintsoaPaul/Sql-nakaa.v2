package exe;

import commands.IExecutor;
import commands.executors.ExecutorBuilder;
import tools.verifier.BaseVerifier;

@SuppressWarnings("unused")
public class Interpreter {

    Sql_nakaa sql_nakaa;


    // Constructor
    public Interpreter(Sql_nakaa sql_nakaa) {

        this.setHost( sql_nakaa );
    }



    // Getters
    public Sql_nakaa getHost() {
        // return the exe.Sql_nakaa instance

        return this.sql_nakaa;
    }
    public String getDbName() {
        // return the name of the current db

        return this.getHost().getDbName();
    }
    public String getDbPath() {
        // return path the current db

        return this.getHost().getDbPath();
    }



    // Setters
    public void setHost(Sql_nakaa h) {
        this.sql_nakaa = h;
    }

    public void changeDb(String baseName) throws Exception {

        if ( this.getHost().getDbName().compareTo( baseName ) == 0 )
            throw new Exception("Efa amn'io Base io anao zao nama!");

        new BaseVerifier().verifyExisting(baseName);

        this.getHost().changeDb(baseName);
        System.out.println("Yesss, nifindra ame Base '"
                + baseName+ "' itsika zao nama!");
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
