package commands.executors.select;

import composants.relations.Relation;
import exe.Interpreter;
import tools.loaders.RelationLoader;
import tools.verifier.RelationVerifier;

import java.util.List;

public class ArgJoin {  // nomRelation || selectQuery
    final List<String> str;
    final RelationVerifier verifier;
    final RelationLoader loader;

    public String[] getKtrs() {
        // we do not take the first and the last parentheses
        int len = str.size() - 2;
        String[] tab = new String[len];
        for (int i = 0; i < len; i++) {
            tab[i] = str.get(i + 1);
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
        else if (str.get(1).equalsIgnoreCase("aboay")) {
            String[] commands = getKtrs();
            rel = new Select2().select(commands, interpreter);
            Limit.handleLimit(rel, commands);
        }
        return rel;
    }
}
