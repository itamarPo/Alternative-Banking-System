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
import objects.loans.NewLoanDTO;
import utils.EngineServlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(name = "AdminPullInformationServlet", urlPatterns = {"/Admin-Pull-Information-Servlet"})
public class AdminPullServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Engine engine = EngineServlet.getEngine(getServletContext());
        // customer info
        List<CustomerInfoDTO> customerInfoDTOList = engine.getCustomerInfo();
        List<NewLoanDTO> loansInfoList = engine.getLoansInfo();
        LoanAndCustomerInfoDTO loanAndCustomerInfoDTO = new LoanAndCustomerInfoDTO(customerInfoDTOList, loansInfoList);
        response.setContentType("application/json");
        try (PrintWriter out = response.getWriter()) {
            Gson gson = new Gson();
            //UserManager userManager = ServletUtils.getUserManager(getServletContext());
            //Set<String> usersList = userManager.getUsers();
            String json = gson.toJson(loanAndCustomerInfoDTO);
            out.println(json);
            out.flush();
            //out.println(json2);
            //out.flush();
        }

        // loan info

    }
}
