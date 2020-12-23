<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    %>
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
            String nextPage = "../controller/modifyController.jsp";
            String messageNextPage = request.getParameter("message");
            if(customerBean==null || customerBean.getEmail()==""){ //Si no estamos loggeados debemos loggearnos
                nextPage="loginView.jsp";
            %>
                <jsp:forward page="<%=nextPage%>"></jsp:forward>
            <%
            }
            if(messageNextPage==null){
                messageNextPage="";
            }


       %>
        <p><%=messageNextPage%></p>
        <form action="../control/modifyController.jsp" method="post">
        	
            <label for="Nombre">Nombre</label>
            <input type="text" name="Nombre" size="30" value="<%=customerBean.getNombre()%>" required>
            <label for="Apellidos">Apellidos</label>
            <input type="text" name="Apellidos" size="100" value="<%=customerBean.getApellidos()%>" required>
            <label for="Fecha_Nacimiento">Fecha de nacimiento</label>
            <input type="date" name="Fecha_Nacimiento" value="<%=customerBean.getFechaNacimiento()%>" required>
        
        
            <p> Intereses </p>
     		    <label><input type="checkbox" name="interes" value="Lectura"> Lectura</label>
     		    <label><input type="checkbox" name="interes" value="Cine"> Cine</label>
     		    <label><input type="checkbox" name="interes" value="Deportes"> Deportes</label>
     		    <label><input type="checkbox" name="interes" value="Videojuegos"> Videojuegos</label>
     		    <label><input type="checkbox" name="interes" value="Música"> M&uacute;sica</label>
     		    <label><input type="checkbox" name="interes" value="Series"> Series</label>
     		    <label><input type="checkbox" name="interes" value="Programación"> Programaci&oacute;n</label>
     		    <label><input type="checkbox" name="interes" value="Fotografía"> Fotograf&iacute;a</label>
     		    <label><input type="checkbox" name="interes" value="Pintura"> Pintura</label>
     		    <label><input type="checkbox" name="interes" value="Baile"> Baile</label>
            
     		<br> <br>
            <input type="submit" value="Modificar Datos">   
          
        </form>
         
    </body>


</html>
