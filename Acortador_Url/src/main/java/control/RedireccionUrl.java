package control;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import servicio.UrlDAO;

@WebServlet({ "/redireccionUrl", "/srbt.ic/*", "/redireccion" })
public class RedireccionUrl extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = LogManager.getLogger(UrlDAO.class);
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RedireccionUrl() {
        super();
    }

	/*
	 * Mét0do para redirecionar independientemente cada url acortada con su url asociada en la base de datos. A mayores usamos metodos para actualizar el numero de veces
	 * que se usa la url acortada y guardamos el navegador con la que se a usado. Por último en el caso de que la url acortada no le queden más usos tras usarla será borrada
	 * de la base de datos.
	 *
	 */

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		//Recogemos la url acortada y buscamos la url larga asociada, tambien recogemos el navegador usado
		String urlBuscada = request.getPathInfo();
		urlBuscada = urlBuscada.substring(1);
		UrlDAO urlDao = new UrlDAO();
		
		String urlLarga = urlDao.devuelveUrLarga(urlBuscada);

		String userAgent = request.getHeader("User-Agent");
		logger.info("Navegador: {}", userAgent);
		System.out.println("Navegador: "+userAgent);

		//Verificamos que la url encontrada sea correcta y no este vacia
		if (urlLarga == null || urlLarga.isEmpty()) {
		    RequestDispatcher dispatcher = request.getRequestDispatcher("/aIndex");
		    dispatcher.forward(request, response);
			logger.info("No se ha podido realizar la redirección: {}", urlLarga);

		} else {

			int numeroDeIntentos = urlDao.obtenerUrl(urlLarga).getNumeroIntentos();
			int numeroDeVecesUsadasAntes = urlDao.obtenerUrl(urlLarga).getVecesUsada();
			int numeroDeVecesUsadasAhora =  numeroDeVecesUsadasAntes + 1;

			//Actualizamos el numero de veces usada la url acortada y la eliminamos en caso de no tener más intentos tras el actual. Tambien actualiza los navegadores usados
			if(numeroDeVecesUsadasAntes >= numeroDeIntentos) {
				urlDao.eliminarUrl(urlLarga);
			}else {
				urlDao.actualizarNumeroIntentos(urlLarga);
				urlDao.actualizarNavegadores(urlLarga, userAgent);
				if(numeroDeVecesUsadasAhora >= numeroDeIntentos) {
					urlDao.eliminarUrl(urlLarga);
				}
			}

			logger.info("Redirección realizada exitosamente: {}", urlLarga);
			logger.info("URL buscada: {}", urlBuscada);

			response.setStatus(HttpServletResponse.SC_FOUND);
			response.setHeader("Location", urlLarga);
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
