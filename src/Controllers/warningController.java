package Controllers;

import Models.Other.Warning;
import com.jfoenix.controls.JFXButton;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class warningController {

    @FXML Label warningLabel;
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
        Stage stage = (Stage) warningLabel.getScene().getWindow();

        stage.close();
    }

    public void closeApp(){
        Platform.exit();
    }

}
