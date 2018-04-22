package Controllers;

import Models.Other.Warning;
import Models.Voting.PollDatabase;
import Models.Voting.Voting;
import com.jfoenix.controls.JFXButton;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class votingPollController{

    @FXML
    Label account;
    @FXML
    Label dateLabel;
    @FXML
    Label question;
    @FXML
    JFXButton answer1;
    @FXML
    JFXButton answer2;
    @FXML
    Label questionCounter;
    @FXML
    JFXButton nextQuestion;

    Voting voting;
    String username;
    String date;
    int pollCounter = 0;
    int votingIndex;



    public votingPollController(Voting voting,String username,String date,int index){
        this.voting = voting;
        this.username = username;
        this.date = date;
        this.votingIndex = index;
    }

    @FXML
    private void initialize(){
        account.setText(username);
        dateLabel.setText("Today: "+date);
        setUi(pollCounter);
    }

    private void setUi(int poll){
        question.setText(voting.getPolls().get(poll).getQuestion());
        answer1.setText(voting.getPolls().get(poll).getChoices()[0]);
        answer2.setText(voting.getPolls().get(poll).getChoices()[1]);
        questionCounter.setText("Question "+(pollCounter+1)+"/"+voting.getPollCounter());
        nextQuestion.setVisible(false);
        answer1.setVisible(true);
        answer2.setVisible(true);
    }


    public void nextQuestion(){
        setUi(++pollCounter);

    }

    public void answer1(){
        answer1.setVisible(false);
        answer2.setVisible(false);
        if ((pollCounter+1) == voting.getPollCounter()) {
            nextQuestion.setText("End");
            nextQuestion.setOnMouseClicked(event -> end());
        }
        nextQuestion.setVisible(true);

    }

    public void answer2(){
        answer1.setVisible(false);
        answer2.setVisible(false);
        if ((pollCounter+1) == voting.getPollCounter()) {
            nextQuestion.setText("End");
            nextQuestion.setOnMouseClicked(event -> end());
        }
        nextQuestion.setVisible(true);
    }

    public void end(){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../View/votingSuccessful.fxml"));
            fxmlLoader.setController(new votingSuccessfulController(voting,username,date,votingIndex));
            Parent root = (Parent) fxmlLoader.load();
            Stage currentStage = (Stage) nextQuestion.getScene().getWindow();

            Stage stage = new Stage();
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setTitle("E-vote");
            stage.setScene(new Scene(root, 1024,768));
            stage.show();
            currentStage.close();

        } catch (IOException e){
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }


    public void closeApp(){
        Warning.showConfirmAlert("Do you really want to exit? You will be logged out automatically," +
                " but your changes in this voting will be discarded." +
                "If you wish to save your changes, please complete the voting first.");
    }

}