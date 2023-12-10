package commands.executors.drop;

import exe.Interpreter;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class DropRelation extends Drop{

    public DropRelation() {}



    public void drop(String[] commands, Interpreter interpreter) {

        String nomRelation = commands[2];
        String file_name = interpreter.getDbPath()+ "/relations/" + nomRelation + ".ser";
        Path path = Paths.get(file_name);
        String LOG = "Azafady nama, mbola tsy mi-existe io Relation "+nomRelation+" io.";

        try {

            if (Files.deleteIfExists(path))
                LOG = "Yessss, voafafa lay Relation '"+ nomRelation +"'!";
        } catch (Exception e) {

            e.printStackTrace();
            LOG = "Erreur lors de FAFAY TABLE "+nomRelation;
        }
        System.out.println( LOG ) ;
    }
}
