package servlet.Admin;

import com.google.gson.Gson;
import database.Engine;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import utils.EngineServlet;

import java.io.IOException;

@WebServlet(name = "AdminIncreaseYazServlet", urlPatterns = {"/Admin-Increase-Yaz-Servlet"})
public class AdminIncreaseYazServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Engine engine = EngineServlet.getEngine(getServletContext());
        engine.moveTImeForward2();
        response.setContentType("application/json");
        Gson gson = new Gson();
        Integer yaz = Engine.getTime();
        String json = gson.toJson(yaz.toString());
        response.getWriter().println(json);
        response.getWriter().flush();
        response.getWriter().close();
        //TODO: later on, needs to save the engine to a file!
    }
}
