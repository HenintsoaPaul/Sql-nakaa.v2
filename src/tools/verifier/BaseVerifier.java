package tools.verifier;

import tools.IVerifier;

import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;

public class BaseVerifier implements IVerifier {

    String pathToData = "data/";
    public BaseVerifier() {}
    public BaseVerifier(String pathToData) {
        this.pathToData = pathToData;
    }


    @Override
    public boolean isExisting(String nomBase) {

        String file_name = pathToData + nomBase;
        Path path = Paths.get(file_name);
        return Files.exists(path, LinkOption.NOFOLLOW_LINKS);
    }

    @Override
    public void verifyExisting(String nomBase)
            throws Exception {

        if ( !isExisting( nomBase ) )
            throw new Exception("Azafady nama fa tena tsy mi-existe io Base "
                    +nomBase+" io!");
    }
}
