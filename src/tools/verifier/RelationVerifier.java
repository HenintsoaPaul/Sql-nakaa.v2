package tools.verifier;

import tools.IVerifier;
import tools.Funct;

import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;

public class RelationVerifier implements IVerifier {

    private static final String[] allowedTypes =
            { "int", "double", "String", "boolean", "date", "dateHeure" };

    String pathToDb; // data/first_db
    public RelationVerifier(String pathToDb) {
        this.pathToDb = pathToDb;
    }


    @Override
    public boolean isExisting(String nomRelation) {

        String file_name = this.pathToDb + "/relations/" + nomRelation + ".ser";
        Path path = Paths.get(file_name);
        return Files.exists(path, LinkOption.NOFOLLOW_LINKS);
    }

    @Override
    public void verifyExisting(String nomRelation)
            throws Exception {

        if ( !isExisting( nomRelation ) )
            throw new Exception("Azafady nama fa tena tsy mi-existe io Relation "
                    +nomRelation+" io!");
    }



    public static void isAllowedAttributType(String attrib) throws Exception {

        if ( !Funct.isInTabString( allowedTypes, attrib ) )
            throw new Exception( "Le type "+attrib+" n'existe pas!"+
                    " Les types existantes sont: "+
                    String.join( ", ", allowedTypes) );
    }
}
