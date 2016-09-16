function mostra() {
    document.getElementById("tendina").classList.toggle("show");

}
function getCrud(ruolo) {

    if (ruolo === "A") {
        document.getElementById("gestisciOrdini").style.setProperty("display", "block");
        document.getElementById("modPassword").style.setProperty("display", "block");
        document.getElementById("crud").style.setProperty("display", "block");

    } else if (ruolo === "U") {
        document.getElementById("gestisciOrdini").style.setProperty("display", "block");
        document.getElementById("modPassword").style.setProperty("display", "block");
    } else {

    }
}
function RichiestaEdit(id) {

    var nomePizza = document.getElementById("labelAutoCompleteNome" + id).innerHTML;
    var ingredienti = document.getElementById("labelAutoCompleteIngredienti" + id).innerHTML;
    var prezzo = document.getElementById("labelAutoCompletePrezzo" + id).innerHTML;
    var disponibile = document.getElementById("labelAutoCompleteDisponibile" + id).innerHTML;
    //alert(prezzo); //5.00 €
    if(prezzo.length === 6){    //e.cc
        prezzo = prezzo[0] + prezzo[1] + prezzo[2] + prezzo[3];
    }else{      //ee.cc
        prezzo = prezzo[0] + prezzo[1] + prezzo[2] + prezzo[3] + prezzo[4];
    }

    document.getElementById("idPizza").value = id;
    document.getElementById("nomeEdit").value = nomePizza;
    document.getElementById("ingredientiEdit").value = ingredienti;
    document.getElementById("txtPrezzoEdit").value = prezzo;

    if (disponibile === "Si") {
        document.getElementById("disponibileEditTrue").checked = "checked";
    } else {
        document.getElementById("disponibileEditFalse").checked = "checked";
    }
}
function RichiestaAcquista(username, indirizzo) {

    data = new Date();

    var yyyy = data.getFullYear();
    var mm = (data.getMonth() + 1);//I mesi vanno da 0 a 11 -.-"
    var gg = data.getUTCDate();
    if (mm < 10) {
        var date = yyyy + '-0' + mm + '-' + gg;
    } else {
        var date = yyyy + '-' + mm + '-' + gg;
    }


    var hh = data.getHours();
    hh = (hh+1)%24;//setto un'ora in piu di quella corrente perche l'annullamento è entro un ora dalla consegna
    var min = data.getMinutes();
    min = (min+1)%60;
    if (min <= 9) {
        min = "0" + min;
    }
    var time = hh + ':' + min;

    var html = "";
    if (username === null || username === undefined || username === "") {
        html += "<form action='Dispatcher?src=desktop&cmd=login' method='POST' name='frmLogin'>"
                + "<div id='boxRiepilogoLogin'>"
                + "<h1>&Egrave; necessario accedere al tuo profilo</h1><hr>"
                + "<p><h3>Username:</h3> <input type='text' name='txtUsername' id='txtUsername' class='txtRiepilogo' value='' required/></p>"
                + "<p><h3>Password:</h3> <input type='password' name='txtPassword' id='txtPassword' class='txtRiepilogo' value='' required/></p>"
                + "<p><input id='btnAccedi' class='buttonRiepilogo' type='submit' value='Accedi' name='btnAccedi'/></p>"
                + "</div>"
                + "</form>";
    } else {
        html += "<h2 class='marginLeft'>Dati Fatturazione</h2>";
        html += "<form action='Dispatcher?src=desktop&cmd=acquista' method='POST' onsubmit='return ControlloOraOrdine()' name='frmAcquista'>";
        html += "<div class='divCampiInfo'><p class='campiInfo'>Indirizzo:</p> <p class='campiInfo'><input type='text' name='txtIndirizzo' value='" + indirizzo + "' required/></p></div>"
                + "<div class='divCampiInfo'><p class='campiInfo'>Data e ora:</p> <p class='campiInfo'> <input type='date' id='txtData' name='txtData' value='" + date + "' min='" + date + "' max='2016-12-31'/><input id='txtOrario' type='time' name='txtOrario' value='" + time + "'/><p></div><br><br>";



        for (var i = 0; i < document.getElementsByClassName("pNascosto").length; i++) {
            if (i === 0) {
                html += "<div class='clearBoth boxRiepilogoCarrello'>";
            }
            if (i % 5 === 0 && i !== 0) {
                html += "</div><div class='boxRiepilogoCarrello'>";
            }
            html += "<h4>" + document.getElementsByClassName("pNascosto")[i].innerHTML + "</h4>";
        }
        html += "</div>";

        html += "<div id='containerBtnConfermaOrdine' class='marginLeft'><input id='btnConfermaOrdine' class='buttonRiepilogo' type='submit' value='Conferma Ordine' name='btnConfermaOrdine'/></div></form>";
    }
    document.getElementById("testoConfermaCarrello").innerHTML = html;
}



function ControlloFormatoPrezzo(form) {
    
    console.log(modulo);
    var modulo = document.getElementsByName(""+form)[0];
    var prezzo='';
    if (form === "frmEdit") {
        prezzo = document.getElementById("txtPrezzoEdit").value;
    } else if (form === "frmAdd") {
        prezzo = document.getElementById("txtPrezzoAdd").value;
    } else {
        alert("C'è qualquadra che non cosa!");
    }
    
    //var virgola = prezzo.indexOf(",");

    var regExp = new RegExp("^[0-9]{1,2}\\.[0-9]{2}$", 'g');
    var matchregex = regExp.test(prezzo);
     console.log(matchregex);
    if (matchregex == false) {
        console.log("["+prezzo+"]");
        window.alert("correggere formato PREZZO\n   -Inserire 3 o 4 cifre (es. 6.50 , 10.00) \n   -Usare come separatore '.' (punto) \nModulo non spedito");
        $('#txtPrezzoEdit').focus();
        return false;
    }else{
        modulo.submit();
        return true;
    }
}



function ControlloOraOrdine() {
    var orarioOrdine = document.getElementById("txtOrario").value;
    var dataOrdine = document.getElementById("txtData").value;
    data = new Date();
    var yyyy = data.getFullYear();
    var mm = (data.getMonth() + 1);//I mesi vanno da 0 a 11 -.-"
    if (mm < 10) {
        mm = '0' + mm;
    }
    var gg = data.getUTCDate();
    if (gg < 10) {
        gg = '0' + gg;
    }
    var hh = data.getHours();
    var min = data.getMinutes();
    var hhHint = (hh+1)%24;
    var minHint = (min+1)%60;
    if (hhHint < 10) {
        hhHint = '0' + hhHint;
    }
    if (minHint < 10) {
        minHint = '0' + minHint;
    }

    var oraOrdine = orarioOrdine[0] + "" + orarioOrdine[1];
    var minOrdine = orarioOrdine[3] + "" + orarioOrdine[4];
    var giornoOrdine = dataOrdine[8] + "" + dataOrdine[9];
    var meseOrdine = dataOrdine[5] + "" + dataOrdine[6];

    //alert(giornoOrdine +">"+gg);

    if (meseOrdine > mm) {//CASO OTTIMO
        clearCarrelloDiv();
        document.frmAcquista.submit();
        return true;
    } else if (meseOrdine === mm) {
        if (giornoOrdine > gg) {//CASO OTTIMO
            clearCarrelloDiv();
            document.frmAcquista.submit();
            return true;
        } else if (giornoOrdine == gg) {
            if (oraOrdine > hh) {
                if ((oraOrdine - hh) === 1 && minOrdine<=min) {
                    
                    alert("Operazione non possibile! Ora errata \n - necessaria un'ora di preavviso per la consegna \n - primo orario disponibile ->  "+hhHint+":"+minHint);
                    document.frmAcquista.txtOrario.focus;
                    return false;
                } else {
                    clearCarrelloDiv();
                    document.frmAcquista.submit();
                    return true;
                }
            } else if (oraOrdine === hh) {
                
                alert("Operazione non possibile! Ora errata \n - necessaria un'ora di preavviso per la consegna \n - primo orario disponibile ->  "+hhHint+":"+minHint);
                document.frmAcquista.txtOrario.focus;
                return false;
            } else {
                
                alert("Operazione non possibile! Ora errata \n - necessaria un'ora di preavviso per la consegna \n - primo orario disponibile ->  "+hhHint+":"+minHint);
                document.frmAcquista.txtOrario.focus;
                return false;
            }
        } else {//CASO PESSIMO
            
            alert("Operazione non possibile! Ora errata \n - necessaria un'ora di preavviso per la consegna \n - primo orario disponibile ->  "+hhHint+":"+minHint);
            document.frmAcquista.txtOrario.focus;
            return false;
        }
    } else {//CASO PESSIMO
        
        alert("Operazione non possibile! Ora errata \n - necessaria un'ora di preavviso per la consegna \n - primo orario disponibile ->  "+hhHint+":"+minHint);
        document.frmAcquista.txtOrario.focus;
        return false;
    }

    clearCarrelloDiv();
    

}

function clearCarrelloDiv(){
    //alert("clearCArrelloDiv");
    carrelloDiv = document.getElementById("carrelloOrdine");
    //alert("#####" + carrelloDiv.innerHTML);
    carrelloDiv.innerHTML = " ";
    //alert("#####" + carrelloDiv.innerHTML);
}
//
//$(function () {
//    $('.rating').barrating({
//        theme: 'fontawesome-stars'
//    });
//});
//
//$(function () {
//    $('.mostraRating').barrating({
//        theme: 'fontawesome-stars'
//    });
//    $('.mostraRating').barrating('readonly', true);
//});

function RichiestaAnnullaOrdine(idOrdine) {
    var conferma = confirm("Sei sicuro di voler annullare l'ordine?");
    if (conferma === true) {
        window.location.href = "Dispatcher?src=desktop&cmd=annullaOrdine&idOrdine=" + idOrdine;
    }
}
function allMostra() {
    var rowOrder = document.getElementsByClassName("rowOrder");
    for (var i = 0; i < rowOrder.length; i++) {
        rowOrder[i].style.setProperty("background-color", "white");
    }
    $("#divPizzeOrdine").hide();
}
function RichiestaAvvenutaConsegna(idOrdine) {
    var conferma = confirm("Hai davvero ricevuto quest'ordine?");
    if (conferma === true) {
        window.location.href = "Dispatcher?src=desktop&cmd=avvenutaConsegna&idOrdine=" + idOrdine;
        document.getElementById("btnAnnullaOrdine").style.setProperty("display", "none");
    }

}


// Close the dropdown menu if the user clicks outside of it
//window.onclick = function (eent) {
//    if (!event.target.matches('.btnTendina')) {
//        var dropdowns = document.getElementsByClassName("tendina");
//        var i;
//        for (i = 0; i < dropdowns.length; i++) {
//            var openDropdown = dropdowns[i];
//            if (openDropdown.classList.contains('show')) {
//                openDropdown.classList.remove('show');
//            }
//        }
//    }
//};

//
//$(document).ready(function() {
//    console.log('siamo dentro');
//    var formEditPizza = document.getElementById('btnEditPizza');
//    if (formEditPizza === null) {
//        console.log("la mamma di tomcat è una vacca");
//    }
//    console.log(formEditPizza);
//    //formEditPizza.addEventLister('click', function() {console.log('tua madre!!!');});
//});
//
//