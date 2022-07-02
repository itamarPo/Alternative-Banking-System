package servlet.customer.payments;

import database.Engine;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import userinterface.utils.HttpUtil;
import utils.EngineServlet;

import java.io.IOException;

@WebServlet(name = "CustomerPullPaymentsServlet", urlPatterns = {"/Customer-Pull-Payments-Servlet"})
public class CustomerPullPaymentsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Engine engine = EngineServlet.getEngine(req.getServletContext());
    }
}
