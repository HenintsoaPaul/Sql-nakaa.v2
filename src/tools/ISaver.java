package tools;

import composants.RelationnalModel;
import exe.Interpreter;

import java.io.IOException;

public interface ISaver {

     void save(RelationnalModel relationnalModel, Interpreter interpreter)
            throws IOException;
}
