package customercomponents.customerlogin;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class CustomerLoginController {

    //JAVAFX components
    @FXML private TextField nameTextField;
    @FXML private Button loginButton;

    //Regular fields
    private Stage primaryStage;
    private Scene customerScreenScene;


    //Setters
    public void setPrimaryStage(Stage primaryStage) {this.primaryStage = primaryStage;}
    public void setCustomerScreenScene(Scene customerScreenScene) {this.customerScreenScene = customerScreenScene;}

    //Regular Methods
    @FXML
    void loginOnAction(ActionEvent event) {
        primaryStage.setScene(customerScreenScene);
    }

}