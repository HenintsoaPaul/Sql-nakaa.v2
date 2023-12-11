package commands.executors.select.conditions.conditionsVerifier;

import commands.executors.select.conditions.INumericalConditionVerifier;

public class DifferenceVerifier implements INumericalConditionVerifier {

    public DifferenceVerifier() {}

    @Override
    public boolean isTrue(Object value, Object valueVerifier) {

        return !valueVerifier.equals(value);
    }
}
