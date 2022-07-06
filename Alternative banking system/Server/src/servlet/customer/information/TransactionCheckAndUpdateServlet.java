package servlet.customer.information;

import com.google.gson.Gson;
import database.Engine;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import utils.EngineServlet;
import utils.ServerChecks;

import java.io.IOException;

import static userinterface.Constants.*;

@WebServlet(name = "TransactionCheckAndUpdateServlet", urlPatterns = {"/Transaction-CheckAndUpdate-Servlet"})
public class TransactionCheckAndUpdateServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userName = ServerChecks.getUserName(request);
        //Session doesn't exist!
        if (userName == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            ServerChecks.setMessageOnResponse(response.getWriter(), ServerChecks.NO_SESSION_FOUND);
            return;
        }
        Engine engine = EngineServlet.getEngine(getServletContext());
        //User isn't customer!
        if (engine.isUserAdmin(userName)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            ServerChecks.setMessageOnResponse(response.getWriter(), ServerChecks.LIMITED_ACCESS);
            return;
        }
        //Server is in rewind!
        if (engine.getServerStatus().equals(REWIND)) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            ServerChecks.setMessageOnResponse(response.getWriter(), ServerChecks.STATUS_PROBLEM);
            return;
        }
        try{
            String chargeorWithdraw = request.getParameter("charge");
            if(!chargeorWithdraw.equalsIgnoreCase("true") && !chargeorWithdraw.equalsIgnoreCase("false")){
                throw new Exception();
            }
            Boolean chargeOrWithdraw = Boolean.parseBoolean(request.getParameter("charge"));
            Double amount = Double.parseDouble(request.getParameter(AMOUNT));
            if(amount <= 0){
                throw new Exception();
            }
            Gson gson = GSON_INSTANCE;
            if(chargeOrWithdraw)
            {
                engine.addMoneyToAccount(engine.getCustomerByName(userName), amount);
                response.setContentType("application/json");
                response.getWriter().println(gson.toJson(engine.getCustomerInfo(userName)));
            } else{
                try {
                    engine.drawMoneyFromAccount(engine.getCustomerByName(userName), amount);
                    response.setContentType("application/json");
                    ServerChecks.setMessageOnResponse(response.getWriter(),gson.toJson(engine.getCustomerInfo(userName)));
//                    response.getWriter().println(gson.toJson(engine.getCustomerInfo(userName)));
                } catch (Exception e) {
                    response.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
                    ServerChecks.setMessageOnResponse(response.getWriter(), e.toString());
//                    response.getWriter().println(e.toString());
                }
            }
        } catch (Exception e){
            ServerChecks.setMessageOnResponse(response.getWriter(), "One of the query parameters is missing or invalid!");
        }

    }
}
