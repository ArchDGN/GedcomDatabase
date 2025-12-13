package GedcomTag;


public class GedcomName extends GedcomTag {
    private String prenom;
    private String nom;

    public GedcomName() {
        super("NAME");
    }

    public void setNom(String nom) {
        this.nom = nom;
    }
    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    @Override
    public void parse(String value) {
        String[] parts = value.split("/");
        prenom = parts[0].trim();
        nom = parts.length > 1 ? parts[1].trim() : "";
    }

    @Override
    public String toString() {
        return prenom + " " + nom;
    }

    public String getPrenom() { return prenom; }
    public String getNom() { return nom; }
}
