import GedcomApp.GedcomApp;
import GedcomExceptions.GedcomAppException;

public class Main {
    public static void main(String[] args) {
        GedcomApp app = new GedcomApp();
        try {
            app.run();
        } catch (GedcomAppException e) {
            throw new RuntimeException(e);
        }
    }
}
