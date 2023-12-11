package tools.parsers;

public class StringParser implements IValueParser {

    @Override
    public Object parse(String value) {
        return value;
    }
}
