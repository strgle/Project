package lims.dataSearch.webApi;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.core.handler.SessionFactory;

import lims.dataSearch.services.DailyService;
import pers.czf.commonUtils.MsgUtils;
import pers.czf.dbManager.Dao;

@Controller
@RequestMapping("/api/lims/dataSearch/result")
public class DailyResults {
	
	@Autowired
	private DailyService service;
	
	@Autowired
	private Dao dao;
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/detail",produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String detail(HttpServletRequest request){
		String pageKey = request.getParameter("pageKey");
		List<String> ordList = new ArrayList<String>();
		String rt = null;
		if(pageKey!=null&&!"".equals(pageKey)){
			Object orders = SessionFactory.getAttribute(pageKey);
			if(orders!=null){
				List<String> allOrders = (List<String>) orders;
				if(allOrders.size()>20){
					ordList = allOrders.subList(0, 20);
					rt = service.ordDetail(ordList).toString();
					allOrders.removeAll(ordList);
				}else if(allOrders.size()>0){
					rt = service.ordDetail(allOrders).toString();
					SessionFactory.removeAttribute(pageKey);
				}
			}
		}
		if(rt==null||rt.equals("")){
			return new JSONArray().toString();
		}else{
			return rt;
		}
	}
	
	@RequestMapping(value="/analytes/{pointId}",produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String analytes(@PathVariable("pointId") String pointId){
		//获取采样点下最近20个检测样品信息
		String sql = "select ordno from (SELECT ORDNO FROM ORDERS O WHERE O.SAMPLE_POINT_ID=? ORDER BY SAMPDATE DESC) where ROWNUM<=30";
		List<String> orders = dao.queryListValue(sql, String.class, pointId);
		
		String sql2 = "select distinct ct.sinonym from plantdaily CT,ANALYTES T WHERE CT.TESTCODE=T.TESTCODE AND CT.ANALYTE=T.ANALYTE AND T.ANALTYPE='N' and ct.ordno in (?)";
		List<String> analytes = dao.queryListValue(sql2, String.class,orders);
		JSONArray array = new JSONArray();
		for(String a:analytes){
			array.put(a);
		}
		return array.toString();
	}
	
	@RequestMapping(value="/sampdesc/{ordno}",produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String sampdesc(HttpServletRequest request,@PathVariable("ordno") String ordno){
		String sampdesc = request.getParameter("sampdesc");
		String sql = "update orders set sampdesc = ? where ordno=?";
		dao.excuteUpdate(sql, sampdesc,ordno);
		return MsgUtils.success().toString();
	}
	
}
