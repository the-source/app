package services;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import models.Usuario;

import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.stereotype.Service;

import tools.DB_Connection;
import tools.FirstInCapitals;

@Service
@SuppressWarnings("deprecation")
public class Serv_Usuario extends DB_Connection {
	
	private FirstInCapitals f = new FirstInCapitals();
	
	private class to_Object implements ParameterizedRowMapper<Usuario>{
		@Override
		public Usuario mapRow(ResultSet rs, int rows) throws SQLException {
			Usuario us = new Usuario();
			us.setId_us(rs.getInt("id_us"));
			us.setId_rol(rs.getInt("id_rol"));
			us.setNombre(rs.getString("nombre"));
			us.setCi(rs.getInt("ci"));
			us.setAp(rs.getString("ap"));
			us.setAm(rs.getString("am"));
			us.setDirecc(rs.getString("direcc"));
			us.setTelf(rs.getInt("telf"));
			us.setNombre_us(rs.getString("nombre_us"));
			us.setClave_us(rs.getString("clave_us"));
			us.setFoto(rs.getString("foto"));
			return us;
		}
	}
	
	private Integer generate_Id(){
		try {
			String sql = "SELECT MAX(id_us) FROM usuario";
			return db.queryForInt(sql)+1;
		} catch (Exception e) {
			return 0;
		}
	}
	
	public Usuario get_by_Id(Integer id_us){
		String sql = "SELECT * FROM usuario WHERE id_us=?";
		return db.queryForObject(sql, new to_Object(), id_us);
	}
	
	public Usuario start_session(String nombre_us, String clave_us){
		String sql = "SELECT * FROM usuario WHERE nombre_us = ? AND clave_us = ?";
		return db.queryForObject(sql, new to_Object(), new Object[]{nombre_us, clave_us});
	}
	
	public List<Usuario> get_All(){
		String sql = "SELECT * FROM usuario";
		return db.query(sql, new to_Object());
	}
	
	public List<Usuario> get_All_by_Rol(Integer id_rol){
		String sql = "SELECT * FROM usuario WHERE id_rol=?";
		return db.query(sql, new to_Object(), id_rol);
	}
	
	public void add(Usuario us){
		String sql = "INSERT INTO usuario(id_us, ci, nombre, ap, am, foto, direcc, telf, nombre_us, clave_us, verificado, id_rol) VALUES(?,?,?,?,?,?,?,?,?,?,?,?)";
		if(us.getId_rol()!=4){
			db.update(sql, new Object[]{generate_Id(), us.getCi(), f.FiC(us.getNombre()), f.FiC(us.getAp()), f.FiC(us.getAm()), us.getFoto(), us.getDirecc(), us.getTelf(), us.getNombre_us(), us.getClave_us(), us.getVerificado(), us.getId_rol()});
		}
		else{
			db.update(sql, new Object[]{generate_Id(), us.getCi(), f.FiC(us.getNombre()), us.getAp(), us.getAm(), us.getFoto(), us.getDirecc(), us.getTelf(), us.getNombre_us(), us.getClave_us(), us.getVerificado(), us.getId_rol()});
		}
	}
	
	public void modify(Usuario us){
		String sql = "UPDATE usuario SET ci=?, nombre=?, ap=?, am=?, foto=?, direcc=?, telf=?, nombre_us=?, clave_us=?, vefificado=?, id_rol=? WHERE id_us=?";
		if(us.getId_rol()!=4){
			db.update(sql, new Object[]{us.getCi(), f.FiC(us.getNombre()), f.FiC(us.getAp()), f.FiC(us.getAm()), us.getFoto(), us.getDirecc(), us.getTelf(), us.getNombre_us(), us.getClave_us(), us.getVerificado(), us.getId_rol(), us.getId_us()});
		}
		else{
			db.update(sql, new Object[]{us.getCi(), f.FiC(us.getNombre()), us.getAp(), us.getAm(), us.getFoto(), us.getDirecc(), us.getTelf(), us.getNombre_us(), us.getClave_us(), us.getVerificado(), us.getId_rol(), us.getId_us()});
		}
	}
	
	public void delete(Integer id_us){
		String sql = "DELETE FROM usuario WHERE id_us=?";
		db.update(sql, id_us);
	}
}