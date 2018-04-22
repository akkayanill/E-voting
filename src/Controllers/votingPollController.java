package Controllers;

import Models.Other.Warning;
import Models.Voting.PollDatabase;
import Models.Voting.Voting;
import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
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
    @FXML
    BorderPane questionPane;
    @FXML
    BorderPane graphPane;
    @FXML
    PieChart graph;
    @FXML
    Label graphQuestion;


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
        graphPane.setVisible(false);
        questionPane.setVisible(true);
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
        calculate(true);
        results();
    }

    public void answer2(){
        calculate(false);
        results();
    }

    public void calculate(boolean choice){
        int numOfVoters = voting.getVoterCount();
        Double a = 0.0;
        Double b = 0.0;
        double voterPart;
        if (numOfVoters!=0) {
            voterPart = 100 / numOfVoters;
            a = voting.getPolls().get(pollCounter).getStats()[0] / voterPart;
            b = voting.getPolls().get(pollCounter).getStats()[1] / voterPart;
        }
        if (choice) a++;
        else b++;
        voterPart = 100 / ++numOfVoters;
        voting.getPolls().get(pollCounter).setStats((a * voterPart), (b * voterPart));
    }

    public void results(){
        graphQuestion.setText(voting.getPolls().get(pollCounter).getQuestion());
        ObservableList<PieChart.Data> pieChartData =
                FXCollections.observableArrayList(
                        new PieChart.Data(voting.getPolls().get(pollCounter).getChoices()[0], voting.getPolls().get(pollCounter).getStats()[0]),
                        new PieChart.Data(voting.getPolls().get(pollCounter).getChoices()[1], voting.getPolls().get(pollCounter).getStats()[1]));
        graph.setData(pieChartData);
        graph.setClockwise(false);
        questionPane.setVisible(false);
        graphPane.setVisible(true);
        if ((pollCounter+1) == voting.getPollCounter()) {
            nextQuestion.setText("End");
            nextQuestion.setOnMouseClicked(event -> end());
        }
        nextQuestion.setVisible(true);

        final Label caption = new Label("");
        caption.setTextFill(Color.DARKORANGE);
        caption.setStyle("-fx-font: 24 arial;");

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

}