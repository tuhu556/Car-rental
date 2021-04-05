<%-- 
    Document   : feedback
    Created on : Mar 5, 2021, 10:32:14 PM
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Feedback</title>
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
            <center><h2>${requestScope.ERROR_LIST}</h2></center>
                    <c:set var="list" value="${requestScope.FEEDBACK}"></c:set>
                    <c:if test="${not empty list}">
                <table class="table table-striped" border="1">
                    <thead>
                        <tr>
                            <th>Feedback ID</th>
                            <th>Car ID</th>
                            <th>Content</th>
                            <th>Rating</th>
                            <th>Create Date</th>
                        </tr>
                    </thead>
                    <tbody>
                    <center><h2>${requestScope.CANCEL_ORDER}</h2></center>
                            <c:forEach var="fb" items="${list}" varStatus="counter">
                        <tr>
                            <td>${fb.feedbackID}</td>
                            <td>${fb.carID}</td>
                            <td>${fb.content}</td>
                            <td>${fb.rating}/10</td>
                            <td>${fb.createDate}</td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </c:if> 
            <c:if test="${empty list}">
                <div class="col-md-7 col-lg-8">
                    <form action="SubmitFeedbackController" method="POST">
                        <input type="hidden" name="carID" value="${requestScope.CARID}">
                        <div class="col-12">
                            <label for="text" class="form-label">Content <span class="text-muted">(Optional)</span></label>
                            <input type="text" class="form-control" name="content" placeholder="content">
                        </div>
                        <div class="col-12">
                            <label for="number" class="form-label">Rating(0-10) <span class="text-muted">(Optional)</span></label>
                            <input type="number" class="form-control" name="rating" min="0" max="10" required="">
                        </div>
                        <button class="w-100 btn btn-primary btn-lg" type="submit">Submit Feedback</button>
                    </form>
                </div>
            </c:if>
            <form action="ViewHistoryController" method="POST">
                <button type="submit" class="btn btn-info">Back</button>
            </form>
        </main>
    </body>
</html>
