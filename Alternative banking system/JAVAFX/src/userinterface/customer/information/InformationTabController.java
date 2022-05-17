package userinterface.customer.information;

import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import userinterface.table.loantable.*;


public class InformationTabController {

    //Sub components
    @FXML ScrollPane newLoanerTable;
    @FXML NewLoanTableController newLoanerTableController;
    @FXML ScrollPane pendingLoanerTable;
    @FXML PendingLoanTableController pendingLoanerTableController;
    @FXML ScrollPane activeLoanerTable;
    @FXML ActiveLoanTableController activeLoanerTableController;
    @FXML ScrollPane riskLoanerTable;
    @FXML RiskLoanTableController riskLoanerTableController;
    @FXML ScrollPane finishedLoaderTable;
    @FXML FinishedLoanTableController finishedLoaderTableController;

    @FXML ScrollPane newLenderTable;
    @FXML NewLoanTableController newLenderTableController;
    @FXML ScrollPane pendingLenderTable;
    @FXML PendingLoanTableController pendingLenderTableController;
    @FXML ScrollPane activeLenderTable;
    @FXML ActiveLoanTableController activeLenderTableController;
    @FXML ScrollPane riskLenderTable;
    @FXML RiskLoanTableController riskLenderTableController;
    @FXML ScrollPane finishedLenderTable;
    @FXML FinishedLoanTableController finishedLenderTableController;

    //JavaFX components
    @FXML ScrollPane informationSP;
    @FXML BorderPane informationBP;
    @FXML TabPane loanerTB;
    @FXML Tab newLoanerTab;
    @FXML Tab pendingLoanerTab;
    @FXML Tab activeLoanerTab;
    @FXML Tab riskLoanerTab;
    @FXML Tab finishedLoanerTab;
    @FXML TabPane lenderTB;
    @FXML Tab newLenderTab;
    @FXML Tab pendingLenderTab;
    @FXML Tab activeLenderTab;
    @FXML Tab riskLenderTab;
    @FXML Tab finishedLenderTab;


}
