package services;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import models.Menu;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.stereotype.Service;

import tools.DB_Connection;
import tools.FirstInCapitals;

@Service
@SuppressWarnings("deprecation")
public class Serv_Menu extends DB_Connection {
	
	@Autowired private Serv_Url serv_Url;
	
	private FirstInCapitals f = new FirstInCapitals();
	
	private class to_Object implements ParameterizedRowMapper<Menu>{
		@Override
		public Menu mapRow(ResultSet rs, int rows) throws SQLException {
			Menu me = new Menu();
			me.setId_men(rs.getInt("id_men"));
			me.setNombre(rs.getString("nombre"));
			try {
				me.setUrls(serv_Url.get_Urls_Assings_to_Menu(rs.getInt("id_men")));
			} catch (Exception e) {
				me.setUrls(null);
			}
			return me;
		}
	}
	
	private Integer generate_Id(){
		try {
			String sql = "SELECT MAX(id_men) FROM menu";
			return db.queryForInt(sql)+1;
		} catch (Exception e) {
			return 0;
		}
	}
	
	public Menu get_by_Id(Integer id_men){
		String sql = "SELECT * FROM menu WHERE id_men=?";
		return db.queryForObject(sql, new to_Object(), id_men);
	}
	
	public List<Menu> get_All(){
		String sql = "SELECT * FROM menu";
		return db.query(sql, new to_Object());
	}
	
	public List<Menu> get_Menus_Assigns_to_Rol(Integer id_rol){
		String sql = "SELECT * FROM menu WHERE id_men IN (SELECT id_men FROM rol_men WHERE id_rol=?)";
		return db.query(sql, new to_Object(), id_rol);
	}
	
	public List<Menu> get_Menus_without_assign_to_Menu(Integer id_rol){
		String sql = "SELECT * FROM menu WHERE id_men NOT IN (SELECT id_men FROM rol_men WHERE id_rol=?)";
		return db.query(sql, new to_Object(), id_rol);
	}
	
	public void add(Menu me){
		String sql = "INSERT INTO menu(id_men, nombre) VALUES(?,?)";
		db.update(sql, new Object[]{generate_Id(), f.FiC(me.getNombre())});
	}
	
	public void modify(Menu me){
		String sql = "UPDATE menu SET nombre=? WHERE id_men=?";
		db.update(sql, new Object[]{f.FiC(me.getNombre()), me.getId_men()});
	}
	
	public void assing_url_to_menu(Integer id_men, Integer id_url){
		String sql = "INSERT INTO men_url(id_men, id_url) VALUES(?,?)";
		db.update(sql, new Object[]{id_men, id_url});
	}
	
	public void delete(Integer id_men){
		delete_from_men_url(id_men);
		String sql = "DELETE FROM menu WHERE id_men=?";
		db.update(sql, id_men);
	}
	
	public void delete_from_men_url(Integer id_men){
		String sql = "DELETE FROM men_url WHERE id_men=?";
		db.update(sql, id_men);
	}
}