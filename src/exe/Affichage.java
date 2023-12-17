package exe;

import composants.relations.Attribut;
import composants.relations.Relation;

import java.util.Vector;

public class Affichage {
    final static int MAX_COL_LONG = 20, MIN_COL_LONG = 1;
    @SuppressWarnings("unchecked")
    public static void afficherDonnees(Relation rel) throws Exception {
        Attribut[] attribs = rel.getAttributs();
        int lenAtb = attribs.length;

        int[] maxColsLong = new int[ lenAtb ];
        for (int i = 0; i < lenAtb; i++) {
            maxColsLong[i] = getMaxColLongueur( attribs[i].getNomAttribut(), rel );
        }
        System.out.println("Relation " + rel.getNomRelation());


        // header
        afficherSeparation(maxColsLong);
        System.out.print("|");
        for (int i = 0; i < lenAtb; i++)
            afficherCellule(attribs[i].getNomAttribut(), maxColsLong[i]);
        System.out.println();
        afficherSeparation(maxColsLong);


        // data
        for (Vector<Vector> ligne : rel.getLignes()) {
            System.out.print("|");
            for (int i = 0; i < lenAtb; i++)
                afficherCellule(ligne.get(i), maxColsLong[i]);
            System.out.println();
        }
        afficherSeparation(maxColsLong);
    }



    static int getMaxColLongueur(String colonne, Relation rel) throws Exception {
        int longueur = colonne.length(),
                idColonne = rel.getIndexAttrib(colonne);

        for (Vector ligne : rel.getLignes()) {
            int longueurLigne = ligne.get(idColonne) == null ?
                    4 : ligne.get(idColonne).toString().length();
            if (longueurLigne > longueur)
                longueur = longueurLigne;
        }

        return longueur;
    }



    static void afficherSeparation(int[] maxColsLong) {
        System.out.print("+");
        for (int i : maxColsLong) {

            int max = Math.min(i, MAX_COL_LONG);
            max = Math.max(max, MIN_COL_LONG);

            for (int j = 0; j < max + 2; j++)
                System.out.print("-");

            System.out.print("+");
        }

        System.out.println();
    }



    static void afficherCellule(Object cellule, int tailleMax) {
        int max = Math.min(tailleMax, MAX_COL_LONG);
        max = Math.max(max, MIN_COL_LONG);
        String celluleString;

        if (cellule == null) {
            celluleString = "null";
        } else if (cellule instanceof Boolean) {
            celluleString = (Boolean)cellule ? "vrai" : "faux";
        } else {
            celluleString = cellule.toString();
        }

        System.out.print(" ");

        if (celluleString.length() > max) {
            // azertyuiopqsdfghjklmnopqrstuvwxyz
            // 0123456789
            // max = 7
            // maxIndex = max - 3 = 2
            int maxIndex = max - 3;
            System.out.print(celluleString.substring(0, maxIndex) + "...");
        } else {
            System.out.print(celluleString);
        }

        for (int i = 0; i < max - celluleString.length(); i++) {
            System.out.print(" ");
        }
        System.out.print(" |");
    }
}
