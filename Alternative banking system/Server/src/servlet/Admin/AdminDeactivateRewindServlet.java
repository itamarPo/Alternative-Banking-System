package servlet.Admin;

import database.Engine;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import utils.EngineServlet;

import java.io.IOException;

@WebServlet(name = "AdminDeactivateRewindServlet", urlPatterns = {"/Admin-Deactivate-Rewind-Servlet"})
public class AdminDeactivateRewindServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //TODO: make sure there is an active session for admin!
        Engine engine = EngineServlet.getEngine(getServletContext());
        getServletContext().setAttribute("Engine", engine.deactivateRewind());
        //TODO: might not work!
        Engine engine2 = EngineServlet.getEngine(getServletContext());
//        engine = engine.deactivateRewind();
    }
}
