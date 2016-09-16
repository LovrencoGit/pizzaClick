<%-- 
    Document   : visualizzaProfilo
    Created on : 22-ago-2016, 12.02.00
    Author     : Lovrenco
--%>

<%@page import="model.Utente"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%! int nOrdini;
    Utente u;       %>
<%  nOrdini =(int) session.getAttribute("nOrdini");
    u= (Utente) session.getAttribute("utente");     %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Pizza Click</title>
        <link type="text/css" rel="stylesheet" href="css/style.css"/>
    </head>
    <body>
        <main>
        <jsp:include page="header.jsp" flush="true"/>
        <%=u.printDatiUtente(u, nOrdini)%>
        <jsp:include page="footer.jsp" flush="true"/>
        </main>
    </body>
</html>
