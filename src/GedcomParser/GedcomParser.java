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
    private int mode = 0; // 0 = individus, 1 = familles
    private String currentId = null;
    private Individu currentIndividu = null;
    private Famille currentFamille = null;

    public GedcomParser(GedcomDatabase gedcomDatabase) {
        this.gedcomDatabase = gedcomDatabase;
    }

    public void readFile(String filePath) throws GedcomParserException {

        // ===== PASSE 1 : INDIVIDUS =====
        mode = 0;
        resetParserState();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                try {
                    parseLine(line.trim());
                } catch (InvalidParserTagException | InvalidParseLevelException e) {
                    // Ignoré en passe 1
                }
            }
            closeCurrentObject();
        } catch (IOException e) {
            throw new GedcomParserException("Erreur lecture (passe 1) : " + e.getMessage());
        }

        // ===== PASSE 2 : FAMILLES =====
        mode = 1;
        resetParserState();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                try {
                    parseLine(line.trim());
                } catch (InvalidParserTagException | InvalidParseLevelException e) {
                    // Ignoré en passe 2
                }
            }
            closeCurrentObject();
        } catch (IOException e) {
            throw new GedcomParserException("Erreur lecture (passe 2) : " + e.getMessage());
        }
    }

    private void resetParserState() {
        currentIndividu = null;
        currentFamille = null;
        currentId = null;
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

            if (value.equals("INDI") && mode == 0) {
                currentIndividu = new Individu(null, null, null);
                currentIndividu.setId(tag);
            }
            else if (value.equals("FAM") && mode == 1) {
                currentFamille = new Famille(null, null, null, null, null);
                currentFamille.setId(tag);
            }

            return;
        }

        // INDIVIDU
        if (currentIndividu != null) {
            switch (tag) {
                case "NAME":
                    // Format : Prénom /Nom/
                    currentIndividu.setNom(value);
                    break;

                case "SEX":
                    currentIndividu.setSexe(value.trim());
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
                    currentFamille.setMariWithId(value.trim().replaceAll("@", ""), gedcomDatabase);
                    break;

                case "WIFE":
                    currentFamille.setFemmeWithId(value.trim().replaceAll("@", ""), gedcomDatabase);
                    break;

                case "CHIL":
                    currentFamille.addEnfantWithId(value.trim().replaceAll("@", ""), gedcomDatabase);
                    break;

                case "OBJ":
                    currentFamille.setObjet(value);
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
            if (currentIndividu.getNom() == null) {
                emptyExceptionMessage += "ne contient pas de nom; ";
                hasEmpty = true;
            }
            if (currentIndividu.getSexe() == null) {
                emptyExceptionMessage += "ne contient pas de tag SEX;";
                hasEmpty = true;
            }

            gedcomDatabase.ajouterIndividu(currentIndividu);
        }

        if (currentFamille != null) {
            if (currentFamille.getMari() == null && currentFamille.getFemme() == null) {
                emptyExceptionMessage = "La famille " + currentId + " ne contient ni HUSB ni WIFE.";
                hasEmpty = true;
            }

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
