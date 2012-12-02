package services;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import models.Evento;
import models.Topico;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.stereotype.Service;

import tools.DB_Connection;
import tools.FirstInCapitals;

@Service
@SuppressWarnings("deprecation")
public class Serv_Topico extends DB_Connection {
	
	@Autowired private Serv_Evento serv_Evento;
	
	private FirstInCapitals f = new FirstInCapitals();
	
	private class to_Object implements ParameterizedRowMapper<Topico>{
		@Override
		public Topico mapRow(ResultSet rs, int rows) throws SQLException {
			Topico tp = new Topico();
			tp.setId_top(rs.getInt("id_top"));
			tp.setNombre(rs.getString("nombre"));
			tp.setTipo(rs.getBoolean("tipo"));
			try {
				tp.setEventos(serv_Evento.get_All_by_Topico(rs.getInt("id_top")));
			} catch (Exception e) {
				e.printStackTrace();
				tp.setEventos(null);
			}
			return tp;
		}
	}
	
	private Integer generate_Id(){
		try {
			String sql = "SELECT MAX(id_top) FROM topico";
			return db.queryForInt(sql)+1;
		} catch (Exception e) {
			return 0;
		}
	}
	
	public Topico get_by_Id(Integer id_top){
		String sql = "SELECT * FROM topico WHERE id_top=?";
		return db.queryForObject(sql, new to_Object(), id_top);
	}
	
	public List<Topico> get_All(){
		String sql = "SELECT * FROM topico";
		return db.query(sql, new to_Object());
	}
	
	public List<Topico> get_All_tipo(Boolean tipo){
		String sql = "SELECT * FROM topico WHERE tipo=?";
		return db.query(sql, new to_Object(), tipo);
	}
	
	public void add(Topico tp){
		String sql = "INSERT INTO topico(id_top, nombre, tipo) VALUES(?,?,?)";
		db.update(sql, new Object[]{generate_Id(), f.FiC(tp.getNombre()), tp.getTipo()});
	}
	
	public void modify(Topico tp){
		String sql = "UPDATE topico SET nombre=?, tipo=? WHERE id_top=?";
		db.update(sql, new Object[]{f.FiC(tp.getNombre()), tp.getTipo(), tp.getId_top()});
	}
	
	public void delete(Integer id_top){
		delete_from_evento(id_top);
		String sql = "DELETE FROM topico WHERE id_top=?";
		db.update(sql, id_top);
	}
	
	public void delete_from_evento(Integer id_top){
		for(Evento ev: serv_Evento.get_All_by_Topico(id_top)){
			serv_Evento.delete(ev.getId_eve());
		}
	}
}