package models;

import java.util.List;

public class Menu {
	
	private Integer id_men;
	private String nombre;
	
	private List<Url> urls;
	
	public Integer getId_men() {
		return id_men;
	}
	public void setId_men(Integer id_men) {
		this.id_men = id_men;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public List<Url> getUrls() {
		return urls;
	}
	public void setUrls(List<Url> urls) {
		this.urls = urls;
	}
}