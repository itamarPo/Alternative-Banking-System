package servlet.customer.payments;

import database.Engine;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import userinterface.utils.HttpUtil;
import utils.EngineServlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static userinterface.Constants.GSON_INSTANCE;
import static userinterface.Constants.USERNAME;

@WebServlet(name = "CustomerPullPaymentsServlet", urlPatterns = {"/Customer-Pull-Payments-Servlet"})
public class CustomerPullPaymentsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Engine engine = EngineServlet.getEngine(request.getServletContext());
        String userName = String.valueOf(request.getSession().getAttribute(USERNAME));
        List<ActiveRiskLoanDTO> loansForPayment = engine.getCustomerActiveRiskLoan(userName);
        List<PaymentNotificationDTO> paymentNotifications = engine.getNotifications(userName);
        List<ActiveRiskLoanDTO> riskLoans = loansForPayment.stream().filter(l -> l.getStatus().equals("Risk")).collect(Collectors.toList());
        List<ActiveRiskLoanDTO> closeActiveLoans = loansForPayment.stream().filter(l-> l.getStatus().equals("Active")).collect(Collectors.toList());
        List<ActiveRiskLoanDTO> makeActivePayment = closeActiveLoans.stream().filter(l -> l.getNextPaymentTime() == Engine.getTime()).collect(Collectors.toList());
        PaymentUpdateDTO paymentUpdateDTO = new PaymentUpdateDTO(closeActiveLoans, riskLoans, makeActivePayment, paymentNotifications);
        response.getWriter().println(GSON_INSTANCE.toJson(paymentUpdateDTO));
        response.getWriter().flush();
        response.getWriter().close();
    }
}
