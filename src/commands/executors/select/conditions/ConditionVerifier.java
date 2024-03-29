package commands.executors.select.conditions;

import composants.relations.Relation;

import java.util.Arrays;
import java.util.List;

public abstract class ConditionVerifier {

    public static void verifyPossibleCondition( String[] condition, Relation rel ) throws Exception {
        String nomAttrib = condition[0],
                operator = condition[1],
                valueToVerify = condition[2],
                relationName = rel.getNomRelation();

        if ( !rel.isMyAttrib( nomAttrib ) ) {
            throw new Exception("L'attribut " + nomAttrib
                    + " n'existe pas dans la Relation " + relationName + "!");
        }

        List<String> operationPossibles = Arrays.asList("==", "<", ">", ">=", "<=", "<>");
        if (!operationPossibles.contains(operator)) {
            throw new Exception("L'opération " + operator + " est inconnue!" +
                    "Les operations possibles sont: " + String.join(", ", operationPossibles));
        }

        if (!rel.isNumericAttrib(nomAttrib)) {
            if (!operator.equals("=="))
                throw new Exception("L'opération " + operator + " est impossible a realiser " +
                    "sur l'attribut " + nomAttrib + " de la Relation '" + relationName + "'!");
        }

        try {
            Double.parseDouble( valueToVerify );
        }
        catch ( NumberFormatException nfe ) {
            try {
                Integer.parseInt( valueToVerify );
            }
            catch ( NumberFormatException nfee ) {
                if (!operator.equals("=="))
                    throw new Exception( "ERROR: Tokony ho nombre ny condition ampesaina "+
                        "rehefa mampiasa operateur numerique: column (cond_numerique) number" );
            }
        }
    }
}
