package userinterface;

import com.google.gson.Gson;

public class Constants {
    public final static String FULL_PATH_DOMAIN = "http://localhost:8080/Server_Web";
    public final static String LOGIN_RESOURCE = "/login";
    public final static String ADMIN_PULL_INFORMATION_RESOURCE = "/Admin-Pull-Information-Servlet";
    public final static String CUSTOMER_PULL_INFORMATION_RESOURCE = "/Customer-Pull-Information-Servlet";
    public final static String CHECK_LOAN_NAME_RESOURCE = "/Check-Loan-Name";
    public final static Gson GSON_INSTANCE = new Gson();
    public final static int REFRESH_RATE = 600;
    public final static String UPLOAD_FILE = "/upload-file";
    public final static String USERNAME = "userName";
    public final static int INVALID = -1;

}
