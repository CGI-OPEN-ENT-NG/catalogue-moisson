package fr.openent.moisson.domain.enumeration;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * The PublicCible enumeration.
 */
public enum PublicCible {
    ELEVE("Elève"),
    ENSEIGNANT("Enseignant"),
    ELEVE_ET_ENSEIGNANT("Elève, Enseignant");

    @JsonValue
    private String value;

    PublicCible(String value) {
        this.value=value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
