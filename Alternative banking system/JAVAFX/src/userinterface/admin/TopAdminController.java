package userinterface.admin;

import database.Engine;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
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
    private final String ADMIN = "Admin";

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
    private Engine engine;


    //constructor
    public TopAdminController() {

    }

    //initialize after constructor
    @FXML
    private void initialize()  {
        if (CenterAdminController != null){
            CenterAdminController.setAdminTopController(this);
        }
        YazLABEL.setText(YAZSTATEMENT + '0');
        UserCB.getItems().add("Admin");
        UserCB.setValue("Admin");
    }

    //getters
    public Label getFileLABEL() {return FileLABEL;}
    public ComboBox<String> getUserCB() {return UserCB;}
    public Label getYazLABEL() {return YazLABEL;}
    public MainController getMainController() {return mainController;}
    public Engine getEngine() {return engine;}

    //setters
    public void setMainControllerAndEngine(MainController mainController, Engine engine) {
        this.mainController = mainController;
        this.engine = engine;
    }

    //Regular Methods
    public void setTopBar(TopCustomerController topCustomerController, String newChoice){
        this.FileLABEL.setText(topCustomerController.getFileLABEL().getText());
        this.UserCB.setValue(newChoice);
        this.YazLABEL.setText(topCustomerController.getYazLABEL().getText());
    }

    void LoadFileAction(String AbsolutePath) {
        try {
            engine.loadFile(AbsolutePath);
            FileLABEL.setText(FILESTATMENT + AbsolutePath);
            YazLABEL.setText(YAZSTATEMENT + Engine.getTime());
            UserCB.getItems().addAll(engine.getCustomerNames());
            CenterAdminController.enableAfterFileLoader();
            mainController.getTopCustomerController().setTopBarAfterFileLoaded(this);
        } catch (Exception e)  {
            //TODO: add file check from the engine
        }
        CenterAdminController.getNewLoanController().setValues(engine.getLoansInfo());
    }

    @FXML
    public void SetCBOnAction(ActionEvent actionEvent) {
        String UserPick = UserCB.getValue();
        if(!UserPick.equals(ADMIN)){
            mainController.changeScene(UserPick);
        }
    }





}
