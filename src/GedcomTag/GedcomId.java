package GedcomTag;

public class GedcomId extends GedcomTag {
    private String id;

    public GedcomId() {
        super("NAME");
    }

    @Override
    public void parse(String value) {
        this.id = value.trim();
    }

    @Override
    public String toString() {
        return id;
    }
}
