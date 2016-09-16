function carousel() {
    var i;
    var x = document.getElementsByClassName("mySlides");//numero di immagini presenti
    for (i = 0; i < x.length; i++) {
        x[i].style.display = "none";
    }
    myIndex++;
    if (myIndex > x.length) { //Caso in cui myIndex sia maggiore del numero di immagini presenti
        myIndex = 1
    }
    x[myIndex - 1].style.display = "block";//immagine da far vedere
    setTimeout(carousel, 3500); // Cambia l'immagine ogni 5 secondi
}
        