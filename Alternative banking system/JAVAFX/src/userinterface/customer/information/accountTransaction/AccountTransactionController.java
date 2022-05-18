package userinterface.customer.information.accountTransaction;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import objects.customers.AccountTransactionDTO;
import objects.customers.CustomerInfoDTO;
import userinterface.admin.TopAdminController;
import userinterface.customer.information.InformationTabController;

import java.io.IOException;
import java.net.URL;
import java.util.List;

public class AccountTransactionController {


    //SubComponents
    private TransactionPopUpController popUpController;
    private String MESSAGE;
    private boolean popUpExist = false;
    //JavaFX components
    @FXML private AnchorPane accountTransactionAP;
    @FXML private HBox accountTransactionHB;
    @FXML private Button charge;
    @FXML private Button withdraw;
    @FXML private TableView<AccountTransactionDTO> tableView;
    @FXML private TableColumn<AccountTransactionDTO, Double> balanceAfter;
    @FXML private TableColumn<AccountTransactionDTO, Double> balanceBefore;
    @FXML private TableColumn<AccountTransactionDTO, Integer> timeOfTransaction;
    @FXML private TableColumn<AccountTransactionDTO, Double> transactionAmount;



    public InformationTabController getInformationTabController() {
        return informationTabController;
    }

    //Regular Fields
    private InformationTabController informationTabController;

    @FXML
    private void initialize() throws Exception{
        FXMLLoader loaderPopUp = new FXMLLoader();
        URL popUpFXML = getClass().getResource("/userinterface/customer/information/accountTransaction/transactionPopUp.fxml");
        loaderPopUp.setLocation(popUpFXML);
        Parent root1 = loaderPopUp.load();
        popUpController = loaderPopUp.getController();
        MESSAGE = popUpController.getMessageButton().getText();
        popUpController.setAccountTransactionController(this);
        popUpController.setPopUpScene();

        timeOfTransaction.setCellValueFactory(new PropertyValueFactory<AccountTransactionDTO, Integer>("timeOfTransaction"));
        balanceBefore.setCellValueFactory(new PropertyValueFactory<AccountTransactionDTO, Double>("balanceBefore"));
        balanceAfter.setCellValueFactory(new PropertyValueFactory<AccountTransactionDTO, Double>("balanceAfter"));
        transactionAmount.setCellValueFactory(new PropertyValueFactory<>("incomeOrExpense" + "transactionAmount"));
    }

    //Getters

    //Setters
    public void setInformationTabController(InformationTabController informationTabController) {
        this.informationTabController = informationTabController;
    }

    //Regular methods
    @FXML
    void chargeButtonOnAction(ActionEvent event) {
        popUpController.setPopUp(informationTabController.getTopCustomerController().getMainController().getPrimaryStage(), MESSAGE + "charge:", popUpExist );
        if(!popUpExist)
            popUpExist = true;
    }

    @FXML
    void withdrawButtonOnAction(ActionEvent event) {
        popUpController.setPopUp(informationTabController.getTopCustomerController().getMainController().getPrimaryStage(), MESSAGE + "withdraw:", popUpExist );
        if(!popUpExist)
            popUpExist = true;
    }

    public void setTableValues(CustomerInfoDTO customer){
        ObservableList<AccountTransactionDTO>  customerTransactions= FXCollections.observableArrayList(customer.getTransactionDTOS());
        tableView.getItems().setAll(customerTransactions);
    }

}
