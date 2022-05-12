package userinterface.admin;

import database.Engine;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import userinterface.MainController.MainController;
import userinterface.customer.TopCustomerController;

import java.io.File;

public class TopAdminController {

    //constants
    private final String YAZSTATEMENT = "Current Yaz: " ;
    private final String FILESTATMENT = "File: " ;

    //SubComponents
    @FXML private AnchorPane CenterAdmin;
    @FXML private CenterAdminController CenterAdminController;

    //JavaFX components
    @FXML private ComboBox<String> UserCB;
    @FXML private ScrollPane MainSP;
    @FXML private BorderPane MainBP;
    @FXML private Label FileLABEL;
    @FXML private Label YazLABEL;

    //Regular Fields
    private MainController mainController;


    //Getters!
    public AnchorPane getAdminAnchorPane() {return AdminAP;}

    //constructor
    public TopAdminController() {

    }

    //initialize after constructor
    @FXML
    private void initialize()  {
        IncreaseYazBUTTON.setDisable(true);
        LoansBUTTON.setDisable(true);
        CustomersInformationBUTTON.setDisable(true);
    }

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    @FXML
    void LoadFileAction(ActionEvent event) {
        mainController.openFileChooser();
    }

    public void enableAfterFileLoader(){
        IncreaseYazBUTTON.setDisable(false);
        LoansBUTTON.setDisable(false);
        CustomersInformationBUTTON.setDisable(false);
    }


}
