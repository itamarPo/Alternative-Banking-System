package userinterface.table.loantable;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.net.URL;
import java.util.ResourceBundle;

public class ActiveLoanTableController implements Initializable {

    //JavaFX components
    @FXML private TableView<?> tableView;
    @FXML private TableColumn<?, ?> loanID;
    @FXML private TableColumn<?, ?> category;
    @FXML private TableColumn<?, ?> owner;
    @FXML private TableColumn<?, ?> amount;
    @FXML private TableColumn<?, ?> duration;
    @FXML private TableColumn<?, ?> interest;
    @FXML private TableColumn<?, ?> timePerPayment;
    @FXML private TableColumn<?, ?> listOfLenders;
    @FXML private TableColumn<?, ?> startingActiveTime;
    @FXML private TableColumn<?, ?> nextPaymentTime;
    @FXML private TableColumn<?, ?> payments;
    @FXML private TableColumn<?, ?> allInitialPayedSoFar;
    @FXML private TableColumn<?, ?> allInterestPayedSoFar;
    @FXML private TableColumn<?, ?> allInitialLeftToPay;
    @FXML private TableColumn<?, ?> allInterestLeftToPay;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }
}