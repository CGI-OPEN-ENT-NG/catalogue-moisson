package fr.openent.moisson.domain.search.enumeration;

import fr.openent.moisson.domain.search.ElasticSearchConstants;

public enum LepESEnum {

    /*
     * CONSTANTES pour ArticlePapierESEnum
     */
    ID("id", ElasticSearchConstants.TEXT),
    EAN("ean", ElasticSearchConstants.KEYWORD),
    TITRE("titre", ElasticSearchConstants.TEXT),
    DESCRIPTION("description", ElasticSearchConstants.TEXT),
    DUREE("duree", ElasticSearchConstants.KEYWORD),

    // Others Entities ES
    CONDITIONS("conditions", ElasticSearchConstants.NESTED),
    TYPELICENCE("licence", ElasticSearchConstants.NESTED);


    /**
     * Nom du champs
     */
    String fieldName;

    /**
     * Type de champs : text, keyword, date, long, boolean, multi-field....
     */
    String fieldType;

    LepESEnum(String fieldName, String fieldType) {
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
