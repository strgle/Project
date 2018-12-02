package lims.zltj.mvc;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.core.handler.SessionFactory;

import lims.zltj.services.ZltjKhService;
import lims.zltj.vo.AddSampVo;
import pers.czf.commonUtils.DateUtils;
import pers.czf.commonUtils.MsgUtils;
import pers.czf.commonUtils.ProjectUtils;
import pers.czf.dbManager.Dao;

@Controller
@RequestMapping("lims/zltj/jy")
public class ZltjJyAction {
	
	@Autowired
	private Dao dao;
	
	@Autowired
	private ZltjKhService service;
	
	@RequestMapping
	public String index(HttpServletRequest request){
		Calendar curDate= Calendar.getInstance();
		
		Date startDate =  null;
		Date endDate = null;
		String startStr = request.getParameter("startDate");
		if(startStr == null||startStr.equals("")){
			int day = curDate.get(Calendar.DAY_OF_MONTH);
			if (day <= 25)
			{
	    	  curDate.set(Calendar.DAY_OF_MONTH, 26);
	    	  curDate.add(Calendar.MONTH, -1);
			}
			else
			{
	    	  curDate.set(Calendar.DAY_OF_MONTH, 26);
			}
			startDate = curDate.getTime();
			startStr = DateUtils.DateFormat(startDate, "yyy-MM-dd");
		}else{
			startDate = DateUtils.FormatToDate(startStr, "yyyy-MM-dd");
		}
		request.setAttribute("startDate", startStr);
		
		String endStr = request.getParameter("endDate");
		if(endStr == null||endStr.equals("")){
			int day = curDate.get(Calendar.DAY_OF_MONTH);
		    if (day >= 26)
		    {
		    	  curDate.set(Calendar.DAY_OF_MONTH, 25);
		    	  curDate.add(Calendar.MONTH, 1);
		    }
		    else
		    {
		    	  curDate.set(Calendar.DAY_OF_MONTH, 25);
		    }
			endDate = curDate.getTime();
			endStr = DateUtils.DateFormat(endDate, "yyy-MM-dd");
		}else{
			endDate = DateUtils.FormatToDate(endStr+" 23:59:59", "yyyy-MM-dd HH:mm:ss");
		}
		request.setAttribute("endDate", endStr);
		
		//获取本月度加样统计
		String sql1 = "select o.area_name,count(*) count_Num "
				+ " from orders o,folders f,batches b "
				+ " where o.folderno=f.folderno and f.batchid=b.batchid "
				+ " and b.tasktype='临时加样' and o.sampdate>=? and o.sampdate<=? and o.status in ('Done','OOS')"

				+ " group by o.area_name ";
		List<Map<String,Object>> jyCount = dao.queryListMap(sql1, startDate,endDate);
		
		String sql2 = "select o.area_name,sum(t.price) price "
				+ " from orders o,folders f,batches b,ordtask ot,tests t "
				+ " where o.folderno=f.folderno and f.batchid=b.batchid and o.ordno=ot.ordno and ot.testcode=t.testcode "
				+ " and b.tasktype='临时加样'  and o.sampdate>=? and o.sampdate<=? and o.status in ('Done','OOS') "

				+ " group by o.area_name ";
		
		List<Map<String,Object>> jyPrice = dao.queryListMap(sql2, startDate,endDate);
		
		//获取车间装置排序
		//String sql3 = "select area_name from(select distinct p.area_name,a.sorter from areas a,areasites ar,plants p where a.area_name=ar.area_name and a.area_name = p.area_name  and ar.dept =? order by a.sorter)";
		//String sql3="select distinct area_name from(select distinct t2.area_name,p.areasorter,p.sorter from sample_points_user t1,sample_points t2,plants p  where t1.usrnam=? AND t1.sample_point_id = t2.sample_point_id and t2.area_name = p.area_name and t2.plant = p.plant  and t2.area_name is not null and t2.plant is not null  order by p.areasorter,p.sorter)";
		String sql3="select distinct area_name " + 
	            "from sample_points_user t1, sample_points t2 " + 
	            "         where t1.usrnam = ? " + 
	            "           AND t1.sample_point_id = t2.sample_point_id " + 
	            "           and t2.area_name is not null " + 
	            "         order by nlssort(t2.area_name, 'NLS_SORT=SCHINESE_PINYIN_M')";
		List<String> areaList = dao.queryListValue(sql3, String.class,SessionFactory.getUsrName());
		
		List<Map<String,Object>> jytj = new ArrayList<Map<String,Object>>();
		int countNum = 0;
		float countPrice = 0;
		for(String area:areaList){
			Map<String,Object> map = new HashMap<String, Object>();
			map.put("area", area);
			for(Map<String,Object> jyc:jyCount){
				String areaName = jyc.get("areaName").toString();
				if(area.equals(areaName)){
					BigDecimal num = (BigDecimal) jyc.get("countNum");
					countNum += num.intValue();
					map.put("countNum", num.intValue());	
					break;
				}
			}
			
			for(Map<String,Object> jyc:jyPrice){
				String areaName = jyc.get("areaName").toString();
				if(area.equals(areaName)){
					BigDecimal price = (BigDecimal) jyc.get("price");
					countPrice += price.floatValue();
					
					map.put("price", price);
					break;
				}
			}
			jytj.add(map);
		}
		request.setAttribute("jytj", jytj);
		request.setAttribute("countNum", countNum);
		request.setAttribute("countPrice", countPrice);
		return "lims/zltj/jytj";
	}
	
	/**
	 * 按车间进行检测成本统计
	 * @param request
	 * @return
	 */
	@RequestMapping("jccb")
	public String jccb(HttpServletRequest request){
		Calendar curDate= Calendar.getInstance();
		
		Date startDate =  null;
		Date endDate = null;
		String startStr = request.getParameter("startDate");
		if(startStr == null||startStr.equals("")){
			int day = curDate.get(Calendar.DAY_OF_MONTH);
			if (day <= 25)
			{
	    	  curDate.set(Calendar.DAY_OF_MONTH, 26);
	    	  curDate.add(Calendar.MONTH, -1);
			}
			else
			{
	    	  curDate.set(Calendar.DAY_OF_MONTH, 26);
			}
			startDate = curDate.getTime();
			startStr = DateUtils.DateFormat(startDate, "yyy-MM-dd");
		}else{
			startDate = DateUtils.FormatToDate(startStr, "yyyy-MM-dd");
		}
		request.setAttribute("startDate", startStr);
		
		String endStr = request.getParameter("endDate");
		if(endStr == null||endStr.equals("")){
			int day = curDate.get(Calendar.DAY_OF_MONTH);
		    if (day >= 26)
		    {
		    	  curDate.set(Calendar.DAY_OF_MONTH, 25);
		    	  curDate.add(Calendar.MONTH, 1);
		    }
		    else
		    {
		    	  curDate.set(Calendar.DAY_OF_MONTH, 25);
		    }
			endDate = curDate.getTime();
			endStr = DateUtils.DateFormat(endDate, "yyy-MM-dd");
		}else{
			endDate = DateUtils.FormatToDate(endStr+" 23:59:59", "yyyy-MM-dd HH:mm:ss");
		}
		request.setAttribute("endDate", endStr);
		
		//获取可查看车间列表
		String area = request.getParameter("area");
		String sql0="select distinct area_name " + 
	            "from sample_points_user t1, sample_points t2 " + 
	            "         where t1.usrnam = ? " + 
	            "           AND t1.sample_point_id = t2.sample_point_id " + 
	            "           and t2.area_name is not null " + 
	            "         order by nlssort(t2.area_name, 'NLS_SORT=SCHINESE_PINYIN_M')";
		List<Map<String,Object>> areaList = dao.queryListMap(sql0, SessionFactory.getUsrName());
		
		for(Map<String,Object> map:areaList){
			String areaName = map.get("areaName").toString();
			if(area==null||area.equals("")){
				area=areaName;
				break;
			}
			if(areaName.equals(area)){
				map.put("selected", true);
				break;
			}
		}
		request.setAttribute("areaList", areaList);
		request.setAttribute("area", area);
		
		String taskType = request.getParameter("taskType");
		List<String> types = new ArrayList<String>();
		if(taskType==null||taskType.equals("")){
			types.add("常规检验");
			types.add("临时加样");
			types.add("抽样检查");
			types.add("按需检测");
			types.add("开停车");
			types.add("自动登录");
			types.add("调度加样");
			types.add("聚烯烃抽样");
		}else if(taskType.equals("0")){
			types.add("常规检验");
			types.add("自动登录");
		}else{
			types.add(taskType);
		}
		request.setAttribute("taskType", taskType);
		//获取本月度加样统计
		String sql1 = "select o.plant,count(*) count_Num "
				+ " from orders o,folders f,batches b "
				+ " where o.folderno=f.folderno and f.batchid=b.batchid "
				+ " and o.sampdate>=? and o.sampdate<=? and o.status in ('Done','OOS')"
				+ " and b.tasktype in (?) and o.area_name=? "
				+ " group by o.plant ";
		List<Map<String,Object>> jcCount = dao.queryListMap(sql1, startDate,endDate,types,area);
		
		String sql2 = "select o.plant,sum(t.price) price "
				+ " from orders o,folders f,batches b,ordtask ot,tests t "
				+ " where o.folderno=f.folderno and f.batchid=b.batchid and o.ordno=ot.ordno and ot.testcode=t.testcode "
				+ " and o.sampdate>=? and o.sampdate<=? and o.status in ('Done','OOS') "
				+ " and b.tasktype in (?) and o.area_name=? "
				+ " group by o.plant";
		
		List<Map<String,Object>> jcPrice = dao.queryListMap(sql2, startDate,endDate,types,area);
		
		//获取装置排序
		String sql3 = "select distinct plant from plants where area_name=? order by plant";
		/*String sql3="select distinct area_name " + 
	            "from sample_points_user t1, sample_points t2 " + 
	            "         where t1.usrnam = ? " + 
	            "           AND t1.sample_point_id = t2.sample_point_id " + 
	            "           and t2.area_name is not null " + 
	            "         order by nlssort(t2.area_name, 'NLS_SORT=SCHINESE_PINYIN_M')";*/
		List<String> plantList = dao.queryListValue(sql3, String.class,area);
		
		List<Map<String,Object>> jytj = new ArrayList<Map<String,Object>>();
		int countNum = 0;
		float countPrice = 0;
		for(String plant:plantList){
			Map<String,Object> map = new HashMap<String, Object>();
			map.put("plant", plant);
			for(Map<String,Object> jyc:jcCount){
				String plantName = jyc.get("plant").toString();
				if(plant.equals(plantName)){
					BigDecimal num = (BigDecimal) jyc.get("countNum");
					countNum += num.intValue();
					map.put("countNum", num.intValue());	
					break;
				}
			}
			
			for(Map<String,Object> jyc:jcPrice){
				String plantName = jyc.get("plant").toString();
				if(plant.equals(plantName)){
					BigDecimal price = (BigDecimal) jyc.get("price");
					countPrice += price.floatValue();
					
					map.put("price", price);
					break;
				}
			}
			jytj.add(map);
		}
		request.setAttribute("jytj", jytj);
		request.setAttribute("countNum", countNum);
		request.setAttribute("countPrice", countPrice);
		return "lims/zltj/jctj";
	}
	
	@RequestMapping("detail/{showParam}")
	public String detail(HttpServletRequest request,@PathVariable String showParam){
		String area = request.getParameter("area");
		//获取车间列表
		//String sql3 = "select area_name from(select distinct p.area_name,a.sorter from areas a,areasites ar,plants p where a.area_name=ar.area_name and a.area_name = p.area_name  and ar.dept =? order by a.sorter)";
		//String sql3="select distinct area_name from(select distinct t2.area_name,p.areasorter,p.sorter from sample_points_user t1,sample_points t2,plants p  where t1.usrnam=? AND t1.sample_point_id = t2.sample_point_id and t2.area_name = p.area_name and t2.plant = p.plant  and t2.area_name is not null and t2.plant is not null  order by p.areasorter,p.sorter)";
		String sql3="select distinct area_name " + 
	            "from sample_points_user t1, sample_points t2 " + 
	            "         where t1.usrnam = ? " + 
	            "           AND t1.sample_point_id = t2.sample_point_id " + 
	            "           and t2.area_name is not null " + 
	            "         order by nlssort(t2.area_name, 'NLS_SORT=SCHINESE_PINYIN_M')";
		List<String> areaList = dao.queryListValue(sql3, String.class,SessionFactory.getUsrName());
		
		if((area==null||area.equals(""))&&areaList.size()>0){
			area = areaList.get(0);
		}
		request.setAttribute("areaList", areaList);
		request.setAttribute("area", area);
		
		Calendar curDate= Calendar.getInstance();
		int year = curDate.get(Calendar.YEAR);
		int month = curDate.get(Calendar.MONTH);
		
		Date startDate =  null;
		Date endDate = null;
		String startStr = request.getParameter("startDate");
		if(startStr == null||startStr.equals("")){
			int day = curDate.get(Calendar.DAY_OF_MONTH);
			if (day <= 25)
			{
	    	  curDate.set(Calendar.DAY_OF_MONTH, 26);
	    	  curDate.add(Calendar.MONTH, -1);
			}
			else
			{
	    	  curDate.set(Calendar.DAY_OF_MONTH, 26);
			}
			startDate = curDate.getTime();
			startStr = DateUtils.DateFormat(startDate, "yyy-MM-dd");
		}else{
			startDate = DateUtils.FormatToDate(startStr, "yyyy-MM-dd");
		}
		request.setAttribute("startDate", startStr);
		
		String endStr = request.getParameter("endDate");
		if(endStr == null||endStr.equals("")){
			int day = curDate.get(Calendar.DAY_OF_MONTH);
		    if (day >= 26)
		    {
		    	  curDate.set(Calendar.DAY_OF_MONTH, 25);
		    	  curDate.add(Calendar.MONTH, 1);
		    }
		    else
		    {
		    	  curDate.set(Calendar.DAY_OF_MONTH, 25);
		    }
			endDate = curDate.getTime();
			endStr = DateUtils.DateFormat(endDate, "yyy-MM-dd");
		}else{
			endDate = DateUtils.FormatToDate(endStr+" 23:59:59", "yyyy-MM-dd HH:mm:ss");
		}
		request.setAttribute("endDate", endStr);
		
		//统计车间的加样明细信息
		String sql1 = "select o.ordno,o.area_name,O.PLANT,o.description pointdesc,O.LOGDATE,o.sampdate,u.fullname,b.batchname,o.sampdesc,nvl(m.matname,o.matcode) matname "
				+ " from orders o left join materials m on o.matcode = m.matcode,folders f,batches b,users u "
				+ " where o.folderno=f.folderno and f.batchid=b.batchid and u.usrnam=f.createdby "
				+ " and b.tasktype='临时加样' and o.status in ('Done','OOS') and o.area_name=? and o.sampdate>=? and o.sampdate<=?"
				
				+ " order by o.sampdate";
		List<Map<String,Object>> jyDetail = dao.queryListMap(sql1, area,startDate,endDate);
		request.setAttribute("jyDetail", jyDetail);
		
		//统计单个样品的费用
		String sqlp = "select t2.ordno key,sum(t3.price) value "
				+ " from orders o,folders f,batches b,ordtask t2,tests t3 where o.folderno=f.folderno and f.batchid=b.batchid and o.ordno=t2.ordno and t2.testcode=t3.testcode "
				+ " and b.tasktype='临时加样' and o.status in ('Done','OOS') and o.area_name=? and o.sampdate>=? and o.sampdate<=?"
				
				+ " group by t2.ordno";
		Map<String,Object> priceMap = dao.queryKeyValue(sqlp, area,startDate,endDate);
		request.setAttribute("priceMap", priceMap);
		//分析项目
		String sqlTest = "select t2.ordno key,wm_concat(t2.testno) value  from orders o,folders f,batches b,ordtask t2,tests t3 where o.folderno=f.folderno and f.batchid=b.batchid and o.ordno=t2.ordno and t2.testcode=t3.testcode  and b.tasktype='临时加样' and o.status in ('Done','OOS') and o.area_name=? and o.sampdate>=? and o.sampdate<=?  group by t2.ordno";
	    
	    Map<String, Object> testList = this.dao.queryKeyValue(sqlTest, area, startDate, endDate );
	    request.setAttribute("testList", testList);
		//获取总费用
		String sql2 = "select sum(t.price) price "
				+ " from orders o,folders f,batches b,ordtask ot,tests t "
				+ " where o.folderno=f.folderno and f.batchid=b.batchid and o.ordno=ot.ordno and ot.testcode=t.testcode "
				+ " and b.tasktype='临时加样' and o.area_name=? and o.sampdate>=? and o.sampdate<=?  and o.status in ('Done','OOS') ";
		
		Float price = dao.queryValue(sql2,Float.class,area, startDate,endDate);
		request.setAttribute("countPrice", price);
		if(showParam.equals("tj")){
			return "lims/zltj/jyDetail1";
		}else{
			return "lims/zltj/jyDetail2";
		}
	}
	
	
	/**
	 * 不加样申请
	 */
	  @RequestMapping({"/detailNo"})
	  public String detailNo(HttpServletRequest request) {
		request.setAttribute("area", SessionFactory.getFullName());  
		//处理开始结束日期
		Calendar curDate = Calendar.getInstance();
		Date startDate =  null;
		Date endDate = null;
		String startStr = request.getParameter("startDate");
		if(startStr == null||startStr.equals("")){
			int day = curDate.get(Calendar.DAY_OF_MONTH);
			if (day <= 25)
			{
	    	  curDate.set(Calendar.DAY_OF_MONTH, 26);
	    	  curDate.add(Calendar.MONTH, -1);
			}
			else
			{
	    	  curDate.set(Calendar.DAY_OF_MONTH, 26);
			}
			startDate = curDate.getTime();
			startStr = DateUtils.DateFormat(startDate, "yyy-MM-dd");
		}else{
			startDate = DateUtils.FormatToDate(startStr, "yyyy-MM-dd");
		}
		request.setAttribute("startDate", startStr);
		
		String endStr = request.getParameter("endDate");
		if(endStr == null||endStr.equals("")){
			int day = curDate.get(Calendar.DAY_OF_MONTH);
		    if (day >= 26)
		    {
		    	  curDate.set(Calendar.DAY_OF_MONTH, 25);
		    	  curDate.add(Calendar.MONTH, 1);
		    }
		    else
		    {
		    	  curDate.set(Calendar.DAY_OF_MONTH, 25);
		    }
			endDate = curDate.getTime();
			endStr = DateUtils.DateFormat(endDate, "yyy-MM-dd");
		}else{
			endDate = DateUtils.FormatToDate(endStr+" 23:59:59", "yyyy-MM-dd HH:mm:ss");
		}
		request.setAttribute("endDate", endStr);
		
		String sql1 = "select o.ordno,o.area_name,O.PLANT,o.description pointdesc,O.LOGDATE,o.sampdate,"
				+ "u.fullname,b.batchname,o.sampdesc,nvl(m.matname,o.matcode) matname  "
				+ "from orders o left join materials m on o.matcode = m.matcode,folders f,batches b,users u "
				+ " where o.folderno=f.folderno and f.batchid=b.batchid and u.usrnam=f.createdby  "
				+ "and b.tasktype='临时加样' and o.status in ('Done','OOS') and (o.area_name=? or exists(select 1 from sample_points s where s.sample_point_id=o.sample_point_id and s.real_area=?))"
				+ "and o.sampdate>=? and o.sampdate<=? "
				+ "and not exists(select 1 from NOADDTJ_SAMPLE where ordno=o.ordno and (status=1 or status=0)) "
				+ " order by o.sampdate";
	    
	    List<Map<String, Object>> jyDetail = this.dao.queryListMap(sql1, SessionFactory.getFullName(),SessionFactory.getFullName(), startDate, endDate );
	    request.setAttribute("jyDetail", jyDetail);
	    String sqlp = "select t2.ordno key,sum(t3.price) value "
	    		+ "from orders o,folders f,batches b,ordtask t2,tests t3 "
	    		+ "where o.folderno=f.folderno and f.batchid=b.batchid and o.ordno=t2.ordno "
	    		+ "and t2.testcode=t3.testcode  and b.tasktype='临时加样' "
	    		+ "and o.status in ('Done','OOS') and (o.area_name=? or exists(select 1 from sample_points s where s.sample_point_id=o.sample_point_id and s.real_area=?)) and o.sampdate>=? "
	    		+ "and o.sampdate<=?  "
	    		+ "and not exists(select 1 from NOADDTJ_SAMPLE where ordno=o.ordno and (status=1 or status=0)) "
	    		+ " group by t2.ordno";
	    
	    Map<String, Object> priceMap = this.dao.queryKeyValue(sqlp,  SessionFactory.getFullName(),SessionFactory.getFullName(), startDate, endDate );
	    request.setAttribute("priceMap", priceMap);

	    String sqlTest = "select t2.ordno key,wm_concat(t2.testno) value  "
	    		+ "from orders o,folders f,batches b,ordtask t2,tests t3 "
	    		+ "where o.folderno=f.folderno and f.batchid=b.batchid "
	    		+ "and o.ordno=t2.ordno and t2.testcode=t3.testcode  "
	    		+ "and b.tasktype='临时加样' and o.status in ('Done','OOS') "
	    		+ "and (o.area_name=? or exists(select 1 from sample_points s where s.sample_point_id=o.sample_point_id and s.real_area=?)) and o.sampdate>=? and o.sampdate<=?  "
	    		+ "and not exists(select 1 from NOADDTJ_SAMPLE where ordno=o.ordno and (status=1 or status=0)) "
	    		+ " group by t2.ordno";
	    
	    Map<String, Object> testList = this.dao.queryKeyValue(sqlTest, SessionFactory.getFullName(),SessionFactory.getFullName(), startDate, endDate );
	    request.setAttribute("testList", testList);
	    String sql2 = "select sum(t.price) price  "
	    		+ "from orders o,folders f,batches b,ordtask ot,tests t  "
	    		+ "where o.folderno=f.folderno and f.batchid=b.batchid "
	    		+ "and o.ordno=ot.ordno and ot.testcode=t.testcode  "
	    		+ "and not exists(select 1 from NOADDTJ_SAMPLE where ordno=o.ordno and (status=1 or status=0)) "
	    		+ " and b.tasktype='临时加样' and (o.area_name=? or exists(select 1 from sample_points s where s.sample_point_id=o.sample_point_id and s.real_area=?)) and o.sampdate>=? "
	    		+ "and o.sampdate<=?  and o.status in ('Done','OOS') ";
	    
	    Float price = (Float)this.dao.queryValue(sql2, Float.class, SessionFactory.getFullName(),SessionFactory.getFullName(), startDate, endDate );
	    request.setAttribute("countPrice", price);
	    
	    return "lims/jy/jyIndex";
	  }
	  /**
	   * 添加不加样统计信息
	   * @param request
	   * @return
	   */
	  @RequestMapping(value={"/addSpDetail"}, produces={"application/json;charset=UTF-8"})
	  @ResponseBody
	  public String addSpDetail(HttpServletRequest request)
	  {
	    String[] selectOrders = request.getParameterValues("selectId");
	    String remark = request.getParameter("reason");
	    if (selectOrders == null) {
	      return MsgUtils.error("添加失败").toString();
	    }
	    String insertSql = "insert into NOADDTJ_SAMPLE(ordno,status,remark,applyuser,applydate) values(?,0,?,?,sysdate)";
	    String updateSql = "update NOADDTJ_SAMPLE set status=0,remark=?,applyuser=?,applydate=sysdate where ordno=?";
	    
	    for (int i = 0; i < selectOrders.length; i++)
	    {
	      String ordno = selectOrders[i];
	      if (this.dao.excuteUpdate(updateSql,remark, ordno, SessionFactory.getUsrName() ) == 0) {
	        this.dao.excuteUpdate(insertSql, ordno, remark, SessionFactory.getUsrName() );
	      }
	    }
	    return MsgUtils.success().toString();
	  }
	  /**
	   * 获取审核不加样统计
	   */
	  @RequestMapping({"/approveJy"})
	  public String approveJy(HttpServletRequest request)
	  {
	    String areaName = request.getParameter("area");
	    String sql = "select A.area_name areaName  from AREASITES A,orders p,SAMPLE_POINTS s,NOADDTJ_SAMPLE t "
	    		+ "WHERE A.area_name=s.area_name and p.area_name=s.area_name and p.plant = s.plant  "
	    		+ "and t.ordno=p.ordno and t.status=0  and A.dept=? group by A.area_name";
	    
	    List<String> Arealist = this.dao.queryListValue(sql, String.class, ProjectUtils.getDept());
	    if (((areaName == null) || (areaName.equals(""))) && (Arealist.size() > 0)) {
	      areaName = (String)Arealist.get(0);
	    }
	    request.setAttribute("area", areaName);
	    request.setAttribute("areaList", Arealist);
	    String sql2 = "select to_char(o.sampdate,'yyyy-mm-dd HH24:mi:ss') sampdate,nvl(m.matname,o.matcode) matname,o.ordno,o.description,b.createdby,"
	    		+ "na.remark,ot.testno,t.price  from batches b,folders f,ordtask ot,tests  t,NOADDTJ_SAMPLE na,orders o left join materials m on o.matcode = m.matcode  "
	    		+ "where b.batchid = f.batchid and f.folderno = o.folderno and o.ordno = ot.ordno and na.ordno=o.ordno  and ot.testcode = t.testcode "
	    		+ " and b.tasktype = '临时加样' and o.area_name = ? and o.status in ('OOS', 'Done')  and na.status=0  "
	    		+ "order by o.sampdate,o.ordno ";
	    
	    List<Map<String, Object>> addDetailList = this.dao.queryListMap(sql2,  areaName );
	    
	    List<AddSampVo> addSampList = new ArrayList<AddSampVo>();
	    AddSampVo samp = null;
	    for (Map<String, Object> map : addDetailList)
	    {
	      String sampeDate = (String)map.get("sampdate");
	      String matname = (String)map.get("matname");
	      String ordNo = (String)map.get("ordno");
	      String testNo = (String)map.get("testno");
	      String description = (String)map.get("description");
	      String createdBy = (String)map.get("createdby");
	      String comments = (String)map.get("remark");
	      
	      Float price = Float.valueOf(0.0F);
	      if (map.get("price") != null) {
	        price = Float.valueOf(((BigDecimal)map.get("price")).floatValue());
	      }
	      if ((samp == null) || (!ordNo.equals(samp.getOrdNo())))
	      {
	        samp = new AddSampVo();
	        samp.setMatname(matname);
	        samp.setOrdNo(ordNo);
	        samp.setSampeDate(sampeDate);
	        samp.setCreateBy(createdBy);
	        samp.setDecription(description);
	        samp.setComments(comments);
	        addSampList.add(samp);
	      }
	      samp.addTest(testNo, price.floatValue());
	    }
	    request.setAttribute("addSampList", addSampList);
	    return "lims/jy/approveIndex";
	  }
	  /**
	   * 审核通过
	   * @param request
	   * @return
	   */
	  @RequestMapping(value={"/approveDeal"}, produces={"application/json;charset=UTF-8"})
	  @ResponseBody
	  public String approveDeal(HttpServletRequest request)
	  {
	    String[] selectOrders = request.getParameterValues("selectId");
	    if (selectOrders == null) {
	      return MsgUtils.error("未选择").toString();
	    }
	    String sql = "update NOADDTJ_SAMPLE na set approve=?,status=1,approvedate = sysdate where ordno=?";
	    String sql2 = "update batches set tasktype='临时加样(不统计)' where batchid in (select batchid from folders f,orders o where o.ordno=? and f.folderno=o.folderno )";
	    
	    for (int i = 0; i < selectOrders.length; i++)
	    {
	      String ordno = selectOrders[i];
	      this.dao.excuteUpdate(sql, SessionFactory.getUsrName(), ordno );
	      this.dao.excuteUpdate(sql2, ordno );
	    }
	    return MsgUtils.success().toString();
	  }
	  /**
	   * 审核退回
	   */
	  @RequestMapping(value={"/approveRefuse"}, produces={"application/json;charset=UTF-8"})
	  @ResponseBody
	  public String approveRefuse(HttpServletRequest request)
	  {
	    String[] selectOrders = request.getParameterValues("selectId");
	    String remark = request.getParameter("reason");
	    if (selectOrders == null) {
	      return MsgUtils.error("未选择").toString();
	    }
	    String sql = "update NOADDTJ_SAMPLE na set approve=?,status=-1,approvedate = sysdate,approveremark=? where ordno=?";
	    
	    for (int i = 0; i < selectOrders.length; i++)
	    {
	      String ordno = selectOrders[i];
	      this.dao.excuteUpdate(sql,  SessionFactory.getUsrName(), remark, ordno );
	    }
	    return MsgUtils.success().toString();
	  }
	  /**
	   * 查询不加样统计审核记录
	   */
	  @RequestMapping({"/queryApproveAddSample"})
	  public String queryApproveAddSample(HttpServletRequest request) {
		  	String areaName = request.getParameter("area");
		    String sql = "select A.area_name areaName  "
		    		+ "from AREASITES A,orders p,SAMPLE_POINTS s,NOADDTJ_SAMPLE t "
		    		+ "WHERE A.area_name=s.area_name and p.area_name=s.area_name and p.plant = s.plant  "
		    		+ "and t.ordno=p.ordno   and A.dept=? group by A.area_name";
		    
		    List<String> Arealist = this.dao.queryListValue(sql, String.class, ProjectUtils.getDept() );
		    if (((areaName == null) || (areaName.equals(""))) && (Arealist.size() > 0)) {
		      areaName = (String)Arealist.get(0);
		    }
		    request.setAttribute("area", areaName);
		    request.setAttribute("areaList", Arealist);
		    Calendar curDate = Calendar.getInstance();
		    Date startDate =  null;
			Date endDate = null;
			String startStr = request.getParameter("startDate");
			if(startStr == null||startStr.equals("")){
				int day = curDate.get(Calendar.DAY_OF_MONTH);
				if (day <= 25)
				{
		    	  curDate.set(Calendar.DAY_OF_MONTH, 26);
		    	  curDate.add(Calendar.MONTH, -1);
				}
				else
				{
		    	  curDate.set(Calendar.DAY_OF_MONTH, 26);
				}
				startDate = curDate.getTime();
				startStr = DateUtils.DateFormat(startDate, "yyy-MM-dd");
			}else{
				startDate = DateUtils.FormatToDate(startStr, "yyyy-MM-dd");
			}
			request.setAttribute("startDate", startStr);
			
			String endStr = request.getParameter("endDate");
			if(endStr == null||endStr.equals("")){
				int day = curDate.get(Calendar.DAY_OF_MONTH);
			    if (day >= 26)
			    {
			    	  curDate.set(Calendar.DAY_OF_MONTH, 25);
			    	  curDate.add(Calendar.MONTH, 1);
			    }
			    else
			    {
			    	  curDate.set(Calendar.DAY_OF_MONTH, 25);
			    }
				endDate = curDate.getTime();
				endStr = DateUtils.DateFormat(endDate, "yyy-MM-dd");
			}else{
				endDate = DateUtils.FormatToDate(endStr+" 23:59:59", "yyyy-MM-dd HH:mm:ss");
			}
			request.setAttribute("endDate", endStr);
			
			String sql2 = "select to_char(o.sampdate,'yyyy-mm-dd HH24:mi:ss') sampdate,nvl(m.matname,o.matcode) matname,o.ordno,o.description,b.createdby,"
					+ "b.comments,ot.testno,t.price ,n.applyuser,n.approve,to_char(n.approvedate,'yyyy-mm-dd HH24:mi:ss') approvedate,n.remark,n.approveremark,o.plant,"
					+ "n.status  from batches b,folders f,ordtask ot,tests  t,NOADDTJ_SAMPLE n, orders o left join materials m on o.matcode = m.matcode  "
					+ "where b.batchid = f.batchid and f.folderno = o.folderno and o.ordno = ot.ordno  and ot.testcode = t.testcode and n.ordno=o.ordno "
					+ "and (n.applyuser=? or n.approve=?) and o.sampdate>=? and o.sampdate<=? and o.area_name =?  order by o.sampdate,o.ordno ";
		    
		    List<Map<String, Object>> addDetailList = this.dao.queryListMap(sql2, SessionFactory.getUsrName(), SessionFactory.getUsrName(), startDate, endDate, areaName );
		    List<AddSampVo> addSampList = new ArrayList<AddSampVo>();
			AddSampVo samp = null;
			 for (Map<String, Object> map : addDetailList)
			    {
			      String sampeDate = (String)map.get("sampdate");
			      String matname = (String)map.get("matname");
			      String ordNo = (String)map.get("ordno");
			      String testNo = (String)map.get("testno");
			      String description = (String)map.get("description");
			      String createdBy = (String)map.get("createdby");
			      String comments = (String)map.get("comments");
			      String applyuser = (String)map.get("applyuser");
			      String approve = (String)map.get("approve");
			      String approveDate = (String)map.get("approvedate");
			      String remark = (String)map.get("remark");
			      String approveremark = (String)map.get("approveremark");
			      String status = map.get("status").toString();
			      String plant = (String)map.get("plant");
			      Float price = Float.valueOf(0.0F);
			      if (map.get("price") != null) {
			        price = Float.valueOf(((BigDecimal)map.get("price")).floatValue());
			      }
			      if ((samp == null) || (!ordNo.equals(samp.getOrdNo())))
			      {
			        samp = new AddSampVo();
			        samp.setMatname(matname);
			        samp.setOrdNo(ordNo);
			        samp.setSampeDate(sampeDate);
			        samp.setCreateBy(createdBy);
			        samp.setDecription(description);
			        samp.setComments(comments);
			        addSampList.add(samp);
			        samp.setApplyUser(applyuser);
			        samp.setApprove(approve);
			        samp.setRemark(remark);
			        samp.setApproveRemark(approveremark);
			        samp.setApproveDate(approveDate);
			        samp.setStatus(status);
			        samp.setPlant(plant);
			      }
			      samp.addTest(testNo, price.floatValue());
			    }
			    request.setAttribute("addSampList", addSampList);
			    return "lims/jy/queryIndex";
		 
	  }
	  
}
