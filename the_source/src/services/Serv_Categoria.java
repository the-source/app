package services;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import models.Archivo;
import models.Categoria;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.stereotype.Service;

import tools.DB_Connection;
import tools.FirstInCapitals;

@Service
@SuppressWarnings("deprecation")
public class Serv_Categoria extends DB_Connection {
	
	@Autowired private Serv_Archivo serv_Archivo;
	
	private FirstInCapitals f = new FirstInCapitals();
	
	private class to_Object implements ParameterizedRowMapper<Categoria>{
		@Override
		public Categoria mapRow(ResultSet rs, int rows) throws SQLException {
			Categoria ca = new Categoria();
			ca.setId_cat(rs.getInt("id_cat"));
			ca.setNombre(rs.getString("nombre"));
			try {
				ca.setArchivos(serv_Archivo.get_All_by_Categoria(rs.getInt("id_cat")));
			} catch (Exception e) {
				ca.setArchivos(null);
			}
			return ca;
		}
	}
	
	private Integer generate_Id(){
		try {
			String sql = "SELECT MAX(id_cat) FROM categoria";
			return db.queryForInt(sql)+1;
		} catch (Exception e) {
			return 0;
		}
	}
	
	public Categoria get_by_Id(Integer id_cat){
		String sql = "SELECT * FROM categoria WHERE id_cat=?";
		return db.queryForObject(sql, new to_Object(), id_cat);
	}
	
	public List<Categoria> get_All(){
		String sql = "SELECT * FROM categoria";
		return db.query(sql, new to_Object());
	}
	
	public void add(Categoria ca){
		String sql = "INSERT INTO categoria(id_cat, nombre) VALUES(?,?)";
		db.update(sql, new Object[]{generate_Id(), f.FiC(ca.getNombre())});
	}
	
	public void modify(Categoria ca){
		String sql = "UPDATE categoria SET nombre=? WHERE id_cat=?";
		db.update(sql, new Object[]{f.FiC(ca.getNombre()), ca.getId_cat()});
	}
	
	public void delete(Integer id_cat){
		delete_from_archivo(id_cat);
		String sql = "DELETE FROM categoria WHERE id_cat=?";
		db.update(sql, id_cat);
	}
	
	public void delete_from_archivo(Integer id_cat){
		for(Archivo ar: serv_Archivo.get_All_by_Categoria(id_cat)){
			serv_Archivo.delete(ar.getId_arc());
		}
	}
}