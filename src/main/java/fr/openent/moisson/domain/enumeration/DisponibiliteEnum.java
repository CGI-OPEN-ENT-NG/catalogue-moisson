package fr.openent.moisson.domain.enumeration;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * The DisponibiliteEnum enumeration.
 */
public enum DisponibiliteEnum {
    DISPONIBLE("Disponible"),
    NON_DISPONIBLE("Non disponible"),
    EN_COURS_D_IMPRESSION("En cours d'impression"),
    EN_COURS_DE_REIMPRESSION("En cours de réimpression"),
    NON_DISPONIBLE_PROVISOIREMENT("Non disponible provisoirement"),
    EPUISE("Epuisé"),
    A_PARAITRE("À paraître");

    @JsonValue
    private String value;

    private DisponibiliteEnum(String value) {
        this.value=value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }}
