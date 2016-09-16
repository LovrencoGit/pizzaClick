<%-- 
    Document   : gallery
    Created on : 25-lug-2016, 7.56.06
    Author     : Lovrenco
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <title>Pizza Click</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="css/style.css">
    <script src="js/slideshow.js" type="text/javascript"></script>

    <body>
        <main>
            <jsp:include page="header.jsp" flush="true"/>
            <div id="divSlider">
                <img class="mySlides" src="img/slide_1.jpg"/>
                <img class="mySlides" src="img/slide_2.jpg"/>
                <img class="mySlides" src="img/slide_3.jpg"/>
            </div>
            <script>
                var myIndex = 0;
                carousel();
            </script>
            <jsp:include page="footer.jsp" flush="true"/>
        </main>
    </body>
</html>


