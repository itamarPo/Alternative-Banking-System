package servlet.common;

import database.Engine;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import utils.EngineServlet;

import java.io.IOException;

import static userinterface.Constants.REWIND;
import static userinterface.Constants.USERNAME;

@WebServlet(name = "LoginServlet", urlPatterns = {"/login"})
public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Engine engine = EngineServlet.getEngine(getServletContext());
        String userName = request.getParameter(USERNAME);
        String isAdmin = request.getParameter("isAdmin");
        //user already exists
        synchronized (this){
            if(engine.isNameExists(userName)){
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            }
            else{
                if(engine.getServerStatus().equals(REWIND)){
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    response.getWriter().println("Can't login because admin uses rewind!");
                } else{
                    if(isAdmin.equals("true")){
                        if(!engine.isAdminExist()){
                            engine.setAdminExist(true);
                            request.getSession(true).setAttribute(USERNAME, userName);
                            engine.setAdminName(userName);
                        } else{
                            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                        }
                    } else{
                        engine.addCustomer(userName,false);
                        request.getSession(true).setAttribute(USERNAME, userName);
                    }
                }

            }
        }
    }
}
