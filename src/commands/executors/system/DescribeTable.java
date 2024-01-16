package commands.executors.system;

import commands.IExecutor;
import composants.relations.Attribut;
import composants.relations.Relation;
import exe.Affichage;
import exe.Interpreter;
import tools.loaders.RelationLoader;

import java.util.Vector;

@SuppressWarnings("rawtypes")
public class DescribeTable implements IExecutor {
    @Override
    public void execute(String[] commands, Interpreter interpreter)
            throws Exception {

        Relation relation = describeTable(commands, interpreter);
        Affichage.afficherDonnees(relation);
    }

    private Relation describeTable(String[] commands, Interpreter interpreter)
            throws Exception {
        String nomRelation = commands[1];

        String nom = "DESC "+ nomRelation;
        Attribut[] attribs = new Attribut[2];
        attribs[0] = new Attribut("nomAttribut", "String");
        attribs[1] = new Attribut("typeAttribut", "String");

        Relation rel = new Relation( nom, attribs );

        RelationLoader loader = new RelationLoader(interpreter.getDbPath());
        Vector<Vector> donnees = getDonnees(loader.loadRelation(nomRelation));

        rel.setLignes(donnees);
        return rel;
    }

    Vector<Vector> getDonnees(Relation rel) {
        Vector<Vector> result = new Vector<>();
        for ( Attribut atb: rel.getAttributs() ) {
            Vector v = new Vector();
            v.add( atb.getNomAttribut() );
            v.add( atb.getTypeAttribut() );
            result.add( v );
        }
        return result;
    }
}
