package Controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class registrationSuccessfulController {

    @FXML Button backButton;

    public void closeApp() {
        System.exit(0);
    }

    public void goToLogin() {
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/View/login.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            Stage stage = (Stage) backButton.getScene().getWindow();
            stage.setTitle("E-vote - Log In");
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e){
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }
}
