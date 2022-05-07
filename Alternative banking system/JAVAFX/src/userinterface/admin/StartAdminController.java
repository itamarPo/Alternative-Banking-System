package userinterface.admin;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import userinterface.MainController.MainController;

public class StartAdminController {

    // JavaFX components
    @FXML private Button CustomersInformationBUTTON;
    @FXML private Button IncreaseYazBUTTON;
    @FXML private Button LoadFileBUTTON;
    @FXML private Button LoansBUTTON;
    @FXML private AnchorPane AdminAP;

    private MainController mainController;


    //Getters!
    public AnchorPane getAdminAnchorPane() {return AdminAP;}

    //constructor
    public StartAdminController() {

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
