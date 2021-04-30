<%-- 
    Document   : cart
    Created on : Feb 25, 2021, 8:51:57 PM
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Shopping Cart</title>
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
            <c:set var="cart" value="${sessionScope.CART}"/>
            <c:set var="totalPrice" value="${0}"/>
            <c:set var="discountValue" value="${requestScope.VALUE}"/>
            <c:if test="${not empty cart.getCart()}">
                <form action="Discount" method="POST">
                    Discount Code: <input type="text" name="txtDiscount" value="${param.txtDiscount}"/>
                    <button type="submit" class="btn btn-secondary">Select</button>
                    <div>${requestScope.DISCOUNT_NAME}</div>
                    <div>${requestScope.ERROR_DIS}</div>
                </form>
                <table class="table table-striped" border="1">
                    <thead>
                        <tr>
                            <th>NO.</th>
                            <th>Car ID</th>
                            <th>Car Name</th>
                            <th>category ID</th>
                            <th>Year</th>
                            <th>Color</th>
                            <th>Image</th>
                            <th>Start Date</th>
                            <th>End Date</th>
                            <th>Total Days</th>
                            <th>Quantity</th>
                            <th>Price</th>
                            <th>Update</th>
                            <th>Delete</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="order" items="${cart.getCart()}" varStatus="counter">
                        <form action="Cart" method="POST">
                            <p class="text-danger" role="alert">${requestScope.ERROR_CART}</p>
                            <tr>
                            <input type="hidden" name="txtCarID" value="${order.value.carID}"/>
                            <input type="hidden" name="txtCode" value="${order.value.code}"/>
                            <input type="hidden" name="start" value="${order.value.startDate}"/>
                            <input type="hidden" name="end" value="${order.value.endDate}"/>
                            <td>${counter.count}</td>
                            <td>${order.value.carID}</td>
                            <td>${order.value.carName}</td>
                            <td>${order.value.categoryID}</td>
                            <td>${order.value.year}</td>
                            <td>${order.value.color}</td>
                            <td><img class="pic-1" src="${order.value.image}" width="310px" height="150px"></td>
                            <td>${order.value.startDate}</td>
                            <td>${order.value.endDate}</td>
                            <td>${order.value.totalDays}</td>
                            <td><input type="number"  name="txtQuantity" value="${order.value.quantity}" min="1" /></td>
                            <td>${order.value.price}</td>
                            <td>
                                <input type="submit" name="btnAction" value="Update"/>
                            </td>
                            <td>
                                <input type="submit" name="btnAction" value="Delete"/>
                            </td>
                            </tr>
                        </form>
                        <c:set var="totalPrice" value="${(totalPrice + order.value.price*order.value.quantity*order.value.totalDays)}"/>
                    </c:forEach>
                    </tbody>
                </table>
                <div>
                    <c:set var="totalPrice" value="${totalPrice - (totalPrice*(discountValue/100))}"/>
                    <h2>TOTAL PRICE: <c:out value="${totalPrice}"/></h2>
                    <input type="hidden" value="${totalPrice}" name="totalPrice">
                </div>
                <div><p class="text-danger" role="alert">${requestScope.ERROR_ORDER}</p></div>
                    <c:if test="${requestScope.ERROR_CART == NULL}">
                    <form action="ConfirmOrder" method="POST">
                        <input type="hidden" value="${requestScope.CODE}" name="txtCodeDiscount">
                        <input type="hidden" value="${totalPrice}" name="txtTotalPrice">
                        <button type="submit" class="btn btn-success">Confirm Order</button>
                    </form>
                </c:if>
            </c:if> 
            <c:if test="${empty cart.getCart()}">
                <div>Nothing is here!</div>
                <div><a href="carPage">Shopping</a></div>
            </c:if>
        </main>
    </body>
</html>
