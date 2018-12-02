package lims.screen.mvc;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import pers.czf.commonUtils.MsgUtils;
import pers.czf.dbManager.Dao;

@Controller
@RequestMapping("lims/screen/gq")
public class GqScreen {
	
	@Autowired
	private Dao dao;
	
	
	@RequestMapping
	public String index(HttpServletRequest request){
		request.setAttribute("m", request.getParameter("m"));
		//获取样品类型
		String sql = "select distinct mat_type from sample_points where mat_type is not null order by mat_type";
		List<String> matTypes = dao.queryListValue(sql, String.class);
		request.setAttribute("matTypes", matTypes);
		return "lims/screen/gq";
	}
	
	@RequestMapping("xs")
	public String xs(HttpServletRequest request){
		//获取样品类型
		String sql = "select distinct SALETYPE id,saletype title,'item' type from sample_points where SALETYPE is not null order by id";
		String saleTypes = dao.queryJSONArray(sql).toString();
		request.setAttribute("saleTypes", saleTypes);
		return "lims/screen/gqxs";
	}
	
	@RequestMapping(value="matType",produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String matType(HttpServletRequest request){
		String matType = request.getParameter("matType");
		//获取所有的采样点及存储样品信息
		String sql1 = "select sample_point_id,description,matcode,plant,plant_type from sample_points where mat_type=? order by sample_point_id";
		JSONArray points = dao.queryJSONArray(sql1, matType);
		return points.toString();
	}
	
	@RequestMapping(value="points",produces = "application/json;charset=UTF-8",method=RequestMethod.POST)
	@ResponseBody
	public String points(HttpServletRequest request){
		
		String matType = request.getParameter("matType");
		//检查有异常检测数据的采样点信息
		String sql = "select distinct t1.sample_point_id from sample_points t1 "
				+ "	where t1.mat_type=? ";
		List<String> points = dao.queryListValue(sql, String.class, matType);
		
		//检查最后一个检测样品是否正常
		JSONArray ycPoints = new JSONArray();
		String sql2 = "select * from (SELECT sampdate FROM CT_Q_DATA ct where sample_point_id=? group by sampdate order by sampdate desc) where rownum=1";
		String sql3 = "SELECT count(*) FROM CT_Q_DATA ct where sampdate = ? and ct.sample_point_id=? and S='OOS-A'";
		for(String p:points){
			List<Map<String,Object>> lists = dao.queryListMap(sql2, p);
			if(lists.size()>=1){
				Integer count = dao.queryValue(sql3, Integer.class, lists.get(0).get("sampdate"),p);
				if(count>0){
					ycPoints.put(p);
				}
			}
		}
		return ycPoints.toString();
	}
	

	@RequestMapping(value="allpoints",produces = "application/json;charset=UTF-8",method=RequestMethod.POST)
	@ResponseBody
	public String allpoints(HttpServletRequest request){
		JSONArray ycMatType = new JSONArray();
		//获取所有的mat_type信息
		String sql0 = "select distinct t1.mat_type from sample_points t1 where t1.mat_type is not null";
		List<String> matTypes = dao.queryListValue(sql0, String.class);
		
		//检查有异常检测数据的采样点信息
		String sql = "select distinct t1.sample_point_id from sample_points t1 "
				+ "	where t1.mat_type=? ";

		for(String matType:matTypes){
			List<String> points = dao.queryListValue(sql, String.class, matType);
			
			//检查最后一个检测样品是否正常
			String sql2 = "select * from (SELECT sampdate FROM CT_Q_DATA ct where sample_point_id=? group by sampdate order by sampdate desc) where rownum=1";
			String sql3 = "SELECT count(*) FROM CT_Q_DATA ct where sampdate = ? and ct.sample_point_id=? and S='OOS-A'";
			
			for(String p:points){
				List<Map<String,Object>> lists = dao.queryListMap(sql2, p);
				if(lists.size()>=1){
					Integer count = dao.queryValue(sql3, Integer.class, lists.get(0).get("sampdate"),p);
					if(count>0){
						ycMatType.put(matType);
						break;
					}
				}
			}
		}

		return ycMatType.toString();
	}
	
	@RequestMapping(value="salePoints")
	public String salePoints(HttpServletRequest request){
		String saleType = request.getParameter("id");
		
		
		//获取所有的采样点信息
		String sql = "select t2.area_name,t2.plant,t2.description,t2.matcode,t2.sample_point_id,nvl(t1.sample_point_id,0) ischecked  "
				+ " from SALE_GQ t1 right join sample_points t2 on  t1.sample_point_id=t2.sample_point_id "
				+ " where t2.saletype=? order by area_name,plant,description";

		List<Map<String,Object>> points = dao.queryListMap(sql, saleType);
		request.setAttribute("saleType", saleType);
		request.setAttribute("points", points);
		
		return "lims/screen/salePoints";
	}
	
	
	@RequestMapping(value="saleSet",produces = "application/json;charset=UTF-8",method=RequestMethod.POST)
	@ResponseBody
	public String saleSet(HttpServletRequest request){
		String saleType = request.getParameter("id");
		String pointId = request.getParameter("pointId");
		String isCheck = request.getParameter("isCheck");
		//检查采样点是否存在
	
		if(isCheck.equals("false")){
			dao.excuteUpdate("delete from SALE_GQ where sample_point_id = ?", pointId);
			return MsgUtils.success().toString();
		}else{
			//获取最新的检测结果信息			
			String sql2 = "insert into SALE_GQ(mat_type,sample_point_id) values(?,?)";
			dao.excuteUpdate(sql2, saleType,pointId);
			return MsgUtils.success().toString();
		}
	}
	
	@RequestMapping(value="areaPlant",produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String areaPlant(HttpServletRequest request){
		String area = request.getParameter("area");
		String plant = request.getParameter("plant");
		String t = request.getParameter("t");
		String m = request.getParameter("m");
		if("0".equals(m)){
			return MsgUtils.fail("无超标").toString();
		}
		//获取所有的采样点及存储样品信息
		
		String sql1 = "select count(*) from ct_q_data t1,orders t2 where t1.ordno=t2.ordno and t1.s='OOS-A' and t1.insertdate>sysdate-"+t+"/24 and t2.area_name=? and t2.plant=?";
		
		Integer count = dao.queryValue(sql1, Integer.class, area,plant);
		if(count>0){
			return MsgUtils.success().toString();
		}else{
			return MsgUtils.fail("无超标").toString();
		}
	}
	
	/**
	 * 装置流程图  装置、样品信息
	 * @param request
	 * @return
	 */
	@RequestMapping("/flowAreaPlant")
	public String flowAreaPlant(HttpServletRequest request){
		String area = request.getParameter("area");
		String plant = request.getParameter("plant");
		
		//获取所有的样品编号
		String sql1 = "select * from (select t2.ordno from orders t2 where exists(select 1 from ct_q_data t1 where t1.ordno=t2.ordno and t1.s='OOS-A') and t2.area_name=? and t2.plant=? order by t2.sampdate desc) where rownum<=20";
		List<String> ordList= dao.queryListValue(sql1,String.class,area,plant);

		//获取样品信息
		String sql2 = "select t1.ordno,to_char(t1.sampdate,'yyyy/MM/dd hh24:mi') sampdate,t1.matcode,t1.matname,t1.status,t3.batchname,"
				+ " t1.area_name,t1.plant,t1.sample_point_id,t1.pointdesc,t3.tasktype,t3.type,to_char(t1.apprdate,'yyyy/MM/dd hh24:mi:ss') apprdate,"
				+ " t1.grade "
				+ " from orders t1,folders t2,batches t3 "
				+ " where  t1.ordno=t1.ordgroup and t1.folderno=t2.folderno and t2.batchid=t3.batchid and t1.ordno in (?) order by sampdate desc";
		if(!ordList.isEmpty()){
			List<Map<String,Object>> list = dao.queryListMap(sql2, ordList);
			request.setAttribute("points",list);
		}
		
		return "lims/screen/sampleList";
	}
	
	
	@RequestMapping(value="areaMatCode",produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String areaMatCode(HttpServletRequest request){
		String area = request.getParameter("area");
		String[] areas = area.split(",");
		String matcode = request.getParameter("matcode");
		String[] mats = matcode.split(",");
		String m = request.getParameter("m");
		if("0".equals(m)){
			return MsgUtils.fail("无超标").toString();
		}
		//获取所有的采样点及存储样品信息
		String sql1 = "select * from (select o.ordno,o.status,o.grade from orders o where  o.area_name in (?) and o.matcode in (?)  and o.status in ('Done','OOS') and grade is not null  and o.ordno=o.ordgroup order by o.sampdate desc) where rownum = 1";
		List<Map<String,Object>> list = dao.queryListMap(sql1, new Object[]{areas,mats});
		if(!list.isEmpty()){
			String grade = list.get(0).get("grade").toString();
			if("OOS-A".equals(grade)){
				return MsgUtils.success().toString();
			}else{
				return MsgUtils.fail("无超标").toString();
			}
		}else{
			return MsgUtils.fail("无超标").toString();
		}
	}
	

	@RequestMapping(value="flowAreaMatCode")
	public String flowAreaMatCode(HttpServletRequest request){
		String area = request.getParameter("area");
		String[] areas = area.split(",");
		String matcode = request.getParameter("matcode");
		String[] mats = matcode.split(",");
		
		//获取所有的采样点及存储样品信息
		String sql1 = "select ordno from (select o.ordno from orders o where  o.area_name in (?) and o.matcode in (?)  and o.status in ('Done','OOS') and grade is not null  and o.ordno=o.ordgroup order by o.sampdate desc) where rownum <= 10";
		List<String> ordList = dao.queryListValue(sql1,String.class,new Object[]{areas,mats});
		
		//获取样品信息
		String sql2 = "select t1.ordno,to_char(t1.sampdate,'yyyy/MM/dd hh24:mi') sampdate,t1.matcode,t1.matname,t1.status,t3.batchname,"
				+ " t1.area_name,t1.plant,t1.sample_point_id,t1.pointdesc,t3.tasktype,t3.type,to_char(t1.apprdate,'yyyy/MM/dd hh24:mi:ss') apprdate,"
				+ " t1.grade "
				+ " from orders t1,folders t2,batches t3 "
				+ " where  t1.ordno=t1.ordgroup and t1.folderno=t2.folderno and t2.batchid=t3.batchid and t1.ordno in (?) order by sampdate desc";
		
		if(!ordList.isEmpty()){
			List<Map<String,Object>> list = dao.queryListMap(sql2, ordList);
			request.setAttribute("points",list);
		}
		
		return "lims/screen/sampleList";
	}
	

	
	
	
}
