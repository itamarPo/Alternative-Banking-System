package userinterface.customer;

import database.Engine;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import objects.customers.CustomerInfoDTO;
import objects.customers.loanInfo.LoanInfoDTO;
import objects.loans.ActiveRiskLoanDTO;
import objects.loans.FinishedLoanDTO;
import objects.loans.NewLoanDTO;
import objects.loans.PendingLoanDTO;
import userinterface.MainController.MainController;
import userinterface.admin.TopAdminController;
import userinterface.customer.information.InformationTabController;
import userinterface.customer.inlay.InlayTabController;
import userinterface.customer.loanforsell.LoanSellTabController;
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

    @FXML private ScrollPane loanSellTab;
    @FXML private LoanSellTabController loanSellTabController;



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
        loanSellTabController.setTopCustomerController(this);
        customerOptionsTB.getSelectionModel().selectedItemProperty().addListener((ov, oldTab, newTab) -> {
            String user = UserCB.getValue();
            switch(newTab.getText()){
                case "Information":{
                    updateInformationTab(user);
                    break;
                } case "Inlay":{
                    updateInlayTab();
                    break;
                } case "Payments":{
                    updatePayments(user);
                    break;
                }
                case "Buy/Sell Loans":{
                    updateLoanSellTab(user);
                }
            }
        });
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
        paymentsTabController.setEngine(this.engine);
        loanSellTabController.setEngine(this.engine);
        informationTabController.setControllersAndStages();
        inlayTabController.setControllersAndStages();
        paymentsTabController.setControllersAndStages();
        loanSellTabController.setControllersAndStages();
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
            mainController.getTopAdminController().updateAdminTable();
        }
        else {
            changeInfoFollowedComboBox(UserPick);
        }
    }

    public void changeInfoFollowedComboBox(String UserPick){

//        //Information tab changes
//        informationTabController.setUserName(UserPick);
//        informationTabController.getTransactionInfoController().setTableValues(engine.getCustomerInfo().stream().filter(l->l.getName().equals(UserPick)).findFirst().orElse(null));
//        informationTabController.getNewLoanerTableController().setValues(engine.getLoansInfo().stream().filter(p->p.getBorrowerName().equals(UserPick)).filter(p->p.getStatus().equals("New")).collect(Collectors.toList()));
//        informationTabController.getBalanceLabel().setText("Balance: "+
//                engine.getCustomerInfo().stream().filter(l->l.getName().equals(UserPick)).findFirst().orElse(null).getBalance());
//
//
//        List<NewLoanDTO> temp = engine.getLoansInfo().stream().filter(p->p.getBorrowerName().equals(UserPick)).filter(p->p.getStatus().equals("Pending")).collect(Collectors.toList());
//        List<PendingLoanDTO> pending = new ArrayList<>();
//        temp.forEach(x -> pending.add((PendingLoanDTO) x));
       // informationTabController.getPendingLoanerTableController().setValues(pending);
        //Inlay tab changes
        updateInformationTab(UserPick);
        updatePayments(UserPick);
        updateInlayTab();
        updateLoanSellTab(UserPick);
    }

    public InformationTabController getInformationTabController() {
        return informationTabController;
    }

    public PaymentsTabController getPaymentsTabController() {
        return paymentsTabController;
    }

    public InlayTabController getInlayTabController() {
        return inlayTabController;
    }

    public void updateInformationTab (String UserPick){
        informationTabController.setUserName(UserPick);
        informationTabController.getTransactionInfoController().setTableValues(engine.getCustomerInfo().stream().filter(l->l.getName().equals(UserPick)).findFirst().orElse(null));
        informationTabController.getBalanceLabel().setText("Balance: "+
                engine.getCustomerInfo().stream().filter(l->l.getName().equals(UserPick)).findFirst().orElse(null).getBalance());

        List<NewLoanDTO> temp = engine.getLoansInfo().stream().filter(l->l.getBorrowerName().equals(UserPick)).collect(Collectors.toList());
        List<PendingLoanDTO> pending = new ArrayList<>();

        List<ActiveRiskLoanDTO> active = new ArrayList<>();
        List<ActiveRiskLoanDTO> risk = new ArrayList<>();
        List<FinishedLoanDTO> finished = new ArrayList<>();
        //Loaner Tables
        informationTabController.getNewLoanerTableController().setValues(temp.stream().filter(p->p.getStatus().equals("New")).collect(Collectors.toList()));
        temp.stream().filter(x -> x.getStatus().equals("Pending")).forEach(y -> pending.add((PendingLoanDTO) y));
        informationTabController.getPendingLoanerTableController().setValues(pending);
        temp.stream().filter(x -> x.getStatus().equals("Active")).forEach(y -> active.add((ActiveRiskLoanDTO)  y));
        informationTabController.getActiveLoanerTableController().setValues(active);
        temp.stream().filter(x -> x.getStatus().equals("Risk")).forEach(y -> risk.add((ActiveRiskLoanDTO) y));
        informationTabController.getRiskLoanerTableController().setValues(risk);
        temp.stream().filter(x -> x.getStatus().equals("Finished")).forEach(y -> finished.add((FinishedLoanDTO) y));
        informationTabController.getFinishedLoanerTableController().setValues(finished);
        //Lender Tables


        List<NewLoanDTO> temp2 = engine.getLoansInfo().stream().filter(x -> !x.getStatus().equals("New")).collect(Collectors.toList());
        final List<PendingLoanDTO> temp3 = new ArrayList<>();
        temp2.forEach(x -> temp3.add( (PendingLoanDTO) x));
        List<PendingLoanDTO> pendingLenders = temp3.stream().filter(p->p.getListOfLenders().containsKey(UserPick)).collect(Collectors.toList());
        List<ActiveRiskLoanDTO> activeLenders = new ArrayList<>();
        List<ActiveRiskLoanDTO> riskLenders = new ArrayList<>();
        List<FinishedLoanDTO>finishedLenders = new ArrayList<>();

        informationTabController.getPendingLenderTableController().setValues(pendingLenders.stream().filter(p -> p.getStatus().equals("Pending")).collect(Collectors.toList()));
        pendingLenders.stream().filter(p->p.getStatus().equals("Active")).forEach(x -> activeLenders.add((ActiveRiskLoanDTO) x));
        informationTabController.getActiveLenderTableController().setValues(activeLenders.stream().filter(p -> p.getStatus().equals("Active")).collect(Collectors.toList()));
        pendingLenders.stream().filter(p->p.getStatus().equals("Risk")).forEach(x -> riskLenders.add((ActiveRiskLoanDTO) x));
        informationTabController.getRiskLenderTableController().setValues(riskLenders.stream().filter(p -> p.getStatus().equals("Risk")).collect(Collectors.toList()));
        pendingLenders.stream().filter(p->p.getStatus().equals("Finished")).forEach(x -> finishedLenders.add((FinishedLoanDTO) x));
        informationTabController.getFinishedLenderTableController().setValues(finishedLenders.stream().filter(p -> p.getStatus().equals("Finished")).collect(Collectors.toList()));
    }
    public void updatePayments(String userPick){
        List<NewLoanDTO> temp = engine.getLoansInfo().stream().filter(l->l.getBorrowerName().equals(userPick)).collect(Collectors.toList());
        List<ActiveRiskLoanDTO> closeLoanActive = new ArrayList<>();
        List<ActiveRiskLoanDTO> closeLoanRisk = new ArrayList<>();
        List<NewLoanDTO> temp2 = engine.getLoansInfo().stream().filter(l->l.getBorrowerName().equals(userPick)).collect(Collectors.toList());
        List<ActiveRiskLoanDTO> makePaymentActive = new ArrayList<>();
        List<ActiveRiskLoanDTO> makePaymentRisk = new ArrayList<>();
        //Loaner Tables
        temp.stream().filter(x -> x.getStatus().equals("Active")).forEach(y -> closeLoanActive.add((ActiveRiskLoanDTO)  y));
        temp.stream().filter(x -> x.getStatus().equals("Risk")).forEach(y -> closeLoanRisk.add((ActiveRiskLoanDTO) y));
        temp2.stream().filter(x -> x.getStatus().equals("Active")).forEach(y -> makePaymentActive.add((ActiveRiskLoanDTO)  y));
        makePaymentActive.removeIf(x -> x.getNextPaymentTime() != Engine.getTime());
        temp2.stream().filter(x -> x.getStatus().equals("Risk")).forEach(y -> makePaymentRisk.add((ActiveRiskLoanDTO)  y));
        makePaymentActive.removeIf(x -> x.getNextPaymentTime() != Engine.getTime());
        paymentsTabController.setValues(engine.getNotifications(userPick),makePaymentActive,makePaymentRisk,closeLoanActive,closeLoanRisk);
    }
    public void updateInlayTab(){
        inlayTabController.addCategoriesToCCB();
        inlayTabController.resetFields();
    }

    public void updateLoanSellTab(String userPick){
        List<CustomerInfoDTO> customers = engine.getCustomerInfo();
        CustomerInfoDTO customer = null;
        for(CustomerInfoDTO customerInfoDTO: customers){
            if(customerInfoDTO.getName().equals(userPick))
                customer=customerInfoDTO;
        }
        List<String> loanID = new ArrayList<>();
        List<String> loansForSale = customer.getLoansForSale().stream().map(LoanInfoDTO::getLoanName).collect(Collectors.toList());
        List<String> lenderLoans = customer.getLenderList().stream().map(LoanInfoDTO::getLoanName).collect(Collectors.toList());
        lenderLoans.removeIf(p -> loansForSale.contains(p));
        List<ActiveRiskLoanDTO> loansOnSale = engine.getLoansForSale(userPick);
        loanSellTabController.setValues(lenderLoans,loansOnSale);
    }
}
