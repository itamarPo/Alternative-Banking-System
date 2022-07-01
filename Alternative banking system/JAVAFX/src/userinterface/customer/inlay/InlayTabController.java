package userinterface.customer.inlay;

import customercomponents.customerscreen.CustomerScreenController;
import database.Engine;
import exceptions.accountexception.WithDrawMoneyException;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;
import objects.customers.CustomerInfoInlayDTO;
import objects.loans.NewLoanDTO;
import objects.loans.PendingLoanDTO;
import org.controlsfx.control.CheckComboBox;
import org.controlsfx.control.Notifications;
import userinterface.table.loantable.NewLoanTableController;
import userinterface.table.loantable.PendingLoanTableController;
import userinterface.table.loantable.tableobject.NewLoanTableObject;
import userinterface.table.loantable.tableobject.PendingLoanTableObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.Arrays.stream;
import static userinterface.Constants.DIFFERENT;

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

    private Integer amountToInvest;
    private Integer maxOwnership;
    private boolean filterInProgress = false;

    //Regular fields
    private CustomerScreenController customerScreenController;
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

        //Creating checkbox column
        TableColumn<NewLoanTableObject, CheckBox> checkBoxCol = new TableColumn<>();
        //TableColumn<?, CheckBox> checkBoxTableColumn = new TableColumn<>();
        checkBoxCol.setCellValueFactory(new PropertyValueFactory<>("IsSelected"));
        checkBoxCol.setText("Select loans");
        //Adding column to table
        newLoanTBController.getTableView().getColumns().add(0, checkBoxCol);
        //Creating checkbox column
        TableColumn<PendingLoanTableObject, CheckBox> checkBoxColumn = new TableColumn<>();
        checkBoxColumn.setCellValueFactory(new PropertyValueFactory<>("IsSelected"));
        //Adding column to table
        pendingLoanTBController.getTableView().getColumns().add(0,checkBoxColumn);

    }

//    public void setControllersAndStages(){
//        newLoanTBController.setInlayTabController(this);
//        pendingLoanTBController.setInlayTabController(this);
//        pendingLoanTBController.setPrimaryStage(topCustomerController.getMainController().getPrimaryStage());
//    }

    //Getters
    public CheckComboBox getCategoriesCCB() {return categoriesCCB;}

    //Setters
    public void setCustomerScreenController(CustomerScreenController customerScreenController) {
        this.customerScreenController = customerScreenController;
    }
    public void setEngine(Engine engine) {this.engine = engine;}


    //Regular methods
    @FXML
    public void confirmFilterOnAction(ActionEvent actionEvent){
        CustomerInfoInlayDTO customerInfoInlay;
        int amountToinvest = getAmountToInvest();
        List<String> categoriesList = getFilteredCategories(); // this list might be empty! if so there is no filter for categories!
        int minInterest = getMinInterest();
        int minYAZ = getMinYAZ();
      //  int maxOpenLoans = getMaxOpenLoans();
        int maxownership = getMaxOwnership();
       // customerInfoInlay.add(new CustomerInfoInlayDTO(false,"",0));
        if(minInterest == INVALID || minYAZ == INVALID || maxownership == INVALID)
            return;
        customerScreenController.inlaySumCheck((double)amountToinvest, maxownership, categoriesList ,minInterest,minYAZ);
//        if(maxOpenLoans==DIFFERENT) {
//            if(customerInfoInlay==null)
//                Notifications.create().title("ERROR").text("ERRORRRRRR").hideAfter(Duration.seconds(2)).position(Pos.CENTER).showError();
//            else
//                maxOpenLoans = customerInfoInlay.getOpenLoans();
//        }
//        if(customerInfoInlay.isWithDrawException()){
//            amountErrorLabel.setText(customerInfoInlay.getResult());
//            amountToinvest = INVALID;
//        }

//        if(amountToinvest == INVALID || minInterest == INVALID || minYAZ == INVALID || maxOpenLoans == INVALID || maxownership == INVALID) {
////            newLoanTBController.getTableView().getItems().clear();
////            pendingLoanTBController.getTableView().getItems().clear();
//            return;
//        }
//        enableAllErrors();
//        this.amountToInvest = amountToinvest;
//        this.maxOwnership = maxownership;
        //input check

            //throw new WithDrawMoneyException(customerScreenController.getUserCB().getValue(), (double)amountToinvest);
        //filtering
       // List<NewLoanDTO> filteredLoans = customerScreenController.getFilteredLoans(categoriesList,minInterest,minYAZ,maxOpenLoans);
                //inlay
//        //final List<NewLoanDTO>[] filteredLoans = new List[]{new ArrayList<>()};
//        inlayTask filteredNewLoans = new inlayTask(categoriesList,minInterest,minYAZ,"Name"/*Cus.getUserCB().getValue()*/, maxOpenLoans, engine);
//        Thread thread = new Thread(filteredNewLoans);
//        thread.setName("HELPME");
//        bindTaskToProgress(filteredNewLoans,()-> {confirmSelectionButton.setDisable(false); confirmScrambleButton.setDisable(false);
//            filterInProgress = false;});
//        thread.start();
//        filteredNewLoans.valueProperty().addListener(new ChangeListener<List<NewLoanDTO>>() {
//            @Override
//            public void changed(ObservableValue<? extends List<NewLoanDTO>> observable, List<NewLoanDTO> oldValue, List<NewLoanDTO> newValue) {
//                if (newValue != null) {
//                   // filteredLoans[0] = newValue;
//                    newLoanTBController.setValues(newValue.stream().filter(x -> x.getStatus().equals("New")).collect(Collectors.toList()));
//                    List<PendingLoanDTO> filteredPendingLoans = new ArrayList<>();
//                    newValue.stream().filter(p -> p.getStatus().equals("Pending")).forEach(x -> filteredPendingLoans.add((PendingLoanDTO) x));
//                    pendingLoanTBController.setValues(filteredPendingLoans);
//                }
//            }
//       });
//        this.amountToInvest = amountToinvest;
//        this.maxOwnership = maxownership;
    }

    public void filterCheckAndContinue(CustomerInfoInlayDTO customerInfoInlay, int maxownership, List<String> categoriesList,
                                       int minInterest, int minYAZ){
       int maxOpenLoans = getMaxOpenLoans();
        int amountToinvest = getAmountToInvest();
        if(maxOpenLoans==DIFFERENT) {
            if(customerInfoInlay==null)
                Notifications.create().title("ERROR").text("ERRORRRRRR").hideAfter(Duration.seconds(2)).position(Pos.CENTER).showError();
            else
                maxOpenLoans = customerInfoInlay.getOpenLoans();
        }
        if(customerInfoInlay.isWithDrawException()){
            amountErrorLabel.setText(customerInfoInlay.getResult());
            amountToinvest = INVALID;
        }

        if(amountToinvest == INVALID || maxOpenLoans == INVALID) {
//            newLoanTBController.getTableView().getItems().clear();
//            pendingLoanTBController.getTableView().getItems().clear();
            return;
        }
        enableAllErrors();
        this.amountToInvest = amountToinvest;
        this.maxOwnership = maxownership;
        //input check
        //throw new WithDrawMoneyException(customerScreenController.getUserCB().getValue(), (double)amountToinvest);
        //filtering
       customerScreenController.getFilteredLoans(categoriesList,minInterest,minYAZ,maxOpenLoans);
    }

    public void inlayImplement(List<NewLoanDTO> filteredLoans){
        newLoanTBController.setValues(filteredLoans.stream().filter(x -> x.getStatus().equals("New")).collect(Collectors.toList()));
        List<PendingLoanDTO> filteredPendingLoans = new ArrayList<>();
        filteredLoans.stream().filter(p -> p.getStatus().equals("Pending")).forEach(x -> filteredPendingLoans.add((PendingLoanDTO) x));
        pendingLoanTBController.setValues(filteredPendingLoans);
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
            if(number <= 0){
                throw new NumberFormatException();
            }
          //  engine.checkAmountOfInvestment(topCustomerController.getUserCB().getValue(), (double) number); //WithDrawMoneyException
            //TODO: http request to check if sum to invest is valid
            amountErrorLabel.setText("");
            return number;
        } catch (NumberFormatException e) {
            amountErrorLabel.setText("Invalid input. Please enter a positive integer!");
        } catch (Exception e) {
            amountErrorLabel.setText("This filter is mandatory!");
        }
        return INVALID;
    }
    public List<String> getFilteredCategories(){
        ObservableList<String> selectedCategories = categoriesCCB.getCheckModel().getCheckedItems();
        if(selectedCategories.size() == 0){
            ObservableList<String> list = categoriesCCB.getItems();
            //return engine.getCategoriesList().getCategoriesList();
            return list.stream().collect(Collectors.toList());
        }
        return selectedCategories.stream().collect(Collectors.toList());
    }
    public int getMinInterest() {
        try {
            if (!minInterestCB.isSelected()) {
                return 0;
            }
            String interestInput = minInterestTF.getText();
            if (interestInput.equals("")) {
                throw new NumberFormatException();
            } else {
                int minInterest = Integer.parseInt(interestInput); //NumberFormatException
                if (minInterest < 0) {
                    throw new NumberFormatException(); //NumberFormatException
                }
                minInterestErrorLabel.setText("");
                return minInterest;
            }

        } catch (NumberFormatException e) {
            minInterestErrorLabel.setText("Invalid input. Please enter a positive integer!");
            return INVALID;
        }
    }
    public int getMinYAZ(){
       try{
        if (!minYazCB.isSelected()) {
            return 0;
        }
        String YAZInput = minYazTF.getText();
        if (YAZInput.equals("")) {
            throw new NumberFormatException();
        } else {
                int minYAZ = Integer.parseInt(YAZInput); //NumberFormatException
                if (minYAZ < 0) {
                    throw new NumberFormatException(); //NumberFormatException
            }
                minYazErrorLabel.setText("");
                return minYAZ;
            }
        }catch (NumberFormatException e) {
           minYazErrorLabel.setText("Invalid input. Please enter a positive integer!");
       }
        return INVALID;
    }
    public int getMaxOpenLoans(){
        if(!maxOpenLoansCB.isSelected()){
            return DIFFERENT;
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


    public void addCategoriesToCCB(List<String> engineCategories) {
        ObservableList<String> categories = FXCollections.observableArrayList(engineCategories);
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
        if(!filterInProgress) {
            progressBar.setVisible(false);
            progressPercent.setText("");
        }
    }

    public void bindTaskToProgress(inlayTask filteredNewLoans, Runnable onFinish){
//        topCustomerController.getUserCB().setDisable(true);
        confirmSelectionButton.setDisable(true);
        confirmScrambleButton.setDisable(true);
        filterInProgress = true;
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
        List<NewLoanDTO> newLoansPicked = newLoanTBController.getTableView().getItems().stream().filter(x -> x.getIsSelected().isSelected())
                .map(t -> new NewLoanDTO(t.getLoanID(), t.getBorrowerName(), t.getLoanCategory(), t.getSizeNoInterest(), t.getTimeLimitOfLoan(),
                t.getInterestPerPayment(), t.getTimePerPayment(), t.getStatus())).collect(Collectors.toList());
        List<PendingLoanDTO> pendingLoansPicked = pendingLoanTBController.getTableView().getItems().stream().filter(x -> x.getIsSelected().isSelected())
                .map(t -> new PendingLoanDTO(t.getLoanID(), t.getBorrowerName(), t.getLoanCategory(), t.getSizeNoInterest(), t.getTimeLimitOfLoan(),
                        t.getInterestPerPayment(), t.getTimePerPayment(), t.getStatus(), t.getListOfLenders(), t.getCollectedSoFar(),
                        t.getSumLeftToBeCollected())).collect(Collectors.toList());
        newLoansPicked.addAll(pendingLoansPicked);
        if(newLoansPicked.size() == 0){
            Notifications ownerNotExist = Notifications.create().title("Error").text("You must select a loan for the inlay!").hideAfter(Duration.seconds(5)).position(Pos.CENTER);
            ownerNotExist.show();
            return;
        }
        customerScreenController.makeInlay(newLoansPicked, amountToInvest, maxOwnership);
//        engine.splitMoneyBetweenLoans(newLoansPicked.stream().map(NewLoanDTO::getLoanID).collect(Collectors.toList()), amountToInvest, topCustomerController.getUserCB().getValue(), maxOwnership);
        //TODO: http request to inlay
//        resetFields();
//        Notifications successInlay = Notifications.create().title("Success").text("The Inlay was successfully complete!").hideAfter(Duration.seconds(5)).position(Pos.CENTER);
//        successInlay.showInformation();
//        topCustomerController.updateInformationTab(topCustomerController.getUserCB().getValue());
    }

    public void clearSelectionCategoryOnAction(ActionEvent actionEvent){
        categoriesCCB.getCheckModel().clearChecks();
    }

}
