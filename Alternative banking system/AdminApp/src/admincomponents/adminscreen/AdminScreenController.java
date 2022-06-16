package admincomponents.adminscreen;

import database.Engine;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.controlsfx.control.*;
import userinterface.table.customerTable.CustomerTableController;
import userinterface.table.loantable.*;

import java.io.File;
import java.util.Timer;

public class AdminScreenController {

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
    @FXML private FinishedLoanTableController finishedLoanController;

    @FXML private ScrollPane customerTable;
    @FXML private CustomerTableController customerTableController;

    //JavaFX components
    @FXML private Button IncreaseYazBUTTON;
    @FXML private Label currentYazLabel;
    @FXML private ComboBox<String> skinCB;
    @FXML private ToggleSwitch rewindToggleSwitch;
    @FXML private ComboBox<String> rewindCB;
    @FXML private AnchorPane AdminAP;

    //Regular Fields
    private Timer timer;

   // private Engine engine;
    private FadeTransition yazTransition;

    //Constructor
    public AdminScreenController() {

    }

    //initialize after constructor
    @FXML
    private void initialize()  {
        //IncreaseYazBUTTON.setDisable(true);
        //newLoanController.setCenterAdminController(this);
        //pendingLoanController.setCenterAdminController(this);
        //activeLoanController.setCenterAdminController(this);
        //riskLoanController.setCenterAdminController(this);
        //finishedLoanController.setCenterAdminController(this);
        yazTransition = new FadeTransition();
    }


//    public void setControllersAndStages(){
//        pendingLoanController.setPrimaryStage(topAdminController.getMainController().getPrimaryStage());
//        activeLoanController.setPrimaryStage(topAdminController.getMainController().getPrimaryStage());
//        riskLoanController.setPrimaryStage(topAdminController.getMainController().getPrimaryStage());
//        finishedLoanController.setPrimaryStage(topAdminController.getMainController().getPrimaryStage());
//    }

    //Getters
    public NewLoanTableController getNewLoanController() {
        return newLoanController;
    }
    public FinishedLoanTableController getFinishLoanController() {return finishedLoanController;}
    public PendingLoanTableController getPendingLoanController() {return pendingLoanController;}
    public ActiveLoanTableController getActiveLoanController() {return activeLoanController;}
    public RiskLoanTableController getRiskLoanController() {return riskLoanController;}
    public CustomerTableController getCustomerTableController() {
        return customerTableController;
    }


    //Setters


    //Regular Methods
    @FXML
    void increaseYazOnAction(ActionEvent event){
        //engine.moveTImeForward2();
        //Integer time = Engine.getTime() ;
      //  topAdminController.getYazLABEL().setText( "Current Yaz: " + time.toString());
        currentYazLabel.setText(currentYazLabel.getText()+"1");
        //topAdminController.updateAdminTable();
        //if(topAdminController.isAnimationOn())
           // topAdminController.yazIncreaseAnimation();
    }

    public void enableAfterFileLoader(){
        IncreaseYazBUTTON.setDisable(false);
    }

}
