/**
 * Created by Alexander Lomat on 06.06.19
 * Version 0.0.1
 */

package by.epam.xmlparser.creator;

import by.epam.xmlparser.builder.OldCardsBuilder;
import by.epam.xmlparser.builder.OldCardsDomBuilder;
import by.epam.xmlparser.builder.OldCardsSaxBuilder;
import by.epam.xmlparser.builder.OldCardsStaxBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class OldCardsCreator {

    private static final Logger LOGGER = LogManager.getLogger();
    private static OldCardsCreator instance;

    private OldCardsCreator() {
    }

    public static OldCardsCreator getInstance() {
        if (instance == null) {
            instance = new OldCardsCreator();
        }
        return instance;
    }

    public enum ParserType {
        SAX,
        STAX,
        DOM
    }

    public OldCardsBuilder createBuilder(ParserType type) {
        LOGGER.info (type + " parser was created. ");
        switch (type) {
            case DOM:
                return new OldCardsDomBuilder();
            case STAX:
                return new OldCardsStaxBuilder();
            case SAX:
                return new OldCardsSaxBuilder();
            default:
                throw new EnumConstantNotPresentException(ParserType.class, type.toString());
        }
    }
}
