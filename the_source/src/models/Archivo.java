package models;

public class Archivo {
	
	private Integer id_arc, id_cat, id_des;
	private String nombre;
	
	private Desarrollo desarrollo;
	private Categoria categoria;
	
	public Integer getId_arc() {
		return id_arc;
	}
	public void setId_arc(Integer id_arc) {
		this.id_arc = id_arc;
	}
	public Integer getId_cat() {
		return id_cat;
	}
	public void setId_cat(Integer id_cat) {
		this.id_cat = id_cat;
	}
	public Integer getId_des() {
		return id_des;
	}
	public void setId_des(Integer id_des) {
		this.id_des = id_des;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public Desarrollo getDesarrollo() {
		return desarrollo;
	}
	public void setDesarrollo(Desarrollo desarrollo) {
		this.desarrollo = desarrollo;
	}
	public Categoria getCategoria() {
		return categoria;
	}
	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}
}