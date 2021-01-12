package fr.openent.moisson.domain.search;

import java.util.Objects;

/**
 * Une classe pour stocker un nom de champ ES et sa valeur
 *
 * @author Jean-Marc LEMAITRE
 */
public class ESField {

    private String fieldname;
    private Object value;

    /**
     * Constructeur
     */
    public ESField(String aFieldname, Object aValue) {
        fieldname = aFieldname;
        value = aValue;
    }

    /**
     * Renvoie le nom du champ
     * @return
     */
    public String getFieldname() {
        return fieldname;
    }

    /**
     * Renvoie la valeur du champ
     * @return
     */
    public Object getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ESField esField = (ESField) o;
        return Objects.equals(fieldname, esField.fieldname) && Objects.equals(value, esField.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fieldname, value);
    }

    /**
     * For debugging purpose
     */
    public String toString() {
        return new StringBuilder("[").append(fieldname).append("|")
            .append(value).append("]").toString();
    }
}
