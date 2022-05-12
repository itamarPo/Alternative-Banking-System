package userinterface.admin;

import database.Engine;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;


import java.io.File;

public class CenterAdminController {

    //JavaFX components
    @FXML private Button IncreaseYazBUTTON;
    @FXML private Button LoadFileBUTTON;
    @FXML private AnchorPane AdminAP;

    //Regular Fields
    private TopAdminController topAdminController;

    //initialize after constructor
    @FXML
    private void initialize()  {

    }



    //Regular methods
    public void setAdminTopController(TopAdminController topAdminController) {
        topAdminController = topAdminController;
    }

    public void openFileChooser(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select xml file");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("xml files", "*.xml"));
        File selectedFile = fileChooser.showOpenDialog(topAdminController.getMainController().getPrimaryStage());
        if (selectedFile == null) {
            return;
        }
        String absolutePath = selectedFile.getAbsolutePath();
        topAdminController.LoadFileAction(absolutePath);

    }

    public void enableAfterFileLoader(){
        IncreaseYazBUTTON.setDisable(false);
    }
}
