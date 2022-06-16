package servlet.Customer;

import database.Engine;
import exceptions.filesexepctions.LoanCategoryNotExistException;
import exceptions.filesexepctions.TimeOfPaymentNotDivideEqualyException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import utils.EngineServlet;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Collection;
import java.util.Scanner;

@WebServlet(name = "FileUploadServlet", urlPatterns = {"/upload-file"})
public class FileUploadServlet extends HttpServlet{

        @Override
        protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            //TODO: fix row 26
            response.setContentType("text/plain");
            InputStream file = request.getPart("file1").getInputStream();
            String customerName = null;
            for(Cookie cookie: request.getCookies())
                if(cookie.getName().equals("Name")){
                    customerName = cookie.getValue();
                }
            if(customerName == null){
                response.sendError(HttpServletResponse.SC_FORBIDDEN);
            }else{
                try {
                    EngineServlet.getEngine(getServletContext()).loadFile(file, customerName);
                }catch (LoanCategoryNotExistException e){
                    response.getWriter().println(e.toString());
                    response.sendError(HttpServletResponse.SC_NOT_ACCEPTABLE);
                }catch (TimeOfPaymentNotDivideEqualyException e) {
                    response.getWriter().println(e.toString());
                    response.sendError(HttpServletResponse.SC_NOT_ACCEPTABLE);
                } catch (Exception e) {
                    response.sendError(HttpServletResponse.SC_NOT_ACCEPTABLE);
                }
            }
        }

        private void printPart(Part part, PrintWriter out) {
            StringBuilder sb = new StringBuilder();
            sb
                    .append("Parameter Name: ").append(part.getName()).append("\n")
                    .append("Content Type (of the file): ").append(part.getContentType()).append("\n")
                    .append("Size (of the file): ").append(part.getSize()).append("\n")
                    .append("Part Headers:").append("\n");

            for (String header : part.getHeaderNames()) {
                sb.append(header).append(" : ").append(part.getHeader(header)).append("\n");
            }

            out.println(sb.toString());
        }


}

