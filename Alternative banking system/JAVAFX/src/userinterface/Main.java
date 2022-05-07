package userinterface;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import userinterface.MainController.MainController;

import java.net.URL;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        URL mainFXML = getClass().getResource("/userinterface/MainController/TopBar.fxml");
        loader.setLocation(mainFXML);
        BorderPane root = loader.load();
        MainController controller = loader.getController();

        controller.setPrimaryStage(primaryStage);
        controller.setBorderPane(root);
        controller.setStartAdminController();

        primaryStage.setTitle("Alternative Banking System");
        Scene scene = new Scene(root, 600, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
