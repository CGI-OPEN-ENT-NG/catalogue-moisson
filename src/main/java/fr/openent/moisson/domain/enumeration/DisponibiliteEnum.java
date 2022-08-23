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
    NON_JUSQU_A_EPUISEMENT_DES_STOCKS("Disponible jusqu'à épuisement des stocks"),
    MANQUE_SANS_DATE("Manque sans date"),
    NON_DISPONIBLE_A_LONG_TERME("Non disponible à long terme"),
    EPUISE("Epuisé"),
    A_PARAITRE("À paraître"),
    PRECOMMANDE("Précommande"),
    TRUE("true"),
    FALSE("false");

    @JsonValue
    private String value;

    DisponibiliteEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
