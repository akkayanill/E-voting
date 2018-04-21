package Controllers;

import Models.Other.TimeFlow;
import Models.Other.Warning;
import Models.Voting.PollDatabase;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class votingController {

    @FXML Label account;
    @FXML Label dateLabel;
    @FXML AnchorPane anchorParent;



    private String username;
    private TimeFlow timeFlow = new TimeFlow();
    private PollDatabase votings;
    private DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy");


    public votingController(String username){
        this.username = username;
    }

    /**
     * Loads data on stage start
     */
    @FXML
    private void initialize(){
        account.setText(username);

        //Time flow initialization
        dateLabel.setText("Today: "+timeFlow.toString());

        //Votings initialization
        votings = new PollDatabase("/src/Data/PollData.csv");
        votings.loadDatabase();

        //filling labels
        for (int i=0;(i<votings.size());i++){
            Label labelTitle = (Label)((VBox)((BorderPane) anchorParent.getChildren().get(i)).getChildren().get(0)).getChildren().get(0);
            Label labelAvailable = (Label)((VBox)((BorderPane) anchorParent.getChildren().get(i)).getChildren().get(0)).getChildren().get(1);
            addToLabel(labelTitle, labelAvailable, i);
        }

        setAvailability();
    }

    void setAvailability() {
        for (int i = 0; (i < votings.size()); i++) {
            BorderPane pollButton = (BorderPane) anchorParent.getChildren().get(i);
            if (checkifAvailable(i)) {
                pollButton.setDisable(false);
                pollButton.setOpacity(1);
            }
            else {
                pollButton.setDisable(true);
                pollButton.setOpacity(0.5);
            }

        }
    }

   private boolean checkifAvailable(int i){
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
        timeFlow.next();
        dateLabel.setText("Today: " +timeFlow.toString());
        setAvailability();
    }

    public void closeApp(){
        Warning.showConfirmAlert("Do you really want to exit? You will be logged out automatically");
    }

    public void logOut(){
            try{
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/View/login.fxml"));
                Parent root = (Parent) fxmlLoader.load();
                Stage stage = (Stage) account.getScene().getWindow();
                stage.setTitle("E-vote - Log In");
                stage.setScene(new Scene(root));
                stage.show();
                Warning.showAlert("You have successfully logged out");

            } catch (IOException e){
                e.printStackTrace();
                System.out.println(e.getMessage());
            }
    }

}
