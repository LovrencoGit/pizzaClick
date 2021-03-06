package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map.Entry;
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

            System.out.println("[DESKTOP_SERVLET] addCarrello");
            Utente utente = (Utente) session.getAttribute("utente");
            if (controlloUtente(utente, "all", response, request, "[addCarrello] Sessione scaduta. Riprovare")) {
                if (session.getAttribute("carrello") != null) {
                    Carrello carrello = (Carrello) session.getAttribute("carrello");
                    int idPizza = Integer.parseInt(request.getParameter("pizza"));
                    Pizza pizzaCarello = (Pizza) DBManager.getPizzaById(idPizza);
                    carrello.addCarrello(pizzaCarello);
                    session.setAttribute("carrello", carrello);

                    PrintWriter out = response.getWriter();
                    String html = carrello.printCarrello();
                    out.print(html);
                    out.flush();
                    out.close();
                }
            }

            /*Rimuovere pizza dal carrello*/
        } else if (cmd.equals("removeCarrello")) {

            System.out.println("[DESKTOP_SERVLET] removeCarrello");
            Utente utente = (Utente) session.getAttribute("utente");
            if (controlloUtente(utente, "all", response, request, "[removeCarrello] Sessione scaduta. Riprovare")) {
                Carrello carrello = (Carrello) session.getAttribute("carrello");
                String clear = request.getParameter("clear");
                if (clear.equals("false")) {
                    System.out.println("[removeCarrello(clear=false)] PrezzoTotaleCarrello: " + carrello.getPrezzoTotale());
                    int idPizza = Integer.parseInt(request.getParameter("pizza"));
                    Pizza pizzaCarrello = (Pizza) DBManager.getPizzaById(idPizza);
                    carrello.removeCarrello(pizzaCarrello);
                } else if (clear.equals("true")) {
                    System.out.println("[removeCarrello(clear=true)] PrezzoTotaleCarrello: " + carrello.getPrezzoTotale());
                    carrello.clear();
                } else {
                    goToErrorPage(true, response, request);
                }
                session.setAttribute("carrello", carrello);
                PrintWriter out = response.getWriter();
                String html = carrello.printCarrello();
                out.print(html);
                out.flush();
                out.close();
            }

            /*Effettuare login*/
        } else if (cmd.equals("login")) {

            System.out.println("[DESKTOP_SERVLET] login");
            String username = request.getParameter("txtUsername");
            goToErrorPage(InputChecker.checkGenericText(username), response, request);
            String password = request.getParameter("txtPassword");
            goToErrorPage(InputChecker.checkGenericText(password), response, request);
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

            System.out.println("[DESKTOP_SERVLET] logout");
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

            System.out.println("[DESKTOP_SERVLET] doveSiamo");
            rd = request.getRequestDispatcher("doveSiamo.jsp");
            rd.forward(request, response);

        } else if (cmd.equals("chiSiamo")) {

            System.out.println("[DESKTOP_SERVLET] chiSiamo");
            rd = request.getRequestDispatcher("chiSiamo.jsp");
            rd.forward(request, response);

        } else if (cmd.equals("gallery")) {

            System.out.println("[DESKTOP_SERVLET] gallery");
            rd = request.getRequestDispatcher("gallery.jsp");
            rd.forward(request, response);

        } else if (cmd.equals("index")) {

            System.out.println("[DESKTOP_SERVLET] index");
            rd = request.getRequestDispatcher("index.jsp");
            rd.forward(request, response);

        } else if (cmd.equals("crud")) {

            System.out.println("[DESKTOP_SERVLET] crud");
            Utente utente = (Utente) session.getAttribute("utente");
            if (controlloUtente(utente, "A", response, request, "[crud] Operazione permessa solo all'amministratore")) {
                ArrayList menuCrud = DBManager.caricaMenu("Admin");
                session.setAttribute("elenco_pizze_admin", menuCrud);
                rd = request.getRequestDispatcher("crud.jsp");
                rd.forward(request, response);
            }

            /*Aggiungere pizza al menu*/
        } else if (cmd.equals("addMenu")) {

            System.out.println("[DESKTOP_SERVLET] addMenu");
            Utente utente = (Utente) session.getAttribute("utente");
            if (controlloUtente(utente, "A", response, request, "[addMenu] Operazione permessa solo all'amministratore")) {
                String nomePizza = request.getParameter("txtNomePizza");
                goToErrorPage(InputChecker.checkGenericText(nomePizza), response, request);
                String ingredienti = request.getParameter("txtIngredienti");
                goToErrorPage(InputChecker.checkGenericText(ingredienti), response, request);
                String prezzoString = request.getParameter("txtPrezzoPizza");
                goToErrorPage(InputChecker.checkPrezzo(prezzoString + ""), response, request);
                double prezzoPizza = Double.parseDouble(prezzoString);
                String disponibile = request.getParameter("optDisponibile");
                DBManager.inserisciNuovaPizza(nomePizza, ingredienti, prezzoPizza, disponibile);
                ArrayList menuCrud = DBManager.caricaMenu("Admin");
                session.setAttribute("elenco_pizze_admin", menuCrud);
                rd = request.getRequestDispatcher("crud.jsp");
                rd.forward(request, response);
            }
            
            /*Modificare pizza del menu*/
        } else if (cmd.equals("editMenu")) {

            System.out.println("[DESKTOP_SERVLET] editMenu");
            Utente utente = (Utente) session.getAttribute("utente");
            if (controlloUtente(utente, "A", response, request, "[editMenu] Operazione permessa solo all'amministratore")) {
                int idPizzaEdit = Integer.parseInt(request.getParameter("idPizza"));
                String nomePizza = request.getParameter("txtNomePizza");
                goToErrorPage(InputChecker.checkGenericText(nomePizza), response, request);
                String ingredienti = request.getParameter("txtIngredienti");
                goToErrorPage(InputChecker.checkGenericText(ingredienti), response, request);
                String prezzoPizzaString = request.getParameter("txtPrezzoPizza");
                goToErrorPage(InputChecker.checkPrezzo(prezzoPizzaString), response, request);
                double prezzoPizza = Double.parseDouble(prezzoPizzaString);
                String disponibile = request.getParameter("optDisponibile");
                DBManager.modificaPizza(idPizzaEdit, nomePizza, ingredienti, prezzoPizza, disponibile);
                ArrayList<Pizza> elencoAdmin = (ArrayList<Pizza>) session.getAttribute("elenco_pizze_admin");
                ArrayList<Pizza> elencoUser = (ArrayList<Pizza>) session.getAttribute("elenco_pizze_user");
                Pizza pizzaNew = new Pizza(idPizzaEdit, nomePizza, ingredienti, prezzoPizza, disponibile.equals("T"));
                elencoAdmin = editInElencoAdmin(elencoAdmin, pizzaNew);
                elencoUser = editInElencoUser(elencoUser, pizzaNew);
                session.setAttribute("elenco_pizze_admin", elencoAdmin);
                session.setAttribute("elenco_pizze_user", elencoUser);

                rd = request.getRequestDispatcher("crud.jsp");
                rd.forward(request, response);
            }
            
            /*Cancellare pizza dal menu*/
        } else if (cmd.equals("deleteMenu")) {

            System.out.println("[DESKTOP_SERVLET] deleteMenu");
            Utente utente = (Utente) session.getAttribute("utente");
            if (controlloUtente(utente, "A", response, request, "[deleteMenu] Operazione permessa solo all'amministratore")) {
                int idPizzaDelete = Integer.parseInt(request.getParameter("idPizza"));
                DBManager.rimuoviPizza(idPizzaDelete);
                ArrayList menuCrud = DBManager.caricaMenu("Admin");
                session.setAttribute("elenco_pizze_admin", menuCrud);
                rd = request.getRequestDispatcher("crud.jsp");
                rd.forward(request, response);
            }

            /*Stampare carrello al refresh*/
        } else if (cmd.equals("printCarrello")) {

            System.out.println("[DESKTOP_SERVLET] printCarrello");
            Utente utente = (Utente) session.getAttribute("utente");
            if (controlloUtente(utente, "all", response, request, "[printCarrello] Sessione scaduta. Riprovare")) {
                Carrello c = (Carrello) session.getAttribute("carrello");
                PrintWriter out = response.getWriter();
                String html = "";
                if (c != null && !c.getElencoPizze().isEmpty()) {
                    html = c.printCarrello();
                }
                out.print(html);
                out.flush();
                out.close();
            }
            
        } else if (cmd.equals("acquista")) {

            System.out.println("[DESKTOP_SERVLET] acquista");
            Utente utente = (Utente) session.getAttribute("utente");
            if (controlloUtente(utente, "all", response, request, "[aquista] Sessione scaduta. Rieseguire login")) {
                Ordine o = new Ordine();
                Carrello carrello = (Carrello) session.getAttribute("carrello");
                String indirizzo = request.getParameter("txtIndirizzo");
                goToErrorPage(InputChecker.checkGenericText(indirizzo), response, request);
                String data = request.getParameter("txtData");
                goToErrorPage(InputChecker.checkData(data), response, request);
                String time = request.getParameter("txtOrario");
                goToErrorPage(InputChecker.checkOra(time), response, request);

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
            }
            
        } else if (cmd.equals("visualizzaOrdini")) {

            System.out.println("[DESKTOP_SERVLET] visualizzaOrdini");
            Utente utente = (Utente) session.getAttribute("utente");
            if (controlloUtente(utente, "all", response, request, "[visualizzaOrdini] Sessione scaduta. Rieseguire login")) {
                ArrayList<Ordine> elencoOrdini = DBManager.getOrdiniUtente(utente);
                session.setAttribute("elenco_ordini", elencoOrdini);
                if (session.getAttribute("feedbackAcquista") == null) {
                    session.setAttribute("feedbackAcquista", false);
                }
                rd = request.getRequestDispatcher("gestioneOrdini.jsp");
                rd.forward(request, response);
            }
            
        } else if (cmd.equals("annullaOrdine")) {

            System.out.println("[DESKTOP_SERVLET] annullaOrdine");
            Utente utente = (Utente) session.getAttribute("utente");
            if (controlloUtente(utente, "all", response, request, "[annullaOrdine] Sessione scaduta. Rieseguire login")) {
                int idOrdine = Integer.parseInt(request.getParameter("idOrdine"));
                DBManager.annullaOrdine(idOrdine);
                ArrayList<Ordine> elencoOrdini = DBManager.getOrdiniUtente(utente);
                session.setAttribute("elenco_ordini", elencoOrdini);
                rd = request.getRequestDispatcher("gestioneOrdini.jsp");
                rd.forward(request, response);
            }
            
        } else if (cmd.equals("avvenutaConsegna")) {

            System.out.println("[DESKTOP_SERVLET] avvenutaConsegna");
            Utente utente = (Utente) session.getAttribute("utente");
            if (controlloUtente(utente, "all", response, request, "[avvenutaConsegna] Sessione scaduta. Rieseguire login")) {
                int idOrdine = Integer.parseInt(request.getParameter("idOrdine"));
                DBManager.avvenutaConsegna(idOrdine);
                ArrayList<Ordine> elencoOrdini = DBManager.getOrdiniUtente(utente);
                session.setAttribute("elenco_ordini", elencoOrdini);
                rd = request.getRequestDispatcher("gestioneOrdini.jsp");
                rd.forward(request, response);
            }
            
        } else if (cmd.equals("valutaOrdine")) {

            System.out.println("[DESKTOP_SERVLET] valutaOrdine");
            Utente utente = (Utente) session.getAttribute("utente");
            if (controlloUtente(utente, "all", response, request, "[valutaOrdine] Sessione scaduta. Rieseguire login")) {
                int idOrdine = Integer.parseInt(request.getParameter("idOrdine"));
                int rating = Integer.parseInt(request.getParameter("rating"));
                System.out.println("rate[" + rating + "]");
                DBManager.inserisciValutazione(idOrdine, rating);
                ArrayList<Ordine> elencoOrdini = DBManager.getOrdiniUtente(utente);
                session.setAttribute("elenco_ordini", elencoOrdini);
            }
            
        } else if (cmd.equals("pizzeOrdine")) {

            System.out.println("[DESKTOP_SERVLET] pizzeOrdine");
            int idOrdine = Integer.parseInt(request.getParameter("idOrdine"));
            ArrayList<PizzaPrenotata> elencoPizze = DBManager.getElencoPizzeByIdOrdine(idOrdine);
            PrintWriter out = response.getWriter();
            String html = "";
            html += "<button class='floatRight' id='chiudiInfoOrdine' onclick='allMostra()'>X</button>";
            html += "<h2>Ordine selezionato: </h2>";
            HashMap<String, Integer> map = ArrayListPizzaPrenotataDisplayer.getElencoPizzaPrenotataToHashMap(elencoPizze);
            for (Entry<String, Integer> entry : map.entrySet()) {
                String nome = entry.getKey();
                PizzaPrenotata item = ArrayListPizzaPrenotataDisplayer.getPizzaByNomePizza(elencoPizze, nome);
                Integer qty = entry.getValue();
                html += "<p> - " + item.getNomePizzaPrenotata() + "                                       " + String.format(Locale.US, "%1$.2f", item.getPrezzoPizzaPrenotata()) + " €  (x " + qty + ")</p>";
            }
            out.print(html);
            out.flush();
            out.close();

        } else if (cmd.equals("gestisciOrdiniAdmin")) {

            System.out.println("[DESKTOP_SERVLET] gestisciOrdiniAdmin");
            Utente utente = (Utente) session.getAttribute("utente");
            if (controlloUtente(utente, "A", response, request, "[gestisciOrdiniAdmin] Operazione permessa solo all'amministratore")) {
                ArrayList<Utente> elencoUsername = DBManager.getUsernames();
                ArrayList<Ordine> elencoOrdiniAdmin = DBManager.getOrdiniAdmin();
                session.setAttribute("elenco_ordini_admin", elencoOrdiniAdmin);
                session.setAttribute("elenco_usernames", elencoUsername);
                rd = request.getRequestDispatcher("gestioneOrdiniAdmin.jsp");
                rd.forward(request, response);
            }
            
        } else if (cmd.equals("deleteOrdine")) {

            System.out.println("[DESKTOP_SERVLET] deleteOrdine");
            Utente utente = (Utente) session.getAttribute("utente");
            if (controlloUtente(utente, "A", response, request, "[deleteOrdine] Operazione permessa solo all'amministratore")) {
                int idOrdineDelete = Integer.parseInt(request.getParameter("idOrdine"));
                DBManager.rimuoviOrdine(idOrdineDelete);
                ArrayList<Ordine> elencoOrdiniAdmin = DBManager.getOrdiniAdmin();
                session.setAttribute("elenco_ordini_admin", elencoOrdiniAdmin);
                rd = request.getRequestDispatcher("gestioneOrdiniAdmin.jsp");
                rd.forward(request, response);
            }
            
        } else if (cmd.equals("visualizzaProfilo")) {

            System.out.println("[DESKTOP_SERVLET] visualizzaProfilo");
            Utente utente = (Utente) session.getAttribute("utente");
            if (controlloUtente(utente, "all", response, request, "[visualizzaProfilo] Sessione scaduta. Rieseguire login")) {
                int nOrdini = DBManager.contaOrdiniUtente(utente.getIdUtente());
                session.setAttribute("utente", utente);
                if (nOrdini != Integer.MAX_VALUE) {
                    session.setAttribute("nOrdini", nOrdini);
                }
                rd = request.getRequestDispatcher("visualizzaProfilo.jsp");
                rd.forward(request, response);
            }
        }

    }

    /**
     * **** METODI AUSILIARI *****
     */
    private ArrayList<Pizza> editInElencoAdmin(ArrayList<Pizza> elencoAdmin, Pizza pizzaNew) {
        for (int i = 0; i < elencoAdmin.size(); i++) {
            if (elencoAdmin.get(i).getIdPizza() == pizzaNew.getIdPizza()) {
                elencoAdmin.remove(i);
                elencoAdmin.add(i, pizzaNew);
                return elencoAdmin;
            }
        }
        return null;
    }

    private ArrayList<Pizza> editInElencoUser(ArrayList<Pizza> elencoUser, Pizza pizzaNew) {
        for (int i = 0; i < elencoUser.size(); i++) {
            if (elencoUser.get(i).getIdPizza() == pizzaNew.getIdPizza()) {
                elencoUser.remove(i);
                if (pizzaNew.isDisponibile()) {
                    elencoUser.add(i, pizzaNew);
                }
                return elencoUser;
            }
        }
        if (pizzaNew.isDisponibile()) {
            elencoUser.add(pizzaNew);
        }
        return elencoUser;
    }

    private ArrayList<Pizza> removeById(ArrayList<Pizza> elenco, int idPizzaRmv) {
        for (Pizza pizza : elenco) {
            if (pizza.getIdPizza() == idPizzaRmv) {
                elenco.remove(pizza);
                return elenco;
            }
        }
        return elenco;
    }

    /**
     * @return true, utente autorizzato
     * @return false, utente non autorizzato -> page error.jsp
     */
    private boolean controlloUtente(Utente utente, String utenteRichiesto, HttpServletResponse response, HttpServletRequest request, String msg)
            throws IOException, ServletException {
        if (!(utenteRichiesto.equals("all")) && !(utente.getRuolo().equals(utenteRichiesto))) {
            //String msg = "PROVA PROVA PROVA";
            HttpSession session = request.getSession();
            session.setAttribute("msgerrorjsp", msg);
            RequestDispatcher rd;
            rd = request.getRequestDispatcher("error.jsp");
            rd.forward(request, response);
            return false;
        }
        return true;
    }

    private void goToErrorPage(boolean check, HttpServletResponse response, HttpServletRequest request)
            throws IOException, ServletException {
        if (check) {
            String msg = "<p> I dati inseriti in input non sono validi.  La preghiamo di riprovare </p> "
                    + " I seguenti caratteri non sono permessi: <br> "
                    + " ', # , ; , : , * , + , { , } , ( , \\ , ] , [ , § , ! , ? , / , % , £ , $ , % , & , = ";
            HttpSession session = request.getSession();
            session.setAttribute("msgerrorjsp", msg);
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
