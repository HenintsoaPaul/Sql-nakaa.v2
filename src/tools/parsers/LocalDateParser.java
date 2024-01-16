package tools.parsers;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class LocalDateParser implements IValueParser {
    @Override
    public Object parse(String value) {

        String format = "dd-MM-yyyy";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        return LocalDate.parse(value, formatter);
    }
}
