package userinterface.MainController;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import userinterface.admin.TopAdminController;
import database.Engine;
import userinterface.customer.TopCustomerController;

import java.io.File;
import java.net.URL;
public class MainController {

    //constants
    private final String YAZSTATEMENT = "Current Yaz: " ;
    private final String FILESTATMENT = "File: " ;

    //JavaFX components
    @FXML private ComboBox<String> UserCB;
    @FXML private Label FileLABEL;
    @FXML private BorderPane MainBP;
    @FXML private ScrollPane MainSP;
    @FXML private Label YazLABEL;
    private Stage primaryStage;


    //Regular Fields
    private Engine engine;

    //constructor
    public MainController(Stage primaryStage, TopAdminController topAdminController, Scene adminScene, TopCustomerController topCustomerController, Scene customerScene) {
        this.primaryStage = primaryStage;
        this.topAdminController = topAdminController;
        this.AdminScene = adminScene;
        this.topCustomerController = topCustomerController;
        this.CustomerScene = customerScene;
        engine = new Engine();
    }

    //initialize after constructor
    @FXML
    private void initialize()  {
        YazLABEL.setText(YAZSTATEMENT + '0');
        UserCB.getItems().add("Admin");
        UserCB.setValue("Admin");
    }

    //Getters!
    public BorderPane getMainBP() {
        return MainBP;
    }


    //Setters!
    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }


    //controllers creation
    public void setMinorControllers() throws Exception{
        setStartAdminController(); //Admin start scene controller

    }



    public void openFileChooser(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select xml file");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("xml files", "*.xml"));
        File selectedFile = fileChooser.showOpenDialog(primaryStage);
        if (selectedFile == null) {
            return;
        }


        String absolutePath = selectedFile.getAbsolutePath();

        try {
            engine.loadFile(absolutePath);
            FileLABEL.setText(FILESTATMENT + absolutePath);
            YazLABEL.setText(YAZSTATEMENT + Engine.getTime());
            UserCB.getItems().addAll(engine.getCustomerNames());
            adminController.enableAfterFileLoader();
        } catch (Exception e)  {
            //TODO: add file check from the engine
        }

    }



}
