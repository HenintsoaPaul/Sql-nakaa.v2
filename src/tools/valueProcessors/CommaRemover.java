package tools.valueProcessors;

import tools.IValueProcessor;

public class CommaRemover implements IValueProcessor {

    public CommaRemover() {}

    @Override
    public String process(String value) {
        return value.replace(",", "");
    }
}
