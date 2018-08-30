package controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class RulesPaneController {

    private Stage rulesStage;

    @FXML
    private TextArea rules;

    public void setObjects(Stage rules) {
        this.rulesStage = rules;
    }

    /**
     * Wird automatisch bei der Initialisierung aufgerufen. Hier werden die Regeln aus der Text datei aus den resourcen
     * ausgelesen und in die TextArea übertragen.
     */
    @FXML
    private void initialize() {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(RulesPaneController.class.getClassLoader().getResourceAsStream("resources/Rules.txt"), StandardCharsets.UTF_8));
            String zeile;
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
