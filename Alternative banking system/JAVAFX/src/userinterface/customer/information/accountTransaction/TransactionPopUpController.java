package userinterface.customer.information.accountTransaction;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class TransactionPopUpController {

    //JavaFX components
    @FXML private Button confirmButton;
    @FXML private Label errorMessage;
    @FXML private AnchorPane transactionPopUpAP;
    @FXML private Label messageButton;
    @FXML private TextField textField;
    private Stage popUpStage;

    private Scene popUpScene;

    public TransactionPopUpController() {
        popUpStage = new Stage();
    }




    //Regular Fields
    private AccountTransactionController accountTransactionController;

    //Getters
    public Label getMessageButton() {return messageButton;}
    public AnchorPane getTransactionPopUpAP() {return transactionPopUpAP;}

    //Setters
    public void setMessageButton(String Message) {
        messageButton.setText(Message);
    }


    //Regular Methods
    public void setPopUp(Stage primaryStage, String message, boolean popUpExist){
        if(!popUpExist) {
            popUpStage.initModality(Modality.WINDOW_MODAL);
            popUpStage.initOwner(primaryStage);
        }
        messageButton.setText(message);
        popUpStage.show();
    }

    public void setPopUpScene(){
        popUpScene = new Scene(transactionPopUpAP,350,160);
        popUpStage.setScene(popUpScene);
    }
    public void setAccountTransactionController(AccountTransactionController accountTransactionController) {
        this.accountTransactionController = accountTransactionController;
    }

    public AccountTransactionController getAccountTransactionController() {
        return accountTransactionController;
    }

    @FXML
    void confirmButtonSetOnAction(ActionEvent event) {

    }
}
