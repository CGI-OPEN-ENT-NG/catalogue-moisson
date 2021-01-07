package fr.openent.moisson.domain.enumeration;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * The Technologie enumeration.
 */
public enum Technologie {
    WINDOWS("Windows"),
    MAC_OS("MacOS"),
    IOS_TABLETTE("iOS tablette"),
    ANDROID_TABLETTE("Android tablette"),
    SMARTPHONE("Smartphone"),
    CHROME_OS("ChromeOS");

    @JsonValue
    private String value;

    Technologie(String value) {
        this.value=value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
