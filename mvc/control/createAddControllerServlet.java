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
        response.setContentType("text/html");
        PrintWriter out=response.getWriter();
        out.print("<!DOCTYPE html><html><body><h1>Anuncio</h1>");
        out.print("<p>Titulo: "+tituloAnuncio+"</p>");
        out.print("<p>Contenido: "+cuerpoAnuncio+"</p>");
        out.print("<p>Tipo Anuncio: "+tipoAnuncio+"</p>");
        out.print("<p>Fecha Publicación: "+fecha_publicación+"</p>");
        out.print("<p>Temas:</p>");
        for(int i=0;i<temas.length;i++){
            out.print("<p>"+temas[i]+"</p>");
        }
        out.print("<h2>Esto es una prueba</h2>");
        out.print("<p>Fecha Caducidad: "+fecha_caducidad+"</p>");
        out.print("</body></html>");
    }
}