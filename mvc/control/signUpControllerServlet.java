package mvc.control;



import es.uco.pw.display.useBean.CustomerBean;
import es.uco.pw.data.dao.Usuario.ContactoDAO;
import es.uco.pw.business.Usuario.Contacto;
import es.uco.pw.business.DTO.DTOUsuario.ContactoDTO;


import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.*;


@WebServlet(name="signUpController", urlPatterns="/signUpController")


public class signUpControllerServlet extends HttpServlet{

 protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException

{

response.setContentType("text/html;charset=UTF-8");


String nextPage="../../index.jsp";
String messageNextPage="";
String dataBasePath=request.getServletContext().getInitParameter("AbsolutePath")+request.getServletContext().getInitParameter("sqlProperties");


HttpSession session = request.getSession();

CustomerBean customerBean=(CustomerBean)session.getAttribute("customerBean");


    if(customerBean==null || customerBean.getEmail()==""){
        String email =request.getParameter("Email");
        if(email!=null){
            ContactoDAO contactoDAO=new ContactoDAO(dataBasePath);
            Contacto contact = contactoDAO.ObtenerContactoById(email);
            if(contact==null){
                String password=request.getParameter("password");
                String again_password=request.getParameter("again_password");
                if(again_password.equals(password)){
                    String nombre=request.getParameter("Nombre");
                    String apellidos=request.getParameter("Apellidos");
                    String fechaString = request.getParameter("Fecha_Nacimiento");
                    java.text.SimpleDateFormat format = new java.text.SimpleDateFormat("yyyy-MM-dd");
                    java.util.Date fecha=null;
                    try{
                        fecha=format.parse(fechaString);

                    }

                    catch(Exception e)
                    {
                        System.out.println("Error al obtener la fecha");

                    }
                    
                    String[] intereses = request.getParameterValues("interes");
                    java.util.ArrayList<String> listaIntereses=new java.util.ArrayList<String>();
                    if(intereses!=null){
                        for(int i=0;i<intereses.length;i++){
                            listaIntereses.add(intereses[i]);
                        }
                    }
                    ContactoDTO contactoDTO = new ContactoDTO(email,nombre,apellidos,fecha,listaIntereses);
                    contactoDAO.InsertarContacto(contactoDTO,password);
                    
                                        
                 customerBean.setEmail(email);
                     customerBean.setContrasena(password);
                     customerBean.setNombre(contactoDTO.getName());
                    customerBean.setApellidos(contactoDTO.getLastName());
                   customerBean.setFechaNacimiento(contactoDTO.getBirthDate());
                    customerBean.setIntereses(contactoDTO.getTagsLists());

                }else{
                    messageNextPage="Las contraseñas no coinciden";
                    nextPage="../view/signUpView.jsp";
                    RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("../view/signUpView.jsp");
                dispatcher.forward(request, response);
                }
            }else{
                messageNextPage="Ya hay un usuario con el email introducido";
                nextPage="../view/signUpView.jsp";
                RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("../view/signUpView.jsp");
                dispatcher.forward(request, response);
            }
        }else{
           
            nextPage="../view/signUpView.jsp";
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("../view/signUpView.jsp");
            dispatcher.forward(request, response);
        }
    }



RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("../../index.jsp");
                dispatcher.forward(request, response);


}


}
