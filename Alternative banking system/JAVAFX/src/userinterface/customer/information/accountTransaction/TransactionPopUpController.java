package userinterface.customer.information.accountTransaction;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class TransactionPopUpController {

    //Constants


    //JavaFX components
    @FXML private Button confirmButton;
    @FXML private Label errorMessage;
    @FXML private Label messageButton;

    //Getters
    public Label getMessageButton() {return messageButton;}
    public AnchorPane getTransactionPopUpAP() {return transactionPopUpAP;}

    //Setters
    public void setMessageButton(String Message) {
        messageButton.setText(Message);
    }



    @FXML
    private TextField textField;

    @FXML
    private AnchorPane transactionPopUpAP;

    @FXML
    void confirmButtonSetOnAction(ActionEvent event) {

    }
}
