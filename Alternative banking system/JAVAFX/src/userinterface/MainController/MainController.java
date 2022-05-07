package userinterface.MainController;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import userinterface.admin.StartAdminController;
import database.Engine;
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

    //controllers
    private StartAdminController adminController;
    private ObservableList<String> cbNames = FXCollections.observableArrayList("Admin");

    //constructor
    public MainController(){
        engine = new Engine();
    }

    //initialize after constructor
    @FXML
    private void initialize()  {
        YazLABEL.setText(YAZSTATEMENT + '0');
        UserCB.setItems(cbNames);
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
    public void setAllControllers() throws Exception{
        setStartAdminController(); //Admin start scene controller

    }

    public  void setStartAdminController() throws Exception{
        FXMLLoader loader = new FXMLLoader();
        URL mainFXML = getClass().getResource("/userinterface/admin/AdminEntry.fxml");
        loader.setLocation(mainFXML);
        Parent AP = loader.load();
        adminController = loader.getController();
        adminController.setMainController(this);
        MainBP.setCenter(adminController.getAdminAnchorPane());
    }

    public void openFileChooser(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select xml file");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("xml files", "*.xml"));
        File selectedFile = fileChooser.showOpenDialog(primaryStage);
        if (selectedFile == null) {
            return;
        }
        //TODO: add file check from the engine

        String absolutePath = selectedFile.getAbsolutePath();

        try {
            engine.loadFile(absolutePath);
        } catch (Exception e) {

        }
        FileLABEL.setText(FILESTATMENT + absolutePath);
        YazLABEL.setText(YAZSTATEMENT + Engine.getTime());
        adminController.enableAfterFileLoader();

        System.out.println(engine.getCustomerNames());

    }
}
