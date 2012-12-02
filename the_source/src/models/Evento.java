package models;

import java.util.List;

public class Evento {
	
	private Integer id_eve, id_top, id_us, tipo_coord;
	private String descrip, reg_hora, reg_fecha, hora_ini, fecha_ini;
	
	private Topico topico;
	private List<Desarrollo> desarrollos;
	private Usuario usuario;

	public Integer getId_eve() {
		return id_eve;
	}
	public void setId_eve(Integer id_eve) {
		this.id_eve = id_eve;
	}
	public Integer getId_top() {
		return id_top;
	}
	public void setId_top(Integer id_top) {
		this.id_top = id_top;
	}
	public Integer getId_us() {
		return id_us;
	}
	public void setId_us(Integer id_us) {
		this.id_us = id_us;
	}
	public Integer getTipo_coord() {
		return tipo_coord;
	}
	public void setTipo_coord(Integer tipo_coord) {
		this.tipo_coord = tipo_coord;
	}
	public String getDescrip() {
		return descrip;
	}
	public void setDescrip(String descrip) {
		this.descrip = descrip;
	}
	public String getReg_hora() {
		return reg_hora;
	}
	public void setReg_hora(String reg_hora) {
		this.reg_hora = reg_hora;
	}
	public String getReg_fecha() {
		return reg_fecha;
	}
	public void setReg_fecha(String reg_fecha) {
		this.reg_fecha = reg_fecha;
	}
	public String getHora_ini() {
		return hora_ini;
	}
	public void setHora_ini(String hora_ini) {
		this.hora_ini = hora_ini;
	}
	public String getFecha_ini() {
		return fecha_ini;
	}
	public void setFecha_ini(String fecha_ini) {
		this.fecha_ini = fecha_ini;
	}
	public Topico getTopico() {
		return topico;
	}
	public void setTopico(Topico topico) {
		this.topico = topico;
	}
	public List<Desarrollo> getDesarrollos() {
		return desarrollos;
	}
	public void setDesarrollos(List<Desarrollo> desarrollos) {
		this.desarrollos = desarrollos;
	}
	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
}