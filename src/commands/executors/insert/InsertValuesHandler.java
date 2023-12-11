package commands.executors.insert;

import tools.parsers.IValueParser;
import tools.parsers.ValueParserBuilder;
import composants.relations.Relation;

import tools.valueProcessors.CommaRemover;

import java.util.Arrays;
import java.util.Vector;

public abstract class InsertValuesHandler {

    /**
     * It returns all the values to be inserted in the command, as Object cast
     * according the type of the column who will receive it.
     * @param relation The relation which will receive the data.
     * @param columnsName The names of columns of the relation that will receive data.
     * @param values The values to be inserted in the relation.
     */
    static Vector<Object> getValuesInsert(Relation relation, String[] columnsName, String[] values)
            throws Exception {
        // values ? INSERT ... VALUES ->> ( val1, val2, ... ) <<-

        Vector<Object> valuesInsert = new Vector<>();

        for ( int i = 0; i < columnsName.length; i++ ) {

            String attributType = relation.getTypeAttribut(columnsName[i]);
            try {

                IValueParser valueParser = ValueParserBuilder.build(attributType);

                Object value = valueParser.parse(values[i]);

                valuesInsert.add( value );
            }
            catch (NullPointerException e) {

                throw new Exception("Tsy afaka atao lasa \""+attributType+"\" io valeur \""+
                        values[i]+"\" io satria tsy mbola hain'e program e magnano anazy!");
            }
        }

        return valuesInsert;
    }


    /**
     * It returns all the values to be inserted in a relation, as String.
     * @param commands The split array of String constituting the input query.
     */
    public static String[] getStringValues(String[] commands) {

        int index = Arrays.asList(commands).lastIndexOf("(");
        String[] result = Arrays.copyOfRange(commands, index + 1, Arrays.asList(commands).lastIndexOf(")"));

        CommaRemover rmv = new CommaRemover();
        for (int i = 0; i < result.length; i++) {
            result[i] = rmv.process(result[i]);
        }

        return result;
    }
}
