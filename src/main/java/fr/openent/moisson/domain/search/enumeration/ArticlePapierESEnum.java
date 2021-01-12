package fr.openent.moisson.domain.search.enumeration;

import fr.openent.moisson.domain.search.ElasticSearchConstants;

public enum ArticlePapierESEnum {

    /*
     * CONSTANTES pour TitrePublieES
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
    PRIXHT("pxht", ElasticSearchConstants.SCALED_FLOAT),
    TVA("tvas", ElasticSearchConstants.NESTED),
    DISCIPLINE("disciplines", ElasticSearchConstants.NESTED),
    NIVEAU("niveaus", ElasticSearchConstants.NESTED),
    DISPONIBILITE("disponibilite", ElasticSearchConstants.NESTED),
    PRIXTTC("prixttc", ElasticSearchConstants.SCALED_FLOAT);


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
