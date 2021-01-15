package fr.openent.moisson.domain.search.enumeration;

import fr.openent.moisson.domain.search.ElasticSearchConstants;

public enum TvaESEnum {

    /*
     * CONSTANTES pour TvaESEnum
     */
    ID("id", ElasticSearchConstants.TEXT),
    TAUX("taux", ElasticSearchConstants.SCALED_FLOAT),
    POURCENT("pourcent", ElasticSearchConstants.SCALED_FLOAT);
    /**
     * Nom du champs
     */
    String fieldName;

    /**
     * Type de champs : text, keyword, date, long, boolean, multi-field....
     */
    String fieldType;

    TvaESEnum(String fieldName, String fieldType) {
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
