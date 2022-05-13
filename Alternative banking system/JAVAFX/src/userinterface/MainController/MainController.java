package userinterface.MainController;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
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

    //Main Stage
    private Stage primaryStage;
    //Admin
    private TopAdminController topAdminController;
    private Scene AdminScene;
    //Customer
    private TopCustomerController topCustomerController;
    private Scene CustomerScene;

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

    }

    //getters
    public Stage getPrimaryStage() {return primaryStage;}
    public TopCustomerController getTopCustomerController() {return topCustomerController;}

    //****Regular Methods****//
    public void changeScene(String newChoice){
        if(newChoice.equals("Admin")){
            topAdminController.setTopBar(topCustomerController,newChoice);
            primaryStage.setScene(AdminScene);
        }
        else{
            topCustomerController.setTopBar(topAdminController,newChoice);
            primaryStage.setScene(CustomerScene);
        }
    }

    public void setSubControllers() {
        topAdminController.setMainControllerAndEngine(this, engine);
        topCustomerController.setMainControllerAndEngine(this, engine);
        topCustomerController.setTopBar(topAdminController,"Admin");
    }






//    public void openFileChooser(){
//        FileChooser fileChooser = new FileChooser();
//        fileChooser.setTitle("Select xml file");
//        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("xml files", "*.xml"));
//        File selectedFile = fileChooser.showOpenDialog(primaryStage);
//        if (selectedFile == null) {
//            return;
//        }
//
//
//        String absolutePath = selectedFile.getAbsolutePath();
//
//        try {
//            engine.loadFile(absolutePath);
//            FileLABEL.setText(FILESTATMENT + absolutePath);
//            YazLABEL.setText(YAZSTATEMENT + Engine.getTime());
//            UserCB.getItems().addAll(engine.getCustomerNames());
//            adminController.enableAfterFileLoader();
//        } catch (Exception e)  {
//            //TODO: add file check from the engine
//        }
//
//    }



}
