package fr.openent.moisson.domain.enumeration;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * The Technologie enumeration.
 */
public enum Technologie {

    LINUX("Linux"),
    WINDOWS("Windows"),
    MAC_OS("MacOS"),
    IOS_TABLETTE("iOS tablette"),
    TABLETTE_IOS("Tablette iOS"),
    ANDROID_TABLETTE("Android tablette"),
    TABLETTE_ANDROID("Tablette Android"),
    SMARTPHONE("Smartphone"),
    SMARTPHONE_ANDROID("Smartphone Android"),
    SMARTPHONE_IOS("Smartphone iOS"),
    CHROMEBOOK("Chromebook"),
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
