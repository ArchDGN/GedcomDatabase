package GedcomDatabase;

import java.util.Map;
import java.util.HashMap;

import GedcomExceptions.*;

public class GedcomDatabase {
    private final Map<String, Individu> individus;
    private final Map<String, Famille> familles;

    // Constructeur
    public GedcomDatabase() {
        individus = new HashMap<>();
        familles = new HashMap<>();
    }

    public void afficherContenu() {
        System.out.println("Individus:");
        for (Individu individu : individus.values()) {
            System.out.println(individu);
        }
        System.out.println("\nFamilles:");
        for (Famille famille : familles.values()) {
            System.out.println(famille);
        }
    }

    public void afficherArbre(String individuId) {

    }

    // Méthodes pour ajouter des individus et des familles
    public void ajouterIndividu(Individu individu) {
        if (individuExiste(individu.getId().toString())) {
            System.err.println("ID unique non respecté, individu non ajouté : " + individu.getId());
        } else {
            individus.put(individu.getId().toString(), individu);
        }
    }

    public void ajouterFamille(Famille famille) {
        if (familleExiste(famille.getId().toString())) {
            System.err.println("ID unique non respecté, famille non ajoutée : " + famille.getId());
            return;
        }

        familles.put(famille.getId().toString(), famille);
    }

    public boolean individuExiste(String individuId) {
        return individus.containsKey(individuId);
    }

    public boolean familleExiste(String familleId) {
        return familles.containsKey(familleId);
    }

    public Individu getIndividuById(String individuId) {
        return individus.get(individuId);
    }

    // Getters
    public Map<String, Individu> getIndividus() {
        return individus;
    }

    public Map<String, Famille> getFamilles() {
        return familles;
    }
}
