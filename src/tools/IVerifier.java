package tools;

public interface IVerifier {

    boolean isExisting(String relationalModelName);

    void verifyExisting(String relationalModelName) throws Exception;
}
