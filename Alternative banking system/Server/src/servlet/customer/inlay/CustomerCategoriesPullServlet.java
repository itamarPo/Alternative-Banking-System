package servlet.customer.inlay;

import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import utils.EngineServlet;

import java.io.IOException;

@WebServlet(name = "CustomerCategoriesPullServlet", urlPatterns = {"/Customer-Categories-Pull-Servlet"})
public class CustomerCategoriesPullServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Gson returnGson = new Gson();
        String returnJson = returnGson.toJson(EngineServlet.getEngine(getServletContext()).getCategoriesList().getCategoriesList());
        //String json = gson.toJson(loanAndCustomerInfoDTO);
        response.getWriter().println(returnJson);
        response.getWriter().flush();
        response.getWriter().close();
    }
}
