package tools.loaders;

import composants.relations.Relation;
import exe.Interpreter;
import tools.ILoader;
import tools.verifier.BaseVerifier;
import tools.verifier.RelationVerifier;

import java.io.FileInputStream;
import java.io.ObjectInputStream;

public class RelationLoader implements ILoader {

    public static Relation getRelation(String nomRelation, Interpreter interpreter)
            throws Exception {

        new BaseVerifier().verifyExisting(interpreter.getDbName());
        new RelationVerifier(interpreter.getDbPath()).verifyExisting(nomRelation);

        Relation rel = null;
        String fileName = interpreter.getDbPath() + "/relations/" + nomRelation + ".ser";
        try {
            FileInputStream fileIn = new FileInputStream( fileName );
            ObjectInputStream in = new ObjectInputStream( fileIn );
            rel = (Relation) in.readObject();
            in.close();
            fileIn.close();
        } catch( Exception e ) {
            e.printStackTrace();
        }
        return rel;
    }
}
