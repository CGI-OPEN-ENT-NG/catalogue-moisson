package fr.openent.moisson.domain.search.enumeration;

import fr.openent.moisson.domain.search.ElasticSearchConstants;

public enum DisponibiliteESENum {

    /*
     * CONSTANTES pour DisponibiliteESENum
     */
    ID("id", ElasticSearchConstants.TEXT),
    COMMENTAIRE("commentaire",ElasticSearchConstants.TEXT),
    DATE_DISPONIBILITE("datedisponibilite", ElasticSearchConstants.DATE),
    COMMANDABLE("commandable", ElasticSearchConstants.BOOLEAN),
    VALEUR("valeur", ElasticSearchConstants.KEYWORD);

    /**
     * Nom du champs
     */
    String fieldName;

    /**
     * Type de champs : text, keyword, date, long, boolean, multi-field....
     */
    String fieldType;

    DisponibiliteESENum(String fieldName, String fieldType) {
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

