package userinterface.MainController;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.stage.Stage;
import userinterface.admin.TopAdminController;
import database.Engine;
import userinterface.customer.TopCustomerController;

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

    public TopAdminController getTopAdminController() {return topAdminController;}

    //****Regular Methods****//
    public void changeScene(String newChoice){
//        double width = primaryStage.getWidth();
//        double height = primaryStage.getHeight();
        if(newChoice.equals("Admin")){
            topAdminController.setTopBar(topCustomerController,newChoice);
            primaryStage.setScene(AdminScene);
        }
        else{
            topCustomerController.setTopBar(topAdminController,newChoice);
            primaryStage.setScene(CustomerScene);
        }

//        primaryStage.setWidth(width);
//        primaryStage.setHeight(height);


    }

    public void setSubControllers() {
        topAdminController.setMainControllerAndEngine(this, engine);
        topCustomerController.setMainControllerAndEngine(this, engine);
        topCustomerController.setTopBar(topAdminController,"Admin");
    }

}
