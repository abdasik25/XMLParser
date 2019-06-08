/**
 * Created by Alexander Lomat on 07.06.19
 * Version 0.0.1
 */

package by.epam.xmlparser.builder;

import by.epam.xmlparser.entity.*;
import by.epam.xmlparser.creator.OldCardsCreator;
import org.testng.annotations.Test;

import java.util.Set;

import static org.testng.Assert.*;

public class OldCardsDomBuilderTest extends ExpectedDataSetup {

    OldCardsBuilder domBuilder = OldCardsCreator.getInstance().createBuilder(OldCardsCreator.ParserType.DOM);

    @Test
    @Override
    public void testBuilder() {
        domBuilder.buildDataSet(FILEPATH);
        Set<OldCard> actualSet = domBuilder.getDevices();
        assertEquals(actualSet, expectedSet);
    }
}