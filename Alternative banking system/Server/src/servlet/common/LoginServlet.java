package servlet.common;

import database.Engine;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import utils.EngineServlet;

import java.io.IOException;

@WebServlet(name = "LoginServlet", urlPatterns = {"/login"})
public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Engine engine = EngineServlet.getEngine(getServletContext());
        String userName = request.getParameter("userName");
        String isAdmin = request.getParameter("isAdmin");
        //user already exists
        synchronized (this){
            if(engine.isCustomerExists(userName)){
                response.sendError(401);
            }
            else{
                if(isAdmin.equals("true")){
                    if(!engine.isAdminExist()){
                        engine.setAdminExist(true);
                        request.getSession(true).setAttribute("userName", userName);
                    } else{
                        response.sendError(401);
                    }
                } else{
                    engine.addCustomer(userName,false);
                    request.getSession(true).setAttribute("userName", userName);
                }
            }
        }
    }
}
