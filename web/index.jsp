<%-- 
    Document   : index
    Created on : 18-lug-2016, 15.48.59
    Author     : Lovrenco
--%>

<%@page import="model.Utente"%>
<%@page import="model.Carrello"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<jsp:useBean id="MenuBean" class="model.Menu" scope="session"/>
<jsp:useBean id="UtenteBean" class="model.Utente" scope="session"/>
<jsp:useBean id="CarrelloBean" class="model.Carrello" scope="session"/>
<jsp:setProperty name="MenuBean" property="elencoPizze" value='<%=session.getAttribute("elenco_pizze_user")%>'/>
<%if(session.getAttribute("carrello")!=null){   CarrelloBean = (Carrello) session.getAttribute("carrello"); }%>
<%if(session.getAttribute("utente")!=null){ UtenteBean = (Utente) session.getAttribute("utente");   }%>
<html>
    <head>
        <title>Pizza Click</title>
        <script src="js/jquery.1.12.4.min.js"></script>
        <link type="text/css" rel="stylesheet" href="css/style.css"/>
        <link type="text/css" rel="stylesheet" href="css/overlay_css.css"/>
        <script type="text/javascript" src="js/ajax.js"></script>
        <script type="text/javascript" src="js/overlay.js"></script>
        <script src="js/javascript.js" type="text/javascript"></script>
    </head>
    <body onload="RichiestaPrintCarrello(<%=CarrelloBean.getElencoPizze().size()%>)">
        <main>
            <jsp:include page="header.jsp" flush="true"/>
            <div id="carrello" class="floatRight">
                <table>
                    <tr>
                        <td><img class="floatRight" src="img/carrello_75x75.png" width="75" height="75" alt="carrello_75x75"/></td>
                    </tr>
                    <tr>
                        <td>
                            <div id="carrelloOrdine">

                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td><input class="buttonGenerico apriConfermaCarrello" id="btnAcquista" type="submit" value="Acquista" name="btnAcquista" 
                                   onclick="RichiestaAcquista('<%=UtenteBean.getUsername()%>', '<%=UtenteBean.getIndirizzo()%>')"/>
                            <a id="labelClear" href="#" onclick="RichiestaClear();"> svuota carrello </a>
                        </td>
                    </tr>
                </table>
            </div>

            <%=MenuBean.menuToTableHTML()%>
            <br>
            <div class="overlayCarrello" id="overlayCarrello" style="display:none;"></div><!--Overlay non toccare!-->
            
            <div id="boxConfermaCarrello">
                <p id="testoConfermaCarrello"></p>
                <p class="chiudiConfermaCarrello">X</p>
            </div>
            
            <jsp:include page="footer.jsp" flush="true"/>
        </main>

    </body>
</html>
