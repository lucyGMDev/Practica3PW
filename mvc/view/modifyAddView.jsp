<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ page import="es.uco.pw.business.DTO.DTOAnuncio.AnuncioDTO"%>
<%@ page import="es.uco.pw.business.Anuncios.TipoAnuncio"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="java.util.Date"%>


<jsp:useBean id="customerBean" scope="session" class="es.uco.pw.display.useBean.CustomerBean"></jsp:useBean>


<!DOCTYPE html>
<html>
    <head>
        <title>Modificar Anuncio</title>
        <meta charset="UTF-8"/>
        <%
            AnuncioDTO anuncio = (AnuncioDTO)request.getAttribute("anuncio");
            if(anuncio==null){
        %>
                <jsp:forward page="/showUserAddLoad"></jsp:forward>
        <%
            }
        %>
    </head>
    <body>
        <form>
            <label for="titulo">Titulo: </label>
            <input type="text" name="titulo" value="<%=anuncio.getTitulo()%>"/><br/>

            <label for="cuerpo">Cuerpo Anuncio</label><br>
            <textarea name="cuerpo" rows=10 cols=30><%=anuncio.getCuerpo()%></textarea><br/>
            
        <%
            if(anuncio.getTipo()==TipoAnuncio.Flash){
        %>
            <label for="fechaCaducidad">Fecha caducidad:</label>
        <%
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String fechaCaducidadActual = sdf.format(anuncio.getFechaFin());
            String fechaMinimaString="";
            fechaMinimaString=sdf.format(new Date(new Date().getTime()+(1000*60*60*24)));
        %>
            <input type="date" name="fechaCaducidad" value="<%=fechaCaducidadActual%>" min="<%=fechaMinimaString%>"/>

        <%
            }    
        %>
            



        </form>
    </body>
</html>