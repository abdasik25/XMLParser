/**
 * Created by Alexander Lomat on 07.06.19
 * Version 0.0.1
 */

package by.epam.xmlparser.builder;

import by.epam.xmlparser.representation.OldCardRepresentation;
import by.epam.xmlparser.entity.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

import static by.epam.xmlparser.representation.OldCardRepresentation.*;


public class OldCardsStaxBuilder extends OldCardsBuilder {

    private static final Logger LOGGER = LogManager.getLogger();

    private XMLInputFactory inputFactory;

    public OldCardsStaxBuilder() {
        inputFactory = XMLInputFactory.newInstance();
    }

    @Override
    void buildDataSet(String filename) {
        XMLStreamReader reader;
        String name;
        try (InputStream inputStream = Files.newInputStream(Paths.get(filename))) {
            reader = inputFactory.createXMLStreamReader(inputStream);
            while (reader.hasNext()) {
                int type = reader.next();
                if (type == XMLStreamConstants.START_ELEMENT) {
                    name = reader.getLocalName();
                    if (valueOf(name.toUpperCase()) == OLDCARD) {
                        OldCard device = buildOldCard(reader);
                        oldCards.add(device);
                    }
                }
            }
        } catch (XMLStreamException | IOException e) {
            LOGGER.error("Can't read " + filename, e);
        }
    }

    private OldCard buildOldCard(XMLStreamReader reader) throws XMLStreamException {
        OldCard.Builder builder = new OldCard.Builder();
        builder.setNumber(reader.getAttributeValue(null, NUMBER.getTagName()));
        String name;
        while (reader.hasNext()) {
            int type = reader.next();
            if (type == XMLStreamConstants.START_ELEMENT) {
                name = reader.getLocalName();
                switch (valueOf(name.toUpperCase())) {
                    case THEME:
                        builder.setTheme(OldCardTheme.valueOf(getXMLText(reader).toUpperCase()));
                        break;
                    case TYPE:
                        builder.setType(OldCardType.valueOf(getXMLText(reader).toUpperCase()));
                        break;
                    case COUNTRY:
                        builder.setCountry(getXMLText(reader));
                        break;
                    case WASSENT:
                        builder.setWasSent(Boolean.parseBoolean(getXMLText(reader)));
                        break;
                    case YEAR:
                        builder.setYear(Integer.parseInt(getXMLText(reader)));
                        break;
                    case VALUABLE:
                        builder.setValuable(OldCardValuable.valueOf(getXMLText(reader).toUpperCase()));
                        break;
                    case AUTHOR:
                        builder.setAuthor(buildAuthor(reader));
                        break;
                    default:
                        throw new EnumConstantNotPresentException(OldCardRepresentation.class, name);
                }
            } else if (type == XMLStreamConstants.END_ELEMENT) {
                name = reader.getLocalName();
                if (valueOf(name.toUpperCase()) == OLDCARD) {
                    LOGGER.info(builder.build() + " was added. ");
                    return builder.build();
                }
            }
        }
        throw new XMLStreamException("Wrong element tag");
    }

    private OldCardAuthor buildAuthor(XMLStreamReader reader) throws XMLStreamException {
        OldCardAuthor.Builder builder = new OldCardAuthor.Builder();
        builder.setIsAlive(Boolean.parseBoolean(reader.getAttributeValue(0)));
        int type;
        String name;
        while (reader.hasNext()) {
            type = reader.next();
            if (type == XMLStreamConstants.START_ELEMENT) {
                name = reader.getLocalName().replaceAll(OLDCARD_REPRESENTATION_REGEX_PATTERN, "").toUpperCase();
                switch (valueOf(name)) {
                    case NAME:
                        builder.setName(getXMLText(reader));
                        break;
                    case AUTHORCOUNTRY:
                        builder.setCountry(getXMLText(reader));
                        break;
                    default:
                        throw new EnumConstantNotPresentException(OldCardRepresentation.class, name);
                }
            } else if (type == XMLStreamConstants.END_ELEMENT) {
                name = reader.getLocalName().replaceAll(OLDCARD_REPRESENTATION_REGEX_PATTERN, "").toUpperCase();
                if (valueOf(name) == AUTHOR) {
                    return builder.build();
                }
            }
        }
        throw new XMLStreamException("Unknown element in tag ");
    }

    private String getXMLText(XMLStreamReader reader) throws XMLStreamException {
        String text = null;
        if (reader.hasNext()) {
            reader.next();
            if (reader.isCharacters()) {
                text = reader.getText();
            }
        }
        return text;
    }
}
