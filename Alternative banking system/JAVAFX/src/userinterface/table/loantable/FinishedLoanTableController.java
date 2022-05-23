package userinterface.table.loantable;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import objects.loans.ActiveRiskLoanDTO;
import objects.loans.FinishedLoanDTO;

import java.util.List;
import java.util.Map;

public class FinishedLoanTableController {

    //JavaFX components
    @FXML
    private TableView<FinishedLoanDTO> finishedTable;
    @FXML private TableColumn<FinishedLoanDTO, String> loanID;
    @FXML private TableColumn<FinishedLoanDTO, String> category;
    @FXML private TableColumn<FinishedLoanDTO, String> owner;
    @FXML private TableColumn<FinishedLoanDTO, Double> amount;
    @FXML private TableColumn<FinishedLoanDTO, Integer> duration;
    @FXML private TableColumn<FinishedLoanDTO, Integer> interest;
    @FXML private TableColumn<FinishedLoanDTO, Integer> timePerPayment;
    @FXML private TableColumn<FinishedLoanDTO, Button> listOfLenders;
    @FXML private TableColumn<FinishedLoanDTO, Button> payments;
    @FXML private TableColumn<FinishedLoanDTO, Integer> startingTime;
    @FXML private TableColumn<FinishedLoanDTO, Integer> finishingTime;

    public void initialize(){
        loanID.setCellValueFactory(new PropertyValueFactory<FinishedLoanDTO, String>("loanID"));
        category.setCellValueFactory(new PropertyValueFactory<FinishedLoanDTO, String>("loanCategory"));
        owner.setCellValueFactory(new PropertyValueFactory<FinishedLoanDTO, String>("borrowerName"));
        duration.setCellValueFactory(new PropertyValueFactory<FinishedLoanDTO, Integer>("timeLimitOfLoan"));
        amount.setCellValueFactory(new PropertyValueFactory<FinishedLoanDTO, Double>("sizeNoInterest"));
        interest.setCellValueFactory(new PropertyValueFactory<FinishedLoanDTO, Integer>("interestPerPayment"));
        timePerPayment.setCellValueFactory(new PropertyValueFactory<FinishedLoanDTO, Integer>("timePerPayment"));
        listOfLenders.setCellValueFactory(new PropertyValueFactory<FinishedLoanDTO, Button>("lendersButton"));
        startingTime.setCellValueFactory(new PropertyValueFactory<FinishedLoanDTO, Integer>("startingTime"));
        payments.setCellValueFactory(new PropertyValueFactory<FinishedLoanDTO, Button>("paymentsButton"));
        finishingTime.setCellValueFactory(new PropertyValueFactory<FinishedLoanDTO, Integer>("finishingTime"));
    }

    public void setValues(List<FinishedLoanDTO> finishedList){
        ObservableList<FinishedLoanDTO> FinishedLoanDTOObservableList = FXCollections.observableList(finishedList);
        finishedTable.getItems().setAll(FinishedLoanDTOObservableList);
    }
    public TableView<?> getFinishedTable() {
        return finishedTable;
    }

}
