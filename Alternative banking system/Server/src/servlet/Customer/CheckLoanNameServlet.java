package servlet.Customer;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import utils.EngineServlet;

import java.io.IOException;

@WebServlet(name = "CheckLoanName", urlPatterns = {"/Check-Loan-Name"})
public class CheckLoanNameServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if(EngineServlet.getEngine(getServletContext()).getLoanByName((String)request.getAttribute("LoanName"))==null)
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
    }
}
