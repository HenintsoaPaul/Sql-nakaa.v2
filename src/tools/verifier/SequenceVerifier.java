package tools.verifier;

import tools.IVerifier;

@SuppressWarnings("unused")
public class SequenceVerifier implements IVerifier {

    @Override
    public boolean isExisting(String relationalModelName) {
        return false;
    }

    @Override
    public void verifyExisting(String relationalModelName) {

    }
}
