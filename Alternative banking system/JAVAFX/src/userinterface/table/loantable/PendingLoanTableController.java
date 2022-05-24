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
import objects.loans.LenderMap;
import objects.loans.PendingLoanDTO;
import userinterface.table.LendersTableController;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PendingLoanTableController {

    private LendersTableController lendersTableController;

    private boolean lenderStageExist = false;
    //JavaFX components
    @FXML private TableView<PendingLoanDTO> tableView;
    @FXML private TableColumn<PendingLoanDTO, String> loanID;
    @FXML private TableColumn<PendingLoanDTO, String> category;
    @FXML private TableColumn<PendingLoanDTO, String> owner;
    @FXML private TableColumn<PendingLoanDTO, Double> amount;
    @FXML private TableColumn<PendingLoanDTO, Integer> duration;
    @FXML private TableColumn<PendingLoanDTO, Integer> interest;
    @FXML private TableColumn<PendingLoanDTO, Integer> timePerPayment;
    @FXML private TableColumn<PendingLoanDTO, Button> listOfLenders;
    @FXML private TableColumn<PendingLoanDTO, Double> collectedSoFar;
    @FXML private TableColumn<PendingLoanDTO, Double> sumLeftToBeCollected;
    //private List<Button> buttonList = new ArrayList<>();

    //Constructor
    public PendingLoanTableController() {

    }

    //Initialize after constructor
    public void initialize() {
        loanID.setCellValueFactory(new PropertyValueFactory<PendingLoanDTO, String>("loanID"));
        category.setCellValueFactory(new PropertyValueFactory<PendingLoanDTO, String>("loanCategory"));
        owner.setCellValueFactory(new PropertyValueFactory<PendingLoanDTO, String>("borrowerName"));
        duration.setCellValueFactory(new PropertyValueFactory<PendingLoanDTO, Integer>("timeLimitOfLoan"));
        amount.setCellValueFactory(new PropertyValueFactory<PendingLoanDTO, Double>("sizeNoInterest"));
        interest.setCellValueFactory(new PropertyValueFactory<PendingLoanDTO, Integer>("interestPerPayment"));
        timePerPayment.setCellValueFactory(new PropertyValueFactory<PendingLoanDTO, Integer>("timePerPayment"));
        listOfLenders.setCellValueFactory(new PropertyValueFactory<PendingLoanDTO, Button>("lendersButton"));
        collectedSoFar.setCellValueFactory(new PropertyValueFactory<PendingLoanDTO, Double>("collectedSoFar"));
        sumLeftToBeCollected.setCellValueFactory(new PropertyValueFactory<PendingLoanDTO, Double>("sumLeftToBeCollected"));
        FXMLLoader loaderlenders = new FXMLLoader();
        URL lendersFXML = getClass().getResource("/userinterface/table/lendersTable.fxml");
        loaderlenders.setLocation(lendersFXML);
        try {
            Parent root1 = loaderlenders.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        lendersTableController = loaderlenders.getController();
    }

    //Getters
    public TableView<PendingLoanDTO> getTableView() {return tableView;}

    //Setters
    public void setValues(List<PendingLoanDTO> pendingLoansList){
        ObservableList<PendingLoanDTO> pendingLoanDTOObservableList = FXCollections.observableArrayList(pendingLoansList);
        tableView.getItems().setAll(pendingLoanDTOObservableList);
        for(int i=0; i<tableView.getItems().size(); i++){
            tableView.getItems().get(i).getLendersButton().setText("Show Lenders");
           Button button = tableView.getItems().get(i).getLendersButton();
            int finalI = i;
            button.setOnAction(new EventHandler<ActionEvent>(){
                @Override
                public void handle(ActionEvent actionEvent){
                    if(!lenderStageExist){
                        lenderStageExist = true;
                        lendersTableController.setPopUpScene();
                    }
                    List<LenderMap> lenders = new ArrayList<>();
                    Map<String, Double> lendersMap = pendingLoansList.get(finalI).getListOfLenders();
                    for (Map.Entry<String,Double> entry : lendersMap.entrySet()){
                        lenders.add(new LenderMap(entry.getKey(), entry.getValue()));
                }
                    lendersTableController.setValues(lenders);
                    lendersTableController.getPopUpLenderStage().show();
                }
            });
        }
    }
}