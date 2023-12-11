package composants.relations;

public abstract class RelationException {

    public static String getUnfoundedAttribExceptionMessage(String nomRelation , String nomAttribut) {
        return "Relation Error: "+
                "L'attribut \""+ nomAttribut+ "\" est INTROUVABLE "+
                "dans la Relation \""+ nomRelation + "\"!\n" +
                "Vous pouvez essayez la commande DESC \""+ nomRelation +"\"" ;
    }
}
