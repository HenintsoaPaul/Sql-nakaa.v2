package commands.executors.drop.dropFactory;

import commands.executors.drop.Drop;
import commands.executors.drop.DropRelation;

public class DropRelationBuilder implements IDropBuilder {


    @Override
    public Drop build() {
        return new DropRelation();
    }
}
