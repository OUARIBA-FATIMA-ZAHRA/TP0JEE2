<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Inventory Management</title>
</head>
<body>
    <h1>Welcome to Inventory Management System</h1>
    <p>Application is running successfully!</p>
    <ul>
        <li><a href="${pageContext.request.contextPath}/categories">Manage Categories</a></li>
        <li><a href="${pageContext.request.contextPath}/suppliers">Manage Suppliers</a></li>
    </ul>
</body>
</html>