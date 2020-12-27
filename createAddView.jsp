<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<jsp:useBean id="customerBean" scope="session" class="es.uco.pw.display.useBean.CustomerBean"></jsp:useBean>
<%@ page import="es.uco.pw.data.dao.Intereses.InteresesDAO"%>
<%@ page import="es.uco.pw.data.dao.Usuario.ContactoDAO"%>
<%@ page import="java.util.Hashtable"%>
<%@ page import="java.util.Enumeration"%>
<%@ page import="java.util.ArrayList"%>
<!DOCTYPE html>
<html>
    <head>
        <script>
            function show(addType) {
                if(addType=="tematic"){
                    var tags=document.getElementById("tags");
                    var endDate=document.getElementById("endDate");
                    var individualized=document.getElementById("individualized");
                    tags.className="show";
                    endDate.className="hidden";
                    individualized.className="hidden";
                }else if(addType=="flash"){
                    var tags=document.getElementById("tags");
                    var endDate=document.getElementById("endDate");
                    var individualized=document.getElementById("individualized");
                    tags.className="hidden";
                    endDate.className="show";
                    individualized.className="hidden";
                }else if(addType=="individualized"){
                    var tags=document.getElementById("tags");
                    var endDate=document.getElementById("endDate");
                    var individualized=document.getElementById("individualized");
                    tags.className="hidden";
                    endDate.className="hidden";
                    individualized.className="show";
                    
                }else{
                    var tags=document.getElementById("tags");
                    var endDate=document.getElementById("endDate");
                    var individualized=document.getElementById("individualized");
                    tags.className="hidden";
                    endDate.className="hidden";
                    individualized.className="hidden";
                }
            }
        </script>
        <link type="text/css" rel="stylesheet" href="/ControlContactos/includes/css/styles.css"/>
    </head>
    <body>
        <h1>Introduzca el anuncio</h1>
        <form method="post" action="/ControlContactos/createAdd">
            <p>
                <label for="title">Titulo<label>
                <input type="text" name="title"/>
            </p>
            <p>
                <label for="content">Cuerpo del Anuncio<label>
                <input type="textarea" name="content"/>
            </p>
            <p>
                <label for="addType">Tipo Anuncio<label><br/>
                
                <input type="radio" name="addType" value="generic" onchange="show(this.value)" checked/>
                <label for="generic">General</lable><br/>
                
                <input type="radio" name="addType" value="tematic" onchange="show(this.value)"/>
                <label for="tematic">Tematico</lable><br/>
                
                <input type="radio" name="addType" value="flash" onchange="show(this.value)"/>
                <label for="flash">Flash</lable><br/>
                
                <input type="radio" name="addType" value="individualized" onchange="show(this.value)"/>
                <label for="individualized">Individual</lable><br/>           

            </p>
            <div id="tags" class="hidden">
                <% 
                    String dataBasePath=application.getInitParameter("AbsolutePath")+application.getInitParameter("sqlProperties");
                    InteresesDAO interesesDAO= new InteresesDAO(dataBasePath);
                    Hashtable<Integer,String> temas= interesesDAO.DevolverIntereses();
                %>
                <label for="tags">Temas</label><br/>
                <%
                    Enumeration enumeration = temas.elements();
                    Enumeration claves=temas.keys();
                    while(enumeration.hasMoreElements() && claves.hasMoreElements()){
                        %>
                        <input type="checkbox" value="<%=claves.nextElement()%>" name="tags"/><label><%=enumeration.nextElement()%></label>
                        <%
                    }
                %>
                
                

            </div>
            <div id="endDate" class="hidden">
                <label for="endDate">Fecha Caducidad</label>
                <input type="Date" name="endDate"/>

            </div>
            <div id="individualized" class="hidden"> 
            <%

                ContactoDAO contactoDAO= new ContactoDAO(dataBasePath);
                ArrayList<String> usuarios = contactoDAO.ObtenerEmailContactos();

            %>
                
                <label for="individualized">Individualizado</label><br/>
            <%
                for(String email : usuarios){
            %>
                    <input type="checkbox" value="<%=email%>"/><label><%=email%></label>
            <%
                }
            %>
                
            </div>
            <label for="estadoAnuncio">Estado del Anuncio: </label>
            <input type="radio" value="publicado" name="estadoAnuncio"/><label>Publicado</label> 
            <input type="radio" value="editado" name="estadoAnuncio"/><label>En Edicion</label>  
            <br/>
            <input type="submit" value="Crear Anuncio"/>
        </form>
    </body>
</html>