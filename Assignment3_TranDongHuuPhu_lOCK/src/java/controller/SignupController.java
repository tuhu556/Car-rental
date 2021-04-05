/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.UserDAO;
import dto.UserDTO;
import dto.UserErrorDTO;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import utils.MailUtils;

/**
 *
 * @author Admin
 */
public class SignupController extends HttpServlet {

    private static final String SUCCESS = "confirm.jsp";
    private static final String ERROR = "signup.jsp";
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
        boolean check = true;
        try {
            String email = request.getParameter("txtEmail");
            String name = request.getParameter("txtName");
            String address = request.getParameter("txtAddress");
            String phone = request.getParameter("txtPhone");
            String password = request.getParameter("txtPassword");
            String confirm = request.getParameter("txtConfirm");
            UserDAO dao = new UserDAO();
            UserErrorDTO error = new UserErrorDTO("", "", "", "", "", "");

            if (!dao.checkEmail(email)) {
                check = false;
                error.setEmailError("This Email is already exist--Please try another Email");
            }
            if (!email.trim().equals("")) {
                if (!email.matches("(\\w*\\d*)+@(\\w+\\.\\w+){1}(\\.\\w+)?")) {
                    check = false;
                    error.setEmailError("Wrong format Email!");
                }
            }
            if (!phone.trim().equals("")) {
                if (phone.length() > 14) {
                    check = false;
                    error.setPhoneError("Max Length is 14");
                }
            }

            if (!password.trim().equals("")) {
                if (password.length() < 3 || password.length() > 30) {
                    check = false;
                    error.setPasswordError("Password length from 3 to 30");
                }
                if (!confirm.trim().equals("")) {
                    if (!confirm.equals(password)) {
                        check = false;
                        error.setConfirmError("Confirm does not match with password! Please try again!");
                    }
                }

                if (check) {
                    MailUtils mail = new MailUtils();
                    String code = mail.getAlphaNumericString(6);
                    UserDTO dto = new UserDTO(email, password, name, phone, address, "US", false, code);
                    dao.create(dto);
                    mail.sendMail(email, code);
                    HttpSession session = request.getSession();
                    session.setAttribute("EMAIL", email);
                    url = SUCCESS;
                } else {
                    request.setAttribute("ERROR", error);
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
