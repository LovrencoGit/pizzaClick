package model;

import java.util.ArrayList;
import java.util.Locale;

public class Menu {

    private ArrayList<Pizza> elencoPizze;

    public Menu() {
        elencoPizze = new ArrayList<>();
    }

    public Menu(ArrayList<Pizza> elencoPizze) {
        this.elencoPizze = elencoPizze;
    }

    public ArrayList<Pizza> getElencoPizze() {
        return elencoPizze;
    }

    public void setElencoPizze(ArrayList<Pizza> elencoPizze) {
        this.elencoPizze = elencoPizze;
    }

    /**
     * *********************************************************
     * @return html della tabella del menu delle pizze
     */
    public String menuToTableHTML() {
        String menuToTableHTML = "<br><table class='tab'>";
        menuToTableHTML += "<thead><th>PIZZE</th>"
                + "<th>INGREDIENTI</th><th>PREZZO</th></thead>";

        for (Pizza item : elencoPizze) {
            menuToTableHTML += "<tr>";
            menuToTableHTML += "<td class='nomiPizze padding'>" + item.getNomePizza() + "</td>";
            menuToTableHTML += "<td class='padding'>" + item.getIngredienti() + "</td>";
            menuToTableHTML += "<td class='padding'>" + String.format(Locale.US, "%1$.2f", item.getPrezzoPizza()) + " €</td>";
            menuToTableHTML += "<td class='padding'><button onclick='RichiestaAdd(" + item.getIdPizza() + ")' id='rcorners1'>+</button></td>";
            menuToTableHTML += "</tr>";
        }
        menuToTableHTML += "<table>";
        return menuToTableHTML;
    }


    public String crudToTableHTML() {
        String crudToTableHTML = "<br><table class='tab'>";
        crudToTableHTML += "<thead><th>PIZZE</th>"
                + "<th>INGREDIENTI</th><th>PREZZO</th><th>DISPONIBILE</th></thead>";

        for (Pizza item : elencoPizze) {
            crudToTableHTML += "<tr>";
            
            crudToTableHTML += "<td><label class='nomiPizze' id='labelAutoCompleteNome" + item.getIdPizza() + "'>" + item.getNomePizza() + "</label></td>";
            
            crudToTableHTML += "<td><label id='labelAutoCompleteIngredienti" + item.getIdPizza() + "'>" + item.getIngredienti() + "</label></td>";
            
            String prezzoToPrint = String.format(Locale.US, "%1$.2f", item.getPrezzoPizza());
            crudToTableHTML += "<td><label id='labelAutoCompletePrezzo" + item.getIdPizza() + "'>" + prezzoToPrint + " €</label></td>";
           
            String disponibile = (item.isDisponibile() == true) ? "Si" : "No";
            crudToTableHTML += "<td><label id='labelAutoCompleteDisponibile" + item.getIdPizza() + "'>" + disponibile + "</label></td>";
            

            crudToTableHTML += "<td><img src='img/edit_70x70.png' width='35px' height='35px' name='" + item.getIdPizza() + "' class='apriEdit' onclick='RichiestaEdit(" + item.getIdPizza() + ")'/></td>";
            crudToTableHTML += "<td><a href='Dispatcher?src=desktop&cmd=deleteMenu&idPizza=" + item.getIdPizza() + "'><img src='img/delete_70x70.png' width='35px' height='35px'/></td>";
            crudToTableHTML += "</tr>";
        }
        crudToTableHTML += "<table>";
        return crudToTableHTML;
    }

   

}
