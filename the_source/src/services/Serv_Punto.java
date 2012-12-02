package services;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import models.Punto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.stereotype.Service;

import tools.DB_Connection;
import tools.FirstInCapitals;

@Service
@SuppressWarnings("deprecation")
public class Serv_Punto extends DB_Connection {
	
	@Autowired private Serv_Evento serv_Evento;
	
	private FirstInCapitals f = new FirstInCapitals();
	
	private class to_Object implements ParameterizedRowMapper<Punto>{
		@Override
		public Punto mapRow(ResultSet rs, int rows) throws SQLException {
			Punto tp = new Punto();
			/**String a=rs.getString(1);
			a=a.replace("(","");
			a=a.replace(")","");
			String v[]=a.split(",");
			System.out.println(a);
			System.out.println(" coordenadas "+v[0]+" "+v[0]);
			tp.setX(v[0]);
			tp.setY(v[1]);**/
			tp.setX(rs.getString("x"));
			tp.setX(rs.getString("y"));
			
			return tp;
		}
	}
	
	private Integer generate_Id(){
		try {
			String sql = "SELECT MAX(gid) FROM punto";
			return db.queryForInt(sql)+1;
		} catch (Exception e) {
			return 0;
		}
	}
	
	public List<Punto> get_By_evento(Integer id_eve){
		String sql = "select punto_coordenadas(?)";
		return db.query(sql, new to_Object(), id_eve);
	}
	
	public void add(Punto punto,String[] x,String[] y){
		String sqla="INSERT INTO punto(gid, the_geom, id_eve) VALUES ("+Integer.toString(generate_Id())+", 'MULTIPOINT(";
		for(int i=0; i<x.length; i++){
		sqla+="("+y[i]+" "+x[i]+")";
		if(i<x.length-1) sqla+=" , ";
		}
		sqla +=")', "+punto.getId_eve()+");";
		db.update(sqla);
	}
	/**
	public void modify(Topico tp){
		String sql = "UPDATE topico SET nombre=?, tipo=? WHERE id_top=?";
		db.update(sql, new Object[]{f.FiC(tp.getNombre()), tp.getTipo(), tp.getId_top()});
	}
	**/
	/**public void delete(Integer id_top){
		delete_from_evento(id_top);
		String sql = "DELETE FROM topico WHERE id_top=?";
		db.update(sql, id_top);
	}
	
	public void delete_from_evento(Integer id_top){
		for(Evento ev: serv_Evento.get_All_by_Topico(id_top)){
			serv_Evento.delete(ev.getId_eve());
		}
	}**/
}