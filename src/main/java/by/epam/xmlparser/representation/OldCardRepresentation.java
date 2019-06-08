/**
 * Created by Alexander Lomat on 06.06.19
 * Version 0.0.1
 */

package by.epam.xmlparser.representation;

public enum OldCardRepresentation {

    OLDCARDS("oldcards"),
    NUMBER("number"),
    OLDCARD("oldcard"),
    THEME("theme"),
    TYPE("type"),
    COUNTRY("country"),
    WASSENT("wasSent"),
    YEAR("year"),
    VALUABLE("valuable"),
    ISALIVE("isAlive"),
    NAME("name"),
    AUTHORCOUNTRY("authorCountry"),
    AUTHOR("author");

    private String tagName;
    public static final String OLDCARD_REPRESENTATION_REGEX_PATTERN = "(?<=\\p{Lower})(?=\\p{Upper})";

    OldCardRepresentation(String tagName) {
        this.tagName = tagName;
    }

    public String getTagName() {
        return tagName;
    }
}
