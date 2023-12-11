package commands.executors.select.conditions.conditionsVerifier;

import commands.executors.select.conditions.INumericalConditionVerifier;

public class InfEqualVerifier implements INumericalConditionVerifier {
    public InfEqualVerifier() {}

    @SuppressWarnings({"rawtypes", "unchecked"})
    @Override
    public boolean isTrue(Object value, Object valueVerifier) {

        if (value instanceof Comparable && valueVerifier instanceof Comparable) {

            return ((Comparable) value).compareTo(valueVerifier) <= 0;
        } else {

            throw new IllegalArgumentException("Les objets doivent être " +
                    "des instances de classes qui implémentent l'interface Comparable");
        }
    }
}
