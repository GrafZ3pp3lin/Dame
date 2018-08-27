package controller;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.*;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.StandardCharsets;

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
            BufferedReader br = new BufferedReader(new InputStreamReader(RulesPaneController.class.getClassLoader().getResourceAsStream("resources/Rules.txt"), StandardCharsets.UTF_8));
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
