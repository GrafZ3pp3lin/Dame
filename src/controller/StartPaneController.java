package controller;

import javafx.fxml.FXML;

public class StartPaneController {

    private Main control;

    public void setInstances(Main control) {
        this.control = control;
    }

    @FXML
    private void onSinglePlayerStart() {
        control.sampleGame();
    }

    @FXML
    private void onMultiPlayerStart() {
        System.err.println("Oops. Somebody forgot to implement me!");
    }

}
