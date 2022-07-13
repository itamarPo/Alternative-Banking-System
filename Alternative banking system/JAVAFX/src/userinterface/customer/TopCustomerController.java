package userinterface.customer;

import database.Engine;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import objects.customers.CustomerInfoDTO;
import userinterface.MainController.MainController;
import userinterface.admin.TopAdminController;
import userinterface.customer.information.InformationTabController;

import java.util.stream.Stream;

public class TopCustomerController {

    //constants
    private final String YAZSTATEMENT = "Current Yaz: " ;
    private final String FILESTATEMENT = "File: " ;
    private final String ADMIN = "Admin";

    //SubComponents
    @FXML private AnchorPane informationTab;
    @FXML private InformationTabController informationTabController;



    //JavaFX
    @FXML private ComboBox<String> UserCB;
    @FXML private ScrollPane MainSP;
    @FXML private BorderPane MainBP;
    @FXML private Label FileLABEL;
    @FXML private Label YazLABEL;
    @FXML private TabPane customerOptionsTB;

    @FXML private Tab information;
    @FXML private Tab inlay;
    @FXML private Tab payments;

    //Regular Fields
    private MainController mainController;
    private Engine engine;



    //constructor
    public TopCustomerController(){

    }


    //getters
    public Label getFileLABEL() {return FileLABEL;}
    public ComboBox<String> getUserCB() {return UserCB;}
    public Label getYazLABEL() {return YazLABEL;}

    //setters
    public void setMainControllerAndEngine(MainController mainController, Engine engine) {
        this.mainController = mainController;
        this.engine = engine;
        informationTabController.setEngine(this.engine);
    }

    //****Regular Methods****//
    public void setTopBarAfterFileLoaded(TopAdminController topAdminController){
        this.FileLABEL.setText(topAdminController.getFileLABEL().getText());
        this.UserCB.setItems(topAdminController.getUserCB().getItems());
        this.YazLABEL.setText(topAdminController.getYazLABEL().getText());
    }

    public void setTopBar(TopAdminController topAdminController, String newChoice){
        this.FileLABEL.setText(topAdminController.getFileLABEL().getText());
        this.UserCB.setValue(newChoice);
        this.YazLABEL.setText(topAdminController.getYazLABEL().getText());
    }

    @FXML
    public void SetCBOnAction(ActionEvent actionEvent) {
        String UserPick = UserCB.getValue();
        if(UserPick.equals(ADMIN)){
            mainController.changeScene(ADMIN);
        }
        else {
            informationTabController.setUserName(UserPick);
            //CustomerInfoDTO customer = engine.getCustomerInfo().stream().collect().filter(l->(UserPick.equals(CustomerInfoDTO::getName())))
            informationTabController.getTransactionInfoController().setTableValues(getCustomer(UserPick));
            informationTabController.getNewLoanerTableController().setValues(getCustomer(UserPick).getLenderList().);
        }
    }

    //WAS HAVING TROUBLE WITH STREAM. FIX IT ITAY, line 93
    public CustomerInfoDTO getCustomer(String UserPick){
        for(CustomerInfoDTO customer: engine.getCustomerInfo()){
            if(customer.getName().equals(UserPick))
                return customer;
        }
        return null;
    }
}
