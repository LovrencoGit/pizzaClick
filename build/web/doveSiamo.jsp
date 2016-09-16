<%-- 
    Document   : doveSiamo
    Created on : 24-lug-2016, 11.15.44
    Author     : Lovrenco
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Pizza Click</title>
        <link rel="stylesheet" href="css/style.css" type="text/css"/>
        <link rel="stylesheet" href="css/style_map.css" type="text/css"/>
        <script src="js/maps.js" type="text/javascript"></script>
        <script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyC1xlKvYhX_0Q38o756U4sTPp3e_kPR5n4&callback=initMap"
        async defer></script>
    </head>
    <body>
        <main>
            <jsp:include page="header.jsp" flush="true"/>
            <h2>Dove Siamo</h2>
            <div id="map"></div>
            <br>
            <jsp:include page="footer.jsp" flush="true"/>
        </main>
    </body>

</html>
