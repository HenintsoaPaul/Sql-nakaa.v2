package commands.executors.insert;

import commands.IExecutor;
import composants.relations.Relation;
import debug.StringArrayDebug;
import exe.Interpreter;
import tools.Funct;
import tools.loaders.RelationLoader;
import tools.savers.RelationSaver;
import tools.verifier.RelationVerifier;

import java.io.IOException;
import java.util.Vector;

public class Insert implements IExecutor {
    /**
     * INSERT ( etu, nom ) INTO étudiants VALUES ( 11_int, Sharon_String )
     * -> APIDIRO ( etu, nom ) AGNATY étudiants VALUES ( 11, Sharon_String )
     */
    public Insert() {}

    @Override
    public void execute(String[] commands, Interpreter interpreter)
            throws Exception {

        Relation relation = insert(commands, interpreter);
        new RelationSaver().save(relation, interpreter); // update existing relation
    }

    Relation insert(String[] commands, Interpreter interpreter)
            throws Exception {

        String dbPath = interpreter.getDbPath();
        String nomRelation = Funct.getNomRelation(commands, "AGNATY");

        new RelationVerifier(dbPath).verifyExisting(nomRelation);

        Relation rel = RelationLoader.loadRelation(nomRelation, dbPath);

        String[] columns = InsertColumnsHandler.getColumnsName( commands );
        int[] columnsIndexes = InsertColumnsHandler.getColumnsIndexes( rel, columns );

        String[] vals = InsertValuesHandler.getStringValues(commands);
        Vector<?> values  = InsertValuesHandler.getValuesInsert( rel, columns, vals );

        // insert in all columns
        if ( rel.getAttributs().length == columns.length ) {
            rel.addLigne( values );
        }
        // in specific columns
        else {
            rel.addLigne( columnsIndexes, values );
        }

        String LOG = "Yesss, tafiditra ao anaty Relation " + nomRelation + " ireo data rht!";
        System.out.println(LOG);
        return rel;
    }
}