package commands.executors.select;

import commands.IExecutor;
import commands.executors.select.join.JoinHandler;
import commands.executors.select.join.JoinHandlerFactory;
import composants.relations.Relation;
import exe.Affichage;
import exe.Interpreter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Select2 implements IExecutor {

    @Override
    public void execute(String[] commands, Interpreter interpreter)
            throws Exception {

        Relation relation = select(commands, interpreter);
        Affichage.afficherDonnees(relation);
    }

    public Relation select(String[] commands, Interpreter interpreter)
            throws Exception {

        List<String> splitQuery = Arrays.asList(commands),
                joinPart = getJoinPart(splitQuery),
                joinIndicators = getJoinIndicators(joinPart);

        List<ArgJoin> argJoins = getArgJoins(joinPart, interpreter.getDbPath());
        List<Relation> relations = getRelations(argJoins, interpreter);

        // process jointures 2 by 2
        Relation rel = relations.get(0);
        for (int i = 1; i < relations.size(); i++) {
            rel = joinRelations(rel, relations.get(i), joinIndicators.get(i - 1));
        }

        // where ...
        return rel;
    }

    private Relation joinRelations(Relation relation1, Relation relation2, String joinIndicator)
            throws Exception {
        String joinIdentifierBegin = joinIndicator.split(" ")[0];
        JoinHandler joinHandler = JoinHandlerFactory.build(joinIdentifierBegin);
        return joinHandler.joinTwoRelations(relation1, relation2);
    }

    private List<Relation> getRelations(List<ArgJoin> argJoins, Interpreter interpreter)
            throws Exception {
        List<Relation> relations = new ArrayList<>();
        for (ArgJoin argJoin : argJoins) {
            relations.add(argJoin.process(interpreter));
        }
        return relations;
    }

    private List<ArgJoin> getArgJoins(List<String> joinPart, String pathToDb) {
        List<ArgJoin> list = new ArrayList<>();

        List<String> argJoinKtr = new ArrayList<>();
        for (int i = 0; i < joinPart.size(); i++) {
            // un arg join se termine par un "+" ou par la fin de joinPart
            String str = joinPart.get(i);
            if (str.equals("+")) {
                if (!argJoinKtr.isEmpty()) {
                    ArgJoin arg = new ArgJoin(argJoinKtr, pathToDb);
                    list.add(arg);
                    argJoinKtr = new ArrayList<>();
                }
            }
            else {
                argJoinKtr.add(str);
            }
        }
        ArgJoin arg = new ArgJoin(argJoinKtr, pathToDb);
        list.add(arg);

        return list;
    }

    private List<String> getJoinIndicators(List<String> joinPart) {
        /*
        - on regroupe tous les joinIndicators dans un list<string>
        - on remplace les joinIndicators dans joinPart par des "+"
         */
        List<String> list = new ArrayList<>();

        List<String> joiners = Arrays.asList("x", "X", ",");
        boolean isInTeta = false;
        StringBuilder stringBuilder = null;

        for (int i = 0; i < joinPart.size(); i++) {
            String str = joinPart.get(i);

            if (str.equals("]")) {
                assert stringBuilder != null;
                stringBuilder.append(str);
                list.add(stringBuilder.toString());
                isInTeta = false;
                stringBuilder = null;
                joinPart.set(i, "+");
            }
            else if (isInTeta) {
                stringBuilder.append(str).append(" ");
                joinPart.set(i, "+");
            }
            else if (joiners.contains(str)) {
                list.add(str);
                joinPart.set(i, "+");
            }
            else if (str.equalsIgnoreCase("teta[")) {
                stringBuilder = new StringBuilder();
                stringBuilder.append(str).append(" ");
                isInTeta = true;
                joinPart.set(i, "+");
            }
        }

        return list;
    }

    private List<String> getJoinPart(List<String> splitQuery) {
        String from = "ame", where = "refa";
        int indexWhere = splitQuery.contains(where) ?
                splitQuery.lastIndexOf(where) : splitQuery.lastIndexOf(where.toUpperCase()),

                indexFrom = splitQuery.contains(from) ?
                        splitQuery.indexOf(from) : splitQuery.indexOf(from.toUpperCase());

        if (indexWhere == -1)
            indexWhere = splitQuery.size();

        return splitQuery.subList(indexFrom + 1, indexWhere);
    }
}
