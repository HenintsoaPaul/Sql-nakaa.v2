package composants;

import commands.executors.select.where.SelectWhere;
import composants.relations.Relation;

import java.util.List;
import java.util.Vector;

public class Predicat {
    // of pattern:  colA OPERATOR colB
    List<String> characters;

    public List<String> getCharacters() {
        return characters;
    }

    public void setCharacters(List<String> characters) {
        if (characters == null) {
            throw new IllegalArgumentException("ERROR: characters cannot be null!");
        }
        this.characters = characters;
    }

    public Predicat(List<String> characters) {
        this.characters = characters;
    }

    public void sout() {
        for (String str : characters) {
            System.out.println(str);
        }
        System.out.println("---");
    }
    public String[] toSimpleCondition() {
        String[] tab = new String[3];
        for (int i = 0; i < 3; i++) {
            tab[i] = characters.get(i);
        }
        return tab;
    }

    @SuppressWarnings("rawtypes")
    public Vector<Vector> getMatchingRows(Relation xJoinedRelation)
            throws Exception {
        // verifier que colA et colB appartiennent à la relation xJoined
        xJoinedRelation.getIndexAttrib(characters.get(0));
        xJoinedRelation.getIndexAttrib(characters.get(2));

        return SelectWhere.getLignesWhere(xJoinedRelation, this);
    }
}
