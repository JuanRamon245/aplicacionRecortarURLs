<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<% response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
   response.setHeader("Pragma", "no-cache"); 
   response.setDateHeader("Expires", 0); 
%>    
    

<%@ page import="javax.servlet.http.HttpSession" %>

<!DOCTYPE html>
<html lang="es">
	<head>
    	<meta charset="UTF-8">
    	<meta name="viewport" content="width=device-width, initial-scale=1.0">
    	<title>Acortador de URL</title>
    	<link rel="stylesheet" href="<%= request.getContextPath() %>/assets/css/styles.css">
	</head>
	<body>
	
	<%
	HttpSession  sessionActual = request.getSession();
	String mensaje  = "";
	mensaje = (String) sessionActual.getAttribute("Mensaje");
	if(mensaje != null && !mensaje.isEmpty()) {
	%>
		<div class="urlError">
	  		&#x2757; La url acortada que ha introducido no existe. 
		</div>
	<%
	}
	mensaje = "";
	%>
		<header>
			<h1>Acortador de URL</h1>
		</header>
		<div class="principal">
	    	<table>
		    	<form id="formulario" action="procesaUrl" method="POST">
		    		<tr class="ocultar">
		    			<td>
		    				<label for="nombre">Introduce tu nombre</label>	
			    		</td>
				    	<td>
				    		<div class="grupoCampo">
								<div class="campo">
									<input name="nombre" id="nombre" autocomplete="off"	required type="text" size="2" maxlength="20">
								</div>	
							</div>
				    	</td>
			    	</tr>
		    		<tr class="ocultar">
			    		<td>
			        		<label for="numero"><br>Número de usos del enlace<br>(máximo 50) </label>
				        </td>
				        <td>
				        	<br>	        
				     		<div class="grupoCampo">
								<div class="campo">
									<input type="number" name="numero" id="numero" max="50" min="1" placeholder="" autocomplete="on" size="2" >
								</div>
							</div>
				        </td>  
			    	</tr>
			        <tr class="ocultar">
				        <td colspan="2">
				        <label for="urlLarga" class="textoUrl"><br>Introduce la URL que desea acortar</label><br>
				            <div class="grupoUrl">
								<div class="campoUrl">
									<input name="urlLarga" id="urlLarga" placeholder="Introduce la URL" autocomplete="on" required type="text" size="60" >
								</div>
								<span class="grupoBotonEnviar">
							  		<button class="botones" id="botonBorrarUrl" type="button">Borrar</button> 
							    </span>
							</div>
							<p class="mensajeError"></p>
				        </td>
			        </tr>
			        <tr class="ocultar">
			        	<td class="centrar">
					        <span class="grupoBotonEnviar"><br>
					        	<button class="botonEnviar botones" id="botonEnviar" type="submit">Acortar Url</button>
					        </span>
				        </td>
				        <td colspan="2" class="centrar">
					        <span class="grupoBotonEnviar"><br>
					        	<button class="botones" id="reiniciar" type="reset">Reiniciar</button>
					        </span>
				        </td>
			        </tr>
			    </form>
	   			<br></br>
			    <tr>
				    <td colspan="2">
				    	<p class="acortado"></p>
				    </td>
				</tr>
				<tr>
			    	<td colspan="2">
				    	<p class="acortado"></p>
				    </td>
				</tr>
			    <tr>
			    	<td colspan="2">
			    		<div id="copiar" class="acortado">
			    			<%=request.getAttribute("urlCorta") %> 
			    		</div>
			    	</td>
			    </tr>
			    <tr colspan="2">
				    <td class="botonesRespuesta">
				    	<button id="botonCopiar" class="acortado botones centrar">Copiar </button>
				    	<span id="span">¡Copiado!</span>
				    </td>
				</tr>
				<tr>
				    <td style ="padding-top:50px;" colspan="2" class="botonesRespuesta">
				    	
				    	<button id="recargar" class="acortado botones centrar">Acortar una nueva Url</button>
				    </td>
			    </tr> 
	     	</table>
    	</div>
	    <footer>
	        <p class="nombres">
	        	Juan Ramón de León Martín
	        </p>
	        <p class="nombres">
	        	Christian Gómez Lozano
	        </p>
	        <p class="nombres">
	        	Mara Hernández Paz 
	        </p>
	    </footer>
    	<script src="<%= request.getContextPath() %>/assets/js/index.js" charset="UTF-8"></script>
	</body>
</html>