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
import org.controlsfx.control.Notifications;
import userinterface.admin.TopAdminController;
import database.Engine;
import userinterface.customer.TopCustomerController;
import userinterface.table.AdminCustomerTableController;

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
//        double x = primaryStage.getWidth();
//        double y = primaryStage.getHeight();
        if(newChoice.equals("Admin")){
            topAdminController.setTopBar(topCustomerController,newChoice);
            primaryStage.setScene(AdminScene);
        }
        else{
            topCustomerController.setTopBar(topAdminController,newChoice);
            primaryStage.setScene(CustomerScene);
        }
        primaryStage.setMaximized(true);
//        primaryStage.setWidth(x);
//        primaryStage.setHeight(y);


    }

    public void setSubControllers() {
        topAdminController.setMainControllerAndEngine(this, engine);
        topCustomerController.setMainControllerAndEngine(this, engine);
        topCustomerController.setTopBar(topAdminController,"Admin");
    }

}
