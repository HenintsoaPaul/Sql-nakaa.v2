package commands.executors.select;

import commands.IExecutor;
import commands.executors.select.join.JoinHandler;
import commands.executors.select.join.JoinHandlerFactory;
import commands.executors.select.where.SelectLines;
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

        verifyParentheses(splitQuery);

        List<ArgJoin> argJoins = getArgJoins(joinPart, interpreter.getDbPath());
        List<Relation> relations = getRelationsFromArgJoins(argJoins, interpreter);

        JoinHandler.renameIdenticalAttributs(relations);

        // process jointures 2 by 2
        Relation rel = relations.get(0);
        for (int i = 1; i < relations.size(); i++) {
            rel = joinRelations(rel, relations.get(i), joinIndicators.get(i - 1));
        }

        // where ...
        rel = SelectLines.selectWhere(commands, rel);

        Limit.handleLimit(rel, commands);

        // columns
        String[] columnsName = SelectColumns.getColumnsName(commands);
        if (columnsName.length == 0) {
            throw new RuntimeException("Tsy misy columns ao amin'io query '" + String.join(" ", commands) + "' io!");
        }
        rel = Projection.project(columnsName, rel);

        return rel;
    }

    private Relation joinRelations(Relation relation1, Relation relation2, String joinIndicator)
            throws Exception {
        String joinIdentifierBegin = joinIndicator.split(" ")[0];
        JoinHandler joinHandler = JoinHandlerFactory.build(joinIdentifierBegin);
        return joinHandler.joinRelations(relation1, relation2, joinIndicator);
    }

    private List<Relation> getRelationsFromArgJoins(List<ArgJoin> argJoins, Interpreter interpreter)
            throws Exception {
        List<Relation> relations = new ArrayList<>();
        for (ArgJoin argJoin : argJoins) {
            Relation relationFromArgJoin = argJoin.process(interpreter);
            relations.add(relationFromArgJoin);
        }
        return relations;
    }

    private List<ArgJoin> getArgJoins(List<String> joinPart, String pathToDb) {
        List<ArgJoin> list = new ArrayList<>();

        List<String> argJoinKtr = new ArrayList<>();
        for (String str : joinPart) {
            // un arg join se termine par un "+" ou par la fin de joinPart
            if (str.equals("+")) {
                if (!argJoinKtr.isEmpty()) {
                    ArgJoin arg = new ArgJoin(argJoinKtr, pathToDb);
                    list.add(arg);
                    argJoinKtr = new ArrayList<>();
                }
            } else {
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

        int parenthesesCounter = 0;
        for (int i = 0; i < joinPart.size(); i++) {
            String str = joinPart.get(i);

            if (str.equals("(")) parenthesesCounter ++;
            else if (str.equals(")")) parenthesesCounter --;
            else if (parenthesesCounter == 0) {
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
        }

        return list;
    }

    private List<String> getJoinPart(List<String> splitQuery) {
        int lenSplitQuery = splitQuery.size(), indexFrom, indexEndJoinPart = -1;

        String from = "ame";
        indexFrom = splitQuery.contains(from) ?
                splitQuery.indexOf(from) : splitQuery.indexOf(from.toUpperCase());

        // joinPartClosers tsy anaty parentheses == toutes les parentheses ouvertes ont ete fermees
        List<String> joinPartClosers = Arrays.asList("refa", "limit", "order_by");
        int parenthesesCounter = 0;
        for (int i = indexFrom; i < splitQuery.size(); i++) {
            String str = splitQuery.get(i);
            if (str.equals("(")) parenthesesCounter ++;
            else if (str.equals(")")) parenthesesCounter --;
            else if (parenthesesCounter == 0 && joinPartClosers.contains(str.toLowerCase())) {
                indexEndJoinPart = i;
                break;
            }
        }

         int indexEnd = indexEndJoinPart == -1 ? lenSplitQuery : indexEndJoinPart;
        return splitQuery.subList(indexFrom + 1, indexEnd);
    }

    private static void verifyParentheses(List<String> splitQuery) {
        int verify = 0;
        for (String str : splitQuery) {
            if (verify < 0) {
                throw new RuntimeException("Misy parenthese(s) fermante(s) tsy manana ouvrante quelque part name!");
            }

            if (str.equals("(")) {
                verify ++;
            }
            else if (str.equals(")")) {
                verify --;
            }
        }
        if (verify > 0) {
            throw new RuntimeException("Misy parenthese(s) ouvrante(s) tsy mihidy quelque part nama!");
        }
        else if (verify < 0) {
            throw new RuntimeException("Misy parenthese(s) fermante(s) tsy mihidy quelque part nama!");
        }
    }

    public static int getIndexZeroLevelParentheses(List<String> list, String keyWord) {
        int index = -1, parenthesesCounter = 0;
        for (int i = 0; i < list.size(); i++) {
            String str = list.get(i);
            if (str.equals("(")) parenthesesCounter ++;
            else if (str.equals(")")) parenthesesCounter --;
            else if (parenthesesCounter == 0 && str.equalsIgnoreCase(keyWord)) {
                index = i;
                break;
            }
        }
        return index;
    }
}
