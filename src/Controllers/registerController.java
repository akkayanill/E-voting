package Controllers;

import Models.User.*;
import Models.Other.*;


import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class registerController {

    @FXML JFXTextField usernameField;
    @FXML JFXPasswordField passwordField;
    @FXML JFXPasswordField confirmPasswordField;
    @FXML JFXCheckBox accept;
    @FXML JFXButton signUpButton;

    private UserDatabase database;
    private String username;
    private String password;
    private String confirmPassword;

    public registerController(UserDatabase database){
        this.database = database;
    }

    public void loadFields(){
        username = usernameField.getText();
        password = passwordField.getText();
        confirmPassword = confirmPasswordField.getText();
    }
    /**
     * Closes Window by clicking on Label X
     */
    public void closeApp() {
        System.exit(0);
    }

    public void goToLogin() {
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/View/login.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            Stage stage = (Stage) signUpButton.getScene().getWindow();
            stage.setTitle("E-vote - Log In");
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e){
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

    public void registerSuccessful(){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/View/registrationSuccessful.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            Stage stage = (Stage) signUpButton.getScene().getWindow();
            stage.setTitle("E-vote - Registration Successful");
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e){
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }


    public void register() {
            if (verifyInput()) {
                if (!(database.isInDatabase(username))) {
                        database.addUser(username, password);
                        database.saveToFile();
                        registerSuccessful();
                }
                else Warning.showAlert("There is already account with that e-mail address.");
            }
    }

    /**
     * Verifies input if TextFields are not empty and checks e-mail TextField for correct pattern.
     * @return true if both of the TextFields are empty and e-mail TextField contains one ampersand symbol and at least one dot.
     */
    private boolean verifyInput() {
        loadFields();

        if ((username.isEmpty()) &&  password.isEmpty() && confirmPassword.isEmpty()) {
            Warning.showAlert("Please enter all information");
            //wrongDataStyle(usernameField);
            //wrongDataStyle(passwordField);
            return false;
        }


        else if (username.isEmpty()) {
            Warning.showAlert("Please enter your e-mail");
            //wrongDataStyle(usernameField);
            return false;
        }

        else if (password.isEmpty()) {
            Warning.showAlert("Please enter your password");
            //wrongDataStyle(passwordField);
            return false;
        }

        else if(password.length() < 8) {
            Warning.showAlert("Password needs to be at least 8 characters long");
            return false;
        }

        else if (confirmPassword.isEmpty()) {
            Warning.showAlert("Please confirm your password");
            return false;
        }

        else if (!(password.equals(confirmPassword))) {
            Warning.showAlert("Passwords does not match");
            return false;
        }

        else if (((accept.isIndeterminate() == false) && (accept.isPressed()==false))) {   //TODO
            Warning.showAlert("You must accept Terms & Conditions");
            return false;
        }

        else {
            if(((username.length() - username.replace("@","").length())==1) && ((username.length() - username.replace(".","").length())>=1)){
                return true;
            }
            else {
                Warning.showAlert("Invalid email adress!");
                //wrongDataStyle(usernameField);
                return false;
            }

        }
    }

}
