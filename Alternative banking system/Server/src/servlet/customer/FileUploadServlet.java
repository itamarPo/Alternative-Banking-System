package servlet.customer;

import exceptions.filesexepctions.LoanCategoryNotExistException;
import exceptions.filesexepctions.LoanIDAlreadyExists;
import exceptions.filesexepctions.TimeOfPaymentNotDivideEqualyException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import utils.EngineServlet;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Scanner;

import static userinterface.Constants.USERNAME;

@WebServlet(name = "FileUploadServlet", urlPatterns = {"/upload-file"})
@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 1024 * 1024 * 5, maxRequestSize = 1024 * 1024 * 5 * 5)
public class FileUploadServlet extends HttpServlet{

        @Override
        protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            response.setContentType("text/plain");
            //InputStream file = request.getParts();

            PrintWriter out = response.getWriter();

            Collection<Part> parts = request.getParts();

            out.println("Total parts : " + parts.size());

            StringBuilder fileContent = new StringBuilder();
            for (Part part : parts) {
                printPart(part, out);

                //to write the content of the file to an actual file in the system (will be created at c:\samplefile)
                //part.write("samplefile");

                //to write the content of the file to a string
                fileContent.append(readFromInputStream(part.getInputStream()));
            }
            InputStream file = new ByteArrayInputStream(fileContent.toString().getBytes(StandardCharsets.UTF_8));
            //printFileContent(fileContent.toString(), out);



            String customerName = null;
            if(request.getSession(false) != null){
                customerName = String.valueOf(request.getSession(false).getAttribute(USERNAME));
            }
            if(customerName == null){
                response.sendError(HttpServletResponse.SC_FORBIDDEN);
            }else{
                //TODO: file loading errors aren't working properly
                try {
                    EngineServlet.getEngine(getServletContext()).loadFile(file, customerName);
                    response.setStatus(200);
                }catch (LoanCategoryNotExistException e){
                    response.sendError(HttpServletResponse.SC_NOT_ACCEPTABLE);
                    response.getWriter().write(e.toString());
                }catch (TimeOfPaymentNotDivideEqualyException e) {
                    response.sendError(HttpServletResponse.SC_NOT_ACCEPTABLE);
                    response.getWriter().write(e.toString());
                } catch (LoanIDAlreadyExists e){
                    response.sendError(HttpServletResponse.SC_NOT_ACCEPTABLE);
                    response.getWriter().write(e.toString());
                }
                catch (Exception e) {
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST);
                    response.getWriter().write("Unknown error!");
                }
            }
            response.getWriter().close();
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

    private String readFromInputStream(InputStream inputStream) {
        return new Scanner(inputStream).useDelimiter("\\Z").next();
    }

    private void printFileContent(String content, PrintWriter out) {
        out.println("File content:");
        out.println(content);
    }


}






