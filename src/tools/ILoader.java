package tools;

import composants.RelationalModel;

public interface ILoader {

    @SuppressWarnings("unused")
    RelationalModel load(String relationalModelName, String pathToStorage) throws Exception;
}
