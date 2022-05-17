package userinterface.table.loantable;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class PendingLoanTableController {

    //JavaFX components
    @FXML private TableView tableView;
    @FXML private TableColumn<?, ?> loanID;
    @FXML private TableColumn<?, ?> category;
    @FXML private TableColumn<?, ?> owner;
    @FXML private TableColumn<?, ?> amount;
    @FXML private TableColumn<?, ?> duration;
    @FXML private TableColumn<?, ?> interest;
    @FXML private TableColumn<?, ?> timePerPayment;
    @FXML private TableColumn<?, ?> lisfOfLenders;
    @FXML private TableColumn<?, ?> collectedSoFar;
    @FXML private TableColumn<?, ?> sumLeftToBeCollected;
}
