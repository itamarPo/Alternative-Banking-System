package servlet.customer.payments;

import database.Engine;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import utils.EngineServlet;

import java.io.IOException;

import static userinterface.Constants.CUSTOMER_PAYMENT_INFO_RESOURCE;
import static userinterface.Constants.USERNAME;

@WebServlet(name = "CustomerClosePaymentsServlet", urlPatterns = {"/Customer-Close-Payments-Servlet"})
public class CustomerClosePaymentServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Engine engine = EngineServlet.getEngine(getServletContext());
            engine.closeLoan(String.valueOf(request.getSession().getAttribute(USERNAME)), request.getParameter("loanID"));
            //request.getServletContext().getRequestDispatcher(CUSTOMER_PAYMENT_INFO_RESOURCE).forward(request, response);
        } catch (Exception e) {
           response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
           response.getWriter().println(e);
           response.getWriter().flush();
           response.getWriter().close();
        }
    }
}
