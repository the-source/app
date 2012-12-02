package tools;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

@SuppressWarnings("deprecation")
public class DB_Connection {
	
	protected SimpleJdbcTemplate db;
	
	@Resource(name="dataSource")
	public void setDataSource(DataSource dataSource) {
		this.db = new SimpleJdbcTemplate(dataSource);
	}
}