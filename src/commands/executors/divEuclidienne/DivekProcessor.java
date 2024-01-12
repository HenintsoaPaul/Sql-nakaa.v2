package commands.executors.divEuclidienne;

import composants.relations.Attribut;
import composants.relations.Relation;
import composants.relations.RelationOperator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;

@SuppressWarnings({"rawtypes", "unchecked"})
public class DivekProcessor {

    public DivekProcessor() {}

    public Relation processDivek(List<Relation> relations)
            throws Exception {
        Relation dividende = relations.get(0),
                diviseur = relations.get(1);

        Attribut[] attributsDiviseur = diviseur.getAttributs(),
                attributsDividende = dividende.getAttributs(),
                attributsQuotient = getAttributsQuotient(attributsDividende, attributsDiviseur);

        int[] indexesAttributsQuotient = dividende.getIndexAttribs(attributsQuotient),
                indexesAttributsDiviseurInRelationDividende = dividende.getIndexAttribs(attributsDiviseur);

        // parcourir toutes les lignes du diviseur
        List<Relation> relationsParLigne = new ArrayList<>();
        Vector<Vector> lignesDuDiviseur = diviseur.getLignes();
        for (Vector ligneDiviseur: lignesDuDiviseur) {

            // creer des relations a partir des ligneDiviseur matching dans les lignesDividende
            Vector<Vector> lignesDuQuotient = new Vector<>();
            Vector<Vector> lignesDuDividende = dividende.getLignes();
            for (Vector ligneDividende: lignesDuDividende) {

                // verification ligneDividende contenant ligneDiviseur
                if (isLigneContenant(ligneDividende, ligneDiviseur, indexesAttributsDiviseurInRelationDividende)) {

                    Vector ligneQuotient = getLigneQuotient(ligneDividende, indexesAttributsQuotient);
                    lignesDuQuotient.add(ligneQuotient);
                }
            }

            if (!lignesDuQuotient.isEmpty()) {
                Relation rel = new Relation(attributsQuotient);
                rel.setLignes(lignesDuQuotient);
                relationsParLigne.add(rel);
            }
        }

        Relation rel = relationsParLigne.get(0);
        // intersection de toutes les relations par ligne du diviseur
        for (int i = 1; i < relationsParLigne.size(); i++) {
            rel = RelationOperator.intersection(rel, relationsParLigne.get(i));
        }
        rel.setNomRelation(dividende.getNomRelation() + " / " + diviseur.getNomRelation());

        return rel;
    }

    private Vector getLigneQuotient(Vector ligneDividende, int[] indexesAttributsQuotient) {
        Vector ligneQuotient = new Vector<>();

        for (int index : indexesAttributsQuotient)
            ligneQuotient.add(ligneDividende.get(index));

        return ligneQuotient;
    }

    private boolean isLigneContenant(Vector ligneDividende, Vector ligneDiviseur, int[] indexesAtbDiviseurInRelationDividende) {
        for (int i = 0; i < indexesAtbDiviseurInRelationDividende.length; i++) {

            int index = indexesAtbDiviseurInRelationDividende[i];
            if (!ligneDividende.get(index).equals( ligneDiviseur.get(i) ))
                return false;
        }
        return true;
    }

    private Attribut[] getAttributsQuotient(Attribut[] attributsDividende, Attribut[] attributsDiviseur) {
        List<Attribut> atbDividende = Arrays.asList(attributsDividende),
                atbDiviseur = Arrays.asList(attributsDiviseur),
                atbQuotient = new ArrayList<>();

        List<String> nomAtbDiviseur = new ArrayList<>();
        for (Attribut atb : attributsDiviseur)
            nomAtbDiviseur.add(atb.getNomAttribut());

        for (Attribut atbDvd: atbDividende) {
            if (!nomAtbDiviseur.contains(atbDvd.getNomAttribut()))
                atbQuotient.add(atbDvd);
        }

        Attribut[] attributs = new Attribut[atbQuotient.size()];
        for (int i = 0; i < atbQuotient.size(); i++)
            attributs[i] = atbQuotient.get(i);

        return attributs;
    }

}
