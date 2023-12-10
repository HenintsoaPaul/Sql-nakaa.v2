package commands.executors.create.createFactory;

import commands.executors.create.Create;
import commands.executors.create.CreateBase;

public class CreateBaseBuilder implements ICreateBuilder {

    @Override
    public Create build() {

        return new CreateBase();
    }
}
