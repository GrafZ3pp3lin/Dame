package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 * initialisiert das About Fenster
 *
 * @author Johannes Gaiser
 */
public class AboutPaneController {

    /**
     * Das "About" Fesnter.
     */
    private Stage aboutStage;

    /**
     * Label auf der Oberfläche. Hier werden Informationen über die Software eingefügt.
     */
    @FXML
    private Label label_des;

    /**
     * Methode, um Objekte zu übergeben.
     *
     * @param stage Das About Fenster
     */
    public void setObjects(Stage stage) {
        aboutStage = stage;
    }

    /**
     * setzt den Text für das Label.
     * Wird automatisch bei der Initialisierung aufgerufen.<br>
     * Es werden alle Entwickler gennant und der Rahmen der Entstehung.
     */
    @FXML
    private void initialize() {
        label_des.setText("Diese Software wurde im Rahmen eines Projekts an der DHBW Horb entwickelt.\n" +
                "Entwickler:\n\n" +
                "Alexander Hengsteler\n" +
                "Joel Schmid\n" +
                "Johannes Gaiser\n" +
                "Mareike Giek");
    }

    /**
     * schließt die About Oberfläche wieder.
     * Wird ausgeführt, sobald auf das Fenster geglickt wird.
     */
    @FXML
    private void close() {
        aboutStage.close();
    }

}
