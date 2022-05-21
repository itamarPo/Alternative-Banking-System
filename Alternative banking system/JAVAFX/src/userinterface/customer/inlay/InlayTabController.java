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
    //Constatns
    private final int INVALID = -1;
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
    @FXML private Label maxOpenLoanErrorLabel;
    //maximum ownership
    @FXML private GridPane scrambleResultGP;
    @FXML private TextField maxOwnershipLoanTF;
    @FXML private Label maxOwnershipLoanErrorLabel;

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
        minInterestCB.selectedProperty().addListener((observable, newEnable, oldEnable) -> {
            minInterestTF.setDisable(newEnable);
            minInterestTF.setText("");
            minInterestErrorLabel.setText("");}
        );
        minYazCB.selectedProperty().addListener((observable, newEnable, oldEnable) -> {
                    minYazTF.setDisable(newEnable);
                    minYazTF.setText("");
                    minYazErrorLabel.setText("");}
        );
        maxOpenLoansCB.selectedProperty().addListener((observable, newEnable, oldEnable) -> {
            maxOpenLoansTF.setDisable(newEnable);
            maxOpenLoansTF.setText("");
            maxOpenLoanErrorLabel.setText("");}
        );
        maxOwnershipLoanCB.selectedProperty().addListener((observable, newEnable, oldEnable) -> {
            maxOwnershipLoanTF.setDisable(newEnable);
            maxOwnershipLoanTF.setText("");
            maxOwnershipLoanErrorLabel.setText("");}

        );

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
        int amountToInvest = getAmountToInvest();
        List<String> categoriesList = getFilteredCategories(); // this list might be empty! if so there is no filter for categories!
        int minInterest = getMinInterest();
        int minYAZ = getMinYAZ();
        int maxOpenLoans = getMaxOpenLoans();
        int maxOwnership = getMaxOwnership();
        if(amountToInvest != INVALID && minInterest != INVALID && minYAZ != INVALID && maxOpenLoans != INVALID && maxOwnership != INVALID){
            enableAllErrors();
            //activate inlay!
        }

    }

    private void enableAllErrors() {
        amountErrorLabel.setText("");
        minInterestErrorLabel.setText("");
        maxOwnershipLoanErrorLabel.setText("");
        minYazErrorLabel.setText("");
        maxOpenLoanErrorLabel.setText("");
    }

    public int getAmountToInvest(){
        try {
            String amountInput = amountTF.getText();
            if(amountInput.equals("")){
                throw new Exception();
            }
            int number = Integer.parseInt(amountInput); // NumberFormatException
            engine.checkAmountOfInvestment(topCustomerController.getUserCB().getValue(), (double) number); //WithDrawMoneyException
            amountErrorLabel.setText("");
            return number;
        } catch (NumberFormatException e) {
            amountErrorLabel.setText("Invalid input. Please enter a positive integer!");
        } catch (WithDrawMoneyException e) {
            amountErrorLabel.setText(e.toString());
        } catch (Exception e) {
            amountErrorLabel.setText("This filter is mandatory!");
        }
        return INVALID;
    }

    public List<String> getFilteredCategories(){
        ObservableList<String> selectedCategories = categoriesCCB.getCheckModel().getCheckedItems();
        return selectedCategories.stream().collect(Collectors.toList());
    }

    public int getMinInterest() {
        if (!minInterestCB.isSelected()) {
            return 0;
        }
        String interestInput = minInterestTF.getText();
        if (interestInput.equals("")) {
            return 0;
        } else {
            try {
                int minInterest = Integer.parseInt(interestInput); //NumberFormatException
                if (minInterest < 0) {
                    throw new NumberFormatException(); //NumberFormatException
                }
                minInterestErrorLabel.setText("");
                return minInterest;
            } catch (NumberFormatException e) {
                minInterestErrorLabel.setText("Invalid input. Please enter a positive integer!");
            }
        }
        return INVALID;
    }

    public int getMinYAZ(){
        if (!minYazCB.isSelected()) {
            return 0;
        }
        String YAZInput = minYazTF.getText();
        if (YAZInput.equals("")) {
            return 0;
        } else {
            try {
                int minYAZ = Integer.parseInt(YAZInput); //NumberFormatException
                if (minYAZ < 0) {
                    throw new NumberFormatException(); //NumberFormatException
                }
                minYazErrorLabel.setText("");
                return minYAZ;
            } catch (NumberFormatException e) {
                minYazErrorLabel.setText("Invalid input. Please enter a positive integer!");
            }
        }
        return INVALID;
    }


    public int getMaxOpenLoans(){
        if(!maxOpenLoansCB.isSelected()){
            return 0;
        }
        else{
            String maxOpenLoansInput = maxOpenLoansTF.getText();
            try {
                int maxOpenLoans = Integer.parseInt(maxOpenLoansInput); //NumberFormatException
                if(maxOpenLoans < 1){
                    throw new NumberFormatException();
                }
                maxOpenLoanErrorLabel.setText("");
                return maxOpenLoans;
            }catch (NumberFormatException e){
                maxOpenLoanErrorLabel.setText("Invalid input. Please enter a positive integer!");
            }
        }
        return INVALID;
    }

    public int getMaxOwnership(){
        if(!maxOwnershipLoanCB.isSelected()){
            return 100;
        }
        else{
            String maxOwnershipInput = maxOwnershipLoanTF.getText();
            try {
                int maxOwnership = Integer.parseInt(maxOwnershipInput); //NumberFormatException
                if(maxOwnership < 1 || maxOwnership > 100){
                    throw new Exception();
                }
                maxOwnershipLoanErrorLabel.setText("");
                return maxOwnership;
            } catch (NumberFormatException e){
                maxOwnershipLoanErrorLabel.setText("Invalid input. Please enter a positive integer!");
            } catch (Exception e) {
                maxOwnershipLoanErrorLabel.setText("Please enter an integer between 1 and 100!");
            }
        }
        return INVALID;
    }


    public void addCategoriesToCCB() {
        ObservableList<String> categories = FXCollections.observableArrayList(engine.getCategoriesList().getCategoriesList());
        categoriesCCB.getItems().clear();
        categoriesCCB.getItems().addAll(categories);
    }

    public void resetFields() {
        amountTF.setText("");
        minInterestCB.setSelected(false);
        minYazCB.setSelected(false);
        maxOpenLoansCB.setSelected(false);
        maxOwnershipLoanCB.setSelected(false);
    }
}
