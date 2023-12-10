package commands.executors.create;

import tools.verifier.RelationVerifier;
import composants.relations.Relation;
import exe.Interpreter;
import tools.Funct;
import tools.savers.RelationSaver;

public class CreateRelation extends Create {

    public CreateRelation() {}



    public void create(String[] commands, Interpreter interpreter)
            throws Exception {

        Relation rel = createRelation(commands, interpreter);
        if ( rel != null )
            new RelationSaver().save( rel, interpreter );
    }



    Relation createRelation(String[] commands, Interpreter inter)
            throws Exception {

        String nomRelation = commands[2];

        isRelationAlreadyExisting(nomRelation, inter);
        verifyAttribsType(commands);

        String[][] attribs = getAttribsRelation(commands);
        Relation rel = null;
        String LOG = "Yesss, ";

        try {
            rel = new Relation( nomRelation, attribs );
            LOG += "Relation "+ nomRelation+ " voaforona successfully bro!";
        } catch ( Exception e ) {
            LOG = "Create Table Error: MAMOROOGNA TABLE "+ nomRelation+ "\n"+ e.getMessage();
        }

        System.out.println( LOG );
        return rel;
    }

     String[][] getAttribsRelation( String[] commands ) {

        String[][] attribs = new String[ (commands.length-5)/2 ][2];
        for ( int i = 4, k = 0; i < commands.length-2; i++, k++ ) {
            attribs[k][0] = commands[i];
            i ++;
            attribs[k][1] = Funct.takeOffComma(commands[i]);
        }
        return attribs;
    }



    // Verifications
    static void isRelationAlreadyExisting( String nomRelation, Interpreter inter )
            throws Exception {

        boolean alreadyExisting = new RelationVerifier(inter.getDbPath())
                .isExisting(nomRelation);
        if (alreadyExisting)
            throw new Exception("Efa mi-existe io Relation "+nomRelation+" io nama!!");
    }

    static void verifyAttribsType( String[] commands )
            throws Exception {

        String[] attribsType = getAttribsType(commands);
        for ( String type: attribsType )
            RelationVerifier.isAllowedAttributType(type);
    }

    static String[] getAttribsType( String[] commands ) {

        String[] types = new String[ (commands.length-5)/2 ];
        for( int i = 5, k = 0; i < commands.length-1; i++, k++ ) {
            types[k] = Funct.takeOffComma( commands[i] );
            i ++;
        }
        return types;
    }
}
