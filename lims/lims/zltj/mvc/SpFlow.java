package lims.zltj.mvc;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import pers.czf.dbManager.Dao;

@Controller
@RequestMapping("lims/zltj/spflow")
public class SpFlow {
	
	@Autowired
	private Dao dao;
	
	@RequestMapping
	public String index(HttpServletRequest request){
		String busiId = request.getParameter("busiId");
		String flowType = request.getParameter("flowType");
		String sql="select t1.*,t2.fullname from sp_flow t1,users t2 where t1.oper_user=t2.usrnam and busi_id=? and flow_type=? order by oper_time";
		List<Map<String,Object>> list = dao.queryListMap(sql, busiId,flowType);
		request.setAttribute("spflow", list);
		return "lims/zltj/spflow";
	}
}
