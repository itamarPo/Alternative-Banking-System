package servlet.customer.inputchecks;

import com.google.gson.Gson;
import database.Engine;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import objects.customers.CustomerInfoInlayDTO;
import utils.EngineServlet;

import java.io.IOException;

import static userinterface.Constants.USERNAME;

@WebServlet(name = "CheckInlayInputServlet", urlPatterns = {"/Check-Inlay-Input-Servlet"})
public class CheckInlayInputServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String number = request.getParameter("Amount");
        double amount = Double.parseDouble(number);
        Engine engine = EngineServlet.getEngine(getServletContext());
        CustomerInfoInlayDTO customerInfoInlayDTO;
        int maxOpenLoans = engine.getNumOfLoans();
        Gson gson = new Gson();
        String json;
        try {
            engine.checkAmountOfInvestment(String.valueOf(request.getSession().getAttribute(USERNAME)), amount);
             customerInfoInlayDTO = new CustomerInfoInlayDTO(false,"", maxOpenLoans);
             json = gson.toJson(customerInfoInlayDTO);
             response.getWriter().println(json);
        }catch (Exception e){
            customerInfoInlayDTO = new CustomerInfoInlayDTO(true, e.toString(), maxOpenLoans);
            response.sendError(HttpServletResponse.SC_NOT_ACCEPTABLE);
            json = gson.toJson(customerInfoInlayDTO);
            response.getWriter().println(json);
        }
    }
}
