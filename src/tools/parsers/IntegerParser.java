package tools.parsers;

public class IntegerParser implements IValueParser, INumericParser {


    @Override
    public Object parse(String value) {

        return Integer.parseInt(value);
    }

    @Override
    public Object numericParse(String value) {

        return parse(value);
    }
}
