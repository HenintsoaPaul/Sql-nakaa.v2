package commands.executors.select.conditions;

import composants.relations.Relation;
import composants.relations.RelationOperator;

import java.util.Iterator;
import java.util.List;

public abstract class ConditionGetter {

    public static Relation getRelationFromSeparators(List<Relation> relationsFromConditions, List<String> separators)
            throws Exception {
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

    static void processSeparator(List<String> separators, String sep, List<Relation> relationsFromConditions, Iterator<String> sepIterator)
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

        // delete the old relations rel1 and rel2 from the list
        relationsFromConditions.remove(indexSep+1);
        relationsFromConditions.set(indexSep, temp);

        // delete sep from separators
        sepIterator.remove();
    }
}
