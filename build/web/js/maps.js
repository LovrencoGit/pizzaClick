function initMap() {
    var map;
    var myLatLng = {lat: 45.09864, lng: 7.65369};
    map = new google.maps.Map(document.getElementById("map"), {
        center: {lat: 45.09864, lng: 7.65369},
        zoom: 16
    });
    var marker = new google.maps.Marker({
        map: map,
        position: myLatLng,
        title: 'Siamo qui'
    });
}