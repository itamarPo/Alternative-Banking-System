package userinterface.customer.inlay;

import database.Engine;
import exceptions.accountexception.WithDrawMoneyException;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;
import objects.loans.NewLoanDTO;
import objects.loans.PendingLoanDTO;
import org.controlsfx.control.CheckComboBox;
import org.controlsfx.control.Notifications;
import userinterface.customer.TopCustomerController;
import userinterface.table.loantable.NewLoanTableController;
import userinterface.table.loantable.PendingLoanTableController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.Arrays.stream;

public class InlayTabController {
    //Constatns
    private final int INVALID = -1;
    //Sub components
    @FXML private ScrollPane newLoanTB;
    @FXML private NewLoanTableController newLoanTBController;
    @FXML private ScrollPane pendingLoanTB;
    @FXML private PendingLoanTableController pendingLoanTBController;

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

    @FXML private ProgressBar progressBar;

    @FXML private Label progressPercent;
    @FXML private Button clearSelectionCategoryButton;
    //Regular Fields

    private int amountToInvest;
    private int maxOwnership;


    //Regular fields
    private TopCustomerController topCustomerController;
    private Engine engine;


    //Initialize after constructor
    @FXML
    private void initialize() {
        //Binding check boxes with text fields in inlay details
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

        //Adding checkbox column to the new table
        TableColumn<NewLoanDTO, CheckBox> checkBoxCol = new TableColumn<>();
        checkBoxCol.setCellValueFactory(new PropertyValueFactory<>("IsSelected"));
        checkBoxCol.setText("Select loans");
        newLoanTBController.getTableView().getColumns().add(0, checkBoxCol);
        //Adding checkbox column to the pending table
        TableColumn<PendingLoanDTO, CheckBox> checkBoxColumn = new TableColumn<>();
        checkBoxColumn.setCellValueFactory(new PropertyValueFactory<>("IsSelected"));
        pendingLoanTBController.getTableView().getColumns().add(0,checkBoxColumn);

    }

    public void setControllersAndStages(){
        newLoanTBController.setInlayTabController(this);
        pendingLoanTBController.setInlayTabController(this);
        pendingLoanTBController.setPrimaryStage(topCustomerController.getMainController().getPrimaryStage());
    }

    //Getters
    public CheckComboBox getCategoriesCCB() {return categoriesCCB;}

    //Setters
    public void setTopCustomerController(TopCustomerController topCustomerController) {
        this.topCustomerController = topCustomerController;
    }
    public void setEngine(Engine engine) {this.engine = engine;}


    //Regular methods
    @FXML
    public void confirmFilterOnAction(ActionEvent actionEvent){

        int amountToinvest = getAmountToInvest();
        List<String> categoriesList = getFilteredCategories(); // this list might be empty! if so there is no filter for categories!
        int minInterest = getMinInterest();
        int minYAZ = getMinYAZ();
        int maxOpenLoans = getMaxOpenLoans();
        int maxownership = getMaxOwnership();
        final int[] amountToInvestTmp = new int[1];
        final int[] maxownershipTmp = new int[1];
        if(amountToinvest == INVALID || minInterest == INVALID || minYAZ == INVALID || maxOpenLoans == INVALID || maxownership == INVALID) {
            newLoanTBController.getTableView().getItems().clear();
            pendingLoanTBController.getTableView().getItems().clear();
            return;
        }
        enableAllErrors();
        final List<NewLoanDTO>[] filteredLoans = new List[]{new ArrayList<>()};
        inlayTask filteredNewLoans = new inlayTask(categoriesList,minInterest,minYAZ,topCustomerController.getUserCB().getValue(), maxOpenLoans, engine);
        Thread thread = new Thread(filteredNewLoans);
        thread.setName("HELPME");
        bindTaskToProgress(filteredNewLoans,()-> {confirmSelectionButton.setDisable(false); confirmScrambleButton.setDisable(false);});
        thread.start();
        filteredNewLoans.valueProperty().addListener(new ChangeListener<List<NewLoanDTO>>() {
            @Override
            public void changed(ObservableValue<? extends List<NewLoanDTO>> observable, List<NewLoanDTO> oldValue, List<NewLoanDTO> newValue) {
                if (newValue != null) {
                   // filteredLoans[0] = newValue;
                    newLoanTBController.setValues(newValue.stream().filter(x -> x.getStatus().equals("New")).collect(Collectors.toList()));
                    amountToInvestTmp[0] = amountToinvest;
                    maxownershipTmp[0] = maxownership;
                    List<PendingLoanDTO> filteredPendingLoans = new ArrayList<>();
                    newValue.stream().filter(p -> p.getStatus().equals("Pending")).forEach(x -> filteredPendingLoans.add((PendingLoanDTO) x));
                    pendingLoanTBController.setValues(filteredPendingLoans);
                }
            }
       });
        this.amountToInvest = amountToinvest;
        this.maxOwnership = maxownership;
    }


    private void enableAllErrors() {
        amountErrorLabel.setText("");
        minInterestErrorLabel.setText("");
        maxOwnershipLoanErrorLabel.setText("");
        minYazErrorLabel.setText("");
        maxOpenLoanErrorLabel.setText("");
    }

    //getting filter info from user
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
        if(selectedCategories.size() == 0){
            return engine.getCategoriesList().getCategoriesList();
        }
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
            return engine.getNumOfLoans();
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
        amountErrorLabel.setText("");
        maxOpenLoanErrorLabel.setText("");
        maxOwnershipLoanErrorLabel.setText("");
        minYazErrorLabel.setText("");
        minInterestErrorLabel.setText("");
        amountTF.setText("");
        minInterestCB.setSelected(false);
        minYazCB.setSelected(false);
        maxOpenLoansCB.setSelected(false);
        maxOwnershipLoanCB.setSelected(false);
        newLoanTBController.getTableView().getItems().clear();
        pendingLoanTBController.getTableView().getItems().clear();
        progressBar.setVisible(false);
        progressPercent.setText("");
    }

    public void bindTaskToProgress(inlayTask filteredNewLoans, Runnable onFinish){
        confirmSelectionButton.setDisable(true);
        confirmScrambleButton.setDisable(true);
        progressBar.setVisible(true);
        progressBar.progressProperty().bind(filteredNewLoans.progressProperty());
        progressPercent.textProperty().bind(
                Bindings.concat(
                        Bindings.format(
                                "%.0f",
                                Bindings.multiply(
                                        filteredNewLoans.progressProperty(),
                                        100)),
                        " %"));
    filteredNewLoans.valueProperty().addListener((observable, oldValue, newValue) -> {
        onTaskFinished(Optional.ofNullable(onFinish));
    });
}

    public void onTaskFinished(Optional<Runnable> onFinish) {
        this.progressPercent.textProperty().unbind();
        this.progressBar.progressProperty().unbind();
        onFinish.ifPresent(Runnable::run);
    }
    @FXML
    public void confirmInlayOnAction(ActionEvent actionEvent)throws Exception{
        List<NewLoanDTO> newLoansPicked = newLoanTBController.getTableView().getItems().stream().filter(x -> x.getIsSelected().isSelected()).collect(Collectors.toList());
        List<NewLoanDTO> pendingLoansPicked = pendingLoanTBController.getTableView().getItems().stream().filter(x -> x.getIsSelected().isSelected()).collect(Collectors.toList());
        newLoansPicked.addAll(pendingLoansPicked);
        if(newLoansPicked.size() == 0){
            Notifications ownerNotExist = Notifications.create().title("Error").text("You must select a loan for the inlay!").hideAfter(Duration.seconds(5)).position(Pos.CENTER);
            ownerNotExist.show();
            return;
        }
        engine.splitMoneyBetweenLoans(newLoansPicked.stream().map(NewLoanDTO::getLoanID).collect(Collectors.toList()), amountToInvest, topCustomerController.getUserCB().getValue(), maxOwnership);
        resetFields();
        Notifications successInlay = Notifications.create().title("Success").text("The Inlay was successfully complete!").hideAfter(Duration.seconds(5)).position(Pos.CENTER);
        successInlay.showInformation();
        topCustomerController.updateInformationTab(topCustomerController.getUserCB().getValue());
    }

    public void clearSelectionCategoryOnAction(ActionEvent actionEvent){
        categoriesCCB.getCheckModel().clearChecks();
    }

}
