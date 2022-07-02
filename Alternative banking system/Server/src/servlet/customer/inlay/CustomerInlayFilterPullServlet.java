package servlet.customer.inlay;

import com.google.gson.Gson;
import database.Engine;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import objects.customers.loanInfo.CustomerFilterLoansDTO;
import objects.loans.NewLoanDTO;
import objects.loans.PendingLoanDTO;
import sun.misc.IOUtils;
import utils.EngineServlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static java.lang.System.out;
import static userinterface.Constants.CHECK_INLAY_INPUT_RESOURCE;
import static userinterface.Constants.USERNAME;

@WebServlet(name = "CustomerInlayFilterPullServlet", urlPatterns = {"/Customer-Inlay-Filter-Pull-Servlet"})
public class CustomerInlayFilterPullServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Integer minYaz = Integer.parseInt(request.getParameter("minYAZ"));
        Integer minInterest = Integer.parseInt(request.getParameter("minInterest"));
        Integer maxOpenLoans = Integer.parseInt(request.getParameter("maxOpenLoans"));
        Gson gson = new Gson();
        String json = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        List<String> categories= Arrays.asList(gson.fromJson(json, String[].class));
        String userName = String.valueOf(request.getSession().getAttribute(USERNAME));
        Engine engine = EngineServlet.getEngine(getServletContext());
        List<NewLoanDTO> filteredList = engine.getFilteredLoans(categories, minInterest, minYaz, userName, maxOpenLoans);
        List<NewLoanDTO> newLoans = new ArrayList<>();
        filteredList.stream().filter(l -> l.getStatus().equals("New")).forEach(l -> newLoans.add(l));
        List<PendingLoanDTO> pendingLoans = new ArrayList<>();
        filteredList.stream().filter(l -> l.getStatus().equals("Pending")).forEach(l -> pendingLoans.add((PendingLoanDTO) l));
        CustomerFilterLoansDTO loans = new CustomerFilterLoansDTO(newLoans,pendingLoans);
        Gson returnGson = new Gson();
        String returnJson = returnGson.toJson(loans);
        response.getWriter().println(returnJson);
        response.getWriter().flush();
        response.getWriter().close();

//        Integer minYaz = Integer.parseInt(request.getParameter("minYAZ"));
//        Integer minInterest = Integer.parseInt(request.getParameter("minInterest"));
//        Integer maxOpenLoans = Integer.parseInt(request.getParameter("maxOpenLoans"));
//        Gson gson = new Gson();
//        String json = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
//        List<String> categories= Arrays.asList(gson.fromJson(json, String[].class));
//        Engine engine = EngineServlet.getEngine(getServletContext());
//        List<NewLoanDTO> filteredList = engine.getFilteredLoans(categories, minInterest, minYaz, String.valueOf(request.getSession().getAttribute(USERNAME)), maxOpenLoans);
//        List<NewLoanDTO> newLoans = new ArrayList<>();
//        filteredList.stream().filter(l -> l.getStatus().equals("New")).forEach(l -> newLoans.add(l));
//        List<PendingLoanDTO> pendingLoans = new ArrayList<>();
//        filteredList.stream().filter(l -> l.getStatus().equals("Pending")).forEach(l -> pendingLoans.add((PendingLoanDTO) l));
//        CustomerFilterLoansDTO loans = new CustomerFilterLoansDTO(newLoans,pendingLoans);
//        Gson returnGson = new Gson();
//        String returnJson = returnGson.toJson(loans);
//        response.getWriter().println(returnJson);
//        response.getWriter().flush();
//        response.getWriter().close();
    }
}
