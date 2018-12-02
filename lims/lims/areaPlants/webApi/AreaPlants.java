package lims.areaPlants.webApi;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.core.handler.SessionFactory;
import lims.areaPlants.tools.AreaPlantTools;
import lims.areaPlants.vo.ParamsVo;
import pers.czf.commonUtils.ProjectUtils;
import pers.czf.dbManager.Dao;

@Controller
@RequestMapping("/api/lims/areaPlants/")
public class AreaPlants {
	
	@Autowired
	private Dao dao;
	

	/**
	 * 获取车间、装置信息
	 * @param request
	 * @return
	 */
	/*@RequestMapping(value="/area/{searchType}",produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String areas(HttpServletRequest request,@PathVariable("searchType") String searchType){
		JSONArray rd = new JSONArray();
		//type 为 1 ：查询所有的车间信息
		if("1".equalsIgnoreCase(searchType)){
			String sql = "select area_name id,area_name title,'area' type,MIN(sorter) sorter from PLANTS GROUP BY AREA_NAME order by sorter";
			rd = dao.queryJSONArray(sql);
		}else{
			String usrName = SessionFactory.getUsrName();
			String sql = "select t2.area_name id,t2.area_name title,'area' type,min(t2.sorter) sorter "
					+ " from PLANTMEMBERS t1,plants t2  "
					+ " where usrnam=? AND t1.area_name = t2.area_name and t1.plant=t2.plant group by t2.area_name order by sorter";
			rd = dao.queryJSONArray(sql, usrName);
		}
		return rd.toString();
	}
	*/
	/**
	 * 获取车间、装置信息
	 * @param request
	 * @return
	 */
	/*@RequestMapping(value="/plants/{searchType}",produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String plants(HttpServletRequest request,@PathVariable("searchType") String searchType){
		JSONArray rd = new JSONArray();
		//type 为 1 ：查询所有检测结果
		if("1".equalsIgnoreCase(searchType)){
			String sql = "select AREA_NAME,PLANT from PLANTS order by sorter";
			rd = dao.queryJSONArray(sql);
		}else{
			String usrName = SessionFactory.getUsrName();
			String sql = "select T1.area_name,T1.plant,T2.SORTER from PLANTMEMBERS T1,PLANTS T2 "
					+ " where usrnam=? AND T1.AREA_NAME=T2.AREA_NAME AND T1.PLANT=T2.PLANT order by T2.SORTER";
			rd = dao.queryJSONArray(sql, usrName);
		}
		return AreaPlantTools.areaPlantTree(rd).toString();
	}
	*/
	/**
	 * 获取车间、装置信息
	 * @param request
	 * @return
	 */
	/*@RequestMapping(value="/reportPlants",produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String reportPlants(HttpServletRequest request){
		JSONArray rd = new JSONArray();
		String sql = "select T2.area_name,T2.plant,T2.SORTER from PLANTS T2 "
				+ " where exists(select 1 from ct_orders t3 where t2.AREA_NAME=t3.area_name and t2.plant=t3.plant) order by T2.SORTER";
		rd = dao.queryJSONArray(sql);
		return AreaPlantTools.areaPlantTree(rd).toString();
	}
	*/
	/**
	 * 获取车间对应的装置信息
	 * @param request
	 * @return
	 */
	/*@RequestMapping(value="/areaPlants/{searchType}",produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String areaPlants(HttpServletRequest request,@PathVariable("searchType") String searchType){
		JSONArray rd = new JSONArray();
		String area = request.getParameter("areaName");
		//type 为 1 ：查询所有检测结果
		if("1".equalsIgnoreCase(searchType)){
			String sql = "select AREA_NAME||'@@'||PLANT id,PLANT title,'plant' type from PLANTS WHERE AREA_NAME=?";
			rd = dao.queryJSONArray(sql, area);
		}else{
			String usrName = SessionFactory.getUsrName();
			if(usrName==null){
				usrName=request.getParameter("usrName");
			}
			String sql = "select area_name||'@@'||plant id,plant title,'plant' type from PLANTMEMBERS where usrnam=? and area_name=? order by area_name,plant";
			rd = dao.queryJSONArray(sql, usrName,area);
		}
		
		return rd.toString();
	}
	*/
	/**
	 * 获取装置对应的采样点信息
	 * 在样品模版组中查询
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/planpoints",produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String points(HttpServletRequest request,ParamsVo param){

		String sql = "SELECT s.sample_point_id id,s.description title "
				+ " from sample_points s,sample_points_user spu "
				+ " where s.sample_point_id=spu.sample_point_id and spu.usrnam=? and s.area_name=? and s.plant=? order by s.sample_point_id";
		
		JSONArray rd = dao.queryJSONArray(sql,SessionFactory.getUsrName(), param.getAreaName(),param.getPlant());
		return rd.toString();
	}
	
	/**
	 * 获取车间装置下对应的样品信息
	 * 通过样品模版组配置查询
	 * @param request
	 * @return
	 */
	/*@RequestMapping(value="/sample",produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String sample(ParamsVo param){
		JSONArray rd = new JSONArray();
		if("area".equals(param.getSearchType())){
			String sql = "select distinct t2.matcode id,t2.matname title,t3.area_name "
					+ " from IPSAMPLEGROUPDETAILS t,SAMPLE_PROGRAMS t1,materials t2,sample_points t3 "
					+ " where t.sp_code=t1.sp_code and t.sample_point_id = t3.sample_point_id and t1.matcode=t2.matcode "
					+ " and t3.area_name=? order by title";
			rd = dao.queryJSONArray(sql, param.getAreaName());
		}else{
			String sql = "select distinct t2.matcode id,t2.matname title,t3.area_name "
					+ " from IPSAMPLEGROUPDETAILS t,SAMPLE_PROGRAMS t1,materials t2,sample_points t3 "
					+ " where t.sp_code=t1.sp_code and t.sample_point_id = t3.sample_point_id and t1.matcode=t2.matcode "
					+ " and t3.area_name=? and t3.plant=? order by title";
			rd = dao.queryJSONArray(sql, param.getAreaName(),param.getPlant());
		}
		
		return rd.toString();
	}
	
	*/
	
	  
	  @RequestMapping(value={"/plants/{searchType}"}, produces={"application/json;charset=UTF-8"})
	  @ResponseBody
	  public String plants(HttpServletRequest request, @PathVariable("searchType") String searchType)
	  {
	    JSONArray rd = new JSONArray();
	    if ("1".equalsIgnoreCase(searchType))
	    {
	      String sql = "select AREA_NAME,PLANT from PLANTS order by sorter";
	      rd = this.dao.queryJSONArray(sql);
	    }
	    else
	    {
	      String usrName = SessionFactory.getUsrName();
	      String sql = "select distinct t2.area_name,t2.plant,'1' from sample_points_user t1,sample_points t2  where usrnam=? AND t1.sample_point_id = t2.sample_point_id and t2.dept=? and t2.area_name is not null and t2.plant is not null  order by t2.area_name";
	      
	      rd = this.dao.queryJSONArray(sql, usrName, SessionFactory.getDept() );
	    }
	    return AreaPlantTools.areaPlantTree(rd).toString();
	  }
	  
	  @RequestMapping(value={"/area/{searchType}"}, produces={"application/json;charset=UTF-8"})
	  @ResponseBody
	  public String areas(HttpServletRequest request, @PathVariable("searchType") String searchType)
	  {
	    JSONArray rd = new JSONArray();
	    if ("1".equalsIgnoreCase(searchType))
	    {
	      String sql = "select area_name id,area_name title,'area' type,MIN(sorter) sorter from PLANTS GROUP BY AREA_NAME order by sorter";
	      rd = this.dao.queryJSONArray(sql);
	    }
	    else
	    {
	      String usrName = SessionFactory.getUsrName();
	      String sql = "select distinct t2.area_name id,t2.area_name title,'area' type,min(t2.sorter) sorter  from sample_points_user t1,sample_points t2   where usrnam=? AND t1.sample_point_id = t2.sample_point_id  group by t2.area_name order by sorter";
	      
	      rd = this.dao.queryJSONArray(sql,  usrName );
	    }
	    return rd.toString();
	  }
	  
	  /**
	   * 装置日报-分析项目
	   * @param request
	   * @return
	   */
	  @RequestMapping(value= {"/matAnalyte"},produces= {"application/json;charset=UTF-8"})
	  @ResponseBody
	  public String matAnalyte(HttpServletRequest request) {
	  	String sql = " select distinct s.area_name,s.plant,o.matcode,nvl(m.MATNAME,o.MATCODE) matname "
	  			+ "from orders o left join materials m on o.matcode=m.matcode,sample_points s  "
	  			+ "where o.sample_point_id=s.sample_point_id and o.matcode is not null "
	  			+ "and exists(select 1 from sample_points_user p "
	  			+ "where s.sample_point_id=p.sample_point_id  and  p.usrnam=?   )"
	  			+ "order by nlssort(s.area_name, 'NLS_SORT=SCHINESE_PINYIN_M'),nlssort(s.plant, 'NLS_SORT=SCHINESE_PINYIN_M'),o.matcode";
	    
	    List<Map<String, Object>> mats = this.dao.queryListMap(sql,SessionFactory.getUsrName());
	    //JSONArray jArray =AreaPlantTools.areaPlantMatsCheckTree(mats);
	   
	    return AreaPlantTools.areaPlantMatsTree(mats).toString();
	    //return AreaPlantTools.areaPlantMatsCheckTree(mats).toString();
	  }
	  
	  /**
	   * 车间-装置-样品 条件时间
	   * @param request
	   * @return
	   */
	  @RequestMapping(value={"/mat"}, produces={"application/json;charset=UTF-8"})
	  @ResponseBody
	  public String mat(HttpServletRequest request)
	  {
	    String startDate = request.getParameter("startDate");
	    String endDate = request.getParameter("endDate");
	    String usrName = SessionFactory.getUsrName();
	    
	    String sql = " select distinct s.area_name,s.plant,o.matcode,nvl(m.MATNAME,o.MATCODE) matname "
	  			+ "from orders o left join materials m on o.matcode=m.matcode,sample_points s  "
	  			+ "where o.sample_point_id=s.sample_point_id and o.matcode is not null "
	    		+ "and o.REALSAMPDATE>=TO_DATE(?,'yyyy-MM-dd hh24:mi:ss')  "
	    		+ "and o.realsampdate<=TO_DATE(?,'yyyy-MM-dd hh24:mi:ss')  "
	    		+ "and exists(select 1 from sample_points_user p where s.sample_point_id=p.sample_point_id  and  p.usrnam=?   ) "
	    		+ "order by nlssort(s.area_name, 'NLS_SORT=SCHINESE_PINYIN_M'),nlssort(s.plant, 'NLS_SORT=SCHINESE_PINYIN_M'),o.matcode";
	    
	    List<Map<String, Object>> mats = this.dao.queryListMap(sql,  startDate + " 00:00:00", endDate + " 23:59:59", usrName );
	    
	    return AreaPlantTools.areaPlantMatsTree(mats).toString();
	  }
	  /**
	   * 车间-装置-样品 条件时间
	   * @param request
	   * @return
	   */
	  @RequestMapping(value={"/matReport"}, produces={"application/json;charset=UTF-8"})
	  @ResponseBody
	  public String matReport(HttpServletRequest request)
	  {
	    String startDate = request.getParameter("startDate");
	    String endDate = request.getParameter("endDate");
	    String usrName = SessionFactory.getUsrName();
	    
	    String sql = " select distinct s.area_name,s.plant,o.matcode,nvl(m.MATNAME,o.MATCODE) matname "
	  			+ "from ct_orders o left join materials m on o.matcode=m.matcode,sample_points s  "
	  			+ "where o.sample_point_id=s.sample_point_id and o.matcode is not null "
	    		+ "and o.sampdate>=TO_DATE(?,'yyyy-MM-dd hh24:mi:ss')  "
	    		+ "and o.sampdate<=TO_DATE(?,'yyyy-MM-dd hh24:mi:ss')  "
	    		+ "and exists(select 1 from sample_points_user p where s.sample_point_id=p.sample_point_id  and  p.usrnam=?   ) "
	    		+ "order by nlssort(s.area_name, 'NLS_SORT=SCHINESE_PINYIN_M'),nlssort(s.plant, 'NLS_SORT=SCHINESE_PINYIN_M'),o.matcode";
	    
	    List<Map<String, Object>> mats = this.dao.queryListMap(sql,  startDate + " 00:00:00", endDate + " 23:59:59", usrName );
	    
	    return AreaPlantTools.areaPlantMatsTree(mats).toString();
	  }
	  @RequestMapping(value={"/points"}, produces={"application/json;charset=UTF-8"})
	  @ResponseBody
	  public String points(HttpServletRequest request)
	  {
	    String startDate = request.getParameter("startDate");
	    String endDate = request.getParameter("endDate");
	    
	    List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
	    
	    String usrName = SessionFactory.getUsrName();
	    
	    String sql = "select s.area_name,s.plant,s.sample_point_id,s.description "
	    		+ "from sample_points s "
	    		+ "where exists(select 1 from folders f,ORDERS O "
	    		+ "where f.folderno=o.folderno   "
	    		+ "and s.sample_point_id=o.sample_point_id   "
	    		+ "and o.REALSAMPDATE>=TO_DATE(?,'yyyy-MM-dd hh24:mi:ss')   "
	    		+ "and o.realsampdate<=TO_DATE(?,'yyyy-MM-dd hh24:mi:ss')) "
	    		+ "and exists(select 1 from sample_points_user p  "
	    		+ "where p.sample_point_id=s.sample_point_id  and p.usrnam=?  ) "
	    		+ " order by nlssort(s.area_name, 'NLS_SORT=SCHINESE_PINYIN_M'),nlssort(s.plant, 'NLS_SORT=SCHINESE_PINYIN_M'),sample_point_id";
	    
	    list = this.dao.queryListMap(sql,startDate + " 00:00:00", endDate + " 23:59:59", usrName );
	    
	    return AreaPlantTools.areaPlantPointsTree(list).toString();
	  }
	  
	  @RequestMapping(value={"/pointsRaw"}, produces={"application/json;charset=UTF-8"})
	  @ResponseBody
	  public String pointsRaw(HttpServletRequest request)
	  {
	    String startDate = request.getParameter("startDate");
	    String endDate = request.getParameter("endDate");
	    
	    List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
	    
	    String usrName = SessionFactory.getUsrName();
	    
	    String sql = "select s.area_name,s.plant,s.sample_point_id,s.description "
	    		+ "from sample_points s "
	    		+ "where exists(select 1 from batches b,folders f,ORDERS O "
	    		+ "where b.batchid = f.batchid and f.folderno=o.folderno "
	    		+ "and b.type='RAW' and f.dept=?  "
	    		+ "and s.sample_point_id=o.sample_point_id   "
	    		+ "and o.REALSAMPDATE>=TO_DATE(?,'yyyy-MM-dd hh24:mi:ss')   "
	    		+ "and o.realsampdate<=TO_DATE(?,'yyyy-MM-dd hh24:mi:ss')) "
	    		+ "and exists(select 1 from sample_points_user p  "
	    		+ "where p.sample_point_id=s.sample_point_id  and p.usrnam=?  )  "
	    		+ "order by nlssort(s.area_name, 'NLS_SORT=SCHINESE_PINYIN_M'),nlssort(s.plant, 'NLS_SORT=SCHINESE_PINYIN_M'),sample_point_id";
	    
	    list = this.dao.queryListMap(sql,  SessionFactory.getDept(), startDate + " 00:00:00", endDate + " 23:59:59", usrName );
	    
	    return AreaPlantTools.areaPlantPointsTree(list).toString();
	  }
	  
	  @RequestMapping(value={"/pointsTemp"}, produces={"application/json;charset=UTF-8"})
	  @ResponseBody
	  public String pointsTemp(HttpServletRequest request)
	  {
	    String startDate = request.getParameter("startDate");
	    String endDate = request.getParameter("endDate");
	    
	    List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
	    
	    String usrName = SessionFactory.getUsrName();
	    
	    String sql = "select s.area_name,s.plant,s.sample_point_id,s.description "
	    		+ "from sample_points s "
	    		+ "where exists(select 1 from batches b,folders f,ORDERS O "
	    		+ "where b.batchid = f.batchid and f.folderno=o.folderno "
	    		+ "and b.tasktype='临时加样'  "
	    		+ "and s.sample_point_id=o.sample_point_id   "
	    		+ "and o.REALSAMPDATE>=TO_DATE(?,'yyyy-MM-dd hh24:mi:ss')   "
	    		+ "and o.realsampdate<=TO_DATE(?,'yyyy-MM-dd hh24:mi:ss')) "
	    		+ "and exists(select 1 from sample_points_user p  "
	    		+ "where p.sample_point_id=s.sample_point_id  and p.usrnam=?  )  "
	    		+ "order by nlssort(s.area_name, 'NLS_SORT=SCHINESE_PINYIN_M'),nlssort(s.plant, 'NLS_SORT=SCHINESE_PINYIN_M'),sample_point_id";
	    
	    list = this.dao.queryListMap(sql,  startDate + " 00:00:00", endDate + " 23:59:59", usrName );
	    
	    return AreaPlantTools.areaPlantPointsTree(list).toString();
	  }
	  
	  @RequestMapping(value={"/pointsSampling"}, produces={"application/json;charset=UTF-8"})
	  @ResponseBody
	  public String pointsSampling(HttpServletRequest request)
	  {
	    String startDate = request.getParameter("startDate");
	    String endDate = request.getParameter("endDate");
	    
	    List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
	    
	    String usrName = SessionFactory.getUsrName();
	    
	    String sql = "select s.area_name,s.plant,s.sample_point_id,s.description "
	    		+ "from sample_points s "
	    		+ "where exists(select 1 from batches b,folders f,ORDERS O "
	    		+ "where b.batchid = f.batchid and f.folderno=o.folderno "
	    		+ "and b.tasktype='抽样检查'  "
	    		+ "and s.sample_point_id=o.sample_point_id   "
	    		+ "and o.REALSAMPDATE>=TO_DATE(?,'yyyy-MM-dd hh24:mi:ss')   "
	    		+ "and o.realsampdate<=TO_DATE(?,'yyyy-MM-dd hh24:mi:ss')) "
	    		+ "and exists(select 1 from sample_points_user p  "
	    		+ "where p.sample_point_id=s.sample_point_id  and p.usrnam=?  ) "
	    		+ " order by nlssort(s.area_name, 'NLS_SORT=SCHINESE_PINYIN_M'),nlssort(s.plant, 'NLS_SORT=SCHINESE_PINYIN_M'),sample_point_id";
	    
	    list = this.dao.queryListMap(sql,  startDate + " 00:00:00", endDate + " 23:59:59", usrName );
	    
	    return AreaPlantTools.areaPlantPointsTree(list).toString();
	  }
	  
	  @RequestMapping(value={"/reportPlants"}, produces={"application/json;charset=UTF-8"})
	  @ResponseBody
	  public String reportPlants(HttpServletRequest request)
	  {
	    String startDate = request.getParameter("startDate");
	    String endDate = request.getParameter("endDate");
	    if ((startDate == null) || (endDate == null) || ("".equals(startDate)) || ("".equals(endDate)))
	    {
	      Calendar calendar = Calendar.getInstance();
	      DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	      endDate = format.format(calendar.getTime());
	      calendar.add(11, -8);
	      startDate = format.format(calendar.getTime());
	    }
	    request.setAttribute("endDate", endDate);
	    request.setAttribute("startDate", startDate);
	    
	    List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
	    
	    String sql = "select distinct s.area_name,s.plant,s.sample_point_id,s.description "
	    		+ "FROM CT_ORDERS O inner JOIN SAMPLE_POINTS S "
	    		+ "ON O.SAMPLE_POINT_ID = S.SAMPLE_POINT_ID LEFT JOIN AREAS "
	    		+ "ON AREAS.AREA_NAME = S.AREA_NAME LEFT JOIN FOLDERS F ON F.FOLDERNO = O.FOLDERNO "
	    		+ "WHERE EXISTS(SELECT 1 FROM IPCOA_TEMPVALUES IP WHERE IP.ORDNO=O.ORDNO) "
	    		+ "and o.SAMPDATE>=TO_DATE(?,'yyyy-MM-dd') and o.SAMPDATE<=TO_DATE(?,'yyyy-MM-dd') "
	    		+ "and o.STATUS = 'Released' "
	    		+ "and F.dept in (select dept from departments c  where instr(c.releatedepts, ?) > 0) "
	    		+ "order by nlssort(s.area_name, 'NLS_SORT=SCHINESE_PINYIN_M'),nlssort(s.plant, 'NLS_SORT=SCHINESE_PINYIN_M'),s.sample_point_id";
	    
	    list = this.dao.queryListMap(sql, startDate, endDate, SessionFactory.getDept() );
	    return AreaPlantTools.areaPlantPointsTree(list).toString();
	  }
	  
	  @RequestMapping(value={"/reportPlantsApprove"}, produces={"application/json;charset=UTF-8"})
	  @ResponseBody
	  public String reportPlantsApprove(HttpServletRequest request)
	  {
	    String startDate = request.getParameter("startDate");
	    String endDate = request.getParameter("endDate");
	    if ((startDate == null) || (endDate == null) || ("".equals(startDate)) || ("".equals(endDate)))
	    {
	      Calendar calendar = Calendar.getInstance();
	      DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	      endDate = format.format(calendar.getTime());
	      calendar.add(11, -8);
	      startDate = format.format(calendar.getTime());
	    }
	    request.setAttribute("endDate", endDate);
	    request.setAttribute("startDate", startDate);
	    
	    List<Map<String, Object>> list = new ArrayList();
	    
	    String sql = "select distinct s.area_name,s.plant,s.sample_point_id,s.description FROM CT_ORDERS O inner JOIN SAMPLE_POINTS S ON O.SAMPLE_POINT_ID = S.SAMPLE_POINT_ID LEFT JOIN AREAS ON AREAS.AREA_NAME = S.AREA_NAME LEFT JOIN FOLDERS F ON F.FOLDERNO = O.FOLDERNO WHERE EXISTS(SELECT 1 FROM IPCOA_TEMPVALUES IP WHERE IP.ORDNO=O.ORDNO) and o.STATUS = 'Approved'  and F.dept in (select dept from departments c  where instr(c.releatedepts, ?) > 0) order by s.area_name,s.plant,s.sample_point_id";
	    
	    list = this.dao.queryListMap(sql,  SessionFactory.getDept() );
	    return AreaPlantTools.areaPlantPointsTree(list).toString();
	  }
	  
	  @RequestMapping(value={"/reportPlantsOOS"}, produces={"application/json;charset=UTF-8"})
	  @ResponseBody
	  public String reportPlantsOOS(HttpServletRequest request)
	  {
	    String startDate = request.getParameter("startDate");
	    String endDate = request.getParameter("endDate");
	    if ((startDate == null) || (endDate == null) || ("".equals(startDate)) || ("".equals(endDate)))
	    {
	      Calendar calendar = Calendar.getInstance();
	      DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	      endDate = format.format(calendar.getTime());
	      calendar.add(11, -8);
	      startDate = format.format(calendar.getTime());
	    }
	    request.setAttribute("endDate", endDate);
	    request.setAttribute("startDate", startDate);
	    
	    List<Map<String, Object>> list = new ArrayList();
	    
	    String sql = "select distinct s.area_name,s.plant,s.sample_point_id,s.description FROM CT_ORDERS O inner JOIN SAMPLE_POINTS S ON O.SAMPLE_POINT_ID = S.SAMPLE_POINT_ID LEFT JOIN AREAS ON AREAS.AREA_NAME = S.AREA_NAME LEFT JOIN FOLDERS F ON F.FOLDERNO = O.FOLDERNO WHERE EXISTS(SELECT 1 FROM IPCOA_TEMPVALUES IP WHERE IP.ORDNO=O.ORDNO) and o.SAMPDATE>=TO_DATE(?,'yyyy-MM-dd') and o.SAMPDATE<=TO_DATE(?,'yyyy-MM-dd') and o.STATUS = 'Released' and (o.flag = 'N' or o.flag='不合格') and F.dept in (select dept from departments c  where instr(c.releatedepts, ?) > 0) order by s.area_name,s.plant,s.sample_point_id";
	    
	    list = this.dao.queryListMap(sql,  startDate, endDate, SessionFactory.getDept() );
	    return AreaPlantTools.areaPlantPointsTree(list).toString();
	  }
	  
	  @RequestMapping(value={"/reportPlantsDone"}, produces={"application/json;charset=UTF-8"})
	  @ResponseBody
	  public String reportPlantsDone(HttpServletRequest request)
	  {
	    String startDate = request.getParameter("startDate");
	    String endDate = request.getParameter("endDate");
	    if ((startDate == null) || (endDate == null) || ("".equals(startDate)) || ("".equals(endDate)))
	    {
	      Calendar calendar = Calendar.getInstance();
	      DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	      endDate = format.format(calendar.getTime());
	      calendar.add(11, -8);
	      startDate = format.format(calendar.getTime());
	    }
	    request.setAttribute("endDate", endDate);
	    request.setAttribute("startDate", startDate);
	    
	    List<Map<String, Object>> list = new ArrayList();
	    
	    String sql = "select distinct s.area_name,s.plant,s.sample_point_id,s.description FROM CT_ORDERS O inner JOIN SAMPLE_POINTS S ON O.SAMPLE_POINT_ID = S.SAMPLE_POINT_ID LEFT JOIN AREAS ON AREAS.AREA_NAME = S.AREA_NAME LEFT JOIN FOLDERS F ON F.FOLDERNO = O.FOLDERNO WHERE EXISTS(SELECT 1 FROM IPCOA_TEMPVALUES IP WHERE IP.ORDNO=O.ORDNO) and o.SAMPDATE>=TO_DATE(?,'yyyy-MM-dd') and o.SAMPDATE<=TO_DATE(?,'yyyy-MM-dd') and o.STATUS = 'Released' and (o.flag <> 'N' or o.flag is null or o.flag = '合格') and F.dept in (select dept from departments c  where instr(c.releatedepts, ?) > 0) order by s.area_name,s.plant,s.sample_point_id";
	    
	    list = this.dao.queryListMap(sql, startDate, endDate, SessionFactory.getDept() );
	    return AreaPlantTools.areaPlantPointsTree(list).toString();
	  }
	  
	  @RequestMapping(value={"/areaPlants/{searchType}"}, produces={"application/json;charset=UTF-8"})
	  @ResponseBody
	  public String areaPlants(HttpServletRequest request, @PathVariable("searchType") String searchType)
	  {
	    JSONArray rd = new JSONArray();
	    String area = request.getParameter("areaName");
	    if ("1".equalsIgnoreCase(searchType))
	    {
	      String sql = "select AREA_NAME||'@@'||PLANT id,PLANT title,'plant' type from PLANTS WHERE AREA_NAME=?";
	      rd = this.dao.queryJSONArray(sql, area );
	    }
	    else
	    {
	      String usrName = SessionFactory.getUsrName();
	      if (usrName == null) {
	        usrName = request.getParameter("usrName");
	      }
	      String sql = "select area_name||'@@'||plant id,plant title,'plant' type from sample_points_user,sample_points where sample_points_user.sample_point_id=sample_points.sample_point_id and sample_points_user.usrnam=? and sample_points.area_name=? order by area_name,plant";
	      rd = this.dao.queryJSONArray(sql, usrName, area );
	    }
	    return rd.toString();
	  }
	  
	  @RequestMapping(value={"/plantPoints"}, produces={"application/json;charset=UTF-8"})
	  @ResponseBody
	  public String plantPoints(HttpServletRequest request, ParamsVo param)
	  {
	    String sql = "SELECT sample_point_id id,description title  from sample_points  where area_name=? and plant=? order by pointsorter,sample_point_id";
	    
	    JSONArray rd = this.dao.queryJSONArray(sql,  param.getAreaName(), param.getPlant() );
	    return rd.toString();
	  }
	  
	  @RequestMapping(value={"/sample"}, produces={"application/json;charset=UTF-8"})
	  @ResponseBody
	  public String sample(ParamsVo param)
	  {
	    JSONArray rd = new JSONArray();
	    if ("area".equals(param.getSearchType()))
	    {
	      String sql = "select distinct t2.matcode id,t2.matname title from IPSAMPLEGROUPDETAILS t,SAMPLE_PROGRAMS t1,materials t2,sample_points t3  where t.sp_code=t1.sp_code and t.sample_point_id = t3.sample_point_id and t1.matcode=t2.matcode  and t3.area_name=? order by title";
	      
	      rd = this.dao.queryJSONArray(sql,  param.getAreaName() );
	    }
	    else
	    {
	      String sql = "select distinct t2.matcode id,t2.matname title from IPSAMPLEGROUPDETAILS t,SAMPLE_PROGRAMS t1,materials t2,sample_points t3  where t.sp_code=t1.sp_code and t.sample_point_id = t3.sample_point_id and t1.matcode=t2.matcode  and t3.area_name=? and t3.plant=? order by title";
	      
	      rd = this.dao.queryJSONArray(sql, param.getAreaName(), param.getPlant() );
	    }
	    return rd.toString();
	  }
}
