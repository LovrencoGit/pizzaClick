package model;

public class PizzaPrenotata {
    private int idPizzaPrenotata;
    private int idOrdine;
    private String nomePizzaPrenotata;
    private double prezzoPizzaPrenotata;

    public PizzaPrenotata() {
        idPizzaPrenotata= Integer.MAX_VALUE;
        idOrdine= Integer.MAX_VALUE;
        nomePizzaPrenotata="";
        prezzoPizzaPrenotata=0.0;
    }

    public PizzaPrenotata(int idPizzaPrenotata, int idOrdine, String nomePizzaPrenotata, double prezzoPizzaPrenotata) {
        this.idPizzaPrenotata = idPizzaPrenotata;
        this.idOrdine = idOrdine;
        this.nomePizzaPrenotata = nomePizzaPrenotata;
        this.prezzoPizzaPrenotata = prezzoPizzaPrenotata;
    }

    public int getIdPizzaPrenotata() {
        return idPizzaPrenotata;
    }

    public void setIdPizzaPrenotata(int idPizzaPrenotata) {
        this.idPizzaPrenotata = idPizzaPrenotata;
    }

    public int getIdOrdine() {
        return idOrdine;
    }

    public void setIdOrdine(int idOrdine) {
        this.idOrdine = idOrdine;
    }

    public String getNomePizzaPrenotata() {
        return nomePizzaPrenotata;
    }

    public void setNomePizzaPrenotata(String nomePizzaPrenotata) {
        this.nomePizzaPrenotata = nomePizzaPrenotata;
    }

    public double getPrezzoPizzaPrenotata() {
        return prezzoPizzaPrenotata;
    }

    public void setPrezzoPizzaPrenotata(double prezzoPizzaPrenotata) {
        this.prezzoPizzaPrenotata = prezzoPizzaPrenotata;
    }
    

}
