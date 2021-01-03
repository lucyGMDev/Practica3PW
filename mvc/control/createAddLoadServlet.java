package mvc.control;

import java.util.ArrayList;
import java.util.Hashtable;
import java.io.IOException;
import javax.servlet.*;
import javax.servlet.http.*;

import es.uco.pw.data.dao.Intereses.InteresesDAO;
import es.uco.pw.data.dao.Usuario.ContactoDAO;
import es.uco.pw.display.useBean.CustomerBean;

public class createAddLoadServlet extends HttpServlet{
    public void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
    {
        String dataBasePath= request.getServletContext().getInitParameter("AbsolutePath")+request.getServletContext().getInitParameter("sqlProperties");
        HttpSession session=request.getSession();
        CustomerBean customerBean = (CustomerBean)session.getAttribute("customerBean");
        if(customerBean == null || customerBean.getEmail() == ""){ //Si no estas logueado tienes que loguearte
            response.sendRedirect("/GestorAnuncios/mvc/view/loginView.jsp");
        }

        InteresesDAO interesesDAO = new InteresesDAO(dataBasePath);
        Hashtable<Integer,String> intereses = interesesDAO.DevolverIntereses();

        ContactoDAO contactoDAO = new ContactoDAO(dataBasePath);
        ArrayList<String> usuarios = contactoDAO.ObtenerEmailContactos();

        request.setAttribute("intereses",intereses);
        request.setAttribute("usuarios",usuarios);
        RequestDispatcher disp = request.getRequestDispatcher("mvc/view/createAddView.jsp");
        disp.include(request,response);

    }
}
