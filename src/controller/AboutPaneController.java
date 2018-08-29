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

    private Stage aboutStage;

    @FXML
    private Label label_des;

    public void setObjects(Stage stage) {
        aboutStage = stage;
    }

    /**
     * wird automatisch bei der Initialisierung aufgerufen. Hier wird der Text für das Label gesetzt.
     * Wichtig ist, dass alle Entwickler gennant werden.
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

    @FXML
    private void close() {
        aboutStage.close();
    }

}
