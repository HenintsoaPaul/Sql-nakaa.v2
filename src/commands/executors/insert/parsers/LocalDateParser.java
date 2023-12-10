package commands.executors.insert.parsers;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class LocalDateParser implements IValueParser {


    @Override
    public Object parse(String value) {

        String format = "dd-MM-yyyy";
        return LocalDateTime.parse( value, DateTimeFormatter.ofPattern(format));
    }
}
