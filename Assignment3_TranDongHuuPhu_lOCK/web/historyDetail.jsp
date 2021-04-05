<%-- 
    Document   : historyDetail
    Created on : Mar 4, 2021, 8:26:42 AM
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Order History</title>
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
            <c:set var="list" value="${requestScope.DETAIL}"></c:set>
            <c:if test="${not empty list}">
                <table class="table table-striped" border="1">
                    <thead>
                        <tr>
                            <th>NO.</th>
                            <th>Order ID</th>
                            <th>Car ID</th>
                            <th>Car Name</th>
                            <th>Image</th>
                            <th>Quantity</th>
                            <th>Price</th>
                            <th>Start Date</th>
                            <th>End Date</th>
                            <th>Total Days</th>
                            <th>Feedback</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="history" items="${list}" varStatus="counter">
                            <tr>
                                <td>${counter.count}</td>
                                <td>${history.orderID}</td>
                                <td>${history.carID}</td>
                                <td>${history.carName}</td>
                                <td><img src="${history.image}" width="310px" height="150px"></td>
                                <td>${history.quantity}</td>
                                <td>${history.price}</td>
                                <td>${history.startDate}</td>
                                <td>${history.endDate}</td>
                                <td>${history.totalDays}</td>
                        <form action="FeedbackController" method="POST">
                            <input type="hidden" name="carID" value="${history.carID}">
                            <td><button type="submit" class="btn btn-success">Feedback</button></td>
                        </form>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </c:if> 
            <c:if test="${empty list}">
                <div>No result!</div>
            </c:if>
            <form action="ViewHistoryController" method="POST">
                <button type="submit" class="btn btn-info">Back</button>
            </form>
        </main>
    </body>
</html>
