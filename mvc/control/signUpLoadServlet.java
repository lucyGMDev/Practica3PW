package mvc.control;



import es.uco.pw.data.dao.Intereses.InteresesDAO;


import java.io.IOException;
import java.util.Hashtable;
import java.util.Enumeration;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.*;


@WebServlet(name="signUpLoad", urlPatterns="/signUpLoad")


public class signUpLoadServlet extends HttpServlet{

 protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException

{

response.setContentType("text/html;charset=UTF-8");


String dataBasePath=request.getServletContext().getInitParameter("AbsolutePath")+request.getServletContext().getInitParameter("sqlProperties");
InteresesDAO interesesDAO = new InteresesDAO(dataBasePath);
Hashtable<Integer, String> intereses_totales= interesesDAO.DevolverIntereses();
Enumeration elementos = intereses_totales.elements();
Enumeration claves = intereses_totales.keys();
request.setAttribute("elementos", elementos);
request.setAttribute("claves", claves);

RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/mvc/view/signUpView.jsp");

dispatcher.forward(request, response);



}


}
