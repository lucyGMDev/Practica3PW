<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<jsp:useBean id="customerBean" scope="session" class="es.uco.pw.display.useBean.CustomerBean"></jsp:useBean>
<jsp:useBean id="errorLoginBean" scope="session" class="es.uco.pw.display.useBean.ErrorLoginBean"></jsp:useBean>
<%@ page import="es.uco.pw.data.dao.Usuario.ContactoDAO"%>
<%@ page import="es.uco.pw.business.Usuario.Contacto"%>
<%

    String nextPage= "../../index.jsp";
    String messageNextPage="";
    String dataBasePath=application.getInitParameter("AbsolutePath")+application.getInitParameter("sqlProperties");
    String email=request.getParameter("Email");
    String contraseña=request.getParameter("password");

    if(customerBean== null || customerBean.getEmail().equals("")){
        if(email!=null){
           
            
            ContactoDAO contactoDAO = new ContactoDAO(dataBasePath);
            Contacto contact = contactoDAO.ObtenerContactoById(email);
            if(contact!=null){//El contacto existe
                String contraseña_usuario=contactoDAO.ObtenerPasswordUsuario(contact.getEmail());
                if(contraseña_usuario.equals(contraseña)){
                    errorLoginBean.setNumerosIntentos(3);
%> 
                    <jsp:setProperty property="email" value="<%=email%>" name="customerBean"/>
                    <jsp:setProperty property="contraseña" value="<%=contraseña%>" name="customerBean"/>
                    <jsp:setProperty property="nombre" value="<%=contact.getName()%>" name="customerBean"/>
                    <jsp:setProperty property="apellidos" value="<%=contact.getLastName()%>" name="customerBean"/>
                    <jsp:setProperty property="fechaNacimiento" value="<%=contact.getBirthDate()%>" name="customerBean"/>
                    <jsp:setProperty property="intereses" value="<%=contact.getTagsLists()%>" name="customerBean"/>
<%
                }else{
                    int numeroIntentos=errorLoginBean.getNumerosIntentos()-1;
                    if(numeroIntentos>0){
                    nextPage="../view/loginView.jsp";
                    
                    messageNextPage="La contraseña es incorrecta, te quedan "+numeroIntentos+" intentos</br>";
                    }else{
                        nextPage=application.getInitParameter("PaginaRedireccionErrorLogin");
                        errorLoginBean.setNumerosIntentos(3);
                        response.sendRedirect(nextPage);
                    }
                    errorLoginBean.setNumerosIntentos(numeroIntentos);
                }                
            }else{
                int numeroIntentos=errorLoginBean.getNumerosIntentos()-1;
                nextPage="../view/loginView.jsp";
                if(numeroIntentos>0){
                    nextPage="../view/loginView.jsp";
                    
                    messageNextPage="No existe ningun contacto con email "+email+ " te quedan "+numeroIntentos+" intentos</br>";
                }else{
                    nextPage=application.getInitParameter("PaginaRedireccionErrorLogin");
                    errorLoginBean.setNumerosIntentos(3);
                    response.sendRedirect(nextPage);
                }
                errorLoginBean.setNumerosIntentos(numeroIntentos);
 
            }
        }else{
            nextPage="../view/loginView.jsp";
        }
    }
%>

<jsp:forward page="<%=nextPage%>">
	<jsp:param value="<%=messageNextPage%>" name="message"/>
</jsp:forward>