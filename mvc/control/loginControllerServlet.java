package mvc.control;



import es.uco.pw.display.useBean.CustomerBean;
import es.uco.pw.display.useBean.ErrorLoginBean;
import es.uco.pw.data.dao.Usuario.ContactoDAO;
import es.uco.pw.business.Usuario.Contacto;


import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.*;


@WebServlet(name="loginController", urlPatterns="/loginController")


public class loginControllerServlet extends HttpServlet{

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
    {


        response.setContentType("text/html;charset=UTF-8");




        String nextPage= "/GestorAnuncios/homePage";
        String messageNextPage="";
        String dataBasePath= request.getServletContext().getInitParameter("AbsolutePath")+request.getServletContext().getInitParameter("sqlProperties");
        String email=request.getParameter("Email");
        String contrasena=request.getParameter("password");


        HttpSession session = request.getSession();


        CustomerBean customerBean=(CustomerBean)session.getAttribute("customerBean");
        ErrorLoginBean errorLoginBean=(ErrorLoginBean)session.getAttribute("errorLoginBean");
        if(errorLoginBean==null){
            errorLoginBean = new ErrorLoginBean();
        }
        if(customerBean== null || customerBean.getEmail().equals("")){
            if(email!=null){
                ContactoDAO contactoDAO = new ContactoDAO(dataBasePath);
                Contacto contact = contactoDAO.ObtenerContactoById(email);
                if(contact!=null){//El contacto existe
                    String contrasena_usuario=contactoDAO.ObtenerPasswordUsuario(contact.getEmail());
                    if(contrasena_usuario.equals(contrasena)){
                        errorLoginBean.setNumerosIntentos(3);


                        customerBean.setEmail(email);
                        customerBean.setContrasena(contrasena);
                        customerBean.setNombre(contact.getName());
                        customerBean.setApellidos(contact.getLastName());
                        customerBean.setFechaNacimiento(contact.getBirthDate());
                        customerBean.setIntereses(contact.getTagsLists());
                        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/homePage");
                        dispatcher.forward(request, response);
                        

                    }else{
                        int numeroIntentos=errorLoginBean.getNumerosIntentos()-1;
                        if(numeroIntentos>0){
                            nextPage="../view/loginView.jsp";
                            
                            messageNextPage="La contraseña es incorrecta, te quedan "+numeroIntentos+" intentos</br>";
                        }else{
                            nextPage=request.getServletContext().getInitParameter("PaginaRedireccionErrorLogin");
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
                        nextPage=request.getServletContext().getInitParameter("PaginaRedireccionErrorLogin");
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
