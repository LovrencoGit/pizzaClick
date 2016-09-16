package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Locale;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.*;
import utilities.*;

/**
 *
 * @author Lovrenco
 */
public class DesktopServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        HttpSession session = request.getSession();
        String cmd = request.getParameter("cmd");
        RequestDispatcher rd;

        /*Aggiungere una nuova pizza al carrello*/
        if (cmd.equals("addCarrello")) {

            Utente utente = (Utente) session.getAttribute("utente");
            controlloUtente(utente, "all", response, request);
            if (session.getAttribute("carrello") != null) {
                Carrello carrello = (Carrello) session.getAttribute("carrello");
                int idPizza = Integer.parseInt(request.getParameter("pizza"));
                Pizza pizzaCarello = (Pizza) DBManager.getPizzaById(idPizza);
                carrello.addCarrello(pizzaCarello);
                session.setAttribute("carrello", carrello);

                PrintWriter out = response.getWriter();
                String html = "";
                for (Pizza item : carrello.getElencoPizze()) {
                    html += "<p><button id='rcorners2' "
                            + "onclick='RichiestaRemove(" + item.getIdPizza() + ", " + carrello.getPrezzoTotale() + ", " + item.getPrezzoPizza() + ")'>X</button>"
                            + item.getNomePizza() + "     " + String.format(Locale.US, "%1$.2f", item.getPrezzoPizza())+" €" 
                            + "<p class='pNascosto'>" + item.getNomePizza() + "      " + String.format(Locale.US, "%1$.2f", item.getPrezzoPizza()) + " €</p></p>";
                }
                html += "<p>TOTALE " + String.format(Locale.US, "%1$.2f", carrello.getPrezzoTotale()) + " €</p>";
                out.print(html);
                out.flush();
                out.close();
            }

            /*Rimuovere pizza dal carrello*/
        } else if (cmd.equals("removeCarrello")) {

            Utente utente = (Utente) session.getAttribute("utente");
            controlloUtente(utente, "all", response, request);
            Carrello carrello = (Carrello) session.getAttribute("carrello");
            int idPizza = Integer.parseInt(request.getParameter("pizza"));
            String clear = request.getParameter("clear");
            if (clear.equals("false")) {
                Pizza pizzaCarrello = (Pizza) DBManager.getPizzaById(idPizza);
                carrello.removeCarrello(pizzaCarrello);
                session.setAttribute("carrello", carrello);
                PrintWriter out = response.getWriter();
                String html = "";
                System.out.println("[removeCarrello(clear=false)] PrezzoTotaleCarrello: " + carrello.getPrezzoTotale());
                for (Pizza item : carrello.getElencoPizze()) {
                    html += "<p><button id='rcorners2' "
                            + "onclick='RichiestaRemove(" + item.getIdPizza() + ", " + carrello.getPrezzoTotale() + ", " + item.getPrezzoPizza() + ")'>X</button>"
                            + item.getNomePizza() + "      " + String.format(Locale.US, "%1$.2f", item.getPrezzoPizza()) + " €<p class='pNascosto'>" + item.getNomePizza() + "      " + String.format(Locale.US, "%1$.2f", item.getPrezzoPizza()) + " €</p></p>";
                }

                if (carrello.getPrezzoTotale() == 0.0) {
                    html += "";
                } else {
                    html += "<p>TOTALE " + String.format(Locale.US, "%1$.2f", carrello.getPrezzoTotale()) + " €</p>";
                }
                out.print(html);
                out.flush();
                out.close();
            }

            /*Effettuare login*/
        } else if (cmd.equals("login")) {

            String username = request.getParameter("txtUsername");
            goToErrorPage( InputChecker.checkGenericText(username) , response,request);
            String password = request.getParameter("txtPassword");
            goToErrorPage( InputChecker.checkGenericText(password) , response,request);
            Utente utente = DBManager.login(username, password);
            if (!(utente.getRuolo().equals(""))) {
                session.setAttribute("utente", utente);
            } else {
                String message = "Login fallito, riprovare";
                session.setAttribute("messaggio", message);
            }
            rd = request.getRequestDispatcher("index.jsp");
            rd.forward(request, response);

            /*Effettuare logout*/
        } else if (cmd.equals("logout")) {

            if (session.getAttribute("utente") != null) {
                session.removeAttribute("utente");
                session.removeAttribute("carrello");
                session.setAttribute("messaggio", "");
            }
            Utente empty = new Utente();
            session.setAttribute("utente", empty);
            rd = request.getRequestDispatcher("index.jsp");
            rd.forward(request, response);

        } else if (cmd.equals("doveSiamo")) {

            rd = request.getRequestDispatcher("doveSiamo.jsp");
            rd.forward(request, response);

        } else if (cmd.equals("chiSiamo")) {

            rd = request.getRequestDispatcher("chiSiamo.jsp");
            rd.forward(request, response);

        } else if (cmd.equals("gallery")) {

            rd = request.getRequestDispatcher("gallery.jsp");
            rd.forward(request, response);

        } else if (cmd.equals("index")) {

            rd = request.getRequestDispatcher("index.jsp");
            rd.forward(request, response);

        } else if (cmd.equals("crud")) {

            Utente utente = (Utente) session.getAttribute("utente");
            controlloUtente(utente, "A", response, request);
            ArrayList menuCrud = DBManager.caricaMenu("Admin");
            session.setAttribute("elenco_pizze_admin", menuCrud);
            rd = request.getRequestDispatcher("crud.jsp");
            rd.forward(request, response);

            /*Aggiungere pizza al menu*/
        } else if (cmd.equals("addMenu")) {

            Utente utente = (Utente) session.getAttribute("utente");
            controlloUtente(utente, "A", response, request);
            String nomePizza = request.getParameter("txtNomePizza");
            goToErrorPage( InputChecker.checkGenericText(nomePizza) , response,request);
            String ingredienti = request.getParameter("txtIngredienti");
            goToErrorPage( InputChecker.checkGenericText(ingredienti) , response,request);
            String prezzoString = request.getParameter("txtPrezzoPizza");
            //System.out.println("****** ADD |" + prezzoString);
            goToErrorPage( InputChecker.checkPrezzo(prezzoString+"") , response,request);
            double prezzoPizza = Double.parseDouble(prezzoString);
            String disponibile = request.getParameter("optDisponibile");
            DBManager.inserisciNuovaPizza(nomePizza, ingredienti, prezzoPizza, disponibile);
            ArrayList menuCrud = DBManager.caricaMenu("Admin");
            session.setAttribute("elenco_pizze_admin", menuCrud);
            rd = request.getRequestDispatcher("crud.jsp");
            rd.forward(request, response);

            /*Modificare pizza del menu*/
        } else if (cmd.equals("editMenu")) {

            Utente utente = (Utente) session.getAttribute("utente");
            controlloUtente(utente, "A", response, request);
            int idPizzaRimosso = Integer.parseInt(request.getParameter("idPizza"));
            Pizza p = DBManager.getPizzaById(idPizzaRimosso);
            DBManager.rimuoviPizza(idPizzaRimosso);
            ArrayList<Pizza> elencoAdmin = (ArrayList<Pizza>) session.getAttribute("elenco_pizze_admin");
            ArrayList<Pizza> elencoUser = (ArrayList<Pizza>) session.getAttribute("elenco_pizze_user");
            rimuoviDaAdmin(elencoAdmin, p);
            rimuoviDaUser(elencoUser, p);
            //Rimozione Finita -- Inizio aggiunta pizza
            String nomePizza = request.getParameter("txtNomePizza");
            goToErrorPage( InputChecker.checkGenericText(nomePizza) , response,request);
            String ingredienti = request.getParameter("txtIngredienti");
            goToErrorPage( InputChecker.checkGenericText(ingredienti) , response,request);
            String prezzoPizzaString = request.getParameter("txtPrezzoPizza");
            //System.out.println("****** EDIT |" + prezzoPizzaString);
            goToErrorPage( InputChecker.checkPrezzo(prezzoPizzaString) , response,request);
            double prezzoPizza = Double.parseDouble(prezzoPizzaString);
            String disponibile = request.getParameter("optDisponibile");

            DBManager.inserisciNuovaPizza(nomePizza, ingredienti, prezzoPizza, disponibile);
            Pizza pizzaAggiunta = DBManager.getPizzaByAll(nomePizza, ingredienti, prezzoPizza, disponibile);
            elencoAdmin.add(pizzaAggiunta);
            if (pizzaAggiunta.isDisponibile()) {
                elencoUser.add(pizzaAggiunta);
            }
            session.setAttribute("elenco_pizze_admin", elencoAdmin);
            session.setAttribute("elenco_pizze_user", elencoUser);

            rd = request.getRequestDispatcher("crud.jsp");
            rd.forward(request, response);

            /*Cancellare pizza dal menu*/
        } else if (cmd.equals("deleteMenu")) {

            Utente utente = (Utente) session.getAttribute("utente");
            controlloUtente(utente, "A", response, request);
            int idPizzaDelete = Integer.parseInt(request.getParameter("idPizza"));
            DBManager.rimuoviPizza(idPizzaDelete);
            ArrayList menuCrud = DBManager.caricaMenu("Admin");
            session.setAttribute("elenco_pizze_admin", menuCrud);
            rd = request.getRequestDispatcher("crud.jsp");
            rd.forward(request, response);

            /*Stampare carrello al refresh*/
        } else if (cmd.equals("printCarrello")) {

            Utente utente = (Utente) session.getAttribute("utente");
            controlloUtente(utente, "all", response, request);
            Carrello c = (Carrello) session.getAttribute("carrello");
            PrintWriter out = response.getWriter();
            String html = "";
            if (c != null && !c.getElencoPizze().isEmpty()) {
                html = c.printCarrello();
            }
            out.print(html);
            out.flush();
            out.close();

        } else if (cmd.equals("acquista")) {

            Utente utente = (Utente) session.getAttribute("utente");
            controlloUtente(utente, "all", response, request);
            Ordine o = new Ordine();
            Carrello carrello = (Carrello) session.getAttribute("carrello");
            String indirizzo = request.getParameter("txtIndirizzo");
            goToErrorPage( InputChecker.checkGenericText(indirizzo) , response,request);
            String data = request.getParameter("txtData");
            goToErrorPage( InputChecker.checkData(data) , response,request);
            String time = request.getParameter("txtOrario");
            goToErrorPage( InputChecker.checkOra(time) , response,request);

            //YYYY-MM-DD HH:MM:SS (SS-> default 00)
            String datatime = data + " " + time + ":00";
            DBManager.acquista(carrello, utente, indirizzo, datatime);
            ArrayList<Ordine> elencoOrdini = DBManager.getOrdiniUtente(utente);
            session.setAttribute("elenco_ordini", elencoOrdini);
            session.setAttribute("carrello", new Carrello());
            boolean feedbackAcquista = true;
            session.setAttribute("feedbackAcquista", feedbackAcquista);
            rd = request.getRequestDispatcher("Dispatcher?src=desktop&cmd=visualizzaOrdini");
            rd.forward(request, response);

        } else if (cmd.equals("visualizzaOrdini")) {

            Utente utente = (Utente) session.getAttribute("utente");
            controlloUtente(utente, "all", response, request);
            ArrayList<Ordine> elencoOrdini = DBManager.getOrdiniUtente(utente);
            session.setAttribute("elenco_ordini", elencoOrdini);
            if (session.getAttribute("feedbackAcquista") == null) {
                session.setAttribute("feedbackAcquista", false);
            }
            rd = request.getRequestDispatcher("gestioneOrdini.jsp");
            rd.forward(request, response);

        } else if (cmd.equals("annullaOrdine")) {

            Utente utente = (Utente) session.getAttribute("utente");
            controlloUtente(utente, "all", response, request);
            int idOrdine = Integer.parseInt(request.getParameter("idOrdine"));
            DBManager.annullaOrdine(idOrdine);
            ArrayList<Ordine> elencoOrdini = DBManager.getOrdiniUtente(utente);
            session.setAttribute("elenco_ordini", elencoOrdini);
            rd = request.getRequestDispatcher("gestioneOrdini.jsp");
            rd.forward(request, response);

        } else if (cmd.equals("avvenutaConsegna")) {

            Utente utente = (Utente) session.getAttribute("utente");
            controlloUtente(utente, "all", response, request);
            int idOrdine = Integer.parseInt(request.getParameter("idOrdine"));
            DBManager.avvenutaConsegna(idOrdine);
            ArrayList<Ordine> elencoOrdini = DBManager.getOrdiniUtente(utente);
            session.setAttribute("elenco_ordini", elencoOrdini);
            rd = request.getRequestDispatcher("gestioneOrdini.jsp");
            rd.forward(request, response);

        } else if (cmd.equals("valutaOrdine")) {

            System.out.println("[valutaOrdine]");
            Utente utente = (Utente) session.getAttribute("utente");
            controlloUtente(utente, "all", response, request);
            int idOrdine = Integer.parseInt(request.getParameter("idOrdine"));
            int rating = Integer.parseInt(request.getParameter("rating"));
            System.out.println("rate["+rating+"]");
            DBManager.inserisciValutazione(idOrdine, rating);
            ArrayList<Ordine> elencoOrdini = DBManager.getOrdiniUtente(utente);
            session.setAttribute("elenco_ordini", elencoOrdini);

        } else if (cmd.equals("pizzeOrdine")) {

            int idOrdine = Integer.parseInt(request.getParameter("idOrdine"));
            //System.out.println("*********" + idOrdine + "***********");
            ArrayList<PizzaPrenotata> elencoPizze = DBManager.getElencoPizzeByIdOrdine(idOrdine);
            PrintWriter out = response.getWriter();
            String html = "";
            html += "<button class='floatRight' id='chiudiInfoOrdine' onclick='allMostra()'>X</button>";
            html += "<h2>Ordine selezionato: </h2>";
            for (PizzaPrenotata item : elencoPizze) {
                html += "<p> - " + item.getNomePizzaPrenotata() + "                                       " + String.format(Locale.US, "%1$.2f", item.getPrezzoPizzaPrenotata()) + " €</p>";
            }
            out.print(html);
            out.flush();
            out.close();

        } else if (cmd.equals("gestisciOrdiniAdmin")) {

            Utente utente = (Utente) session.getAttribute("utente");
            controlloUtente(utente, "A", response, request);
            ArrayList<Utente> elencoUsername = DBManager.getUsernames();
            ArrayList<Ordine> elencoOrdiniAdmin = DBManager.getOrdiniAdmin();
            session.setAttribute("elenco_ordini_admin", elencoOrdiniAdmin);
            session.setAttribute("elenco_usernames", elencoUsername);
            rd = request.getRequestDispatcher("gestioneOrdiniAdmin.jsp");
            rd.forward(request, response);

        } else if (cmd.equals("deleteOrdine")) {

            Utente utente = (Utente) session.getAttribute("utente");
            controlloUtente(utente, "A", response, request);
            int idOrdineDelete = Integer.parseInt(request.getParameter("idOrdine"));
            DBManager.rimuoviOrdine(idOrdineDelete);
            ArrayList<Ordine> elencoOrdiniAdmin = DBManager.getOrdiniAdmin();
            session.setAttribute("elenco_ordini_admin", elencoOrdiniAdmin);
            rd = request.getRequestDispatcher("gestioneOrdiniAdmin.jsp");
            rd.forward(request, response);
            
        } else if (cmd.equals("visualizzaProfilo")) {
            
            Utente utente = (Utente) session.getAttribute("utente");
            controlloUtente(utente, "all", response, request);
            int nOrdini = DBManager.contaOrdiniUtente(utente.getIdUtente());
            session.setAttribute("utente", utente);
            if (nOrdini != Integer.MAX_VALUE) {
                session.setAttribute("nOrdini", nOrdini);
            }
            rd = request.getRequestDispatcher("visualizzaProfilo.jsp");
            rd.forward(request, response);
        }

    }

    /**
     * **** METODI AUSILIARI *****
     */
    private boolean rimuoviDaUser(ArrayList<Pizza> elencoUser, Pizza p) {
        for (int i = 0; i < elencoUser.size(); i++) {
            Pizza temp = (Pizza) elencoUser.get(i);
            if (p.getIdPizza() == temp.getIdPizza() && p.isDisponibile()) {
                elencoUser.remove(i);
                return true;
            }
        }
        return false;
    }

    private boolean rimuoviDaAdmin(ArrayList<Pizza> elencoAdmin, Pizza p) {
        for (int i = 0; i < elencoAdmin.size(); i++) {
            Pizza temp = (Pizza) elencoAdmin.get(i);
            if (p.getIdPizza() == temp.getIdPizza()) {
                elencoAdmin.remove(i);
                return true;
            }
        }
        return false;
    }

    private void controlloUtente(Utente utente, String utenteRichiesto, HttpServletResponse response, HttpServletRequest request)
            throws IOException, ServletException {
        if (!(utenteRichiesto.equals("all")) && !(utente.getRuolo().equals(utenteRichiesto))) {
            RequestDispatcher rd;
            rd = request.getRequestDispatcher("error.jsp");
            rd.forward(request, response);
        }
    }

    private void goToErrorPage(boolean check, HttpServletResponse response, HttpServletRequest request)
            throws IOException, ServletException {
        if (check) {
            RequestDispatcher rd;
            rd = request.getRequestDispatcher("error.jsp");
            rd.forward(request, response);
        }
    }

// <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}//end class
