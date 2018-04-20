package Controllers;

import Models.User.*;
import Models.Other.*;


import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    public void closeApp() { System.exit(0); }
    private void wrongDataStyle(Node node){
        node.setStyle("-fx-border-color: red;" + "-fx-border-width:2");
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

    public void registerSuccessful() {
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/View/registrationSuccessful.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            Stage stage = (Stage) signUpButton.getScene().getWindow();
            stage.setTitle("E-vote - Registration Successful");
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
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
            } else Warning.showAlert("There is already account with that e-mail address.");
        }
    }
    public boolean emailTypeChecker(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";

        Pattern pat = Pattern.compile(emailRegex);
        if (email == null)
            return false;
        return pat.matcher(email).matches();
    }


    public void showTermsOfUse() {
        Warning.showAlert("1- : Registration: inform users that they must agree to a set of \n" + "rules when they register on your website or mobile app\n" +
                "2: - Owner of content: notify users that you, is the \n" + "owner of the content appearing on your website except in cases where \n" + "other users can post content (share, create etc.) where the users are the owners of such content\n" +
                "3: - Changes to the agreement: users should be informed about \n" + "upcoming changes to the agreement before the changes are applied.");
    }
    /**
     * Verifies input if TextFields are not empty and checks e-mail TextField for correct pattern.
     * @return true if both of the TextFields are empty and e-mail TextField contains one ampersand symbol and at least one dot.
     */

    private boolean verifyInput() {
        String message = " ";
        int i = 0;
        loadFields();
/*
        if ((username.isEmpty()) &&  password.isEmpty() && confirmPassword.isEmpty()) {
            wrongDataStyle(usernameField);
            wrongDataStyle(passwordField);
            i++;
            message = message + i + "- : Please enter all information";
        }
*/
        if (username.isEmpty()) {
            wrongDataStyle(usernameField);
            i++;
            message = message + "\n " + i + "- : Please enter your e-mail";
        }
        if (password.isEmpty()) {
            wrongDataStyle(passwordField);
            i++;
            message = message + "\n " + i + "- : Please enter your password";
        }
        if(password.length() < 8) {
            wrongDataStyle(passwordField);
            i++;
            message = message + "\n " + i + "- : Password needs to be at least 8 characters long";
        }
        if (confirmPassword.isEmpty()) {
            wrongDataStyle(confirmPasswordField);
            i++;
            message = message + "\n " + i + "- : Please confirm your password";
        }
        if (!(password.equals(confirmPassword))) {
            wrongDataStyle(confirmPasswordField);
            wrongDataStyle(passwordField);
            i++;
            message = message + "\n " + i + "- : Passwords does not match";
        }
        if (!accept.isSelected()) {
            i++;
            message = message + "\n " + i + "- : Please accept the terms of usage";
        }

        if(i == 0 && emailTypeChecker(username)){
            return true;
        } else {
            i++;
            message = message + "\n " + i + "- : Invalid email adress!";
            Warning.showAlert(message);
            wrongDataStyle(usernameField);
        }

    return false;
    }

}
