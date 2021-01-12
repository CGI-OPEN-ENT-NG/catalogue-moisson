package fr.openent.moisson.domain.search;
public class ElasticSearchConstants {

    // Types de champs
    /**
     * "Field" de type boolean
     */
    public static final String BOOLEAN = "boolean";

    /**
     * "Field" de type date
     */
    public static final String DATE = "date";

    /**
     * "Field" de type boolean
     */
    public static final String FIELDS = "fields";

    /**
     * "Field" de type keyword
     */
    public static final String KEYWORD = "keyword";

    /**
     * "Field" de type integer
     */
    public static final String INTEGER = "integer";

    /**
     * "Field" de type long
     */
    public static final String LONG = "long";

    /**
     * "Field" de type integer
     */
    public static final String FLOAT = "float";

    /**
     * "Field" de type long
     */
    public static final String DOUBLE = "double";

    /**
     * "Field" de type text
     */
    public static final String TEXT = "text";

    /**
     * "Field" de type multiple : multi-field
     */
    public static final String MULTI = "multi";

    // Constantes diverse
    /**
     * pour ajouter .keyword
     */
    public static final String DOT_KEYWORD = ".keyword";

    /**
     * Constante pour "type"
     */
    public static final String TYPE = "type";

    /**
     * Constante pour "nested"
     */
    public static final String NESTED = "nested";

    /**
     * Constante pour "Scaled_Float"
     */
    public static final String SCALED_FLOAT = "Scaled_Float";



    // Tri et ordre
    /**
     * Champ de tri par défaut
     */
    public static final String DEFAULT_SORT_FIELD = "_score";

    /**
     * Ordre par défaut
     */
    public static final boolean DEFAULT_SORT_ORDER = false;

    protected ElasticSearchConstants() {
    }
}
