package commands.executors.insert.parsers;

public class IntegerParser implements IValueParser {


    @Override
    public Object parse(String value) {

        return Integer.parseInt(value);
    }
}
