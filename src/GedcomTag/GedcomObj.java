package GedcomTag;

public class GedcomObj extends GedcomTag {

    private String path;

    public GedcomObj() {
        super("OBJ");
    }

    @Override
    public void parse(String value) {
        path = value.trim();
    }

    @Override
    public String toString() {
        return "OBJ(" + path + ")";
    }

    public String getPath() {
        return path;
    }
}
