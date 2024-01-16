package commands.executors.divEuclidienne;

import commands.IExecutor;
import composants.relations.Relation;
import exe.Affichage;
import exe.Interpreter;
import tools.loaders.RelationLoader;
import tools.verifier.RelationVerifier;

import java.util.ArrayList;
import java.util.List;

public class DivEuclidienne implements IExecutor {
    @Override
    public void execute(String[] commands, Interpreter interpreter)
            throws Exception {
        String dbPath = interpreter.getDbPath();

        List<String> relationsName = new ArrayList<>();
        relationsName.add(commands[1]);
        relationsName.add(commands[3]);

        new RelationVerifier(dbPath).verifyRelationsExistance(relationsName);

        List<Relation> relations = RelationLoader.loadRelations(relationsName, dbPath);

        verificationNbrColonne(relations);

        Relation result = processDivek(relations);
        Affichage.afficherDonnees(result);
    }

    private void verificationNbrColonne(List<Relation> relations)
            throws Exception {
        Relation dividende = relations.get(0),
                diviseur = relations.get(1);
        if (diviseur.getAttributs().length > dividende.getAttributs().length) {
            throw new Exception("Azafady nama! Le nombre de colonne dans la relation dividende \\'" +
                    dividende.getNomRelation() + "\\' doit etre >= au nombre de colonne dans la relation diviseur \\'" +
                    diviseur.getNomRelation() + "!");
        }
    }

    private Relation processDivek(List<Relation> relations)
            throws Exception {
        DivekProcessor processor = new DivekProcessor();
        return processor.processDivek(relations);
    }
}
