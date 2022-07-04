package userinterface;

import com.google.gson.Gson;

public class Constants {
    public final static String FULL_PATH_DOMAIN = "http://localhost:8080/Server_Web";
    public final static String LOGIN_RESOURCE = "/login";
    public final static String ADMIN_PULL_INFORMATION_RESOURCE = "/Admin-Pull-Information-Servlet";
    public final static String AVAILABLE_CATEGORIES_PULL_RESOURCE = "/Available-Categories-Pull-Servlet";
    public final static String CUSTOMER_PULL_INFORMATION_RESOURCE = "/Customer-Pull-Information-Servlet";
    public final static String CUSTOMER_PULL_CATEGORIES_RESOURCE = "/Customer-Categories-Pull-Servlet";
    public final static String CREATE_LOAN_RESOURCE = "/Create-Loan-Servlet";
    public final static String CHECK_INLAY_INPUT_RESOURCE = "/Check-Inlay-Input-Servlet";
    public final static String CUSTOMER_INLAY_FILTER_RESOURCE = "/Customer-Inlay-Filter-Pull-Servlet";
    public final static String CUSTOMER_MAKE_INLAY_RESOURCE = "/Customer-Make-Inlay-Servlet";
    public final static String TRANSACTION_POPUP_IMPLEMENTATION = "/Transaction-CheckAndUpdate-Servlet";
    public final static String CUSTOMER_PAYMENT_INFO_RESOURCE = "/Customer-Pull-Payments-Servlet";
    public final static String CUSTOMER_CLOSE_LOAN_RESOURCE = "/Customer-Close-Loan-Servlet";
    public final static String CUSTOMER_MAKE_PAYMENT_RESOURCE = "/Customer-Make-Payment-Servlet";
    public final static String CUSTOMER_BUYSELL_PULL_RESOURCE = "/Customer-BuySell-Pull-Servlet";
    public final static String CUSTOMER_SELL_LOANS_RESOURCE = "/Customer-Sell-Loans-Servlet";
    public final static String CUSTOMER_BUY_LOAN_RESOURCE = "/Customer-Buy-Loan-Servlet";
    public final static Gson GSON_INSTANCE = new Gson();
    public final static int REFRESH_RATE = 3000;
    public final static String UPLOAD_FILE = "/upload-file";
    public final static String USERNAME = "userName";
    public final static String AMOUNT = "Amount";
    public final static int INVALID = -1;
    public final static int DIFFERENT = -2;

}
