package commands.executors.select.conditions.conditionsVerifier;

import commands.executors.select.conditions.INumericalConditionVerifier;

public class EqualVerifier implements INumericalConditionVerifier {

    public EqualVerifier() {}

    @Override
    public boolean isTrue(Object value, Object valueVerifier) {

        return valueVerifier.equals(value);
    }
}
