/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.CarDAO;
import dao.OrderDAO;
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
public class ConfirmOrderController extends HttpServlet {

    private static final String SUCCESS = "order.jsp";
    private static final String ERROR = "cart.jsp";
    final private int AFTER = 1;
    final private int BEFORE = 0;
    private static final Logger LOG = Logger.getLogger(ConfirmOrderController.class);

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
            String code = request.getParameter("txtCodeDiscount");
            Float totalPrice = Float.parseFloat(request.getParameter("txtTotalPrice"));
            HttpSession session = request.getSession();
            CartDTO cart = (CartDTO) session.getAttribute("CART");
            UserDTO user = (UserDTO) session.getAttribute("LOGIN_USER");
            if (user.getEmail() == null) {
                url = ERROR;
            }
            if (code == "") {
                code = "noDiscount";
            }
            if (cart != null) {
                CarDAO dao = new CarDAO();
                boolean check = true;
                for (CarDTO dto : cart.getCart().values()) {
                    int remainQuantity = dao.getQuantity(dto.getCarID()) - dao.calculateQuantityOrder(Date.valueOf(dto.getStartDate()), Date.valueOf(dto.getEndDate()), dto.getCarID());
                    if (remainQuantity - dto.getQuantity() < 0) {
                        check = false;
                    }
                }
                for (CarDTO car1 : cart.getCart().values()) {
                    for (CarDTO car2 : cart.getCart().values()) {
                        if (car1.getCarID().equalsIgnoreCase(car2.getCarID()) && !car1.getCode().equalsIgnoreCase(car2.getCode())) {
                            if (Date.valueOf(car1.getStartDate()).getTime() <= Date.valueOf(car2.getEndDate()).getTime()
                                    && Date.valueOf(car1.getEndDate()).getTime() >= Date.valueOf(car2.getStartDate()).getTime()) {
                                int totalCar = car1.getQuantity() + car2.getQuantity();
                                if (totalCar > dao.getQuantity(car1.getCarID())){
                                    check = false;
                                }
                            }
                        }
                    }
                }

                if (check) {
                    OrderDAO order = new OrderDAO();
                    if (order.createOrder(cart, code, totalPrice)) {
                        request.setAttribute("ORDER", "Your order is Confirmed!");
                        session.removeAttribute("CART");
                        url = SUCCESS;
                    } else {
                        request.setAttribute("ERROR_ORDER", "Your order is Error!");
                    }

                } else {
                    request.setAttribute("ERROR_ORDER", "We don't have enough number of quantity! Please check again!");
                }
            }

        } catch (Exception e) {
            LOG.error(e);
        } finally {
            request.getRequestDispatcher(url).forward(request, response);
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
