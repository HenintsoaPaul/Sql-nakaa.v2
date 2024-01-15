package commands.executors.select;

import commands.IExecutor;
import commands.executors.select.join.JoinHandlerFactory;
import commands.executors.select.where.SelectLines;
import composants.relations.Relation;
import exe.Affichage;
import exe.Interpreter;
import tools.Funct;
import tools.loaders.RelationLoader;
import tools.verifier.RelationVerifier;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

public class Select implements IExecutor {

    public Select() {}

    @Override
    public void execute(String[] commands, Interpreter interpreter)
            throws Exception {

        Relation relation = select(commands, interpreter);
        Affichage.afficherDonnees(relation);
    }

    public static Relation select(String[] commands, Interpreter inter)
            throws Exception {

        // ---
        new Select2().execute(commands, inter);
        // ---

        String dbPath = inter.getDbPath(),
                nomRelation = Funct.getNomRelation( commands, "AME" );

        new RelationVerifier(dbPath).verifyExisting( nomRelation );

        Relation rel = RelationLoader.loadRelation( nomRelation, dbPath );

        List<String> splitQuery = Arrays.asList(commands);

        // verification des parentheses
        verifyParentheses(splitQuery);

        // on prend les indices des parentheses
        List<List<Integer>> indexesParentheses = getIndexesParentheses(splitQuery);
        for (List<Integer> pair : indexesParentheses) {
            System.out.println(pair.get(0) + ";" + pair.get(1));
            System.out.println("---");
        }

        // on regroupe tous les string dans des parentheses
        List<List<String>> strInParentheses = getStrInParentheses(splitQuery, indexesParentheses);
        for (List<String> sublist : strInParentheses) {
            for (String str : sublist) System.out.print(str + ";");
            System.out.println("\n---");
        }

        // RELATIONS <- JOIN
        List<String> joinIndicators = Arrays.asList("x", "X", ",", "teta[");
        for (String joinIndicator : joinIndicators) {
            if (splitQuery.contains(joinIndicator)) {
                rel = JoinHandlerFactory
                        .build(splitQuery, rel)
                        .join(splitQuery, dbPath, rel);
                break;
            }
        }

        // LIGNES <- WHERE
        rel = SelectLines.selectWhere(commands, rel);

        // COLONNES
        String[] columnsName = SelectColumns.getColumnsName(commands);
        rel = Projection.project(columnsName, rel);

        // LIMIT
        Limit.handleLimit(splitQuery, rel);

        return rel;
    }

    private static List<List<String>> getStrInParentheses(List<String> splitQuery, List<List<Integer>> indexesParentheses) {
        List<List<String>> list = new ArrayList<>();
        for (List<Integer> pair : indexesParentheses) {
            List<String> sublist = new ArrayList<>();
            int indexOuvert = pair.get(0);
            for (int i = indexOuvert + 1; i < pair.get(1); i++) {
                sublist.add(splitQuery.get(i));
            }
            list.add(sublist);
        }
        return list;
    }

    private static List<List<Integer>> getIndexesParentheses(List<String> splitQuery) {
        List<List<Integer>> list = new ArrayList<>();
        Stack<Integer> pile = new Stack<>();
        for (int i = 0; i < splitQuery.size(); i ++) {
            String str = splitQuery.get(i);
            if (str.equals("(")) {
                pile.push(i);
            }
            else if (str.equals(")")) {
                List<Integer> pair = new ArrayList<>(2);
                pair.add(pile.pop());
                pair.add(i);
                list.add(pair);
            }
        }
        return list;
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
}