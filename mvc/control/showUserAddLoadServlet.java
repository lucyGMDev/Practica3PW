package mvc.control;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.ArrayList;

import es.uco.pw.business.Anuncios.EstadoAnuncio;
import es.uco.pw.business.DTO.DTOAnuncio.AnuncioDTO;
import es.uco.pw.data.dao.Anuncios.AnuncioDAO;
import es.uco.pw.display.useBean.CustomerBean;

public class showUserAddLoadServlet extends HttpServlet{
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
    {
        HttpSession session=request.getSession();
        CustomerBean customerBean = (CustomerBean)session.getAttribute("customerBean");
        if(customerBean == null || customerBean.getEmail()==""){//Si no está loggeado debe logguearse
            response.sendRedirect("/GestorAnuncios/mvc/view/loginView.jsp");
        }

        String dataBasePath= request.getServletContext().getInitParameter("AbsolutePath")+request.getServletContext().getInitParameter("sqlProperties");
        AnuncioDAO anuncioDAO = new AnuncioDAO(dataBasePath);
        ArrayList<AnuncioDTO> anunciosUsuario = anuncioDAO.ObtenerAnunciosUsuario(customerBean.getEmail());
        ArrayList<AnuncioDTO> listaAnuncios = new ArrayList<AnuncioDTO>();

        for(AnuncioDTO anuncio : anunciosUsuario)
        {
            if(anuncio.getEstadoAnuncio()!= EstadoAnuncio.enEspera)
            {
                listaAnuncios.add(anuncio);
            }
        }       

        request.setAttribute("listaAnuncios",listaAnuncios);
        RequestDispatcher disp = request.getRequestDispatcher("mvc/view/showUserAddView.jsp");
        disp.forward(request,response);

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
    {
        doGet(request, response);
    }
}
