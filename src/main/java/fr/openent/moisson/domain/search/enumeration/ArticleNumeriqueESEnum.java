package fr.openent.moisson.domain.search.enumeration;

import fr.openent.moisson.domain.search.ElasticSearchConstants;

public enum ArticleNumeriqueESEnum {

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
    COLLECTION("ressourceprimaire", ElasticSearchConstants.MULTI),
    DISTRIBUTEUR("distributeur", ElasticSearchConstants.MULTI),
    URL_COUVERTURE("urlcouverture", ElasticSearchConstants.KEYWORD),
    URL_DEMO("urldemo", ElasticSearchConstants.KEYWORD),
    DATE_PARUTION("dateparution", ElasticSearchConstants.DATE),
    COMPATIBLE_GAR("compatiblegar", ElasticSearchConstants.BOOLEAN),
    ACCESSIBLE_ENT("acessibleent", ElasticSearchConstants.BOOLEAN),
    EAN_PAPIER("eanpapier", ElasticSearchConstants.KEYWORD),
    CLASSE("classes", ElasticSearchConstants.KEYWORD),
    PUBLIC_CIBLE("publiccible", ElasticSearchConstants.KEYWORD),

    // Others Entities ES
    DISCIPLINE("disciplines", ElasticSearchConstants.NESTED),
    NIVEAU("niveaux", ElasticSearchConstants.NESTED),
    OFFRES("offres", ElasticSearchConstants.NESTED),
    TECHNO("technos", ElasticSearchConstants.NESTED),
    DISPONIBILITE("disponibilite", ElasticSearchConstants.NESTED);

    /**
     * Nom du champ
     */
    String fieldName;

    /**
     * Type de champs : text, keyword, date, long, boolean, multi-field....
     */
    String fieldType;

    ArticleNumeriqueESEnum(String fieldName, String fieldType) {
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
