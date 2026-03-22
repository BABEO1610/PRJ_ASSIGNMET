<%-- 
    Document   : payment-result
    Created on : Mar 3, 2026, 5:05:45 PM
    Author     : ADMIN
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>

<title>Kết quả thanh toán</title>

<link rel="stylesheet" href="assets/payment-result.css">

</head>

<body>

<div class="container">

    <div class="result-box">

        <%
            String message = (String) request.getAttribute("message");

            boolean success = false;

            if(message != null && message.contains("thành công")){
                success = true;
            }
        %>

        <div class="icon <%= success ? "success" : "fail" %>">
            <%= success ? "✔" : "✖" %>
        </div>

        <div class="message">
            ${message}
        </div>

        <a class="btn-back" href="user-bills">
            Quay lại trang thanh toán
        </a>

    </div>

</div>

</body>
</html>
