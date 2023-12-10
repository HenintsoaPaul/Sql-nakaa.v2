package exe;

import commands.executors.select.Select;
import commands.executors.create.Create;
import commands.executors.drop.Drop;
import commands.executors.insert.Insert;
import tools.verifier.BaseVerifier;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;

@SuppressWarnings("unused")
public class Interpreter {

    Sql_nakaa sql_nakaa;
    HashMap<String, Class<?>> executors =
            new HashMap<>();


    // Constructor
    public Interpreter(Sql_nakaa sql_nakaa) {

        this.setHost( sql_nakaa );
        this.setExecutors();
    }

    private void setExecutors() {
        executors.put("MAMOROOGNA", Create.class);
        executors.put("FAFAY", Drop.class);
        executors.put("APIDIRO", Insert.class);
        executors.put("ABOAY", Select.class);
        // ...
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

    /**
     *
     * @param firstWord - first word of the input command
     * @return - the class of the right IExecutor
     */
    public Class<?> getExecutorName(String firstWord) {

        return executors.get(firstWord.toUpperCase());
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
    @SuppressWarnings("rawtypes")
    public boolean translate(String input)
            throws NoSuchMethodException,
            InvocationTargetException, IllegalAccessException {

        boolean toContinue = true;
        String[] commands = input.split(" ");

        try {
            Class<?> executorClass = getExecutorName(commands[0]);

            Object caller = executorClass.newInstance();

            Class[] params = new Class[2];
            params[0] = String[].class;
            params[1] = Interpreter.class;

            Method execute = executorClass.getDeclaredMethod("execute", params);

            execute.invoke(caller, commands, this);
        }
        catch (NullPointerException e) { // firstWord not in executorsList

            if (commands[0].equalsIgnoreCase("VELOMA")) {
                toContinue = false;
            }

        } catch (InstantiationException e) {

            throw new RuntimeException(e);
        }
        return toContinue;
    }
}
