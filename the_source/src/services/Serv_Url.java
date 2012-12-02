package services;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import models.Url;

import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.stereotype.Service;

import tools.DB_Connection;
import tools.FirstInCapitals;

@Service
@SuppressWarnings("deprecation")
public class Serv_Url  extends DB_Connection {
	
	private FirstInCapitals f = new FirstInCapitals();
	
	private class to_Object implements ParameterizedRowMapper<Url>{
		@Override
		public Url mapRow(ResultSet rs, int rows) throws SQLException {
			Url url = new Url();
			url.setId_url(rs.getInt("id_url"));
			url.setNombre(rs.getString("nombre"));
			url.setEnlace(rs.getString("enlace"));
			return url;
		}
	}
	
	private Integer generate_Id(){
		try {
			String sql = "SELECT MAX(id_url) FROM url";
			return db.queryForInt(sql)+1;
		} catch (Exception e) {
			return 0;
		}
	}
	
	public Url get_by_Id(Integer id_url){
		String sql = "SELECT * FROM url WHERE id_url=?";
		return db.queryForObject(sql, new to_Object(), id_url);
	}
	
	public List<Url> get_All(){
		String sql = "SELECT * FROM url";
		return db.query(sql, new to_Object());
	}
	
	public List<Url> get_Urls_Assings_to_Menu(Integer id_men){
		String sql = "SELECT * FROM url WHERE id_url IN (SELECT id_url FROM men_url WHERE id_men=?)";
		return db.query(sql, new to_Object(), id_men);
	}
	
	public List<Url> get_Urls_without_Assign(Integer id_men){
		String sql = "SELECT * FROM url WHERE id_url NOT IN (SELECT id_url FROM men_url WHERE id_men=?)";
		return db.query(sql, new to_Object(), id_men);
	}
	
	public void add(Url url){
		url.setId_url(generate_Id());
		String sql = "INSERT INTO url(id_url, nombre, enlace) VALUES(?,?,?)";
		db.update(sql, new Object[]{url.getId_url(), f.FiC(url.getNombre()), url.getEnlace()});
	}
	
	public void modify(Url url){
		String sql = "UPDATE url SET nombre=?, enlace=? WHERE id_url=?";
		db.update(sql, new Object[]{f.FiC(url.getNombre()), url.getEnlace(), url.getId_url()});
	}
	
	public void delete(Integer id_url){
		delete_from_men_url(id_url);
		String sql = "DELETE FROM url WHERE id_url=?";
		db.update(sql, id_url);
	}
	
	public void delete_from_men_url(Integer id_url){
		String sql = "DELETE FROM men_url WHERE id_url=?";
		db.update(sql, id_url);
	}
}