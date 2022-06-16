package admincomponents.adminscreen;

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.util.Duration;
import okhttp3.*;
import org.controlsfx.control.Notifications;
import userinterface.Constants;
import userinterface.utils.HttpUtil;

import java.io.IOException;
import java.util.TimerTask;

public class AdminInfoRefresher extends TimerTask {

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


        }
    }
}
