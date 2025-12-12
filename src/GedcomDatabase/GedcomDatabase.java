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

    private void verifierUniqueIndividus(Individu individu) throws GedcomDatabaseExceptionIndividu {
        for (Individu ind : individus.values()) {
            if (ind.getId().equals(individu.getId())) {
                throw new GedcomDatabaseExceptionIndividu("ID Individu dupliqué entre : " + ind + " et " + individu);
            }
        }
    }

    private void verifierUniqueFamilles(Famille famille) throws GedcomDatabaseExceptionFamille {
        for (Famille fam : familles.values()) {
            if (fam.getId().equals(famille.getId())) {
                throw new GedcomDatabaseExceptionFamille("ID Famille dupliqué entre : " + fam.getId() + " et " + famille.getId());
            }
        }
    }

    // Méthodes pour ajouter des individus et des familles
    public void ajouterIndividu(Individu individu) {
        try {
            verifierUniqueIndividus(individu);
        } catch (GedcomDatabaseExceptionIndividu e) {
            System.err.println(e.getMessage());
            System.err.println("ID unique non respecté, individu non ajouté : " + individu);
            return;
        }

        individus.put(individu.getId(), individu);
    }

    public void ajouterFamille(Famille famille) {
        try {
            verifierUniqueFamilles(famille);
        } catch (GedcomDatabaseExceptionFamille e) {
            System.err.println(e.getMessage());
            System.err.println("ID unique non respecté, famille non ajoutée : " + famille.getId());
            return;
        }
        familles.put(famille.getId(), famille);
    }

    // Getters
    public Map<String, Individu> getIndividus() {
        return individus;
    }

    public Map<String, Famille> getFamilles() {
        return familles;
    }
}
