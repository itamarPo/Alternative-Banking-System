package userinterface.customer.information.accountTransaction;

import database.Engine;
import database.client.Customer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
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
    private Engine engine;

    private String userName;
    boolean chargeOrWithdraw;

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public TransactionPopUpController() {
        popUpStage = new Stage();
        //popUpStage = (Stage)confirmButton.getScene().getWindow();
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

    public void setEngine(Engine engine) {
        this.engine = engine;
    }

    //Regular Methods
    public void setPopUp(Stage primaryStage, String message, boolean popUpExist, boolean chargeOrWithdraw){
        if(!popUpExist) {
           // popUpStage.initModality(Modality.WINDOW_MODAL);
            popUpStage.initOwner(primaryStage);
        }
        messageButton.setText(message);
        popUpStage.show();
        this.chargeOrWithdraw = chargeOrWithdraw;
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
        String userInput = textField.getText();
        Double userSum;
        Customer customer;
        try {
           userSum =  Double.parseDouble(userInput);
           customer = engine.getCustomerByName(userName);
           if(chargeOrWithdraw)
                customer.addMoney(userSum);
           else
               customer.drawMoney(userSum);
           accountTransactionController.getInformationTabController().getBalanceLabel().setText("Balance: " + customer.getBalance());
           errorMessage.setText(null);
           popUpStage.close();
        }
        catch (Exception e){
            errorMessage.setText("Incorrect Input. Please Enter a valid Number");
        }
    }
}
