package commands.executors.select.where;

import commands.executors.select.conditions.INumericalConditionVerifier;
import commands.executors.select.conditions.conditionsVerifier.NumericalConditionVerifierBuilder;
import composants.Predicat;
import composants.relations.Relation;
import tools.parsers.INumericParser;
import tools.parsers.NumericParserBuilder;

import java.util.Arrays;
import java.util.List;
import java.util.Vector;

@SuppressWarnings("rawtypes")
public abstract class SelectWhere {

    public static Relation getRelationAFromCondition(Relation relation, String[] condition)
            throws Exception {
        Relation rel = relation.clone();
        Vector<Vector> rows = getLignesWhere(relation, condition);
        rel.setLignes(rows);
        return rel;
    }
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

    /**
     * Used when processing tetaJoin on two relations.
     * @param rel the xJoined relation from the two relations.
     * @param predicat colA [operator] colB
     */
    public static Vector<Vector> getLignesWhere (Relation rel, Predicat predicat)
            throws Exception {

        List<String> ktr = predicat.getCharacters();
        String columnName1 = ktr.get(0),
                columnName2 = ktr.get(2);

        int indexColumn1 = rel.getIndexAttrib( columnName1 ),
                indexColumn2 = rel.getIndexAttrib( columnName2 );

        Class<?> classColumn1 = rel.getClassOfAttrib( columnName1 ),
                classColumn2 = rel.getClassOfAttrib( columnName2 );

        Vector<Vector> result = new Vector<>();
        for( Vector ligneOrg: rel.getLignes() )
            if ( isVerifyingRow( ligneOrg, ktr.get(1), indexColumn1, classColumn1, indexColumn2, classColumn2 ) ) {
                result.add( ligneOrg );
            }

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

        return conditionVerifier.isTrue(valueFromRelation, valueVerifier);
    }
    @SuppressWarnings("RedundantArrayCreation")
    static boolean isVerifyingRow(Vector ligne, String operator,
                                  int indexAttrib1, Class clazz1,
                                  int indexAttrib2, Class clazz2)
            throws Exception {

        String[] columnTypes = { clazz1.getSimpleName(), clazz2.getSimpleName() };
        List<String> numericAttributes = Arrays.asList(new String[]{"Integer", "Double"});
        for (String columnType : columnTypes) {
            if (!numericAttributes.contains(columnType))
                throw new Exception("Tsy attribut numeric io type \"" + columnType
                        + "\" io nama! int na double gne mety.");
        }

        Object value1 = ligne.get(indexAttrib1),
                value2 = ligne.get(indexAttrib2);

        INumericalConditionVerifier conditionVerifier =
                NumericalConditionVerifierBuilder.build(operator);

        return conditionVerifier.isTrue(value1, value2);
    }
}
