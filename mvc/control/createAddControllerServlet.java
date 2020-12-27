package mvc.control;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import javax.servlet.*;
import javax.servlet.http.*;

public class createAddControllerServlet extends HttpServlet{
    public void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
    {
        String tituloAnuncio=request.getParameter("title");
        String cuerpoAnuncio=request.getParameter("content");
        String tipoAnuncio=request.getParameter("addType");
        Date fecha_publicación=new Date();
        String fecha_caducidad = request.getParameter("endDate");
        String[] temas=request.getParameterValues("tags");
        String tipoPublicación=request.getParameter("estadoAnuncio");
        response.setContentType("text/html");
        PrintWriter out=response.getWriter();
        out.print("<!DOCTYPE html><html><body><h1>Anuncio</h1>");
        out.print("<p>Titulo: "+tituloAnuncio+"</p>");
        out.print("<p>Contenido: "+cuerpoAnuncio+"</p>");
        out.print("<p>Tipo Anuncio: "+tipoAnuncio+"</p>");
        out.print("<p>Fecha Publicación: </p>");
        out.print("<p>Fecha Caducidad: </p>");
        

        out.print("<p>Tipo publicacion: "+tipoPublicación+"</p>");
       
        
        out.print("</body></html>");
    }
}