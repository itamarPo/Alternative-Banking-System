package servlet.Customer;

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

@WebServlet(name = "CustomerPullInfoServlet", urlPatterns = {"/Customer-Pull-Information-Servlet"})
public class CustomerPullServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Engine engine = EngineServlet.getEngine(getServletContext());

        List<CustomerInfoDTO> customerInfoDTOList = engine.getCustomerInfo();
        List<NewLoanDTO> loansInfoList = engine.getLoansInfo();
        List<PaymentNotificationDTO> paymentNotification= engine.getNotifications(request.getParameter("userName"));
        List<LoansForSaleDTO> loansOnSale = engine.getLoansAvailableToBuy(request.getParameter("userName"));
        CustomersRelatedInfoDTO loanAndCustomerInfoDTO = new CustomersRelatedInfoDTO(customerInfoDTOList, loansInfoList, paymentNotification, loansOnSale);
        response.setContentType("application/json");
        try (PrintWriter out = response.getWriter()) {
            Gson gson = new Gson();
            //UserManager userManager = ServletUtils.getUserManager(getServletContext());
            //Set<String> usersList = userManager.getUsers();
            String json = gson.toJson(loanAndCustomerInfoDTO);
            out.println(json);
            out.flush();
        }


    }
}
