package commands.executors.insert.parsers;

public class DoubleParser implements IValueParser {


    @Override
    public Object parse(String value) {

        return Double.parseDouble(value);
    }
}
