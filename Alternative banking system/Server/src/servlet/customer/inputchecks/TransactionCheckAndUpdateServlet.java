package servlet.customer.inputchecks;

import com.google.gson.Gson;
import database.Engine;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import utils.EngineServlet;

import java.io.IOException;

import static userinterface.Constants.*;

@WebServlet(name = "TransactionCheckAndUpdateServlet", urlPatterns = {"/Transaction-CheckAndUpdate-Servlet"})
public class TransactionCheckAndUpdateServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Engine engine = EngineServlet.getEngine(getServletContext());
        Boolean chargeOrWithdraw = Boolean.parseBoolean(request.getParameter("chargeOrWithdraw"));
        Double amount = Double.parseDouble(request.getParameter(AMOUNT));
        String userName = String.valueOf(request.getSession().getAttribute(USERNAME));
        Gson gson = GSON_INSTANCE;
        if(chargeOrWithdraw)
        {
           engine.addMoneyToAccount(engine.getCustomerByName(userName), amount);
           response.getWriter().println(gson.toJson(engine.getCustomerInfo(userName)));
        }
        else{
            try {
                engine.drawMoneyFromAccount(engine.getCustomerByName(userName), amount);
                response.getWriter().println(gson.toJson(engine.getCustomerInfo(userName)));
            } catch (Exception e) {
                response.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
                response.getWriter().println(e.toString());
            }
        }
    }
}
