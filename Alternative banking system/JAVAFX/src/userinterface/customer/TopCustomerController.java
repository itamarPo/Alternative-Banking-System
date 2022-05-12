package userinterface.customer;

import database.Engine;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import userinterface.MainController.MainController;
import userinterface.admin.TopAdminController;

public class TopCustomerController {

    //constants
    private final String YAZSTATEMENT = "Current Yaz: " ;
    private final String FILESTATMENT = "File: " ;
    private final String ADMIN = "Admin";

    //SubComponents
    @FXML private AnchorPane CenterCustomer;
    @FXML private CenterCustomerController CenterCustomerController;

    //JavaFX
    @FXML private ComboBox<String> UserCB;
    @FXML private ScrollPane MainSP;
    @FXML private BorderPane MainBP;
    @FXML private Label FileLABEL;
    @FXML private Label YazLABEL;

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
    }

    //Regular methods
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
    }
}
