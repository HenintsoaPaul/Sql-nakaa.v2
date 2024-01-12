package tools.parsers;

import java.util.HashMap;

public abstract class ValueParserBuilder {

    static final HashMap<String, IValueParser> parsers = new HashMap<>();

    static {
        parsers.put("String", new StringParser());
        parsers.put("int", new IntegerParser());
        parsers.put("double", new DoubleParser());
        parsers.put("boolean", new BooleanParser());
        parsers.put("date", new LocalDateParser());
        parsers.put("dateHeure", new LocalDateTimeParser());
    }

    public static IValueParser build(String valueType) {

        return parsers.get(valueType);
    }
}
