package lims.screen.mvc;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import pers.czf.dbManager.Dao;


@Controller
@RequestMapping("lims/screen/msg")
public class ScreenMsg {
	@Autowired
	private Dao dao;
	
	@RequestMapping()
	public String index(HttpServletRequest request){
		String sql = "select * from screen_msg where flag='LIB'";
		Map<String,Object> msg = dao.queryMap(sql);
		request.setAttribute("msg", msg);
		return "lims/screen/msg";
	}
	
	@RequestMapping("save")
	public String save(HttpServletRequest request){
		String msg1 = request.getParameter("msg1");
		String msg2 = request.getParameter("msg2");
		String msg3 = request.getParameter("msg3");
		String existsSql = "select count(*) from screen_msg where flag='LIB'";
		if(dao.queryValue(existsSql, Integer.class)>0){
			String updateSql = "update screen_msg set msg1=?,msg2=?,msg3=? where flag='LIB'";
			dao.excuteUpdate(updateSql, msg1,msg2,msg3);
		}else{
			String updateSql = "insert into screen_msg(flag,msg1,msg2,msg3) values(?,?,?,?)";
			dao.excuteUpdate(updateSql, "LIB",msg1,msg2,msg3);
		}
		
		Map<String,Object> msg = new HashMap<String, Object>();
		msg.put("msg1", msg1);
		msg.put("msg2", msg2);
		msg.put("msg3", msg3);
		request.setAttribute("msg", msg);
		return "lims/screen/msg";
	}
}
