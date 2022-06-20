package admincomponents.adminscreen;

import com.google.gson.Gson;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.util.Duration;
import objects.admin.LoanAndCustomerInfoDTO;
import objects.customers.CustomerInfoDTO;
import objects.loans.NewLoanDTO;
import okhttp3.*;
import org.controlsfx.control.Notifications;
import userinterface.Constants;
import userinterface.utils.HttpUtil;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.TimerTask;
import javafx.scene.control.CheckBox;

import static userinterface.Constants.*;

public class AdminInfoRefresher extends TimerTask {
    private AdminScreenController adminScreenController;

    public AdminInfoRefresher(AdminScreenController adminScreenController) {
        this.adminScreenController = adminScreenController;
    }

    @Override
    public void run() {
        String finalUrlInformation = HttpUrl.parse(FULL_PATH_DOMAIN + ADMIN_PULL_INFORMATION_RESOURCE)
                .newBuilder()
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
                //String[] str = jsonArrayOfCustomers.split("\r \n");
               // String required = str[1].substring(0, str[1].length()-2);
                LoanAndCustomerInfoDTO loanAndCustomerInfo = GSON_INSTANCE.fromJson(jsonArrayOfInformation, LoanAndCustomerInfoDTO.class);
               // NewLoanDTO[] loans = GSON_INSTANCE.fromJson(required, NewLoanDTO[].class);
                List<NewLoanDTO> loans = loanAndCustomerInfo.getLoanList();
                List<CustomerInfoDTO> customerList = loanAndCustomerInfo.getCustomerList();
              //  List<NewLoanDTO> loanDTOList = Arrays.asList(loans);
                Platform.runLater(() ->{
                      adminScreenController.getCustomerTableController().setValues(customerList);
                      adminScreenController.getNewLoanController().setValues(loans);
                });
               // return false;
            }

        });
//        String finalUrlLoansTable = HttpUrl.parse(FULL_PATH_DOMAIN + ADMIN_PULL_LOANS_TABLE_RESOURCE)
//                .newBuilder()
//                .build()
//                .toString();
//        Request requestLoansTable = new Request.Builder()
//                .url(finalUrlLoansTable)
//                .build();

//        HttpUtil.runAsync(requestLoansTable, true, new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//                System.out.println("error");
//            }

//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                String jsonArrayOfLoans = response.body().string();
//                Gson gson = new Gson();
//                if (response.code() != 200) {
//                    System.out.println("error!");
//                    System.out.println(response.body().toString());
//                }
//                else {
//                    System.out.println(jsonArrayOfLoans);
//                    if (!jsonArrayOfLoans.equals("[]\r\n")) {
//                        NewLoanDTO[] loans = gson.fromJson(jsonArrayOfLoans, NewLoanDTO[].class);
//                        List<NewLoanDTO> loanDTOList = Arrays.asList(loans);
//                        Platform.runLater(() ->
//                                adminScreenController.getNewLoanController().setValues(loanDTOList));
//                    }
//                }
//                //return false;
//            }
//        });
    }
}
