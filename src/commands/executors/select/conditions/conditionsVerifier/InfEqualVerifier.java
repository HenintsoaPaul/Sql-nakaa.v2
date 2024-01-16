package commands.executors.select.conditions.conditionsVerifier;

import commands.executors.select.conditions.INumericalConditionVerifier;

public class InfEqualVerifier implements INumericalConditionVerifier {
    public InfEqualVerifier() {}

    @Override
    public boolean verify(double value1, double value2) {
        return value1 <= value2;
    }
}
