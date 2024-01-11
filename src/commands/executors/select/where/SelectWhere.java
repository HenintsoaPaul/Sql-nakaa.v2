package commands.executors.select.where;

import commands.executors.select.conditions.INumericalConditionVerifier;
import commands.executors.select.conditions.conditionsVerifier.NumericalConditionVerifierBuilder;
import composants.relations.Relation;
import tools.parsers.INumericParser;
import tools.parsers.NumericParserBuilder;

import java.util.Vector;

@SuppressWarnings("rawtypes")
public abstract class SelectWhere {

    /**
     * It returns the lines respecting the specified condition
     * from all the original lines of the relation 'rel' given
     * as parameter.
     */
    public static Vector<Vector> getLignesWhere (Relation rel, String[] condition)
            throws Exception {

        String columnName = condition[0];
        int indexColumn = rel.getIndexAttrib( columnName );
        Class<?> classColumn = rel.getClassOfAttrib( columnName );

        Vector<Vector> result = new Vector<>();
        for( Vector ligneOrg: rel.getLignes() )
            if ( isVerifyingRow( ligneOrg, condition, indexColumn, classColumn ) )
                result.add( ligneOrg );

        return result;
    }

    static boolean isVerifyingRow(Vector ligne, String[] condition,
                                  int indexAttrib, Class clazz )
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

        //        System.out.println("is: "+ is+"\n---");
        return conditionVerifier.isTrue(valueFromRelation, valueVerifier);
    }
}
