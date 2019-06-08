/**
 * Created by Alexander Lomat on 07.06.19
 * Version 0.0.1
 */

package by.epam.xmlparser.builder;

import by.epam.xmlparser.entity.OldCard;

import java.util.HashSet;
import java.util.Set;

public abstract class OldCardsBuilder {

    protected Set<OldCard> oldCards;

    public OldCardsBuilder() {
        oldCards = new HashSet<>();
    }

    public Set<OldCard> getDevices() {
        return oldCards;
    }

    abstract void buildDataSet(String filename);
}
