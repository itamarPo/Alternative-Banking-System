package servlet.customer.inputchecks;

import database.Engine;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import utils.EngineServlet;

import java.io.IOException;

import static userinterface.Constants.USERNAME;

@WebServlet(name = "CheckInlaySumServlet", urlPatterns = {"/Check-Inlay-Sum-Servlet"})
public class CheckInlaySumServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String number = request.getParameter("Amount");
        double amount = Double.parseDouble(number);
        Engine engine = EngineServlet.getEngine(getServletContext());
        try {
            engine.checkAmountOfInvestment(String.valueOf(request.getSession().getAttribute(USERNAME)), amount);
        }catch (Exception e){
            response.sendError(HttpServletResponse.SC_NOT_ACCEPTABLE);
            response.getWriter().println(e.toString());
        }
    }
}
