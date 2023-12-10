package commands.executors.drop.dropFactory;

import commands.executors.drop.Drop;
import commands.executors.drop.DropBase;

public class DropBaseBuilder implements IDropBuilder {

    @Override
    public Drop build() {
        return new DropBase();
    }
}
