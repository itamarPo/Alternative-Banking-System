package servlet.Admin;

import com.google.gson.Gson;
import database.Engine;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import objects.admin.LoanAndCustomerInfoDTO;
import objects.customers.CustomerInfoDTO;
import objects.loans.ActiveRiskLoanDTO;
import objects.loans.FinishedLoanDTO;
import objects.loans.NewLoanDTO;
import objects.loans.PendingLoanDTO;
import utils.EngineServlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "AdminPullInformationServlet", urlPatterns = {"/Admin-Pull-Information-Servlet"})
public class AdminPullServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Engine engine = EngineServlet.getEngine(getServletContext());
        //loans info
        List<NewLoanDTO> allLoans = engine.getLoansInfo(null);
        List<NewLoanDTO> newLoans = new ArrayList<>();
        allLoans.stream().filter(l -> l.getStatus().equals("New")).forEach(l -> newLoans.add(l));
        List<PendingLoanDTO> pendingLoans = new ArrayList<>();
        allLoans.stream().filter(l -> l.getStatus().equals("Pending")).forEach(l-> pendingLoans.add((PendingLoanDTO) l));
        List<ActiveRiskLoanDTO> activeLoans = new ArrayList<>();
        allLoans.stream().filter(l -> l.getStatus().equals("Active")).forEach(l-> activeLoans.add((ActiveRiskLoanDTO) l));
        List<ActiveRiskLoanDTO> riskLoans = new ArrayList<>();
        allLoans.stream().filter(l -> l.getStatus().equals("Risk")).forEach(l-> riskLoans.add((ActiveRiskLoanDTO) l));
        List<FinishedLoanDTO> finishedLoans = new ArrayList<>();
        allLoans.stream().filter(l -> l.getStatus().equals("Finished")).forEach(l-> finishedLoans.add((FinishedLoanDTO) l));
        // customer info
        List<CustomerInfoDTO> customerInfoDTOList = engine.getCustomersInfo();

        LoanAndCustomerInfoDTO loanAndCustomerInfoDTO = new LoanAndCustomerInfoDTO(customerInfoDTOList,newLoans,pendingLoans,activeLoans,riskLoans,finishedLoans);
        response.setContentType("application/json");
        try (PrintWriter out = response.getWriter()) {
            Gson gson = new Gson();
            String json = gson.toJson(loanAndCustomerInfoDTO);
            out.println(json);
            out.flush();
        }


    }
}
