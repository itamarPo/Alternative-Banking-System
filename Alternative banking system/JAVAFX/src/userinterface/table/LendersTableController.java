package userinterface.table;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.MapValueFactory;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import userinterface.table.loantable.PendingLoanTableController;

import java.util.List;
import java.util.Map;

public class LendersTableController {

    private PendingLoanTableController pendingLoanTableController;
    @FXML
    private TableView<LenderMap> lendersTable;

    @FXML
    private TableColumn<Map<String, Double>, String> loanerName;

    @FXML
    private TableColumn<LenderMap, Double> amount;

    private Stage popUpLenderStage;

    public Stage getPopUpLenderStage() {
        return popUpLenderStage;
    }

    private Scene popUpLenderScene;

    public void initialize(){
        loanerName.setCellValueFactory(new PropertyValueFactory<Map<String, Double> ,String>(""));
        amount.setCellValueFactory(new PropertyValueFactory<Map<String, Double>, Double>(""));
    }

    public void setValues(Map<String, Double> values){
        lendersTable.getItems().setAll(values);
    }

    public LendersTableController(){
        popUpLenderStage = new Stage();
    }

    public void setPopUpScene(){
        popUpLenderScene = new Scene(lendersTable, 222, 292);
        popUpLenderStage.setScene(popUpLenderScene);
    }
}