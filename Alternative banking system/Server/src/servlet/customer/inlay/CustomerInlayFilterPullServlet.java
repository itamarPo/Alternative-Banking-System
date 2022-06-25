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

import static java.lang.System.out;
import static userinterface.Constants.USERNAME;

@WebServlet(name = "CustomerInlayFilterPullServlet", urlPatterns = {"/Customer-Inlay-Filter-Pull-Servlet"})
public class CustomerInlayFilterPullServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Integer minYaz = Integer.getInteger(request.getParameter("minYAZ"));
        Integer minInterest = Integer.getInteger(request.getParameter("minInterest"));
        Integer maxOpenLoans = Integer.getInteger(request.getParameter("maxOpenLoans"));
        Gson gson = new Gson();
        String json = request.getReader().toString();
        List<String> categories= Arrays.stream(gson.fromJson(json, String[].class)).collect(Collectors.toList());
        Engine engine = EngineServlet.getEngine(getServletContext());
        List<NewLoanDTO> filteredList = engine.getFilteredLoans(categories, minInterest, minYaz, String.valueOf(request.getSession().getAttribute(USERNAME)), maxOpenLoans);
        Gson returnGson = new Gson();
        String returnJson = returnGson.toJson(filteredList);
        //String json = gson.toJson(loanAndCustomerInfoDTO);
        response.getWriter().println(returnJson);
        response.getWriter().flush();
    }
}
