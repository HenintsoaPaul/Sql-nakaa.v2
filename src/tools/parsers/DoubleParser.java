package tools.parsers;

public class DoubleParser implements IValueParser, INumericParser {


    @Override
    public Object parse(String value) {

        return Double.parseDouble(value);
    }

    @Override
    public Object numericParse(String value) {

        return parse(value);
    }
}
