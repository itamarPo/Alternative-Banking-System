package userinterface.MainController;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import userinterface.admin.StartAdminController;
import database.Engine;
import java.io.File;
import java.net.URL;
public class MainController {

    //controls
    @FXML private ComboBox<String> CustomersCB;
    @FXML private Label FileLABEL;
    @FXML private BorderPane MainBP;
    @FXML private Label YazLABEL;
    private Stage primaryStage;
    private BorderPane BP;
    private Engine engine;


    private final String YAZSTATEMENT = "Current Yaz: " ;
    private final String FILESTATMENT = "File: " ;
    //controllers
    private StartAdminController adminController;
    private ObservableList<String> cbNames = FXCollections.observableArrayList("Admin");

    public MainController(){
        engine = new Engine();
    }

    @FXML
    private void initialize()  {
        YazLABEL.setText(YAZSTATEMENT + '0');
        CustomersCB.setItems(cbNames);
    }

    public void setBorderPane(BorderPane borderPane) {
        this.BP = borderPane;
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }
    public  void setStartAdminController() throws  Exception{

        FXMLLoader loader = new FXMLLoader();
        URL mainFXML = getClass().getResource("/userinterface/admin/AdminEntry.fxml");
        loader.setLocation(mainFXML);
        AnchorPane AP = loader.load();
        adminController = loader.getController();
        adminController.setMainController(AP, this);
        BP.setCenter(AP);
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
