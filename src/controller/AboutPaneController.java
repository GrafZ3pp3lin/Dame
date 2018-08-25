package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

/**
 * initialisiert das About Fenster
 *
 * @author Johannes Gaiser
 */
public class AboutPaneController {

    @FXML
    private Label label_des;

    @FXML
    private void initialize() {
        label_des.setText("Diese Software wurde im Rahmen eines Projekts an der DHBW Horb entwickelt.\n" +
                "Entwickler:\n\n" +
                "Alexander Hengsteler\n" +
                "Joel Schmid\n" +
                "Johannes Gaiser\n" +
                "Mareike Giek");
    }
}
