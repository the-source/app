package models;

import java.util.List;

public class Topico {
	
	private Integer id_top;
	private String nombre;
	private Boolean tipo;
	
	private List<Evento> eventos;
	
	public Integer getId_top() {
		return id_top;
	}
	public void setId_top(Integer id_top) {
		this.id_top = id_top;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public Boolean getTipo() {
		return tipo;
	}
	public void setTipo(Boolean tipo) {
		this.tipo = tipo;
	}
	public List<Evento> getEventos() {
		return eventos;
	}
	public void setEventos(List<Evento> eventos) {
		this.eventos = eventos;
	}
}