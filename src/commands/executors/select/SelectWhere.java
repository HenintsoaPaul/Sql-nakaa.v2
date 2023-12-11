package commands.executors.select;

import commands.executors.select.conditions.INumericalConditionVerifier;
import commands.executors.select.conditions.conditionsVerifier.NumericalConditionVerifierBuilder;
import composants.relations.Relation;
import tools.parsers.INumericParser;
import tools.parsers.NumericParserBuilder;

import java.util.Vector;

@SuppressWarnings("rawtypes")
public abstract class SelectWhere {

    /**
     * It returns the lines respecting the specified condition.
     */
    static Vector<Vector> getLignesWhere (Relation rel, String[] condition, Vector<Vector> dataOrg )
            throws Exception {

        Vector<Vector> result = new Vector<>();

        String columnName = condition[0];
        int indexColumn = rel.getIndexAttrib( columnName );
        Class<?> classColumn = rel.getClassOfAttrib( columnName );

        for( Vector ligneOrg: dataOrg )
            if ( isVerifyingRow( ligneOrg, condition, indexColumn, classColumn ) )
                result.add( ligneOrg );

        return result;
    }

    static boolean isVerifyingRow(Vector ligne, String[] condition, int indexAttrib, Class clazz )
            throws Exception {

        String columnType = clazz.getSimpleName();

        INumericParser numericParser = NumericParserBuilder.build(columnType);
        if (numericParser == null)
            throw new Exception("Tsy attribut numeric io type \""+columnType
                    +"\" io nama! int na double gne mety.");

        Object valueVerifier = numericParser.numericParse(condition[2]);
        Object valueFromRelation = clazz.cast( ligne.get(indexAttrib) );

        String operator = condition[1];
        INumericalConditionVerifier conditionVerifier =
                NumericalConditionVerifierBuilder.build(operator);

        boolean is = conditionVerifier.isTrue(valueFromRelation, valueVerifier);
//        System.out.println("is: "+ is+"\n---");

        return is;
    }
}
