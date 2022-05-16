package userinterface.table.loantable;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class FinishedLoanTableController {

    //JavaFX components
    @FXML
    private TableView<?> finishedTable;
    @FXML private TableColumn<?, ?> loanID;
    @FXML private TableColumn<?, ?> category;
    @FXML private TableColumn<?, ?> owner;
    @FXML private TableColumn<?, ?> amount;
    @FXML private TableColumn<?, ?> duration;
    @FXML private TableColumn<?, ?> interest;
    @FXML private TableColumn<?, ?> timePerPayment;
    @FXML private TableColumn<?, ?> listOfLenders;
    @FXML private TableColumn<?, ?> payments;
    @FXML private TableColumn<?, ?> startingTime;
    @FXML private TableColumn<?, ?> finishingTime;

    public TableView<?> getFinishedTable() {
        return finishedTable;
    }
}
