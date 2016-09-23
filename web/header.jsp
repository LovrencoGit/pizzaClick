<%-- 
    Document   : header
    Created on : 18-lug-2016, 15.34.04
    Author     : Lovrenco
--%>

<%@page import="model.Utente"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<jsp:useBean id="UtenteBean" class="model.Utente" scope="session"/>
<%!String feedbackLogin;%>
<%
    UtenteBean = (Utente) session.getAttribute("utente");
    feedbackLogin = (String) session.getAttribute("messaggio");
%>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link type="text/css" rel="stylesheet" href="css/style.css"/>
        <script src="js/javascript.js" type="text/javascript"></script>
        <script src="js/ajax.js" type="text/javascript"></script>
        <title>Pizza Click</title>
    </head>
    <body>
        <header>
            <a href="Dispatcher?src=desktop&cmd=index"><img src="img/logo_pizza.png" width="600" height="120" alt="logo_pizza"/></a>

            <div id="box" class="floatRight">
                <%if (UtenteBean.getRuolo().equals("")) {%>
                <form action="Dispatcher?src=desktop&cmd=login" method="POST" name="frmLogin">
                    <table>
                        <thead>
                        <th>Accedi al tuo profilo</th>
                        </thead>
                        <tr>
                            <td><input type="text" name="txtUsername" id="txtUsername" value="" required placeholder="Username"/><br></td>
                        </tr>
                        <tr>
                            <td><input type="password" name="txtPassword" id="txtPassword" value="" required placeholder="Password"/><br></td>
                        </tr>    
                        <tr>
                            <td><input id="btnAccedi" class="buttonGenerico floatRight" type="submit" value="Accedi" name="btnAccedi"/></td>
                        </tr>
                        <tr>
                            <td><%=feedbackLogin%></td>
                        </tr>
                    </table>
            </div>
            <div id="box2" class="floatRight">
                <%} else if (UtenteBean.getRuolo().equals("A") || UtenteBean.getRuolo().equals("U")) {%>
                <table>
                    <tr>
                        <td>Username: </td>
                        <td><%=UtenteBean.getUsername()%></td>
                    </tr>
                    <tr>
                        <td>Ruolo: </td>
                        <td><%=UtenteBean.getRuolo().equals("A") ? "admin" : "cliente"%></td>
                    </tr>
                </table>
            </div>    
            <a href="Dispatcher?src=desktop&cmd=logout"><input id="btnLogout" class="floatRight buttonLogin" type="submit" value="Logout" name="btnLogout" /></a>
                <%}%>
        </form>



    </header>
    <nav>
        <ul>
            <li><a href="Dispatcher?src=desktop&cmd=index">Home</a></li>

            <li class="dropdown">
                <a href="Dispatcher?src=desktop&cmd=chiSiamo" class="btnTendina">Chi Siamo</a>
            </li>

            <li class="dropdown">
                <a href="Dispatcher?src=desktop&cmd=doveSiamo" class="btnTendina">Dove Siamo</a>
            </li>
            <li class="dropdown">
                <a href="Dispatcher?src=desktop&cmd=gallery" class="btnTendina">Gallery</a>
            </li>
            <%if (UtenteBean.getRuolo().equals("A")) {%>
            <li class="dropdown">
                <a href="javascript:void(0)" class="btnTendina" onclick="mostra()" id="btnTendina1">Profilo</a>
                <div class="tendina" id="tendina">
                    <a href="Dispatcher?src=desktop&cmd=visualizzaOrdini">I miei ordini</a>
                    <a href="Dispatcher?src=desktop&cmd=visualizzaProfilo">Visualizza profilo</a>
                    <a href="Dispatcher?src=desktop&cmd=gestisciOrdiniAdmin"><i>Gestione ordini</i></a>
                    <a href="Dispatcher?src=desktop&cmd=crud"><i>Gestione menu</i></a>
                </div>
            </li>
            <%} else if (UtenteBean.getRuolo().equals("U")) {%>
            <li class="dropdown">
                <a href="javascript:void(0)" class="btnTendina" onclick="mostra()" id="btnTendina1">Profilo</a>
                <div class="tendina" id="tendina">
                    <a href="Dispatcher?src=desktop&cmd=visualizzaOrdini">I miei ordini</a>
                    <a href="Dispatcher?src=desktop&cmd=visualizzaProfilo">Visualizza profilo</a>
                </div>
            </li>
            <%} else {%>
            <%}%>
        </ul>

    </nav>
</body>
</html>
