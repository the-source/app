package models;

import java.util.List;

public class Categoria {
	
	private Integer id_cat;
	private String nombre;
	
	private List<Archivo> archivos;
	
	public Integer getId_cat() {
		return id_cat;
	}
	public void setId_cat(Integer id_cat) {
		this.id_cat = id_cat;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public List<Archivo> getArchivos() {
		return archivos;
	}
	public void setArchivos(List<Archivo> archivos) {
		this.archivos = archivos;
	}
}