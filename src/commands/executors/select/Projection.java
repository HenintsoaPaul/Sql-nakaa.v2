package commands.executors.select;

import commands.IExecutor;
import commands.executors.select.where.SelectLines;
import composants.relations.Attribut;
import composants.relations.Relation;
import exe.Affichage;
import exe.Interpreter;
import tools.Funct;
import tools.loaders.RelationLoader;
import tools.verifier.RelationVerifier;

import java.util.Vector;

@SuppressWarnings("ALL")
public class Projection implements IExecutor {
    /*
    PROJETEO ( etu, vola ) AME test
     */
    public Projection() {}

    @Override
    public void execute(String[] commands, Interpreter interpreter)
            throws Exception {

        Relation relation = project(commands, interpreter.getDbPath());
        Affichage.afficherDonnees(relation);
    }



    public static Relation project(String[] commands, String dbPath)
            throws Exception {

        String nomRelation = Funct.getNomRelation(commands, "AME");

        new RelationVerifier(dbPath).verifyExisting( nomRelation );

        Relation rel = new RelationLoader().loadRelation(nomRelation, dbPath);

        return SelectColumns.selectColumns(commands, rel);
    }

    public static Relation project(String[] columnsName, Relation relation)
            throws Exception {

        Attribut[] attribs;
        if (columnsName[0].equalsIgnoreCase("aby")) {
            attribs = relation.getAttributs();
        }
        else {
            attribs = relation.getAttributs(columnsName);
        }

        String relationName = "["+String.join( ", ", columnsName )+"] from "
                + relation.getNomRelation();
        Relation result = new Relation(relationName, attribs);

        Vector<Vector> donnees = SelectLines.getAllLines(relation, columnsName);
        result.setLignes( donnees );

        return result;
    }
}