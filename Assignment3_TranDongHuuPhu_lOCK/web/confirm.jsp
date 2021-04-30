<%-- 
    Document   : confirm
    Created on : Feb 25, 2021, 9:20:09 PM
    Author     : Admin
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Confirm Email</title>
        <link rel="stylesheet" href="css/login.css">
        <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.15.1/css/all.css" integrity="sha384-vp86vTRFVJgpjF9jiIGPEEqYqlDwgyBgEF109VFjmqGmIY/Y4HV4d3Gp2irVfcrp" crossorigin="anonymous">
    </head>
    <body>
        <section class="signup">
            <div class="container">
                <div class="signup-content">
                    <div class="signup-form">
                        <a href="carPage">Back to home page</a>
                        <h2 class="form-title">Confirm Code</h2>
                        <p>Your Account has been created, We have sent code to your email, Please check it out!</p>
                        <form action="ConfirmCodeEmail" method="POST" class="register-form" id="register-form">
                            <input type="hidden" value="${sessionScope.EMAIL}" name="email">
                            <div class="form-group">
                                <label for="name"><i class="fas fa-user"></i></label>
                                <input type="text" name="txtCode" id="name" placeholder="Your Code" required/>
                            </div>
                            <p class="text-danger">${requestScope.ERROR_CODE}</p>
                            <input type="submit" name="btnAction" value="Submit" id="signin" class="form-submit"/>
                        </form>
                        <form action="ResendCodeEmail" method="POST">
                            <input type="hidden" value="${sessionScope.EMAIL}" name="email">
                            <input type="submit" name="btnAction" value="Resend" id="signin" class="form-submit"/>
                        </form>
                    </div>
                    <div class="signup-image">
                        <figure><img src="image/car2.jpg" alt="sing up image"></figure>
                    </div>

                </div>
            </div>
        </section>
    </body>
</html>
