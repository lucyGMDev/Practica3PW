<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ page import="java.util.ArrayList"%>
<%@ page import="es.uco.pw.business.DTO.DTOAnuncio.AnuncioDTO"%>
<%@ page import="es.uco.pw.business.Anuncios.TipoAnuncio"%>
<%@ page import="es.uco.pw.business.Anuncios.EstadoAnuncio"%>

<jsp:useBean id="customerBean" scope="session" class="es.uco.pw.display.useBean.CustomerBean"></jsp:useBean>

<!DOCTYPE html>
<html>
    <head>
        <title>Modificar anuncios</title>
        <meta charset="UTF-8"/>
        <%
            ArrayList<AnuncioDTO> anunciosUsuario = (ArrayList<AnuncioDTO>)request.getAttribute("listaAnuncios");
            if(anunciosUsuario == null){
                %>
                    <jsp:forward page="/showUserAddLoad"></jsp:forward>
                <%
            }
        %>
    </head>
    <body>
        <%
            for(AnuncioDTO add : anunciosUsuario)
            {
                %>
                   <div>
                        <p><%=add.getTitulo()%></p>
                        <p><%=add.getCuerpo()%></p>
                        <p>Propietario: <%=add.getEmailPropietario()%></p>
                        <p>Tipo Anuncio: <%=add.getTipo().toString()%></p>
                        <p>Fecha publicación: <%=add.getFechaPublicacion()%></p>
                        <p>Estado Anuncio: <%=add.getEstadoAnuncio()%></p>
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
                        <a href="/GestorAnuncios/modifyAddLoad?idAnuncio=<%=add.getId()%>">Modificar Anuncio</a>
                    <%
                        if(add.getEstadoAnuncio()==EstadoAnuncio.publicado){
                    %>
                            <a href="#">Archivar Anuncio</a>
                    <%
                        }
                    %>
                    <%
                        if(add.getEstadoAnuncio()==EstadoAnuncio.editado){
                    %>
                            <a href="#">Publicar anuncio</a>
                    <%
                        }
                    %>
                        <hr/>
                    </div>
                <%
            }
        %>
    </body>
</html>