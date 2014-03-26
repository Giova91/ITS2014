package ITS;

import com.google.gson.Gson;
import com.google.gson.stream.JsonWriter;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet(name = "ContattiSQL", urlPatterns = {"/ContattiSQL"})
public class ContattiSQL extends HttpServlet {

    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet contattiSQL</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet contattiSQL at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        } finally {
            out.close();
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String contattoId = request.getParameter("ContattoId");
        if (contattoId == null)
            doGetAll(request, response);
        else if (contattoId.equals(""))
            doGetAll(request, response);
        else
            doGetSingle(contattoId, request, response);
        
    }
         
    protected void doGetSingle(String contattoId, HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try{
            try {
                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver").newInstance();
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(ITS.ContattiSQL.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InstantiationException ex) {
                Logger.getLogger(ITS.ContattiSQL.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalAccessException ex) {
                Logger.getLogger(ITS.ContattiSQL.class.getName()).log(Level.SEVERE, null, ex);
            }

            //accesso al db
            
            Connection con = 
                    DriverManager
                            .getConnection(
                                    "jdbc:"
                                            +"sqlserver://G-FONTANEL\\MYSQLSERVER;"
                                            +"databaseName=ITS2014;"
                                            +"user=ITS2014;password=ITS2014");
            
            Statement stmt = con.createStatement();
            ResultSet rs = 
                    stmt.executeQuery(
                    "SELECT ContattoId, Cognome, Nome, Cellulare, Email"
                    + " FROM dbo.Contatti"
                    + " WHERE ContattiId = " + contattoId);
            rs.next();
           
            ContattoViewModel viewModel = new ContattoViewModel();
            viewModel.cognome = rs.getString(1);
            viewModel.nome = rs.getString(2);
            viewModel.cellulare = rs.getString(3);
            viewModel.email = rs.getString(4);
            
            JsonWriter writer = new JsonWriter(response.getWriter());
            Gson gson = new Gson();
            gson.toJson(
                        viewModel
                        , ContattoViewModel.class
                        , writer);
            
            con.close();
            
            } catch (SQLException ex) {
            Logger.getLogger(ITS.ContattiSQL.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }    
    
    
    protected void doGetAll(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {        
            PrintWriter writer = response.getWriter();
            
            response.setContentType("application/json");
            response.setCharacterEncoding("utf-8");
            writer.print("[");
            try {
                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver").newInstance();
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(ITS.ContattiSQL.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InstantiationException ex) {
                Logger.getLogger(ITS.ContattiSQL.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalAccessException ex) {
                Logger.getLogger(ITS.ContattiSQL.class.getName()).log(Level.SEVERE, null, ex);
            }

            //accesso al db
            
            Connection con = 
                    DriverManager
                            .getConnection(
                                    "jdbc:"
                                            +"sqlserver://G-FONTANEL\\MYSQLSERVER;"
                                            +"databaseName=ITS2014;"
                                            +"user=ITS2014;password=ITS2014");
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT ContattoId, Cognome, Nome, Cellulare, Email FROM dbo.Contatti");
            
            boolean first = true;
            while (true) {
                if(!rs.next()) break;
                int contattoId=rs.getInt(1);
                String cognome = rs.getString(2);
                String nome = rs.getString(3);
                String cellulare = rs.getString(4);
                String email = rs.getString("Email");
 
                if (first) {
                    first = false;
                }
                else {
                    writer.println(",");
                }

                writer.print("{");
                writer.printf("\"contatto_id\": \"%s\"", contattoId);
                writer.printf(", \"cognome\": \"%s\"", cognome);
                writer.printf(", \"nome\": \"%s\"", nome);
                writer.printf(", \"cellulare\": \"%s\"", cellulare);
                writer.printf(", \"email\": \"%s\"", email);
                writer.print(" }");
            }
            
            con.close();
            writer.println("]");
            } catch (SQLException ex) {
            Logger.getLogger(ITS.ContattiSQL.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        try {
            BufferedReader reader = request.getReader();
            
            Gson gson = new Gson();
            ContattoViewModel contatto = (ContattoViewModel) gson.fromJson(reader, ContattoViewModel.class); //in questo modo si legge lo stream in ingresso e restituisce ContattoViewModel
            try {
                try {
                    Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver").newInstance();
                } catch (InstantiationException ex) {
                    Logger.getLogger(ContattiSQL.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IllegalAccessException ex) {
                    Logger.getLogger(ContattiSQL.class.getName()).log(Level.SEVERE, null, ex);
                }
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(ContattiSQL.class.getName()).log(Level.SEVERE, null, ex);
            }
            
           Connection con = 
                    DriverManager
                            .getConnection(
                                    "jdbc:"
                                            +"sqlserver://G-FONTANEL\\MYSQLSERVER;"
                                            +"databaseName=ITS2014;"
                                            +"user=ITS2014;password=ITS2014");
            
            Statement stmt = con.createStatement();
            
            String sql =
                    "INSERT INTO dbo.Contatti (Cognome, Nome, Cellulare, Email)"
                    + "VALUES ("
                    + "'" + contatto.cognome +"'"
                    + ", '" + contatto.nome +"'"
                    + ", '" + contatto.cellulare +"'"
                    + ", '" + contatto.email +"'"
                    + ")"
                    ;
            
            stmt.execute(sql); //esegue la query
            
            JsonWriter writer = new JsonWriter(response.getWriter());
            gson.toJson(Risposta.OK, Risposta.class, writer);
            
        } catch (SQLException ex) {
            Logger.getLogger(ContattiSQL.class.getName()).log(Level.SEVERE, null, ex);
        }
                   
    }
protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String contattoId = request.getParameter("ContattoId");
            BufferedReader reader = request.getReader();
            Gson gson = new Gson();
            ContattoViewModel contatto = (ContattoViewModel) gson.fromJson(reader, ContattoViewModel.class); //in questo modo si legge lo stream in ingresso e restituisce ContattoViewModel 
            try {
                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver").newInstance();
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(ContattiSQL.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InstantiationException ex) {
                Logger.getLogger(ContattiSQL.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalAccessException ex) {
                Logger.getLogger(ContattiSQL.class.getName()).log(Level.SEVERE, null, ex);
            }

            //apertura DB
            Connection con = 
                    DriverManager
                            .getConnection(
                                    "jdbc:"
                                            +"sqlserver://G-FONTANEL\\MYSQLSERVER;"
                                            +"databaseName=ITS2014;"
                                            +"user=ITS2014;password=ITS2014");
            Statement stmt = con.createStatement();
            String sql = "UPDATE dbo.Contatti SET " +
                        "cognome = '" + contatto.cognome + "'" + 
                        ", nome = '" + contatto.nome + "'" + 
                        ", cellulare = '"+ contatto.cellulare + "'" + 
                        ", email = '" + contatto.email + "'" 
                        + " WHERE ContattoId = " + contattoId;
            stmt.execute(sql); //esegue la query 
            JsonWriter writer = new JsonWriter(response.getWriter());
            gson.toJson(Risposta.OK, Risposta.class, writer); //
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(ContattiSQL.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String contattoId = request.getParameter("ContattoId");
            Gson gson = new Gson();
            //apertura DB
            Connection con = 
                    DriverManager
                            .getConnection(
                                    "jdbc:"
                                            +"sqlserver://G-FONTANEL\\MYSQLSERVER;"
                                            +"databaseName=ITS2014;"
                                            +"user=ITS2014;password=ITS2014");
            Statement stmt = con.createStatement();
            String sql = "DELETE FROM dbo.Contatti "
                        + "WHERE ContattoId = " + contattoId;
            stmt.execute(sql); //esegue la query 
            JsonWriter writer = new JsonWriter(response.getWriter());
            gson.toJson(Risposta.OK, Risposta.class, writer); //
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(ContattiSQL.class.getName()).log(Level.SEVERE, null, ex);
        }
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
