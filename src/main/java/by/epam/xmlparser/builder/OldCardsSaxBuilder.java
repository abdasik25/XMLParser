/**
 * Created by Alexander Lomat on 07.06.19
 * Version 0.0.1
 */

package by.epam.xmlparser.builder;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;

public class OldCardsSaxBuilder extends OldCardsBuilder {

    private static final Logger LOGGER = LogManager.getLogger();
    private OldCardsHandler oldCardsHandler;
    private SAXParser parser;

    public OldCardsSaxBuilder() {
        oldCardsHandler = new OldCardsHandler();
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            factory.setNamespaceAware(true);
            parser = factory.newSAXParser();
        } catch (ParserConfigurationException | SAXException e) {
            LOGGER.error("Wrong parser configuration ", e);
        }
    }

    @Override
    void buildDataSet(String fileName) {
        try {
            parser.parse(fileName, oldCardsHandler);
            oldCards = oldCardsHandler.getDevices();
        } catch (SAXException | IOException e) {
            LOGGER.error("Can't parse " + fileName, e);
        }
    }
}
