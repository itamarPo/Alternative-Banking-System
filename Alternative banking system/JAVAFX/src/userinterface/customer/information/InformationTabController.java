package userinterface.customer.information;

import database.Engine;
import database.client.AccountTransaction;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import userinterface.customer.TopCustomerController;
import userinterface.customer.information.accountTransaction.AccountTransactionController;
import userinterface.table.loantable.*;


public class InformationTabController {

    //Sub components
    @FXML private ScrollPane newLoanerTable;
    @FXML private NewLoanTableController newLoanerTableController;
    @FXML private ScrollPane pendingLoanerTable;
    @FXML private PendingLoanTableController pendingLoanerTableController;
    @FXML private ScrollPane activeLoanerTable;
    @FXML private ActiveLoanTableController activeLoanerTableController;
    @FXML private ScrollPane riskLoanerTable;
    @FXML private RiskLoanTableController riskLoanerTableController;
    @FXML private ScrollPane finishedLoaderTable;
    @FXML private FinishedLoanTableController finishedLoaderTableController;

    @FXML private ScrollPane pendingLenderTable;
    @FXML private PendingLoanTableController pendingLenderTableController;
    @FXML private ScrollPane activeLenderTable;
    @FXML private ActiveLoanTableController activeLenderTableController;
    @FXML private ScrollPane riskLenderTable;
    @FXML private RiskLoanTableController riskLenderTableController;
    @FXML private ScrollPane finishedLenderTable;
    @FXML private FinishedLoanTableController finishedLenderTableController;

    @FXML private AnchorPane transactionInfo;
    @FXML private AccountTransactionController transactionInfoController;

    //JavaFX components
    @FXML private AnchorPane informationAP;
    @FXML private ScrollPane informationSP;
    @FXML private BorderPane informationBP;
    @FXML private TabPane loanerTB;
    @FXML private Tab newLoanerTab;
    @FXML private Tab pendingLoanerTab;
    @FXML private Tab activeLoanerTab;
    @FXML private Tab riskLoanerTab;
    @FXML private Tab finishedLoanerTab;
    @FXML private TabPane lenderTB;
    @FXML private Tab newLenderTab;
    @FXML private Tab pendingLenderTab;
    @FXML private Tab activeLenderTab;
    @FXML private Tab riskLenderTab;
    @FXML private Tab finishedLenderTab;

    //Regular Fields
    private TopCustomerController topCustomerController;
    private Engine engine;
    private String userName;

    public AccountTransactionController getTransactionInfoController() {
        return transactionInfoController;
    }


    public NewLoanTableController getNewLoanerTableController() {
        return newLoanerTableController;
    }

    @FXML
    private void initialize() {
        transactionInfoController.setInformationTabController(this);
    }

    public void setUserName(String userName) {
        this.userName = userName;
        transactionInfoController.setUserName(this.userName);
    }

    public TopCustomerController getTopCustomerController() {
        return topCustomerController;
    }

    //Setters
    public void setTopCustomerController(TopCustomerController topCustomerController) {
        this.topCustomerController = topCustomerController;
    }

    public void setEngine(Engine engine){
        this.engine = engine;
        transactionInfoController.setEngine(this.engine);
    }

}
