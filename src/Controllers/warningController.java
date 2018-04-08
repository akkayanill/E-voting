package Controllers;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class warningController {

    @FXML Label warningLabel;
    @FXML JFXButton okayButton;
    private String warningMessage;

    /**
     * Sets warning message to Label in fxml
     */
    @FXML
    private void initialize() {
        warningLabel.setText(warningMessage);

    }

    public warningController(String message) {
        warningMessage = message;
    }

    /**
     * Closes warning message stage
     */
    public void okay(){
        Stage stage = (Stage) okayButton.getScene().getWindow();
        stage.close();

    }

}
