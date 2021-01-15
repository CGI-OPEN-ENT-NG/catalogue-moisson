package fr.openent.moisson.domain.search.enumeration;

import fr.openent.moisson.domain.search.ElasticSearchConstants;

public enum LicenceESEnum {
    /*
     * CONSTANTES pour NiveauESEnum
     */
    ID("id", ElasticSearchConstants.TEXT),
    VALEUR("valeur", ElasticSearchConstants.TEXT);

    /**
     * Nom du champs
     */
    String fieldName;

    /**
     * Type de champs : text, keyword, date, long, boolean, multi-field....
     */
    String fieldType;

    LicenceESEnum(String fieldName, String fieldType) {
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
