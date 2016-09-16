<%-- 
    Document   : gestioneOrdiniAdmin
    Created on : 22-ago-2016, 9.17.22
    Author     : Lovrenco
--%>

<%@page import="model.Utente"%>
<%@page import="model.Ordine"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%! ArrayList<Ordine> elencoOrdiniAdmin = new ArrayList<>();
    ArrayList<Utente> elencoUsernames = new ArrayList<>();%>
<%elencoOrdiniAdmin = (ArrayList<Ordine>) session.getAttribute("elenco_ordini_admin");
    elencoUsernames = (ArrayList<Utente>) session.getAttribute("elenco_usernames");%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Pizza Click</title>
        <link type="text/css" rel="stylesheet" href="css/style.css"/>
        <link rel="stylesheet" href="css/fontawesome-stars.css">
        <%--<link rel="stylesheet" href="css/font-awesome.min.css">Probabilmente ci odia--%>
        <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/font-awesome/latest/css/font-awesome.min.css">
        <script src="js/jquery.1.12.4.min.js"></script>
        <script src="js/jquery.barrating.min.js"></script>
        <script src="js/ajax.js" type="text/javascript"></script>
        <script src="js/javascript.js" type="text/javascript"></script>

    </head>
    <body>
        <main>
        <jsp:include page="header.jsp" flush="true"/>
        <%=Ordine.ordiniToHTMLForAdmin(elencoOrdiniAdmin, elencoUsernames)%>
        
        <div class="hide fixed" id="divPizzeOrdine">

        </div>   
        <br>
        <jsp:include page="footer.jsp" flush="true"/>
        </main>
    </body>
</html>
