package userinterface.admin.topAdmin;

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
    private final String STYLE1 = "/userinterface/admin/topAdmin/TopAdmin1.css";

    //SubComponents
    @FXML private AnchorPane CenterAdmin;
    @FXML private userinterface.admin.centerAdmin.CenterAdminController CenterAdminController;

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
//        MainSP.getStylesheets().add(STYLE1);
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
        this.CenterAdminController.setEngine(engine);
        this.CenterAdminController.setControllersAndStages();
    }

    //Regular Methods
    public void setTopBar(TopCustomerController topCustomerController, String newChoice){
        this.FileLABEL.setText(topCustomerController.getFileLABEL().getText());
        this.UserCB.setValue(newChoice);
        this.YazLABEL.setText(topCustomerController.getYazLABEL().getText());
    }

   public void LoadFileAction(String AbsolutePath) {
        try {
            engine.loadFile(AbsolutePath);
            FileLABEL.setText(FILESTATMENT + AbsolutePath);
            YazLABEL.setText(YAZSTATEMENT + Engine.getTime());
//            UserCB.setItems((ObservableList<String>) engine.getCustomerNames());
            UserCB.getItems().clear();
            UserCB.getItems().add("Admin");
            UserCB.setValue("Admin");
            UserCB.getItems().addAll(engine.getCustomerNames());
            CenterAdminController.enableAfterFileLoader();
            mainController.getTopCustomerController().setTopBarAfterFileLoaded(this);
            updateAdminTable();
            Notifications categoryNotExist = Notifications.create().text("File loaded successfully!").hideAfter(Duration.seconds(5)).position(Pos.CENTER);
            categoryNotExist.show();

        } catch (LoanCategoryNotExistException e) {
            Notifications categoryNotExist = Notifications.create().title("Error").text(e.toString()).hideAfter(Duration.seconds(10)).position(Pos.CENTER);
            categoryNotExist.show();
        } catch (OwnerLoanNotExistException e) {
            Notifications ownerNotExist = Notifications.create().title("Error").text(e.toString()).hideAfter(Duration.seconds(10)).position(Pos.CENTER);
            ownerNotExist.show();
        } catch (TimeOfPaymentNotDivideEqualyException e) {
            Notifications TimeNotDivideEqually = Notifications.create().title("Error").text(e.toString()).hideAfter(Duration.seconds(10)).position(Pos.CENTER);
            TimeNotDivideEqually.show();
        } catch (TwoClientsWithSameNameException e) {
            Notifications categoryNotExist = Notifications.create().title("Error").text(e.toString()).hideAfter(Duration.seconds(10)).position(Pos.CENTER);
            categoryNotExist.show();
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



}
