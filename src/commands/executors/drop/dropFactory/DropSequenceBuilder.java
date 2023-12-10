package commands.executors.drop.dropFactory;

import commands.executors.drop.Drop;
import commands.executors.drop.DropSequence;

public class DropSequenceBuilder implements IDropBuilder {

    @Override
    public Drop build() {

        return new DropSequence();
    }
}
