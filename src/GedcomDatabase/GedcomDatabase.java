package GedcomDatabase;

import java.util.*;

import GedcomExceptions.*;
import GedcomTag.*;

public class GedcomDatabase {
    private final Map<String, Individu> individus;
    private final Map<String, Famille> familles;

    Set<String> dejaAffiche = new HashSet<>();

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
        afficherArbre(individuId, 0);
    }

    private void afficherArbre(String individuId, int niveau) {

        if (dejaAffiche.contains(individuId)) {
            return;
        }
        dejaAffiche.add(individuId);

        Individu individu = individus.get(individuId);
        if (individu == null) return;

        String indent = "  ".repeat(niveau);
        System.out.println(indent + " " + individu.getNom());

        for (Famille famille : familles.values()) {

            if (famille.chercherIndividuDansFamille(individuId)) {

                System.out.println(indent + "  Famille " + famille.getId());

                if (famille.getMari() != null) {
                    System.out.println(indent + "    Mari : " + famille.getMari().getNom());
                }
                if (famille.getFemme() != null) {
                    System.out.println(indent + "    Femme : " + famille.getFemme().getNom());
                }

                System.out.println(indent + "    Enfants :");
                if (famille.getEnfants() != null && !famille.getEnfants().isEmpty()) {
                    for (Individu enfant : famille.getEnfants()) {
                        System.out.println(indent + "      - " + enfant.getNom());
                    }
                } else {
                    System.out.println(indent + "      Aucun enfant");
                }

                // récursion UNIQUEMENT si l'individu est parent
                if (famille.chercherIndividuParent(individuId)) {

                    for (Individu enfant : famille.getEnfants()) {
                        afficherArbre(enfant.getId().toString(), niveau + 1);
                    }
                }
            }
        }
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
