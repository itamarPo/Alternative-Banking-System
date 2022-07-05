package servlet.Admin;

import database.Engine;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import utils.EngineServlet;

import java.io.IOException;

@WebServlet(name = "AdminRewindTimeServlet", urlPatterns = {"/Admin-Rewind-Time-Servlet"})
public class AdminRewindTimeServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Engine engine = EngineServlet.getEngine(getServletContext());
        String selectedYaz = request.getParameter("TimeToMove");
        try {
            getServletContext().setAttribute("Engine", engine.loadSelcetedYaz("Yaz", selectedYaz));
        } catch (Exception e) {
            //TODO: error, there is no file for the selected yaz! (probably postman user!)
        }
    }
}
