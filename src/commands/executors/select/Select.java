package commands.executors.select;

import commands.IExecutor;
import composants.relations.Relation;
import exe.Affichage;
import exe.Interpreter;
import tools.Funct;
import tools.loaders.RelationLoader;
import tools.verifier.RelationVerifier;

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

        // jointures
//        rel = Joint.joints(rel, commands, inter);

        // LIGNES <- WHERE
        SelectLines.selectWhere(commands, rel);


        // COLONNES
        return Projection.project(columnsName, rel);
    }
}