<%-- 
    Document   : menu
    Created on : Feb 25, 2021, 10:11:31 PM
    Author     : Admin
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<nav class="navbar navbar-dark bg-dark" aria-label="First navbar example">
    <div class="container-fluid">
        <a class="navbar-brand" href="carPage">Car rental: ${sessionScope.LOGIN_USER.email}</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarsExample01" aria-controls="navbarsExample01" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>

        <div class="collapse navbar-collapse" id="navbarsExample01">
            <ul class="navbar-nav me-auto mb-2">
                <li class="nav-item active">
                    <a class="nav-link" aria-current="page" href="carPage">Home</a>
                    <c:if test="${sessionScope.LOGIN_USER==NULL}">
                        <a class="nav-link" aria-current="page" href="login-page">Login</a>
                    </c:if> 
                    <a class="nav-link" aria-current="page" href="cart-page">Cart</a>
                    <c:if test="${sessionScope.LOGIN_USER!=NULL}">
                        <a class="nav-link" aria-current="page" href="ViewHistory">History Order</a>
                        <a class="nav-link" aria-current="page" href="Logout">Logout</a>
                    </c:if>
                </li>
            </ul>
        </div>
    </div>
</nav>
