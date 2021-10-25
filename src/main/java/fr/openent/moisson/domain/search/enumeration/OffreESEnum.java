package fr.openent.moisson.domain.search.enumeration;

import fr.openent.moisson.domain.search.ElasticSearchConstants;

public enum OffreESEnum {

    /*
     * CONSTANTES pour ArticlePapierESEnum
     */
    ID("id", ElasticSearchConstants.TEXT),
    EAN_LIBRAIRE("eanlibraire", ElasticSearchConstants.KEYWORD),
    QTE_MINI("quantiteminimaleachat", ElasticSearchConstants.INTEGER),
    PRESCRIPTEUR("prescripteur", ElasticSearchConstants.BOOLEAN),
    LIBELLE("libelle", ElasticSearchConstants.TEXT),
    PRIXHT("prixht", ElasticSearchConstants.SCALED_FLOAT),
    ADOPTANT("adoptant", ElasticSearchConstants.BOOLEAN),
    DUREE("duree", ElasticSearchConstants.TEXT),
    REF_EDITEUR("referenceediteur", ElasticSearchConstants.KEYWORD),
    TYPE("type", ElasticSearchConstants.TEXT),
    IS3ANS("is3ans", ElasticSearchConstants.BOOLEAN),

    // Others Entities ES
    TVA("tvas", ElasticSearchConstants.NESTED),
    LEP("leps", ElasticSearchConstants.NESTED),
    LICENCE("licence", ElasticSearchConstants.NESTED);

    /**
     * Nom du champ
     */
    String fieldName;

    /**
     * Type de champs : text, keyword, date, long, boolean, multi-field....
     */
    String fieldType;

    OffreESEnum(String fieldName, String fieldType) {
        this.fieldName = fieldName;
        this.fieldType = fieldType;
    }

    public String getFieldName() {
        return this.fieldName;
    }

    public String getFieldType() {
        return this.fieldType;
    }

}
