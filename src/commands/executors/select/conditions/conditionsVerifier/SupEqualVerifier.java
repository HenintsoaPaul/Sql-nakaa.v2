package commands.executors.select.conditions.conditionsVerifier;

import commands.executors.select.conditions.INumericalConditionVerifier;

public class SupEqualVerifier implements INumericalConditionVerifier {

    public SupEqualVerifier() {}

    @Override
    public boolean verify(double value1, double value2) {
        return value1 >= value2;
    }
}
