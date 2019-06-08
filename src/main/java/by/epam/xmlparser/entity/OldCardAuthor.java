/**
 * Created by Alexander Lomat on 05.06.19
 * Version 0.0.1
 */

package by.epam.xmlparser.entity;

public class OldCardAuthor {

    private String name;
    private String country;
    private boolean isAlive;

    public static class Builder {

        private OldCardAuthor oldCardAuthor;

        public Builder() {
            oldCardAuthor = new OldCardAuthor();
        }

        public Builder setName(String name) {
            oldCardAuthor.name = name;
            return this;
        }

        public Builder setCountry(String country) {
            oldCardAuthor.country = country;
            return this;
        }

        public Builder setIsAlive(boolean isAlive) {
            oldCardAuthor.isAlive = isAlive;
            return this;
        }

        public OldCardAuthor build() {
            return oldCardAuthor;
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
        OldCardAuthor oldCardAuthor = (OldCardAuthor) obj;
        return (name.equals(oldCardAuthor.name) && country.equals(oldCardAuthor.country) && isAlive == oldCardAuthor.isAlive);
    }

    @Override
    public int hashCode() {
        int result = 63 * (isAlive ? 1 : 0) + name.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Author{" +
                "name='" + name + '\'' +
                ", country='" + country + '\'' +
                ", isAlive=" + isAlive +
                '}';
    }
}
