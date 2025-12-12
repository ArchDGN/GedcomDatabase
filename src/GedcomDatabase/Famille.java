package GedcomDatabase;

import java.util.Set;

public class Famille {
    private String id;
    private String mariId;
    private String femmeId;
    private Set<String> enfantsIds;
    private String objet;

    // Constructeur
    public Famille(String id, String mariId, String femmeId, Set<String> enfantsIds) {
        this.id = id;
        this.mariId = mariId;
        this.femmeId = femmeId;
        this.enfantsIds = enfantsIds;
    }

    // Getters
    public String getId() {
        return id;
    }

    public String getMariId() {
        return mariId;
    }

    public String getFemmeId() {
        return femmeId;
    }

    public Set<String> getEnfantsIds() {
        return enfantsIds;
    }

    public String getObjet() { return objet; }

    // Setters
    public void setId(String id) {
        this.id = id;
    }

    public void setMariId(String mariId) {
        this.mariId = mariId;
    }

    public void setFemmeId(String femmeId) {
        this.femmeId = femmeId;
    }

    public void setEnfantsIds(Set<String> enfantsIds) {
        this.enfantsIds = enfantsIds;
    }

    public void setObjet(String objet) {
        this.objet = objet;
    }

    @Override
    public String toString() {
        return "Famille{" +
                "id='" + id + '\'' +
                ", mariId='" + mariId + '\'' +
                ", femmeId='" + femmeId + '\'' +
                ", enfantsIds=" + enfantsIds +
                ", objet='" + objet + '\'' +
                '}';
    }

}
