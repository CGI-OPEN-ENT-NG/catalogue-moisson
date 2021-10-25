package fr.openent.moisson.domain.search.enumeration;

import fr.openent.moisson.domain.search.ElasticSearchConstants;

public enum ConditionESEnum {
    /*
     * CONSTANTES pour NiveauESEnum
     */
    ID("id", ElasticSearchConstants.TEXT),
    GRATUITE("gratuite", ElasticSearchConstants.INTEGER),
    CONDITIONGRATUITE("conditionGratuite", ElasticSearchConstants.INTEGER);

    /**
     * Nom du champ
     */
    String fieldName;

    /**
     * Type de champs : text, keyword, date, long, boolean, multi-field....
     */
    String fieldType;

    ConditionESEnum(String fieldName, String fieldType) {
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
