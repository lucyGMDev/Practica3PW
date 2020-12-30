package mvc.control;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.ArrayList;
import es.uco.pw.display.useBean.CustomerBean;
import es.uco.pw.business.Anuncios.TipoAnuncio;
import es.uco.pw.business.Anuncios.AnuncioFlash;
import es.uco.pw.business.Anuncios.AnuncioTematico;
import es.uco.pw.business.Anuncios.EstadoAnuncio;
import es.uco.pw.business.Usuario.Contacto;
import es.uco.pw.data.dao.Anuncios.AnuncioDAO;
import es.uco.pw.data.dao.Usuario.ContactoDAO;
import es.uco.pw.business.DTO.DTOUsuario.ContactoDTO;
import es.uco.pw.business.DTO.DTOAnuncio.AnuncioFlashDTO;
import es.uco.pw.business.DTO.DTOAnuncio.AnuncioGeneralDTO;
import es.uco.pw.business.DTO.DTOAnuncio.AnuncioIndividualizadoDTO;
import es.uco.pw.business.DTO.DTOAnuncio.AnuncioTematicoDTO;

public class createAddControllerServlet extends HttpServlet{
    public void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
    {
        HttpSession session=request.getSession();
        CustomerBean customerBean = (CustomerBean)session.getAttribute("customerBean");
        String email_usuario="";
        //Si no estas logeado no puedes crear un anuncio, por lo que redirecciona a la vista del login
        if(customerBean ==null || customerBean.getEmail() == ""){
            
            response.sendRedirect("/GestorAnuncios/mvc/view/loginView.jsp");
        }else{
            //En el caso de que este registrado nunca debe acceder a este formulario por url, solo por post
            //a traves de su vista, por lo que en caso de que acceda al servlet por url lo redirecciono a la vista+
            response.sendRedirect("/GestorAnuncios/mvc/view/createAddView.jsp");
        }
    }
    
    
    public void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
    {
        HttpSession session=request.getSession();
        CustomerBean customerBean = (CustomerBean)session.getAttribute("customerBean");
        String email_usuario="";
        if(customerBean !=null && customerBean.getEmail() != ""){
            email_usuario=customerBean.getEmail();
        }else{
            response.sendRedirect("/GestorAnuncios/mvc/view/loginView.jsp");
        }

        String dataBasePath= request.getServletContext().getInitParameter("AbsolutePath")+request.getServletContext().getInitParameter("sqlProperties");
        ContactoDAO contactoDAO = new ContactoDAO(dataBasePath);
        AnuncioDAO anuncioDAO = new AnuncioDAO(dataBasePath);
        Contacto usuario=contactoDAO.ObtenerContactoById(email_usuario);
        
        String tituloAnuncio=request.getParameter("title");
        String cuerpoAnuncio=request.getParameter("content");
        String tipoAnuncioString=request.getParameter("addType");
        TipoAnuncio tipoAnuncio=TipoAnuncio.valueOf(tipoAnuncioString);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String fechaPublicacionString="";
        Date fecha_publicación=null;
        
        try{
            fechaPublicacionString=sdf.format(new Date());
            fecha_publicación=sdf.parse(fechaPublicacionString);
        }catch(Exception e){
            e.printStackTrace();
        }
        
        
        String estadoAnuncioString=request.getParameter("estadoAnuncio");
        EstadoAnuncio estadoAnuncio=EstadoAnuncio.valueOf(estadoAnuncioString);
        String[] arrayDestinatarios=request.getParameterValues("destinatarios");
        ArrayList<Contacto> destinataros = new ArrayList<Contacto>();
        
        if(arrayDestinatarios!=null){
        for(int i=0;i<arrayDestinatarios.length;i++)
            {
                destinataros.add(contactoDAO.ObtenerContactoById(arrayDestinatarios[i]));
            }
        }

        
        if(tipoAnuncio == TipoAnuncio.General){
            AnuncioGeneralDTO anuncioGeneralDTO= new AnuncioGeneralDTO(tituloAnuncio,cuerpoAnuncio,fecha_publicación,
                                                                        usuario,estadoAnuncio,destinataros);
            
            anuncioDAO.InsertarAnuncioGeneral(anuncioGeneralDTO);
            
        }else if(tipoAnuncio == TipoAnuncio.Tematico){
            String[] temasArray=request.getParameterValues("tags");
            ArrayList<String> temas=new ArrayList<String>();
            
            if(temasArray!=null){
                for(int i=0;i<temasArray.length;i++){
                    temas.add(temasArray[i]);
                }
            }

            AnuncioTematicoDTO anuncioTematicoDTO=new AnuncioTematicoDTO(tituloAnuncio,cuerpoAnuncio,fecha_publicación,usuario,
                                                                         estadoAnuncio,destinataros,temas);
            anuncioDAO.InsertarAnuncioTematico(anuncioTematicoDTO);
        }else if(tipoAnuncio==TipoAnuncio.Flash){
            String fechaCaducidadString=request.getParameter("endDate");
            Date fechaCaducidad=null;
            if(tipoAnuncio == TipoAnuncio.Flash){
                try{
                    fechaCaducidad=sdf.parse(fechaCaducidadString);
                }catch(Exception e){
                    e.printStackTrace();
                } 
            }
            AnuncioFlashDTO anuncioFlashDTO=new AnuncioFlashDTO(tituloAnuncio,cuerpoAnuncio,fecha_publicación,fechaCaducidad,usuario,
                                                                estadoAnuncio,destinataros);
            anuncioDAO.InsertarAnuncioFlash(anuncioFlashDTO);
        }
    }
}
