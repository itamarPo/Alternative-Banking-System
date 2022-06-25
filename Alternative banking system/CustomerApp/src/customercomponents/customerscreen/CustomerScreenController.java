package customercomponents.customerscreen;

import com.google.gson.Gson;
import customercomponents.customerlogin.CustomerLoginController;
import database.Engine;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import objects.customers.CustomerInfoDTO;
import objects.customers.CustomersRelatedInfoDTO;
import objects.customers.loanInfo.LoanInfoDTO;
import objects.loans.*;
import objects.loans.payments.PaymentNotificationDTO;
import okhttp3.*;
import org.controlsfx.control.Notifications;
import userinterface.customer.information.InformationTabController;
import userinterface.customer.inlay.InlayTabController;
import userinterface.customer.loanforsell.LoanSellTabController;
import userinterface.customer.payments.PaymentsTabController;
import userinterface.utils.HttpUtil;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.stream.Collectors;

import static userinterface.Constants.*;
import static userinterface.Constants.GSON_INSTANCE;

public class CustomerScreenController {

    //constants
    private final String YAZSTATEMENT = "Current Yaz: " ;
    private final String FILESTATEMENT = "File: " ;
    private final String ADMIN = "Admin";
    private final String THEMEDEFAULT = "/userinterface/customer/TopCustomerDefault.css";
    private final String THEMEDARK = "/userinterface/customer/TopCustomerDark.css";
    private final String THEMEBRIGHT = "/userinterface/customer/TopCustomerBright.css";
    private final String DEFAULT = "Default";
    private final String DARK = "Dark";
    private final String BRIGHT = "Bright";

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
    @FXML private ComboBox<String> ThemeCB;
    @FXML private ScrollPane MainSP;
    @FXML private BorderPane MainBP;
    @FXML private Label FileLABEL;
    @FXML private Label YazLABEL;
    @FXML private TabPane customerOptionsTB;

    @FXML private Tab information;
    @FXML private Tab inlay;
    @FXML private Tab payments;

    //Regular Fields
    private Stage primaryStage;
    private String userName;
    private CustomerLoginController customerLoginController;
    private Timer timer;
    //constructor
    public CustomerScreenController(){

    }

    @FXML
    private void initialize() {
        informationTabController.setCustomerScreenController(this);
        paymentsTabController.setCustomerScreenController(this);
        inlayTabController.setCustomerScreenController(this);
        loanSellTabController.setCustomerScreenController(this);
        customerOptionsTB.getSelectionModel().selectedItemProperty().addListener((ov, oldTab, newTab) -> {
            //String user = UserCB.getValue();
            switch(newTab.getText()){
                case "Information":{
                    //updateInformationTab(user);
                    break;
                } case "Inlay":{
                    updateInlayTab();
                    break;
                } case "Payments":{
                  //  updatePayments(user);
                    break;
                }
                case "Buy/Sell Loans":{
                  //  updateLoanSellTab(user);
                }
            }
        });
        MainSP.getStylesheets().add(THEMEDEFAULT);
    }



    //getters
    public Label getFileLABEL() {return FileLABEL;}
    public ComboBox<String> getUserCB() {return UserCB;}
    public Label getYazLABEL() {return YazLABEL;}
    //public MainController getMainController() {return mainController;}
    public ScrollPane getMainSP() {return MainSP;}
    public ComboBox<String> getThemeCB() {return ThemeCB;}

    //setters
//    public void setMainControllerAndEngine(MainController mainController, Engine engine) {
//        this.mainController = mainController;
//        this.engine = engine;
//        informationTabController.setEngine(this.engine);
//        inlayTabController.setEngine(this.engine);
//        paymentsTabController.setEngine(this.engine);
//        loanSellTabController.setEngine(this.engine);
////        informationTabController.setControllersAndStages();
////        inlayTabController.setControllersAndStages();
////        paymentsTabController.setControllersAndStages();
////        loanSellTabController.setControllersAndStages();
//    }
    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }
    //****Regular Methods****//
//    public void setTopBarAfterFileLoaded(TopAdminController topAdminController){
//        this.FileLABEL.setText(topAdminController.getFileLABEL().getText());
//        this.UserCB.setItems(topAdminController.getUserCB().getItems());
//        this.YazLABEL.setText(topAdminController.getYazLABEL().getText());
//        if(ThemeCB.getItems().size() == 0){
//            ThemeCB.setItems(topAdminController.getThemeCB().getItems());
//        }
//    }

//    public void setTopBar(TopAdminController topAdminController, String newChoice){
//        this.FileLABEL.setText(topAdminController.getFileLABEL().getText());
//        this.UserCB.setValue(newChoice);
//        if(!newChoice.equals(ADMIN))
//            changeInfoFollowedComboBox(newChoice);
//        this.YazLABEL.setText(topAdminController.getYazLABEL().getText());
//        this.ThemeCB.setValue(topAdminController.getThemeCB().getValue());
//    }

//    @FXML
//    public void SetCBOnAction(ActionEvent actionEvent) {
//        String UserPick = UserCB.getValue();
//        if(UserPick.equals(ADMIN)){
//            mainController.changeScene(ADMIN);
////            mainController.getTopAdminController().updateAdminTable();
//        }
//        else {
//            changeInfoFollowedComboBox(UserPick);
//        }
//    }
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
//        mainController.getTopAdminController().updateTheme(ThemeCB.getValue());
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setCustomerLoginController(CustomerLoginController customerLoginController) {
        this.customerLoginController = customerLoginController;
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

    /*Pitaron elganti yoter: kria po le HTTP aim ma she ani zarih*/
    public void startInfoRefresh(){
        CustomerInfoRefresher customerInfoRefresher = new CustomerInfoRefresher(this);
        timer = new Timer();
        timer.schedule(customerInfoRefresher, REFRESH_RATE, REFRESH_RATE);
    }
    public void changeInfoFollowedComboBox(String UserPick){
        String finalUrlInformation = HttpUrl.parse(FULL_PATH_DOMAIN + CUSTOMER_PULL_INFORMATION_RESOURCE)
                .newBuilder().addQueryParameter("userName", UserPick)
                .build()
                .toString();
        Request requestCustomerTable = new Request.Builder()
                .url(finalUrlInformation)
                .build();

        HttpUtil.runAsync(requestCustomerTable,true, new Callback() {
            public void onFailure(Call call, IOException e) {
                System.out.println("problem");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String jsonArrayOfInformation = response.body().string();
                CustomersRelatedInfoDTO CustomersRelatedInfo = GSON_INSTANCE.fromJson(jsonArrayOfInformation, CustomersRelatedInfoDTO.class);
                List<NewLoanDTO> loans = CustomersRelatedInfo.getRelatedLoans();
                CustomerInfoDTO customer = CustomersRelatedInfo.getCustomerInfo();
                List<PaymentNotificationDTO> paymentNotification = CustomersRelatedInfo.getPaymentsNotificationList();
                List<LoansForSaleDTO> loansOnSale = CustomersRelatedInfo.getLoansForSaleList();
                List<String> categories = CustomersRelatedInfo.getCategories();
                Platform.runLater(() ->{
                   // updateInformationTab(UserPick, customer, loans);
                    updatePayments(UserPick, paymentNotification, loans);
                    updateInlayTab();
                    updateLoanSellTab(UserPick, customer, loansOnSale);
                });
            }

        });

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

    public void updateInformationTab (String UserPick, List<NewLoanDTO> loanList, CustomerInfoDTO customerInfo){

//        informationTabController.setUserName(UserPick);
        informationTabController.getTransactionInfoController().setTableValues(customerInfo);
        informationTabController.getBalanceLabel().setText("Balance: "+ customerInfo.getBalance());

        List<NewLoanDTO> temp = loanList.stream().filter(l->l.getBorrowerName().equals(UserPick)).collect(Collectors.toList());
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
        List<NewLoanDTO> temp2 = loanList.stream().filter(x -> !x.getStatus().equals("New")).collect(Collectors.toList());
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
    public void updatePayments(String userPick, List<PaymentNotificationDTO> paymentNotificationList, List<NewLoanDTO> loanList){
        List<NewLoanDTO> temp = loanList.stream().filter(l->l.getBorrowerName().equals(userPick)).collect(Collectors.toList());
        List<ActiveRiskLoanDTO> closeLoanActive = new ArrayList<>();
        List<ActiveRiskLoanDTO> closeLoanRisk = new ArrayList<>();
        List<NewLoanDTO> temp2 = loanList.stream().filter(l->l.getBorrowerName().equals(userPick)).collect(Collectors.toList());
        List<ActiveRiskLoanDTO> makePaymentActive = new ArrayList<>();
        List<ActiveRiskLoanDTO> makePaymentRisk = new ArrayList<>();
        //Loaner Tables
        temp.stream().filter(x -> x.getStatus().equals("Active")).forEach(y -> closeLoanActive.add((ActiveRiskLoanDTO)  y));
        temp.stream().filter(x -> x.getStatus().equals("Risk")).forEach(y -> closeLoanRisk.add((ActiveRiskLoanDTO) y));
        temp2.stream().filter(x -> x.getStatus().equals("Active")).forEach(y -> makePaymentActive.add((ActiveRiskLoanDTO)  y));
        makePaymentActive.removeIf(x -> x.getNextPaymentTime() != Engine.getTime());
        temp2.stream().filter(x -> x.getStatus().equals("Risk")).forEach(y -> makePaymentRisk.add((ActiveRiskLoanDTO)  y));
        makePaymentActive.removeIf(x -> x.getNextPaymentTime() != Engine.getTime());
        paymentsTabController.setValues(paymentNotificationList,makePaymentActive,makePaymentRisk,closeLoanActive,closeLoanRisk);
        paymentsTabController.getFinishImage().setVisible(false);
      //  paymentsTabController.setAnimation(mainController.getTopAdminController().isAnimationOn());
    }
    public void updateInlayTab(){ //TODO: add relevant categories with HTTP call
        inlayTabController.addCategoriesToCCB();
        inlayTabController.resetFields();
    }

    public boolean inlaySumCheck(Double amount){
        final boolean[] sumCheck = {false};
        String finalUrlInformation = HttpUrl.parse(FULL_PATH_DOMAIN + CHECK_INLAY_SUM_RESOURCE)
                .newBuilder().addQueryParameter("Amount", amount.toString())
                .build()
                .toString();
        Request request = new Request.Builder()
                .url(finalUrlInformation)
                .build();
        HttpUtil.runAsync(request, false, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if(!response.isSuccessful())
                {
                    sumCheck[0] = false;
                }
            }
        });
        return false;
    }

    public List<NewLoanDTO> getFilteredLoans(Double amountToInvest,List<String> categories, Integer minInterest, Integer minYAZ, Integer maxOpenLoans, Integer maxOwnership){
        List<NewLoanDTO> filteredLoans;
        Gson gson = new Gson();
        String json = gson.toJson(categories);
//        RequestBody body = RequestBody.create(json, "") // new
//        RequestBody body = RequestBody.create(JSON, jsonObject.toString());
        RequestBody body = RequestBody.create(
                json, MediaType.parse("application/json"));

        String finalUrlInformation = HttpUrl.parse(FULL_PATH_DOMAIN + CUSTOMER_INLAY_FILTER_RESOURCE)
                .newBuilder()
                .addQueryParameter("minInterest", minInterest.toString())
                .addQueryParameter("minYAZ", minYAZ.toString())
                .addQueryParameter("maxOpenLoans", maxOpenLoans.toString())
                .build()
                .toString();
        Request request = new Request.Builder()
                .url(finalUrlInformation)
                .post(body)
                .build();

        HttpUtil.runAsync(request, false, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

            }
        });

    }


    public void updateLoanSellTab(String userPick, CustomerInfoDTO customer, List<LoansForSaleDTO> loansOnSale){
      // CustomerInfoDTO customer = customers.stream().filter(l->l.getName().equals(userPick)).findFirst().orElse(null);
//        CustomerInfoDTO customer = null;
//        for(CustomerInfoDTO customerInfoDTO: customers){
//            if(customerInfoDTO.getName().equals(userPick))
//                customer=customerInfoDTO;
//        }
        List<String> loanID = new ArrayList<>();
        List<String> loansForSale = customer.getLoansForSale().stream().map(LoanInfoDTO::getLoanName).collect(Collectors.toList());
        List<String> lenderLoans = customer.getLenderList().stream().filter(p -> p.getStatus().equals("Active")).map(LoanInfoDTO::getLoanName).collect(Collectors.toList());
        lenderLoans.removeIf(p -> loansForSale.contains(p));
        //List<LoansForSaleDTO> loansOnSale = engine.getLoansAvailableToBuy(userPick);
        loanSellTabController.setValues(lenderLoans,loansOnSale);
    }

    @FXML
    public void LoadFileOnAction(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select xml file");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("xml files", "*.xml"));
        File selectedFile = fileChooser.showOpenDialog(primaryStage);
        if (selectedFile == null) {
            return;
        }
        String absolutePath = selectedFile.getAbsolutePath();
        creatFileRequest(absolutePath);
    }

    public void creatFileRequest(String absolutePath){
        File f = new File(absolutePath);
        RequestBody body =
                new MultipartBody.Builder()
                        .addFormDataPart("file1", f.getName(), RequestBody.create(f, MediaType.parse("text/xml")))
                        //.addFormDataPart("key1", "value1") // you can add multiple, different parts as needed
                        .build();

       Request request = new Request.Builder()
                .url(FULL_PATH_DOMAIN + UPLOAD_FILE)
                .post(body)
                .build();

        HttpUtil.runAsync(request, false, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Platform.runLater(() ->
                        Notifications.create().title("Error").text("Unknown Error").hideAfter(Duration.seconds(5)).position(Pos.CENTER).show());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if(response.code() != 200){
                    Platform.runLater(() ->
                    {
                        try {
                            Notifications.create().title("Error").text(response.body().string()).hideAfter(Duration.seconds(5)).position(Pos.CENTER).show();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    });
                } else{
                    Platform.runLater(() ->
                    {
                        try {
                            Notifications.create().title("Success").text(response.body().string()).hideAfter(Duration.seconds(5)).position(Pos.CENTER).show();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    });
                }
             //   return false;
            }
        });


   //    Call call = HTTP_CLIENT.newCall(request);

     //   Response response = call.execute();

      //  System.out.println(response.body().string());
        return;
    }
}
