package fr.openent.moisson.domain.search.enumeration;

import fr.openent.moisson.domain.search.ElasticSearchConstants;

public enum NiveauESEnum {
    /*
     * CONSTANTES pour NiveauESEnum
     */
    ID("id", ElasticSearchConstants.TEXT),
    LIBELLE("libelle", ElasticSearchConstants.MULTI),
    TERME("terme", ElasticSearchConstants.KEYWORD),
    CONCEPT("concept", ElasticSearchConstants.KEYWORD);

    /**
     * Nom du champ
     */
    String fieldName;

    /**
     * Type de champs : text, keyword, date, long, boolean, multi-field....
     */
    String fieldType;

    NiveauESEnum(String fieldName, String fieldType) {
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
