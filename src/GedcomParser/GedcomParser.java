package GedcomParser;

import GedcomDatabase.*;
import GedcomExceptions.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import java.util.HashSet;
import java.util.Set;

public class GedcomParser {

    private final GedcomDatabase gedcomDatabase;

    // États du parser
    private String currentId = null;
    private Individu currentIndividu = null;
    private Famille currentFamille = null;

    // Variables temporaires pour remplir les objets
    private String tempNom = "";
    private String tempPrenom = "";
    private String tempSexe = "";

    private String tempMari = "";
    private String tempFemme = "";
    private Set<String> tempEnfants = new HashSet<>();
    private String tempObjet = "";

    public GedcomParser(GedcomDatabase gedcomDatabase) {
        this.gedcomDatabase = gedcomDatabase;
    }

    public void readFile(String filePath) throws GedcomParserException {

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {

            String line;
            while ((line = br.readLine()) != null) {
                try {
                    parseLine(line.trim());
                } catch (InvalidParserTagException | InvalidParseLevelException e) {
                    System.err.println("Erreur parsing ligne : " + e.getMessage());
                }
            }

            // Finalise le dernier bloc
            closeCurrentObject();

        } catch (IOException e) {
            throw new GedcomParserException("Erreur de lecture du fichier : " + e.getMessage());
        }
    }

    private void parseLine(String line) throws InvalidParserTagException, InvalidParseLevelException {
        if (line.isEmpty()) return;

        String[] parts = line.split(" ", 3);
        int level = Integer.parseInt(parts[0]);
        String tag = parts.length > 1 ? parts[1] : "";
        tag = tag.replaceAll("@", "");
        String value = parts.length > 2 ? parts[2] : "";

        // Début d'un nouveau bloc 0 @ID@ INDI/FAM
        if (level == 0) {

            try {
                closeCurrentObject();
            } catch (InvalidParserArgumentEmptyException e) {
                System.err.println("Erreur fermeture bloc : " + e.getMessage());
            }

            if (value.equals("INDI")) {
                currentId = tag;
                tempNom = "";
                tempPrenom = "";
                tempSexe = "";
                currentIndividu = new Individu("", "", "", "");
            }
            else if (value.equals("FAM")) {
                currentId = tag;
                tempMari = "";
                tempFemme = "";
                tempEnfants = new HashSet<>();
                currentFamille = new Famille("", "", "", new HashSet<>());
            }

            return;
        }

        // INDIVIDU
        if (currentIndividu != null) {
            switch (tag) {
                case "NAME":
                    // Format : Prénom /Nom/
                    String[] nameParts = value.split(" /");
                    tempPrenom = nameParts[0].trim();
                    try {
                        tempNom = nameParts[1].replace("/", "").trim();
                    } catch (ArrayIndexOutOfBoundsException e) {
                        tempNom = "";
                    }
                    break;

                case "SEX":
                    tempSexe = value.trim();
                    break;

                case "FAMS":
                    break;
                case "FAMC":
                    break;

                default :
                    throw new InvalidParserTagException("Tag non supporté : " + tag);
            }
        }

        // FAMILLE
        if (currentFamille != null) {
            switch (tag) {
                case "HUSB":
                    tempMari = value.trim().replaceAll("@", "");
                    break;

                case "WIFE":
                    tempFemme = value.trim().replaceAll("@", "");
                    break;

                case "CHIL":
                    tempEnfants.add(value.trim().replaceAll("@", ""));
                    break;

                case "OBJ":
                    tempObjet = value.trim();
                    break;
                default :
                    throw new InvalidParserTagException("Tag non supporté : " + tag);
            }
        }

        if (level != 1) {
            throw new InvalidParseLevelException("Niveau non supporté : " + level);
        }
    }

    /**
     * Finalise un INDIVIDU ou une FAMILLE avant de passer à la suivante
     */
    private void closeCurrentObject() throws InvalidParserArgumentEmptyException {

        boolean hasEmpty = false;
        String emptyExceptionMessage = "L'individu " + currentId + " a des champs vides : ";

        if (currentIndividu != null) {
            if (tempPrenom.isEmpty()) {
                emptyExceptionMessage += "ne contient pas de PRENOM; ";
                hasEmpty = true;
            }
            if (tempNom.isEmpty()) {
                emptyExceptionMessage += "ne contient pas de nom; ";
                hasEmpty = true;
            }
            if (tempSexe.isEmpty()) {
                emptyExceptionMessage += "ne contient pas de tag SEX;";
                hasEmpty = true;
            }

            currentIndividu.setId(currentId);
            currentIndividu.setNom(tempNom);
            currentIndividu.setPrenom(tempPrenom);
            currentIndividu.setSexe(tempSexe);

            gedcomDatabase.ajouterIndividu(currentIndividu);
        }

        if (currentFamille != null) {
            if (tempMari.isEmpty() && tempFemme.isEmpty()) {
                emptyExceptionMessage = "La famille " + currentId + " ne contient ni HUSB ni WIFE.";
                hasEmpty = true;
            }

            currentFamille.setId(currentId);
            currentFamille.setMariId(tempMari);
            currentFamille.setFemmeId(tempFemme);
            currentFamille.setEnfantsIds(tempEnfants);
            currentFamille.setObjet(tempObjet);

            gedcomDatabase.ajouterFamille(currentFamille);
        }

        currentIndividu = null;
        currentFamille = null;
        currentId = null;

        if (hasEmpty) {
            throw new InvalidParserArgumentEmptyException(emptyExceptionMessage);
        }
    }
}
