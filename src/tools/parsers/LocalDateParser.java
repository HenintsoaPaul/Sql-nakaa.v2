package tools.parsers;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LocalDateParser implements IValueParser {


    @Override
    public Object parse(String value) {

        String format = "dd-MM-yyyy";
        return LocalDateTime.parse( value, DateTimeFormatter.ofPattern(format));
    }
}
