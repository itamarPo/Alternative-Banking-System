package userinterface.table.loantable;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.net.URL;
import java.util.ResourceBundle;

public class NewLoanTableController implements Initializable {

    //JavaFX components
    @FXML private TableView<?> tableView;
    @FXML private TableColumn<?, ?> loanID;
    @FXML private TableColumn<?, ?> category;
    @FXML private TableColumn<?, ?> owner;
    @FXML private TableColumn<?, ?> duration;
    @FXML private TableColumn<?, ?> interest;
    @FXML private TableColumn<?, ?> timePerPayment;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }
}
