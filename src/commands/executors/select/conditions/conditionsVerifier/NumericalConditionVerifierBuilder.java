package commands.executors.select.conditions.conditionsVerifier;

import commands.executors.select.conditions.INumericalConditionVerifier;

import java.util.HashMap;

public abstract class NumericalConditionVerifierBuilder {

    static HashMap<String, INumericalConditionVerifier> verifiers = new HashMap<>();

    static {
        verifiers.put("==", new EqualVerifier());
        verifiers.put(">", new SuperiorVerifier());
        verifiers.put("<", new InferiorVerifier());
        verifiers.put(">=", new SupEqualVerifier());
        verifiers.put("<=", new InfEqualVerifier());
        verifiers.put("<>", new DifferenceVerifier());
    }

    public static INumericalConditionVerifier build(String numericOperator) {

        return verifiers.get(numericOperator);
    }
}
