package servlet.customer;

import database.Engine;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import utils.EngineServlet;

import java.io.IOException;

import static userinterface.Constants.AMOUNT;
import static userinterface.Constants.USERNAME;

@WebServlet(name = "CreateLoanServlet", urlPatterns = {"/Create-Loan-Servlet"})
public class CreateLoanServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Engine engine = EngineServlet.getEngine(getServletContext());
       // String customerName = request.getParameter(USERNAME);
        String customerName = String.valueOf(request.getSession().getAttribute(USERNAME));
        String loanID = request.getParameter("loanID");
        String category = request.getParameter("category");
        Integer timePerPayment = Integer.parseInt(request.getParameter("timePerPayment"));
        Integer loanInterest = Integer.parseInt(request.getParameter("loanInterest"));
        Integer loanDuration = Integer.parseInt(request.getParameter("loanDuration"));
        Double amount = Double.parseDouble(request.getParameter(AMOUNT));
        synchronized (this) {
            if (engine.getLoanByName(loanID) == null) {
                engine.createNewLoan(customerName, category, loanID, loanDuration, loanInterest, timePerPayment, amount);
            } else{
                response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            }
        }
        else{
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

}
