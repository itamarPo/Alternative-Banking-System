package userinterface.table.loantable;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import objects.loans.ActiveRiskLoanDTO;

import java.util.List;
import java.util.Map;

public class RiskLoanTableController {

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
    }

    public void setValues(List<ActiveRiskLoanDTO> riskList){
        ObservableList<ActiveRiskLoanDTO> activeRiskLoanDTOObservableList = FXCollections.observableList(riskList);
        tableView.getItems().setAll(activeRiskLoanDTOObservableList);
    }
}