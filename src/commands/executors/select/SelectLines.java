package commands.executors.select;

import commands.executors.select.conditions.ConditionGetter;
import composants.relations.Relation;
import exe.Affichage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;

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


    /**
     * Drop rows of rel who don't respect the conditions.
     */
    static void selectWhere(String[] commande, Relation rel)
            throws Exception {

        String where = "refa";
        List<String> cmd = Arrays.asList(commande);
        int indexWhere = cmd.contains(where) ?
                cmd.indexOf(where) :
                cmd.indexOf(where.toUpperCase());

        if ( indexWhere != -1 ) {

            List<String> refa = cmd.subList(indexWhere+1, cmd.size());
            List<String[]> conditions = ConditionGetter.getConditions(refa);
            String[] separatorsS = ConditionGetter.getConditionSeparator(commande);
            List<String> separators = new ArrayList<>(List.of(separatorsS));

            Relation relation =
                    ConditionGetter.getRelation(conditions, separators, rel);
            Affichage.afficherDonnees(relation);

//            inverseAllTsyConditions(conditions);
//            verifyAllConditions(conditions, rel);
//            Vector<Vector> lignes = rel.getLignes();
//            lignes = getLignesWhere(rel, conditions[0]);
//            rel.setLignes(lignes);
        }
    }
}
