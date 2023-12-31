package fr.openent.moisson.domain.search.enumeration;

import fr.openent.moisson.domain.search.ElasticSearchConstants;

public enum ArticlePapierESEnum {

    /*
     * CONSTANTES pour ArticlePapierESEnum
     */
    ID("id", ElasticSearchConstants.TEXT),
    EAN("ean", ElasticSearchConstants.KEYWORD),
    ARK("ark", ElasticSearchConstants.KEYWORD),
    EDITEUR("editeur", ElasticSearchConstants.KEYWORD),
    TITRE("titre", ElasticSearchConstants.TEXT),
    AUTEUR("auteur", ElasticSearchConstants.TEXT),
    DESCRIPTION("description", ElasticSearchConstants.TEXT),
    REF_EDITEUR("referenceediteur", ElasticSearchConstants.KEYWORD),
    COLLECTION("ressourceprimaire", ElasticSearchConstants.MULTI),
    DISTRIBUTEUR("distributeur", ElasticSearchConstants.MULTI),
    URL_COUVERTURE("urlcouverture", ElasticSearchConstants.KEYWORD),
    DATE_PARUTION("dateparution", ElasticSearchConstants.DATE),
    PRIXHT("prixht", ElasticSearchConstants.SCALED_FLOAT),
    TYPE("type", ElasticSearchConstants.TEXT),
    BOOKSELLER("bookseller", ElasticSearchConstants.KEYWORD),

    // Others Entities ES
    TVA("tvas", ElasticSearchConstants.NESTED),
    DISCIPLINE("disciplines", ElasticSearchConstants.NESTED),
    NIVEAU("niveaux", ElasticSearchConstants.NESTED),
    DISPONIBILITE("disponibilite", ElasticSearchConstants.NESTED);


    /**
     * Nom du champs
     */
    String fieldName;

    /**
     * Type de champs : text, keyword, date, long, boolean, multi-field....
     */
    String fieldType;

    ArticlePapierESEnum(String fieldName, String fieldType) {
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
