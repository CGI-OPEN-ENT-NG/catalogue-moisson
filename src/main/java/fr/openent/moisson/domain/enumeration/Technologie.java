package fr.openent.moisson.domain.enumeration;

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

    private String value;

    private Technologie(String value) {
        this.value=value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
