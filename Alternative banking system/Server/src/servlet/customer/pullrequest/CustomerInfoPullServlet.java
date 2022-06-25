package servlet.customer.pullrequest;

import com.google.gson.Gson;
import database.Engine;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import objects.customers.CustomerInfoDTO;
import objects.customers.CustomersRelatedInfoDTO;
import objects.loans.LoansForSaleDTO;
import objects.loans.NewLoanDTO;
import objects.loans.payments.PaymentNotificationDTO;
import utils.EngineServlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import static userinterface.Constants.USERNAME;

@WebServlet(name = "CustomerPullInfoServlet", urlPatterns = {"/Customer-Pull-Information-Servlet"})
public class CustomerInfoPullServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Engine engine = EngineServlet.getEngine(getServletContext());
        if(request.getSession() != null) {
            String userName = String.valueOf(request.getSession().getAttribute(USERNAME));
            List<NewLoanDTO> loansInfoList = engine.getLoansInfo(userName);
            CustomerInfoDTO customerInfoDTO = engine.getCustomerInfo(userName);
            List<String> categoriesList = engine.getCategoriesList().getCategoriesList();
            List<PaymentNotificationDTO> paymentNotification = engine.getNotifications(userName);
            List<LoansForSaleDTO> loansOnSale = engine.getLoansAvailableToBuy(userName);
            CustomersRelatedInfoDTO loanAndCustomerInfoDTO = new CustomersRelatedInfoDTO(loansInfoList, customerInfoDTO, categoriesList, paymentNotification, loansOnSale);
            response.setContentType("application/json");
            try (PrintWriter out = response.getWriter()) {
                Gson gson = new Gson();
                //UserManager userManager = ServletUtils.getUserManager(getServletContext());
                //Set<String> usersList = userManager.getUsers();
                String json = gson.toJson(loanAndCustomerInfoDTO);
                out.println(json);
                out.flush();
            }
        } else{
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
        }



    }
}
