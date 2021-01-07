package fr.openent.moisson.domain.enumeration;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * The TypeLicence enumeration.
 */
public enum TypeLicence {
    ELEVE("Licence élève"),
    ENSEIGNANT("Enseignant"),
    ETABLISSEMENT("Etablissement");

    @JsonValue
    private String value;

    TypeLicence(String value) {
        this.value=value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
