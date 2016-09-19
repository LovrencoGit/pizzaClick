//Overlay aggiungi pizza al menu
$(document).ready(function () {
    $(".apriAdd").click(
            function () {
                $('#overlay').fadeIn('fast');
                $('#boxAdd').fadeIn('slow');
            });
    $("#btnAddPizza").click(function(){
        console.log("ADD");
        ControlloInput('frmAdd');
    });
    $(".chiudiAdd").click(
            function () {
                $('#overlay').fadeOut('fast');
                $('#boxAdd').hide();
            });

    //chiusura emergenza 
    $("#overlay").click(
            function () {
                $(this).fadeOut('fast');
                $('#boxAdd').hide();
            });
});
//Overlay modifica menu
$(document).ready(function () {
    $(".apriEdit").click(
            function () {
                $('#overlay').fadeIn('fast');
                $('#boxEdit').fadeIn('slow');
            });
    $("#btnEditPizza").click(function(){
        console.log("EDIT");
        ControlloInput('frmEdit');
    });

    $(".chiudiEdit").click(
            function () {
                $('#overlay').fadeOut('fast');
                $('#boxEdit').hide();
            });

    //chiusura emergenza 
    $("#overlay").click(
            function () {
                $(this).fadeOut('fast');
                $('#boxEdit').hide();
            });
});

//Overlay acquista
$(document).ready(function Acquista() {
    $(".apriConfermaCarrello").click(
            function () {
                $('#overlayCarrello').fadeIn('fast');
                $('#boxConfermaCarrello').fadeIn('slow');
            });

    $(".chiudiConfermaCarrello").click(
            function () {
                $('#overlayCarrello').fadeOut('fast');
                $('#boxConfermaCarrello').hide();
            });

    //chiusura emergenza 
    $("#overlayCarrello").click(
            function () {
                $(this).fadeOut('fast');
                $('#boxConfermaCarrello').hide();
            });
});
