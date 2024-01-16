package commands.executors.select.where;

import commands.executors.select.ArgWhere;
import commands.executors.select.Select2;
import commands.executors.select.conditions.ConditionGetter;
import composants.relations.Relation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;

@SuppressWarnings({"rawTypes", "rawtypes"})
public abstract class SelectLines {
    static final String whereKeyWord = "refa";

    /**
     * It returns all the rows of the specified relation rel. But only,
     * the specified columns will be hold for each row.
     * @param rel The relation from which we will take the rows.
     * @param columnsName The names of the desired columns to be hold.
     */
    @SuppressWarnings("unchecked")
    public static Vector<Vector> getAllLines(Relation rel, String[] columnsName )
            throws Exception {

        Vector<Vector> lignes = new Vector<>();

        int[] indexAttribs = rel.getIndexAttribs(columnsName);

        Vector<Vector> dataOrg = rel.getLignes();
        for ( Vector ligneOrg: dataOrg ) {

            Vector ligne = new Vector(); // ajout des colonnes specifies pour chq ligne
            for (int indexAttrib : indexAttribs)
                ligne.add(ligneOrg.get(indexAttrib));

            lignes.add( ligne );
        }
        return lignes;
    }


    /**
     * Drop rows of rel who don't respect the conditions.
     */
    public static Relation selectWhere(String[] commande, Relation rel)
            throws Exception {

        List<String> cmd = Arrays.asList(commande);
        return selectWhere(cmd, rel);
    }
    public static Relation selectWhere(List<String> splitQuery, Relation rel)
            throws Exception {
        int indexWhere = Select2.getIndexZeroLevelParentheses(splitQuery, whereKeyWord);

        if ( indexWhere != -1 ) {
            List<String> wherePart = getWherePart(splitQuery, indexWhere);
            rel = getRelationFromWherePart(wherePart, rel);
        }
        return rel;
    }

    public static Relation getRelationFromWherePart(List<String> wherePart, Relation relation)
            throws Exception {
        List<String> conditionSeparators = getConditionSeparators(wherePart);
        List<ArgWhere> argWheres = getArgWheres(wherePart);
        List<Relation> relations = getRelationsFromArgWheres(argWheres, relation);

        return ConditionGetter.getRelationFromSeparators(relations, conditionSeparators);
    }

    private static List<Relation> getRelationsFromArgWheres(List<ArgWhere> argWheres, Relation rel)
            throws Exception {
        List<Relation> relations = new ArrayList<>();
        for (ArgWhere argWhere : argWheres) {
            Relation relationFromArgWhere = argWhere.process(rel);
            relations.add(relationFromArgWhere);
        }
        return relations;
    }

    private static List<ArgWhere> getArgWheres(List<String> wherePart) {
        List<ArgWhere> list = new ArrayList<>();

        List<String> argWhereKtr = new ArrayList<>();
        for (String str : wherePart) {
            // un argWhere se termine par un "+" ou par la fin de joinPart
            if (str.equals("+")) {
                if (!argWhereKtr.isEmpty()) {
                    ArgWhere arg = new ArgWhere(argWhereKtr);
                    list.add(arg);
                    argWhereKtr = new ArrayList<>();
                }
            } else {
                argWhereKtr.add(str);
            }
        }
        ArgWhere arg = new ArgWhere(argWhereKtr);
        list.add(arg);

        return list;
    }

    private static List<String> getConditionSeparators(List<String> wherePart) {
        /*
        - on regroupe tous les conditionSeparators dans un list<string>
        - on remplace les conditionSeparators dans wherePart par des "+"
         */
        List<String> list = new ArrayList<>(),
                conditionSeparators = Arrays.asList("na", "ary");
        int parenthesesCounter = 0;
        for (int i = 0; i < wherePart.size(); i++) {
            String str = wherePart.get(i);
            if (str.equals("(")) parenthesesCounter ++;
            else if (str.equals(")")) parenthesesCounter --;
            else if (parenthesesCounter == 0 && conditionSeparators.contains(str.toLowerCase())) {
                list.add(str);
                wherePart.set(i, "+");
            }
        }
        return list;
    }

    private static List<String> getWherePart(List<String> splitQuery, int indexWhere) {
        List<String> wherePartFinishers = Arrays.asList("limit", "order_by");
        int indexEnd = -1, parenthesesCounter = 0;

        for (int i = indexWhere + 1; i < splitQuery.size(); i++) {
            String str = splitQuery.get(i);
            if (str.equals("(")) parenthesesCounter ++;
            else if (str.equals(")")) parenthesesCounter --;
            else if (parenthesesCounter == 0 && wherePartFinishers.contains(str.toLowerCase())) {
                indexEnd = i;
                break;
            }
        }

        if (indexEnd == -1) indexEnd = splitQuery.size();
        return splitQuery.subList(indexWhere + 1, indexEnd);
    }
}
