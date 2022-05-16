package userinterface.table.loantable;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;

public class RiskLoanTableController {

    //JavaFX components
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
}
