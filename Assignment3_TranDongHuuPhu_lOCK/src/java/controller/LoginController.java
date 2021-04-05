/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.UserDAO;
import dto.UserDTO;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import utils.CAPTCHAverify;

/**
 *
 * @author Admin
 */
public class LoginController extends HttpServlet {

    private static final String ERROR = "login.jsp";
    private static final String ADMIN = "admin.jsp";
    private static final String USER = "carPage.jsp";
    private static final String USER_NOT_CONFIRM = "confirm.jsp";
    private static final String AD = "AD";
    private static final String US = "US";
    private static final Logger LOG = Logger.getLogger(LoginController.class);

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
            String email = request.getParameter("txtEmail");
            String password = request.getParameter("txtPassword");
            UserDAO dao = new UserDAO();
            UserDTO user = dao.checkLogin(email, password);
            HttpSession session = request.getSession();
            if (user == null) {
                url = ERROR;
                request.setAttribute("LOGIN_ERROR", "Incorrect User ID or password!");

            } else {
                String gRecaptchaREsponse = request.getParameter("g-recaptcha-response");
                if (user.isStatus()) {
                    boolean loginSuccess = false;
                    loginSuccess = CAPTCHAverify.verify(gRecaptchaREsponse);
                    if (!loginSuccess) {
                        request.setAttribute("CAPTCHA_ERROR", "Captcha invalid!");
                    } else {
                        session.setAttribute("LOGIN_USER", user);
                        if (user.getRoleID().equals(AD)) {
                            url = ADMIN;
                        }
                        if (user.getRoleID().equals(US)) {
                            url = USER;
                        }
                    }
                } else {
                    url = USER_NOT_CONFIRM;
                    session.setAttribute("EMAIL", email);
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
