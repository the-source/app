package services;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import models.Archivo;

import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.stereotype.Service;

import tools.DB_Connection;
import tools.FirstInCapitals;

@Service
@SuppressWarnings("deprecation")
public class Serv_Archivo extends DB_Connection {
	
	private FirstInCapitals f = new FirstInCapitals();
	
	private class to_Object implements ParameterizedRowMapper<Archivo>{
		@Override
		public Archivo mapRow(ResultSet rs, int rows) throws SQLException {
			Archivo ar = new Archivo();
			ar.setId_arc(rs.getInt("id_arc"));
			ar.setNombre(rs.getString("nombre"));
			ar.setId_cat(rs.getInt("id_cat"));
			ar.setId_des(rs.getInt("id_des"));
			return ar;
		}
	}
	
	private Integer generate_Id(){
		try {
			String sql = "SELECT MAX(id_arc) FROM archivo";
			return db.queryForInt(sql)+1;
		} catch (Exception e) {
			return 0;
		}
	}
	
	public Archivo get_by_Id(Integer id_arc){
		String sql = "SELECT * FROM archivo WHERE id_arc=?";
		return db.queryForObject(sql, new to_Object(), id_arc);
	}
	
	public List<Archivo> get_All(){
		String sql = "SELECT * FROM archivo";
		return db.query(sql, new to_Object());
	}
	
	public List<Archivo> get_All_by_Categoria(Integer id_cat){
		String sql = "SELECT * FROM archivo WHERE id_cat=?";
		return db.query(sql, new to_Object(), id_cat);
	}
	
	public List<Archivo> get_All_by_Desarrollo(Integer id_des){
		String sql = "SELECT * FROM archivo WHERE id_des=?";
		return db.query(sql, new to_Object(), id_des);
	}
	
	public void add(Archivo ar){
		String sql = "INSERT INTO archivo(id_arch, nombre, id_cat, id_des) VALUES(?,?,?,?)";
		db.update(sql, new Object[]{generate_Id(), f.FiC(ar.getNombre()), ar.getId_cat(), ar.getId_des()});
	}
	
	public void modify(Archivo ar){
		String sql = "UPDATE archivo SET nombre=?, id_cat=?, id_des=? WHERE id_arc=?";
		db.update(sql, new Object[]{f.FiC(ar.getNombre()), ar.getId_cat(), ar.getId_des(), ar.getId_arc()});
	}
	
	public void delete(Integer id_arc){
		String sql = "DELETE FROM archivo WHERE id_arc=?";
		db.update(sql, id_arc);
	}
}