package userinterface.customer;

import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import userinterface.customer.information.InformationTabController;

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
