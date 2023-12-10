package commands.executors.drop.dropFactory;

import commands.executors.drop.Drop;
import commands.executors.drop.DropBase;
import commands.executors.drop.DropRelation;

public class DropBaseBuilder implements IDropBuilder {

    @Override
    public Drop build() {
        return new DropBase();
    }
}
