package commands.executors.create.createFactory;

import commands.executors.create.Create;
import commands.executors.create.CreateSequence;

public class CreateSequenceBuilder implements ICreateBuilder {

    @Override
    public Create build() {

        return new CreateSequence();
    }
}
