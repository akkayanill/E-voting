package Controllers;

import Models.Other.TimeFlow;
import Models.Other.Warning;
import Models.User.User;
import Models.User.UserDatabase;
import Models.Voting.PollDatabase;
import Models.Voting.Voting;
import com.jfoenix.controls.JFXButton;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class votingAppController {

    @FXML Label account;
    @FXML Label dateLabel;
    @FXML AnchorPane anchorParent;
    @FXML JFXButton previousPageButton;
    @FXML JFXButton nextPageButton;
    @FXML BorderPane pollButton1;
    @FXML BorderPane pollButton2;
    @FXML BorderPane pollButton3;
    @FXML BorderPane pollButton4;


    private DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private TimeFlow timeFlow = new TimeFlow();
    private int totalPages;
    private int page= -1;
    private PollDatabase votings = new PollDatabase("/src/Data/PollData.csv");
    private UserDatabase database =new UserDatabase("/src/Data/UsrData.csv");
    private User currentUsr;




    public votingAppController(String username){
        database.loadDatabase();
        votings.loadDatabase();
        this.currentUsr = database.getUserByUserName(username);
    }


    public votingAppController(String username,Voting voting, int index){
        votings.loadDatabase();
        votings.getVoting(index).replaceStats(voting);
        votings.saveToFile();

        database.loadDatabase();
        this.currentUsr = database.getUserByUserName(username);
        currentUsr.addCompletedVoting();
    }
    /**
     * Loads data on stage start
     */
    @FXML
    private void initialize(){

        pollButton1.setOnMouseClicked(event -> openPoll(0));
        pollButton2.setOnMouseClicked(event -> openPoll(1));
        pollButton3.setOnMouseClicked(event -> openPoll(2));
        pollButton4.setOnMouseClicked(event -> openPoll(3));

        account.setText(currentUsr.getEmail());

        //Time flow initialization
        dateLabel.setText("Today: "+timeFlow.toString());

        ;

        //filling labels
        totalPages = ((votings.size()-1) / 4)+1;
        nextPage();

    }

    public void nextPage(){
        setInvisible();
        page++;
        for (int i=0;(i<4);i++){
            if (((page*4)+i) < votings.size()) {
                    Label labelTitle = (Label) ((VBox) ((BorderPane) anchorParent.getChildren().get(i)).getChildren().get(0)).getChildren().get(0);
                    Label labelAvailable = (Label) ((VBox) ((BorderPane) anchorParent.getChildren().get(i)).getChildren().get(0)).getChildren().get(1);
                    anchorParent.getChildren().get(i).setVisible(true);
                    addToLabel(labelTitle, labelAvailable, ((page*4)+i));
            }
            else anchorParent.getChildren().get(i).setVisible(false);
        }
        setAvailability();
        checkPageButtons();
    }

   public void previousPage(){
        setInvisible();
        page--;
        for (int i=0;(i<4);i++){
            if (((page*4)+i) < votings.size()) {
                   Label labelTitle = (Label) ((VBox) ((BorderPane) anchorParent.getChildren().get(i)).getChildren().get(0)).getChildren().get(0);
                   Label labelAvailable = (Label) ((VBox) ((BorderPane) anchorParent.getChildren().get(i)).getChildren().get(0)).getChildren().get(1);
                   anchorParent.getChildren().get(i).setVisible(true);
                   addToLabel(labelTitle, labelAvailable, (page*4)+i);
            }
            else anchorParent.getChildren().get(i).setVisible(false);
        }
        setAvailability();
        checkPageButtons();
    }

    private void checkPageButtons(){
        if ((page-1) < 0) {
            previousPageButton.setDisable(true);
            previousPageButton.setOpacity(0.5);
        }
        else {
            previousPageButton.setDisable(false);
            previousPageButton.setOpacity(1);
        }
        if ((page+1)<totalPages){
            nextPageButton.setDisable(false);
            nextPageButton.setOpacity(1);
        }
        else {
            nextPageButton.setDisable(true);
            nextPageButton.setOpacity(0.5);
        }
    }


    private void setInvisible(){
        for (int i=0;i<4;i++) {
            anchorParent.getChildren().get(i).setVisible(false);
        }
    }

    void setAvailability() {
        for (int i = 0; i < 4; i++) {
            if (i<(votings.size()-(page*4))) {
                BorderPane pollButton = (BorderPane) anchorParent.getChildren().get(i);
                if (checkIfAvailable((page * 4) + i)) {
                    pollButton.setDisable(false);
                    pollButton.setOpacity(1);
                } else {
                    pollButton.setDisable(true);
                    pollButton.setOpacity(0.5);
                }
            }
        }
    }

    private boolean checkIfAvailable(int i){
      LocalDate today = timeFlow.getDate();
      if (((today.isAfter(votings.getVoting(i).getDateFrom()) && today.isBefore(votings.getVoting(i).getDateTo()))) || (today.isEqual(votings.getVoting(i).getDateFrom())) || (today.isEqual(votings.getVoting(i).getDateTo()))){
          return true;
      }
     else return false;
    }

    private void addToLabel(Label labelTitle, Label labelAvailable, int index){
        labelTitle.setText(votings.getVoting(index).getTitle());
        String availability = "Available from "+votings.getVoting(index).getDateFrom().format(format)+" - "+votings.getVoting(index).getDateTo().format(format);
        labelAvailable.setText(availability);

    }

    public void nextDay(){
        int thisMonth = timeFlow.getDate().getMonthValue();
        timeFlow.next();
        dateLabel.setText("Today: " +timeFlow.toString());
        setAvailability();
        if (timeFlow.getDate().getMonthValue() > thisMonth){
            if (currentUsr.getThisMonthVotings() > 0) {
                Warning.showAlert("It's a new month! New fresh start!\n\n"
                                + "Last month you completed " + currentUsr.getThisMonthVotings() + " voting/s and totally you have completed "
                                + currentUsr.getCompletedVotings() + " voting/s! \nKeep rockin'! "
                        , 400);
            }
            else {
                Warning.showAlert("It's a new month! New fresh start!\n\n"
                                + "It's a pity, but you didn't complete any voting last month. \nThough, totally you have completed "
                                + currentUsr.getCompletedVotings() + " voting/s! \nGood luck, and don't forget to vote :)"
                        , 400);
            }
            currentUsr.setThisMonthVotings(0);
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
            Warning.showAlert("You have been successfully logged out");

        } catch (IOException e){
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

    public void openPoll(int pane){
        int index = (page*4)+pane;
        if (!( votings.getVoting(index).votedAlready(currentUsr.getEmail()) )) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../View/votingPoll.fxml"));
                fxmlLoader.setController(new votingPollController(votings.getVoting(index), currentUsr.getEmail(), timeFlow.toString(),index));
                Parent root = (Parent) fxmlLoader.load();
                Stage currentStage = (Stage) pollButton1.getScene().getWindow();

                Stage stage = new Stage();
                stage.initStyle(StageStyle.UNDECORATED);
                stage.setTitle("E-vote");
                stage.setScene(new Scene(root, 1024, 768));
                stage.show();
                currentStage.close();

            } catch (IOException e) {
                e.printStackTrace();
                System.out.println(e.getMessage());
            }
        }
        else {
            Warning.showAlert("You already completed this voting. One user may vote for each voting only once.");
            return;
        }
    }






}
