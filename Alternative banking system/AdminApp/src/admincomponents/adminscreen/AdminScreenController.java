package admincomponents.adminscreen;

import database.Engine;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import objects.admin.LoanAndCustomerInfoDTO;
import okhttp3.*;
import org.controlsfx.control.*;
import userinterface.table.customerTable.CustomerTableController;
import userinterface.table.loantable.*;
import userinterface.utils.HttpUtil;

import java.io.File;
import java.io.IOException;
import java.util.Timer;

import static userinterface.Constants.*;

public class AdminScreenController {

    //Constants
    private final String THEMEDEFAULT = "/userinterface/admin/TopAdminDefault.css";
    private final String THEMEDARK = "/userinterface/admin/TopAdminDark.css";
    private final String THEMEBRIGHT = "/userinterface/admin/TopAdminBright.css";
    private final String DEFAULT = "Default";
    private final String DARK = "Dark";
    private final String BRIGHT = "Bright";
    private final String YAZSTATEMENT = "Current YAZ: " ;

    //Sub components
    @FXML private ScrollPane newLoan;
    @FXML private NewLoanTableController newLoanController;

    @FXML private ScrollPane pendingLoan;
    @FXML private PendingLoanTableController pendingLoanController;

    @FXML private ScrollPane activeLoan;
    @FXML private ActiveLoanTableController activeLoanController;

    @FXML private ScrollPane riskLoan;
    @FXML private RiskLoanTableController riskLoanController;

    @FXML private ScrollPane finishedLoan;
    @FXML private FinishedLoanTableController finishedLoanController;

    @FXML private ScrollPane customerTable;
    @FXML private CustomerTableController customerTableController;

    //JavaFX components
    @FXML private Button IncreaseYazBUTTON;
    @FXML private Label currentYazLabel;
    @FXML private Label nameLabel;
    @FXML private ComboBox<String> skinCB;
    @FXML private ToggleSwitch rewindToggleSwitch;
    @FXML private ComboBox<String> rewindCB;
    @FXML private AnchorPane AdminAP;
    @FXML private ScrollPane MainSP;

    //Regular Fields
    private Timer timer;
    private String userName;


    //Constructor
    public AdminScreenController() {

    }
    //initialize after constructor
    @FXML
    private void initialize()  {
        skinCB.getItems().addAll(DEFAULT,BRIGHT,DARK);
        rewindCB.getItems().add("1");
        MainSP.getStylesheets().add(THEMEDEFAULT);
        rewindToggleSwitch.selectedProperty().addListener((ov, oldTab, newTab) -> {
            rewindCB.setDisable(!rewindCB.isDisable());
        });
    }




    //Getters
    public Label getCurrentYazLabel() {return currentYazLabel;}
    public Label getNameLabel() {return nameLabel;}
    public NewLoanTableController getNewLoanController() {return newLoanController;}
    public FinishedLoanTableController getFinishLoanController() {return finishedLoanController;}
    public PendingLoanTableController getPendingLoanController() {return pendingLoanController;}
    public ActiveLoanTableController getActiveLoanController() {return activeLoanController;}
    public RiskLoanTableController getRiskLoanController() {return riskLoanController;}
    public CustomerTableController getCustomerTableController() {return customerTableController;}


    //Setters
    public void setUserName(String userName) {this.userName = userName;}

    //    public void setControllersAndStages(){
//        pendingLoanController.setPrimaryStage(topAdminController.getMainController().getPrimaryStage());
//        activeLoanController.setPrimaryStage(topAdminController.getMainController().getPrimaryStage());
//        riskLoanController.setPrimaryStage(topAdminController.getMainController().getPrimaryStage());
//        finishedLoanController.setPrimaryStage(topAdminController.getMainController().getPrimaryStage());
//    }


    @FXML
    public void SetSkinCBOnAction(ActionEvent actionEvent) {
        MainSP.getStylesheets().clear();
        switch(skinCB.getValue()){
            case DEFAULT:{
                MainSP.getStylesheets().add(THEMEDEFAULT);
                break;
            }
            case DARK:{
                MainSP.getStylesheets().add(THEMEDARK);
                break;
            }
            case BRIGHT:{
                MainSP.getStylesheets().add(THEMEBRIGHT);
                break;
            }
        }
    }
    //Regular Methods
    @FXML
    void increaseYazOnAction(ActionEvent event){
        RequestBody body = RequestBody.create("", MediaType.parse("txt"));
        String finalUrlInformation = HttpUrl.parse(FULL_PATH_DOMAIN + ADMIN_INCREASE_YAZ_RESOURCE )
                .newBuilder()
                .build()
                .toString();
        Request request = new Request.Builder()
                .url(finalUrlInformation)
                .post(body)
                .build();
        HttpUtil.runAsync(request, true, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if(!response.isSuccessful()){

                } else{
                    Platform.runLater(() ->{
                        String jsonYaz = null;
                        try {
                            jsonYaz = response.body().string();
                        } catch (IOException e) {

                        }
                        String YAZ = GSON_INSTANCE.fromJson(jsonYaz, String.class);
                        currentYazLabel.setText(YAZSTATEMENT + YAZ);
                    });
                }
            }
        });
        //engine.moveTImeForward2();
        //Integer time = Engine.getTime() ;
      //  topAdminController.getYazLABEL().setText( "Current Yaz: " + time.toString());

        //topAdminController.updateAdminTable();
        //if(topAdminController.isAnimationOn())
           // topAdminController.yazIncreaseAnimation();
    }

    public void disableWhileRewind(){
        IncreaseYazBUTTON.setDisable(true);
    }

    public void enableAfterRewind(){IncreaseYazBUTTON.setDisable(false);}

    public void startInfoRefresh(){
        AdminInfoRefresher adminInfoRefresher = new AdminInfoRefresher(this);
        timer = new Timer();
        timer.schedule(adminInfoRefresher, REFRESH_RATE, REFRESH_RATE);
    }

}
