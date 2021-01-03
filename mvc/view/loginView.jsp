<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%-- <%@ page errorPage="/includes/errorPage.jsp"%> --%>
<jsp:useBean id="customerBean" scope="session" class="es.uco.pw.display.useBean.CustomerBean"></jsp:useBean>
<!DOCTYPE html>
<html lang="es">
    <head>
        <title>Inicio de sesi&oacute;n</title>
        <meta charset="UTF-8"/>
        <style>
        label{
            display: inline-block;
            width: 80em;
        }   
        </style>
    </head>
    <body>
       <%
            String nextPage = "/GestorAnuncios/loginController";
            String messageNextPage = request.getParameter("message");
            if(customerBean!=null && customerBean.getEmail()!=""){
                nextPage="/homePage";
            %>
                <jsp:forward page="<%=nextPage%>"></jsp:forward>
            <%
            }
            if(messageNextPage==null){
                messageNextPage="";
            }


       %>
       <%=messageNextPage%><br/>
        <form action="/GestorAnuncios/loginController" method="post">
        	<label for="Email">Email</label>
            <input type="text" name="Email" size="50" required>
            <br/>
            <label for="password">Contrase&ntilde;a</label>
            <input type="password" name="password" size="50" required>
     		<br/> <br/>
            <input type="submit" value="Iniciar sesi&oacute;n">   
          
        </form>
          
    </body>


</html>
