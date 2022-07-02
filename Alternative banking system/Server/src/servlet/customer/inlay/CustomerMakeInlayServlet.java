package servlet.customer.inlay;

import com.google.gson.Gson;
import database.Engine;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import objects.loans.NewLoanDTO;
import utils.EngineServlet;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static userinterface.Constants.*;

@WebServlet(name = "CustomerMakeInlayServlet", urlPatterns = {"/Customer-Make-Inlay-Servlet"})
public class CustomerMakeInlayServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Engine engine = EngineServlet.getEngine(getServletContext());
        Gson gson = GSON_INSTANCE;
        String json = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        List<NewLoanDTO> newLoanDTOList = Arrays.asList(gson.fromJson(json, NewLoanDTO[].class));
        String userName = String.valueOf(request.getSession().getAttribute(USERNAME));
        Integer amount = Integer.parseInt(request.getParameter(AMOUNT));
        Integer maxOwnership = Integer.parseInt(request.getParameter("maxOwnership"));
        //TODO: must check that these loans are still relevant, meaning they are still new/active!
        //TODO: make sure this function is synchronized!
        engine.splitMoneyBetweenLoans(newLoanDTOList.stream().map(NewLoanDTO::getLoanID).collect(Collectors.toList()),amount, userName,maxOwnership );
    }
}
