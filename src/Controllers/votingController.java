package Controllers;

import Models.Other.Days;
import Models.Other.Warning;
import Models.Voting.Poll;
import Models.Voting.PollDatabase;
import Models.Voting.Voting;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class votingController {

    @FXML Label account;
    @FXML Label dayLabel;
    @FXML AnchorPane anchorParent;



    private String username;

    private Days week = new Days();
    private PollDatabase votings;

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
        week.setToday(1);
        dayLabel.setText("Today: "+week.getTodayFullName());

        //Votings initialization
        votings = new PollDatabase("\\src\\Data\\PollData.evdb");
        votings.loadDatabase();


        // HARDCODE SET   //TODO DELETE IN FINAL VERSION AFTER FILE DATABASE LOAD
        List<Integer> test1 = Arrays.asList(1,2,3,5);
        List<Integer> test2 = Arrays.asList(1,6);
        List<Integer> test3 = Arrays.asList(6,7);
        List<Integer> test4= Arrays.asList(4,5);

        List<Poll> polls = new ArrayList<>();
        polls.add( new Poll("text", "Which one?", "Donald Trump", "Barrack Obama"));
        votings.addVoting(new Voting("Presidents", 1, polls , test1));
        votings.addVoting(new Voting("Sweets",1,polls,test2));
        votings.addVoting(new Voting("Lecturers",1,polls,test3));
        votings.addVoting(new Voting("Girls",1,polls,test4));



        //filling labels
        for (int i=0;(i<votings.size());i++){
            Label labelTitle = (Label)((VBox)((BorderPane) anchorParent.getChildren().get(i)).getChildren().get(0)).getChildren().get(0);
            Label labelAvailable = (Label)((VBox)((BorderPane) anchorParent.getChildren().get(i)).getChildren().get(0)).getChildren().get(1);
            addToLabel(labelTitle,labelAvailable,i);

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
        int today = week.getToday();
        if (votings.getVoting(i).getDaysAvailable().contains(today)) {
            return true;
        }
        else return false;
    }

    private void addToLabel(Label labelTitle,Label labelAvailable,int index){
        labelTitle.setText(votings.getVoting(index).getTitle());
        String availability = "Available on ";
        for (int i=0;i<votings.getVoting(index).getDaysAvailable().size();i++){
            availability = availability.concat(" "+week.getDayAbbr(votings.getVoting(index).getDaysAvailable().get(i)));
            if (!(i == votings.getVoting(index).getDaysAvailable().size()-1)){
               availability = availability.concat(",");
            }
        }
        labelAvailable.setText(availability);

    }

    public void nextDay(){
        if (week.getToday()==7) {
            week.setToday(1);
        }
        else {
            week.setToday((week.getToday() + 1));
        }
        dayLabel.setText("Today: " + week.getTodayFullName());
        setAvailability();
    }

    public void closeApp(){ //TODO
        Warning.showConfirmAlert("Do you really want to exit? You will be logged out automatically");
        //System.exit(0);
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




    public void demo(){
        Warning.showAlert("Sorry! This function is not available in demo version");
    }

}
