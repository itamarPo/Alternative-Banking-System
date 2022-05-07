package userinterface.admin;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import userinterface.MainController.MainController;

public class StartAdminController {

    //controls
    @FXML private Button CustomersInformationBUTTON;
    @FXML private Button IncreaseYazBUTTON;
    @FXML private Button LoadFileBUTTON;
    @FXML private Button LoansBUTOON;
    @FXML private AnchorPane AdminAnchorPane;
    private MainController mainController;
    private AnchorPane AP;

    @FXML
    private void initialize()  {
        IncreaseYazBUTTON.setDisable(true);
        LoansBUTOON.setDisable(true);
        CustomersInformationBUTTON.setDisable(true);
    }

    public void setMainController(AnchorPane AP, MainController mainController) {
        this.mainController = mainController;
        this.AP = AP;
    }

    @FXML
    void LoadFileAction(ActionEvent event) {
        mainController.openFileChooser();
    }

    public void enableAfterFileLoader(){
        IncreaseYazBUTTON.setDisable(false);
        LoansBUTOON.setDisable(false);
        CustomersInformationBUTTON.setDisable(false);
    }

}
