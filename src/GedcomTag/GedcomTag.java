package GedcomTag;

public abstract class GedcomTag {
    private final String tagName;

    protected GedcomTag(String tagName) {
        this.tagName = tagName;
    }

    public String getTagName() {
        return tagName;
    }

    // Chaque tag sait se parser lui-même :
    public abstract void parse(String value);

    @Override
    public abstract String toString();
}
