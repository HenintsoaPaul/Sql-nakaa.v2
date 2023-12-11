package commands.executors.select.conditions;

public interface INumericalConditionVerifier {

    boolean isTrue(Object value, Object valueVerifier);
}
