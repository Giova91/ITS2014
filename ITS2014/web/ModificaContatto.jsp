<%-- 
    Document   : ModificaContatto
    Created on : 17-mar-2014, 15.41.08
    Author     : Giovanni
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Modifica Contatto</h1>
        <form>
            <input type="hidden" id="contatto_id" value="<%= request.getParameter("ContattoId")%>"/>
            <fieldset>
                <label for="cognome">Cognome</label>
                <input type="text" id="cognome"/>
                <label for="nome">Nome</label>
                <input type="text" id="nome"/>
                <label for="cellulare">Cellulare</label>
                <input type="text" id="cellulare"/>
                <label for="email">Email</label>
                <input type="text" id="email"/>
            </fieldset>
            <a href="#" id="invia">Aggiorna</a> 
        </form>
        <script src='Scripts/jquery-1.11.0.js'></script>
        <script src='Scripts/modifica-contatto.js'></script>
    </body>
</html>
