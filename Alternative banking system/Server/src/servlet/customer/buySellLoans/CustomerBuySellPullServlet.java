package servlet.customer.buySellLoans;

import database.Engine;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import objects.customers.loanInfo.BuySellUpdateDTO;
import objects.loans.LoansForSaleDTO;
import utils.EngineServlet;

import java.io.IOException;
import java.util.List;

import static userinterface.Constants.GSON_INSTANCE;
import static userinterface.Constants.USERNAME;

@WebServlet(name = "CustomerBuySellPullServlet", urlPatterns = {"/Customer-BuySell-Pull-Servlet"})
public class CustomerBuySellPullServlet extends HttpServlet {



    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Engine engine = EngineServlet.getEngine(getServletContext());
        String userName = String.valueOf(request.getSession().getAttribute(USERNAME));
        List<String> loansAvailableToSell = engine.getLoansAvailableToSell(userName);
        List<LoansForSaleDTO> loansAvailableToBuy = engine.getLoansAvailableToBuy(userName);
        BuySellUpdateDTO buySellLoans = new BuySellUpdateDTO(loansAvailableToBuy,loansAvailableToSell);
        response.getWriter().println(GSON_INSTANCE.toJson(buySellLoans));
        response.getWriter().flush();
        response.getWriter().close();
    }
}
