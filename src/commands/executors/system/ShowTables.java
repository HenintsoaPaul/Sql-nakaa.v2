package commands.executors.system;

import commands.IExecutor;
import composants.relations.Attribut;
import composants.relations.Relation;
import exe.Affichage;
import exe.Interpreter;

import java.io.File;
import java.util.Objects;
import java.util.Vector;

public class ShowTables implements IExecutor {
    @Override
    public void execute(String[] commands, Interpreter interpreter)
            throws Exception {

        Relation relation = showTables(interpreter);
        Affichage.afficherDonnees(relation);
    }

    private Relation showTables(Interpreter interpreter)
            throws Exception {
        String dbName = interpreter.getDbName(),
                nomRelation = "All Relations in DataBase '"+ dbName+ "'";

        Attribut[] attrib = new Attribut[1];
        attrib[0] = new Attribut("nomRelation", "String");

        Relation rel = new Relation( nomRelation, attrib );
        rel.setLignes( getAllRelationsName( dbName ) );

        return rel;
    }

    @SuppressWarnings("rawtypes")
    Vector<Vector> getAllRelationsName(String dbName) {
        File folderDb = new File("data/"+ dbName+ "/relations");
        Vector<Vector> result = new Vector<>();
        for ( File content: Objects.requireNonNull(folderDb.listFiles())) {
            Vector<String> v = new Vector<>();
            v.add( content.getName().split("\\.")[0] );
            result.add(v);
        }
        return result;
    }
}
