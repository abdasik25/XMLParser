/**
 * Created by Alexander Lomat on 07.06.19
 * Version 0.0.1
 */

package by.epam.xmlparser.builder;

import by.epam.xmlparser.entity.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.HashSet;

import static by.epam.xmlparser.representation.OldCardRepresentation.*;

public class OldCardsDomBuilder extends OldCardsBuilder {

    private static final Logger LOGGER = LogManager.getLogger();
    private DocumentBuilder documentBuilder;

    public OldCardsDomBuilder() {
        this.oldCards = new HashSet<>();
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            documentBuilder = factory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            LOGGER.error("Wrong configuration of parser. ", e);
        }
    }

    @Override
    void buildDataSet(String filename) {
        Document document;
        try {
            document = documentBuilder.parse(filename);
            Element root = document.getDocumentElement();
            NodeList oldCardList = root.getElementsByTagName(OLDCARD.getTagName());
            for (int i = 0; i < oldCardList.getLength(); i++) {
                Element oldCardElement = (Element) oldCardList.item(i);
                OldCard oldCard = buildOldCard(oldCardElement);
                oldCards.add(oldCard);
            }
        } catch (IOException | SAXException e) {
            LOGGER.error("Error while parsing. " + filename, e);
        }
    }

    private OldCard buildOldCard(Element oldCardElement) {
        OldCard.Builder builder = new OldCard.Builder();
        builder.setNumber(oldCardElement.getAttribute(NUMBER.getTagName()))
                .setTheme(OldCardTheme.valueOf(getTextContent(oldCardElement, THEME.getTagName()).toUpperCase()))
                .setType(OldCardType.valueOf(getTextContent(oldCardElement, TYPE.getTagName()).toUpperCase()))
                .setCountry(getTextContent(oldCardElement, COUNTRY.getTagName()))
                .setWasSent(Boolean.parseBoolean(getTextContent(oldCardElement, WASSENT.getTagName())))
                .setYear(Integer.parseInt(getTextContent(oldCardElement, YEAR.getTagName())))
                .setValuable(OldCardValuable.valueOf(getTextContent(oldCardElement, VALUABLE.getTagName()).toUpperCase()))
                .setAuthor(oldCardElement.getElementsByTagName(AUTHOR.getTagName()).item(0) != null ?
                        buildAuthor((Element) oldCardElement.getElementsByTagName(AUTHOR.getTagName()).item(0)) : null);
        OldCard oldCard = builder.build();
        LOGGER.info(oldCard + " was added. ");
        return oldCard;
    }

    private OldCardAuthor buildAuthor(Element authorElement) {
        OldCardAuthor.Builder builder = new OldCardAuthor.Builder();

        if (authorElement.hasChildNodes()) {
            builder.setIsAlive(Boolean.parseBoolean(authorElement.getAttribute(ISALIVE.getTagName())));
            NodeList nodeList = authorElement.getChildNodes();
            for (int i = 0; i < nodeList.getLength(); i++) {
                if (nodeList.item(i).getNodeName().equals(NAME.getTagName())) {
                    builder.setName(getTextContent(authorElement, NAME.getTagName()));
                }
                if (nodeList.item(i).getNodeName().equals(AUTHORCOUNTRY.getTagName())) {
                    builder.setCountry(getTextContent(authorElement, AUTHORCOUNTRY.getTagName()));
                }
            }
        }
        return builder.build();
    }

    private String getTextContent(Element element, String elementName) {
        NodeList nList = element.getElementsByTagName(elementName);
        Node node = nList.item(0);
        return node.getTextContent();
    }
}
