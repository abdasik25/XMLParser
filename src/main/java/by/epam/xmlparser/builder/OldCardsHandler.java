/**
 * Created by Alexander Lomat on 07.06.19
 * Version 0.0.1
 */

package by.epam.xmlparser.builder;

import by.epam.xmlparser.representation.OldCardRepresentation;
import by.epam.xmlparser.entity.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import java.util.EnumSet;
import java.util.HashSet;
import java.util.Set;

class OldCardsHandler extends DefaultHandler {

    private static final Logger LOGGER = LogManager.getLogger();
    private Set<OldCard> oldCards;
    private OldCard.Builder cardBuilder;
    private OldCardAuthor.Builder authorBuilder;
    private OldCardRepresentation currentOldCardRepresentation;
    private EnumSet<OldCardRepresentation> xmlElements;

    public OldCardsHandler() {
        oldCards = new HashSet<>();
        xmlElements = EnumSet.range(OldCardRepresentation.THEME, OldCardRepresentation.AUTHORCOUNTRY);
    }

    public Set<OldCard> getDevices() {
        return oldCards;
    }

    @Override
    public void characters(char[] ch, int start, int length) {
        String s = new String(ch, start, length).trim();
        if (currentOldCardRepresentation != null) {
            switch (currentOldCardRepresentation) {
                case THEME:
                    cardBuilder.setTheme(OldCardTheme.valueOf(s.toUpperCase()));
                    break;
                case TYPE:
                    cardBuilder.setType(OldCardType.valueOf(s.toUpperCase()));
                    break;
                case COUNTRY:
                    cardBuilder.setCountry(s);
                    break;
                case WASSENT:
                    cardBuilder.setWasSent(Boolean.parseBoolean(s));
                    break;
                case YEAR:
                    cardBuilder.setYear(Integer.parseInt(s));
                    break;
                case VALUABLE:
                    cardBuilder.setValuable(s.isEmpty() ? null : OldCardValuable.valueOf(s.toUpperCase()));
                    break;
                case NAME:
                    authorBuilder.setName(s);
                    break;
                case AUTHORCOUNTRY:
                    authorBuilder.setCountry(s);
                    break;
                default:
                    throw new EnumConstantNotPresentException(
                            currentOldCardRepresentation.getDeclaringClass(), currentOldCardRepresentation.name());
            }
        }
        currentOldCardRepresentation = null;
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) {
        if (OldCardRepresentation.OLDCARD.getTagName().equals(localName)) {
            cardBuilder = new OldCard.Builder();
            cardBuilder.setNumber(attributes.getValue(0));
        } else if (OldCardRepresentation.AUTHOR.getTagName().equals(localName)) {
            authorBuilder = new OldCardAuthor.Builder();
            authorBuilder.setIsAlive(Boolean.parseBoolean(attributes.getValue(0)));
        } else {
            OldCardRepresentation temp = OldCardRepresentation.valueOf(localName
                    .replaceAll(OldCardRepresentation.OLDCARD_REPRESENTATION_REGEX_PATTERN, "").toUpperCase());
            if (xmlElements.contains(temp)) {
                currentOldCardRepresentation = temp;
            }
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) {
        if (OldCardRepresentation.AUTHOR.getTagName().equals(localName)) {
            cardBuilder.setAuthor(authorBuilder.build());
        } else if (OldCardRepresentation.OLDCARD.getTagName().equals(localName)) {
            LOGGER.debug(cardBuilder.build() + " successfully added");
            oldCards.add(cardBuilder.build());
        }
    }

}
