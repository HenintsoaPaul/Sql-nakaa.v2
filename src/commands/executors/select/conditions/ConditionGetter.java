package commands.executors.select.conditions;

import commands.executors.select.SelectWhere;
import composants.relations.Attribut;
import composants.relations.Relation;
import composants.relations.RelationOperator;
import exe.Affichage;

import java.util.*;

@SuppressWarnings("rawtypes")
public abstract class ConditionGetter {

    /**
     * It returns each conditions in String[]( they're split using
     * " " as separator ). So, it returns a String[][] containing all
     * conditions in the query.
     * @param refa String[] from 'where'
     */
    public static List<String[]> getConditions(List<String> refa) {

        String query = String.join(" ", refa);
        String[] separators = {"na", "ary", "NA", "ARY"};
        String regex = String.join("|", separators);

        String[] conditionss= query.split(regex);
        int nbConditions = conditionss.length;
        if (nbConditions == 0)
            throw new IllegalArgumentException("Invalid nbConditions");

        List<String[]> conditions = new ArrayList<>();
        for (String s : conditionss) {

            String[] cond = s.split(" ");
            if (cond[0].isEmpty()) {
                List<String> temp = Arrays.asList(cond).subList(1, cond.length);
                cond = temp.toArray(new String[0]);
            }
            conditions.add(cond);
        }

        return conditions;
    }

    /**
     * It returns all the "ary" and "na" in the commands
     * following their apparition order.
     */
    public static String[] getConditionSeparator(String[] commands) {

        List<String> cmd = Arrays.asList(commands);
        int startIndex = cmd.contains("refa") ?
                cmd.indexOf("refa") :
                cmd.indexOf("REFA");
//        System.out.println("index refa:"+startIndex);

        List<String> separators = Arrays.asList("ary", "na");
        return Arrays.stream(commands)
                .skip(startIndex+1)
                .filter(command ->
                        separators.contains(command)
                        || separators.contains(command.toLowerCase()))
                .toArray(String[]::new);
    }

    public static Relation getRelation(List<String[]> conditions,
                                       List<String> separators,
                                       Relation relation)
            throws Exception {
        // transform each condition to a Relation
        List<Relation> relationsFromConditions = new ArrayList<>();
        Attribut[] attributs = relation.getAttributs();
        for (String[] cond: conditions) {

            Relation rel = new Relation(attributs);
            Vector<Vector> lignes = SelectWhere.getLignesWhere(relation, cond);
            rel.setLignes(lignes);
            relationsFromConditions.add(rel);
        }

        // do all "ARY"
        Iterator<String> aryIterator = separators.iterator();
        while (aryIterator.hasNext()) {
            String sep = aryIterator.next();
            if (sep.equalsIgnoreCase("ARY")) {
                processSeparator(separators, sep, relationsFromConditions, aryIterator);
            }
        }

        // do all "NA"
        Iterator<String> naIterator = separators.iterator();
        while (naIterator.hasNext()) {
            String sep = naIterator.next();
            if (sep.equalsIgnoreCase("NA")) {
                processSeparator(separators, sep, relationsFromConditions, naIterator);
            }
        }
        return relationsFromConditions.get(0);
    }
    static void processSeparator(List<String> separators, String sep,
                                 List<Relation> relationsFromConditions,
                                 Iterator<String> sepIterator)
            throws Exception {

        int indexSep = separators.contains(sep) ?
                separators.indexOf(sep.toLowerCase()) :
                separators.indexOf(sep.toUpperCase());

        // process
        Relation rel1 = relationsFromConditions.get(indexSep),
                rel2 = relationsFromConditions.get(indexSep+1);

        Relation temp = sep.equalsIgnoreCase("ary") ?
                RelationOperator.intersection(rel1, rel2) :
                RelationOperator.union(rel1, rel2);
        Affichage.afficherDonnees(temp);

        // delete the old relations rel1 and rel2 from the list
        relationsFromConditions.remove(indexSep+1);
        relationsFromConditions.set(indexSep, temp);

        // delete sep from separators
        sepIterator.remove();
    }
}
