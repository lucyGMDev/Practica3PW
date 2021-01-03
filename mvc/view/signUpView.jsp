<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ page import="java.util.Enumeration" %>


<%-- <%@ page errorPage="/includes/errorPage.jsp"%> --%>

<jsp:useBean id="customerBean" scope="session" class="es.uco.pw.display.useBean.CustomerBean"></jsp:useBean>
<!DOCTYPE html>
<html lang="es">
    <head>
        <title>P&aacute;gina de registro</title>
        <meta charset="UTF-8"/>
        <style>
            label
            {
                display: inline-block;
                width: 80em;
            }
        </style>
    </head>
    <body>
        <%
            String nextPage = "../controller/signUpController.jsp";
            String messageNextPage = request.getParameter("message");
            if(customerBean!=null && customerBean.getEmail()!=""){
                nextPage="../../index.jsp";
            %>
                <jsp:forward page="<%=nextPage%>"></jsp:forward>
            <%
            }
            if(messageNextPage==null){
                messageNextPage="";
            }


       %>
       <%=messageNextPage%><br/>
        <form action="../control/signUpController.jsp" method="post">
        	<label for="Email">Email</label>
            <input type="text" name="Email" size="50" required>
            <label for="password">Contrase&ntilde;a</label>
            <input type="password" name="password" size="50" required>
            <label for="again_password">Repita la contrase&ntilde;a</label>
            <input type="password" name="again_password" size="50" required>
            <label for="Nombre">Nombre</label>
            <input type="text" name="Nombre" size="30" required>
            <label for="Apellidos">Apellidos</label>
            <input type="text" name="Apellidos" size="100" required>
            <label for="Fecha_Nacimiento">Fecha de nacimiento</label>
            <input type="date" name="Fecha_Nacimiento" required>
            <p> Intereses </p>
     		    <%
                
                Enumeration elementos= (Enumeration)request.getAttribute("elementos");

                
                Enumeration claves= (Enumeration)request.getAttribute("claves");

                if (elementos == null || claves == null)
                {

                    %>

                    <jsp:forward page="/signUpLoad"></jsp:forward>


                    <%
                }
              
                %>
                
               <%

               while (claves.hasMoreElements())
               {
                    %>
                <label><input type="checkbox" name="interes" value="<%=claves.nextElement()%>"><%=elementos.nextElement()%></label>
                <br>
           <%
      }
    %>
            
     		<br> <br>
            <input type="submit" value="Registrarse">   
          
        </form>
          
    </body>


</html>
