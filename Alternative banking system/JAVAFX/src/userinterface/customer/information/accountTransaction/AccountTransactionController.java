package userinterface.customer.information.accountTransaction;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import userinterface.admin.TopAdminController;

import java.io.IOException;
import java.net.URL;

public class AccountTransactionController {


    //SubComponents
    private TransactionPopUpController popUpController;
    private String MESSAGE;

    //JavaFX components
    @FXML private AnchorPane accountTransactionAP;
    @FXML private HBox accountTransactionHB;
    @FXML private TableColumn<?, ?> balanceAfter;
    @FXML private TableColumn<?, ?> balanceBefore;
    @FXML private Button charge;
    @FXML private TableView<?> tableView;
    @FXML private TableColumn<?, ?> timeOfTransaction;
    @FXML private TableColumn<?, ?> transactionAmount;
    @FXML private Button withdraw;



    @FXML
    private void initialize()  {
        FXMLLoader loaderPopUp = new FXMLLoader();
        URL popUpFXML = getClass().getResource("/userinterface/customer/information/accountTransaction/transactionPopUp.fxml");
        loaderPopUp.setLocation(popUpFXML);
        Parent root1 = null;
        try {
            root1 = loaderPopUp.load();
        } catch (IOException e) {

        }
        popUpController = loaderPopUp.getController();
        MESSAGE = popUpController.getMessageButton().getText();
    }


    //Regular methods
    @FXML
    void chargeButtonOnAction(ActionEvent event) {
        Stage popUpStage = new Stage();
        Scene popUpScene = new Scene(popUpController.getTransactionPopUpAP(),350, 160);
        popUpController.setMessageButton(MESSAGE + "charge");
        popUpStage.setScene(popUpScene);
        popUpStage.show();
    }

    @FXML
    void withdrawButtonOnAction(ActionEvent event) {

    }

}
