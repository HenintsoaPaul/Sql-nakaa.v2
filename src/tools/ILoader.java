package tools;

import composants.RelationalModel;

public interface ILoader {

    RelationalModel load(String relationalModelName, String pathToStorage) throws Exception;
}
