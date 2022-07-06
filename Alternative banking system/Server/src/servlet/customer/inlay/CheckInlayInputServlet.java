package servlet.customer.inlay;

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

import static userinterface.Constants.*;

@WebServlet(name = "CheckInlayInputServlet", urlPatterns = {"/Check-Inlay-Input-Servlet"})
public class CheckInlayInputServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String number = request.getParameter(AMOUNT);
        double amount = Double.parseDouble(number);
        Engine engine = EngineServlet.getEngine(getServletContext());
//        Gson gson = new Gson();
//        String json;
        try {
            String userName = String.valueOf(request.getSession().getAttribute(USERNAME));
            engine.checkAmountOfInvestment(String.valueOf(request.getSession().getAttribute(USERNAME)), amount);
            request.getServletContext().getRequestDispatcher(CUSTOMER_INLAY_FILTER_RESOURCE).forward(request,response);
        }catch (Exception e){

            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.getWriter().println(e);
            response.getWriter().flush();
            response.getWriter().close();
        }
    }
}
