package userinterface.table.loantable;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;

public class NewLoanTableController {

    //JavaFX components
    @FXML private TableColumn<?, ?> loanID;
    @FXML private TableColumn<?, ?> category;
    @FXML private TableColumn<?, ?> owner;
    @FXML private TableColumn<?, ?> duration;
    @FXML private TableColumn<?, ?> interest;
    @FXML private TableColumn<?, ?> timePerPayment;
}
