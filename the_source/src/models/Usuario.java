package models;

public class Usuario {
	
	private Integer id_us, id_rol, ci, telf;
	private String nombre, ap, am, foto, direcc, nombre_us, clave_us;
	private Boolean verificado;
	
	private Rol rol;
	
	public Integer getId_us() {
		return id_us;
	}
	public void setId_us(Integer id_us) {
		this.id_us = id_us;
	}
	public Integer getId_rol() {
		return id_rol;
	}
	public void setId_rol(Integer id_rol) {
		this.id_rol = id_rol;
	}
	public Integer getCi() {
		return ci;
	}
	public void setCi(Integer ci) {
		this.ci = ci;
	}
	public Integer getTelf() {
		return telf;
	}
	public void setTelf(Integer telf) {
		this.telf = telf;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getAp() {
		return ap;
	}
	public void setAp(String ap) {
		this.ap = ap;
	}
	public String getAm() {
		return am;
	}
	public void setAm(String am) {
		this.am = am;
	}
	public String getFoto() {
		return foto;
	}
	public void setFoto(String foto) {
		this.foto = foto;
	}
	public String getDirecc() {
		return direcc;
	}
	public void setDirecc(String direcc) {
		this.direcc = direcc;
	}
	public String getNombre_us() {
		return nombre_us;
	}
	public void setNombre_us(String nombre_us) {
		this.nombre_us = nombre_us;
	}
	public String getClave_us() {
		return clave_us;
	}
	public void setClave_us(String clave_us) {
		this.clave_us = clave_us;
	}
	public Rol getRol() {
		return rol;
	}
	public void setRol(Rol rol) {
		this.rol = rol;
	}
	public Boolean getVerificado() {
		return verificado;
	}
	public void setVerificado(Boolean verificado) {
		this.verificado = verificado;
	}
	@Override
	public String toString() {
		return String.format("%s %s %s", nombre, (ap==null?"":ap), (am==null?"":am));
	}
}