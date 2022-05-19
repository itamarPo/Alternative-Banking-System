package userinterface.customer;

import database.Engine;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import objects.customers.CustomerInfoDTO;
import objects.customers.loanInfo.LoanInfoDTO;
import objects.loans.NewLoanDTO;
import userinterface.MainController.MainController;
import userinterface.admin.TopAdminController;
import userinterface.customer.information.InformationTabController;
import userinterface.customer.payments.PaymentsTabController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TopCustomerController {

    //constants
    private final String YAZSTATEMENT = "Current Yaz: " ;
    private final String FILESTATEMENT = "File: " ;
    private final String ADMIN = "Admin";

    //SubComponents
    @FXML private AnchorPane informationTab;
    @FXML private InformationTabController informationTabController;

    @FXML private AnchorPane paymentsTab;
    @FXML private PaymentsTabController paymentsTabController;






    //JavaFX
    @FXML private ComboBox<String> UserCB;
    @FXML private ScrollPane MainSP;
    @FXML private BorderPane MainBP;
    @FXML private Label FileLABEL;
    @FXML private Label YazLABEL;
    @FXML private TabPane customerOptionsTB;

    @FXML private Tab information;
    @FXML private Tab inlay;
    @FXML private Tab payments;

    //Regular Fields
    private MainController mainController;
    private Engine engine;



    //constructor
    public TopCustomerController(){

    }

    @FXML
    private void initialize() {
        informationTabController.setTopCustomerController(this);
        paymentsTabController.setTopCustomerController(this);
    }



    //getters
    public Label getFileLABEL() {return FileLABEL;}
    public ComboBox<String> getUserCB() {return UserCB;}
    public Label getYazLABEL() {return YazLABEL;}
    public MainController getMainController() {return mainController;}

    //setters
    public void setMainControllerAndEngine(MainController mainController, Engine engine) {
        this.mainController = mainController;
        this.engine = engine;
        informationTabController.setEngine(this.engine);
    }

    //****Regular Methods****//
    public void setTopBarAfterFileLoaded(TopAdminController topAdminController){
        this.FileLABEL.setText(topAdminController.getFileLABEL().getText());
        this.UserCB.setItems(topAdminController.getUserCB().getItems());
        this.YazLABEL.setText(topAdminController.getYazLABEL().getText());
    }

    public void setTopBar(TopAdminController topAdminController, String newChoice){
        this.FileLABEL.setText(topAdminController.getFileLABEL().getText());
        this.UserCB.setValue(newChoice);
        if(!newChoice.equals(ADMIN))
            changeInfoFollowedComboBox(newChoice);
        this.YazLABEL.setText(topAdminController.getYazLABEL().getText());
    }

    @FXML
    public void SetCBOnAction(ActionEvent actionEvent) {
        String UserPick = UserCB.getValue();
        if(UserPick.equals(ADMIN)){
            mainController.changeScene(ADMIN);
        }
        else {
            changeInfoFollowedComboBox(UserPick);
        }
    }

    public void changeInfoFollowedComboBox(String UserPick){
        informationTabController.setUserName(UserPick);
        //CustomerInfoDTO customer = ;
        informationTabController.getTransactionInfoController().setTableValues(engine.getCustomerInfo().stream().filter(l->l.getName().equals(UserPick)).findFirst().orElse(null));
        informationTabController.getNewLoanerTableController().setValues(engine.getLoansInfo().stream().filter(p->p.getBorrowerName().equals(UserPick)).collect(Collectors.toList()));
        informationTabController.getBalanceLabel().setText(informationTabController.getBalanceLabel().getText()+
                engine.getCustomerInfo().stream().filter(l->l.getName().equals(UserPick)).findFirst().orElse(null).getBalance());
    }


}
