package userinterface.table;

import database.client.Customer;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.net.URL;
import java.util.ResourceBundle;

public class AdminCustomerTableController implements Initializable {
    @FXML private TableView<Customer> table;
    @FXML private TableColumn<Customer, String> name;
    @FXML private TableColumn<Customer, Double> balance;
    @FXML private TableColumn<Customer, Integer> newLender;
    @FXML private TableColumn<Customer, Integer> pendingLender;
    @FXML private TableColumn<Customer, Integer> activeLender;
    @FXML private TableColumn<Customer, Integer> riskLender;
    @FXML private TableColumn<Customer, Integer> finishedLender;
    @FXML private TableColumn<Customer, Integer> newBorrower;
    @FXML private TableColumn<Customer, Integer> pendingBorrower;
    @FXML private TableColumn<Customer, Integer> activeBorrower;
    @FXML private TableColumn<Customer, Integer> riskBorrower;
    @FXML private TableColumn<Customer, Integer> finishedBorrower;
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void setValues(Customer customer){
    }
}
