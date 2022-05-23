package userinterface.admin;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import userinterface.table.customerTable.CustomerTableController;
import userinterface.table.loantable.*;


import java.io.File;

public class CenterAdminController {

    //Sub components
    @FXML private ScrollPane newLoan;
    @FXML private NewLoanTableController newLoanController;

    @FXML private ScrollPane pendingLoan;
    @FXML private PendingLoanTableController pendingLoanController;

    @FXML private ScrollPane activeLoan;
    @FXML private ActiveLoanTableController activeLoanController;

    @FXML private ScrollPane riskLoan;
    @FXML private RiskLoanTableController riskLoanController;

    @FXML private ScrollPane finishedLoan;
    @FXML private FinishedLoanTableController finishLoanController;

    @FXML private ScrollPane customerTable;
    @FXML private CustomerTableController customerTableController;

    //JavaFX components
    @FXML private Button IncreaseYazBUTTON;
    @FXML private Button LoadFileBUTTON;
    @FXML private AnchorPane AdminAP;

    //Regular Fields
    private TopAdminController topAdminController;


    //Constructor
    public CenterAdminController() {

    }

    //initialize after constructor
    @FXML
    private void initialize()  {
        IncreaseYazBUTTON.setDisable(true);
    }


    //Getters
    public NewLoanTableController getNewLoanController() {
        return newLoanController;
    }
    public FinishedLoanTableController getFinishLoanController() {return finishedLoanController;}
    public CustomerTableController getCustomerTableController() {
        return customerTableController;
    }
    public PendingLoanTableController getPendingLoanController() {return pendingLoanController;}

    public ActiveLoanTableController getActiveLoanController() {return activeLoanController;}

    public RiskLoanTableController getRiskLoanController() {return riskLoanController;}


    //Setters
    public void setAdminTopController(TopAdminController topAdminController) {
        this.topAdminController = topAdminController;
    }


    //Regular Methods
    @FXML
    void openFileChooser(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select xml file");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("xml files", "*.xml"));
        File selectedFile = fileChooser.showOpenDialog(topAdminController.getMainController().getPrimaryStage());
        if (selectedFile == null) {
            return;
        }
        String absolutePath = selectedFile.getAbsolutePath();
        topAdminController.LoadFileAction(absolutePath);
    }

    public void enableAfterFileLoader(){
        IncreaseYazBUTTON.setDisable(false);
    }

}
