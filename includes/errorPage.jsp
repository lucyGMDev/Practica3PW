<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page isErrorPage="true" %>

<!DOCTYPE html>
<html>
    <head>
        <title>ErrorPage</title>
    </head>
    <body>
        <p>Se ha producido el siguiente error</p>
        <%=exception%>
        <a href="/ControlContactos/index.jsp?reset=1">Volver al index</a>
        
    </body>
</html>
