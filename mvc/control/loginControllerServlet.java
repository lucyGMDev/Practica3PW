package es.uco.pw.controllers;



import es.uco.pw.display.useBean.CustomerBean;
import es.uco.pw.display.useBean.ErrorLoginBean;
import es.uco.pw.data.dao.Usuario.ContactoDAO;
import es.uco.pw.business.Usuario.Contacto;


import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.*;


/**
 * Servlet implementation class LoginController
 */

public class loginControllerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public loginControllerServlet() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
 {


response.setContentType("text/html;charset=UTF-8");




String nextPage= "../../index.jsp";
String messageNextPage="";
String dataBasePath=application.getInitParameter("AbsolutePath")+application.getInitParameter("sqlProperties");
String email=request.getParameter("Email");
String contraseña=request.getParameter("password");


HttpSession session = request.getSession();


CustomerBean customerBan=(CustomerBean)session.getAttribute("customerBean");
ErrorLoginBean errorLoginBean=(ErrorLoginBean)session.getAttribute("errorLoginBean");





    if(customerBean== null || customerBean.getEmail().equals("")){
        if(email!=null){
           
          


            ContactoDAO contactoDAO = new ContactoDAO(dataBasePath);
            Contacto contact = contactoDAO.ObtenerContactoById(email);
            if(contact!=null){//El contacto existe
                String contraseña_usuario=contactoDAO.ObtenerPasswordUsuario(contact.getEmail());
                if(contraseña_usuario.equals(contraseña)){
                    errorLoginBean.setNumerosIntentos(3);


                     session.setAttribute("email", email);
                    session.setAttribute("contraseña",contraseña);
                    session.setAttribute("nombre",contact.getName());
                   session.setAttribute("apellidos",contact.getLastName());
                   session.setAttribute("fechaNacimiento",contact.getBirthDate());
                    session.setAttribute("intereses",contact.getTagsLists());

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

                     RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("../view/loginView.jsp");
                     dispatcher.forward(request, response);


                }else{
                    nextPage=application.getInitParameter("PaginaRedireccionErrorLogin");
                    errorLoginBean.setNumerosIntentos(3);
                    response.sendRedirect(nextPage);
                }
                errorLoginBean.setNumerosIntentos(numeroIntentos);

                RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("../view/loginView.jsp");
                dispatcher.forward(request, response);
 
            }
        }else{
             RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("../view/loginView.jsp");
    dispatcher.forward(request, response);
        
}



}

}
    
    
    
}




