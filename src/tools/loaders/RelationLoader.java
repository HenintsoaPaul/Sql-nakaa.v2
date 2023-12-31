package tools.loaders;

import composants.RelationalModel;
import composants.relations.Relation;
import tools.ILoader;
import tools.verifier.BaseVerifier;
import tools.verifier.RelationVerifier;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class RelationLoader implements ILoader {

    public static Relation loadRelation(String nomRelation, String pathToDb)
            throws Exception {

        String[] pathStrings = pathToDb.split("/");
        String dbName = pathStrings[ pathStrings.length-1 ];

        new BaseVerifier().verifyExisting(dbName);
        new RelationVerifier(pathToDb).verifyExisting(nomRelation);

        Relation rel;
        String fileName = pathToDb + "/relations/" + nomRelation + ".ser";
        try {

            FileInputStream fileIn = new FileInputStream( fileName );
            ObjectInputStream in = new ObjectInputStream( fileIn );
            rel = (Relation) in.readObject();

            in.close();
            fileIn.close();
        } catch (IOException | ClassNotFoundException e) {

            throw new RuntimeException(e);
        }
        return rel;
    }

    @Override
    public RelationalModel load(String relationalModelName, String pathToStorage)
            throws Exception {

        return loadRelation(relationalModelName, pathToStorage);
    }
}
