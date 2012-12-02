package models;

import java.util.List;

public class Rol {
	
	private Integer id_rol;
	private String nombre;
	
	private List<Menu> menus;
	private List<Usuario> usuarios;
	
	public Integer getId_rol() {
		return id_rol;
	}
	public void setId_rol(Integer id_rol) {
		this.id_rol = id_rol;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public List<Menu> getMenus() {
		return menus;
	}
	public void setMenus(List<Menu> menus) {
		this.menus = menus;
	}
	public List<Usuario> getUsuarios() {
		return usuarios;
	}
	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}
}