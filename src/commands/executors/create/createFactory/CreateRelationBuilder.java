package commands.executors.create.createFactory;

import commands.executors.create.Create;
import commands.executors.create.CreateRelation;

public class CreateRelationBuilder implements ICreateBuilder {

    @Override
    public Create build() {

        return new CreateRelation();
    }
}
