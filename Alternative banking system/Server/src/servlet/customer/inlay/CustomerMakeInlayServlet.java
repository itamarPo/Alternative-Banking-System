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
        List<String> newLoanDTOList = Arrays.asList(gson.fromJson(json, String[].class));
        String userName = String.valueOf(request.getSession().getAttribute(USERNAME));
        Integer amount = Integer.parseInt(request.getParameter(AMOUNT));
        Integer maxOwnership = Integer.parseInt(request.getParameter("maxOwnership"));
        synchronized (this){
            if(engine.checkLoansStatus(newLoanDTOList)){
                engine.splitMoneyBetweenLoans(newLoanDTOList,amount, userName,maxOwnership );
            } else{
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().println("One or more loans are no longer pending/new!");
            }
        }
    }
}
