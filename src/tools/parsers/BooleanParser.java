package tools.parsers;

public class BooleanParser implements IValueParser {


    @Override
    public Object parse(String value) {

        return Boolean.parseBoolean(value);
    }
}
