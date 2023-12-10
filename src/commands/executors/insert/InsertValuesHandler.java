package commands.executors.insert;

import commands.executors.insert.parsers.IValueParser;
import commands.executors.insert.parsers.ValueParserBuilder;
import composants.relations.Relation;

import tools.valueProcessors.CommaRemover;

import java.util.Arrays;
import java.util.Vector;

public abstract class InsertValuesHandler {

    static Vector<Object> getValuesInsert(Relation relation, String[] columnsName, String[] values)
            throws Exception {
        // values ? INSERT ... VALUES ->> ( val1, val2, ... ) <<-

        Vector<Object> valuesInsert = new Vector<>();

        for ( int i = 0; i < columnsName.length; i++ ) {

            String attributType = relation.getTypeAttribut(columnsName[i]);

            IValueParser valueParser = ValueParserBuilder.build(attributType);

            Object value = valueParser.parse(values[i]);

            valuesInsert.add( value );
        }

        return valuesInsert;
    }



    public static String[] getStringValues(String[] commands) {

        int index = Arrays.asList(commands).lastIndexOf("(");
        String[] result = Arrays.copyOfRange(commands, index + 1, Arrays.asList(commands).lastIndexOf(")"));

        for (int i = 0; i < result.length; i++) {
            result[i] = CommaRemover.process(result[i]);
        }

        return result;
    }
}
