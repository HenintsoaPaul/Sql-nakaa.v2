package commands.executors.select;

import commands.executors.select.conditions.ConditionGetter;
import composants.relations.Relation;
import tools.Funct;

import java.util.Arrays;
import java.util.Vector;

import static commands.executors.select.SelectWhere.getLignesWhere;
import static commands.executors.select.conditions.ConditionGetter.getConditionSeparator;
import static commands.executors.select.conditions.ConditionProcessor.inverseAllTsyConditions;
import static commands.executors.select.conditions.ConditionVerifier.verifyAllConditions;

@SuppressWarnings({"rawTypes", "rawtypes"})
public abstract class SelectLines {

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



    static void selectWhere(String[] commande, Relation rel)
            throws Exception {

        int index = Arrays.asList(commande).indexOf("REFA");
        if (index == -1)
            index = Arrays.asList(commande).indexOf("refa");

        if ( index != -1 ) { // whether there is "REFA" is the query

            String[][] conditions = ConditionGetter.getConditions(commande, index);

            inverseAllTsyConditions(conditions);

            verifyAllConditions(conditions, rel);

            Vector<Vector> lignes = rel.getLignes();

            lignes = getLignesWhere(rel, conditions[0], lignes);

//            -------

            if (conditions.length > 1) {

                String[] operators = getConditionSeparator(commande);
                for (int i = 1; i < conditions.length; i++) {

                    int m = i - 1;
                    switch (operators[m]) {
                        case "ARY":
                            lignes = getLignesWhere(rel, conditions[i], lignes);
                            break;

                        case "NA":
                            Vector<Vector> nvResults =
                                    getLignesWhere(rel, conditions[i], rel.getLignes());
                            lignes = Funct.unionSansRepetition(lignes, nvResults);
                            break;

                        default:
                            throw new Exception("Unknown logic operator!");
                    }
                }
            }
            rel.setLignes(lignes);
        }
    }
}
