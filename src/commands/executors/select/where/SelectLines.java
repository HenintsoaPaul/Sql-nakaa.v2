package commands.executors.select.where;

import commands.executors.select.Select2;
import commands.executors.select.conditions.ConditionGetter;
import composants.relations.Relation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;

import static commands.executors.select.conditions.ConditionInverser.inverseAllTsyConditions;
import static commands.executors.select.conditions.ConditionVerifier.verifyAllConditions;

@SuppressWarnings({"rawTypes", "rawtypes"})
public abstract class SelectLines {
    static String whereKeyWord = "refa";

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
    public static Relation selectWhere(List<String> cmd, Relation rel)
            throws Exception {

        int indexWhere = Select2.getIndexZeroLevelParentheses(cmd, whereKeyWord);
        if ( indexWhere != -1 ) {
            List<String> refa = cmd.subList(indexWhere+1, cmd.size());
            List<String[]> conditions = ConditionGetter.getConditions(refa);

            inverseAllTsyConditions(conditions);
            verifyAllConditions(conditions, rel);

            String[] separatorsS = ConditionGetter.getConditionSeparator(cmd);
            List<String> separators = new ArrayList<>(List.of(separatorsS));

            rel = ConditionGetter.getRelation(conditions, separators, rel);
        }

        return rel;
    }
}
