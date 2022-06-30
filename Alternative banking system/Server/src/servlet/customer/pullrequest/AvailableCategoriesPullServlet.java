package servlet.customer.pullrequest;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import utils.EngineServlet;

import java.io.IOException;
import java.util.List;

import static userinterface.Constants.GSON_INSTANCE;

@WebServlet(name = "AvailableCategoriesPullServlet", urlPatterns = {"/Available-Categories-Pull-Servlet"})
public class AvailableCategoriesPullServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<String> categoriesList = EngineServlet.getEngine(getServletContext()).getCategoriesList().getCategoriesList();
        String json = GSON_INSTANCE.toJson(categoriesList);
        response.getWriter().println(json);
        response.getWriter().flush();
    }
}
