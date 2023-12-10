package commands.executors.insert.parsers;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LocalDateTimeParser implements IValueParser {


    @Override
    public Object parse(String value) {

        String format = "dd-MM-yyyy HH:mm:ss";
        return LocalDateTime.parse( value, DateTimeFormatter.ofPattern(format));
    }
}
