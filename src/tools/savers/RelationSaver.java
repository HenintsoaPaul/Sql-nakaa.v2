package tools.savers;

import composants.RelationalModel;
import composants.relations.Relation;
import exe.Interpreter;
import tools.ISaver;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class RelationSaver implements ISaver {

     public RelationSaver() {}



     void saveRelation(Relation rel, Interpreter inter)
            throws IOException {

        String nomRelation = rel.getNomRelation();
        String fileName = inter.getDbPath()+ "/relations/" + nomRelation;
        try {
            FileOutputStream fileOut = new FileOutputStream( fileName+".ser" );
            ObjectOutputStream out = new ObjectOutputStream( fileOut );
            out.writeObject( rel );

            fileOut.close();
            out.close();
        }
        catch( Exception e ) {
            System.out.println( "Error on saving Relation "+nomRelation );
            throw e;
        }
        System.out.println("Relation "+ nomRelation+" has been saved to "+
                fileName +"\n---");
    }



    @Override
    public void save(RelationalModel relation, Interpreter interpreter)
            throws IOException {

        saveRelation((Relation) relation, interpreter);
    }
}
