package userinterface.customer.payments;

import database.Engine;
import database.client.PaymentNotification;
import exceptions.accountexception.NotEnoughMoneyInAccount;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import objects.loans.ActiveRiskLoanDTO;
import objects.loans.payments.PaymentNotificationDTO;
import org.controlsfx.control.Notifications;
import userinterface.customer.TopCustomerController;
import userinterface.table.loantable.ActiveLoanTableController;
import userinterface.table.loantable.RiskLoanTableController;

import java.util.List;
import java.util.stream.Collectors;

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
    @FXML private TextField paymentAmountTextField;
    @FXML private Label paymentAmountLabel;
    @FXML private AnchorPane notificationsAP;
    @FXML private Label notificationsTitle;
    @FXML private TableView<PaymentNotificationDTO> notificationsTableView;
    @FXML private TableColumn<PaymentNotificationDTO, String> loanIDNotification;
    @FXML private TableColumn<PaymentNotificationDTO, Integer> YAZNotification;
    @FXML private TableColumn<PaymentNotificationDTO, Double> SumNotification;


    //Regular Fields
    private TopCustomerController topCustomerController;
    private Engine engine;
    @FXML
    private void initialize() {
        loanIDNotification.setCellValueFactory(new PropertyValueFactory<>("loanID"));
        YAZNotification.setCellValueFactory(new PropertyValueFactory<>("paymentYaz"));
        SumNotification.setCellValueFactory(new PropertyValueFactory<>("sumOfPayment"));
         makePaymentTabPane.getSelectionModel().selectedItemProperty().addListener((ov, oldTab, newTab) -> {
             makePaymentActiveTableController.getTableView().getSelectionModel().clearSelection();
             makePaymentRiskTableController.getTableView().getSelectionModel().clearSelection();
             paymentAmountLabel.setVisible(!paymentAmountLabel.isVisible());
             paymentAmountTextField.clear();
             paymentAmountTextField.setVisible(!paymentAmountTextField.isVisible());

        });
    }
    public void setControllersAndStages(){
        closeLoanActiveTableController.setPaymentsTabController(this);
        closeLoanActiveTableController.setPrimaryStage(topCustomerController.getMainController().getPrimaryStage());
        closeLoanRiskTableController.setPaymentsTabController(this);
        closeLoanRiskTableController.setPrimaryStage(topCustomerController.getMainController().getPrimaryStage());
        makePaymentActiveTableController.setPaymentsTabController(this);
        makePaymentActiveTableController.setPrimaryStage(topCustomerController.getMainController().getPrimaryStage());
        makePaymentRiskTableController.setPaymentsTabController(this);
        makePaymentRiskTableController.setPrimaryStage(topCustomerController.getMainController().getPrimaryStage());
    }

    //Getters
    public TopCustomerController getTopCustomerController() {
        return topCustomerController;
    }

    //Setters
    public void setTopCustomerController(TopCustomerController topCustomerController) {
        this.topCustomerController = topCustomerController;
    }

    public void setValues(List<PaymentNotificationDTO> paymentNotifications, List<ActiveRiskLoanDTO> makePaymentActive, List<ActiveRiskLoanDTO> makePaymentRisk,List<ActiveRiskLoanDTO> closeLoanActive, List<ActiveRiskLoanDTO> closeLoanRisk){
        ObservableList<PaymentNotificationDTO> PaymentNotificationDTOObservableList = FXCollections.observableList(paymentNotifications);
        notificationsTableView.getItems().setAll(PaymentNotificationDTOObservableList);

        ObservableList<ActiveRiskLoanDTO> closeLoansRisk = FXCollections.observableList(closeLoanRisk);
        closeLoanRiskTableController.setValues(closeLoansRisk);

        ObservableList<ActiveRiskLoanDTO> makePaymentsActive = FXCollections.observableList(makePaymentActive);
        makePaymentActiveTableController.setValues(makePaymentsActive);

        ObservableList<ActiveRiskLoanDTO> makePaymentsRisk = FXCollections.observableList(makePaymentRisk);
        makePaymentRiskTableController.setValues(makePaymentsRisk);

        ObservableList<ActiveRiskLoanDTO> closeLoansActive = FXCollections.observableList(closeLoanActive);
        closeLoanActiveTableController.setValues(closeLoansActive);
    }

    public void setEngine(Engine engine) {
        this.engine = engine;
    }

    //Regular methods
    @FXML
    public void completePaymentOnAction(ActionEvent actionEvent) {
        try {
            ActiveRiskLoanDTO selectedItem = null;
            if (makePaymentActiveTableController.getTableView().getSelectionModel().getSelectedItem() != null) {
                selectedItem = makePaymentActiveTableController.getTableView().getSelectionModel().getSelectedItem();
                engine.makeActivePayment(selectedItem.getLoanID(),selectedItem.getBorrowerName());
                topCustomerController.updatePayments(selectedItem.getBorrowerName());
                topCustomerController.updateInformationTab(selectedItem.getBorrowerName());
                return;
            }
            if (makePaymentRiskTableController.getTableView().getSelectionModel().getSelectedItem() != null) {
                selectedItem = makePaymentRiskTableController.getTableView().getSelectionModel().getSelectedItem();
                try {
                    String amount = paymentAmountTextField.getText();
                    Double Amount = Double.parseDouble(amount);
                    if(Amount <= 0){
                        throw new Exception();
                    }
                    engine.makeRiskPayment(selectedItem.getLoanID(), selectedItem.getBorrowerName(),Amount);
                    topCustomerController.updatePayments(selectedItem.getBorrowerName());
                    topCustomerController.updateInformationTab(selectedItem.getBorrowerName());
                    completePaymentError.setText("Payment completed Successfully!");
                    completePaymentError.setTextFill(Color.GREEN);
                } catch (NumberFormatException e) {
                    completePaymentError.setText("Invalid input!");
                }catch (Exception e){
                    completePaymentError.setText("Please enter a positive number!");
                }
                return;
            }
            if(selectedItem == null){
                throw new Exception();//user didn't select
            }
        } catch (NotEnoughMoneyInAccount e){
            Notifications notEnoughMoney = Notifications.create().title("Error").text(e.toString()).hideAfter(Duration.seconds(10)).position(Pos.CENTER);
            notEnoughMoney.show();
        } catch(Exception e){
            completePaymentError.setText("No loan has been selected!");
        }
    }

    public void setValues(List<PaymentNotificationDTO> paymentNotifications, List<ActiveRiskLoanDTO> makePaymentActive, List<ActiveRiskLoanDTO> makePaymentRisk,List<ActiveRiskLoanDTO> closeLoanActive, List<ActiveRiskLoanDTO> closeLoanRisk){
        ObservableList<PaymentNotificationDTO> PaymentNotificationDTOObservableList = FXCollections.observableList(paymentNotifications);
        notificationsTableView.getItems().setAll(PaymentNotificationDTOObservableList);



        ObservableList<ActiveRiskLoanDTO> closeLoansRisk = FXCollections.observableList(closeLoanRisk);
        closeLoanRiskTableController.setValues(closeLoansRisk);

        ObservableList<ActiveRiskLoanDTO> makePaymentsActive = FXCollections.observableList(makePaymentActive);
        makePaymentActiveTableController.setValues(makePaymentsActive);

        ObservableList<ActiveRiskLoanDTO> makePaymentsRisk = FXCollections.observableList(makePaymentRisk);
        makePaymentRiskTableController.setValues(makePaymentsRisk);

        ObservableList<ActiveRiskLoanDTO> closeLoansActive = FXCollections.observableList(closeLoanActive);
        closeLoanActiveTableController.setValues(closeLoansActive);
    }

    public void setEngine(Engine engine) {
        this.engine = engine;
    }

    //Regular methods
    @FXML
    public void completePaymentOnAction(ActionEvent actionEvent) {
        try {
            ActiveRiskLoanDTO selectedItem = null;
            if (makePaymentActiveTableController.getTableView().getSelectionModel().getSelectedItem() != null) {
                selectedItem = makePaymentActiveTableController.getTableView().getSelectionModel().getSelectedItem();
                engine.makeActivePayment(selectedItem.getLoanID(),selectedItem.getBorrowerName());
                topCustomerController.updatePayments(selectedItem.getBorrowerName());
                topCustomerController.updateInformationTab(selectedItem.getBorrowerName());
                return;
            }
            if (makePaymentRiskTableController.getTableView().getSelectionModel().getSelectedItem() != null) {
                selectedItem = makePaymentRiskTableController.getTableView().getSelectionModel().getSelectedItem();
                try {
                    String amount = paymentAmountTextField.getText();
                    Double Amount = Double.parseDouble(amount);
                    if(Amount <= 0){
                        throw new Exception();
                    }
                    engine.makeRiskPayment(selectedItem.getLoanID(), selectedItem.getBorrowerName(),Amount);
                    topCustomerController.updatePayments(selectedItem.getBorrowerName());
                    topCustomerController.updateInformationTab(selectedItem.getBorrowerName());
                } catch (NumberFormatException e) {
                    completePaymentError.setText("Invalid input!");
                }catch (Exception e){
                    completePaymentError.setText("Please enter a positive number!");
                }
                return;
            }
            if(selectedItem == null){
                throw new Exception();//user didn't select
            }
        } catch (NotEnoughMoneyInAccount e){
            Notifications notEnoughMoney = Notifications.create().title("Error").text(e.toString()).hideAfter(Duration.seconds(10)).position(Pos.CENTER);
            notEnoughMoney.show();
        } catch(Exception e){
            completePaymentError.setText("No loan has been selected!");
        }
    }
}
