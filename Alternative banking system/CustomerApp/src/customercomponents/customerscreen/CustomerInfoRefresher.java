package customercomponents.customerscreen;

import javafx.application.Platform;
import objects.admin.LoanAndCustomerInfoDTO;
import objects.customers.CustomerInfoDTO;
import objects.customers.CustomersRelatedInfoDTO;
import objects.loans.NewLoanDTO;
import okhttp3.*;
import userinterface.utils.HttpUtil;

import java.io.IOException;
import java.util.List;
import java.util.TimerTask;

import static userinterface.Constants.*;

public class CustomerInfoRefresher extends TimerTask {
    private CustomerScreenController customerScreenController;


    public CustomerInfoRefresher(CustomerScreenController customerScreenController) {
        this.customerScreenController = customerScreenController;
    }

    @Override
    public void run() {
        String finalUrlInformation = HttpUrl.parse(FULL_PATH_DOMAIN + "/Customer-Pull-Information-Servlet")
                .newBuilder()
                .build()
                .toString();
        Request requestCustomerTable = new Request.Builder()
                .url(finalUrlInformation)
                .build();

        HttpUtil.runAsync(requestCustomerTable,false, new Callback() {
            public void onFailure(Call call, IOException e) {
                System.out.println("problem");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String jsonArrayOfInformation = response.body().string();
                String userName;
                if(jsonArrayOfInformation==null || jsonArrayOfInformation=="")
                    return;
                CustomersRelatedInfoDTO allTabsCustomerInformation = GSON_INSTANCE.fromJson(jsonArrayOfInformation, CustomersRelatedInfoDTO.class);

                userName = allTabsCustomerInformation.getCustomerInfo().getName();
                List<NewLoanDTO> relatedLoans = allTabsCustomerInformation.getRelatedLoans();
                CustomerInfoDTO customerInfo = allTabsCustomerInformation.getCustomerInfo();


                Platform.runLater(() ->{
                    customerScreenController.updateInformationTab(userName, relatedLoans, customerInfo);
                });
            }

        });

    }
}
