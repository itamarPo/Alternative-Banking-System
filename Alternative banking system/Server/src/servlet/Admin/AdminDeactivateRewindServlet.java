package servlet.Admin;

import database.Engine;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import utils.EngineServlet;
import utils.ServerChecks;

import java.io.IOException;

import static userinterface.Constants.NOTREWIND;
import static userinterface.Constants.REWIND;

@WebServlet(name = "AdminDeactivateRewindServlet", urlPatterns = {"/Admin-Deactivate-Rewind-Servlet"})
public class AdminDeactivateRewindServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userName = ServerChecks.getUserName(request);
        //Session doesn't exist!
        if(userName == null){
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            ServerChecks.setMessageOnResponse(response.getWriter(), ServerChecks.NO_SESSION_FOUND);
            return;
        }
        Engine engine = EngineServlet.getEngine(getServletContext());
        //User isn't admin!
        if(!engine.isUserAdmin(userName)){
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            ServerChecks.setMessageOnResponse(response.getWriter(), ServerChecks.LIMITED_ACCESS);
            return;
        }
        //Server is not in rewind!
        if(engine.getServerStatus().equals(NOTREWIND)){
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            ServerChecks.setMessageOnResponse(response.getWriter(),"Server is already in active mode!");
            return;
        }
        getServletContext().setAttribute("Engine", engine.deactivateRewind());
        ServerChecks.setMessageOnResponse(response.getWriter(), "Server is now in active mode!");
    }
}
