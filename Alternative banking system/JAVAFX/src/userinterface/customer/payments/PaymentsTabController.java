package userinterface.customer.payments;

import customercomponents.customerscreen.CustomerScreenController;
import database.Engine;
import exceptions.accountexception.NotEnoughMoneyInAccount;
import exceptions.accountexception.WithDrawMoneyException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.util.Duration;
import objects.loans.ActiveRiskLoanDTO;
import objects.loans.payments.PaymentNotificationDTO;
import org.controlsfx.control.Notifications;
import userinterface.table.loantable.ActiveLoanTableController;
import userinterface.table.loantable.RiskLoanTableController;

import java.util.List;

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
    @FXML private AnchorPane finishImage;
    @FXML private FinishAnimationController finishImageController;


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
    private CustomerScreenController customerScreenController;
    private Engine engine;
    private boolean animation;
    @FXML
    private void initialize() {
        loanIDNotification.setCellValueFactory(new PropertyValueFactory<>("loanID"));
        YAZNotification.setCellValueFactory(new PropertyValueFactory<>("paymentYaz"));
        SumNotification.setCellValueFactory(new PropertyValueFactory<>("sumOfPayment"));
         makePaymentTabPane.getSelectionModel().selectedItemProperty().addListener((ov, oldTab, newTab) -> {
             makePaymentActiveTableController.getTableView().getSelectionModel().clearSelection();
             makePaymentRiskTableController.getTableView().getSelectionModel().clearSelection();
             paymentAmountLabel.setVisible(!paymentAmountLabel.isVisible());
             if(paymentAmountLabel.isVisible()){
                 completePaymentButton.setText("Confirm Payment");
             }else{
                 completePaymentButton.setText("Complete payment for selected loan");
             }
             paymentAmountTextField.clear();
             paymentAmountTextField.setVisible(!paymentAmountTextField.isVisible());
             completePaymentError.setText("");
             finishImage.setVisible(false);
        });
         closeLoanTabPane.getSelectionModel().selectedItemProperty().addListener((ov, oldTab, newTab) -> {
             closeLoanActiveTableController.getTableView().getSelectionModel().clearSelection();
             closeLoanRiskTableController.getTableView().getSelectionModel().clearSelection();
             closeLoanError.setText("");
             finishImage.setVisible(false);
         });
         closeOrPaymentTabPane.getSelectionModel().selectedItemProperty().addListener((ov,oldTab,newTab)->{
             finishImage.setVisible(false);
         });
    }
//    public void setControllersAndStages(){
//        closeLoanActiveTableController.setPaymentsTabController(this);
//        closeLoanActiveTableController.setPrimaryStage(topCustomerController.getMainController().getPrimaryStage());
//        closeLoanRiskTableController.setPaymentsTabController(this);
//        closeLoanRiskTableController.setPrimaryStage(topCustomerController.getMainController().getPrimaryStage());
//        makePaymentActiveTableController.setPaymentsTabController(this);
//        makePaymentActiveTableController.setPrimaryStage(topCustomerController.getMainController().getPrimaryStage());
//        makePaymentRiskTableController.setPaymentsTabController(this);
//        makePaymentRiskTableController.setPrimaryStage(topCustomerController.getMainController().getPrimaryStage());
//    }

    //Getters
    public CustomerScreenController getTopCustomerController() {
        return customerScreenController;
    }

    public AnchorPane getFinishImage() {
        return finishImage;
    }

    //Setters
    public void setCustomerScreenController(CustomerScreenController customerScreenController) {
        this.customerScreenController = customerScreenController;
    }

    public void setAnimation(boolean animation) {
        this.animation = animation;
    }

    public void setValues(List<PaymentNotificationDTO> paymentNotifications, List<ActiveRiskLoanDTO> makePaymentActive, List<ActiveRiskLoanDTO> riskLoans, List<ActiveRiskLoanDTO> closeLoanActive){
        ObservableList<PaymentNotificationDTO> PaymentNotificationDTOObservableList = FXCollections.observableList(paymentNotifications);
        notificationsTableView.getItems().setAll(PaymentNotificationDTOObservableList);

        closeLoanRiskTableController.setValues(riskLoans);

        makePaymentActiveTableController.setValues(makePaymentActive);

        makePaymentRiskTableController.setValues(riskLoans);

        closeLoanActiveTableController.setValues(closeLoanActive);
        completePaymentError.setText("");

    }

    public void setEngine(Engine engine) {
        this.engine = engine;
    }

    //Regular methods
    @FXML
    public void completePaymentOnAction(ActionEvent actionEvent) {
//        try {
//            ActiveRiskLoanDTO selectedItem = null;
//            if (makePaymentActiveTableController.getTableView().getSelectionModel().getSelectedItem() != null) {
//                selectedItem = makePaymentActiveTableController.getTableView().getSelectionModel().getSelectedItem();
//                engine.makeActivePayment(selectedItem.getLoanID(),selectedItem.getBorrowerName());
//                topCustomerController.updatePayments(selectedItem.getBorrowerName());
//                topCustomerController.updateInformationTab(selectedItem.getBorrowerName());
//                Notifications success = Notifications.create().text("Payment completed Successfully!").hideAfter(Duration.seconds(5)).position(Pos.CENTER);
//                success.show();
//                if(engine.getLoanByName(selectedItem.getLoanID()).getStatus().getStatus().equals("Finished") && animation) {
//                    animateLoanFinish();
//                }
//                return;
//            }
//            if (makePaymentRiskTableController.getTableView().getSelectionModel().getSelectedItem() != null) {
//                selectedItem = makePaymentRiskTableController.getTableView().getSelectionModel().getSelectedItem();
//                try {
//                    String amount = paymentAmountTextField.getText();
//                    Double Amount = Double.parseDouble(amount);
//                    if(Amount <= 0){
//                        throw new Exception();
//                    }
//                    engine.makeRiskPayment(selectedItem.getLoanID(), selectedItem.getBorrowerName(),Amount);
//                    topCustomerController.updatePayments(selectedItem.getBorrowerName());
//                    topCustomerController.updateInformationTab(selectedItem.getBorrowerName());
//                    Notifications success = Notifications.create().text("Payment completed Successfully!").hideAfter(Duration.seconds(5)).position(Pos.CENTER);
//                    success.show();
//                    if(engine.getLoanByName(selectedItem.getLoanID()).getStatus().getStatus().equals("Finished") && animation) {
//                        animateLoanFinish();
//                    }
//                } catch (NumberFormatException e) {
//                    completePaymentError.setText("Invalid input!");
//                }catch (Exception e){
//                    completePaymentError.setText("Please enter a positive number!");
//                }
//                return;
//            }
//            if(selectedItem == null){
//                throw new Exception();//user didn't select
//            }
//        } catch (NotEnoughMoneyInAccount e){
//            Notifications notEnoughMoney = Notifications.create().title("Error").text(e.toString()).hideAfter(Duration.seconds(10)).position(Pos.CENTER);
//            notEnoughMoney.showError();
//        } catch(Exception e){
//            completePaymentError.setText("No loan has been selected!");
//        }
    }

    @FXML
    public void closeLoanOnAction(ActionEvent actionEvent) {
        try{
            ActiveRiskLoanDTO selectedItem = null;
            if (closeLoanActiveTableController.getTableView().getSelectionModel().getSelectedItem() != null) {
                selectedItem = closeLoanActiveTableController.getTableView().getSelectionModel().getSelectedItem();
            }
            if (closeLoanRiskTableController.getTableView().getSelectionModel().getSelectedItem() != null) {
                selectedItem = closeLoanRiskTableController.getTableView().getSelectionModel().getSelectedItem();
            }
            if(selectedItem == null){
                throw new Exception();//user didn't select
            }
            customerScreenController.closeLoan(selectedItem.getLoanID());
//            topCustomerController.updatePayments(selectedItem.getBorrowerName());
//            topCustomerController.updateInformationTab(selectedItem.getBorrowerName());

//            Notifications completedLoan = Notifications.create().title("Success").text("The loan was successfully closed!").hideAfter(Duration.seconds(10)).position(Pos.CENTER);
//            completedLoan.show();
//            if(engine.getLoanByName(selectedItem.getLoanID()).getStatus().getStatus().equals("Finished") && animation) {
//                animateLoanFinish();
//            }

//        } catch (WithDrawMoneyException e) {
//            Notifications notEnoughMoney = Notifications.create().title("Error").text(e.toString()).hideAfter(Duration.seconds(10)).position(Pos.CENTER);
//            notEnoughMoney.showError();
        } catch (Exception e) {
            closeLoanError.setText("No loan has been selected!");
        }
    }
    public void animateLoanFinish(){
        finishImage.setVisible(true);
        finishImageController.setAnimation();
    }
}



