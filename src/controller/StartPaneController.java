package controller;

import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;

public class StartPaneController {

    private final String acht = "8 * 8";
    private final String zehn = "10 * 10";

    private Main control;

    @FXML
    private ChoiceBox choiceBox_size;

    public void setInstances(Main control) {
        this.control = control;
    }

    @FXML
    private void initialize() {
        choiceBox_size.getItems().add(acht);
        choiceBox_size.getItems().add(zehn);
        choiceBox_size.setValue(acht);
    }

    @FXML
    private void onSinglePlayerStart() {
        control.sampleGame();
    }

    @FXML
    private void onMultiPlayerStart() {
        System.err.println("Oops. Somebody forgot to implement me!");
    }

    public int getSize() {
        return choiceBox_size.getValue() == acht ? 8 : 10;
    }

}
