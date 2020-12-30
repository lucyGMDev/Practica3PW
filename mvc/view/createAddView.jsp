<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<jsp:useBean id="customerBean" scope="session" class="es.uco.pw.display.useBean.CustomerBean"></jsp:useBean>
<%@ page import="es.uco.pw.data.dao.Intereses.InteresesDAO"%>
<%@ page import="es.uco.pw.data.dao.Usuario.ContactoDAO"%>
<%@ page import="java.util.Hashtable"%>
<%@ page import="java.util.Enumeration"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.Date"%>
<%@ page import="java.text.SimpleDateFormat"%>
<!DOCTYPE html>
<html>
    <head>
        <script>
            function show(addType) {
                if(addType=="Tematico"){
                    var tags=document.getElementById("tags");
                    var endDate=document.getElementById("endDate");
                    
                    tags.className="show";
                    endDate.className="hidden";
                 
                }else if(addType=="Flash"){
                    var tags=document.getElementById("tags");
                    var endDate=document.getElementById("endDate");
                    
                    tags.className="hidden";
                    endDate.className="show";
                  
                }else{
                    var tags=document.getElementById("tags");
                    var endDate=document.getElementById("endDate");
                    
                    tags.className="hidden";
                    endDate.className="hidden";
                    
                }
            }

            function validacion(){
                var titulo=document.getElementById("titulo").value;
                titulo=titulo.trim();
                if(titulo==""){
                    alert("El titulo no puede estar vacío");
                    return false;
                }
                var cuerpoAnuncio=document.getElementById("cuerpoAnuncio").value;
                cuerpoAnuncio=cuerpoAnuncio.trim();
                if(cuerpoAnuncio==""){
                    
                    alert("El cuerpo del anuncio no puede estár vacío");
                    return false;
                }
                var tipoAnuncio=document.crearAnuncio.addType.value;
                
                if(tipoAnuncio=="General"){
                    
                }else if(tipoAnuncio=="Tematico"){
                    var temasAnuncio=document.getElementsByName("tags");
                    var contTemas=0;
                    
                    for(let i=0;i<temasAnuncio.length;i++){
                        if(temasAnuncio[i].checked){
                            contTemas++;
                        }
                    }

                    if(contTemas==0){
                        alert("Tienes que seleccionar al menos un tema en los anuncios temáticos");
                        return false;
                    }         

                }else if(tipoAnuncio=="Flash"){
                    
                    //Todo Hacer que la fecha de caducidad solo puede ser mayor 

                    
                }else if(tipoAnuncio=="Individualizado"){
                    var destinatarios=document.getElementsByName("destinatarios");
                    var contDestinatarios=0;
                    for(let i=0;i<destinatarios.length;i++){
                        if(destinatarios[i].checked){
                            contDestinatarios++;
                        }
                    }

                    if(contDestinatarios==0){
                        alert("En un anuncio individualizado, hay que seleccionar al menos un tema");
                        return false;
                    }
                }else{
                    alert("Debes seleccionar un tipo de anuncio");
                    return false;
                }
            
                var estadoAnuncio = document.crearAnuncio.estadoAnuncio.value;
                if(estadoAnuncio!= "editado" && estadoAnuncio!="publicado"){
                    alert("El estado de publicación no puede estar vacio");
                    return false;
                }

               
                
                return true;
            }

        </script>
        <link type="text/css" rel="stylesheet" href="/GestorAnuncios/includes/css/styles.css"/>
    </head>
    <body>
        <h1>Introduzca el anuncio</h1>
        <form method="post" action="/GestorAnuncios/createAdd" onsubmit="return validacion()" name="crearAnuncio">
            <p>
                <label for="title">Titulo<label>
                <input type="text" name="title" id="titulo"/>
            </p>
            <p>
                <label for="content" >Cuerpo del Anuncio<label><br/>
                <textarea name="content" id="cuerpoAnuncio" rows="10" cols="50"></textarea>
                
            </p>
            <p>
                <label for="addType">Tipo Anuncio<label><br/>
                
                <input type="radio" name="addType" value="General" onchange="show(this.value)" checked/>
                <label for="generic">General</lable><br/>
                
                <input type="radio" name="addType" value="Tematico" onchange="show(this.value)"/>
                <label for="Tematico">Tematico</lable><br/>
                <input type="radio" name="addType" value="Flash" onchange="show(this.value)"/>
                <label for="Flash">Flash</lable><br/>
                
                <input type="radio" name="addType" value="Individualizado" onchange="show(this.value)"/>
                <label for="Individualizado">Individual</lable><br/>           

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
                <%
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    String fechaMinimaString="";
                    Date fechaMinima=null;
        
                    try{
                        //Dia actual mas un dia
                        fechaMinimaString=sdf.format(new Date(new Date().getTime()+(1000*60*60*24)));
                        fechaMinima=sdf.parse(fechaMinimaString);
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                %>
                <input type="Date" id="endDate" name="endDate" min="<%=fechaMinimaString%>"/>

            </div>
            <div id="individualized"> 
            <%

                ContactoDAO contactoDAO= new ContactoDAO(dataBasePath);
                ArrayList<String> usuarios = contactoDAO.ObtenerEmailContactos();

            %>
                
                <label for="individualized">Destinatarios: </label><br/>
            <%
                for(String email : usuarios){
            %>
                    <input type="checkbox" value="<%=email%>" name="destinatarios"/><label><%=email%></label>
            <%
                }
            %>
                
            </div>
            <label for="estadoAnuncio">Estado del Anuncio: </label>
            <input type="radio" value="publicado" name="estadoAnuncio"/><label>Publicado</label> 
            <input type="radio" value="editado" name="estadoAnuncio" checked/><label>En Edicion</label>  
            <br/>
            <input type="submit" value="Crear Anuncio"/>
        </form>
    </body>
</html>