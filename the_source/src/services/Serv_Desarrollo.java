package services;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import models.Archivo;
import models.Desarrollo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.stereotype.Service;

import tools.DB_Connection;

@Service
@SuppressWarnings("deprecation")
public class Serv_Desarrollo extends DB_Connection {
	
	@Autowired private Serv_Archivo serv_Archivo;
	
	private class to_Object implements ParameterizedRowMapper<Desarrollo>{
		@Override
		public Desarrollo mapRow(ResultSet rs, int rows) throws SQLException {
			Desarrollo ds = new Desarrollo();
			ds.setId_des(rs.getInt("id_des"));
			ds.setId_eve(rs.getInt("id_eve"));
			ds.setDescrip(rs.getString("descrip"));
			try {
				ds.setArchivos(serv_Archivo.get_All_by_Desarrollo(rs.getInt("id_des")));
			} catch (Exception e) {
				ds.setArchivos(null);
			}
			return ds;
		}
	}
	
	private Integer generate_Id(){
		try {
			String sql = "SELECT MAX(id_des) FROM desarrollo";
			return db.queryForInt(sql)+1;
		} catch (Exception e) {
			return 0;
		}
	}
	
	public Desarrollo get_by_Id(Integer id_des){
		String sql = "SELECT * FROM desarrollo WHERE id_des=?";
		return db.queryForObject(sql, new to_Object(), id_des);
	}
	
	public List<Desarrollo> get_All(){
		String sql = "SELECT * FROM desarrollo";
		return db.query(sql, new to_Object());
	}
	
	public List<Desarrollo> get_All_by_Evento(Integer id_eve){
		String sql = "SELECT * FROM desarrollo WHERE id_eve=?";
		return db.query(sql, new to_Object(), id_eve);
	}
	
	public void add(Desarrollo ds){
		String sql = "INSERT INTO desarrollo(id_des, descrip, id_eve) VALUES(?,?,?)";
		db.update(sql, new Object[]{generate_Id(), ds.getDescrip(), ds.getId_eve()});
	}
	
	public void modify(Desarrollo ds){
		String sql = "UPDATE desarrollo SET descrip=?, id_eve=? WHERE id_des=?";
		db.update(sql, new Object[]{ds.getDescrip(), ds.getId_eve(), ds.getId_des()});
	}
	
	public void delete(Integer id_des){
		delete_from_archivo(id_des);
		String sql = "DELETE FROM desarrollo WHERE id_des=?";
		db.update(sql, id_des);
	}
	
	public void delete_from_archivo(Integer id_des){
		for(Archivo ar: serv_Archivo.get_All_by_Desarrollo(id_des)){
			serv_Archivo.delete(ar.getId_arc());
		}
	}
}