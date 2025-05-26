package clases;
//Comentario de prueba
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "url")
public class Url {

	@Column(name = "nombre")
	private String nombre;

	@Id
	@Column(name = "urlLarga")
	private String urlLarga;

	@Column(name = "urlCorta")
	private String urlCorta;

	@Column(name = "fechaCreacion")
	private String fechaCreacion;

	@Column(name = "numeroIntentos")
	private int numeroIntentos;

	@Column(name = "vecesUsada")
	private int vecesUsada;

	@Column(name = "navegadores")
	private String navegadores;

	public Url() {

	}

	public Url(String nombre, String urlLarga, String urlCorta, int numeroIntentos, int vecesUsada, String navegadores) {
		super();
		this.nombre = nombre;
		this.urlLarga = urlLarga;
		this.urlCorta = urlCorta;
		this.fechaCreacion = LocalDateTime.now().format(DateTimeFormatter.ofPattern("YYYY-MM-dd HH:mm:ss"));
		this.numeroIntentos = numeroIntentos;
		this.vecesUsada = vecesUsada;
		this.navegadores = navegadores;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getUrlLarga() {
		return urlLarga;
	}

	public void setUrlLarga(String urlLarga) {
		this.urlLarga = urlLarga;
	}

	public String getUrlCorta() {
		return urlCorta;
	}

	public void setUrlCorta(String urlCorta) {
		this.urlCorta = urlCorta;
	}

	public String getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(String fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public int getNumeroIntentos() {
		return numeroIntentos;
	}

	public void setNumeroIntentos(int numeroIntentos) {
		this.numeroIntentos = numeroIntentos;
	}

	public int getVecesUsada() {
		return vecesUsada;
	}

	public void setVecesUsada(int vecesUsada) {
		this.vecesUsada = vecesUsada;
	}

	public String getNavegadores() {
		return navegadores;
	}

	public void setNavegadores(String navegadores) {
		this.navegadores = navegadores;
	}

	@Override
	public String toString() {
		return "URL [UrlLarga: " + urlLarga + ", UrlCorta: " + urlCorta + ", fechaCreacion: " + fechaCreacion + ", vecesUsada: " + vecesUsada + ", navegadores: " + navegadores +"]";
	}
}
