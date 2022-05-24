package userinterface.table;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.MapValueFactory;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import objects.loans.LenderMap;
import userinterface.table.loantable.PendingLoanTableController;

import java.util.List;
import java.util.Map;

public class LendersTableController {

    private PendingLoanTableController pendingLoanTableController;
    @FXML
    private TableView<LenderMap> lendersTable;

    @FXML
    private TableColumn<LenderMap, String> loanerName;

    @FXML
    private TableColumn<LenderMap, Double> amount;

    private Stage popUpLenderStage;

    public Stage getPopUpLenderStage() {
        return popUpLenderStage;
    }

    private Scene popUpLenderScene;

    public void initialize(){
        loanerName.setCellValueFactory(new PropertyValueFactory<LenderMap, String>("Name"));
        amount.setCellValueFactory(new PropertyValueFactory<LenderMap, Double>("Amount"));
    }

    public void setValues(List<LenderMap> values){
        ObservableList<LenderMap> listData = FXCollections.observableArrayList(values);
       // listData.addAll(values);
        //ObservableMap mapValues = FXCollections.observableMap(listData);
        lendersTable.getItems().setAll(listData);


//        TableColumn<Map, String> firstNameColumn = new TableColumn<Map, String>("First Name");
//        firstNameColumn.setCellValueFactory(new MapValueFactory<String>("firstName"));
//
//        TableView table = new TableView(personMapList);
//        tableView.getColumns().setAll(firstNameColumn);
    }

    public LendersTableController(){
        popUpLenderStage = new Stage();
    }

    public void setPopUpScene(){
        popUpLenderScene = new Scene(lendersTable, 222, 292);
        popUpLenderStage.setScene(popUpLenderScene);
    }
}