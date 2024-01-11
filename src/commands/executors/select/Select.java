package commands.executors.select;

import commands.IExecutor;
import commands.executors.select.join.JoinHandlerFactory;
import commands.executors.select.where.SelectLines;
import composants.relations.Relation;
import exe.Affichage;
import exe.Interpreter;
import tools.Funct;
import tools.loaders.RelationLoader;
import tools.verifier.RelationVerifier;

import java.util.Arrays;
import java.util.List;

public class Select implements IExecutor {

    public Select() {}

    @Override
    public void execute(String[] commands, Interpreter interpreter)
            throws Exception {

        Relation relation = select(commands, interpreter);
        Affichage.afficherDonnees(relation);
    }

    public static Relation select(String[] commands, Interpreter inter)
            throws Exception {

        String dbPath = inter.getDbPath();
        String nomRelation = Funct.getNomRelation( commands, "AME" );

        String[] columnsName = SelectColumns.getColumnsName(commands);

        new RelationVerifier(dbPath).verifyExisting( nomRelation );

        Relation rel = RelationLoader.loadRelation( nomRelation, dbPath );

        List<String> splitQuery = Arrays.asList(commands);

        // RELATIONS <- JOIN
        List<String> joinIndicators = Arrays.asList(new String[]{"x", "X", ",", "teta["});
        for (int i = 0; i < joinIndicators.size(); i++) {
            if (splitQuery.contains(joinIndicators.get(i))) {
                rel = JoinHandlerFactory
                        .build(splitQuery, rel)
                        .join(splitQuery, dbPath, rel);
//                System.out.printf("misy join");
                break;
            }
        }

//        Affichage.afficherDonnees(rel);

        // LIGNES <- WHERE
        rel = SelectLines
                .selectWhere(commands, rel);

        // COLONNES
        rel = Projection
                .project(columnsName, rel);

        return rel;
    }
}