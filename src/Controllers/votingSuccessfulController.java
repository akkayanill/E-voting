package Controllers;

import Models.Other.Warning;
import Models.Voting.Voting;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class votingSuccessfulController {

    @FXML
    Label account;
    @FXML
    Label dateLabel;
    @FXML
    Label votingTitle;
    @FXML
    Label voterCount;

    Voting voting;
    String username;
    String date;
    int votingIndex;




    public votingSuccessfulController(Voting voting, String username, String date,int index){
        this.voting = voting;
        this.username = username;
        this.date = date;
        this.votingIndex = index;
    }

    @FXML
    private void initialize(){
        account.setText(username);
        dateLabel.setText("Today: "+date);
        votingTitle.setText("You have successfully completed "+voting.getTitle()+" voting!");
        voterCount.setText("You and "+voting.getVoterCount()+" other voters already voted.");
        voting.addVoter(username);
    }

    public void backToMainScreen(){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/View/votingApp.fxml"));
            fxmlLoader.setController(new votingAppController(username,voting,votingIndex));
            Parent root = (Parent) fxmlLoader.load();
            Stage stage = (Stage) account.getScene().getWindow();
            stage.setTitle("E-vote - Log In");
            stage.setScene(new Scene(root));
            stage.show();


        } catch (IOException e){
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

    public void closeApp(){
        Warning.showConfirmAlert("Do you really want to exit? You will be logged out automatically");
    }

    public void logOut(){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/View/login.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            Stage currentStage = (Stage) account.getScene().getWindow();
            Stage stage = new Stage();
            stage.setTitle("E-vote - Log In");
            stage.setScene(new Scene(root));
            stage.initStyle(StageStyle.UNDECORATED);
            currentStage.close();
            stage.show();
            Warning.showAlert("You have successfully logged out");

        } catch (IOException e){
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }
}