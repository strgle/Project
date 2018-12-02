package pers.czf.tools;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import pers.czf.dbManager.Dao;
import pers.czf.dbManager.PrimaryKey;

public class LogFactory {
	
	private static Dao dao = null;
	private static String sql = "insert into sys_logs(id,create_time,error_msg) values(?,?,?)";
	
	public static void log(Exception e) {
		StringWriter sw = new StringWriter();  
        e.printStackTrace(new PrintWriter(sw, true));  
        String str = sw.toString();
        dao.excuteUpdate(sql, PrimaryKey.uuid(),LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),str);
	}
	/**
	 * 数据库连接设置
	 * @param dao
	 */
	public void setDao(Dao dao){
		LogFactory.dao = dao;
	}
}
