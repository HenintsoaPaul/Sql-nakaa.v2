package tools.valueProcessors;

import tools.IValueProcessor;

public class CommaRemover implements IValueProcessor {

    @Override
    public static String process(String value) {

        return value.replace(",", "");
    }
}
