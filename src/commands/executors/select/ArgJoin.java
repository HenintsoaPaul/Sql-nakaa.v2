package commands.executors.select;

import composants.relations.Relation;
import exe.Interpreter;
import tools.loaders.RelationLoader;
import tools.verifier.RelationVerifier;

import java.util.List;

public class ArgJoin {  // nomRelation || selectQuery
    List<String> str;
    RelationVerifier verifier;
    RelationLoader loader;

    public List<String> getCharacters() {
        return str;
    }
    public String[] getKtrs() {
        String[] tab = new String[str.size()];
        for (int i = 0; i < str.size(); i++) {
            tab[i] = str.get(i);
        }
        return tab;
    }

    public ArgJoin(List<String> str, String pathToDb) {
        this.str = str;
        verifier = new RelationVerifier(pathToDb);
        loader = new RelationLoader(pathToDb);
    }

    Relation process(Interpreter interpreter)
            throws Exception {

        Relation rel = null;
        if (str.size() == 1) {
            String relationName = str.get(0);
            verifier.verifyExisting(relationName);
            rel = loader.loadRelation(relationName);
        }
        else if (str.get(0).equalsIgnoreCase("aboay")) {
            String[] commands = getKtrs();
            rel = new Select2().select(commands, interpreter);
        }
        return rel;
    }
}
