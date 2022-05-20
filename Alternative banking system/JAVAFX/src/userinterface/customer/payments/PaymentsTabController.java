package userinterface.customer.payments;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import userinterface.customer.TopCustomerController;
import userinterface.table.loantable.ActiveLoanTableController;
import userinterface.table.loantable.RiskLoanTableController;

public class PaymentsTabController {

    //Sub components
    @FXML private ScrollPane closeLoanActiveTable;
    @FXML private ActiveLoanTableController closeLoanActiveTableController;

    @FXML private ScrollPane closeLoanRiskTable;
    @FXML private RiskLoanTableController closeLoanRiskTableController;

    @FXML private ScrollPane makePaymentActiveTable;
    @FXML private ActiveLoanTableController makePaymentActiveTableController;

    @FXML private ScrollPane makePaymentRiskTable;
    @FXML private RiskLoanTableController makePaymentRiskTableController;


    //JavaFX components
    @FXML private Button closeLoanButton;
    @FXML private Label closeLoanError;

    @FXML private Button completePaymentButton;
    @FXML private Label completePaymentError;

    @FXML private AnchorPane paymentsTabAP;
    @FXML private TabPane closeOrPaymentTabPane;
    @FXML private Tab closeLoanTab;
    @FXML private AnchorPane closeLoanAP;
    @FXML private TabPane closeLoanTabPane;
    @FXML private Tab closeLoanActiveTab;
    @FXML private Tab closeLoanRiskTab;
    @FXML private HBox closeButtonAndErrorHB;
    @FXML private Tab makePaymentTab;
    @FXML private AnchorPane makePaymentAP;
    @FXML private TabPane makePaymentTabPane;
    @FXML private Tab makePaymentActiveTab;
    @FXML private Tab makePaymentRiskTab;
    @FXML private HBox makePaymentButtonAndErrorHB;
    @FXML private AnchorPane notificationsAP;
    @FXML private Label notificationsTitle;
    @FXML private TableView notificationsTableView;
    @FXML private TableColumn loanIDNotification;
    @FXML private TableColumn YAZNotification;
    @FXML private TableColumn SumNotification;

    //Regular Fields
    private TopCustomerController topCustomerController;


    //Setters
    public void setTopCustomerController(TopCustomerController topCustomerController) {
        this.topCustomerController = topCustomerController;
    }
}