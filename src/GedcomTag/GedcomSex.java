package GedcomTag;

public class GedcomSex extends GedcomTag {

    private char sex;

    public GedcomSex() {
        super("SEX");
    }

    @Override
    public void parse(String value) {
        sex = value.trim().charAt(0);
    }

    @Override
    public String toString() {
        return "" + sex;
    }

    public char getSex() {
        return sex;
    }
}
