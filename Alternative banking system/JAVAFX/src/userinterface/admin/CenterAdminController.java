package userinterface.admin;

import database.Engine;
import javafx.event.ActionEvent;
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


    //Constructor
    public CenterAdminController() {

    }

    //initialize after constructor
    @FXML
    private void initialize()  {
        IncreaseYazBUTTON.setDisable(true);
    }



    //****Regular Methods****//
    public void setAdminTopController(TopAdminController topAdminController) {
        this.topAdminController = topAdminController;
    }

    @FXML
    void openFileChooser(ActionEvent event) {
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
