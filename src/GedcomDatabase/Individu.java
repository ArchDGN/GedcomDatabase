package GedcomDatabase;

public class Individu {
    private String id;
    private String nom;
    private String prenom;
    private String sexe;

    // Constructeur
    public Individu(String id, String nom, String prenom, String sexe) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.sexe = sexe;
    }

    // Getters
    public String getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getSexe() {
        return sexe;
    }

    // Setters
    public void setId(String id) {
        this.id = id;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public void setSexe(String sexe) {
        this.sexe = sexe;
    }

    @Override
    public String toString() {
        return "Individu{" +
                "id='" + id + '\'' +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", sexe='" + sexe + '\'' +
                '}';
    }
}
