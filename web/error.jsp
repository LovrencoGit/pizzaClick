<%-- 
    Document   : error
    Created on : 20-lug-2016, 10.44.32
    Author     : Lovrenco
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link type="text/css" rel="stylesheet" href="css/style.css"/>
    </head>
    <body>
        <main>
        <jsp:include page="header.jsp" flush="true"/>
        <h1>ERRORE!</h1>
        <h3><%=session.getAttribute("msgerrorjsp")%></h3>
        <jsp:include page="footer.jsp" flush="true"/>
        </main>
    </body>
</html>
