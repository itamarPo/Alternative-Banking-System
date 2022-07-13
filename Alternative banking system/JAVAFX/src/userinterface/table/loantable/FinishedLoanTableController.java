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
import objects.loans.ActiveRiskLoanDTO;
import objects.loans.FinishedLoanDTO;
import objects.loans.LenderMap;
import userinterface.table.LendersTableController;
import userinterface.table.PaymentTableController;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FinishedLoanTableController {


    //JavaFX components
    @FXML
    private TableView<FinishedLoanDTO> finishedTable;
    @FXML private TableColumn<FinishedLoanDTO, String> loanID;
    @FXML private TableColumn<FinishedLoanDTO, String> category;
    @FXML private TableColumn<FinishedLoanDTO, String> owner;
    @FXML private TableColumn<FinishedLoanDTO, Double> amount;
    @FXML private TableColumn<FinishedLoanDTO, Integer> duration;
    @FXML private TableColumn<FinishedLoanDTO, Integer> interest;
    @FXML private TableColumn<FinishedLoanDTO, Integer> timePerPayment;
    @FXML private TableColumn<FinishedLoanDTO, Button> listOfLenders;
    @FXML private TableColumn<FinishedLoanDTO, Button> payments;
    @FXML private TableColumn<FinishedLoanDTO, Integer> startingTime;
    @FXML private TableColumn<FinishedLoanDTO, Integer> finishingTime;

    private boolean lenderStageExist = false;
    private boolean paymentStageExist = false;
    private LendersTableController lendersTableController;
    private PaymentTableController paymentTableController;
    public void initialize(){
        loanID.setCellValueFactory(new PropertyValueFactory<FinishedLoanDTO, String>("loanID"));
        category.setCellValueFactory(new PropertyValueFactory<FinishedLoanDTO, String>("loanCategory"));
        owner.setCellValueFactory(new PropertyValueFactory<FinishedLoanDTO, String>("borrowerName"));
        duration.setCellValueFactory(new PropertyValueFactory<FinishedLoanDTO, Integer>("timeLimitOfLoan"));
        amount.setCellValueFactory(new PropertyValueFactory<FinishedLoanDTO, Double>("sizeNoInterest"));
        interest.setCellValueFactory(new PropertyValueFactory<FinishedLoanDTO, Integer>("interestPerPayment"));
        timePerPayment.setCellValueFactory(new PropertyValueFactory<FinishedLoanDTO, Integer>("timePerPayment"));
        listOfLenders.setCellValueFactory(new PropertyValueFactory<FinishedLoanDTO, Button>("lendersButton"));
        startingTime.setCellValueFactory(new PropertyValueFactory<FinishedLoanDTO, Integer>("startingTime"));
        payments.setCellValueFactory(new PropertyValueFactory<FinishedLoanDTO, Button>("paymentsButton"));
        finishingTime.setCellValueFactory(new PropertyValueFactory<FinishedLoanDTO, Integer>("finishingTime"));
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

    public void setValues(List<FinishedLoanDTO> finishedList){
        ObservableList<FinishedLoanDTO> FinishedLoanDTOObservableList = FXCollections.observableList(finishedList);
        finishedTable.getItems().setAll(FinishedLoanDTOObservableList);
        for(int i=0; i<finishedTable.getItems().size(); i++){
            int finalI = i;
            Button lendersButton = finishedTable.getItems().get(i).getLendersButton();
            lendersButton.setText("Show");
            lendersButton.setOnAction(new EventHandler<ActionEvent>(){
                @Override
                public void handle(ActionEvent actionEvent){
                    if(!lenderStageExist){
                        lenderStageExist = true;
                        lendersTableController.setPopUpScene();
                    }
                    List<LenderMap> lenders = new ArrayList<>();
                    Map<String, Double> lendersMap = finishedList.get(finalI).getListOfLenders();
                    for (Map.Entry<String,Double> entry : lendersMap.entrySet()){
                        lenders.add(new LenderMap(entry.getKey(), entry.getValue()));
                    }
                    lendersTableController.setValues(lenders);
                    lendersTableController.getPopUpLenderStage().show();
                }
            });

            Button paymentButton = finishedTable.getItems().get(i).getPaymentsButton();
            paymentButton.setText("Show");
            paymentButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent){
                    if(!paymentStageExist){
                        paymentStageExist = true;
                        paymentTableController.setPopUpScene();
                    }

                    paymentTableController.setValues(finishedTable.getItems().get(finalI).getPayments());
                    paymentTableController.getPopUpPaymentStage().show();
                }
            });

        }
    }
    public TableView<FinishedLoanDTO> getFinishedTable() {
        return finishedTable;
    }

}
