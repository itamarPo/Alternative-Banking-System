package userinterface.customer.loanforsell;
import database.Engine;
import exceptions.accountexception.NotEnoughMoneyInAccount;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import objects.loans.ActiveRiskLoanDTO;
import org.controlsfx.control.CheckListView;
import org.controlsfx.control.Notifications;
import userinterface.customer.TopCustomerController;
import userinterface.table.loantable.ActiveLoanTableController;

import java.beans.EventHandler;
import java.util.List;

public class LoanSellTabController {

    //Sub components
    @FXML private ScrollPane buyLoansTable;
    @FXML private ActiveLoanTableController buyLoansTableController;

    //JavaFX components
    @FXML private ScrollPane buySellLoanSP;
    @FXML private AnchorPane buySellLoanAP;
    @FXML private TabPane buySellLoanTP;
    @FXML private Tab sellLoanTab;
    @FXML private CheckListView<String> sellLoanCLV;
    @FXML private Button confirmSellButton;
    @FXML private Label sellErrorMessage;
    @FXML private Tab buyLoanTab;
    @FXML private Button confirmBuyButton;
    @FXML private Label errorBuyMessage;

    //Regular Fields
    private TopCustomerController topCustomerController;
    private Engine engine;


    @FXML
    private void initialize() {
        buySellLoanTP.getSelectionModel().selectedItemProperty().addListener((ov, oldTab, newTab) -> {
            sellErrorMessage.setVisible(false);
//            if(sellLoanCLV.getCheckModel().getCheckedItems().size()>0)
//                sellLoanCLV.getCheckModel().getCheckedItems().clear();
            errorBuyMessage.setText("");
        });

    }

    public void setEngine(Engine engine) {
        this.engine = engine;
    }

    public void setControllersAndStages(){
        buyLoansTableController.setLoanSellTabController(this);
        buyLoansTableController.setPrimaryStage(topCustomerController.getMainController().getPrimaryStage());
    }

    //Setters
    public void setTopCustomerController(TopCustomerController topCustomerController) {
        this.topCustomerController = topCustomerController;
    }

    public void setValues(List<String> loanIDs, List<ActiveRiskLoanDTO> loansToSell){
        sellLoanCLV.getCheckModel().clearChecks();
        sellLoanCLV.getItems().clear();
        sellLoanCLV.getItems().addAll(loanIDs);
        ObservableList<ActiveRiskLoanDTO> loansForSale = FXCollections.observableList(loansToSell);
        buyLoansTableController.setValues(loansForSale);
    }
    
    //Regular methods
    @FXML
    public void confirmSellOnAction(ActionEvent actionEvent) {
        List<String> selectedLoans = sellLoanCLV.getCheckModel().getCheckedItems();
        if(selectedLoans.size() == 0){
            sellErrorMessage.setVisible(true);    //error
            return;
        }
        engine.setLoansForSale(topCustomerController.getUserCB().getValue(), selectedLoans);
        sellErrorMessage.setVisible(false);

        topCustomerController.updateLoanSellTab(topCustomerController.getUserCB().getValue());

    }
    @FXML
    public void confirmBuyButtonOnAction(ActionEvent actionEvent) {
        try{
        ActiveRiskLoanDTO selectedLoan = null;
        if (buyLoansTableController.getTableView().getSelectionModel().getSelectedItem() != null) {
            selectedLoan = buyLoansTableController.getTableView().getSelectionModel().getSelectedItem();
            engine.sellLoan(selectedLoan.getLoanID(), topCustomerController.getUserCB().getValue());
        }else{
            throw new Exception();//user didn't select
        }
    } catch (NotEnoughMoneyInAccount e){
        Notifications notEnoughMoney = Notifications.create().title("Error").text(e.toString()).hideAfter(Duration.seconds(10)).position(Pos.CENTER);
        notEnoughMoney.show();
    } catch(Exception e){
//        completePaymentError.setText("No loan has been selected!");
    }
        }
    }
}

