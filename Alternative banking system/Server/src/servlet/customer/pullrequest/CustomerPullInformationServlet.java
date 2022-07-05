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
import objects.loans.*;
import objects.loans.payments.PaymentNotificationDTO;
import utils.EngineServlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import static userinterface.Constants.USERNAME;

@WebServlet(name = "CustomerPullInformationServlet", urlPatterns = {"/Customer-Pull-Information-Servlet"})
public class CustomerPullInformationServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Engine engine = EngineServlet.getEngine(getServletContext());

        //checking if there is an open session for this customer
        if(request.getSession(false)!=null){
            String userName = String.valueOf(request.getSession().getAttribute(USERNAME));
            List<NewLoanDTO> allRelatedLoans = engine.getLoansInfo(userName);
            List<NewLoanDTO> newLoans = new ArrayList<>();
            allRelatedLoans.stream().filter(l -> l.getStatus().equals("New")).forEach(l -> newLoans.add(l));
            List<PendingLoanDTO> pendingLoans = new ArrayList<>();
            allRelatedLoans.stream().filter(l -> l.getStatus().equals("Pending")).forEach(l-> pendingLoans.add((PendingLoanDTO) l));
            List<ActiveRiskLoanDTO> activeLoans = new ArrayList<>();
            allRelatedLoans.stream().filter(l -> l.getStatus().equals("Active")).forEach(l-> activeLoans.add((ActiveRiskLoanDTO) l));
            List<ActiveRiskLoanDTO> riskLoans = new ArrayList<>();
            allRelatedLoans.stream().filter(l -> l.getStatus().equals("Risk")).forEach(l-> riskLoans.add((ActiveRiskLoanDTO) l));
            List<FinishedLoanDTO> finishedLoans = new ArrayList<>();
            allRelatedLoans.stream().filter(l -> l.getStatus().equals("Finished")).forEach(l-> finishedLoans.add((FinishedLoanDTO) l));
            CustomerInfoDTO customerInfoDTO = engine.getCustomerInfo(userName);
            String serverStatus = engine.getServerStatus();
            Integer currentYaz = Engine.getTime();
            CustomersRelatedInfoDTO loanAndCustomerInfoDTO = new CustomersRelatedInfoDTO(newLoans, pendingLoans, activeLoans,
                    riskLoans,finishedLoans, customerInfoDTO, serverStatus, currentYaz.toString());
            response.setContentType("application/json");
            try (PrintWriter out = response.getWriter()) {
                Gson gson = new Gson();
                String json = gson.toJson(loanAndCustomerInfoDTO);
                out.println(json);
                out.flush();
            }
        } else{
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
//            response.getWriter().println("No session was found for this customer! access denied");
        }



    }
}
