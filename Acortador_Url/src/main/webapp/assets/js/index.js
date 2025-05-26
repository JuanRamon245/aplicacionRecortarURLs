let acortado = document.getElementsByClassName("acortado");	
let ocultar = document.getElementsByClassName("ocultar");	
let mensajeError = document.getElementsByClassName("mensajeError");


// Función para borrar el texto del input con la Url larga
document.addEventListener("DOMContentLoaded", function () {
    const botonBorrarUrl = document.getElementById("botonBorrarUrl");
    const urlLarga = document.getElementById("urlLarga");
	
	if (botonBorrarUrl && urlLarga) {
        botonBorrarUrl.addEventListener("click", function () {
            console.log("Botón Borrar presionado");
            urlLarga.value = ""; 
        });
    } else {
        console.error("No se encontraron los elementos para borrar la URL");
    }
});

// Función para copiar el texto
document.getElementById("botonCopiar").addEventListener("click", function () {
	let valor = document.getElementById("copiar").innerText;
	navigator.clipboard.writeText(valor) 
		.then(() => {
	    	let span = document.getElementById("span");
	    	span.style.display = "inline";
	    	setTimeout(()=>{
	    	span.style.display ="none";
	    }, 1800);
		})
		.catch(err => {
	    	console.error("Hubo un error al copiar el texto: ", err);
	    });
});
    	
document.getElementById("formulario").addEventListener("submit", function (event) {
	event.preventDefault(); 
	
		const urlInput = document.getElementById("urlLarga").value;
		const nombreInput = document.getElementById("nombre").value;
		const numeroInput = document.getElementById("numero").value || 50; 
	
		if (nombreValido(nombreInput)) {
			if (isValidUrl(urlInput)) {
						const formData = new URLSearchParams();
						formData.append("urlLarga", urlInput);
						formData.append("nombre", nombreInput);
						formData.append("numero", numeroInput);
						
						// Enviar los datos al servlet usando fetch
						fetch("procesaUrl", {
							method: "POST",
							headers: {
								"Content-Type": "application/x-www-form-urlencoded", 
							},
								body: formData.toString(), 
						})
						.then((response) => {
							if (response.ok) {
								return response.text(); 
							} else {
								throw new Error("Error en la respuesta del servidor");
							}
						})
						.then((data) => {
							for (let i = 0; i < ocultar.length; i++) {
								ocultar[i].style.display = "none"; 
							}
							for (let i = 0; i < acortado.length; i++) {
								acortado[i].style.display = "block";
							}
							document.getElementById("copiar").style.display = "inline-block";
							document.getElementById("copiar").style.padding= "5px 20px 5px 20px";
							
							document.getElementById("copiar").innerText = data; 
				    		        	 	 	 	
							// Asignar el valor del nombre al primer <p>
				    		const nombre = document.getElementById("nombre").value;
				    		const numero = document.getElementById("numero").value || 50; // Por defecto 50

				    		// Mostrar el nombre
				    		const pNombre = document.querySelectorAll(".acortado")[0]; 
				    		            
				    		pNombre.innerHTML = `URL acortado por: ${nombre}`;

				            // Mostrar el número de usos
							const pNumero = document.querySelectorAll(".acortado")[1]; 
				    		pNumero.innerHTML = `Número de usos del enlace: ${numero}`;
				    	})
				    	.catch((error) => {
							location.reload();
				    		console.error("Hubo un problema al procesar la solicitud:", error);
				    		alert("Error al acortar la URL. Inténtalo de nuevo.");
				    	});
				    } else {
						mensajeError[0].innerText = "Por favor, introduce una URL válida.";
						mensajeError[0].style.display = "block";
						setTimeout(function() {
						    mensajeError[0].style.display = "none";
						}, 2000);
				    }
		} else {
			mensajeError[0].innerText = "Por favor, introduce un nombre válido.";
			mensajeError[0].style.display = "block";
			setTimeout(function() {
			    mensajeError[0].style.display = "none";
			}, 2000);
			
		}
		
});

// Función para validar la URL
function isValidUrl(url) {
	try {
		const regex = /^(https?:\/\/)?([a-zA-Z0-9-]+\.)+[a-zA-Z]{2,}(:\d+)?(\/[^\s]*)?$/;
		return regex.test(url);   	
	} catch(e) {
		return false;
	}
}

// Funciçon para validar el nombre
function nombreValido(nombre) {
	try {
		const regex = /^[a-zA-Z]+( [a-zA-Z]+)*$/;
		return regex.test(nombre);   	
	} catch(e) {
		return false;
	}
}
	    		
// Recargar página inicial
document.getElementById("recargar").addEventListener("click", function () {
	location.reload();
});

const nameInput = document.getElementById("nombre");

  nameInput.addEventListener("invalid", function(event) {
    if (nameInput.validity.valueMissing) {
      nameInput.setCustomValidity("Por favor, ingresa tu nombre.");
    }
  });

  nameInput.addEventListener("input", function() {
    nameInput.setCustomValidity(""); 
  });