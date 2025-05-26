package control;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class aIndex
 */
@WebServlet("/aIndex")
public class AIndex extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AIndex() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//request.setAttribute("UrlNoEncontrada", "UrlNoEncontrada");
		//request.getRequestDispatcher("index.jsp").forward(request, response);
		 // response.sendRedirect(request.getContextPath() + "/?Mensaje=UrlNoEncontrada"); // Usar parámetros en la url para añadir datos
		HttpSession session = request.getSession();
		session.setAttribute("Mensaje", "UrlNoEncontrada");
		response.sendRedirect(request.getContextPath());
	
	}

}
