package fr.openent.moisson.domain.enumeration;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * The TypeLicenceGAR enumeration.
 */
public enum TypeLicenceGAR {
    TRANSFERABLE("transferable"),
    NON_TRANFERABLE("non transferable");

    @JsonValue
    private String value;

    private TypeLicenceGAR(String value) {
        this.value=value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
