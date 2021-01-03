package mvc.control;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.Hashtable;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Date;

import es.uco.pw.business.Anuncios.EstadoAnuncio;
import es.uco.pw.business.Anuncios.TipoAnuncio;
import es.uco.pw.business.DTO.DTOAnuncio.AnuncioDTO;
import es.uco.pw.data.dao.Anuncios.AnuncioDAO;
import es.uco.pw.display.useBean.CustomerBean;

public class homePageServlets extends HttpServlet{
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
    {
        String dataBasePath= request.getServletContext().getInitParameter("AbsolutePath")+request.getServletContext().getInitParameter("sqlProperties");
        AnuncioDAO anuncioDAO = new AnuncioDAO(dataBasePath);
        Hashtable<Integer, AnuncioDTO> anuncios = anuncioDAO.ObtenerAnuncios();
        Enumeration enumAnuncios=anuncios.elements();
        int numAnuncios=20;
        int cont=0;
        ArrayList<AnuncioDTO> listaAnuncios=new ArrayList<AnuncioDTO>();
        
        HttpSession session=request.getSession();
        CustomerBean customerBean = (CustomerBean)session.getAttribute("customerBean");
        
        
        //Si no estas logeado mostramos anuncios generales que puede ver cualquier usuario
        if(customerBean ==null || customerBean.getEmail() == ""){
            while(cont<numAnuncios && enumAnuncios.hasMoreElements()){
                AnuncioDTO nextAnuncio=(AnuncioDTO)enumAnuncios.nextElement();
                if(nextAnuncio.getTipo()!=TipoAnuncio.Tematico && nextAnuncio.getTipo()!=TipoAnuncio.Individualizado){
                    //Si el anuncio no esta publicado no se muestra 
                    if(nextAnuncio.getEstadoAnuncio() != EstadoAnuncio.publicado){
                        continue;
                    }
                    
                    //Si el anuncio ha caducado no se muestra y se archiva
                    if(nextAnuncio.getTipo()==TipoAnuncio.Flash && nextAnuncio.getEstadoAnuncio() != EstadoAnuncio.archivado && nextAnuncio.getFechaFin().before(new Date())){ 
                        anuncioDAO.ArchivarAnuncio(nextAnuncio.getId());
                        continue; 
                    }
                    listaAnuncios.add(nextAnuncio);
                    cont++;
                    
                }
            }
        }else{//Si esta loggeado mostramos anuncios personalizados para el usuario
            while(cont<numAnuncios && enumAnuncios.hasMoreElements()){
                AnuncioDTO nextAnuncio=(AnuncioDTO)enumAnuncios.nextElement();
                if(nextAnuncio.getEstadoAnuncio() != EstadoAnuncio.publicado){
                    continue;
                }
                if(nextAnuncio.getTipo()==TipoAnuncio.Flash && nextAnuncio.getEstadoAnuncio() != EstadoAnuncio.archivado && nextAnuncio.getFechaFin().before(new Date())){ 
                    anuncioDAO.ArchivarAnuncio(nextAnuncio.getId());
                    continue; 
                }
                if(nextAnuncio.getEmailPropietario()!=customerBean.getEmail()){
                    if(nextAnuncio.getTipo()==TipoAnuncio.Tematico){
                        boolean usuarioSubscritoTema=false;
                        ArrayList<String> interesesUsuario=customerBean.getIntereses();
                        for(String interes : interesesUsuario){
                            if(nextAnuncio.getTemas().contains(interes)){
                                usuarioSubscritoTema=true;
                                break;
                            }
                        }

                        if(!usuarioSubscritoTema) continue;

                    }
                    if(nextAnuncio.getTipo()==TipoAnuncio.Individualizado){
                        if(!nextAnuncio.getDestinatarios().contains(customerBean.getEmail())){
                            continue;
                        }
                    }
                }

                listaAnuncios.add(nextAnuncio);
                cont++;
            }
        } 
        request.setAttribute("listaAnuncios",listaAnuncios);
        RequestDispatcher disp = request.getRequestDispatcher("index.jsp");
        disp.include(request,response);
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
    {
        doGet(request, response);
    }
}
