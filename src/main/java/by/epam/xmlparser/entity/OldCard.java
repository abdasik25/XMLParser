/**
 * Created by Alexander Lomat on 05.06.19
 * Version 0.0.1
 */

package by.epam.xmlparser.entity;

public class OldCard {

    private OldCardTheme theme;
    private OldCardType type;
    private OldCardAuthor oldCardAuthor;
    private OldCardValuable valuable;
    private String number;
    private String country;
    private int year;
    private boolean wasSent;

    public static class Builder {

        private OldCard oldCard;

        public Builder() {
            oldCard = new OldCard();
        }

        public Builder setTheme(OldCardTheme theme) {
            oldCard.theme = theme;
            return this;
        }

        public Builder setType(OldCardType type) {
            oldCard.type = type;
            return this;
        }

        public Builder setAuthor(OldCardAuthor oldCardAuthor) {
            oldCard.oldCardAuthor = oldCardAuthor;
            return this;
        }

        public Builder setValuable(OldCardValuable valuable) {
            oldCard.valuable = valuable;
            return this;
        }

        public Builder setNumber(String number) {
            oldCard.number = number;
            return this;
        }

        public Builder setCountry(String country) {
            oldCard.country = country;
            return this;
        }

        public Builder setYear(int year) {
            oldCard.year = year;
            return this;
        }

        public Builder setWasSent(boolean wasSent) {
            oldCard.wasSent = wasSent;
            return this;
        }

        public OldCard build() {
            return oldCard;
        }

    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj){
            return true;
        }
        if (obj == null || getClass() != obj.getClass()){
            return false;
        }
        OldCard oldcard = (OldCard) obj;
        return (theme == oldcard.theme && country.equals(oldcard.country) && type == oldcard.type &&
                year == oldcard.year && wasSent == oldcard.wasSent && valuable == oldcard.valuable &&
                oldCardAuthor.equals(oldcard.oldCardAuthor));
    }

    @Override
    public int hashCode() {
        int result = 63 * year / 10 + number.hashCode() % 10;
        return result;
    }

    @Override
    public String toString() {
        return "OldCard{" +
                "theme=" + theme +
                ", type=" + type +
                ", country='" + country + '\'' +
                ", wasSent=" + wasSent +
                ", year=" + year +
                ", author=" + oldCardAuthor +
                ", valuable=" + valuable +
                ", number='" + number + '\'' +
                '}';
    }
}
