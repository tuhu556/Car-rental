/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.CarDAO;
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
public class UpdateCartController extends HttpServlet {

    private static final String SUCCESS = "cart.jsp";
    private static final String ERROR = "login.jsp";
    private static final Logger LOG = Logger.getLogger(SearchController.class);

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
            String startDate = request.getParameter("start");
            String endDate = request.getParameter("end");
            String code = carID + "+++" + startDate + "+++" + endDate;
            Date start = Date.valueOf(startDate);
            Date end = Date.valueOf(endDate);
            HttpSession session = request.getSession();
            UserDTO user = (UserDTO) session.getAttribute("LOGIN_USER");
            if (user.getRoleID().equals("US") && user.isStatus()) {
                int quantity = Integer.parseInt(request.getParameter("txtQuantity"));
                if (quantity > 0) {
                    CartDTO cart = (CartDTO) session.getAttribute("CART");
                    if (cart != null) {
                        CarDTO dto = new CarDTO();
                        CarDAO dao = new CarDAO();
                        int remainQuantity = dao.getQuantity(carID)-dao.calculateQuantityOrder(start, end, carID);
                        if (remainQuantity - quantity < 0) {
                            String error = "This product " + carID + " is out of stock or over available quantity";
                            request.setAttribute("ERROR_CART", error);
                        }
                        for (CarDTO car : cart.getCart().values()) {
                            if (car.getCode().equals(code)) {
                                dto = new CarDTO(carID, car.getCarName(), car.getCategoryID(), car.getPrice(), quantity, car.getYear(), car.getColor(), car.getImage(), true, car.getStartDate(), car.getEndDate(), car.getTotalDays(), code);
                                break;
                            }
                        }
                        cart.update(dto);
                        session.setAttribute("CART", cart);
                        url = SUCCESS;
                    }
                }
            } else {
                url = ERROR;
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
