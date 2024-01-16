package commands.executors.select;

import commands.executors.select.conditions.ConditionVerifier;
import commands.executors.select.where.SelectLines;
import commands.executors.select.where.SelectWhere;
import composants.relations.Relation;

import java.util.Arrays;
import java.util.List;

public class ArgWhere {
    final List<String> str;

    public String[] getKtrs() {
        // we do not take the first and the last parentheses
        int len = str.size() - 2;
        String[] tab = new String[len];
        for (int i = 0; i < len; i++) {
            tab[i] = str.get(i + 1);
        }
        return tab;
    }

    public ArgWhere(List<String> str) {
        this.str = str;
    }

    public Relation process(Relation relation)
            throws Exception {
        Relation result = null;
        if (str.size() == 3) {
            String[] cond = new String[3];
            for (int i = 0; i < 3; i++) cond[i] = str.get(i);
            ConditionVerifier.verifyPossibleCondition(cond, relation);
            result = SelectWhere.getRelationAFromCondition(relation, cond);
        }
        else if (str.get(0).equals("(")) {
            String[] ktr = getKtrs();
            List<String> wherePart = Arrays.asList(ktr);
            result = SelectLines.getRelationFromWherePart(wherePart, relation);
        }
        return result;
    }
}
