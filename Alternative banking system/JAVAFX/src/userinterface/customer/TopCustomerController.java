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
import userinterface.customer.inlay.InlayTabController;
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
    @FXML private ScrollPane inlayTab;
    @FXML private InlayTabController inlayTabController;





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
        inlayTabController.setTopCustomerController(this);
    }



    //getters
    public Label getFileLABEL() {return FileLABEL;}
    public ComboBox<String> getUserCB() {return UserCB;}
    public Label getYazLABEL() {return YazLABEL;}
    public MainController getMainController() {return mainController;}
    public ScrollPane getMainSP() {return MainSP;}

    //setters
    public void setMainControllerAndEngine(MainController mainController, Engine engine) {
        this.mainController = mainController;
        this.engine = engine;
        informationTabController.setEngine(this.engine);
        inlayTabController.setEngine(this.engine);
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

        //Information tab changes
        informationTabController.setUserName(UserPick);
        informationTabController.getTransactionInfoController().setTableValues(engine.getCustomerInfo().stream().filter(l->l.getName().equals(UserPick)).findFirst().orElse(null));
        informationTabController.getNewLoanerTableController().setValues(engine.getLoansInfo().stream().filter(p->p.getBorrowerName().equals(UserPick)).filter(p->p.getStatus().equals("New")).collect(Collectors.toList()));
        informationTabController.getBalanceLabel().setText("Balance: "+
                engine.getCustomerInfo().stream().filter(l->l.getName().equals(UserPick)).findFirst().orElse(null).getBalance());


        List<NewLoanDTO> temp = engine.getLoansInfo().stream().filter(p->p.getBorrowerName().equals(UserPick)).filter(p->p.getStatus().equals("Pending")).collect(Collectors.toList());
        List<PendingLoanDTO> pending = new ArrayList<>();
        temp.forEach(x -> pending.add((PendingLoanDTO) x));
        informationTabController.getPendingLoanerTableController().setValues(pending);
        //Inlay tab changes
        inlayTabController.addCategoriesToCCB();
        inlayTabController.resetFields();



    }


}
