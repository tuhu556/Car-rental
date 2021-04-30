/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dto.CarDTO;
import dto.CartDTO;
import dto.UserDTO;
import java.io.IOException;
import java.sql.Date;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;

/**
 *
 * @author Admin
 */
public class AddCartController extends HttpServlet {

    private static final String SUCCESS = "cart-page";
    private static final String ERROR = "login-page";
    private static final Logger LOG = Logger.getLogger(AddCartController.class);

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
        String url = ERROR;
        try {
            String carID = request.getParameter("txtCarID");
            String carName = request.getParameter("txtCarName");
            String category = request.getParameter("categoryCar");
            String color = request.getParameter("txtColor");
            String image = request.getParameter("txtImage");
            int year = Integer.parseInt(request.getParameter("txtYear"));
            float price = Float.parseFloat(request.getParameter("txtPrice"));
            String startDate = request.getParameter("start");
            String endDate = request.getParameter("end");
            String code = carID + "+++" + startDate + "+++" + endDate;
            HttpSession session = request.getSession();
            CartDTO cart = (CartDTO) session.getAttribute("CART");
            UserDTO user = (UserDTO) session.getAttribute("LOGIN_USER");
            if (user.getEmail() == null) {
                url = ERROR;
            }
            Date start = Date.valueOf(startDate);
            Date end = Date.valueOf(endDate);
            long days = end.getTime() - start.getTime();
            int totalDays = (int) (days / 86400000) + 1;
            if (user.getRoleID().equals("US") && user.isStatus()) {
                if (cart == null) {
                    cart = new CartDTO(user.getEmail(), null);
                }
                cart.add(new CarDTO(carID, carName, category, price, 1, year, color, image, true, startDate, endDate, totalDays, code));
                session.setAttribute("CART", cart);
                url = SUCCESS;
            } else {
                url = ERROR;
            }

            url = SUCCESS;

        } catch (Exception e) {
            LOG.error(e);
        } finally {
            response.sendRedirect(url);
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
