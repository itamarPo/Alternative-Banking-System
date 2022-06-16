package admincomponents.adminscreen;

import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.Request;
import userinterface.Constants;
import userinterface.utils.HttpUtil;

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
