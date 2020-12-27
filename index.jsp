<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%-- <%@ page errorPage="includes/errorPage.jsp"%> --%>
<jsp:useBean id="customerBean" scope="session" class="es.uco.pw.display.useBean.CustomerBean"></jsp:useBean>


<!DOCTYPE html>
<html>
    <head>
        <title>Control de Usuarios</title>
        <meta charset="UTF-8"/>
        
    </head>
    <body>
        
        <h1>Bienvenido</h1>
        <%
            String message = request.getParameter("message");
            String reset = request.getParameter("reset");
            if(message!=null){
        %>
            <p><%= message %></p>
            
        <%


            }
            if(reset!=null){
                customerBean.setEmail("");
                
            }
            if(customerBean!=null && !customerBean.getEmail().equals("")){
        %>
                
                Email: <%=customerBean.getEmail()%></br>
                Contraseña: <%=customerBean.getContraseña()%></br>
                Nombre: <%=customerBean.getNombre()%></br>
                Apellidos: <%=customerBean.getApellidos()%></br>
                Fecha: <%=customerBean.getFechaNacimiento()%></br>
                Lista Intereses: <%=customerBean.getIntereses()%></br>
                <a href="/GestorAnuncios/mvc/view/modifyView.jsp">Modificar usuario</a><br/>
                <a href="/GestorAnuncios/mvc/view/createAddView.jsp">Crear Anuncio</a><br/>
                <a href="/GestorAnuncios/index.jsp?reset=1">LogOut</a><br/>
                
        <%      
            }
            else{
        %>

          <a href="/GestorAnuncios/mvc/view/loginView.jsp">Loggin</a></br>
          <a href="/GestorAnuncios/mvc/view/signUpView.jsp">Registrarse</a></br>
        <%
            }
        %>
    </body>
</html>
