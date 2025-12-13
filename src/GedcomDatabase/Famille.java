package GedcomDatabase;

import java.util.HashSet;
import java.util.Set;
import java.util.HashMap;

import GedcomDatabase.*;
import GedcomTag.*;

public class Famille {
    private GedcomId id;
    private Individu mari;
    private Individu femme;
    private Set<Individu> enfants;
    private GedcomObj objet;

    // Constructeur
    public Famille(GedcomId id, Individu mariId, Individu femmeId, Set<Individu> enfantsIds, GedcomObj objet) {
        this.id = id;
        this.mari = mariId;
        this.femme = femmeId;
        this.enfants = enfantsIds;
        this.objet = objet;
    }

    // Getters
    public GedcomId getId() { return id; }
    public Individu getMari() { return mari; }
    public Individu getFemme() { return femme; }
    public Set<Individu> getEnfants() { return enfants; }
    public GedcomObj getObjet() { return objet; }

    // Setters
    public void setId(GedcomId id) { this.id = id; }
    public void setId(String id) {
        this.id = new GedcomId();
        this.id.parse(id);
    }
    public void setMari(Individu mari) { this.mari = mari; }
    public void setFemme(Individu femme) { this.femme = femme; }
    public void setEnfants(Set<Individu> enfants) { this.enfants = enfants; }
    public void setObjet(GedcomObj objet) { this.objet = objet; }
    public void setObjet(String objet) {
        this.objet = new GedcomObj();
        this.objet.parse(objet);
    }

    public void ajouterEnfant(Individu enfant) {
        if (this.enfants == null) {
            this.enfants = new HashSet<>();
        }
        this.enfants.add(enfant);
    }

    // Set with Ids
    public void setMariWithId(String mariId, GedcomDatabase db) {
        if (db.individuExiste(mariId)) {
            this.mari = db.getIndividuById(mariId);
        } else {
            GedcomId newId = new GedcomId();
            newId.parse(mariId);
            this.mari = new Individu(newId, null, null);
            db.ajouterIndividu(this.mari);
        }
    }

    public void setFemmeWithId(String femmeId, GedcomDatabase db) {
        if (db.individuExiste(femmeId)) {
            this.femme = db.getIndividuById(femmeId);
        } else {
            GedcomId newId = new GedcomId();
            newId.parse(femmeId);
            this.femme = new Individu(newId, null, null);
            db.ajouterIndividu(this.femme);
        }
    }

    public void addEnfantWithId(String enfantId, GedcomDatabase db) {
        if (this.enfants == null) {
            this.enfants = new HashSet<>();
        }

        if (db.individuExiste(enfantId)) {
            this.enfants.add(db.getIndividuById(enfantId));
        } else {
            GedcomId newId = new GedcomId();
            newId.parse(enfantId);
            Individu enfant = new Individu(newId, null, null);
            this.enfants.add(enfant);
            db.ajouterIndividu(enfant);
        }
    }

    @Override
    public String toString() {
        return "Famille{" +
                "id=" + id +
                ", mari=" + (mari != null ? mari : "null") +
                ", femme=" + (femme != null ? femme : "null") +
                ", enfants=" + enfants +
                ", objet=" + objet +
                '}';
    }

}
