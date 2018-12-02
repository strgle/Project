package lims.dataLedger.mvc;

import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.core.utils.KeyUtils;
import lims.dataLedger.services.ProdMesService;
import pers.czf.commonUtils.DateUtils;
import pers.czf.commonUtils.MsgUtils;
import pers.czf.dbManager.Dao;

@Controller
@RequestMapping("lims/dataLedger/prodMes")
public class ProdMesAction {
	
	@Autowired
	private Dao dao;
	
	@Autowired
	private ProdMesService service;
	
	@RequestMapping
	public String index(HttpServletRequest request){
		return "lims/dataLedger/prodMes";
	}
	
	@RequestMapping("monitorInfo")
	public String monitorInfo(HttpServletRequest request){
		String areaName = request.getParameter("areaName");
		String plant = request.getParameter("plant");
		
		request.setAttribute("areaName", areaName);
		request.setAttribute("plant", plant);
		
		//查询生产监控项目
		String sql1 = "select id,prod_condition,units from MES_PROD_CONDITION where area_name = ? and plant = ? order by sort_num ";
		List<Map<String,Object>> data1 = dao.queryListMap(sql1, areaName,plant);
		request.setAttribute("data1", data1);
		
		//查询生产监控时间
		String sql2 = "select id,prod_TIME from MES_PROD_TIME where area_name = ? and plant = ? order by sort_num ";
		List<Map<String,Object>> data2 = dao.queryListMap(sql2, areaName,plant);
		request.setAttribute("data2", data2);
		
		//查询物料平衡统计时间
		String sql3 = "select id,sta_TIME from MES_MB_time where area_name = ? and plant = ? order by sort_num ";
		List<Map<String,Object>> data3 = dao.queryListMap(sql3, areaName,plant);
		request.setAttribute("data3", data3);
		
		//查询物料平衡进方项目
		String sql4 = "select id,prod_condition,units from mes_mb_CONDITION where area_name = ? and plant = ? and type='IN' order by sort_num ";
		List<Map<String,Object>> data4 = dao.queryListMap(sql4, areaName,plant);
		request.setAttribute("data4", data4);
		
		//查询物料平衡出方项目
		String sql5 = "select id,prod_condition,units from mes_mb_CONDITION where area_name = ? and plant = ? and type='OUT' order by sort_num ";
		List<Map<String,Object>> data5 = dao.queryListMap(sql5, areaName,plant);
		request.setAttribute("data5", data5);
		
		return "lims/dataLedger/monitorInfo";
	}
	
	@RequestMapping(value="addMinfo/{type}",produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String addMinfo(HttpServletRequest request,@PathVariable("type") String type){
		String areaName = request.getParameter("areaName");
		String plant = request.getParameter("plant");
		String value = request.getParameter("value");
		String units = request.getParameter("units");
		String sql = null;
		String sql0 = null;
		int sortNum = 0;
		try {
			switch(type){
				case "1":
					sql0 = "select nvl(max(sort_num),0) from MES_PROD_CONDITION where area_name=? and plant=? ";
					sortNum = dao.queryValue(sql0,Integer.class,areaName, plant);
					sql = "insert into MES_PROD_CONDITION values(?,?,?,?,?,?)";
					dao.excuteUpdate(sql,KeyUtils.uuid(),areaName,plant,value,units,sortNum+1);
					break;
				case "2":
					sql0 = "select nvl(max(sort_num),0) from MES_PROD_TIME where area_name=? and plant=? ";
					sortNum = dao.queryValue(sql0,Integer.class,areaName, plant);
					sql = "insert into MES_PROD_TIME values(?,?,?,?,?)";
					dao.excuteUpdate(sql,KeyUtils.uuid(),areaName,plant,value,sortNum+1);
					break;
				case "3":
					sql0 = "select nvl(max(sort_num),0) from MES_MB_time where area_name=? and plant=? ";
					sortNum = dao.queryValue(sql0,Integer.class,areaName, plant);
					sql = "insert into MES_MB_time values(?,?,?,?,?)";
					dao.excuteUpdate(sql,KeyUtils.uuid(),areaName,plant,value,sortNum+1);
					break;
				case "4":
					sql0 = "select nvl(max(sort_num),0) from mes_mb_CONDITION where area_name=? and plant=? ";
					sortNum = dao.queryValue(sql0,Integer.class,areaName, plant);
					sql = "insert into mes_mb_CONDITION values(?,?,?,?,?,?,?)";
					dao.excuteUpdate(sql,KeyUtils.uuid(),areaName,plant,value,units,sortNum+1,"IN");
					break;
				case "5":
					sql0 = "select nvl(max(sort_num),0) from mes_mb_CONDITION where area_name=? and plant=? ";
					sortNum = dao.queryValue(sql0,Integer.class,areaName, plant);
					sql = "insert into mes_mb_CONDITION values(?,?,?,?,?,?,?)";
					dao.excuteUpdate(sql,KeyUtils.uuid(),areaName,plant,value,units,sortNum+1,"OUT");
					break;
				default:
					break;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return MsgUtils.fail("添加的项目信息已经存在，不能重复添加！").toString();
		}

		return MsgUtils.success().toString();
	}
	
	@RequestMapping(value="delMinfo/{type}/{id}",produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String delMinfo(@PathVariable("type") String type,@PathVariable("id") String id){
		String table = "";
		switch(type){
			case "1":
				table = "MES_PROD_CONDITION";
				break;
			case "2":
				table = "MES_PROD_TIME";
				break;
			case "3":
				table = "MES_MB_time";
				break;
			case "4":
				table = "mes_mb_CONDITION";
				break;
			case "5":
				table = "mes_mb_CONDITION";
				break;
			default:
				break;
		}
		dao.excuteUpdate("delete from "+table+" where id=?", id);
		return MsgUtils.success().toString();
	}
	
	@RequestMapping(value="moveUp/{type}/{id}",produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String moveUp(@PathVariable("type") String type,@PathVariable("id") String id){
		String table = "";
		switch(type){
			case "1":
				table = "MES_PROD_CONDITION";
				break;
			case "2":
				table = "MES_PROD_TIME";
				break;
			case "3":
				table = "MES_MB_time";
				break;
			case "4":
				table = "mes_mb_CONDITION";
				break;
			case "5":
				table = "mes_mb_CONDITION";
				break;
			default:
				break;
		}
		//获取当前序号
		if(type.equals("1")||type.equals("2")||type.equals("3")){
			String sql = "select area_name,plant,sort_num from "+table+" where id=?";
			Map<String,Object> mi = dao.queryMap(sql,id);
			
			String sql1 = "select max(sort_num) sort_num FROM "+table+" WHERE SORT_NUM<? and area_name=? and plant=?  ";
			Integer sortNum = dao.queryValue(sql1, Integer.class, mi.get("sortNum"),mi.get("areaName"),mi.get("plant"));
			
			String sql3 = "update "+table+" set sort_num=? where id = ?";
			dao.excuteUpdate(sql3, sortNum,id);
			
			String sql4 = "update "+table+" set sort_num=? where  id <> ? and sort_num=? and area_name=? and plant=? ";
			dao.excuteUpdate(sql4, mi.get("sortNum"),id,sortNum,mi.get("areaName"),mi.get("plant"));
		}else if(type.equals("4")||type.equals("5")){
			String sql = "select area_name,plant,sort_num,type from "+table+" where id=?";
			Map<String,Object> mi = dao.queryMap(sql,id);
			
			String sql1 = "select max(sort_num) sort_num FROM "+table+" WHERE SORT_NUM<? and area_name=? and plant=?  and type=?";
			Integer sortNum = dao.queryValue(sql1, Integer.class, mi.get("sortNum"),mi.get("areaName"),mi.get("plant"),mi.get("type"));
			
			String sql3 = "update "+table+" set sort_num=? where id = ?";
			dao.excuteUpdate(sql3, sortNum,id);
			
			String sql4 = "update "+table+" set sort_num=? where  id <> ? and sort_num=? and type=? and area_name=? and plant=?";
			dao.excuteUpdate(sql4, mi.get("sortNum"),id,sortNum,mi.get("type"),mi.get("areaName"),mi.get("plant"));
		}
		
		return MsgUtils.success().toString();
	}
	
	@RequestMapping("areaProdMes")
	public String prodMes(HttpServletRequest request){
		return "lims/dataLedger/areaProdMes";
	}
	
	@RequestMapping("prodMesSet")
	public String prodMesSet(HttpServletRequest request){
		String areaName = request.getParameter("areaName");
		String plant = request.getParameter("plant");
		String prodDate = request.getParameter("prodDate");
		request.setAttribute("areaName", areaName);
		request.setAttribute("plant", plant);
		request.setAttribute("prodDate", prodDate);
		service.prodMes1(request, areaName, plant, prodDate,prodDate);
		return "lims/dataLedger/prodMesSet";
	}
	
	@RequestMapping("prodMesShow")
	public String prodMesShow(HttpServletRequest request){
		String areaName = request.getParameter("areaName");
		String plant = request.getParameter("plant");
		request.setAttribute("areaName", areaName);
		request.setAttribute("plant", plant);
		
		String prodDate = request.getParameter("prodDate");
		if(prodDate == null || prodDate.equals("")){
			prodDate = DateUtils.DateFormat(new Date(), "yyyy-MM-dd");
		}
		request.setAttribute("prodDate", prodDate);
		//判断信息是否存在
		String sql = "select count(*) from MES_PROD_MES where area_name=? and plant=? and prod_date=?";
		if(dao.queryValue(sql, Integer.class, areaName, plant, prodDate)>0){
			service.prodMes2(request, areaName, plant, prodDate,prodDate);
		}else{
			service.prodMes1(request, areaName, plant, prodDate,prodDate);
		};
		
		
		return "lims/dataLedger/prodMesShow";
	}
	
	@RequestMapping("prodMesAdd")
	public String prodMesAdd(HttpServletRequest request){
		
		String areaName = request.getParameter("areaName");
		String plant = request.getParameter("plant");
		String prodDate = request.getParameter("prodDate");
		
		//删除维护的值
		String sql = "delete from MES_PROD_MES where prod_date = ? and area_name = ? and plant=?";
		dao.excuteUpdate(sql, prodDate,areaName,plant);
		
		//获取所有表单值
		Map<String,String[]> params = request.getParameterMap();
		String sql2 = "insert into MES_PROD_MES(area_name,plant,prod_date,prod_time,prod_condition,prod_value) values(?,?,?,?,?,?)";
		for (Map.Entry<String, String[]> entry : params.entrySet()) {
			String key = entry.getKey();
			if(key.contains("@@")){
				String[] keys = key.split("@@");
				String[] value = entry.getValue();
				dao.excuteUpdate(sql2, areaName,plant,prodDate,keys[1],keys[0],value[0]);
			}
		}
		
		//更新排序及单位
		String sql3 = "update MES_PROD_MES t1 set (units,sort_num) =  "
				+ " (select units,sort_num from MES_PROD_CONDITION t2 "
				+ " where t1.area_name=t2.area_name and t1.plant=t2.plant and t1.prod_condition = t2.prod_condition) "
				+ " where t1.area_name=? and t1.plant=? and t1.prod_date=?";
		dao.excuteUpdate(sql3, areaName,plant,prodDate);
		
		String sql4 = "update MES_PROD_MES t1 set time_num = "
				+ " (select sort_num from MES_PROD_TIME t2 "
				+ " where t1.area_name=t2.area_name and t1.plant=t2.plant and t1.prod_time = t2.prod_time) "
				+ " where t1.area_name=? and t1.plant=? and t1.prod_date=?";
		dao.excuteUpdate(sql4, areaName,plant,prodDate);
		
		return "forward:prodMesShow";
	}
	
	@RequestMapping("mb")
	public String mb(HttpServletRequest request){
		return "lims/dataLedger/mb";
	}
	
	@RequestMapping("mbShow")
	public String mbShow(HttpServletRequest request){
		String areaName = request.getParameter("areaName");
		String plant = request.getParameter("plant");
		request.setAttribute("areaName", areaName);
		request.setAttribute("plant", plant);
		
		String prodDate = request.getParameter("prodDate");
		if(prodDate == null || prodDate.equals("")){
			prodDate = DateUtils.DateFormat(new Date(), "yyyy-MM-dd");
		}
		request.setAttribute("prodDate", prodDate);
		
		//判断信息是否存在
		String sql = "select count(*) from MES_mb_MES where area_name=? and plant=? and prod_date=?";
		if(dao.queryValue(sql, Integer.class, areaName, plant, prodDate)>0){
			service.mbMes2(request, areaName, plant, prodDate,prodDate);
			request.setAttribute("columnNum", 1);
		}else{
			service.mbMes0(request, areaName, plant, prodDate,prodDate);
			request.setAttribute("columnNum", 0);
		};
		return "lims/dataLedger/mbShow";
	}
	
	@RequestMapping("mbSet")
	public String mbSet(HttpServletRequest request){
		String areaName = request.getParameter("areaName");
		String plant = request.getParameter("plant");
		String prodDate = request.getParameter("prodDate");
		request.setAttribute("areaName", areaName);
		request.setAttribute("plant", plant);
		request.setAttribute("prodDate", prodDate);
		
		service.mbMes1(request, areaName, plant, prodDate,prodDate);
		
		return "lims/dataLedger/mbSet";
	}
	
	@RequestMapping("mbAdd")
	public String mbAdd(HttpServletRequest request){
		
		String areaName = request.getParameter("areaName");
		String plant = request.getParameter("plant");
		String prodDate = request.getParameter("prodDate");
		
		//删除维护的值
		String sql = "delete from mes_mb_mes where prod_date = ? and area_name = ? and plant=?";
		dao.excuteUpdate(sql, prodDate,areaName,plant);
		
		//获取所有表单值
		Map<String,String[]> params = request.getParameterMap();
		String sql2 = "insert into mes_mb_mes(area_name,plant,prod_date,prod_time,prod_condition,prod_value1,prod_value2,type) values(?,?,?,?,?,?,?,?)";
		for (Map.Entry<String, String[]> entry : params.entrySet()) {
			String key = entry.getKey();
			if(key.contains("@@")){
				String[] keys = key.split("@@");
				String[] value = entry.getValue();
				dao.excuteUpdate(sql2, areaName,plant,prodDate,keys[1],keys[0],value[0],value[1],keys[2]);
			}
		}
		
		//更新排序及单位
		String sql3 = "update mes_mb_mes t1 set (units,sort_num) =  "
				+ " (select units,sort_num from MES_MB_CONDITION t2 "
				+ " where t1.area_name=t2.area_name and t1.plant=t2.plant and t1.prod_condition = t2.prod_condition and t1.type=t2.type) "
				+ " where t1.area_name=? and t1.plant=? and t1.prod_date=?";
		dao.excuteUpdate(sql3, areaName,plant,prodDate);
		
		String sql4 = "update MES_mb_MES t1 set time_num = "
				+ " (select sort_num from MES_mb_TIME t2 "
				+ " where t1.area_name=t2.area_name and t1.plant=t2.plant and t1.prod_time = t2.sta_time) "
				+ " where t1.area_name=? and t1.plant=? and t1.prod_date=?";
		dao.excuteUpdate(sql4, areaName,plant,prodDate);
		
		return "forward:mbShow";
	}
}
