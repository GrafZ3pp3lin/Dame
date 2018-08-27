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

    public void setInstances(Stage stage) {
        aboutStage = stage;
    }

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
