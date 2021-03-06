<%-- 
    Document   : history
    Created on : Mar 4, 2021, 8:26:21 AM
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Order History</title>
        <link rel="stylesheet" type="text/css" href="<c:url value="css/homePage.css"/>">
        <!-- JavaScript Bundle with Popper -->
        
        <script src="/j<c:url value="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dists/bootstrap.bundle.min.js"/>" integrity="sha384-ygbV9kiqUc6oa4msXn9868pTtWMgiQaeYH7/t7LECLbyPA2x65Kgf80OJFdroafW" crossorigin="anonymous"></script>
        <!-- CSS only -->
        <link href="<c:url value="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/css/bootstrap.min.css"/>" rel="stylesheet" integrity="sha384-giJF6kkoqNQ00vy+HMDP7azOuL0xtbfIcaT9wjKHr8RbDVddVHyTfAAsrekwKmP1" crossorigin="anonymous">
    </head>
    <body>
        <header>
            <jsp:include page="menu.jsp"/>
        </header>
        <main>
            <center>
                <form action="SearchOrder" method="POST">
                    <input type="date" placeholder="Search" name="search">
                    <button class="btn btn-outline-success my-2 my-sm-0" type="submit">Search</button>
                </form>
            </center>
            <center><h2>${requestScope.ERROR_LIST}</h2></center>
                    <c:set var="list" value="${requestScope.HISTORY}"></c:set>
                    <c:if test="${not empty list}">
                <table class="table table-striped" border="1">
                    <thead>
                        <tr>
                            <th>NO.</th>
                            <th>Order ID</th>
                            <th>Total Price</th>
                            <th>Order Date</th>
                            <th>Discount Code</th>
                            <th>Detail</th>
                            <th>Delete</th>
                        </tr>
                    </thead>
                    <tbody>
                    <center><h2>${requestScope.CANCEL_ORDER}</h2></center>
                            <c:forEach var="history" items="${list}" varStatus="counter">
                        <tr>
                            <td>${counter.count}</td>
                            <td>${history.orderID}</td>
                            <td>${history.totalPrice}</td>
                            <td>${history.orderDate}</td>
                            <td>${history.discountCode}</td>
                        <form action="ViewHistoryDetail" method="POST">
                            <input type="hidden" name="orderID" value="${history.orderID}">
                            <td><button type="submit" class="btn btn-warning">Detail</button></td>
                        </form>
                        <script>
                            function accept() {
                                if (confirm("Are you sure to cancel this order?") === true) {
                                    document.getElementById('submit').submit();
                                }
                            }
                        </script>
                        <td><button onclick="accept()" class="btn btn-danger">Cancel Order</button></td>
                        <form action="CancelOrder" method="POST" id="submit">
                            <input type="hidden" name="orderID" value="${history.orderID}">
                        </form>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </c:if> 
            <c:if test="${empty list}">
                <div>Nothing is here!</div>
                <div><a href="carPage">Shopping</a></div>
            </c:if>
        </main>
    </body>
</html>
