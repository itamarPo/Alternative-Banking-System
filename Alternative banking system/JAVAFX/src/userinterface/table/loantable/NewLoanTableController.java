package userinterface.table.loantable;

import database.Engine;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import objects.loans.NewLoanDTO;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class NewLoanTableController implements Initializable {

    //JavaFX components
    @FXML private TableView<NewLoanDTO> tableView;
    @FXML private TableColumn<NewLoanDTO, String> loanID;
    @FXML private TableColumn<NewLoanDTO, String> category;
    @FXML private TableColumn<NewLoanDTO, String> owner;
    @FXML private TableColumn<NewLoanDTO, Integer> duration;
    @FXML private TableColumn<NewLoanDTO, Double> amount;
    @FXML private TableColumn<NewLoanDTO, Integer> interest;
    @FXML private TableColumn<NewLoanDTO, Integer> timePerPayment;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
//        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        loanID.setCellValueFactory(new PropertyValueFactory<NewLoanDTO, String>("loanID"));
        category.setCellValueFactory(new PropertyValueFactory<NewLoanDTO, String>("loanCategory"));
        owner.setCellValueFactory(new PropertyValueFactory<NewLoanDTO, String>("borrowerName"));
        duration.setCellValueFactory(new PropertyValueFactory<NewLoanDTO, Integer>("timeLimitOfLoan"));
        amount.setCellValueFactory(new PropertyValueFactory<NewLoanDTO, Double>("sizeNoInterest"));
        interest.setCellValueFactory(new PropertyValueFactory<NewLoanDTO, Integer>("interestPerPayment"));
        timePerPayment.setCellValueFactory(new PropertyValueFactory<NewLoanDTO, Integer>("timePerPayment"));
    }

    //Getters


    public TableView<NewLoanDTO> getTableView() {
        return tableView;
    }

    //Regular Methods
    public void setValues(List<NewLoanDTO> newLoansList){
       ObservableList<NewLoanDTO> newLoanDTOObservableList = FXCollections.observableArrayList(newLoansList);
       tableView.getItems().setAll(newLoanDTOObservableList);
    }
}
