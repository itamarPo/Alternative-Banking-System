package userinterface.table.loantable;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import objects.loans.ActiveRiskLoanDTO;
import objects.loans.LenderMap;
import objects.loans.LoansForSaleDTO;
import objects.loans.payments.PaymentsDTO;
import userinterface.customer.loanforsell.LoanSellTabController;
import userinterface.table.LendersTableController;
import userinterface.table.PaymentTableController;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public class LoanBuyTableController {

    private boolean lenderStageExist = false;
    private boolean paymentStageExist = false;
    private LendersTableController lendersTableController;
    private PaymentTableController paymentTableController;
    //JavaFX components
    @FXML private TableView<LoansForSaleDTO> tableView;
    @FXML private TableColumn<LoansForSaleDTO, String> loanID;
    @FXML private TableColumn<LoansForSaleDTO, String> category;
    @FXML private TableColumn<LoansForSaleDTO, String> owner;
    @FXML private TableColumn<LoansForSaleDTO, String> seller;
    @FXML private TableColumn<LoansForSaleDTO, Double> amount;
    @FXML private TableColumn<LoansForSaleDTO, Integer> duration;
    @FXML private TableColumn<LoansForSaleDTO, Double> interest;
    @FXML private TableColumn<LoansForSaleDTO, Integer> timePerPayment;
    @FXML private TableColumn<LoansForSaleDTO, Button> listOfLenders;
    @FXML private TableColumn<LoansForSaleDTO, Integer> startingActiveTime;
    @FXML private TableColumn<LoansForSaleDTO, Integer> nextPaymentTime;
    @FXML private TableColumn<LoansForSaleDTO, Button> payments;
    @FXML private TableColumn<LoansForSaleDTO, Double> price;
    @FXML private TableColumn<LoansForSaleDTO, Double> expectedProfit;

    //Regular Fields
    private LoanSellTabController loanSellTabController;
    private Stage primaryStage;

    @FXML
    public void initialize(){
        loanID.setCellValueFactory(new PropertyValueFactory<LoansForSaleDTO, String>("loanID"));
        category.setCellValueFactory(new PropertyValueFactory<LoansForSaleDTO, String>("category"));
        owner.setCellValueFactory(new PropertyValueFactory<LoansForSaleDTO, String>("owner"));
        seller.setCellValueFactory(new PropertyValueFactory<LoansForSaleDTO, String>("seller"));
        duration.setCellValueFactory(new PropertyValueFactory<LoansForSaleDTO, Integer>("timeLimitOfLoan"));
        amount.setCellValueFactory(new PropertyValueFactory<LoansForSaleDTO, Double>("sizeNoInterest"));
        interest.setCellValueFactory(new PropertyValueFactory<LoansForSaleDTO, Double>("interestPerPayment"));
        timePerPayment.setCellValueFactory(new PropertyValueFactory<LoansForSaleDTO, Integer>("timePerPayment"));
        listOfLenders.setCellValueFactory(new PropertyValueFactory<LoansForSaleDTO, Button>("lendersButton"));
        startingActiveTime.setCellValueFactory(new PropertyValueFactory<LoansForSaleDTO, Integer>("startingActiveTime"));
        nextPaymentTime.setCellValueFactory(new PropertyValueFactory<LoansForSaleDTO, Integer>("nextPaymentTime"));
        payments.setCellValueFactory(new PropertyValueFactory<LoansForSaleDTO, Button>("paymentsButton"));
        price.setCellValueFactory(new PropertyValueFactory<LoansForSaleDTO, Double>("price"));
        expectedProfit.setCellValueFactory(new PropertyValueFactory<LoansForSaleDTO, Double>("expectedProfit"));
        FXMLLoader loaderlenders = new FXMLLoader();
        URL lendersFXML = getClass().getResource("/userinterface/table/lendersTable.fxml");
        loaderlenders.setLocation(lendersFXML);
        try {
            Parent root1 = loaderlenders.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        lendersTableController = loaderlenders.getController();

        FXMLLoader loaderPayment = new FXMLLoader();
        URL paymentFXML = getClass().getResource("/userinterface/table/paymentTable.fxml");
        loaderPayment.setLocation(paymentFXML);
        try {
            Parent root1 = loaderPayment.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        paymentTableController = loaderPayment.getController();
    }

    //Getters
    public TableView<LoansForSaleDTO> getTableView() {
        return tableView;
    }

    //Setters
    public void setLoanSellTabController(LoanSellTabController loanSellTabController) {
        this.loanSellTabController = loanSellTabController;
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void setValues(List<LoansForSaleDTO> activeList) {
        ObservableList<LoansForSaleDTO> loansForSaleDTOSObservableList = FXCollections.observableList(activeList);
        tableView.getItems().setAll(loansForSaleDTOSObservableList);
        for(int i=0; i<tableView.getItems().size(); i++){
            int finalI = i;
            Button lendersButton = tableView.getItems().get(i).getLendersButton();
            lendersButton.setText("Show");
            lendersButton.setOnAction(new EventHandler<ActionEvent>(){
                @Override
                public void handle(ActionEvent actionEvent){
                    if(!lenderStageExist){
                        lenderStageExist = true;
                        lendersTableController.setPopUpScene();
                        lendersTableController.getPopUpLenderStage().initModality(Modality.WINDOW_MODAL);
                        lendersTableController.getPopUpLenderStage().initOwner(primaryStage);
                    }
                    List<LenderMap> lenders = new ArrayList<>();
                    Map<String, Double> lendersMap = activeList.get(finalI).getListOfLenders();
                    for (Map.Entry<String,Double> entry : lendersMap.entrySet()){
                        lenders.add(new LenderMap(entry.getKey(), entry.getValue()));
                    }
                    lendersTableController.setValues(lenders);

                    lendersTableController.getPopUpLenderStage().show();
                }
            });
            Button paymentButton = tableView.getItems().get(i).getPaymentsButton();
            paymentButton.setText("Show");
            paymentButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent){
                    if(!paymentStageExist){
                        paymentStageExist = true;
                        paymentTableController.setPopUpScene();
                        paymentTableController.getPopUpPaymentStage().initModality(Modality.WINDOW_MODAL);
                        paymentTableController.getPopUpPaymentStage().initOwner(primaryStage);
                    }
                    List<PaymentsDTO> pay = tableView.getItems().get(finalI).getPayments();
                    paymentTableController.setValues(pay);
                    paymentTableController.getPopUpPaymentStage().show();
                }
            });
        }
    }
}
