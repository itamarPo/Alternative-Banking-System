package userinterface.customer.inlay;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import org.controlsfx.control.CheckComboBox;
import userinterface.customer.TopCustomerController;
import userinterface.table.loantable.NewLoanTableController;
import userinterface.table.loantable.PendingLoanTableController;

public class InlayTabController {
    //Sub components
    @FXML private ScrollPane newLoanTable;
    @FXML private NewLoanTableController newLoanTableController;
    @FXML private ScrollPane pendingLoanTable;
    @FXML private PendingLoanTableController pendingLoanTableController;

    //JavaFX components
    @FXML private ScrollPane inlaySP;
    @FXML private AnchorPane inlayAP;
    @FXML private GridPane scrambleGP;
    @FXML private GridPane scrambleResultGP;
    @FXML private CheckComboBox categoriesCCB;
    @FXML private CheckBox minYazCB;
    @FXML private CheckBox minInterestCB;
    @FXML private CheckBox maxOwnershipLoanCB;
    @FXML private CheckBox maxOpenLoansCB;
    @FXML private TextField amountTF;
    @FXML private TextField minInterestTF;
    @FXML private TextField minYazTF;
    @FXML private TextField maxOwnershipLoanTF;
    @FXML private TextField maxOpenLoansTF;
    @FXML private Label amountErrorLabel;
    @FXML private Label minInterestErrorLabel;
    @FXML private Label minYazErrorLabel;
    @FXML private Label maxOwnerShipErrorLabel;
    @FXML private Label maxOpenLoansErrorLabel;
    @FXML private Button confirmScrambleButton;
    @FXML private Button confirmSelectionButton;
    @FXML private TabPane inlayResultTP;
    @FXML private Tab inlayResultNewTab;
    @FXML private Tab inlayResultPendingTab;

    //Regular fields
    TopCustomerController topCustomerController;


    public void setTopCustomerController(TopCustomerController topCustomerController) {
        this.topCustomerController = topCustomerController;
    }
}
