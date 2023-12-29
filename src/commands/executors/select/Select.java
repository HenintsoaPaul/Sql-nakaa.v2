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
import java.util.Vector;

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
        rel = JoinHandlerFactory
                .build(splitQuery)
                .handle(splitQuery, dbPath, rel);

        Affichage.afficherDonnees(rel);
        Vector line1 = rel.getLignes().get(0);
        for (Object data: line1)
            System.out.println(data);

        // LIGNES <- WHERE
        rel = SelectLines
                .selectWhere(commands, rel);

        // COLONNES
        return Projection
                .project(columnsName, rel);
    }
}