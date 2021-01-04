package mvc.control;

import javax.servlet.*;
import javax.servlet.http.*;

import es.uco.pw.business.DTO.DTOAnuncio.AnuncioDTO;
import es.uco.pw.data.dao.Anuncios.AnuncioDAO;
import es.uco.pw.display.useBean.CustomerBean;

import java.io.IOException;

public class modifyAddLoadServlet extends HttpServlet{
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
    {
        HttpSession session=request.getSession();
        CustomerBean customerBean = (CustomerBean)session.getAttribute("customerBean");
        if(customerBean == null || customerBean.getEmail()==""){//Si no está loggeado debe logguearse
            response.sendRedirect("/GestorAnuncios/mvc/view/loginView.jsp");
        }
        String idAnuncioString=request.getParameter("idAnuncio");
        if(idAnuncioString==null){
            RequestDispatcher disp = request.getRequestDispatcher("/showUserAddLoad");
            disp.forward(request,response);
        }
        Integer idAnuncio = Integer.parseInt(idAnuncioString);
        
        String dataBasePath= request.getServletContext().getInitParameter("AbsolutePath")+request.getServletContext().getInitParameter("sqlProperties");
        AnuncioDAO anuncioDAO = new AnuncioDAO(dataBasePath);
        AnuncioDTO anuncio = anuncioDAO.ObtenerAnuncioID(idAnuncio);

        //Si se le pasa el id de un anuncio que no te pertenece se muestra la pagina para ver tus anuncios
        if(anuncio.getEmailPropietario()!=customerBean.getEmail()){
            RequestDispatcher disp = request.getRequestDispatcher("/homePage");
            disp.forward(request,response);
        }
        
        request.setAttribute("anuncio",anuncio);
        RequestDispatcher disp = request.getRequestDispatcher("mvc/view/modifyAddView.jsp");
        disp.forward(request,response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
    {
        doGet(request, response);
    }
}
