package userinterface.table.loantable;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import objects.loans.ActiveRiskLoanDTO;
import objects.loans.LenderMap;
import userinterface.table.LendersTableController;
import userinterface.table.PaymentTableController;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RiskLoanTableController {

    private boolean lenderStageExist = false;
    private boolean paymentStageExist = false;
    private PaymentTableController paymentTableController;
    private LendersTableController lendersTableController;
    //JavaFX components
    @FXML private TableView<ActiveRiskLoanDTO> tableView;
    @FXML
    private TableColumn<ActiveRiskLoanDTO, String> loanID;
    @FXML
    private TableColumn<ActiveRiskLoanDTO, String> category;
    @FXML
    private TableColumn<ActiveRiskLoanDTO, String> owner;
    @FXML
    private TableColumn<ActiveRiskLoanDTO, Double> amount;
    @FXML
    private TableColumn<ActiveRiskLoanDTO, Integer> duration;
    @FXML
    private TableColumn<ActiveRiskLoanDTO, Integer> interest;
    @FXML
    private TableColumn<ActiveRiskLoanDTO, Integer> timePerPayment;
    @FXML
    private TableColumn<ActiveRiskLoanDTO, Button> listOfLenders;
    @FXML
    private TableColumn<ActiveRiskLoanDTO, Double> startingActiveTime;
    @FXML
    private TableColumn<ActiveRiskLoanDTO, Integer> nextPaymentTime;
    @FXML
    private TableColumn<ActiveRiskLoanDTO, Button> payments;
    @FXML
    private TableColumn<ActiveRiskLoanDTO, Double> allInitialPayedSoFar;
    @FXML
    private TableColumn<ActiveRiskLoanDTO, Double> allInterestPayedSoFar;
    @FXML
    private TableColumn<ActiveRiskLoanDTO, Double> allInitialLeftToPay;
    @FXML
    private TableColumn<ActiveRiskLoanDTO, Double> allInterestLeftToPay;


    public void initialize() {
        loanID.setCellValueFactory(new PropertyValueFactory<ActiveRiskLoanDTO, String>("loanID"));
        category.setCellValueFactory(new PropertyValueFactory<ActiveRiskLoanDTO, String>("loanCategory"));
        owner.setCellValueFactory(new PropertyValueFactory<ActiveRiskLoanDTO, String>("borrowerName"));
        duration.setCellValueFactory(new PropertyValueFactory<ActiveRiskLoanDTO, Integer>("timeLimitOfLoan"));
        amount.setCellValueFactory(new PropertyValueFactory<ActiveRiskLoanDTO, Double>("sizeNoInterest"));
        interest.setCellValueFactory(new PropertyValueFactory<ActiveRiskLoanDTO, Integer>("interestPerPayment"));
        timePerPayment.setCellValueFactory(new PropertyValueFactory<ActiveRiskLoanDTO, Integer>("timePerPayment"));
        listOfLenders.setCellValueFactory(new PropertyValueFactory<ActiveRiskLoanDTO, Button>("lendersButton"));
        startingActiveTime.setCellValueFactory(new PropertyValueFactory<ActiveRiskLoanDTO, Double>("startingActiveTime"));
        nextPaymentTime.setCellValueFactory(new PropertyValueFactory<ActiveRiskLoanDTO, Integer>("nextPaymentTime"));
        payments.setCellValueFactory(new PropertyValueFactory<ActiveRiskLoanDTO, Button>("paymentsButton"));
        allInitialLeftToPay.setCellValueFactory(new PropertyValueFactory<ActiveRiskLoanDTO, Double>("allInitialLeftToPay"));
        allInitialPayedSoFar.setCellValueFactory(new PropertyValueFactory<ActiveRiskLoanDTO, Double>("allInitialPayedSoFar"));
        allInterestLeftToPay.setCellValueFactory(new PropertyValueFactory<ActiveRiskLoanDTO, Double>("allInterestLeftToPay"));
        allInterestPayedSoFar.setCellValueFactory(new PropertyValueFactory<ActiveRiskLoanDTO, Double>("allInterestPayedSoFar"));
        FXMLLoader loaderlenders = new FXMLLoader();
        URL lendersFXML = getClass().getResource("/userinterface/table/lendersTable.fxml");
        loaderlenders.setLocation(lendersFXML);
        try {
            Parent root1 = loaderlenders.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        lendersTableController = loaderlenders.getController();

        FXMLLoader loaderPayment = new FXMLLoader();
        URL paymentFXML = getClass().getResource("/userinterface/table/paymentTable.fxml");
        loaderPayment.setLocation(paymentFXML);
        try {
            Parent root1 = loaderPayment.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        paymentTableController = loaderPayment.getController();
    }

    public void setValues(List<ActiveRiskLoanDTO> riskList){
        ObservableList<ActiveRiskLoanDTO> activeRiskLoanDTOObservableList = FXCollections.observableList(riskList);
        tableView.getItems().setAll(activeRiskLoanDTOObservableList);
        for(int i=0; i<tableView.getItems().size(); i++){
            int finalI = i;
            Button lendersButton = tableView.getItems().get(i).getLendersButton();
            lendersButton.setText("Show");
            lendersButton.setOnAction(new EventHandler<ActionEvent>(){
                @Override
                public void handle(ActionEvent actionEvent){
                    if(!lenderStageExist){
                        lenderStageExist = true;
                        lendersTableController.setPopUpScene();
                    }
                    List<LenderMap> lenders = new ArrayList<>();
                    Map<String, Double> lendersMap = riskList.get(finalI).getListOfLenders();
                    for (Map.Entry<String,Double> entry : lendersMap.entrySet()){
                        lenders.add(new LenderMap(entry.getKey(), entry.getValue()));
                    }
                    lendersTableController.setValues(lenders);
                    lendersTableController.getPopUpLenderStage().show();
                }
            });

            Button paymentButton = tableView.getItems().get(i).getPaymentsButton();
            paymentButton.setText("Show");
            paymentButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent){
                    if(!paymentStageExist){
                        paymentStageExist = true;
                        paymentTableController.setPopUpScene();
                    }

                    paymentTableController.setValues(tableView.getItems().get(finalI).getPayments());
                    paymentTableController.getPopUpPaymentStage().show();
                }
            });

        }
    }
}