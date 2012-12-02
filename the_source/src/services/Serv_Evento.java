package services;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import models.Desarrollo;
import models.Evento;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.stereotype.Service;

import tools.DB_Connection;
import tools.Date_Hour;

@Service
@SuppressWarnings("deprecation")
public class Serv_Evento extends DB_Connection {
	
	@Autowired private Serv_Desarrollo serv_Desarrollo;
	
	private Date_Hour dh = new Date_Hour();
	
	private class to_Object implements ParameterizedRowMapper<Evento>{
		@Override
		public Evento mapRow(ResultSet rs, int rows) throws SQLException {
			Evento ev = new Evento();
			ev.setId_eve(rs.getInt("id_eve"));
			ev.setId_top(rs.getInt("id_top"));
			ev.setId_us(rs.getInt("id_us"));
			ev.setDescrip(rs.getString("descrip"));
			ev.setReg_hora(rs.getTime("reg_hora").toString());
			ev.setReg_fecha(rs.getDate("reg_fecha").toString());
			if(get_Kind_from_topico(rs.getInt("id_top"))==1){
				ev.setHora_ini(rs.getTime("hora_ini").toString());
				ev.setFecha_ini(rs.getDate("fecha_ini").toString());
			}
			ev.setTipo_coord(rs.getInt("tipo_coord"));
			try {
				ev.setDesarrollos(serv_Desarrollo.get_All_by_Evento(rs.getInt("id_eve")));
			} catch (Exception e) {
				ev.setDesarrollos(null);
			}
			return ev;
		}
	}
	
	private Integer generate_Id(){
		try {
			String sql = "SELECT MAX(id_eve) FROM evento";
			return db.queryForInt(sql)+1;
		} catch (Exception e) {
			return 0;
		}
	}
	
	public Integer get_Kind_from_topico(Integer id_top){
		String sql = "SELECT tipo_topico(?)";
		return db.queryForInt(sql, id_top);
	}
	
	public Evento get_by_Id(Integer id_eve){
		String sql = "SELECT * FROM evento WHERE id_eve=?";
		return db.queryForObject(sql, new to_Object(), id_eve);
	}
	
	public List<Evento> get_All(){
		String sql = "SELECT * FROM evento";
		return db.query(sql, new to_Object());
	}
	
	public List<Evento> get_All_by_Topico(Integer id_top){
		String sql = "SELECT * FROM evento WHERE id_top=?";
		return db.query(sql, new to_Object(), id_top);
	}
	
	public List<Evento> get_All_by_Usuario(Integer id_us){
		String sql = "SELECT * FROM evento WHERE id_us=?";
		return db.query(sql, new to_Object(), id_us);
	}
	
	public void add(Evento ev){
		String hora = String.format("%s:%s:%s", new Date().getHours(), new Date().getMinutes(), new Date().getSeconds());
		if(get_Kind_from_topico(ev.getId_top())==0){
			String sql = "INSERT INTO evento(id_eve, descrip, reg_hora, reg_fecha, id_top, id_us, tipo_coord) VALUES(?,?,?,?,?,?,?)";
			db.update(sql, new Object[]{generate_Id(), ev.getDescrip(), dh.h_m_s(hora), new Date(), ev.getId_top(), ev.getId_us(), ev.getTipo_coord()});
		}
		else{
			String sql = "INSERT INTO evento(id_eve, descrip, reg_hora, reg_fecha, hora_ini, fecha_ini, id_top, id_us, tipo_coord) VALUES(?,?,?,?,?,?,?,?,?)";
			db.update(sql, new Object[]{generate_Id(), ev.getDescrip(), dh.h_m_s(hora), new Date(), dh.h_m_s(ev.getHora_ini()), dh.y_m_d(ev.getFecha_ini()), ev.getId_top(), ev.getId_us(), ev.getTipo_coord()});
		}
	}
	
	public void modify(Evento ev){
		String hora = String.format("%s:%s:%s", new Date().getHours(), new Date().getMinutes(), new Date().getSeconds());
		if(get_Kind_from_topico(ev.getId_top())==0){
			String sql = "UPDATE evento SET descrip=?, reg_hora=?, reg_fecha=?, id_top=?, id_us=?, tipo_coord=? WHERE id_eve=?";
			db.update(sql, new Object[]{ev.getDescrip(), hora, new Date(), ev.getId_top(), ev.getId_us(), ev.getTipo_coord(), ev.getId_eve()});
		}
		else{
			String sql = "UPDATE evento SET descrip=?, reg_hora=?, reg_fecha=?, hora_ini=?, fecha_ini=?, id_top=?, id_us=?, tipo_coord=? WHERE id_eve=?";
			db.update(sql, new Object[]{ev.getDescrip(), hora, new Date(), dh.h_m_s(ev.getHora_ini()), dh.y_m_d(ev.getFecha_ini()), ev.getId_top(), ev.getId_us(), ev.getTipo_coord(), ev.getId_eve()});
		}
	}
	
	public void delete(Integer id_eve){
		delete_from_desarrollo(id_eve);
		String sql = "DELETE FROM evento WHERE id_eve=?";
		db.update(sql, id_eve);
	}
	
	public void delete_from_desarrollo(Integer id_eve){
		for(Desarrollo ds: serv_Desarrollo.get_All_by_Evento(id_eve)){
			serv_Desarrollo.delete(ds.getId_eve());
		}
	}
}