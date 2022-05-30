package userinterface.admin.topAdmin;

import database.Engine;
import exceptions.filesexepctions.LoanCategoryNotExistException;
import exceptions.filesexepctions.OwnerLoanNotExistException;
import exceptions.filesexepctions.TimeOfPaymentNotDivideEqualyException;
import exceptions.filesexepctions.TwoClientsWithSameNameException;
import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.util.Duration;
import objects.loans.ActiveRiskLoanDTO;
import objects.loans.FinishedLoanDTO;
import objects.loans.NewLoanDTO;
import objects.loans.PendingLoanDTO;
import org.controlsfx.control.Notifications;
import userinterface.MainController.MainController;
import userinterface.customer.TopCustomerController;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TopAdminController {

    //constants
    private final String YAZSTATEMENT = "Current Yaz: " ;
    private final String FILESTATMENT = "File: " ;
    private final String ADMIN = "Admin";
    private final String THEMEDEFAULT = "/userinterface/admin/topAdmin/TopAdminDefault.css";
    private final String THEMEDARK = "/userinterface/admin/topAdmin/TopAdminDark.css";
    private final String THEMEBRIGHT = "/userinterface/admin/topAdmin/TopAdminBright.css";
    private final String DEFAULT = "Default";
    private final String DARK = "Dark";
    private final String BRIGHT = "Bright";

    //SubComponents
    @FXML private AnchorPane CenterAdmin;
    @FXML private userinterface.admin.centerAdmin.CenterAdminController CenterAdminController;

    //JavaFX components
    @FXML private ComboBox<String> UserCB;
    @FXML private ComboBox<String> ThemeCB;
    @FXML private ScrollPane MainSP;
    @FXML private BorderPane MainBP;
    @FXML private Label FileLABEL;
    @FXML private Label YazLABEL;
    @FXML private Button animationToggleButton;

    //Regular Fields
    private MainController mainController;
    private Engine engine;
    private FadeTransition yazTransition;

    private boolean animationOn;
    //constructor
    public TopAdminController() {

    }

    //initialize after constructor
    @FXML
    private void initialize()  {
        if (CenterAdminController != null){
            CenterAdminController.setAdminTopController(this);
        }
        YazLABEL.setText(YAZSTATEMENT + '0');
        UserCB.getItems().add("Admin");
        UserCB.setValue("Admin");
        ThemeCB.getItems().addAll("Default", "Dark", "Bright");
        ThemeCB.setValue("Default");
        MainSP.getStylesheets().add(THEMEDEFAULT);
//        MainSP.getStylesheets().add(THEMEDARK);
//        MainSP.getStylesheets().add(THEMEBRIGHT);
//        MainSP.getStylesheets().set(2,THEMEDEFAULT);

//        MainSP.getStylesheets().add(THEMEBRIGHT);
        yazTransition = new FadeTransition();
        animationOn = true;
    }


    //getters
    public Label getFileLABEL() {return FileLABEL;}
    public ComboBox<String> getUserCB() {return UserCB;}
    public Label getYazLABEL() {return YazLABEL;}
    public MainController getMainController() {return mainController;}
    public Engine getEngine() {return engine;}
    public ScrollPane getMainSP() {return MainSP;}
    public ComboBox<String> getThemeCB() {return ThemeCB;}

    //setters
    public void setMainControllerAndEngine(MainController mainController, Engine engine) {
        this.mainController = mainController;
        this.engine = engine;
        this.CenterAdminController.setEngine(engine);
        this.CenterAdminController.setControllersAndStages();
    }

    //Regular Methods
    public void setTopBar(TopCustomerController topCustomerController, String newChoice){
        this.FileLABEL.setText(topCustomerController.getFileLABEL().getText());
        this.UserCB.setValue(newChoice);
        this.YazLABEL.setText(topCustomerController.getYazLABEL().getText());
        this.ThemeCB.setValue(topCustomerController.getThemeCB().getValue());
    }

   public void LoadFileAction(String AbsolutePath) {
        try {
            engine.loadFile(AbsolutePath);
            FileLABEL.setText(FILESTATMENT + AbsolutePath);
            YazLABEL.setText(YAZSTATEMENT + Engine.getTime());
            UserCB.getItems().clear();
            UserCB.getItems().add("Admin");
            UserCB.setValue("Admin");
            UserCB.getItems().addAll(engine.getCustomerNames());
            CenterAdminController.enableAfterFileLoader();
            mainController.getTopCustomerController().setTopBarAfterFileLoaded(this);
            updateAdminTable();
            Notifications categoryNotExist = Notifications.create().text("File loaded successfully!").hideAfter(Duration.seconds(5)).position(Pos.CENTER);
            categoryNotExist.showInformation();

        } catch (LoanCategoryNotExistException e) {
            Notifications categoryNotExist = Notifications.create().title("Error").text(e.toString()).hideAfter(Duration.seconds(10)).position(Pos.CENTER);
            categoryNotExist.showError();
        } catch (OwnerLoanNotExistException e) {
            Notifications ownerNotExist = Notifications.create().title("Error").text(e.toString()).hideAfter(Duration.seconds(10)).position(Pos.CENTER);
            ownerNotExist.showError();
        } catch (TimeOfPaymentNotDivideEqualyException e) {
            Notifications TimeNotDivideEqually = Notifications.create().title("Error").text(e.toString()).hideAfter(Duration.seconds(10)).position(Pos.CENTER);
            TimeNotDivideEqually.showError();
        } catch (TwoClientsWithSameNameException e) {
            Notifications categoryNotExist = Notifications.create().title("Error").text(e.toString()).hideAfter(Duration.seconds(10)).position(Pos.CENTER);
            categoryNotExist.showError();
        } catch (JAXBException e) {

        } catch (FileNotFoundException e) {

        } catch (Exception e) {

        }
    }

    @FXML
    public void SetCBOnAction(ActionEvent actionEvent) {
        if(UserCB.getItems().size() != 0) {
            String UserPick = UserCB.getValue();
            if (!UserPick.equals(ADMIN)) {
                mainController.changeScene(UserPick);
            }
        }
    }
    @FXML
    public void SetThemeCBOnAction(ActionEvent actionEvent) {
       MainSP.getStylesheets().clear();
        switch(ThemeCB.getValue()){
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
        mainController.getTopCustomerController().updateTheme(ThemeCB.getValue());
    }
    public void updateTheme(String newTheme){
        MainSP.getStylesheets().clear();
        switch (newTheme){
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

    public void updateAdminTable(){
        //updates customer info table
        CenterAdminController.getCustomerTableController().setValues(engine.getCustomerInfo());
        //updates loans info tables
        List<NewLoanDTO> temp = engine.getLoansInfo();
        List<PendingLoanDTO> pending = new ArrayList<>();
        List<ActiveRiskLoanDTO> active = new ArrayList<>();
        List<ActiveRiskLoanDTO> risk = new ArrayList<>();
        List<FinishedLoanDTO> finished = new ArrayList<>();
        //New
        CenterAdminController.getNewLoanController().setValues(temp.stream().filter(p->p.getStatus().equals("New")).collect(Collectors.toList()));
        //pending
        temp.stream().filter(x -> x.getStatus().equals("Pending")).forEach(y -> pending.add((PendingLoanDTO) y));
        CenterAdminController.getPendingLoanController().setValues(pending);
        //active
        temp.stream().filter(x -> x.getStatus().equals("Active")).forEach(y -> active.add((ActiveRiskLoanDTO)  y));
        CenterAdminController.getActiveLoanController().setValues(active);
        //risk
        temp.stream().filter(x -> x.getStatus().equals("Risk")).forEach(y -> risk.add((ActiveRiskLoanDTO) y));
        CenterAdminController.getRiskLoanController().setValues(risk);
        //finished
        temp.stream().filter(x -> x.getStatus().equals("Finished")).forEach(y -> finished.add((FinishedLoanDTO) y));
        CenterAdminController.getFinishLoanController().setValues(finished);

    }

    public void yazIncreaseAnimation(){
        yazTransition.setNode(CenterAdmin);
        yazTransition.setCycleCount(1);
        yazTransition.setDuration(Duration.seconds(1.6));
        yazTransition.setInterpolator(Interpolator.EASE_BOTH);
        yazTransition.setFromValue(0);
        yazTransition.setToValue(1);
        yazTransition.play();
    }

    public void setAnimationButtonOnAction(ActionEvent actionEvent){
        if(animationOn)
        {
            animationOn=false;
            animationToggleButton.setText("Set Animation On");
        }
        else{
            animationOn=true;
            animationToggleButton.setText("Set Animation Off");
        }
        mainController.getTopCustomerController().getPaymentsTabController().setAnimation(animationOn);
    }

    public boolean isAnimationOn() {
        return animationOn;
    }
}
