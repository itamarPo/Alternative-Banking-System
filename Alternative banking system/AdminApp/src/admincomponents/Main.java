package admincomponents;

import admincomponents.adminlogin.AdminLoginController;
import admincomponents.adminscreen.AdminScreenController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
//import userinterface.MainController.MainController;
//import userinterface.admin.topAdmin.TopAdminController;
//import userinterface.customer.TopCustomerController;

import java.net.URL;

public class Main extends Application {
    private final double WIDTH = 1200;
    private final double HEIGHT = 550;
    @Override
    public void start(Stage primaryStage) throws Exception {
        //Admin Login scene
        FXMLLoader adminLogin = new FXMLLoader();
        URL adminLoginFXML = getClass().getResource("/admincomponents/adminlogin/adminLogin.fxml");
        adminLogin.setLocation(adminLoginFXML);
        Parent root1 = adminLogin.load();
        AdminLoginController adminLoginController = adminLogin.getController();
        Scene adminLoginScene = new Scene(root1, WIDTH, HEIGHT);

        //Admin Screen scene
        FXMLLoader adminScreen = new FXMLLoader();
        URL adminScreenFXML = getClass().getResource("/admincomponents/adminscreen/adminScreen.fxml");
        adminScreen.setLocation(adminScreenFXML);
        Parent root2 = adminScreen.load();
        AdminScreenController adminScreenController = adminScreen.getController();
        Scene adminScreenScene = new Scene(root2,WIDTH,HEIGHT);


//        Customer
//        FXMLLoader loaderCustomer = new FXMLLoader();
//        URL customerFXML = getClass().getResource("/userinterface/customer/TopCustomer.fxml");
//        loaderCustomer.setLocation(customerFXML);
//        Parent root2 = loaderCustomer.load();
//        TopCustomerController topCustomerController = loaderCustomer.getController();
//        Scene CustomerScene = new Scene(root2, WIDTH, HEIGHT);

        //Main Controller
        MainController mainController = new MainController(primaryStage, topAdminController, AdminScene, topCustomerController,CustomerScene);
        mainController.setSubControllers();

        //Start program
        primaryStage.setTitle("Alternative Banking System");
        primaryStage.setScene(AdminScene);
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }
}
