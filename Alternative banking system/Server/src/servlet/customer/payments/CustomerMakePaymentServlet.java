package servlet.customer.payments;

import database.Engine;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import utils.EngineServlet;

import java.io.IOException;

import static userinterface.Constants.*;

@WebServlet(name = "CustomerMakePaymentServlet", urlPatterns = {"/Customer-Make-Payment-Servlet"})
public class CustomerMakePaymentServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Engine engine = EngineServlet.getEngine(getServletContext());
        String userName = String.valueOf(request.getSession().getAttribute(USERNAME));
        String loanID = request.getParameter("loanID");
        String activeOrRisk = request.getParameter("activeOrRisk");
        Double amount= Double.parseDouble(request.getParameter(AMOUNT));
        try{
            if(activeOrRisk.equals("Active")){
                engine.makeActivePayment(loanID,userName);
            } else{
                engine.makeRiskPayment(loanID,userName, amount);
            }
        } catch (Exception e){
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().println(e);
            response.getWriter().flush();
            response.getWriter().close();
        }
    }
}
