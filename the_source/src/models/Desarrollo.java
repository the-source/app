package models;

import java.util.List;

public class Desarrollo {
	
	private Integer id_des, id_eve;
	private String descrip;
	
	private List<Archivo> archivos;
	
	public Integer getId_des() {
		return id_des;
	}
	public void setId_des(Integer id_des) {
		this.id_des = id_des;
	}
	public Integer getId_eve() {
		return id_eve;
	}
	public void setId_eve(Integer id_eve) {
		this.id_eve = id_eve;
	}
	public String getDescrip() {
		return descrip;
	}
	public void setDescrip(String descrip) {
		this.descrip = descrip;
	}
	public List<Archivo> getArchivos() {
		return archivos;
	}
	public void setArchivos(List<Archivo> archivos) {
		this.archivos = archivos;
	}
}