package composants;

import commands.executors.select.where.SelectWhere;
import composants.relations.Relation;

import java.util.List;
import java.util.Vector;

public class Predicat {
    // of pattern:  colA OPERATOR colB
    final List<String> characters;

    public List<String> getCharacters() {
        return characters;
    }

    public Predicat(List<String> characters) {
        this.characters = characters;
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
