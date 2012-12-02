package services;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import models.Rol;
import models.Usuario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.stereotype.Service;

import tools.DB_Connection;
import tools.FirstInCapitals;

@Service
@SuppressWarnings("deprecation")
public class Serv_Rol extends DB_Connection {
	
	@Autowired private Serv_Menu serv_Menu;
	@Autowired private Serv_Usuario serv_Usuario;
	
	private FirstInCapitals f = new FirstInCapitals();
	
	private class to_Object implements ParameterizedRowMapper<Rol>{
		@Override
		public Rol mapRow(ResultSet rs, int rows) throws SQLException {
			Rol rol = new Rol();
			rol.setId_rol(rs.getInt("id_rol"));
			rol.setNombre(rs.getString("nombre"));
			try {
				rol.setMenus(serv_Menu.get_Menus_Assigns_to_Rol(rs.getInt("id_rol")));
			} catch (Exception e) {
				rol.setMenus(null);
			}
			try {
				rol.setUsuarios(serv_Usuario.get_All_by_Rol(rs.getInt("id_rol")));
			} catch (Exception e) {
				rol.setUsuarios(null);
			}
			return rol;
		}
	}
	
	private Integer generate_Id(){
		try {
			String sql = "SELECT MAX(id_rol) FROM rol";
			return db.queryForInt(sql)+1;
		} catch (Exception e) {
			return 0;
		}
	}
	
	public Rol get_by_Id(Integer id_rol){
		String sql = "SELECT * FROM rol WHERE id_rol=?";
		return db.queryForObject(sql, new to_Object(), id_rol);
	}
	
	public List<Rol> get_All(){
		String sql = "SELECT * FROM rol";
		return db.query(sql, new to_Object());
	}
	
	public void add(Rol rol){
		String sql = "INSERT INTO rol(id_rol, nombre) VALUES(?,?)";
		db.update(sql, new Object[]{generate_Id(), f.FiC(rol.getNombre())});
	}
	
	public void modify(Rol rol){
		String sql = "UPDATE rol SET nombre=? WHERE id_rol=?";
		db.update(sql, new Object[]{f.FiC(rol.getNombre()), rol.getId_rol()});
	}
	
	public void assign_menu_to_rol(Integer id_rol, Integer id_men){
		String sql = "INSERT INTO rol_men(id_rol, id_men) VALUES(?,?)";
		db.update(sql, new Object[]{id_rol, id_men});
	}
	
	public void delete(Integer id_rol){
		delete_from_rol_men(id_rol);
		delete_Users_from_Rol(id_rol);
		String sql = "DELETE FROM rol WHERE id_rol=?";
		db.update(sql, id_rol);
	}
	
	public void delete_from_rol_men(Integer id_rol){
		String sql = "DELETE FROM rol_men WHERE id_rol=?";
		db.update(sql, id_rol);
	}
	
	public void delete_Users_from_Rol(Integer id_rol){
		for(Usuario us: serv_Usuario.get_All_by_Rol(id_rol)){
			serv_Usuario.delete(us.getId_us());
		}
	}
}