/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import com.google.gson.Gson;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.*;
import utilities.DBManager;

/**
 *
 * @author Lovrenco
 */
public class MobileServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String cmdMobile = request.getParameter("cmdMobile");
        
        System.out.println("[MOBILE_SERVLET] cmdMobile= "+cmdMobile);

        Gson gson = new Gson();
        if (cmdMobile.equals("login")) {

            System.out.println("MOBILE_SERVLET [login]");
            String utenteJSON = request.getParameter("utente");
            Utente utente = gson.fromJson(utenteJSON, Utente.class);
            final Utente utenteLoggato = DBManager.login(utente.getUsername(), utente.getPassword());
            ArrayList<Pizza> menu = new ArrayList<>();
            if (!(utenteLoggato.getRuolo().equals(""))) {
                menu = DBManager.caricaMenu(utenteLoggato.getRuolo());
            }

            List lista = new ArrayList<>();
            lista.add(gson.toJson(utenteLoggato));
            lista.add(gson.toJson(menu));
            //System.out.println(gson.toJson(utente));
            //System.out.println(gson.toJson(menu));
            String listaJSON = gson.toJson(lista);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(listaJSON);
            
        } else if (cmdMobile.equals("prova")) {
            System.out.println("*********** PROVAAAAAAAAAA");
            
        } else if (cmdMobile.equals("acquista")) {

            System.out.println("MOBILE_SERVLET [acquista]");
            
            String utenteJSON = request.getParameter("utente");
            utenteJSON = utenteJSON.replace("+", " ");
            String carrelloJSON = request.getParameter("carrello");
            carrelloJSON = carrelloJSON.replace("+", " ");
            String indirizzo = request.getParameter("indirizzo");
            indirizzo = indirizzo.replace("+", " ");
            String datetime = request.getParameter("datetime");                 // aaaa/mm/gg hh:mm:00
            datetime = datetime.replace("+", " ");
            
            Utente utente = gson.fromJson(utenteJSON, Utente.class);
            Carrello carrello = gson.fromJson(carrelloJSON, Carrello.class);
                        
            DBManager.acquista(carrello, utente, indirizzo, datetime);
            ArrayList<Ordine> elencoOrdini = DBManager.getOrdiniUtente(utente);
            boolean feedbackAcquista = true;
                        
            List lista = new ArrayList<>();
            lista.add(gson.toJson(elencoOrdini));
            lista.add(gson.toJson(feedbackAcquista));
                        
            String listaJSON = gson.toJson(lista);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(listaJSON);

        } else if (cmdMobile.equals("getOrdini")) {
            
            System.out.println("MOBILE_SERVLET [getOrdini]");
            
            String utenteJSON = request.getParameter("utente");
            utenteJSON = utenteJSON.replace("+", " ");
            Utente utente = gson.fromJson(utenteJSON, Utente.class);
            ArrayList<Ordine> elencoOrdini = DBManager.getOrdiniUtente(utente);
            List lista = new ArrayList<>();
            lista.add(gson.toJson(elencoOrdini));
            String listaJSON = gson.toJson(lista);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(listaJSON);    
                    
        }
        
        else if (cmdMobile.equals("rating")) {
            
            System.out.println("MOBILE_SERVLET [rating]");
            
            int idOrdine = Integer.parseInt(request.getParameter("idOrdine"));
            int rating = Integer.parseInt(request.getParameter("rating"));
            DBManager.inserisciValutazione(idOrdine, rating);
            boolean feedback = true;
            List lista = new ArrayList<>();
            lista.add(gson.toJson(feedback));
            String listaJSON = gson.toJson(lista);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(listaJSON);   
                                  
        }
        
        else if (cmdMobile.equals("annulla")){
            
            System.out.println("MOBILE_SERVLET [annulla]");
            
            int idOrdine = Integer.parseInt(request.getParameter("idOrdine"));
            System.out.println("ID ordine da annullare: " + idOrdine);
            DBManager.annullaOrdine(idOrdine);
            boolean feedback = true;
            List lista = new ArrayList<>();
            lista.add(gson.toJson(feedback));
            String listaJSON = gson.toJson(lista);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(listaJSON);   
            
        }
        
        else if (cmdMobile.equals("consegnato")){
            
            System.out.println("MOBILE_SERVLET [consegnato]");
            
            int idOrdine = Integer.parseInt(request.getParameter("idOrdine"));
            System.out.println("ID ordine consegnato: " + idOrdine);
            DBManager.avvenutaConsegna(idOrdine);
            boolean feedback = true;
            List lista = new ArrayList<>();
            lista.add(gson.toJson(feedback));
            String listaJSON = gson.toJson(lista);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(listaJSON);   
            
        }
        
        else if(cmdMobile.equals("getPizzePrenotateByIdOrdine")){
        
            System.out.println("MOBILE_SERVLET [getPizzePrenotateByIdOrdine]");
            
            int idOrdine = Integer.parseInt(request.getParameter("idOrdine"));
            ArrayList<PizzaPrenotata> elencoPizzePrenotate = DBManager.getElencoPizzeByIdOrdine(idOrdine);
            List lista = new ArrayList<>();
            lista.add(gson.toJson(elencoPizzePrenotate));
            String listaJSON = gson.toJson(lista);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(listaJSON);
            
        }else if (cmdMobile.equals("numOrdini")) {
            
            System.out.println("MOBILE_SERVLET [numOrdini]");
            
            String utenteJSON = request.getParameter("utente");
            utenteJSON = utenteJSON.replace("+", " ");
            Utente utente = gson.fromJson(utenteJSON, Utente.class);
            ArrayList<Ordine> elencoOrdini = DBManager.getOrdiniUtente(utente);
            List lista = new ArrayList<>();
            lista.add(""+elencoOrdini.size());
            String listaJSON = gson.toJson(lista);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(listaJSON);    
                    
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
