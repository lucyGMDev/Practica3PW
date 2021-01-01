<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%-- <%@ page errorPage="includes/errorPage.jsp"%> --%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="es.uco.pw.business.DTO.DTOAnuncio.AnuncioDTO"%>
<%@ page import="es.uco.pw.business.Anuncios.TipoAnuncio"%>
<jsp:useBean id="customerBean" scope="session" class="es.uco.pw.display.useBean.CustomerBean"></jsp:useBean>



<!DOCTYPE html>
<html>
    <head>
        <title>Control de Usuarios</title>
        <meta charset="UTF-8"/>

        <%
            //Compruebo si se quiere cerrar el usuario, si ese es el caso se cierra la sesión y se vuelve a llamar
            //al /homePage para que muestre solo los anuncios que pueden ver cualquier usuario
            String reset = request.getParameter("reset");
            if(reset!=null){
                customerBean.setEmail("");
                
            %>
                <%-- <jsp:forward page="/homePage"></jsp:forward> --%>
            <%
            }
        %>
        <%
        ArrayList<AnuncioDTO> anuncios = (ArrayList<AnuncioDTO>)request.getAttribute("listaAnuncios");
        if(anuncios==null){
            %>
                <p>La lista de anuncios está vacia</p>
                <jsp:forward page="/homePage"></jsp:forward>
            <%
        }   
        %>
        
    </head>
    <body>
    
        
        <h1>Bienvenido</h1>
        <%
            String message = request.getParameter("message");
            
            if(message!=null){
        %>
            <p><%= message %></p>
            
        <%


            }
        %>
        <%
            if(customerBean!=null && !customerBean.getEmail().equals("")){
        %>
                
                Email: <%=customerBean.getEmail()%></br>
                Contraseña: <%=customerBean.getContrasena()%></br>
                Nombre: <%=customerBean.getNombre()%></br>
                Apellidos: <%=customerBean.getApellidos()%></br>
                Fecha: <%=customerBean.getFechaNacimiento()%></br>
                Lista Intereses: <%=customerBean.getIntereses()%></br>
                <a href="/GestorAnuncios/mvc/view/modifyView.jsp">Modificar usuario</a><br/>
                <a href="/GestorAnuncios/mvc/view/createAddView.jsp">Crear Anuncio</a><br/>
                <a href="/GestorAnuncios/index.jsp?reset=1">LogOut</a><br/>
                <hr/>

            
        <%      
            }
            else{
        %>

          <a href="/GestorAnuncios/mvc/view/loginView.jsp">Loggin</a></br>
          <a href="/GestorAnuncios/mvc/view/signUpView.jsp">Registrarse</a></br>
        <%
            }
        %>
        <%
            if(anuncios!=null){
                for(AnuncioDTO add : anuncios){
        %>
                    <div>
                        <p><%=add.getTitulo()%></p>
                        <p><%=add.getCuerpo()%></p>
                        <p>Propietario: <%=add.getEmailPropietario()%></p>
                        <p>Tipo Anuncio: <%=add.getTipo().toString()%></p>
                        <p>Fecha publicación: <%=add.getFechaPublicacion()%></p>
        <%
                        if(add.getTipo()==TipoAnuncio.Flash){
        %>
                            <p>Fecha caducidad: <%=add.getFechaFin()%></p>
        <%
                        }
        %>
        <%
                        if(add.getTipo()==TipoAnuncio.Tematico){
                            for(String tema : add.getTemas()){
        %>
                                <p><%=tema%></p>
        <%
                            }
                        }
        %>
                        <hr/>
                    </div>
        <%
                }
            }

        %>  
    </body>
</html>
