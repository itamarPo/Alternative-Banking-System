package servlet.customer.buySellLoans;

import com.google.gson.Gson;
import database.Engine;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import utils.EngineServlet;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static userinterface.Constants.GSON_INSTANCE;
import static userinterface.Constants.USERNAME;

@WebServlet(name = "CustomerSellLoansServlet", urlPatterns = {"/Customer-Sell-Loans-Servlet"})
public class CustomerSellLoansServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Engine engine = EngineServlet.getEngine(getServletContext());
        String json = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        //TODO: for some reason after this line json=null, not sure why.
        List<String> loansToSell = Arrays.asList(GSON_INSTANCE.fromJson(json, String[].class));
        String userName = String.valueOf(request.getSession().getAttribute(USERNAME));
        try{
            engine.setLoansForSale(userName,loansToSell);
        } catch (Exception e){
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().println("One or more loans are not active anymore so it can't be sold!");
        }
    }
}
