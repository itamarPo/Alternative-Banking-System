package userinterface;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import userinterface.MainController.MainController;
import userinterface.admin.TopAdminController;
import userinterface.customer.TopCustomerController;

import java.net.URL;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        //Admin
        FXMLLoader loaderAdmin = new FXMLLoader();
        URL adminFXML = getClass().getResource("/userinterface/admin/TopAdmin.fxml");
        loaderAdmin.setLocation(adminFXML);
        Parent root1 = loaderAdmin.load();
        TopAdminController topAdminController = loaderAdmin.getController();
        Scene AdminScene = new Scene(root1, 600, 400);

        //Customer
        FXMLLoader loaderCustomer = new FXMLLoader();
        URL customerFXML = getClass().getResource("/userinterface/customer/TopCustomer.fxml");
        loaderCustomer.setLocation(customerFXML);
        Parent root2 = loaderAdmin.load();
        TopCustomerController topCustomerController = loaderCustomer.getController();
        Scene CustomerScene = new Scene(root2, 600, 400);

        MainController mainController = new MainController(primaryStage, topAdminController, AdminScene, topCustomerController,CustomerScene);
        mainController.setSubControllers();
        primaryStage.setTitle("Alternative Banking System");
        primaryStage.setScene(AdminScene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
