<%--
    Document   : crud
    Created on : 28-lug-2016, 11.34.39
    Author     : Lovrenco
--%>

<%@page import="model.Pizza"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:useBean id="MenuBean" class="model.Menu" scope="session"/>
<jsp:setProperty name="MenuBean" property="elencoPizze" value='<%=session.getAttribute("elenco_pizze_admin")%>'/>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <%--jQuery--%>
        <script src="js/jquery-1.9.1.min.js" type="text/javascript"></script>
        <%--CSS--%>
        <link type="text/css" rel="stylesheet" href="css/overlay_css.css"/>
        <link type="text/css" rel="stylesheet" href="css/style.css"/>
        <%--Javascript--%>
        <!--script src="js/overlay.js" type="text/javascript" ></script>-->
        <script src="js/ajax.js" type="text/javascript" ></script>
        <script src="js/javascript.js" type="text/javascript"></script>
        
        
        <title>Pizza Click</title>
    </head>
    <body>
        <main>
            <jsp:include page="header.jsp" flush="true"/>
            
            <%=MenuBean.crudToTableHTML()%>
            
            <button id="addPizza" class="apriAdd">Aggiungi pizza</button>
            
            <div class="overlay" id="overlay" style="display:none;"></div><!--Overlay non toccare!-->

            <div id="boxAdd">
                <form method="POST" name="frmAdd" action="Dispatcher?src=desktop&cmd=addMenu" >
                    <div class="testo-box">Nome pizza </div>
                    <div class="testo-box"><input id="nomeAdd" type="text" name="txtNomePizza" value="" class="inputBox" required/></div><br>
                    <div class="testo-box">Ingredienti</div>
                    <div class="testo-box"><input id="ingredientiAdd" maxlength="100" type="text" id="txtAddIngredienti" name="txtIngredienti" value="" class="inputBox" required/></div><br>
                    <div class="testo-box">Prezzo pizza</div>
                    <div class="testo-box"><input type="text" size="1" id="txtPrezzoAdd" name="txtPrezzoPizza" value="" class="inputBox" required placeholder="5.00"/> €</div><br>
                    <div class="testo-box">Disponibile </div>
                    <div class="testo-box"><input type="radio" name="optDisponibile" value="T" checked />Si
                        <input type="radio" name="optDisponibile" value="F"/>No</div><br>
                    <div class="testo-box"><input id="btnAddPizza" type="button" value="Aggiungi pizza" name="btnAddPizza" class="buttonGenerico" /></div>
                </form>
                <p class="chiudiAdd">X</p>
            </div>

            
            <div id="boxEdit">
                <form method="POST" name="frmEdit" action="Dispatcher?src=desktop&cmd=editMenu" >
                    <input type="text" id="idPizza" name="idPizza" class="hide" />
                    <div class="testo-box">Nome pizza</div>
                    <div class="testo-box"><input id="nomeEdit" type="text" id="nomeEdit" name="txtNomePizza" value="" class="inputBox" required/></div><br>
                    <div class="testo-box">Ingredienti</div>
                    <div class="testo-box"><input id="ingredientiEdit" type="text" id="ingredientiEdit" maxlength="100" name="txtIngredienti" value="" class="inputBox" required/></div><br>
                    <div class="testo-box">Prezzo pizza</div>
                    <div class="testo-box"><input type="text" id="txtPrezzoEdit" size="1" name="txtPrezzoPizza" value="" class="inputBox" required placeholder="5.00"/> €</div><br>
                    <div class="testo-box">Disponibile </div>
                    <div class="testo-box"><input type="radio" id="disponibileEditTrue" name="optDisponibile" value="T" checked />Si
                        <input type="radio" id="disponibileEditFalse" name="optDisponibile" value="F" class="campiOverlay" />No</div><br>
                        <div class="testo-box"><input id="btnEditPizza" type="button" value="Modifica pizza" name="btnEditPizza" class="buttonGenerico"/></div>
                </form>
                <p class="chiudiEdit">X</p>
            </div>

            <br>
            <jsp:include page="footer.jsp" flush="true"/>

        </main>
        <script src="js/overlay.js" type="text/javascript" ></script>
    </body>
</html>
