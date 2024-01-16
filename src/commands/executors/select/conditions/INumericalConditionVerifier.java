package commands.executors.select.conditions;

public interface INumericalConditionVerifier {

    default boolean isTrue(Object value1, Object value2) {
        if (value1 instanceof Number && value2 instanceof Number) {
            double val1 = ((Number) value1).doubleValue();
            double val2 = ((Number) value2).doubleValue();
            return verify(val1, val2);
        } else {
            throw new IllegalArgumentException("Les objets doivent être " +
                    "des instances de classes qui implémentent l'interface Number");
        }
    }
    boolean verify(double value1, double value2);
}
