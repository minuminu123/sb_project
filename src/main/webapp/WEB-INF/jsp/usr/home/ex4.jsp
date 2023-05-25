<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<% %>
<!DOCTYPE html>
<%
List<String> imageUrls = (List<String>) request.getAttribute("imageUrls");
%>
<html>
<head>
    <meta charset="UTF-8">
    <title>Image URLs</title>
</head>
<body>
    <h1>Image URLs</h1>
    
    <% for (String imageUrl : imageUrls) { %>
        <img src="<%= imageUrl %>" alt="Image">
    <% } %>
</body>
</html>