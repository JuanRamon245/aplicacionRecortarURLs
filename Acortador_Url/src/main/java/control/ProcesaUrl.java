package control;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import recursos.Recursos;
import clases.Url;
import servicio.UrlDAO;

@WebServlet("/procesaUrl")
public class ProcesaUrl extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = LogManager.getLogger(UrlDAO.class);
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ProcesaUrl() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/*
	 * Mét0do para guardar en la base de datos como un objeto Url con los parametros del usuario. Primero se compureba si cumple las verificaciones,
	 * tras esto si la pagina web es accesible y se puede leer usando metodos https como conexion. Por ultimo verificamos que la url introducida no exista ya
	 * en la base de datos, en caso de ser asi la añade con todos los datos, pero si ya existe, devuelve como parametro al usuario la url acortada que existe de una
	 * sesión previa
	 *
	 */

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		//Recoger todos los datos de los usarios
		String nombre = request.getParameter("nombre");
		String urlLarga = request.getParameter("urlLarga");
		String urlCorta = "No es posible acortar la URL";
		String numeroIntentosString = request.getParameter("numero");
		UrlDAO urlDAO = new UrlDAO();
		
		try {
			//Verificar los parametros introducido los usuarios
			if (esNumeroIntentosValido(numeroIntentosString) && esUrlValida(urlLarga) && esNombreValido(nombre)) {
				URL urlComprobacionRedireccion = new URL(urlLarga);
				HttpURLConnection connection = (HttpURLConnection) urlComprobacionRedireccion.openConnection();
				connection.setRequestMethod("GET");
				connection.setConnectTimeout(15000);
				connection.setReadTimeout(15000);
				int responseCode = connection.getResponseCode();
				int numeroIntentos = Integer.parseInt(numeroIntentosString);

					//Los codigos que puede obtener este int y verifican que la pagina web sea accesible son los que hay entre 200 y 400
					if (responseCode >= 200 && responseCode < 400) {
						urlCorta = Recursos.generarIdAleatorio(7);

						// Si no existe la url introducida por el usuario se añade, si existe, se asigna a urlCorta la que está ya almacenada
						if(!urlDAO.existeUrlLarga(urlLarga)) {

							// No la puede almacenar en la base de datos si la urlLarga excede los 255 caracteres
							Url url = new Url(nombre, urlLarga, urlCorta, numeroIntentos, 0, "");
							urlDAO.insertarUrlVerificandoExistencia(url);
							urlCorta = "http://localhost:8080/Acortador_URL/srbt.ic/" + urlCorta;

						} else {
							urlCorta = urlDAO.devuelveUrlCorta(urlLarga);
							urlCorta = "http://localhost:8080/Acortador_URL/srbt.ic/" + urlCorta;
						}
					} else {
						logger.error("La url no conecta con la pagina, el error de pagina es: {}", responseCode);
					}
				} else {
					logger.error("Datos proporcionados erroneos");
					RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp");
					dispatcher.forward(request, response);
				}

			} catch(Exception e) {
				logger.error("La url enviada no es valida: {}", urlLarga);
			}

		logger.info("La url larga es: {}", urlLarga);
		logger.info("La url corta es: {}", urlCorta);
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		
		response.getWriter().write(urlCorta);
	}

	/*
	 * Mét0do para verificar que la URL introducida por el usuario es correcto
	 *
	 * @param url URL introducida por el usuario
	 * @return retorna true o false en caso de cumplir todas las validaciones o no
	 */
    private boolean esUrlValida(String url) {
        if (url == null || url.isEmpty()) {
            return false;
        }
        try {
            URL validUrl = new URL(url);
            return validUrl.getProtocol().equals("http") || validUrl.getProtocol().equals("https");
        } catch (MalformedURLException e) {
            return false;
        }
    }

	/*
	 * Mét0do para verificar que el número de intentos introducido por el usuario es correcto
	 *
	 * @param intentos Número de intentos introducido por el usuario
	 * @return retorna true o false en caso de cumplir todas las validaciones o no
	 */
	private boolean esNumeroIntentosValido(String intentos) {
		if (intentos == null || intentos.isEmpty()) {
			return true;
		}
		try {
			int numero = Integer.parseInt(intentos);
			return numero >= 1 && numero <= 50;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	/*
	 * Mét0do para verificar que el nombre introducido por el usuario es correcto
	 *
	 * @param input Nombre introducido por el usuario
	 * @return retorna true o false en caso de cumplir todas las validaciones o no
	 */
    public static boolean esNombreValido(String input) {
        if (input == null || input.isEmpty()) {
            return false; 
        }
        
        if (input.charAt(0) == ' ') {
            return false;
        }

        return input.matches("[a-zA-Z]+");
    }
}
