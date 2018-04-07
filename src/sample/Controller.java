package sample;


import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;

public class Controller implements Initializable {
 @FXML
    private void handleClose(ActionEvent event) {
     System.exit(0);
 }

 @Override
    public void initialize(URL url, ResourceBundle rb){
     // TODO
 }


}
