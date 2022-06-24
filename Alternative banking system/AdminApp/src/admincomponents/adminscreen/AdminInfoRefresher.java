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
                LoanAndCustomerInfoDTO loanAndCustomerInfo = GSON_INSTANCE.fromJson(jsonArrayOfInformation, LoanAndCustomerInfoDTO.class);
                List<NewLoanDTO> loans = loanAndCustomerInfo.getLoanList();
                List<CustomerInfoDTO> customerList = loanAndCustomerInfo.getCustomerList();
                Platform.runLater(() ->{
                      adminScreenController.getCustomerTableController().setValues(customerList);
                      adminScreenController.getNewLoanController().setValues(loans);
                });
            }

        });

    }
}
