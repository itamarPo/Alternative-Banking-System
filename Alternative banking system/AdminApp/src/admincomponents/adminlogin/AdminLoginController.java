package admincomponents.adminlogin;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Duration;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.Response;
import org.controlsfx.control.Notifications;
import userinterface.Constants;


import java.io.IOException;

public class AdminLoginController {

    //JAVAFX components
    @FXML private TextField nameTextField;
    @FXML private Button loginButton;

    //Regular fields
    private Stage primaryStage;
    private Scene adminScreenScene;

    //Constructor
    public AdminLoginController() {

    }

    @FXML
    private void initialize() {
    }





    //Setters
    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void setAdminScreenScene(Scene adminScreenScene) {
        this.adminScreenScene = adminScreenScene;
    }

    //Regular Methods
    @FXML
    void loginOnAction(ActionEvent event) {
        primaryStage.setScene(adminScreenScene);
    }


}
