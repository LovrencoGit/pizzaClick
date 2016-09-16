/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author Lovrenco
 */
public class Utente {

    private int idUtente;
    private String username;
    private String password;
    private String indirizzo;
    private String ruolo;
    private String attivo;

    public Utente() {
        idUtente = Integer.MAX_VALUE;
        username = "";
        password = "";
        ruolo = "";
        attivo = "T";
        indirizzo = "";
    }

    public Utente(int idUtente, String username, String password, String indirizzo, String ruolo, String attivo) {
        this.idUtente = idUtente;
        this.username = username;
        this.password = password;
        this.ruolo = ruolo;
        this.attivo = attivo;
        this.indirizzo = indirizzo;
    }

    public String getIndirizzo() {
        return indirizzo;
    }

    public void setIndirizzo(String indirizzo) {
        this.indirizzo = indirizzo;
    }

    public int getIdUtente() {
        return idUtente;
    }

    public void setIdUtente(int idUtente) {
        this.idUtente = idUtente;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRuolo() {
        return ruolo;
    }

    public void setRuolo(String ruolo) {
        this.ruolo = ruolo;
    }

    public String isAttivo() {
        return attivo;
    }

    public void setAttivo(String attivo) {
        this.attivo = attivo;
    }

    /**
     * ***********************************************************************
     * @return feedback, stringa che indica il success/failure del login
     */
    public String feedbackLogin() {
        String feedback;
        if (ruolo.equals("A") || ruolo.equals("U")) {
            feedback = "Benvenuto " + username;
        } else {
            feedback = "Esegui Login";
        }
        return feedback;
    }

    public String printDatiUtente(Utente utente, int nOrdini) {
        String html = "";
        html += "<h2 class='h2ProfiloUtente' >Username: </h2><p class='pProfiloUtente' >" + utente.getUsername() + "</p>";
        html += "<h2 class='h2ProfiloUtente' >Indirizzo: </h2><p class='pProfiloUtente' >" + utente.getIndirizzo() + "</p>";
        html += "<h2 class='h2ProfiloUtente' >Ruolo: </h2><p class='pProfiloUtente' >" + (utente.getRuolo().equals("A") ? "amministratore" : "cliente") + "</p>";
        html += "<h2 class='h2ProfiloUtente' >Numero di ordini effettuati: </h2><p class='pProfiloUtente' >" + nOrdini + "</p>";
        return html;
    }

}
