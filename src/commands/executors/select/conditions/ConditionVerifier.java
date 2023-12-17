package commands.executors.select.conditions;

import composants.relations.Relation;
import tools.Funct;

public abstract class ConditionVerifier {

    public static void verifyAllConditions(String[][] conditions, Relation rel) throws Exception {
        for ( String[] cond : conditions )
            verifyPossibleCondition(cond, rel);
    }

    static void verifyPossibleCondition( String[] condition, Relation rel ) throws Exception {
        String nomAttrib = condition[0];
        String valueVerif = condition[2];

        if ( !rel.isMyAttrib( nomAttrib ) )
            throw new Exception("L'attribut "+nomAttrib
                    +" n'existe pas dans la Relation "+rel.getNomRelation()+"!");

        // verification des conditions numeriques: == ou < ou > ou >= ou <= ou <>
        String[] operationPossibles = { "==", "<", ">", ">=", "<=", "<>" };
        if (Funct.isInTabString(operationPossibles, condition[1]))
            throw new Exception("L'operation "+condition[1]+" est inconnue!"+
                    "Les operations possibles sont: "+String.join(", ", operationPossibles));
        else if ( !rel.isNumericAttrib( nomAttrib) )
            throw new Exception("L'operation "+condition[1]+" est impossible a realiser "+
                    "sur l'attribut "+ nomAttrib+ " de la Relation '"+ rel.getNomRelation()+ "'!");
        else {
            try {
                Double.parseDouble( valueVerif );
            }
            catch ( NumberFormatException nfe ) {
                try {
                    Integer.parseInt( valueVerif );
                }
                catch ( NumberFormatException nfee ) {
                    throw new Exception( "ERROR: Tokony ho nombre ny condition ampesaina "+
                            "rehefa mampiasa operateur numerique: column (cond_numerique) number" );
                }
            }
        }
    }
}
