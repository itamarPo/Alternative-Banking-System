package servlet.Admin;

import com.google.gson.Gson;
import database.Engine;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import objects.customers.CustomerInfoDTO;
import userinterface.Constants;
import utils.EngineServlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(name = "AdminPullInfoServlet", urlPatterns = {"/Admin-Pull-Servlet"})
public class AdminPullInfoServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Engine engine = EngineServlet.getEngine(getServletContext());
        // customer info
        List<CustomerInfoDTO> customerInfoDTOList = engine.getCustomerInfo();
        response.setContentType("application/json");
        try (PrintWriter out = response.getWriter()) {
            Gson gson = new Gson();
            //UserManager userManager = ServletUtils.getUserManager(getServletContext());
            //Set<String> usersList = userManager.getUsers();
            String json = gson.toJson(customerInfoDTOList);
            out.println(json);
            out.flush();
        }

        // loan info
    }
}
