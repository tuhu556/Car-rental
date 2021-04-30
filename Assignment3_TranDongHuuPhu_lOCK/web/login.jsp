<%-- 
    Document   : login
    Created on : Feb 16, 2021, 7:39:44 PM
    Author     : Admin
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login Page</title>
        <link rel="stylesheet" href="css/login.css">
        <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.15.1/css/all.css" integrity="sha384-vp86vTRFVJgpjF9jiIGPEEqYqlDwgyBgEF109VFjmqGmIY/Y4HV4d3Gp2irVfcrp" crossorigin="anonymous">
        <!-- reCAPTCHA script with English language -->
        <script src="https://www.google.com/recaptcha/api.js"></script>

    </head>
    <body>
       
        <main>
            <section class="sign-in">
            <div class="container">
                <div class="signin-content">
                    <div class="signin-image">
                        <figure><img src="image/car1.jpg" alt="sing up image"></figure>
                        <a href="signup-page" class="signup-image-link">Create an account</a>
                        <a href="carPage" class="signup-image-link">Back to Home Page</a>
                    </div>

                    <div class="signin-form">
                        <h2 class="form-title">Sign in</h2>
                        <form action="Login" method="POST" class="register-form" id="login-form">
                            <p class="text-danger" >
                                ${requestScope.LOGIN_ERROR}
                                ${requestScope.RE_LOGIN}
                            </p>
                            <br>
                            <div class="form-group">
                                <label for="your_name"><i class="fas fa-user"></i></label>
                                <input type="text" name="txtEmail" id="your_name" placeholder="Your Email" required/>
                            </div>
                            <div class="form-group">
                                <label for="your_pass"><i class="fas fa-key"></i></label>
                                <input type="password" name="txtPassword" id="your_pass" placeholder="Password" required/>
                            </div>
                            <div class="form-group form-button">
                                <div class="g-recaptcha" data-sitekey="6Ld-12saAAAAAF8aSfY8eHOfvUFJXyj5N0QixKoA"></div>
                                <div class="text-danger text-bold">${requestScope.CAPTCHA_ERROR}</div>

                                <input type="submit" name="btnAction" value="Login" id="signin" class="form-submit"/>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </section>
        </main>
    </body>
</html>
