package userinterface.customer;

import javafx.fxml.FXML;
import javafx.scene.control.Tab;

public class CenterCustomerController {

    //Sub components
    @FXML ScrollPane informationTab;
    @FXML InformationTabController informationTabController;

    //JavaFX components
    @FXML Tab information;
    @FXML Tab inlay;
    @FXML Tab payment;



    //Regular fields
    private TopCustomerController topCustomerController;
}
