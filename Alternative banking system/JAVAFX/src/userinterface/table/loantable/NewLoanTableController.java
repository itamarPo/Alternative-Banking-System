package userinterface.table.loantable;

import admincomponents.adminscreen.AdminScreenController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import objects.loans.NewLoanDTO;
import userinterface.admin.centerAdmin.CenterAdminController;
import userinterface.customer.information.InformationTabController;
import userinterface.customer.inlay.InlayTabController;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class NewLoanTableController implements Initializable {

    //JavaFX components
    @FXML private TableView<NewLoanDTO> tableView;
    @FXML private TableColumn<NewLoanDTO, String> loanID;
    @FXML private TableColumn<NewLoanDTO, String> category;
    @FXML private TableColumn<NewLoanDTO, String> owner;
    @FXML private TableColumn<NewLoanDTO, Integer> duration;
    @FXML private TableColumn<NewLoanDTO, Double> amount;
    @FXML private TableColumn<NewLoanDTO, Integer> interest;
    @FXML private TableColumn<NewLoanDTO, Integer> timePerPayment;

    //Regular Fields
    private InlayTabController inlayTabController;
    private InformationTabController informationTabController;
    private CenterAdminController centerAdminController;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loanID.setCellValueFactory(new PropertyValueFactory<NewLoanDTO, String>("loanID"));
        category.setCellValueFactory(new PropertyValueFactory<NewLoanDTO, String>("loanCategory"));
        owner.setCellValueFactory(new PropertyValueFactory<NewLoanDTO, String>("borrowerName"));
        duration.setCellValueFactory(new PropertyValueFactory<NewLoanDTO, Integer>("timeLimitOfLoan"));
        amount.setCellValueFactory(new PropertyValueFactory<NewLoanDTO, Double>("sizeNoInterest"));
        interest.setCellValueFactory(new PropertyValueFactory<NewLoanDTO, Integer>("interestPerPayment"));
        timePerPayment.setCellValueFactory(new PropertyValueFactory<NewLoanDTO, Integer>("timePerPayment"));
    }


    //Setters
    public void setInlayTabController(InlayTabController inlayTabController) {
        this.inlayTabController = inlayTabController;
    }

    public void setInformationTabController(InformationTabController informationTabController) {
        this.informationTabController = informationTabController;
    }

    public void setCenterAdminController(CenterAdminController centerAdminController) {
        this.centerAdminController = centerAdminController;
    }


    //Getters
    public TableView<NewLoanDTO> getTableView() {
        return tableView;
    }

    //Regular Methods
    public void setValues(List<NewLoanDTO> newLoansList){
       ObservableList<NewLoanDTO> newLoanDTOObservableList = FXCollections.observableArrayList(newLoansList);
       tableView.getItems().setAll(newLoanDTOObservableList);
    }
}
