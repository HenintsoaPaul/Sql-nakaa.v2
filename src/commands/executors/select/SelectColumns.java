package commands.executors.select;

import commands.executors.select.where.SelectLines;
import composants.relations.Attribut;
import composants.relations.Relation;
import tools.valueProcessors.CommaRemover;

import java.util.Arrays;
import java.util.Vector;
import java.util.stream.Collectors;

@SuppressWarnings("ALL")
public abstract class SelectColumns {

    /**
     * Retourne une relation dont les attributs sont les colonnes
     * specifies dans la commande, a partir de la relation
     * donnee comme parametre.
     */
    static Relation selectColumns(String[] commande, Relation rel)
            throws Exception {

        if (commande[1].equalsIgnoreCase("aby"))
            return rel;

        String[] columnsName = getColumnsName(commande);

        Attribut[] attribs = rel.getAttributs(columnsName);

        Relation result = new Relation(String.join( " ", commande ), attribs );

        Vector<Vector> donnees = SelectLines.getAllLines(rel, columnsName);
        result.setLignes( donnees );

        return result;
    }



    static String[] getColumnsName(String[] commands) {

        Vector v = new Vector();

        int index = Arrays.stream(commands)
                .map(String::toLowerCase)
                .collect(Collectors.toList())
                .indexOf("ame");

        CommaRemover rmv = new CommaRemover();
        for ( int i = 1; i < index; i++ ) {

            String word = commands[i];
            if (!word.equals("(") && !word.equals(")"))
                v.add(rmv.process(word));
        }

        String[] results = new String[ v.size() ];
        for( int k = 0; k < v.size(); k++ )
            results[k] = (String) v.get(k);

        return results;
    }
}
