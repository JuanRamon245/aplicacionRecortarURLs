package recursos;

import java.util.Random;

public class Recursos {

	// Genera aleatoriamente una cadena de caracteres 
	public static String generarIdAleatorio(int longitud) {
	    String caracteresPermitidos = "abcdefghijklmnopqrstuvwxyz0123456789";
	    StringBuilder id = new StringBuilder();
	    Random random = new Random();

	    for (int i = 0; i < longitud; i++) {
	        int indiceAleatorio = random.nextInt(caracteresPermitidos.length());
	        char caracterAleatorio = caracteresPermitidos.charAt(indiceAleatorio);
	        id.append(caracterAleatorio);
	    }

	    return id.toString();
	}
}
