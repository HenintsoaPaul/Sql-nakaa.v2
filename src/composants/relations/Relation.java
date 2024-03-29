package composants.relations;

import composants.RelationalModel;
import tools.Funct;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.Vector;

@SuppressWarnings("ALL")
public class Relation implements Serializable, RelationalModel {

    private static final long serialVersionUID = 11l;
    String nomRelation;
    Attribut[] attributs;
    Vector<Vector> lignes = new Vector<>();

    public Relation clone() {
        Attribut[] attributs = this.getAttributs();
        Vector<Vector> rows = this.getLignes();
        Relation rel = null;
        try {
            rel = new Relation(attributs);
            rel.setLignes(rows);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return rel;
    }

    // Constructors
    public Relation(Attribut[] attributs) throws Exception {
        setAttributs(attributs);
    }
    public Relation(Attribut[] atb1, Attribut[] atb2) throws Exception {
        Attribut[] attribs = getAttrib(atb1, atb2);
        setAttributs( attribs );
    }
    public Relation(String nomRelation, Attribut[] attributs) throws Exception {
        setNomRelation(nomRelation);
        setAttributs(attributs);
    }
    public Relation(String nomRelation, String[][] attributs) throws Exception {
        setNomRelation(nomRelation);

        // attributs [ ['etu' , 'int'], ['nom' , 'String'], [ ... ] ... ]
        Attribut[] atribs = new Attribut[ attributs.length ];
        for ( int i = 0; i < atribs.length; i++ )
            atribs[i] = new Attribut( attributs[i][0], attributs[i][1] );
        setAttributs(atribs);
    }



    // Getters
    public String getTypeAttribut(String nomAttribut)
            throws Exception {
        return this.getAttribut( nomAttribut ).getTypeAttribut();
    }
    public String getNomRelation() {
        return this.nomRelation;
    }
    public String[] getNomAttributs() {

        Attribut[] attributs = this.getAttributs();
        int len = attributs.length;
        String[] noms = new String[len];

        for (int i = 0; i < len; i ++)
            noms[i] = attributs[i].getNomAttribut();

        return noms;
    }
    public Vector<Vector> getLignes() {
        return this.lignes;
    }
    public Attribut getAttrib(int indexAttrib)
            throws Exception {
        if ( indexAttrib >= this.getAttributs().length )
            throw new Exception("Relation error: Index Out Bound bro!");

        return this.getAttributs()[indexAttrib];
    }
    public Attribut getAttribut(String nomAttribut)
            throws Exception {
        for (Attribut atb: this.getAttributs())
            if (atb.getNomAttribut().equals(nomAttribut))
                return atb;

        throw new Exception(
                RelationException.getUnfoundedAttribExceptionMessage(
                        this.getNomRelation(),
                        nomAttribut
                ));
    }
    public Attribut[] getAttributs() {
        return this.attributs;
    }
    public Attribut[] getAttrib(Attribut[] atb1, Attribut[] atb2) {
        int len1 = atb1.length, len2 = atb2.length;
        Attribut[] attribs = new Attribut[len1 + len2];

        System.arraycopy(atb1, 0, attribs, 0, len1);
        System.arraycopy(atb2, 0, attribs, len1, len2);

        return attribs;
    }
    public Attribut[] getAttributs(String[] nomAttributs)
            throws Exception {

        if (nomAttributs[0].equalsIgnoreCase("aby")) {
            return this.getAttributs();
        }

        int len = nomAttributs.length;
        Attribut[] attributs = new Attribut[len];

        for( int i = 0; i < len; i++ ) {
            attributs[i] = new Attribut( nomAttributs[i],
                    this.getTypeAttribut( nomAttributs[i] ) );
        }
        return attributs;
    }
    public int getIndexAttrib(String nomAttribut)
            throws Exception {
        for ( int i = 0; i < this.getAttributs().length; i++ )
            if ( getAttrib(i).getNomAttribut().equals(nomAttribut) ) return i;

        throw new Exception(
                RelationException.getUnfoundedAttribExceptionMessage(
                        this.getNomRelation(),
                        nomAttribut
                ));
    }
    /**
     * @param nomDesAttribs - Array of String nomAttrib
     * @return Array containing the index of each nomAttrib
     */
    public int[] getIndexAttribs(String[] nomDesAttribs)
            throws Exception {

        int len = nomDesAttribs.length;
        if (nomDesAttribs[0].equalsIgnoreCase("aby") && len == 1) {

            nomDesAttribs = this.getNomAttributs();
            len = nomDesAttribs.length;
        }

        int[] result = new int[len];
        for (int i = 0; i < len; i++)
            result[i] = getIndexAttrib(nomDesAttribs[i]);

        return result;
    }
    public int[] getIndexAttribs(Attribut[] attributs)
            throws Exception {
        int[] tab = new int[attributs.length];
        for (int i = 0; i < tab.length; i++) {
            tab[i] = getIndexAttrib(attributs[i].getNomAttribut());
        }
        return tab;
    }
    public Class getClassOfAttrib(String nomAttribut) throws Exception {
        String type = this.getTypeAttribut(nomAttribut);
        return this.classAttrib(type);
    }
    Class classAttrib(String typeAttribut) throws ClassNotFoundException {
        String jLang = "java.lang.";
        String[] allowedTypes = { "String", "int", "double", "boolean" };
        String[] clazz = { "String", "Integer", "Double", "Boolean" };

        HashMap<String, Class> map = new HashMap<String, Class>();
        for ( int i = 0; i < clazz.length; i ++ )
            map.put(
                    allowedTypes[i],
                    Class.forName(jLang + clazz[i])
            );
        map.put( "date", LocalDate.class );
        map.put( "dateHeure", LocalDateTime.class );

        return map.get( typeAttribut );
    }
    Class getClassOfAttrib(Attribut attribut) throws ClassNotFoundException {
        String type = attribut.getTypeAttribut();
        return classAttrib( type );
    }
    public boolean isNumericAttrib(String nomAttrib) throws Exception {
        String type = this.getAttribut(nomAttrib).getTypeAttribut();
        return type.compareTo("int") == 0 || type.compareTo("double") == 0;
    }



    // Setters
    public void setNomRelation(String nomRelation) throws Exception {
        if ( nomRelation == null || nomRelation.isEmpty() )
            throw  new Exception("Relation Error: "+
                    "Une Relation doit toujours avoir un nom NON NULL!");
        this.nomRelation = nomRelation;
    }
    public void setAttributs(Attribut[] attributs) throws Exception {
        if ( attributs == null )
            throw new Exception("Relation Error: "+
                    "Les attributs d'une Relation doivent toujours etre NON NULL!");
        this.attributs = attributs;
    }
    public void setLignes(Vector<Vector> lignes) throws Exception {
        if ( lignes == null )
            throw new Exception("Relation Error: "+
                    "Ajoutez plusieurs lignes NULL a une Relation est un tres ridicule bro!");

        // Verification of each line of data
        for ( Vector line: lignes ) verifyLine(line);

        this.lignes = lignes;
    }



    // Ajouter des lignes -- INSERT
    public void addLigne(Vector uneLigne) throws Exception {
        /**
         * Pour realiser des inserts sur tout les colonnes d'une Relation.
         */
        if ( uneLigne == null )
            throw new Exception("Relation Error: "+
                    "Ajoutez une ligne NULL a une Relation est un peu ridicule bro!");
        verifyLine(uneLigne);
        this.getLignes().add( uneLigne );
    }
    public void addLigne(int[] indexColumns, Vector uneLigne) throws Exception {
        /**
         * Pour realiser des inserts seulement sur des colonnes dont les indices
         * ont ete specifiees dans indexColumns.
         */
//        verifyLine(uneLigne);
        Vector v = new Vector();
        for ( int i = 0, ind = 0; i < this.getAttributs().length; i ++ ) {

            if ( Funct.isInTabInt( indexColumns, i ) ) {
                v.add( uneLigne.get(ind) );
                ind ++;
            }
            else v.add(null);
        }
        this.addLigne( v );
    }



    // Exception verifiers
    public boolean isMyAttrib(String attrib) {
        for ( Attribut atb: this.getAttributs() )
            if( atb.getNomAttribut().compareTo(attrib) == 0 ) return true;
        return false;
    }
    public boolean isMyAttrib(Attribut attrib) {
        return isMyAttrib( attrib.getNomAttribut() );
    }
    void verifySameNumberOfAttribs(Vector line) throws Exception {
        int atbLen  = this.getAttributs().length;
        int lineLen = line.size();
        if ( atbLen != lineLen )
            throw new Exception("Relation Error: Lines("+ lineLen+ " columns) must have"+
                    " the same number of attribs AS the Relation("+ atbLen+ " columns).");
    }

    void verifyMatchingTypes(Vector line) throws Exception {
        Attribut[] atbs = this.getAttributs();
        for ( int i = 0; i < line.size(); i ++ ) {
            Class<?> clazzAttrib = getClassOfAttrib( atbs[i] );
            Object data = line.get(i);
            if ( data == null ) {}
            else {
                Class<?> clazzData = data.getClass();
                if ( !clazzData.isAssignableFrom( clazzAttrib ) ) {
                    String str = "class java.";
                    String atb = clazzAttrib.toString();
                    String dt = clazzData.toString();

                    /**
                    * double peut acceuillir des int
                    * -> on ne fait rien.
                     */
                    if ( atb.compareTo(str+ "lang.Double") == 0
                    && dt.compareTo(str+ "lang.Integer") == 0 ) {}

                    /**
                     * verification des dates
                     * -> en cas d'erreur de format, affiche le format attendu
                     */
                    else if ( atb.compareTo(str+ "time.LocalDate") == 0 ) {
                        String format = "dd-MM-yyyy";
                        try {
                            LocalDate date = LocalDate.parse(
                                    data.toString(),
                                    DateTimeFormatter.ofPattern(format)
                            );
                        } catch ( DateTimeParseException dtpe ) {
                            throw new Exception( "Relation Error: '"+ data +
                                    "' doit etre dans le format "+ format );
                        }
                    }
                    else if ( atb.compareTo(str+ "time.LocalDateTime") == 0 ) {
                        String format = "dd-MM-yyyy HH:mm:ss";
                        try {
                            LocalDateTime date = LocalDateTime.parse(
                                    data.toString(),
                                    DateTimeFormatter.ofPattern(format)
                            );
                        } catch ( DateTimeParseException dtpe ) {
                            throw new Exception( "Relation Error: '"+ data +
                                    "' doit etre dans le format "+ format );
                        }
                    }

                    else
                        throw new Exception( "Relation Error: Les types de donnees "+
                                "ne correspondent pas aux types des attributs."+
                                "\n Data: "+data+" ~ TypeData: "+dt+" != TypeAttribut: "+atb );
                }
            }
        }
    }

    void verifyLine(Vector line) throws Exception {
        verifySameNumberOfAttribs(line);
        verifyMatchingTypes(line);
    }
}
