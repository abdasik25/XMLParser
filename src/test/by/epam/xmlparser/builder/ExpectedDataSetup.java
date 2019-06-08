/**
 * Created by Alexander Lomat on 07.06.19
 * Version 0.0.1
 */

package by.epam.xmlparser.builder;

import by.epam.xmlparser.entity.*;
import org.testng.annotations.BeforeClass;

import java.util.HashSet;
import java.util.Set;

abstract class ExpectedDataSetup {

    protected Set<OldCard> expectedSet;
    protected final static String FILEPATH = "test_data/testcards.xml";

    @BeforeClass
    public void setUp() {

        expectedSet = new HashSet<>();

        OldCard oldCard1;
        OldCard.Builder builder1 = new OldCard.Builder();
        OldCardAuthor.Builder authorBuilder1 = new OldCardAuthor.Builder();
        builder1.setNumber("card1");
        builder1.setTheme(OldCardTheme.LANDSCAPE);
        builder1.setType(OldCardType.HOLIDAY);
        builder1.setCountry("Belarus");
        builder1.setWasSent(true);
        builder1.setYear(2015);
        builder1.setValuable(OldCardValuable.HISTORICAL);
        authorBuilder1.setIsAlive(true);
        authorBuilder1.setName("Alexander Lomat");
        authorBuilder1.setCountry("Belarus");
        builder1.setAuthor(authorBuilder1.build());
        oldCard1 = builder1.build();


        OldCard oldCard2;
        OldCard.Builder builder2 = new OldCard.Builder();
        OldCardAuthor.Builder authorBuilder2 = new OldCardAuthor.Builder();
        builder2.setNumber("card2");
        builder2.setTheme(OldCardTheme.NATURE);
        builder2.setType(OldCardType.ADVERTISING);
        builder2.setCountry("Russia");
        builder2.setWasSent(true);
        builder2.setYear(2013);
        builder2.setValuable(OldCardValuable.THEMATIC);
        authorBuilder2.setIsAlive(true);
        authorBuilder2.setName("Dmitriy Kekin");
        authorBuilder2.setCountry("Russia");
        builder2.setAuthor(authorBuilder2.build());
        oldCard2 = builder2.build();

        OldCard oldCard3;
        OldCard.Builder builder3 = new OldCard.Builder();
        OldCardAuthor.Builder authorBuilder3 = new OldCardAuthor.Builder();
        builder3.setNumber("card3");
        builder3.setTheme(OldCardTheme.SPORTS);
        builder3.setType(OldCardType.HOLIDAY);
        builder3.setCountry("USA");
        builder3.setWasSent(true);
        builder3.setYear(2012);
        builder3.setValuable(OldCardValuable.HISTORICAL);
        authorBuilder3.setIsAlive(true);
        authorBuilder3.setName("Alexander Hleb");
        authorBuilder3.setCountry("Belarus");
        builder3.setAuthor(authorBuilder3.build());
        oldCard3 = builder3.build();

        expectedSet.add(oldCard1);
        expectedSet.add(oldCard2);
        expectedSet.add(oldCard3);
    }

    public abstract void testBuilder();
}
