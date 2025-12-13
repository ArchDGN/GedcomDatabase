package GedcomDatabase;

import GedcomTag.*;

public class Individu {
    private GedcomId id;
    private GedcomName nom;
    private GedcomSex sexe;


    // Constructeur
    public Individu(GedcomId id, GedcomName nom, GedcomSex sexe) {
        this.id = id;
        this.nom = nom;
        this.sexe = sexe;
    }

    // Modif
    public void setId(String id) {
        this.id = new GedcomId();
        this.id.parse(id);
    }

    public void setNom(String nom) {
        this.nom = new GedcomName();
        this.nom.parse(nom);
    }

    public void setSexe(String sexe) {
        this.sexe = new GedcomSex();
        this.sexe.parse(sexe);
    }

    public void setNomSeul(String nom) {
        if (this.nom == null) {
            this.nom = new GedcomName();
        }
        this.nom.setNom(nom);
    }

    public void setPrenomSeul(String prenom) {
        if (this.nom == null) {
            this.nom = new GedcomName();
        }
        this.nom.setPrenom(prenom);
    }

    // Getters
    public GedcomId getId() { return id; }
    public GedcomName getNom() { return nom; }
    public GedcomSex getSexe() { return sexe; }

    // Setters
    public void setId(GedcomId id) { this.id = id; }
    public void setNom(GedcomName nom) { this.nom = nom; }
    public void setSexe(GedcomSex sexe) { this.sexe = sexe; }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Individu)) return false;
        Individu other = (Individu) obj;
        return id.equals(other.id);
    }

    @Override
    public String toString() {
        return "Individu{" +
                "id=" + id +
                ", nom=" + nom +
                ", sexe=" + sexe +
                '}';
    }
}
