<%-- 
    Document   : carPage
    Created on : Feb 16, 2021, 8:34:23 PM
    Author     : Admin
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Car rental</title>
        <link rel="stylesheet" type="text/css" href="css/homePage.css">
        <!-- JavaScript Bundle with Popper -->
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/js/bootstrap.bundle.min.js" integrity="sha384-ygbV9kiqUc6oa4msXn9868pTtWMgiQaeYH7/t7LECLbyPA2x65Kgf80OJFdroafW" crossorigin="anonymous"></script>
        <!-- CSS only -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-giJF6kkoqNQ00vy+HMDP7azOuL0xtbfIcaT9wjKHr8RbDVddVHyTfAAsrekwKmP1" crossorigin="anonymous">
    </head>
    <body>
        <header>
            <jsp:include page="menu.jsp"/>
        </header>
        <main>
            <c:set var="car" value="${requestScope.LIST}"/>
            <c:set var="categoryMap" value="${sessionScope.CATEGORY}"/> 
            <center>
                <form action="Search">
                    Car's Name: <input type="text" name="txtSearch" value="${param.txtSearch}"/>
                    Category: <select id="categoryCmb" name="txtCategory">
                        <option value="all">All</option>
                        <c:forEach var="entry" items="${categoryMap}">
                            <option value="${entry.key}" <c:if test="${requestScope.categoryID eq entry.key}"> selected</c:if>>${entry.value}</option>
                        </c:forEach>
                    </select>
                    <br>
                    Amount <input type="number" name="amount" min="0" value="${param.amount}" required=""/>
                    <br>
                    Start Date:<input type="date" name="startDate" value="${param.startDate}"  min="${sessionScope.START_DATE}" required=""/>
                    End Date:<input type="date" name="endDate" value="${param.endDate}"min="${sessionScope.START_DATE}" required=""/>
                    <button type="submit" class="btn btn-info">Select</button>
                </form> 
                    <h2>${requestScope.ERROR_DATE}</h2>
            </center>
            <div class="album py-5 bg-light">
                <center><h2>${requestScope.ERROR}</h2></center>
                <div class="container">
                    <div class="row row-cols-1 row-cols-sm-2 row-cols-md-3 g-3">
                        <c:forEach var="c" items="${car}">
                            <form action="AddCart" method="POST">
                                <div class="col">
                                    <div class="card shadow-sm">
                                        <img class="pic-1" src="${c.image}" width="410px" height="250px">

                                        <div class="card-body">
                                            <p class="card-text">${c.carName} - Color: ${c.color} - Year: ${c.year} - Price:${c.price}$</p>
                                            <input type="hidden" value="${c.carID}" name="txtCarID">
                                            <input type="hidden" value="${c.carName}" name="txtCarName">
                                            <input type="hidden" value="${c.categoryID}" name="categoryCar">
                                            <input type="hidden" value="${c.image}" name="txtImage">
                                            <input type="hidden" value="${c.color}" name="txtColor">
                                            <input type="hidden" value="${c.year}" name="txtYear">
                                            <input type="hidden" value="${c.price}" name="txtPrice">
                                            <input type="hidden" name="start" value="${requestScope.START}">
                                            <input type="hidden" name="end" value="${requestScope.END}">
                                            <div class="d-flex justify-content-between align-items-center">
                                                <div class="btn-group">
                                                    <button type="submit" class="btn btn-sm btn-outline-secondary">Add to Cart</button>
                                                </div>
                                                <small class="text-muted">Quantity: ${c.quantity}</small>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </form>
                        </c:forEach>
                    </div>
                </div>
            </div>
            <div>
                <!---------paging-------->

                <c:if test="${requestScope.PAGE != 1}">
                    <div class="paging">
                        <nav aria-label="Page navigation example">
                            <ul class="pagination justify-content-center">
                                <c:forEach begin="1" end="${requestScope.PAGE }" var="i">
                                    <c:url var="page" value="Search">
                                        <c:param name="txtSearch" value="${requestScope.SEARCH}"></c:param>
                                        <c:param name="txtCategory" value="${requestScope.categoryID}"></c:param>
                                        <c:param name="startDate" value="${requestScope.START}"></c:param>
                                        <c:param name="endDate" value="${requestScope.END}"></c:param>
                                        <c:param name="amount" value="${requestScope.AMOUNT}"></c:param>
                                        <c:param name="index" value="${i}"></c:param>
                                    </c:url>
                                    <li class="page-item"><a class="page-link" href="${page}">${i}</a></li>
                                    </c:forEach>
                            </ul>
                        </nav>
                    </div>
                </c:if>
            </div>
        </main>
    </body>
</html>
