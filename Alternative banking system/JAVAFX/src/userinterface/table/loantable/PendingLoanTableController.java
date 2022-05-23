package userinterface.table.loantable;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import objects.loans.NewLoanDTO;
import objects.loans.PendingLoanDTO;

import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public class PendingLoanTableController {

    //JavaFX components
    @FXML private TableView<PendingLoanDTO> tableView;
    @FXML private TableColumn<PendingLoanDTO, String> loanID;
    @FXML private TableColumn<PendingLoanDTO, String> category;
    @FXML private TableColumn<PendingLoanDTO, String> owner;
    @FXML private TableColumn<PendingLoanDTO, Double> amount;
    @FXML private TableColumn<PendingLoanDTO, Integer> duration;
    @FXML private TableColumn<PendingLoanDTO, Integer> interest;
    @FXML private TableColumn<PendingLoanDTO, Integer> timePerPayment;
    @FXML private TableColumn<PendingLoanDTO, Map<String, Double>> listOfLenders;
    @FXML private TableColumn<PendingLoanDTO, Double> collectedSoFar;
    @FXML private TableColumn<PendingLoanDTO, Double> sumLeftToBeCollected;



    public PendingLoanTableController() {

    }

    public void initialize() {
        loanID.setCellValueFactory(new PropertyValueFactory<PendingLoanDTO, String>("loanID"));
        category.setCellValueFactory(new PropertyValueFactory<PendingLoanDTO, String>("loanCategory"));
        owner.setCellValueFactory(new PropertyValueFactory<PendingLoanDTO, String>("borrowerName"));
        duration.setCellValueFactory(new PropertyValueFactory<PendingLoanDTO, Integer>("timeLimitOfLoan"));
        amount.setCellValueFactory(new PropertyValueFactory<PendingLoanDTO, Double>("sizeNoInterest"));
        interest.setCellValueFactory(new PropertyValueFactory<PendingLoanDTO, Integer>("interestPerPayment"));
        timePerPayment.setCellValueFactory(new PropertyValueFactory<PendingLoanDTO, Integer>("timePerPayment"));
        listOfLenders.setCellValueFactory(new PropertyValueFactory<PendingLoanDTO, Map<String, Double>>("listOfLenders"));
        collectedSoFar.setCellValueFactory(new PropertyValueFactory<PendingLoanDTO, Double>("collectedSoFar"));
        sumLeftToBeCollected.setCellValueFactory(new PropertyValueFactory<PendingLoanDTO, Double>("sumLeftToBeCollected"));
    }



    public void setValues(List<PendingLoanDTO> pendingLoansList){
        ObservableList<PendingLoanDTO> pendingLoanDTOObservableList = FXCollections.observableArrayList(pendingLoansList);
        tableView.getItems().setAll(pendingLoanDTOObservableList);
    }
}