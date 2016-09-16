<%-- 
    Document   : gestioneOrdini
    Created on : 14-ago-2016, 17.59.36
    Author     : Lovrenco
--%>

<%@page import="model.Ordine"%>
<%@page import="java.util.ArrayList"%>
<%@page import="model.Utente"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:useBean id="UtenteBean" class="model.Utente" scope="session"/>
<jsp:useBean id="OrdineBean" class="model.Ordine" scope="session"/>
<%if (session.getAttribute("utente") != null) { UtenteBean = (Utente) session.getAttribute("utente");   }%>
<%! ArrayList<Ordine> elencoOrdini = new ArrayList<>(); boolean feedbackAcquista;%>
<%elencoOrdini = (ArrayList<Ordine>) session.getAttribute("elenco_ordini");
feedbackAcquista = (boolean) session.getAttribute("feedbackAcquista");%>
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
            <%if(feedbackAcquista==true){%>
            <h3>Prenotazione effettuata</h3>
            <%}%>
            <div id="tabellaOrdini">
            <%=Ordine.ordiniToHTMLForUser(elencoOrdini, feedbackAcquista)%>
            <div>
                <div class="hide fixed" id="divPizzeOrdine">
                </div>    
            <%session.setAttribute("feedbackAcquista", false);%>
            
            <br>
            <jsp:include page="footer.jsp" flush="true"/>
        </main>   
    </body>
</html>
