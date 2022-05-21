package userinterface.customer.inlay;

import database.Engine;
import exceptions.accountexception.WithDrawMoneyException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import org.controlsfx.control.CheckComboBox;
import userinterface.customer.TopCustomerController;
import userinterface.table.loantable.NewLoanTableController;
import userinterface.table.loantable.PendingLoanTableController;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.Arrays.stream;

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
    //amount to invest
    @FXML private TextField amountTF;
    @FXML private Label amountErrorLabel;
    //categories
    @FXML private CheckComboBox categoriesCCB;
    //minimum interest
    @FXML private CheckBox minInterestCB;
    @FXML private TextField minInterestTF;
    @FXML private Label minInterestErrorLabel;
    //minimum YAZ
    @FXML private CheckBox minYazCB;
    @FXML private TextField minYazTF;
    @FXML private Label minYazErrorLabel;
    //maximum open loans
    @FXML private CheckBox maxOpenLoansCB;
    @FXML private TextField maxOpenLoansTF;
    @FXML private Label maxOpenLoansErrorLabel;
    //maximum ownership
    @FXML private GridPane scrambleResultGP;
    @FXML private TextField maxOwnershipLoanTF;
    @FXML private Label maxOwnerShipErrorLabel;

    @FXML private CheckBox maxOwnershipLoanCB;











    @FXML private Button confirmScrambleButton;
    @FXML private Button confirmSelectionButton;
    @FXML private TabPane inlayResultTP;
    @FXML private Tab inlayResultNewTab;
    @FXML private Tab inlayResultPendingTab;

    //Properties

    //Initialize after constructor
    @FXML
    private void initialize() {

    }


    //Regular fields
    private TopCustomerController topCustomerController;
    private Engine engine;


    //Getters
    public CheckComboBox getCategoriesCCB() {return categoriesCCB;}

    //Setters
    public void setTopCustomerController(TopCustomerController topCustomerController) {
        this.topCustomerController = topCustomerController;
    }
    public void setEngine(Engine engine) {this.engine = engine;}

    //Regular methods
    @FXML
    public void confirmScrambleOnAction(ActionEvent actionEvent){
        try{
            int amountToInvest = getAmountToInvest();
            List<String> categoriesList = getFilteredCategories();


        } catch (NumberFormatException e) {
            amountErrorLabel.setText("Invalid input. Please enter a positive integer!");
        } catch (WithDrawMoneyException e) {
            amountErrorLabel.setText(e.toString());
        } catch (Exception e) {

        }

    }
    public int getAmountToInvest() throws Exception{
        String amountInput = amountTF.getText();
        int number = Integer.parseInt(amountInput); // NumberFormatException
        engine.checkAmountOfInvestment(topCustomerController.getUserCB().getValue() ,(double)number); //WithDrawMoneyException
        return number;
    }
    public List<String> getFilteredCategories(){
        ObservableList<String> selectedCategories = categoriesCCB.getCheckModel().getCheckedItems();
        return selectedCategories.stream().collect(Collectors.toList());
    }


    public void addCategoriesToCCB() {
        ObservableList<String> categories = FXCollections.observableArrayList(engine.getCategoriesList().getCategoriesList());
        categoriesCCB.getItems().addAll(categories);
    }
}
