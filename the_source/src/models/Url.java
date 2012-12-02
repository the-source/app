package models;

public class Url {
	
	private Integer id_url;
	private String nombre, enlace;
	
	public Integer getId_url() {
		return id_url;
	}
	public void setId_url(Integer id_url) {
		this.id_url = id_url;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getEnlace() {
		return enlace;
	}
	public void setEnlace(String enlace) {
		this.enlace = enlace;
	}
}