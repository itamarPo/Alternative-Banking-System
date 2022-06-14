package admincomponents.adminlogin;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AdminLoginController {

    //JAVAFX components
    @FXML private TextField nameTextField;
    @FXML private Button loginButton;

    //Constructor
    public AdminLoginController() {

    }

    @FXML
    private void initialize() {
    }


    //Regular fields
    private Stage primaryStage;
    private Scene adminScreenScene;


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
