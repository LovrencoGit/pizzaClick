<%-- 
    Document   : chiSiamo
    Created on : 27-lug-2016, 14.22.55
    Author     : Lovrenco
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="css/style.css" type="text/css"/>
        <title>Pizza Click</title>
    </head>
    <body>
        <main>
            <jsp:include page="header.jsp" flush="true"/>            
            <h2>Chi Siamo</h2>
            <div class="fotoTurin">
                <img src="img/chiSiamo.jpg" width="820" height="400" alt="chiSiamo"/>

            </div>
            <div>
                La pizza è un simbolo dell’arte culinaria italiana, un alimento sano e completo, viene consigliata dai nutrizionisti e da questi considerata parte della dieta mediterranea.<br>
                La pizza contiene un elevato contenuto di protidi, ferro e vitamine B1 e PP, non ingrassa ed è più digeribile di altri cibi e grazie ai carboidrati della pasta, alle proteine del formaggio e le vitamine del pomodoro può essere considerata come piatto unico tale da soddisfare il fabbisogno giornaliero del corpo umano. <br>
                La pizza deve essere cotta in forno a legna a circa 485 ° C. I tempi di cottura non devono essere eccessivi, non c’è una regola precisa, ma solo l’esperienza e la vista indicheranno il momento giusto per sfornare, in modo tale che, né la pasta né gli ingredienti devono presentare bruciature.<br>
                Se invece del forno a legna si usa il forno elettrico per cuocere una pizza occorrono circa 20-30 minuti alla temperatura di 250°.
            </div>
            <br>
            <jsp:include page="footer.jsp" flush="true"/>
        </main>

    </body>
</html>
