package composants.relations;

import java.util.Vector;

@SuppressWarnings({"rawtypes", "unchecked"})
public abstract class RelationOperator {

    /**
     * FUNCTIONS WITH OTHER RELATIONS
     */
    // Exception verifications
    static void verifySameNumberOfAttribs(Relation rel1, Relation rel2)
            throws Exception {

        int atbLen1  = rel1.getAttributs().length,
                atbLen2= rel2.getAttributs().length;
        if ( atbLen1 != atbLen2 )
            throw new Exception("Relation Error: The two Relations must have the same "+
                    "number of attribs: 1st=> "+ atbLen1+ " | 2nd=> "+ atbLen2);
    }

    // All functions will use those
    static Relation initRelation(Relation rel1, Relation rel2, String operation)
            throws Exception {

        if ( rel2 == null || rel1 == null )
            throw new Exception("Relation ERROR: Impossible de realiser l'operation "
                    +operation+" CAR une des 2 relations ou les 2 est/sont NULL!");
        /*
         *   Qu'importe l'operation, la Relation result aura tjrs comme attributs les
         *   attributs de la Relation "rel1".
         */
        verifySameNumberOfAttribs( rel1, rel2);

        String nom = rel1.getNomRelation()+ "_"+
                operation.toUpperCase()+ "_"+ rel2.getNomRelation();

        return new Relation( nom, rel1.getAttributs() );
    }
    public static boolean isLignesIdentiques(Vector un, Vector deux ) {
        for ( int i = 0; i < un.size(); i++ )
            if ( un.get(i).toString()
                    .compareTo( deux.get(i).toString() ) != 0 )
                return false;
        return true;
    }



    //  1*) UNION
    public static Relation union( Relation rel1, Relation rel2 ) throws Exception {
        Relation result = initRelation( rel1, rel2, "union" );

        // ajout des toutes les lignes de deux relations
        Vector<Vector> li = new Vector( rel1.getLignes() );
        li.addAll(rel2.getLignes());
        result.setLignes( li );

        return result;
    }



    //  2*) INTERSECTION
    public static Relation intersection( Relation rel1, Relation rel2 ) throws Exception {
        Relation result = initRelation( rel1, rel2, "intersection" );

        Vector<Vector> lignes = getLignesCorrespondantes( rel1.getLignes(), rel2.getLignes() );
        result.setLignes(lignes);
        return result;
    }
    static Vector<Vector> getLignesCorrespondantes(Vector<Vector> l1, Vector<Vector> l2) {
        Vector<Vector> result = new Vector<>();
        for ( Vector ligneL1: l1 )
            for ( Vector ligneL2: l2 ) {
                if ( isLignesIdentiques( ligneL1, ligneL2 ) ) result.add( ligneL1 );
            }
        return result;
    }



    //  3*) DIFFERENCE
    @SuppressWarnings("unused")
    public static Relation difference(Relation rel1, Relation rel2 ) throws Exception {
        Relation result = initRelation( rel1, rel2, "difference" );

        result.setLignes( getLignesDifferentes( rel1.getLignes(), rel2.getLignes() ) );
        return result;
    }
    static Vector<Vector> getLignesDifferentes(Vector<Vector> lignes1, Vector<Vector> lignes2) {
        Vector<Vector> result = new Vector();
        for ( Vector l1 : lignes1 )
            if ( !ligneDans( l1, lignes2 ) ) result.add( l1 );
        return result;
    }
    static boolean ligneDans(Vector ligneVerif, Vector<Vector> listeLignes) {
        for ( Vector li : listeLignes )
            if ( isLignesIdentiques( li, ligneVerif ) ) return true;
        return false;
    }
}
