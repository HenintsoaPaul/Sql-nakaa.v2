package tools.parsers;

import java.util.HashMap;

public abstract class NumericParserBuilder {

    static HashMap<String, INumericParser> parsers = new HashMap<>();

    static {
        parsers.put("Integer", new IntegerParser());
        parsers.put("Double", new DoubleParser());
    }

    public static INumericParser build(String numericType) {

        return parsers.get(numericType);
    }
}
