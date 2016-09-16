
package model;

/**
 *
 * @author Lovrenco
 */
public class Pizza {

    private int idPizza;
    private String nomePizza;
    private String ingredienti;
    private double prezzoPizza;
    private boolean disponibile;

    public Pizza() {
        idPizza = Integer.MAX_VALUE;
        nomePizza = "";
        ingredienti = "";
        prezzoPizza = 0.0;
        disponibile = false;
    }

    public Pizza(int idPizza, String nomePizza, String ingredienti, double prezzoPizza, boolean disponibile) {
        this.idPizza = idPizza;
        this.nomePizza = nomePizza;
        this.ingredienti = ingredienti;
        this.prezzoPizza = prezzoPizza;
        this.disponibile = disponibile;
    }

    public int getIdPizza() {
        return idPizza;
    }

    public void setIdPizza(int idPizza) {
        this.idPizza = idPizza;
    }

    public String getNomePizza() {
        return nomePizza;
    }

    public void setNomePizza(String nomePizza) {
        this.nomePizza = nomePizza;
    }

    public String getIngredienti() {
        return ingredienti;
    }

    public void setIngredienti(String ingredienti) {
        this.ingredienti = ingredienti;
    }

    public double getPrezzoPizza() {
        return prezzoPizza;
    }

    public void setPrezzoPizza(double prezzoPizza) {
        this.prezzoPizza = prezzoPizza;
    }

    public boolean isDisponibile() {
        return disponibile;
    }

    public void setDisponibile(boolean disponibile) {
        this.disponibile = disponibile;
    }

}
