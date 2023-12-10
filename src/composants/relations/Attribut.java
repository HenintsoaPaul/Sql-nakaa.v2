package composants.relations;

import java.io.Serializable;

public class Attribut implements Serializable {

    private static final long serialVersionUID = 10l;
    String nomAttribut;
    String typeAttribut;



    // Constructors
    public Attribut(String nom, String type) throws Exception {
        setNomAttribut(nom);
        setTypeAttribut(type);
    }



    // Getters
    public String getNomAttribut() {
        return this.nomAttribut;
    }
    public String getTypeAttribut() {
        return this.typeAttribut;
    }



    // Setters
    void setNomAttribut(String nom) throws Exception {
        if ( nom == null || nom.isEmpty() )
            throw  new Exception("L'attribut doit toujours avoir" +
                    " un nom( != null && != '' )");

        this.nomAttribut = nom;
    }
    void setTypeAttribut(String type) throws Exception {
        if ( type == null || type.isEmpty() )
            throw  new Exception("L'attribut doit toujours avoir un" +
                    " type( != null && != '' )");

        this.typeAttribut = type;
    }

}
