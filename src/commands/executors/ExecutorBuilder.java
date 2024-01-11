package commands.executors;

import commands.IExecutor;
import commands.executors.create.Create;
import commands.executors.divEuclidienne.DivEuclid;
import commands.executors.drop.Drop;
import commands.executors.insert.Insert;
import commands.executors.select.Projection;
import commands.executors.select.Select;

import java.util.HashMap;

public abstract class ExecutorBuilder {

    static HashMap<String, IExecutor> executors = new HashMap<>();

    static {
        executors.put("MAMOROOGNA", new Create());
        executors.put("FAFAY", new Drop());
        executors.put("APIDIRO", new Insert());
        executors.put("ABOAY", new Select());
        executors.put("PROJETEO", new Projection());
        executors.put("DIVEK", new DivEuclid());
    }


    /**
     *
     * @param firstWord - first word of the input command
     * @return - the class of the right IExecutor
     */
    public static IExecutor build(String firstWord) {

        return executors.get(firstWord);
    }
}
