package tools;

import composants.RelationalModel;
import exe.Interpreter;

import java.io.IOException;

public interface ISaver {

     void save(RelationalModel relationalModel, Interpreter interpreter)
            throws IOException;
}
