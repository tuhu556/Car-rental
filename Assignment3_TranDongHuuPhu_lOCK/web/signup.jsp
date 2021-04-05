<%-- 
    Document   : signup
    Created on : Jan 26, 2021, 5:28:08 AM
    Author     : Admin
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Sign up Page</title>
        <link rel="stylesheet" href="css/login.css">
        <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.15.1/css/all.css" integrity="sha384-vp86vTRFVJgpjF9jiIGPEEqYqlDwgyBgEF109VFjmqGmIY/Y4HV4d3Gp2irVfcrp" crossorigin="anonymous">
    </head>
    <body>
        <section class="signup">
            <div class="container">
                <div class="signup-content">
                    <div class="signup-form">
                        <h2 class="form-title">Sign up</h2>
                        <form action="SignupController" method="POST" class="register-form" id="register-form">
                            <c:set var="error" value="${requestScope.ERROR}"></c:set>
                                <div class="form-group">
                                    <label for="name"><i class="fas fa-user"></i></label>
                                    <input type="text" name="txtName" id="name" placeholder="Your Name" value="${param.txtName}" required/>
                                </div>
                                <p class="text-danger">${error.nameError}</p>
                            <div class="form-group">
                                <label for="email"><i class="fas fa-envelope"></i></label>
                                <input type="email" name="txtEmail" id="email" placeholder="Your Email" value="${param.txtEmail}" required/>
                            </div>
                            <div class="form-group">
                                <label for="phone"><i class="fas fa-envelope"></i></label>
                                <input type="phone" name="txtPhone" id="phone" placeholder="Your Phone" value="${param.txtPhone}" required/>
                            </div>
                            <p class="text-danger">${error.phoneError}</p>
                            <div class="form-group">
                                <label for="address"><i class="fas fa-envelope"></i></label>
                                <input type="text" name="txtAddress" id="address" placeholder="Your Address" value="${param.txtAddress}" required/>
                            </div>
                            <p class="text-danger">${error.emailError}</p>
                            <div class="form-group">
                                <label for="pass"><i class="fas fa-key"></i></label>
                                <input type="password" name="txtPassword" id="pass" placeholder="Password"  value="${param.txtPassword}"required/>
                            </div>
                            <p class="text-danger">${error.passwordError}</p>

                            <div class="form-group">
                                <label for="re-pass"><i class="fas fa-unlock-alt"></i></label>
                                <input type="password" name="txtConfirm" id="re_pass" placeholder="Repeat your password" required/>
                            </div>
                            <p class="text-danger">${error.confirmError}</p>
                            <div class="form-group form-button">
                                <input type="submit" name="btnAction" id="signup" class="form-submit" value="Register"/>
                            </div>
                        </form>
                    </div>
                    <div class="signup-image">
                        <figure><img src="image/car2.jpg" alt="sing up image"></figure>
                        <a href="login.jsp" class="signup-image-link">I am already member</a>
                    </div>
                </div>
            </div>
        </section>
    </body>
</html>
