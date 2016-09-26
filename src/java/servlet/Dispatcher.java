package servlet;

import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import utilities.DBManager;
import model.*;

/**
 *
 * @author Lovrenco
 */
public class Dispatcher extends HttpServlet {

    ArrayList<Pizza> elenco;
    Carrello carrello;
    Utente utente;
    Ordine ordine;
    String messaggio;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        carrello = new Carrello();
        utente = new Utente();
        elenco = (ArrayList<Pizza>) DBManager.caricaMenu("User");
        ordine = new Ordine();
        messaggio = "";
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        HttpSession session = request.getSession();
        String cmd = request.getParameter("cmd");
        if (cmd == null) {//serve nel crud add, delete.... per far si che carichi il menu aggiornato
            session.setAttribute("elenco_pizze_user", elenco);
        } else {
            elenco = (ArrayList<Pizza>) DBManager.caricaMenu("User");
            session.setAttribute("elenco_pizze_user", elenco);
        }
        if (session.getAttribute("carrello") == null) {
            carrello = new Carrello();
            session.setAttribute("carrello", carrello);
        }
        if (session.getAttribute("utente") == null) {
            utente = new Utente();
            session.setAttribute("utente", utente);
        }
        if (session.getAttribute("messaggio") == null) {
            messaggio = "";
            session.setAttribute("messaggio", messaggio);
        }

        RequestDispatcher rd;

        String srcMobile = request.getParameter("srcMobile");
        if (srcMobile != null && srcMobile.equals("mobile")) {
            rd = request.getRequestDispatcher("MobileServlet");
            rd.forward(request, response);
        } else {

            String src = request.getParameter("src");
            if (src == null) {
                rd = request.getRequestDispatcher("index.jsp");
                rd.forward(request, response);
            } else if (src.equals("desktop")) {
                rd = request.getRequestDispatcher("DesktopServlet");
                rd.forward(request, response);
            }

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

}
