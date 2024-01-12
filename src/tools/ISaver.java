package tools;

import composants.RelationalModel;
import exe.Interpreter;

import java.io.IOException;

public interface ISaver {

     @SuppressWarnings("unused")
     void save(RelationalModel relationalModel, Interpreter interpreter)
            throws IOException;
}
