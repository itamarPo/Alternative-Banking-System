package userinterface.admin;

import database.Engine;
import exceptions.filesexepctions.LoanCategoryNotExistException;
import exceptions.filesexepctions.OwnerLoanNotExistException;
import exceptions.filesexepctions.TimeOfPaymentNotDivideEqualyException;
import exceptions.filesexepctions.TwoClientsWithSameNameException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.util.Duration;
import objects.loans.ActiveRiskLoanDTO;
import objects.loans.FinishedLoanDTO;
import objects.loans.NewLoanDTO;
import objects.loans.PendingLoanDTO;
import org.controlsfx.control.Notifications;
import userinterface.MainController.MainController;
import userinterface.customer.TopCustomerController;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.FileNotFoundException;

public class TopAdminController {

    //constants
    private final String YAZSTATEMENT = "Current Yaz: " ;
    private final String FILESTATMENT = "File: " ;
    private final String ADMIN = "Admin";

    //SubComponents
    @FXML private AnchorPane CenterAdmin;
    @FXML private CenterAdminController CenterAdminController;

    //JavaFX components
    @FXML private ComboBox<String> UserCB;
    @FXML private ScrollPane MainSP;
    @FXML private BorderPane MainBP;
    @FXML private Label FileLABEL;
    @FXML private Label YazLABEL;

    //Regular Fields
    private MainController mainController;
    private Engine engine;


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
    }

    //getters
    public Label getFileLABEL() {return FileLABEL;}
    public ComboBox<String> getUserCB() {return UserCB;}
    public Label getYazLABEL() {return YazLABEL;}
    public MainController getMainController() {return mainController;}
    public Engine getEngine() {return engine;}
    public ScrollPane getMainSP() {return MainSP;}

    //setters
    public void setMainControllerAndEngine(MainController mainController, Engine engine) {
        this.mainController = mainController;
        this.engine = engine;
    }

    //Regular Methods
    public void setTopBar(TopCustomerController topCustomerController, String newChoice){
        this.FileLABEL.setText(topCustomerController.getFileLABEL().getText());
        this.UserCB.setValue(newChoice);
        this.YazLABEL.setText(topCustomerController.getYazLABEL().getText());
    }

    void LoadFileAction(String AbsolutePath) {
        try {
            engine.loadFile(AbsolutePath);
            FileLABEL.setText(FILESTATMENT + AbsolutePath);
            YazLABEL.setText(YAZSTATEMENT + Engine.getTime());
            UserCB.getItems().addAll(engine.getCustomerNames());
            CenterAdminController.enableAfterFileLoader();
            mainController.getTopCustomerController().setTopBarAfterFileLoaded(this);
            CenterAdminController.getNewLoanController().setValues(engine.getLoansInfo());
            CenterAdminController.getCustomerTableController().setValues(engine.getCustomerInfo());
            Notifications categoryNotExist = Notifications.create().text("File loaded successfully!").hideAfter(Duration.seconds(5)).position(Pos.TOP_LEFT);
            categoryNotExist.show();

        } catch (LoanCategoryNotExistException e) {
            Notifications categoryNotExist = Notifications.create().title("Error").text(e.toString()).hideAfter(Duration.seconds(10)).position(Pos.TOP_LEFT);
            categoryNotExist.show();
        } catch (OwnerLoanNotExistException e) {
            Notifications ownerNotExist = Notifications.create().title("Error").text(e.toString()).hideAfter(Duration.seconds(10)).position(Pos.TOP_LEFT);
            ownerNotExist.show();
        } catch (TimeOfPaymentNotDivideEqualyException e) {
            Notifications TimeNotDivideEqually = Notifications.create().title("Error").text(e.toString()).hideAfter(Duration.seconds(10)).position(Pos.TOP_LEFT);
            TimeNotDivideEqually.show();
        } catch (TwoClientsWithSameNameException e) {
            Notifications categoryNotExist = Notifications.create().title("Error").text(e.toString()).hideAfter(Duration.seconds(10)).position(Pos.TOP_LEFT);
            categoryNotExist.show();
        } catch (JAXBException e) {

        } catch (FileNotFoundException e) {

        } catch (Exception e) {

        }
    }
        @FXML
    public void SetCBOnAction(ActionEvent actionEvent) {
        String UserPick = UserCB.getValue();
        if(!UserPick.equals(ADMIN)){
            mainController.changeScene(UserPick);
        }
    }

    public void LoadingNewFile(){

    }
}
