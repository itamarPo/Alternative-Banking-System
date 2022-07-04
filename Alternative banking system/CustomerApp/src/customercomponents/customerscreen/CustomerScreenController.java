package customercomponents.customerscreen;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
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
import objects.customers.CustomerInfoInlayDTO;
import objects.customers.CustomersRelatedInfoDTO;
import objects.customers.PaymentUpdateDTO;
import objects.customers.loanInfo.BuySellUpdateDTO;
import objects.customers.loanInfo.CustomerFilterLoansDTO;
import objects.customers.loanInfo.LoanInfoDTO;
import objects.loans.*;
import objects.loans.payments.PaymentNotificationDTO;
import okhttp3.*;
import org.controlsfx.control.Notifications;
import userinterface.customer.createloan.CreateLoanTabController;
import userinterface.customer.information.InformationTabController;
import userinterface.customer.inlay.InlayTabController;
import userinterface.customer.loanforsell.LoanSellTabController;
import userinterface.customer.payments.PaymentsTabController;
import userinterface.table.loantable.NewLoanTableController;
import userinterface.table.loantable.tableobject.NewLoanTableObject;
import userinterface.utils.HttpUtil;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
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

    @FXML private ScrollPane createLoanTab;
    @FXML private CreateLoanTabController createLoanTabController;

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
    @FXML private Tab createLoan;

    //Regular Fields
    private Stage primaryStage;
    private String userName;
    private CustomerLoginController customerLoginController;
    //TODO: add last seen yaz, so when the yaz change we can know about it through the refresher! same for rewind!
    //TODO: itamar update 04/07/22 before dawn: i fixed the issue i wrote to you in whatsapp, it was too late so i didnt want to
    //TODO: nudge. also buying loan now works. all the remaining TODOs are relevant.
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
        createLoanTabController.setCustomerScreenController(this);
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
                    updatePayments();
                    break;
                }
                case "Buy/Sell Loans":{
                    updateLoanSellTab();
                }
                case "Create Loan":{
                    updateCreateLoanTab();
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
    public String getUserName() {return userName;}
    public Stage getPrimaryStage() {return primaryStage;}
    public InformationTabController getInformationTabController() {
        return informationTabController;
    }
    public PaymentsTabController getPaymentsTabController() {
        return paymentsTabController;
    }
    public InlayTabController getInlayTabController() {
        return inlayTabController;
    }

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
    public void startInfoRefresh(String customerName){
        CustomerInfoRefresher customerInfoRefresher = new CustomerInfoRefresher(this, customerName);
        timer = new Timer();
        timer.schedule(customerInfoRefresher, REFRESH_RATE, REFRESH_RATE);
    }
//    public void changeInfoFollowedComboBox(String UserPick){
//        String finalUrlInformation = HttpUrl.parse(FULL_PATH_DOMAIN + CUSTOMER_PULL_INFORMATION_RESOURCE)
//                .newBuilder().addQueryParameter("userName", UserPick)
//                .build()
//                .toString();
//        Request requestCustomerTable = new Request.Builder()
//                .url(finalUrlInformation)
//                .build();
//
//        HttpUtil.runAsync(requestCustomerTable,true, new Callback() {
//            public void onFailure(Call call, IOException e) {
//                System.out.println("problem");
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                String jsonArrayOfInformation = response.body().string();
//                CustomersRelatedInfoDTO CustomersRelatedInfo = GSON_INSTANCE.fromJson(jsonArrayOfInformation, CustomersRelatedInfoDTO.class);
//                List<NewLoanDTO> loans = CustomersRelatedInfo.getRelatedLoans();
//                CustomerInfoDTO customer = CustomersRelatedInfo.getCustomerInfo();
//                List<PaymentNotificationDTO> paymentNotification = CustomersRelatedInfo.getPaymentsNotificationList();
//                List<LoansForSaleDTO> loansOnSale = CustomersRelatedInfo.getLoansForSaleList();
//                List<String> categories = CustomersRelatedInfo.getCategories();
//                Platform.runLater(() ->{
//                   // updateInformationTab(UserPick, customer, loans);
//                    updatePayments(UserPick, paymentNotification, loans);
//                    updateInlayTab();
//                    updateLoanSellTab(UserPick, customer, loansOnSale);
//                });
//            }
//
//        });
//
//    }



    public void updateInformationTab (String UserPick, List<NewLoanDTO> newLoans, List<PendingLoanDTO> pendingLoans, List<ActiveRiskLoanDTO> activeLoans, List<ActiveRiskLoanDTO> riskLoans , List<FinishedLoanDTO> finishedLoans, CustomerInfoDTO customerInfo){
        //Transactions and balance
        informationTabController.setUserName(UserPick);
        informationTabController.getTransactionInfoController().setTableValues(customerInfo);
        informationTabController.getBalanceLabel().setText("Balance: "+ customerInfo.getBalance());

        //Loaner Tables
        informationTabController.getNewLoanerTableController().setValues(newLoans);
        informationTabController.getPendingLoanerTableController().setValues(pendingLoans.stream().filter(l -> l.getBorrowerName().equals(UserPick)).collect(Collectors.toList()));
        informationTabController.getActiveLoanerTableController().setValues(activeLoans.stream().filter(l -> l.getBorrowerName().equals(UserPick)).collect(Collectors.toList()));
        informationTabController.getRiskLoanerTableController().setValues(riskLoans.stream().filter(l -> l.getBorrowerName().equals(UserPick)).collect(Collectors.toList()));
        informationTabController.getFinishedLoanerTableController().setValues(finishedLoans.stream().filter(l -> l.getBorrowerName().equals(UserPick)).collect(Collectors.toList()));

        //Lender Tables
        informationTabController.getPendingLenderTableController().setValues(pendingLoans.stream().filter(l -> l.getListOfLenders().containsKey(UserPick)).collect(Collectors.toList()));
        informationTabController.getActiveLenderTableController().setValues(activeLoans.stream().filter(l -> l.getListOfLenders().containsKey(UserPick)).collect(Collectors.toList()));
        informationTabController.getRiskLenderTableController().setValues(riskLoans.stream().filter(l -> l.getListOfLenders().containsKey(UserPick)).collect(Collectors.toList()));
        informationTabController.getFinishedLenderTableController().setValues(finishedLoans.stream().filter(l -> l.getListOfLenders().containsKey(UserPick)).collect(Collectors.toList()));

    }

    public void transactionUpdate(Boolean chargeOrWithdraw, final Double amount){
        //Gson gson = new Gson();
        //String json = gson.toJson(categories);
//        RequestBody body = RequestBody.create(json, "") // new
//        RequestBody body = RequestBody.create(JSON, jsonObject.toString());
        RequestBody body = RequestBody.create(
                "", MediaType.parse("txt"));

        String finalUrlInformation = HttpUrl.parse(FULL_PATH_DOMAIN + TRANSACTION_POPUP_IMPLEMENTATION)
                .newBuilder()
                .addQueryParameter(AMOUNT, amount.toString())
                .addQueryParameter(USERNAME, userName)
                .addQueryParameter("chargeOrWithdraw", chargeOrWithdraw.toString())
                .build()
                .toString();
        Request request = new Request.Builder()
                .url(finalUrlInformation)
                .post(body)
                .build();

        HttpUtil.runAsync(request, false, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println("call = " + call + ", e = " + e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if(!response.isSuccessful()){
                    Platform.runLater(()-> {
                        try {
                            informationTabController.getTransactionInfoController().getPopUpController().setErrorMessage(response.body().string());
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    });
                }
                else{
                    CustomerInfoDTO customerInfo = GSON_INSTANCE.fromJson(response.body().string(), CustomerInfoDTO.class);
                    Platform.runLater(()->{
                        informationTabController.getBalanceLabel().setText("Balance: " + customerInfo.getBalance());
                        informationTabController.getTransactionInfoController().setTableValues(customerInfo);
                        informationTabController.getTransactionInfoController().getPopUpController().setErrorMessage(null);
                        informationTabController.getTransactionInfoController().getPopUpController().getPopUpStage().close();});
                }
            }
        });
    }
    public void updatePayments(){
        String finalUrlInformation = HttpUrl.parse(FULL_PATH_DOMAIN + CUSTOMER_PAYMENT_INFO_RESOURCE)
                .newBuilder()
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
                if(response.isSuccessful()){
                    PaymentUpdateDTO paymentUpdateDTO = GSON_INSTANCE.fromJson(response.body().string(), PaymentUpdateDTO.class);
                    Platform.runLater(() ->{
                        paymentsTabController.setValues(paymentUpdateDTO.getPaymentNotifications(), paymentUpdateDTO.getMakeActivePayment(),
                                paymentUpdateDTO.getRiskLoans(), paymentUpdateDTO.getCloseActiveLoans());
                        paymentsTabController.getCloseLoanError().setText("");
                        paymentsTabController.getCompletePaymentError().setText("");
                    });
                }
            }
        });
    }


//    public void updatePayments(String userPick, List<PaymentNotificationDTO> paymentNotificationList, List<NewLoanDTO> loanList){
//        List<NewLoanDTO> temp = loanList.stream().filter(l->l.getBorrowerName().equals(userPick)).collect(Collectors.toList());
//        List<ActiveRiskLoanDTO> closeLoanActive = new ArrayList<>();
//        List<ActiveRiskLoanDTO> closeLoanRisk = new ArrayList<>();
//        List<NewLoanDTO> temp2 = loanList.stream().filter(l->l.getBorrowerName().equals(userPick)).collect(Collectors.toList());
//        List<ActiveRiskLoanDTO> makePaymentActive = new ArrayList<>();
//        List<ActiveRiskLoanDTO> makePaymentRisk = new ArrayList<>();
//        //Loaner Tables
//        temp.stream().filter(x -> x.getStatus().equals("Active")).forEach(y -> closeLoanActive.add((ActiveRiskLoanDTO)  y));
//        temp.stream().filter(x -> x.getStatus().equals("Risk")).forEach(y -> closeLoanRisk.add((ActiveRiskLoanDTO) y));
//        temp2.stream().filter(x -> x.getStatus().equals("Active")).forEach(y -> makePaymentActive.add((ActiveRiskLoanDTO)  y));
//        makePaymentActive.removeIf(x -> x.getNextPaymentTime() != Engine.getTime());
//        temp2.stream().filter(x -> x.getStatus().equals("Risk")).forEach(y -> makePaymentRisk.add((ActiveRiskLoanDTO)  y));
//        makePaymentActive.removeIf(x -> x.getNextPaymentTime() != Engine.getTime());
//        paymentsTabController.setValues(paymentNotificationList,makePaymentActive,makePaymentRisk,closeLoanActive,closeLoanRisk);
//        paymentsTabController.getFinishImage().setVisible(false);
//      //  paymentsTabController.setAnimation(mainController.getTopAdminController().isAnimationOn());
//    }
    public void updateInlayTab(){
        //final List<String>[] categories = (List<String>[]) new Object[1];
        String finalUrlInformation = HttpUrl.parse(FULL_PATH_DOMAIN + CUSTOMER_PULL_CATEGORIES_RESOURCE)
                .newBuilder()
                .build()
                .toString();
        Request request = new Request.Builder()
                .url(finalUrlInformation)
                .build();

        HttpUtil.runAsync(request, false, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Notifications.create().title("Error").text("Error getting call").hideAfter(Duration.seconds(3)).position(Pos.CENTER).showError();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                if(!response.isSuccessful()) {
                    Notifications.create().title("Error").text(response.body().string()).hideAfter(Duration.seconds(3)).position(Pos.CENTER).showError();
                    Platform.runLater(()->{
                        inlayTabController.addCategoriesToCCB(null);
                        inlayTabController.resetFields();});
                }
                else
                {
                    List<String> categories;
                    categories = Arrays.asList(GSON_INSTANCE.fromJson(response.body().string(), String[].class)).stream().collect(Collectors.toList());
                    Platform.runLater(()->{
                        inlayTabController.addCategoriesToCCB(categories);
                        inlayTabController.resetFields();});
                }
            }
        });

    }
    public int inlayGetNumOfLoans(){
        return 0;
    }

    public void inlaySumCheck(final Double amount/*,final List<CustomerInfoInlayDTO> customerInfoInlayDTO*/, final int maxOwnerShip,
                                              final List<String> categories, final int minInterest ,final int minYaz ){
       // final CustomerInfoInlayDTO customerInfoInlayDTO[] = new CustomerInfoInlayDTO[1];
        //customerInfoInlayDTO.add(new CustomerInfoInlayDTO(false, "", 0));

       // customerInfoInlayDTO[0] = new CustomerInfoInlayDTO(false, "", 0);
        String finalUrlInformation = HttpUrl.parse(FULL_PATH_DOMAIN + CHECK_INLAY_INPUT_RESOURCE)
                .newBuilder().addQueryParameter("Amount", amount.toString())
                .build()
                .toString();
        Request request = new Request.Builder()
                .url(finalUrlInformation)
                .build();
            HttpUtil.runAsync(request, false, new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    System.out.println("call = " + call + ", e = " + e);
                }
                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    Gson gson = GSON_INSTANCE;
                    CustomerInfoInlayDTO customerInfoInlay = gson.fromJson(response.body().string(), CustomerInfoInlayDTO.class);
//                    customerInfoInlayDTO.get(customerInfoInlayDTO.size()-1).setOpenLoans(customerInfoInlay.getOpenLoans());
//                    customerInfoInlayDTO.get(customerInfoInlayDTO.size()-1).setResult(customerInfoInlay.getResult());
//                    customerInfoInlayDTO.get(customerInfoInlayDTO.size()-1).setWithDrawException(customerInfoInlay.isWithDrawException());
                    Platform.runLater(()->
                            inlayTabController.filterCheckAndContinue(customerInfoInlay, maxOwnerShip, categories, minInterest, minYaz));
                }
            });
    }

    public void getFilteredLoans(List<String> categories, Integer minInterest, Integer minYAZ, Integer maxOpenLoans , Integer amountToInvest){
        Gson gson = new Gson();
        String json = gson.toJson(categories);
        RequestBody body = RequestBody.create(
                json, MediaType.parse("application/json"));

        String finalUrlInformation = HttpUrl.parse(FULL_PATH_DOMAIN + CHECK_INLAY_INPUT_RESOURCE)
                .newBuilder()
                .addQueryParameter("minInterest", String.valueOf(minInterest))
                .addQueryParameter("minYAZ", String.valueOf(minYAZ))
                .addQueryParameter("maxOpenLoans", String.valueOf(maxOpenLoans))
                .addQueryParameter(AMOUNT, String.valueOf(amountToInvest))
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
                if(!response.isSuccessful()) {
                    Platform.runLater(() -> {
                        try {
                            String error = response.body().string();
                            Notifications.create().title("Error").text(error).hideAfter(Duration.seconds(5)).position(Pos.CENTER).showError();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
//                        String errorMessage = userName + " doesn't have enough money in account!";

                    });
                } else
                {
                    String responseJson = response.body().string();
                    response.body().close();
                    CustomerFilterLoansDTO filteredLoans = GSON_INSTANCE.fromJson(responseJson, CustomerFilterLoansDTO.class);
                    List<NewLoanDTO> newLoans = filteredLoans.getNewLoans();
                    List<PendingLoanDTO> pendingLoans = filteredLoans.getPendingLoans();
                    Platform.runLater(()->inlayTabController.inlayImplement(newLoans, pendingLoans));
                }
            }
        });
    }

    public void makeInlay(List<NewLoanDTO> loans, Integer amountToInvest, Integer maxOwnership){
        Gson gson = new Gson();
        String json = gson.toJson(loans.stream().map(NewLoanDTO::getLoanID).collect(Collectors.toList()));
        RequestBody body = RequestBody.create(
                json, MediaType.parse("application/json"));
        String finalUrlInformation = HttpUrl.parse(FULL_PATH_DOMAIN + CUSTOMER_MAKE_INLAY_RESOURCE)
                .newBuilder()
                .addQueryParameter(AMOUNT, String.valueOf(amountToInvest))
                .addQueryParameter("maxOwnership", String.valueOf(maxOwnership))
                .build()
                .toString();
        Request request = new Request.Builder()
                .url(finalUrlInformation)
                .post(body)
                .build();

        HttpUtil.runAsync(request, false, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println("call = " + call + ", e = " + e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if(!response.isSuccessful()){
                    Platform.runLater(()->{
                        try {
                            Notifications.create().title("Error").text(response.body().string()).hideAfter(Duration.seconds(5)).position(Pos.CENTER).showError();
                        } catch (IOException e) {

                        }
                    });
                } else{
                    Platform.runLater(() -> {
                        inlayTabController.resetFields();
                        Notifications successInlay = Notifications.create().title("Success").text("The Inlay was successfully complete!").hideAfter(Duration.seconds(5)).position(Pos.CENTER);
                        successInlay.showInformation();
                    });
                }
            }
        });
    }


    public void updateLoanSellTab(){
        Request request = new Request.Builder()
                .url(FULL_PATH_DOMAIN + CUSTOMER_BUYSELL_PULL_RESOURCE)
                .build();
        HttpUtil.runAsync(request, false, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if(!response.isSuccessful()){

                } else{
                    String responseJson = response.body().string();
                    response.body().close();
                    BuySellUpdateDTO buySellLoans = GSON_INSTANCE.fromJson(responseJson, BuySellUpdateDTO.class);
                    List<String> loansAvailableToSell = buySellLoans.getLoansAvailableToSell();
                    List<LoansForSaleDTO> loansAvailableToBuy = buySellLoans.getLoansAvailableToBuy();
                    Platform.runLater(() ->{
                        loanSellTabController.setValues(loansAvailableToSell,loansAvailableToBuy);
                    });


                }
            }
        });
      // CustomerInfoDTO customer = customers.stream().filter(l->l.getName().equals(userPick)).findFirst().orElse(null);
//        CustomerInfoDTO customer = null;
//        for(CustomerInfoDTO customerInfoDTO: customers){
//            if(customerInfoDTO.getName().equals(userPick))
//                customer=customerInfoDTO;
//        }
//        List<String> loanID = new ArrayList<>();
//        List<String> loansForSale = customer.getLoansForSale().stream().map(LoanInfoDTO::getLoanName).collect(Collectors.toList());
//        List<String> lenderLoans = customer.getLenderList().stream().filter(p -> p.getStatus().equals("Active")).map(LoanInfoDTO::getLoanName).collect(Collectors.toList());
//        lenderLoans.removeIf(p -> loansForSale.contains(p));
//        //List<LoansForSaleDTO> loansOnSale = engine.getLoansAvailableToBuy(userPick);

    }

    public void updateCreateLoanTab(){
        Request request = new Request.Builder()
                .url(FULL_PATH_DOMAIN + CUSTOMER_PULL_CATEGORIES_RESOURCE)
                .build();
        HttpUtil.runAsync(request, false, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println("call = " + call + ", e = " + e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Platform.runLater(()->
                {
                    try {
                        createLoanTabController.setCategoryCB(Arrays.asList(GSON_INSTANCE.fromJson(response.body().string(), String[].class)));
                        createLoanTabController.resetFields();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
            }
        });
    }

    public void LoanNameCheckAndCreate(String loanID,String category, Double amount, Integer loanDuration, Integer timePerPayment, Integer loanInterest){
//        final boolean[] checkName = new boolean[1];
//        checkName[0] = false;
//        if(loanNameTF.getText().equals("") || loanNameTF.getText().equals(null))
//        {
//            nameError.setText("Please insert a name.");
//            return false;
//        }
        RequestBody body = RequestBody.create(
                "", MediaType.parse("txt"));

            String finalUrlLoansTable = HttpUrl.parse(FULL_PATH_DOMAIN + CREATE_LOAN_RESOURCE)
                    .newBuilder()
                    .addQueryParameter("loanID", loanID)
                    .addQueryParameter("category", category)
                    .addQueryParameter(AMOUNT, amount.toString())
                    .addQueryParameter("loanDuration", loanDuration.toString())
                    .addQueryParameter("timePerPayment", timePerPayment.toString())
                    .addQueryParameter("loanInterest", loanInterest.toString())
                    .build()
                    .toString();
            Request request = new Request.Builder()
                    .url(finalUrlLoansTable)
                    .post(body)
                    .build();
            HttpUtil.runAsync(request, false, new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    System.out.println("error");
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if (response.isSuccessful()) {
                        Platform.runLater(()->{
                                Notifications.create().title("Loan Created!").text("The loan was successfully created!").hideAfter(Duration.seconds(3)).position(Pos.CENTER).showInformation();
                                createLoanTabController.resetFields();});

                    } else{
                        Platform.runLater(()->createLoanTabController.setNameError("Loan's name already exists!"));
                    }
                }
            });
//        if (checkName[0]==true)
//            return false;
//        return true;

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
        createFileRequest(absolutePath);
    }

    public void createFileRequest(String absolutePath){
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
                if(!response.isSuccessful()){
                    Platform.runLater(() ->
                    {
                        try {
                            Notifications.create().title("Error").text(response.body().string()).hideAfter(Duration.seconds(5)).position(Pos.CENTER).show();
                        } catch (IOException e) {
//                            throw new RuntimeException(e);
                        }
                    });
                } else{
                    Platform.runLater(() ->
                    {
                        try {
                            Notifications.create().title("Success").text(response.body().string()).hideAfter(Duration.seconds(5)).position(Pos.CENTER).show();
                        } catch (IOException e) {
//                            throw new RuntimeException(e);
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
    public void makePayment(String loanID, String activeOrRisk, Double amountToPay){
        RequestBody body = RequestBody.create(
                "", MediaType.parse("txt"));

        String finalUrlLoansTable = HttpUrl.parse(FULL_PATH_DOMAIN + CUSTOMER_MAKE_PAYMENT_RESOURCE)
                .newBuilder()
                .addQueryParameter("loanID", loanID)
                .addQueryParameter("activeOrRisk", activeOrRisk)
                .addQueryParameter(AMOUNT, String.valueOf(amountToPay))
                .build()
                .toString();
        Request request = new Request.Builder()
                .url(finalUrlLoansTable)
                .post(body)
                .build();
        HttpUtil.runAsync(request, false, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if(!response.isSuccessful()){
                    Platform.runLater(()->{
                        try {
                            Notifications.create().title("Error").text(response.body().string()).hideAfter(Duration.seconds(5)).position(Pos.CENTER).showError();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        paymentsTabController.getCompletePaymentError().setText("");});

                } else{
                    Platform.runLater(()->{
                        Notifications.create().text("Payment completed Successfully!").hideAfter(Duration.seconds(5)).position(Pos.CENTER).showConfirm();
                        updatePayments();});

                }
            }
        });
    }

    public void closeLoan(String loanName){
        RequestBody body = RequestBody.create(
                "", MediaType.parse("txt"));
        String finalUrlInformation = HttpUrl.parse(FULL_PATH_DOMAIN + CUSTOMER_CLOSE_LOAN_RESOURCE)
                .newBuilder()
                .addQueryParameter("loanID", loanName)
                .build()
                .toString();
        Request request = new Request.Builder()
                .url(finalUrlInformation).post(body)
                .build();
        HttpUtil.runAsync(request, false, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if(!response.isSuccessful())
                {
                    Platform.runLater(()->
                    {
                        try {
                            Notifications.create().title("Error").text(response.body().string()).hideAfter(Duration.seconds(3)).position(Pos.CENTER).showError();
                            paymentsTabController.getCloseLoanError().setText("");

                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    });

                }
                else {
                    Platform.runLater(()
                            ->Notifications.create().title("Success").text("The loan was successfully closed!").hideAfter(Duration.seconds(4)).position(Pos.CENTER).showInformation());
                    updatePayments();
                }
            }
        });
    }
    public void putLoansOnSale(List<String> loansToSale){
        Gson gson = new Gson();
        String json = gson.toJson(loansToSale, new TypeToken<List<String>>(){}.getType());
        RequestBody body = RequestBody.create(json, MediaType.parse("application/json"));
        String finalUrlInformation = HttpUrl.parse(FULL_PATH_DOMAIN + CUSTOMER_SELL_LOANS_RESOURCE )
                .newBuilder()
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
                Platform.runLater(() -> {
                    if (!response.isSuccessful()) {
                        try {
                            Notifications.create().title("Error").text(response.body().string()).hideAfter(Duration.seconds(5)).position(Pos.CENTER).showError();
                        } catch (IOException e) {

                        }
                    } else {
                        Notifications.create().title("Success").text("The chosen loans has moved to the transfer list!").hideAfter(Duration.seconds(5)).position(Pos.CENTER).showConfirm();
                    }
                    updateLoanSellTab();
                });
            }
        });
    }
    public void buyLoan(LoansForSaleDTO loansForSale){
        Gson gson = new Gson();
        String json = gson.toJson(loansForSale, new TypeToken<LoansForSaleDTO>(){}.getType());
        RequestBody body = RequestBody.create(json, MediaType.parse("application/json"));
        String finalUrlInformation = HttpUrl.parse(FULL_PATH_DOMAIN + CUSTOMER_BUY_LOAN_RESOURCE )
                .newBuilder()
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
                Platform.runLater(()->{
                    if(response.isSuccessful()){
                        Notifications.create().title("Success").text("Loan purchase has been completed successfully!")
                                .hideAfter(Duration.seconds(5)).position(Pos.CENTER).showInformation();

                    }
                    else{
                        try {
                            Notifications.create().title("Error").text(response.body().string()).hideAfter(Duration.seconds(5)).position(Pos.CENTER).showError();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                });
            }
        });
    }
}
