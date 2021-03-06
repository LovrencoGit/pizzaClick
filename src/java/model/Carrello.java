package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map.Entry;
import utilities.ArrayListPizzaDisplayer;

public class Carrello implements Serializable {

    private ArrayList<Pizza> elencoPizze;
    private double prezzoTotale;

    public Carrello() {
        elencoPizze = new ArrayList<>();
        prezzoTotale = 0.0;
    }

    public Carrello(ArrayList<Pizza> carrello, double prezzoTotale) {
        this.elencoPizze = carrello;
        this.prezzoTotale = prezzoTotale;

    }

    public ArrayList<Pizza> getElencoPizze() {
        return elencoPizze;
    }

    public void setElencoPizze(ArrayList<Pizza> carrello) {
        this.elencoPizze = carrello;
    }

    public double getPrezzoTotale() {
        return prezzoTotale;
    }

    public void setPrezzoTotale(double prezzoTotale) {
        this.prezzoTotale = prezzoTotale;
    }
  

    /**
     * ******************************************************************
     */
    public boolean addCarrello(Pizza p) {
        if (p == null) {
            return false;
        }
        prezzoTotale += p.getPrezzoPizza();
        return elencoPizze.add(p);
    }

    public boolean removeCarrello(Pizza p) {
        for (Pizza i : elencoPizze) {
            if (i.getIdPizza() == p.getIdPizza()) {
                prezzoTotale -= p.getPrezzoPizza();
                return elencoPizze.remove(i);
            }
        }
        return false;
    }

    public void clear(){
        this.elencoPizze.clear();
        this.prezzoTotale = 0.0;
    }
    
    public String printCarrello() {
        String html = "";
        HashMap<Integer, Integer> map = ArrayListPizzaDisplayer.getElencoPizzeToHashMap(elencoPizze);
        for (Entry<Integer, Integer> entry : map.entrySet()) {
            Integer id = entry.getKey();
            Pizza item = ArrayListPizzaDisplayer.getPizzaByIdPizza(elencoPizze, id);
            Integer qty = entry.getValue();
            html += "<p><button id='rcorners2' "
                    + "onclick='RichiestaRemove(" + item.getIdPizza() + ", " + this.getPrezzoTotale() + ", " + item.getPrezzoPizza() + ")'>X</button>"
                    + item.getNomePizza() + "     " + String.format(Locale.US, "%1$.2f", item.getPrezzoPizza()) + " € (x " + qty + ")"
                    + "<p class='pNascosto'>" + item.getNomePizza() + "      " + String.format(Locale.US, "%1$.2f", item.getPrezzoPizza()) + " € (x " + qty + ")</p></p>";
        }
           
        int numPizze = elencoPizze.size();
        if (this.getPrezzoTotale() == 0.0 || numPizze==0) {
            html += "";
        } else {
            String size = (numPizze==1 ? "1 pizza" : numPizze+" pizze");
            html += "<p>TOTALE " + String.format(Locale.US, "%1$.2f", this.getPrezzoTotale()) + " €  (" + size + ")</p>";
        }
        
        return html;
    }
    
    

}
