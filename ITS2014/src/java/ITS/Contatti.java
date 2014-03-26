package ITS;

import java.io.*;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "java", urlPatterns = {"/contatti"})
public class Contatti extends HttpServlet {


    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        
        String format = request.getParameter("format");
        if (format == null) format = "html";
        else if (format.equals("")) format = "html";
        
        String mode = request.getParameter("mode");
        if (mode == null) mode = "static";
        else if (mode.equals("")) mode = "static";
        
        PrintWriter writer = response.getWriter();
        
        if (format.equals("html"))
        {
            writer.println("<html>");
            writer.println("<body>");
            writer.println("<h1>Contatti</h1>");
            writer.println("<table id='contatti'>");
            writer.println("<thead>");
            
            writer.println("<tr>");
            writer.println("<th>Cognome</th>");
            writer.println("<th>Nome</th>");
            writer.println("<th>Cellulare</th>");
            writer.println("<th>E-Mail</th>");
            writer.println("</tr>");
            
            writer.println("</thead>");
            writer.println("<tbody>");
            
            if (mode.equals("static")){
     
                FileReader streamContatti = new FileReader("C:\\Nuova cartella\\contatti.csv");
                BufferedReader readerContatti = new BufferedReader(streamContatti);

                while(true)
                {
                    String line=readerContatti.readLine();
                    if(line == null) break;
                    if(line.equals("")) break;

                    String[] values = line.split(",");

                    writer.println("<tr>");
                    writer.printf("\r\n<td>%s</td>", values[0]);
                    writer.printf("\r\n<td>%s</td>", values[1]);
                    writer.printf("\r\n<td>%s</td>", values[2]);
                    writer.printf("\r\n<td>%s</td>", values[3]);
                    writer.println("</tr>");
                }
                readerContatti.readLine();

            } 
            
            writer.println("</tbody>");
            writer.println("</table>");
            
            if(mode.equals("dynamic")){
                writer.println("<script src='Scripts/jquery-1.11.0.js'></script>");
                writer.println("<script src='Scripts/contatti.js'></script>");
            }
                
                        
            writer.println("</body>");
            writer.println("</html>");
            
        } else if (format.equals("json"))
        {
            response.setContentType("text/json");
            writer.println("[");
            
            FileReader fileContatti
                    = new FileReader("C:\\Nuova cartella\\contatti.csv");
            BufferedReader readerContatti
                    = new BufferedReader(fileContatti);
            readerContatti.readLine();

            boolean first = true;
            
            while(true)
            {
                String line = readerContatti.readLine();
                if (line == null) break;
                if (line.equals("")) break;

                String[] values = line.split(";");

                if (first)
                {   
                    first = false;
                }
                else
                {
                    writer.println(",");
                }
                
                writer.println("{");
                writer.printf("\"cognome\": \"%s\"", values[0]);
                writer.printf(", \"nome\": \"%s\"", values[1]);
                writer.printf(", \"cellulare\": \"%s\"", values[2]);
                writer.printf(", \"email\": \"%s\"", values[3]);
                writer.println("}");
            }

            readerContatti.close();
            
            writer.println("]");
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
