package tools;

@SuppressWarnings("unused")
public interface IVerifier {

    boolean isExisting(String relationalModelName);

    void verifyExisting(String relationalModelName) throws Exception;
}
