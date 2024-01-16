package commands.executors;

import commands.IExecutor;
import commands.executors.create.CreateFactory;
import commands.executors.divEuclidienne.DivEuclidienne;
import commands.executors.drop.DropFactory;
import commands.executors.insert.Insert;
import commands.executors.select.Projection;
import commands.executors.select.Select2;
import commands.executors.system.DescribeTable;
import commands.executors.system.Help;
import commands.executors.system.ShowTables;

import java.util.HashMap;

public abstract class ExecutorBuilder {

    static final HashMap<String, IExecutor> executors = new HashMap<>();

    static {
        executors.put("MAMOROOGNA", new CreateFactory());
        executors.put("FAFAY", new DropFactory());
        executors.put("APIDIRO", new Insert());
        executors.put("ABOAY", new Select2());
        executors.put("PROJETEO", new Projection());
        executors.put("DIVEK", new DivEuclidienne());

        // commands system
        executors.put("HELP", new Help());
        executors.put("ABOAY_TABLES", new ShowTables());
        executors.put("FARIPARITO", new DescribeTable());
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
