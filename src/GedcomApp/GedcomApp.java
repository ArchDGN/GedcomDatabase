package GedcomApp;

import GedcomExceptions.GedcomAppException;
import GedcomExceptions.GedcomParserException;
import GedcomParser.GedcomParser;
import GedcomDatabase.GedcomDatabase;

public class GedcomApp {
    public void run() throws GedcomAppException {
        GedcomDatabase gedcomDatabase = new GedcomDatabase();
        GedcomParser parser = new GedcomParser(gedcomDatabase);
        try {
            parser.readFile("src/Resources/test3.ged");
        } catch (GedcomParserException e) {
            throw new GedcomAppException("Erreur lors de la lecture du fichier GEDCOM : " + e.getMessage());
        }
        gedcomDatabase.afficherContenu();

        System.out.println("\n=== ARBRE GÉNÉALOGIQUE ===");
        gedcomDatabase.afficherArbre("I3");
    }
}
