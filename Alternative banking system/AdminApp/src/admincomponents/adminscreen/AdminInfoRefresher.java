package admincomponents.adminscreen;

import com.google.gson.Gson;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.util.Duration;
import objects.customers.CustomerInfoDTO;
import okhttp3.*;
import org.controlsfx.control.Notifications;
import userinterface.Constants;
import userinterface.utils.HttpUtil;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.TimerTask;

import static userinterface.Constants.GSON_INSTANCE;

public class AdminInfoRefresher extends TimerTask {
    private AdminScreenController adminScreenController;

    public AdminInfoRefresher(AdminScreenController adminScreenController) {
        this.adminScreenController = adminScreenController;
    }

    @Override
    public void run() {
        String finalUrl = HttpUrl.parse(Constants.FULL_PATH_DOMAIN + Constants.ADMIN_PULL_RESOURCE)
                .newBuilder()
                .build()
                .toString();
        Request request = new Request.Builder()
                .url(finalUrl)
                .build();

        HttpUtil.runAsync(request,true, new Callback() {
            public void onFailure(Call call, IOException e) {
                System.out.println("problem");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String jsonArrayOfCustomers = response.body().string();
                CustomerInfoDTO[] customers = GSON_INSTANCE.fromJson(jsonArrayOfCustomers, CustomerInfoDTO[].class);
                List<CustomerInfoDTO> list = Arrays.asList(customers);
                Platform.runLater(() ->
                      adminScreenController.getCustomerTableController().setValues(list));
            }

        });
    }
}
