var XMLHTTP;

//Add pizza al carrello(AJAX)
function RichiestaAdd(id) {
    if (id > 0) {
        var url = "Dispatcher?src=desktop&cmd=addCarrello&pizza=" + id;
        document.getElementById("btnAcquista").style.setProperty("display", "block");
        XMLHTTP = RicavaBrowser(CambioStatoCarrello);
        XMLHTTP.open("GET", url, true);
        XMLHTTP.send(null);
    } else {
        document.getElementById("carrelloOrdine").innerHTML = "";
    }
}
//Remove pizza dal carrello (AJAX)
function RichiestaRemove(id, prezzoTot, prezzoPizza) {
    //passo 'prezzoTot' e 'prezzoPizza' per controllo sparizione bottone aggiungi--> 
    //Se sono uguali vuol dire che stai eliminando l'ultima pizza perciò il bottone deve sparire
    if (id > 0) {
        if (prezzoTot === prezzoPizza) {
            document.getElementById("btnAcquista").style.setProperty("display", "none");
        }
        var url = "Dispatcher?src=desktop&cmd=removeCarrello&pizza=" + id + "&clear=false";
        XMLHTTP = RicavaBrowser(CambioStatoCarrello);
        XMLHTTP.open("GET", url, true);
        XMLHTTP.send(null);
    } else {
        document.getElementById("carrelloOrdine").innerHTML = "";
    }
}

// refresh del carrello (esempio: ritorno sulla index.jsp)
function RichiestaPrintCarrello(sizeCarrello) {
    var divCarrello = document.getElementById("carrelloOrdine");
    var url = "Dispatcher?src=desktop&cmd=printCarrello";
    if (divCarrello.value === "" || divCarrello.value === undefined || divCarrello.value === null) {
        if (sizeCarrello !== 0) {
            document.getElementById("btnAcquista").style.setProperty("display", "block");
        }
        XMLHTTP = RicavaBrowser(CambioStatoCarrello);
        XMLHTTP.open("GET", url, true);
        XMLHTTP.send(null);
    }
}

// invia rating
function RichiestaValutazione(idOrdine) {
    var select = document.getElementById("rating" + idOrdine);
    var optionSelected = select.options[select.selectedIndex].value;
    var url = "Dispatcher?src=desktop&cmd=valutaOrdine&rating=" + optionSelected + "&idOrdine=" + idOrdine;
    XMLHTTP = RicavaBrowser(CambioStatoCarrello);
    XMLHTTP.open("GET", url, true);
    XMLHTTP.send(null);
}

// getPizzePrenotateByIdOrdine
function RichiestaPizzeOrdine(idOrdine) {
    $(".showMostra").show();
    $("divPizzeOrdine").hide();

    var url = "Dispatcher?src=desktop&cmd=pizzeOrdine&idOrdine=" + idOrdine;
    var rowOrder = document.getElementsByClassName("rowOrder");
    for (var i = 0; i < rowOrder.length; i++) {
        rowOrder[i].style.setProperty("background-color", "white");
    }
    document.getElementById("divPizzeOrdine").style.setProperty("display", "block");
    document.getElementById("orderSelected" + idOrdine).style.setProperty("background-color", "yellow");
    
    
    XMLHTTP = RicavaBrowser(CambioStatoDivPizzeOrdine);
    XMLHTTP.open("GET", url, true);
    XMLHTTP.send(null);
}



/***************************************************************************/


function CambioStatoCarrello() {
    if (XMLHTTP.readyState === 4) {
        var R = document.getElementById("carrelloOrdine");
        R.innerHTML = XMLHTTP.responseText;
    }
}
function CambioStatoDivPizzeOrdine() {
    if (XMLHTTP.readyState === 4) {
        var R = document.getElementById("divPizzeOrdine");
        R.innerHTML = XMLHTTP.responseText;
    }
}




function RicavaBrowser(CambiaStato) {
    if (navigator.userAgent.indexOf("MSIE") !== (-1)) {
        var Classe = "Msxml2.XMLHTTP";
        if (navigator.appVersion.indexOf("MSIE 5.5") !== (-1)) {
            Classe = "Microsoft.XMLHTTP";
        }
        try {
            OggettoXMLHTTP = new ActiveXObject(Classe);
            OggettoXMLHTTP.onreadystatechange = CambiaStato;
            return OggettoXMLHTTP;
        } catch (e) {
            alert("Errore: l'ActiveX non verrà eseguito!");
        }
    } else if (navigator.userAgent.indexOf("Mozilla") !== (-1)) {
        OggettoXMLHTTP = new XMLHttpRequest();
        OggettoXMLHTTP.onload = CambiaStato;
        OggettoXMLHTTP.onerror = CambiaStato;
        return OggettoXMLHTTP;
    } else {
        alert("L'esempio non funziona con altri browser!");
    }
}