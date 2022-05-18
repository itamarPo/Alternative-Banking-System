package userinterface.customer.information;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

public class AccountTransactionController {


    //JavaFX components
    @FXML private AnchorPane accountTransactionAP;
    @FXML private HBox accountTransactionHB;
    @FXML private TableColumn<?, ?> balanceAfter;
    @FXML private TableColumn<?, ?> balanceBefore;
    @FXML private Button charge;
    @FXML private TableView<?> tableView;
    @FXML private TableColumn<?, ?> timeOfTransaction;
    @FXML private TableColumn<?, ?> transactionAmount;
    @FXML private Button withdraw;

}
