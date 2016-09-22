package utilities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import model.*;
import java.util.ArrayList;

public class DBManager {

    private static final String url = "jdbc:mysql://127.0.0.1:3306/pizzaclick";
    private static final String user = "root";
    private static final String pwd = "";

    private static Connection conn;
    private static Statement stm;

    /**
     * ************************************************************************
     */
    /**
     * ********************** public methods **********************************
     */
    /**
     * ************************************************************************
     */
    public static void inserisciNuovaPizza(String nomePizza, String ingredienti, double prezzoPizza, String disponibile) {
        try {
            creazioneConnessioneDB();
        } catch (SQLException exc) {
            System.out.println(exc.getMessage()
                    + "\n***[DBmanager.inserisciNuovaPizza: creazioneConnessioneDB fallita]***");
        }

        System.out.println("Sto per caricare dati in PIZZA");

        try {
            String action = "INSERT INTO PIZZA (nomePizza, ingredienti, prezzoPizza, disponibile)"
                    + "VALUES('" + nomePizza + "', '" + ingredienti + "', " + prezzoPizza + ", '" + disponibile + "')";
            //System.out.println(action);
            stm.executeUpdate(action);
            System.out.println("nuova pizza inserita correttamente");

        } catch (SQLException exc) {
            System.out.println(exc.getMessage()
                    + "\n***[DBmanager.inserisciNuovaPizza: executeUpdate fallita]***");

        } finally {

            try {
                chiusuraConnessioneDB();
            } catch (SQLException exc) {
                System.out.println(exc.getMessage()
                        + "\n***[DBmanager.inserisciNuovaPizza: chiusuraConnessioneDB fallita]***");
            }

        }
    }

    public static boolean modificaPizza(int idPizza, String nomePizzaNew, String ingredientiNew, double prezzoPizzaNew, String disponibileNew){
        try {
            creazioneConnessioneDB();
        } catch (SQLException exc) {
            System.out.println(exc.getMessage()
                    + "\n***[DBmanager.modificaPizza: creazioneConnessioneDB fallita]***");
            return false;
        }

        System.out.println("Sto per modificare dati in PIZZA");

        try {
            
            String action = "UPDATE pizza "
                    + "SET nomePizza = '"+nomePizzaNew+"', ingredienti = '"+ingredientiNew+"', "
                    + "prezzoPizza = "+prezzoPizzaNew+", disponibile = '"+disponibileNew+"' "
                    + "WHERE idPIZZA = "+idPizza+";";
            System.out.println(action);
            stm.execute(action);
            System.out.println("pizza modificata correttamente");

        } catch (SQLException exc) {
            System.out.println(exc.getMessage()
                    + "\n***[DBmanager.modificaPizza: executeUpdate fallita]***");
            return false;

        } finally {

            try {
                chiusuraConnessioneDB();
            } catch (SQLException exc) {
                System.out.println(exc.getMessage()
                        + "\n***[DBmanager.modificaPizza: chiusuraConnessioneDB fallita]***");
                return false;
            }
        }
        return true;
    }
    
    public static void acquista(Carrello c, Utente u, String indirizzo, String datetime) {
        int idOrdine = inserisciOrdine(c, u, indirizzo, datetime);
        if (idOrdine != Integer.MAX_VALUE) {
            for (Pizza item : c.getElencoPizze()) {
                inserisciPizzaPrenotata(item, idOrdine);
            }
        }

    }

    public static ArrayList<Utente> getUsernames() {
        ArrayList<Utente> elencoUsernames = new ArrayList<>();
        try {
            creazioneConnessioneDB();
        } catch (SQLException exc) {
            System.out.println(exc.getMessage()
                    + "\n***[DBmanager.getUsernames: creazioneConnessioneDB fallita]***");
        }

        try {

            String action = "SELECT idUTENTE, username FROM UTENTE;";
            //System.out.println(action);
            ResultSet rs = stm.executeQuery(action);
            while (rs.next()) {
                Utente u = new Utente();
                u.setIdUtente(rs.getInt("idUTENTE"));
                u.setUsername(rs.getString("username"));
                elencoUsernames.add(u);
            }
            System.out.println("Utenti trovati");

        } catch (SQLException exc) {
            System.out.println(exc.getMessage()
                    + "\n***[DBmanager.getUsernames: executeUpdate fallita]***");

        } finally {

            try {
                chiusuraConnessioneDB();
            } catch (SQLException exc) {
                System.out.println(exc.getMessage()
                        + "\n***[DBmanager.getUsernames: chiusuraConnessioneDB fallita]***");
            }

        }
        return elencoUsernames;
    }

    public static int inserisciOrdine(Carrello c, Utente u, String indirizzo, String datetime) {
        try {
            creazioneConnessioneDB();
        } catch (SQLException exc) {
            System.out.println(exc.getMessage()
                    + "\n***[DBmanager.inserisciOrdine: creazioneConnessioneDB fallita]***");
        }

        System.out.println("Sto per caricare dati in ORDINE");
                //2016-09-01 20:01
        try {

            String action = "INSERT INTO ORDINE (idUtente, prezzoTotale, indirizzo, data, quantitaTotale, valutazione, annullato, consegnato)"
                    + "VALUES(" + u.getIdUtente() + ", " + c.getPrezzoTotale() + ", '" + indirizzo + "', '" + datetime + "', " + c.getElencoPizze().size() + ", "
                    + "" + 0 + ", 'F', 'F');";
            System.out.println(action);
            stm.executeUpdate(action);
            System.out.println("nuova prenotazione inserita correttamente");
            String action2 = "SELECT MAX(idOrdine) as maxId FROM ORDINE ;";
            ResultSet rs = stm.executeQuery(action2);
            int id = Integer.MAX_VALUE;
            while (rs.next()) {
                id = rs.getInt("maxId");
            }
            return id;

        } catch (SQLException exc) {
            System.out.println(exc.getMessage()
                    + "\n***[DBmanager.inserisciOrdine: executeUpdate(ORDINE) fallita]***");
            return Integer.MAX_VALUE;

        } finally {
            try {
                chiusuraConnessioneDB();
            } catch (SQLException exc) {
                System.out.println(exc.getMessage()
                        + "\n***[DBmanager.inserisciOrdine: chiusuraConnessioneDB fallita]***");
            }

        }
    }

    public static void inserisciPizzaPrenotata(Pizza p, int idOrdine) {
        try {
            creazioneConnessioneDB();
        } catch (SQLException exc) {
            System.out.println(exc.getMessage()
                    + "\n***[DBmanager.inserisciPizzaPrenotata: creazioneConnessioneDB fallita]***");
        }

        System.out.println("Sto per caricare dati in PIZZAPRENOTATA");

        try {

            String action = "INSERT INTO PIZZAPRENOTATA (idOrdine, nomePizzaPrenotata, prezzoPizzaPrenotata)"
                    + "VALUES(" + idOrdine + ", '" + p.getNomePizza() + "', " + p.getPrezzoPizza() + ");";
            System.out.println(action);
            stm.executeUpdate(action);
            System.out.println("nuova pizza prenotata inserita correttamente");
        } catch (SQLException exc) {
            System.out.println(exc.getMessage()
                    + "\n***[DBmanager.inserisciPizzaPrenotata: executeUpdate(PIZZAPRENOTATA) fallita]***");
        } finally {
            try {
                chiusuraConnessioneDB();
            } catch (SQLException exc) {
                System.out.println(exc.getMessage()
                        + "\n***[DBmanager.inserisciPizzaPrenotata: chiusuraConnessioneDB fallita]***");
            }

        }
    }

    public static ArrayList<Ordine> getOrdiniUtente(Utente u) {
        ArrayList<Ordine> output = null;
        try {
            creazioneConnessioneDB();
        } catch (SQLException exc) {
            System.out.println(exc.getMessage()
                    + "\n***[DBmanager.getOrdiniUtente: creazioneConnessioneDB fallita]***");
        }
        try {

            String action = "SELECT * FROM ORDINE WHERE idUTENTE=" + u.getIdUtente() + " AND annullato='F';";
            ResultSet rs = stm.executeQuery(action);

            ArrayList<Ordine> elencoOrdini = new ArrayList<>();
            while (rs.next()) {
                Ordine ordine = new Ordine();
                ordine.setIdOrdine(rs.getInt("idOrdine"));
                ordine.setIdUtente(rs.getInt("idUtente"));
                ordine.setPrezzoTotale(rs.getDouble("prezzoTotale"));
                ordine.setIndirizzo(rs.getString("indirizzo"));
                ordine.setData(rs.getString("data"));
                ordine.setQuantitaTotale(rs.getInt("quantitaTotale"));
                ordine.setValutazione(rs.getInt("valutazione"));
                ordine.setAnnullato(rs.getString("annullato").equals("T"));
                ordine.setConsegnato(rs.getString("consegnato").equals("T"));
                elencoOrdini.add(ordine);
            }

            System.out.println("ordini prelevati correttamente");
            output = elencoOrdini;
        } catch (SQLException exc) {
            System.out.println(exc.getMessage()
                    + "\n***[DBmanager.getOrdiniUtente: executeQuery(ORDINE) fallita]***");
        } finally {
            try {
                chiusuraConnessioneDB();
            } catch (SQLException exc) {
                System.out.println(exc.getMessage()
                        + "\n***[DBmanager.getOrdiniUtente: chiusuraConnessioneDB fallita]***");
            }

        }
        return output;
    }

    public static boolean rimuoviOrdine(int idOrdine) {
        boolean output = false;
        try {
            creazioneConnessioneDB();
        } catch (SQLException exc) {
            System.out.println(exc.getMessage()
                    + "\n***[DBmanager.rimuoviOrdine: creazioneConnessioneDB fallita]***");
        }

        System.out.println("Sto per rimuovere dati in ORDINE");

        try {
            String action = "DELETE FROM PIZZAPRENOTATA WHERE idORDINE=" + idOrdine + ";";
            stm.executeUpdate(action);
            System.out.println("pizza rimossa correttamente");
            String action2 = "DELETE FROM ORDINE WHERE idORDINE=" + idOrdine + ";";
            System.out.println("ordine rimosso correttamente");
            stm.executeUpdate(action2);
            output = true;
        } catch (SQLException exc) {
            System.out.println(exc.getMessage()
                    + "\n***[DBmanager.rimuoviOrdine: executeUpdate fallita]***");
            output = false;
        } finally {

            try {
                chiusuraConnessioneDB();
            } catch (SQLException exc) {
                System.out.println(exc.getMessage()
                        + "\n***[DBmanager.rimuoviOrdine: chiusuraConnessioneDB fallita]***");
            }

        }
        return output;
    }

    public static int contaOrdiniUtente(int idUtente) {
        int output = Integer.MAX_VALUE;
        try {
            creazioneConnessioneDB();
        } catch (SQLException exc) {
            System.out.println(exc.getMessage()
                    + "\n***[DBmanager.contaOrdiniUtente: creazioneConnessioneDB fallita]***");
        }

        System.out.println("Sto per contare gli ordini");

        try {
            String action = "SELECT COUNT(idORDINE) FROM ORDINE WHERE idUTENTE=" + idUtente + ";";
            ResultSet rs = stm.executeQuery(action);
            while (rs.next()) {
                output = rs.getInt("COUNT(idORDINE)");
            }
        } catch (SQLException exc) {
            System.out.println(exc.getMessage()
                    + "\n***[DBmanager.rimuoviOrdine: executeUpdate fallita]***");
        } finally {

            try {
                chiusuraConnessioneDB();
            } catch (SQLException exc) {
                System.out.println(exc.getMessage()
                        + "\n***[DBmanager.rimuoviOrdine: chiusuraConnessioneDB fallita]***");
            }

        }
        return output;
    }

    public static ArrayList<Ordine> getOrdiniAdmin() {
        ArrayList<Ordine> output = null;
        try {
            creazioneConnessioneDB();
        } catch (SQLException exc) {
            System.out.println(exc.getMessage()
                    + "\n***[DBmanager.getOrdiniAdmin: creazioneConnessioneDB fallita]***");
        }
        try {

            String action = "SELECT ORDINE.idORDINE, ORDINE.idUtente, prezzoTotale, ORDINE.indirizzo, "
                    + "ORDINE.data, valutazione, ORDINE.annullato, ORDINE.consegnato  "
                    + "FROM ORDINE, UTENTE "
                    + "WHERE ORDINE.idUTENTE=UTENTE.idUTENTE ORDER BY UTENTE.username;";
            ResultSet rs = stm.executeQuery(action);

            ArrayList<Ordine> elencoOrdini = new ArrayList<>();
            while (rs.next()) {
                Ordine ordine = new Ordine();
                ordine.setIdOrdine(rs.getInt("idORDINE"));
                ordine.setIdUtente(rs.getInt("idUTENTE"));
                ordine.setPrezzoTotale(rs.getDouble("prezzoTotale"));
                ordine.setIndirizzo(rs.getString("indirizzo"));
                ordine.setData(rs.getString("data"));
                ordine.setValutazione(rs.getInt("valutazione"));
                ordine.setAnnullato(rs.getString("annullato").equals("T"));
                ordine.setConsegnato(rs.getString("consegnato").equals("T"));
                
                elencoOrdini.add(ordine);
            }
            System.out.println("ordini prelevati correttamente");
            output = elencoOrdini;
        } catch (SQLException exc) {
            System.out.println(exc.getMessage()
                    + "\n***[DBmanager.getOrdiniAdmin: executeQuery(ORDINE) fallita]***");
        } finally {
            try {
                chiusuraConnessioneDB();
            } catch (SQLException exc) {
                System.out.println(exc.getMessage()
                        + "\n***[DBmanager.getOrdiniAdmin: chiusuraConnessioneDB fallita]***");
            }

        }
        return output;
    }

    public static boolean annullaOrdine(int idOrdine) {
        boolean output = false;
        try {
            creazioneConnessioneDB();
        } catch (SQLException exc) {
            System.out.println(exc.getMessage()
                    + "\n***[DBmanager.annullaOrdine: creazioneConnessioneDB fallita]***");
        }
        try {

            String action = "UPDATE ORDINE SET annullato='T' WHERE idORDINE=" + idOrdine + ";";
            stm.execute(action);

            System.out.println("ordine annullato correttamente");
            output = true;
        } catch (SQLException exc) {
            System.out.println(exc.getMessage()
                    + "\n***[DBmanager.annullaOrdine: execute(annullaOrdine) fallita]***");
            output = false;
        } finally {
            try {
                chiusuraConnessioneDB();

            } catch (SQLException exc) {
                System.out.println(exc.getMessage()
                        + "\n***[DBmanager.annullaOrdine: chiusuraConnessioneDB fallita]***");
            }

        }
        return output;
    }

    public static boolean avvenutaConsegna(int idOrdine) {
        boolean output = false;
        try {
            creazioneConnessioneDB();
        } catch (SQLException exc) {
            System.out.println(exc.getMessage()
                    + "\n***[DBmanager.avvenutaConsegna: creazioneConnessioneDB fallita]***");
        }
        try {

            String action = "UPDATE ORDINE SET consegnato='T' WHERE idORDINE=" + idOrdine + ";";
            stm.execute(action);

            System.out.println("ordine avvenutaConsegna correttamente");
            output = true;
        } catch (SQLException exc) {
            System.out.println(exc.getMessage()
                    + "\n***[DBmanager.avvenutaConsegna: execute(annullaOrdine) fallita]***");
            output = false;
        } finally {
            try {
                chiusuraConnessioneDB();

            } catch (SQLException exc) {
                System.out.println(exc.getMessage()
                        + "\n***[DBmanager.avvenutaConsegna: chiusuraConnessioneDB fallita]***");
            }

        }
        return output;
    }

    public static void rimuoviPizza(int idPizzaToRemove) {
        try {
            creazioneConnessioneDB();
        } catch (SQLException exc) {
            System.out.println(exc.getMessage()
                    + "\n***[DBmanager.rimuoviPizza: creazioneConnessioneDB fallita]***");
        }

        System.out.println("Sto per rimuovere dati in PIZZA");

        try {
            String action = "DELETE FROM PIZZA WHERE idPizza=" + idPizzaToRemove + ";";
            //System.out.println(action);
            stm.executeUpdate(action);
            System.out.println("pizza rimossa correttamente");

        } catch (SQLException exc) {
            System.out.println(exc.getMessage()
                    + "\n***[DBmanager.rimuoviPizza: executeUpdate fallita]***");

        } finally {

            try {
                chiusuraConnessioneDB();
            } catch (SQLException exc) {
                System.out.println(exc.getMessage()
                        + "\n***[DBmanager.rimuoviPizza: chiusuraConnessioneDB fallita]***");
            }

        }

    }

    public static void rimuoviPrenotazione(int idPrenotazioneToRemove) {
        try {
            creazioneConnessioneDB();
        } catch (SQLException exc) {
            System.out.println(exc.getMessage()
                    + "\n***[DBmanager.rimuoviPrenotazione: creazioneConnessioneDB fallita]***");
        }

        System.out.println("Sto per rimuovere dati in PRENOTAZIONI( e PIZZEPRENOTATE)");

        try {
            String action1 = "DELETE FROM PIZZEPRENOTATE WHERE idPrenotazione in (" + idPrenotazioneToRemove + ")";
            //System.out.println(action1);
            stm.executeUpdate(action1);

            System.out.println("pizza(/e) prenotata(/e) rimossa(/e) correttamente");

        } catch (SQLException exc) {
            System.out.println(exc.getMessage()
                    + "\n***[DBmanager.rimuoviPrenotazione: executeUpdate(PIZZEPRENOTATE) fallita]***");

        }

        try {
            String action2 = "DELETE FROM PRENOTAZIONI WHERE idPrenotazione in (" + idPrenotazioneToRemove + ")";
            //System.out.println(action2);
            stm.executeUpdate(action2);

            System.out.println("prenotazione rimossa correttamente");
        } catch (SQLException exc) {
            System.out.println(exc.getMessage()
                    + "\n***[DBmanager.rimuoviPrenotazione: executeUpdate(PRENOTAZIONI) fallita]***");

        } finally {

            try {
                chiusuraConnessioneDB();
            } catch (SQLException exc) {
                System.out.println(exc.getMessage()
                        + "\n***[DBmanager.rimuoviPizza: chiusuraConnessioneDB fallita]***");
            }

        }

    }

    public static ArrayList<Pizza> caricaMenu(String utente) {
        ArrayList<Pizza> elenco = new ArrayList<>();
        try {
            creazioneConnessioneDB();
        } catch (SQLException exc) {
            System.out.println(exc.getMessage()
                    + "\n***[DBmanager.caricaMenu: creazioneConnessioneDB fallita]***");
        }

        System.out.println("Sto per visualizzare le pizze disponibili in PIZZA");

        try {

            String query = ""
                    + "SELECT * "
                    + "FROM PIZZA ";
            if (utente.equals("User")) {
                query += "WHERE disponibile='T' ";
            }
            query += "ORDER BY disponibile DESC, prezzoPizza";
            query += ";";

            //System.out.println(query);
            ResultSet rset = stm.executeQuery(query);

            while (rset.next()) {
                Pizza temp = new Pizza();
                temp.setIdPizza(rset.getInt("idPizza"));
                temp.setNomePizza(rset.getString("nomePizza"));
                temp.setIngredienti(rset.getString("ingredienti"));
                temp.setPrezzoPizza(rset.getDouble("prezzoPizza"));
                String disp = (rset.getString("disponibile"));
                temp.setDisponibile(disp.equals("T"));
                elenco.add(temp);
            }

        } catch (Exception exc) {
            System.out.println(exc.getMessage()
                    + "\n***[DBmanager.caricaMenu: while di caricamento interrotto]***");

        } finally {

            try {
                chiusuraConnessioneDB();
            } catch (SQLException exc) {
                System.out.println(exc.getMessage()
                        + "\n***[DBmanager.caricaMenu: chiusuraConnessioneDB fallita]***");
            }

        }
        return elenco;
    }

    public static void visualizzaPrenotazioni() {
        try {
            creazioneConnessioneDB();
        } catch (SQLException exc) {
            System.out.println(exc.getMessage()
                    + "\n***[DBmanager.visualizzaPrenotazioni: creazioneConnessioneDB fallita]***");
        }

        System.out.println("Sto per visualizzare le prenotazioni");

        try {
            String query = ""
                    + "SELECT * "
                    + "FROM PRENOTAZIONI, PIZZEPRENOTATE "
                    + "WHERE PRENOTAZIONI.idPrenotazione = PIZZEPRENOTATE.idPrenotazione";
            //System.out.println(query);
            ResultSet rset = stm.executeQuery(query);
            System.out.println("Lista prenotazioni: ");
            while (rset.next()) {
                System.out.println(""
                        + "" + rset.getString("idUtente")
                        + ", " + rset.getString("nomePizza")
                        + ", " + rset.getString("quantita")
                        + ", " + rset.getDouble("prezzoTotale"));
            }

        } catch (Exception exc) {
            System.out.println(exc.getMessage()
                    + "\n***[DBmanager.visualizzaPrenotazioni: while di visualizzazione interrotto]***");

        } finally {

            try {
                chiusuraConnessioneDB();
            } catch (SQLException exc) {
                System.out.println(exc.getMessage()
                        + "\n***[DBmanager.visualizzaPrenotazioni: chiusuraConnessioneDB fallita]***");
            }

        }
    }

    public static Pizza getPizzaById(int idPizza) {
        Pizza output = new Pizza();
        try {
            creazioneConnessioneDB();
        } catch (SQLException exc) {
            System.out.println(exc.getMessage()
                    + "\n***[DBmanager.getPizzaById: creazioneConnessioneDB fallita]***");
        }

        System.out.println("Sto per trovare la tua pizza");

        try {
            String query = "SELECT * FROM PIZZA WHERE IDPIZZA='" + idPizza + "';";
            //System.out.println(query);
            ResultSet rs = stm.executeQuery(query);

            while (rs.next()) {
                output.setIdPizza(rs.getInt("idPIZZA"));
                output.setNomePizza(rs.getString("nomePizza"));
                output.setIngredienti(rs.getString("ingredienti"));
                output.setPrezzoPizza(rs.getDouble("prezzoPizza"));
                output.setDisponibile(rs.getBoolean("disponibile"));
            }
            System.out.println("pizza trovata");
        } catch (SQLException exc) {
            System.out.println(exc.getMessage()
                    + "\n***[DBmanager.getPizzaById: executeQuery(ID) fallita]***");

        } finally {

            try {
                chiusuraConnessioneDB();
            } catch (SQLException exc) {
                System.out.println(exc.getMessage()
                        + "\n***[DBmanager.getPizzaById: chiusuraConnessioneDB fallita]***");
            }

        }
        return output;
    }

    public static ArrayList<PizzaPrenotata> getElencoPizzeByIdOrdine(int idOrdine) {
        ArrayList<PizzaPrenotata> output = new ArrayList<>();
        try {
            creazioneConnessioneDB();
        } catch (SQLException exc) {
            System.out.println(exc.getMessage()
                    + "\n***[DBmanager.getElencoPizzeByIdOrdine: creazioneConnessioneDB fallita]***");
        }

        System.out.println("Sto per trovare la tua pizza");

        try {
            String query = "SELECT * FROM PIZZAPRENOTATA WHERE IDORDINE='" + idOrdine + "';";
            //System.out.println(query);
            ResultSet rs = stm.executeQuery(query);

            while (rs.next()) {
                PizzaPrenotata item = new PizzaPrenotata();
                item.setIdPizzaPrenotata(rs.getInt("idPIZZAPRENOTATA"));
                item.setIdOrdine(rs.getInt("idORDINE"));
                item.setNomePizzaPrenotata(rs.getString("nomePizzaPrenotata"));
                item.setPrezzoPizzaPrenotata(rs.getDouble("prezzoPizzaPrenotata"));
                output.add(item);
            }
            System.out.println("pizza trovata");
        } catch (SQLException exc) {
            System.out.println(exc.getMessage()
                    + "\n***[DBmanager.getElencoPizzeByIdOrdine: executeQuery(ID) fallita]***");

        } finally {

            try {
                chiusuraConnessioneDB();
            } catch (SQLException exc) {
                System.out.println(exc.getMessage()
                        + "\n***[DBmanager.getElencoPizzeByIdOrdine: chiusuraConnessioneDB fallita]***");
            }

        }
        return output;
    }

    public static Pizza getPizzaByAll(String nomePizza, String ingredienti,
            double prezzoPizza, String disponibile) {
        Pizza output = new Pizza();
        try {
            creazioneConnessioneDB();
        } catch (SQLException exc) {
            System.out.println(exc.getMessage()
                    + "\n***[DBmanager.getPizzaById: creazioneConnessioneDB fallita]***");
        }

        System.out.println("Sto per trovare la tua pizza");

        try {
            String query = "SELECT * FROM PIZZA WHERE nomePizza='" + nomePizza + "' AND "
                    + "ingredienti='" + ingredienti + "' AND "
                    + "prezzoPizza=" + prezzoPizza + " AND "
                    + "disponibile='" + disponibile + "';";
            //System.out.println(query);
            ResultSet rs = stm.executeQuery(query);

            while (rs.next()) {
                output.setIdPizza(rs.getInt("idPIZZA"));
                output.setNomePizza(rs.getString("nomePizza"));
                output.setIngredienti(rs.getString("ingredienti"));
                output.setPrezzoPizza(rs.getDouble("prezzoPizza"));
                output.setDisponibile(rs.getBoolean("disponibile"));
            }
            System.out.println("pizza trovata");
        } catch (SQLException exc) {
            System.out.println(exc.getMessage()
                    + "\n***[DBmanager.getPizzaById: executeQuery(ID) fallita]***");

        } finally {

            try {
                chiusuraConnessioneDB();
            } catch (SQLException exc) {
                System.out.println(exc.getMessage()
                        + "\n***[DBmanager.getPizzaById: chiusuraConnessioneDB fallita]***");
            }

        }
        return output;
    }

    public static Utente login(String username, String password) {
        Utente output = new Utente();
        try {
            creazioneConnessioneDB();
        } catch (SQLException exc) {
            System.out.println(exc.getMessage()
                    + "\n***[DBmanager.login: creazioneConnessioneDB fallita]***");
        }

        System.out.println("Sto per trovare l'utente che si sta loggando");

        try {
            String query = "SELECT * FROM UTENTE WHERE username='" + username + "' AND password='" + password + "';";
            //System.out.println(query);
            ResultSet rs = stm.executeQuery(query);

            while (rs.next()) {
                output.setIdUtente(rs.getInt("idUTENTE"));
                output.setUsername(rs.getString("username"));
                output.setPassword(rs.getString("password"));
                output.setIndirizzo(rs.getString("indirizzo"));
                output.setRuolo(rs.getString("ruolo"));
                output.setAttivo(rs.getString("attivo"));
            }
            System.out.print("utente creato, se presente");
            System.out.println("***" + output.getUsername() + "---" + output.getPassword());
        } catch (SQLException exc) {
            System.out.println(exc.getMessage()
                    + "\n***[DBmanager.login: executeQuery(USERNAME,PASSWORD) fallita]***");

        } finally {

            try {
                chiusuraConnessioneDB();
            } catch (SQLException exc) {
                System.out.println(exc.getMessage()
                        + "\n***[DBmanager.login: chiusuraConnessioneDB fallita]***");
            }

        }
        return output;
    }

    public static void inserisciValutazione(int idOrdine, int valutazione) {
        System.out.println(valutazione);
        try {
            creazioneConnessioneDB();
        } catch (SQLException exc) {
            System.out.println(exc.getMessage()
                    + "\n***[DBmanager.inserisciValutazione: creazioneConnessioneDB fallita]***");
        }
        try {

            String action = "UPDATE ORDINE SET valutazione=" + valutazione + " WHERE idORDINE=" + idOrdine + ";";
            stm.execute(action);

            System.out.println("valutazione inserita correttamente");

        } catch (SQLException exc) {
            System.out.println(exc.getMessage()
                    + "\n***[DBmanager.inserisciValutazione: execute(inserisciValutazione) fallita]***");

        } finally {
            try {
                chiusuraConnessioneDB();

            } catch (SQLException exc) {
                System.out.println(exc.getMessage()
                        + "\n***[DBmanager.inserisciValutazione: chiusuraConnessioneDB fallita]***");
            }

        }

    }

    /**
     * ************************************************************************
     */
    /**
     * ********************** private/aux methods *****************************
     */
    /**
     * ************************************************************************
     */
    private static void creazioneConnessioneDB() throws SQLException {
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            conn = DriverManager.getConnection(url, user, pwd);
            stm = conn.createStatement();
        } catch (Exception exc) {
            System.out.println(exc.getMessage()
                    + "\n***[DBmanager.ceazioneConnessioneDB: creazione connessione DB fallita]***");
        }

    }

    private static void chiusuraConnessioneDB() throws SQLException {
        stm.close();
        conn.close();
    }

}
