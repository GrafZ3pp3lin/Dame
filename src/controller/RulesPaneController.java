package controller;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.*;

public class RulesPaneController {

    private Stage rulesStage;

    @FXML
    private TextArea rules;

    public void setInstances(Stage rules) {
        this.rulesStage = rules;
    }

    @FXML
    private void initialize() {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(RulesPaneController.class.getClassLoader().getResourceAsStream("resources/Rules.txt")));
            String zeile = "";
            while ((zeile = br.readLine()) != null) {
                rules.appendText(zeile + "\n");
            }
            br.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void close() {
        rulesStage.close();
    }


}
