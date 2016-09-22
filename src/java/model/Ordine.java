package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 *
 * @author Lovrenco
 */
public class Ordine implements Serializable {

    private int idOrdine;
    private int idUtente;
    private int quantitaTotale;
    private double prezzoTotale;
    private String indirizzo;
    private String data;
    private boolean annullato;
    private boolean consegnato;
    private int valutazione;

    public Ordine() {
        idOrdine = Integer.MAX_VALUE;
        idUtente = Integer.MAX_VALUE;
        quantitaTotale = 0;
        prezzoTotale = 0.0;
        indirizzo = "";
        data = "";
        annullato = false;
        consegnato = false;
        valutazione = 0;
    }

    public Ordine(int idOrdine, int idUtente, int quantitaTotale, double prezzoTotale, String indirizzo, String data, boolean annullato, boolean consegnato, int valutazione) {
        this.idOrdine = idOrdine;
        this.idUtente = idUtente;
        this.quantitaTotale = quantitaTotale;
        this.prezzoTotale = prezzoTotale;
        this.indirizzo = indirizzo;
        this.data = data;
        this.annullato = annullato;
        this.consegnato = consegnato;
        this.valutazione = valutazione;
    }

    public int getIdOrdine() {
        return idOrdine;
    }

    public void setIdOrdine(int idOrdine) {
        this.idOrdine = idOrdine;
    }

    public int getIdUtente() {
        return idUtente;
    }

    public void setIdUtente(int idUtente) {
        this.idUtente = idUtente;
    }

    public int getQuantitaTotale() {
        return quantitaTotale;
    }

    public void setQuantitaTotale(int quantitaTotale) {
        this.quantitaTotale = quantitaTotale;
    }

    public double getPrezzoTotale() {
        return prezzoTotale;
    }

    public void setPrezzoTotale(double prezzoTotale) {
        this.prezzoTotale = prezzoTotale;
    }

    public String getIndirizzo() {
        return indirizzo;
    }

    public void setIndirizzo(String indirizzo) {
        this.indirizzo = indirizzo;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public boolean isAnnullato() {
        return annullato;
    }

    public void setAnnullato(boolean annullato) {
        this.annullato = annullato;
    }

    public boolean isConsegnato() {
        return consegnato;
    }

    public void setConsegnato(boolean consegnato) {
        this.consegnato = consegnato;
    }

    public int getValutazione() {
        return valutazione;
    }

    public void setValutazione(int valutazione) {
        this.valutazione = valutazione;
    }

    /**
     * *****************************************************************
     * @param elencoOrdini elenco di tutti gli ordini dell'utente
     * @param feedBackAcquista boolean per feedback dell'ordine
     * @return html della tabella che visualizza gli ordini dell'utente
     */
    public static String ordiniToHTMLForUser(ArrayList<Ordine> elencoOrdini, boolean feedBackAcquista) {

        String ordiniToHTML = "<br><table class='tab'>";
        ordiniToHTML += "<thead><th>ORDINE</th>"
                + "<th>PREZZO</th><th>INDIRIZZO</th><th>DATA</th><th>ORA</th><th></th><th></th><th>VALUTAZIONE</th></thead>";
        int cont = elencoOrdini.size();

        for (int i = elencoOrdini.size() - 1; i >= 0; i--) {
            Ordine item = elencoOrdini.get(i);
            int score = item.getValutazione();
            boolean displayBtnAnnulla = controlloDisplayAnnulla(item.getData());
            ordiniToHTML += (feedBackAcquista && i == elencoOrdini.size() - 1 ? "<tr id='orderSelected" + item.getIdOrdine() + "' class='lastOrder rowOrder'>" : "<tr id='orderSelected" + item.getIdOrdine() + "' class='rowOrder'>");
            ordiniToHTML += "<td class='padding'>" + cont + "</td>";
            ordiniToHTML += "<td class='padding'>" + String.format(Locale.US, "%1$.2f", item.getPrezzoTotale()) + " €</td>";
            ordiniToHTML += "<td class='padding'>" + item.getIndirizzo() + "</td>";
            ordiniToHTML += "<td class='padding'>" + item.getData().substring(0, 10) + "</td>";
            ordiniToHTML += "<td class='padding'>" + item.getData().substring(11, 16) + "</td>";
            if (!(item.isConsegnato())) {
                if (displayBtnAnnulla) {
                    ordiniToHTML += "<td class='padding'><button onclick='RichiestaAnnullaOrdine(" + item.getIdOrdine() + ")' id='btnAnnullaOrdine'>Annulla</button></td>";
                } else {
                    ordiniToHTML += "<td class='padding'><button onclick='RichiestaAnnullaOrdine(" + item.getIdOrdine() + ")' id='btnAnnullaOrdine' class='hide'>Annulla</button></td>";
                }
            } else {
                ordiniToHTML += "<td class='padding'><button onclick='RichiestaAnnullaOrdine(" + item.getIdOrdine() + ")' id='btnAnnullaOrdine' class='hide'>Annulla</button></td>";
            }
            if (item.isConsegnato()) {
                ordiniToHTML += "<td class='padding'>Consegna avvenuta!</td>";
            } else {
                ordiniToHTML += "<td class='padding'><button onclick='RichiestaAvvenutaConsegna(" + item.getIdOrdine() + ")' id='btnConsegnato'>Avvenuta consegna</button></td>";
            }
            ordiniToHTML += "<td align='center' class='padding'>"
                    + "<select id='rating" + item.getIdOrdine() + "' class='rating' onchange='RichiestaValutazione(" + item.getIdOrdine() + ")'>"
                    //+ "<option value=''></option>"
                    + (score == 0 ? "<option value='0' selected='selected'>-</option>" : "<option value='0'>-</option>")
                    + (score == 1 ? "<option value='1' selected='selected'>1</option>" : "<option value='1'>1</option>")
                    + (score == 2 ? "<option value='2' selected='selected'>2</option>" : "<option value='2'>2</option>")
                    + (score == 3 ? "<option value='3' selected='selected'>3</option>" : "<option value='3'>3</option>")
                    + (score == 4 ? "<option value='4' selected='selected'>4</option>" : "<option value='4'>4</option>")
                    + (score == 5 ? "<option value='5' selected='selected'>5</option>" : "<option value='5'>5</option>")
                    + "</select>"
                    + "</td><td><a href='javascript:void(0)' onclick='RichiestaPizzeOrdine(" + item.getIdOrdine() + ")' id='mostra" + item.getIdOrdine() + "' class='showMostra' >Mostra</a></td>";
            ordiniToHTML += "</tr>";
            cont--;
        }

        ordiniToHTML += "<table>";
        return ordiniToHTML;
    }

    public static String ordiniToHTMLForAdmin(ArrayList<Ordine> elencoOrdiniAdmin, ArrayList<Utente> elencoUsernames) {
        String ordiniToHTML = "<br><table class='tab'>";
        ordiniToHTML += "<thead><th>ORDINE</th><th>UTENTE</th>"
                + "<th>PREZZO</th><th>INDIRIZZO</th><th>DATA</th><th>ORA</th><th>Valutazione</th></thead>";
        int cont = elencoOrdiniAdmin.size();
        boolean trovato = false;
        for (int i = elencoOrdiniAdmin.size() - 1; i >= 0; i--) {
            Ordine item = elencoOrdiniAdmin.get(i);
            int score = item.getValutazione();
            trovato = false;
            ordiniToHTML += "<tr id='orderSelected" + item.getIdOrdine() + "' class='rowOrder'>";
            ordiniToHTML += "<td class='padding'>" + cont + "</td>";
            for (int j = 0; j < elencoUsernames.size() && trovato == false; j++) {
                Utente u = elencoUsernames.get(j);
                if (u.getIdUtente() == item.getIdUtente()) {
                    trovato = true;
                    ordiniToHTML += "<td class='padding'>" + u.getUsername() + "</td>";
                }
            }
            ordiniToHTML += "<td class='padding'>" + String.format(Locale.US, "%1$.2f", item.getPrezzoTotale()) + " €</td>";
            ordiniToHTML += "<td class='padding'>" + item.getIndirizzo() + "</td>";
            ordiniToHTML += "<td class='padding'>" + item.getData().substring(0, 10) + "</td>";
            ordiniToHTML += "<td class='padding'>" + item.getData().substring(11, 16) + "</td>";

            ordiniToHTML += "<td align='center' class='padding'>";
            if(item.isAnnullato()){
                ordiniToHTML +="<label> <i>annullato</i> </label>";
            }else{
                ordiniToHTML +="<label> "+(score==0 ? "-":score+"/5 stelle")+" </label>";
            }
                    /*
                    + "<select id='rating" + item.getIdOrdine() + "' class='mostraRating' onchange='RichiestaValutazione(" + item.getIdOrdine() + ")'>"
                    + "<option value=''></option>"
                    + (score == 1 ? "<option value='1' selected='selected'>1</option>" : "<option value='1'>1</option>")
                    + (score == 2 ? "<option value='2' selected='selected'>2</option>" : "<option value='2'>2</option>")
                    + (score == 3 ? "<option value='3' selected='selected'>3</option>" : "<option value='3'>3</option>")
                    + (score == 4 ? "<option value='4' selected='selected'>4</option>" : "<option value='4'>4</option>")
                    + (score == 5 ? "<option value='5' selected='selected'>5</option>" : "<option value='5'>5</option>")
                    + "</select>"
                    */
            ordiniToHTML += "</td><td><a href='javascript:void(0)' onclick='RichiestaPizzeOrdine(" + item.getIdOrdine() + ")' id='mostra" + item.getIdOrdine() + "' class='showMostra' >Mostra</a></td>";
            ordiniToHTML += "<td class='padding'><a href='Dispatcher?src=desktop&cmd=deleteOrdine&idOrdine=" + item.getIdOrdine() + "'><img src='img/delete_70x70.png' width='35px' height='35px'/></td>";
            ordiniToHTML += "</tr>";
            cont--;
        }

        ordiniToHTML += "<table>";
        return ordiniToHTML;
    }

    private static boolean controlloDisplayAnnulla(String dataOrdine) {

        boolean output = false;
        int annoOrdine = Integer.parseInt(dataOrdine.substring(0, 4));
        int meseOrdine = Integer.parseInt(dataOrdine.substring(5, 7));
        int giornoOrdine = Integer.parseInt(dataOrdine.substring(8, 10));
        int oraOrdine = Integer.parseInt(dataOrdine.substring(11, 13));
        int minutiOrdine = Integer.parseInt(dataOrdine.substring(14, 16));

        Calendar gc = new GregorianCalendar(Locale.ITALY);//oggetto per ora di oggi hh:mm:ss

        int annoNow = gc.get(Calendar.YEAR);
        int meseNow = gc.get(Calendar.MONTH) + 1;
        int giornoNow = gc.get(Calendar.DAY_OF_MONTH);
        int oraNow = gc.get(Calendar.HOUR_OF_DAY);
        int minNow = gc.get(Calendar.MINUTE);

        if (meseOrdine > meseNow) {//CASO OTTIMO
            output = true;
        } else if (meseOrdine == meseNow) {
            if (giornoOrdine > giornoNow) {//CASO OTTIMO
                output = true;
            } else if (giornoOrdine == giornoNow) {
                if (oraOrdine > oraNow) {
                    output = ((oraOrdine - oraNow) == 1 && minutiOrdine<=minNow) ? false : true;
                } else if (oraOrdine == oraNow) {
                    output = false;
                } else {
                    output = false;
                }
            } else {//CASO PESSIMO
                output = false;
            }
        } else {//CASO PESSIMO
            output = false;
        }

        return output;
    }
}

