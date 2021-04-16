/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.CarDAO;
import dto.CarDTO;
import java.io.IOException;
import java.sql.Date;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

/**
 *
 * @author Admin
 */
public class SearchController extends HttpServlet {

    private static final String SUCCESS = "carPage.jsp";
    private static final String ERROR = "carPage.jsp";
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
            String search = request.getParameter("txtSearch");
            String index = request.getParameter("index");
            String category = request.getParameter("txtCategory");
            int amount = Integer.parseInt(request.getParameter("amount"));
            String startDate = request.getParameter("startDate");
            String endDate = request.getParameter("endDate");
            Date start = Date.valueOf(startDate);
            Date end = Date.valueOf(endDate);
            if (search == null) {
                search = "";
            }
            if (category.equalsIgnoreCase("all")) {
                category = "";
            }
            if (index == null) {
                index = "1";
            }
            if (Date.valueOf(endDate).getTime() - Date.valueOf(startDate).getTime() <= 0) {
                request.setAttribute("ERROR_DATE", "End Date cannot less than or equal to Start Date");
            } else {
                int indexPage = Integer.parseInt(index);
                CarDAO dao = new CarDAO();

                List<CarDTO> list = dao.Search(indexPage, start, end, search, category, amount);
                if (list != null) {
                    for (CarDTO car : list) {
                        int count = dao.calculateQuantityOrder(start, end, car.getCarID());
                        car.setQuantity(car.getQuantity() - count);
                        if (car.getQuantity() <= 0) {
                            list.remove(car.getCarID());
                        }
                    }
                    int totalPages = dao.countCar(start, end, search, category, amount);
                    request.setAttribute("LIST", list);
                    request.setAttribute("categoryID", category);
                    request.setAttribute("PAGE", totalPages);
                    request.setAttribute("SEARCH", search);
                    request.setAttribute("AMOUNT", amount);
                    request.setAttribute("START", startDate);
                    request.setAttribute("END", endDate);
                    url = SUCCESS;
                } else {
                    request.setAttribute("ERROR", "No results were found!");
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
