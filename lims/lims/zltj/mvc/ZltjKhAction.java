package lims.zltj.mvc;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.crypto.Cipher;
import javax.servlet.http.HttpServletRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.core.handler.SessionFactory;

import lims.zltj.services.SckhBusi;
import lims.zltj.services.ZltjKhService;
import lims.zltj.vo.ChjVo;
import lims.zltj.vo.ChpVo;
import lims.zltj.vo.FxxmVo;
import lims.zltj.vo.ZzhiVo;
import pers.czf.commonUtils.DateUtils;
import pers.czf.commonUtils.MsgUtils;
import pers.czf.commonUtils.ProjectUtils;
import pers.czf.dbManager.Dao;

@Controller
@RequestMapping("lims/zltj/kh")
public class ZltjKhAction {
	
	@Autowired
	private Dao dao;
	
	@Autowired
	private ZltjKhService service;
	
	@RequestMapping
	public String index(HttpServletRequest request){
		//获取车间信息及排序
		/*String sql = "select area_name id,area_name title,area_name area,'item' type from "
				+ " (select t1.area_name,min(t1.sorter) paix from plants t1,PLANTMEMBERS t2 where t1.area_name=t2.area_name and t1.plant=t2.plant and t2.usrnam=?  group by t1.area_name) order by paix";*/
		//String sql = "select area_name id,area_name title,area_name area,'item' type from(select distinct p.area_name,a.sorter from areas a,areasites ar,plants p where a.area_name=ar.area_name and a.area_name = p.area_name  and ar.dept =? order by a.sorter)";
		//String sql="select distinct area_name id,area_name title,area_name area,'item' type from(select distinct t2.area_name,p.areasorter,p.sorter from sample_points_user t1,sample_points t2,plants p  where t1.usrnam=? AND t1.sample_point_id = t2.sample_point_id and t2.area_name = p.area_name and t2.plant = p.plant  and t2.area_name is not null and t2.plant is not null  order by p.areasorter,p.sorter)";
		String sql="select distinct area_name id, area_name title, area_name area, 'item' type " + 
	            "from sample_points_user t1, sample_points t2 " + 
	            "         where t1.usrnam = ? " + 
	            "           AND t1.sample_point_id = t2.sample_point_id " + 
	            "           and t2.area_name is not null " + 
	            "         order by nlssort(t2.area_name, 'NLS_SORT=SCHINESE_PINYIN_M')";
		JSONArray areaList = dao.queryJSONArray(sql,SessionFactory.getUsrName());
		request.setAttribute("areas", areaList);
		return "lims/zltj/khIndex";
	}
	
	@RequestMapping("detail")
	public String detail(HttpServletRequest request) throws Exception{
		String area = request.getParameter("area");
		request.setAttribute("area", area);
		
		//处理开始结束日期
		Calendar calendar = Calendar.getInstance();
		String startDate = request.getParameter("startDate");
		if(startDate==null||startDate.equals("")){
			int day = calendar.get(Calendar.DAY_OF_MONTH);
			if (day <= 25)
			{
				calendar.set(Calendar.DAY_OF_MONTH, 26);
				calendar.add(Calendar.MONTH, -1);
			}
			else
			{
				calendar.set(Calendar.DAY_OF_MONTH, 26);
			}
			startDate = DateUtils.DateFormat(calendar.getTime(), "yyyy-MM-dd");
		}
		request.setAttribute("startDate", startDate);
		
		String endDate = request.getParameter("endDate");
		if(endDate==null||endDate.equals("")){
			int day = calendar.get(Calendar.DAY_OF_MONTH);
		    if (day >= 26)
		    {
		    	calendar.set(Calendar.DAY_OF_MONTH, 25);
		    	calendar.add(Calendar.MONTH, 1);
		    }
		    else
		    {
		    	calendar.set(Calendar.DAY_OF_MONTH, 25);
		    }
			endDate=DateUtils.DateFormat(calendar.getTime(), "yyyy-MM-dd");
		}
		request.setAttribute("endDate", endDate);
		
		//获取时间列表
		List<String> dateList = service.getDayList(startDate, endDate);
		request.setAttribute("dateList", dateList);
		startDate = startDate+" 00:00:00";
		endDate = endDate +" 23:59:59";
		
		//获取车间的装置
		List<ZzhiVo> rdata = new ArrayList<ZzhiVo>();
		List<ZzhiVo> emptydata = new ArrayList<ZzhiVo>();
		
		String sql1= "select distinct p.plant from plants p,plants_setting ps "
				+ "where p.area_name=ps.area_name and p.plant=ps.plant "
				+ "and ps.username = ? and ps.is_check ='1' "
				+ "and  p.area_name=? "
				+ "order by nlssort(p.plant, 'NLS_SORT=SCHINESE_PINYIN_M')";
		List<String> zzList = dao.queryListValue(sql1, String.class, SessionFactory.getUsrName(),area);
		
		for(String zz:zzList){
			ZzhiVo vo = new ZzhiVo(zz);
			rdata.add(vo);
		}
		//样品映射列表
		Map<String,String> ypMap = new HashMap<String, String>();
		String sql2 = "select t1.matcode,t1.matname from MATERIALS t1";
		List<Map<String,Object>> ypList = dao.queryListMap(sql2);
		for(Map<String,Object> map:ypList){
			ypMap.put((String) map.get("matcode"), (String) map.get("matname"));
		}
	
		//获取班组开始时间  获取班组值
		String updateSql = "update orders set team='0' where realsampdate>=to_date(?,'yyyy-MM-dd hh24:mi:ss') and realsampdate<=to_date(?,'yyyy-MM-dd hh24:mi:ss')";
		dao.excuteUpdate(updateSql, startDate,endDate);
		String team = request.getParameter("team");
		if(team==null||"".equals(team)){
			team = "0";
		}
		if(!"0".equals(team)){
			String teamStartTime = request.getParameter("teamStartTime");
			request.setAttribute("teamStartTime", teamStartTime);
			request.setAttribute("team", team);
			Map<Integer,Integer> teamMap = new HashMap<Integer, Integer>();
			teamMap.put(20, 7);
			teamMap.put(3, 8);
			teamMap.put(11, 9);
				
			DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date firstDate = format.parse(startDate);
			Date endTime = format.parse(endDate);
			Calendar endCalendar = Calendar.getInstance();
			endCalendar.setTime(endTime);
				
			Calendar calendar1 = Calendar.getInstance();
			calendar1.setTime(firstDate);
			Calendar calendar2 = Calendar.getInstance();
			calendar2.setTime(firstDate);
			
			while(calendar1.before(endCalendar)&&teamMap.containsKey(calendar1.get(Calendar.HOUR_OF_DAY))){
				//获取开始时间
				int hour = calendar1.get(Calendar.HOUR_OF_DAY);
				calendar2.add(Calendar.HOUR_OF_DAY, teamMap.get(hour));
					
				//
				String updateSql2 = "update orders set team=? where realsampdate>? and realsampdate<=?";
				dao.excuteUpdate(updateSql2, team,calendar1.getTime(),calendar2.getTime());
					
				calendar1.add(Calendar.HOUR_OF_DAY, teamMap.get(hour)+24);
				calendar2.add(Calendar.HOUR_OF_DAY, 24);
			}
		}
		
		//获取指标类型
		String limitType = request.getParameter("limitType");
		
		
		String taskType = request.getParameter("taskType");
		
		
			//装置下样品检测项目
		String sql3 = "select t1.matcode,t1.testcode,t1.analyte,t1.s status,to_char(t3.sampdate,'MM-dd') rq,t1.specno,b.tasktype  "
				+ "from plantdaily t1,sample_points t2,orders t3,folders f,batches b,mate_setting2 t4  "
				+ "where f.folderno=t3.folderno and f.batchid=b.batchid and b.type<>'LP' "
				+ "and t1.ordno=t3.ordno  and t1.matcode=t4.matecode "
				+ "and t1.testcode=t4.testcode and t1.analyte=t4.analyte  "
				+ "and t1.sample_point_id=t2.sample_point_id and t2.real_area=? and t4.is_check='1' "
				+ "and t2.real_plant=? and t4.areaname = ? and t3.status in ('Done','OOS','Active')  "
				+ "and t3.realsampdate>=to_date(?,'yyyy-MM-dd hh24:mi:ss') "
				+ "and t3.realsampdate<=to_date(?,'yyyy-MM-dd hh24:mi:ss')  "
				+ "and not exists(select 1 from NOCHECK_SAMPLE nc where nc.ordno=t3.ordno and nc.status=1)  "
				+ "and t3.team=? order by matcode,analyte,rq";
	    for (ZzhiVo zz : rdata)
	    {
	      List<Map<String, Object>> fxxmList = this.dao.queryListMap(sql3, area, zz.getName(), area, startDate, endDate, team );
	      if (fxxmList.isEmpty())
	      {
	        emptydata.add(zz);
	      }
	      else
	      {
	        List<FxxmVo> fxxmData = SckhBusi.dealFxxmList(fxxmList);
	        
	        String sql = "select lowc,highc,highb,lowb,higha,lowa,innerlowa,innerhigha,charlimits "
	        		+ "from spec_analytes "
	        		+ "where specno=? and analyte=? and testcode=?";
	        for (FxxmVo f : fxxmData)
	        {
	          List<Map<String, Object>> zzl = this.dao.queryListMap(sql,  f.getSpecno(), f.getName(), f.getTestcode() );
	          if (!zzl.isEmpty())
	          {
	            Map<String, Object> zlMap = zzl.get(0);
	            String zb = "";
	            if(zlMap.get("lowa")!=null){
					zb = "≥"+zlMap.get("lowa").toString();
				}
				
				if(zlMap.get("higha")!=null){
					zb = "≤"+zlMap.get("higha").toString();
				}
				
				if(zlMap.get("lowa")!=null&&zlMap.get("higha")!=null){
					zb = zlMap.get("lowa")+"-"+zlMap.get("higha");
					
				}
				
				if(zlMap.get("lowb")!=null){
					zb = "≥"+zlMap.get("lowb").toString();
				}
				
				if(zlMap.get("highb")!=null){
					zb = "≤"+zlMap.get("highb").toString();
				}
				
				if(zlMap.get("lowb")!=null&&zlMap.get("highb")!=null){
					zb = zlMap.get("lowb")+"-"+zlMap.get("highb");
					
				}
				
				if(zlMap.get("lowc")!=null){
					zb = "≥"+zlMap.get("lowc").toString();
				}
				
				if(zlMap.get("highc")!=null){
					zb = "≤"+zlMap.get("highc").toString();
				}
				
				if(zlMap.get("lowc")!=null&&zlMap.get("highc")!=null){
					zb = zlMap.get("lowc")+"-"+zlMap.get("highc");
					
				}
				
				if(zlMap.get("innerlowa")!=null){
					zb = "≥"+zlMap.get("innerlowa").toString();
				}
				
				if(zlMap.get("innerhigha")!=null){
					zb = "≤"+zlMap.get("innerhigha").toString();
				}
				
	            if ((zlMap.get("innerlowa") != null) && (zlMap.get("innerhigha") != null)) {
	              zb = zlMap.get("innerlowa") + "-" + zlMap.get("innerhigha");
	            }
	            if ((zlMap.get("charlimits") != null) && (zlMap.get("charlimits") != "")) {
	              zb = (String)zlMap.get("charlimits");
	            }
	            f.setZb(zb);
	          }
	        }
	        List<ChpVo> chpList = SckhBusi.addToChp(fxxmData, ypMap, dateList);
	        
	        zz.setData(chpList);
	        
	        SckhBusi.dealZZ(zz);
	      }
	    }
		
		for(ZzhiVo zz2:emptydata){
			rdata.remove(zz2);
		}
		
		request.setAttribute("rdata", rdata);
		return "lims/zltj/khDetail";
	}
	/**
	 * 月度车间合格率
	 */
	@RequestMapping({"/cjhglNew"})
	public String cjhglNew(HttpServletRequest request) {
		String area = request.getParameter("area");
		request.setAttribute("area", area);
		
		//处理开始结束日期
		Calendar calendar = Calendar.getInstance();
		String startDate = request.getParameter("startDate");
		if(startDate==null||startDate.equals("")){
			int day = calendar.get(Calendar.DAY_OF_MONTH);
			if (day <= 25)
			{
				calendar.set(Calendar.DAY_OF_MONTH, 26);
				calendar.add(Calendar.MONTH, -1);
			}
			else
			{
				calendar.set(Calendar.DAY_OF_MONTH, 26);
			}
			startDate = DateUtils.DateFormat(calendar.getTime(), "yyyy-MM-dd");
		}
		request.setAttribute("startDate", startDate);
		
		String endDate = request.getParameter("endDate");
		if(endDate==null||endDate.equals("")){
			int day = calendar.get(Calendar.DAY_OF_MONTH);
		    if (day >= 26)
		    {
		    	calendar.set(Calendar.DAY_OF_MONTH, 25);
		    	calendar.add(Calendar.MONTH, 1);
		    }
		    else
		    {
		    	calendar.set(Calendar.DAY_OF_MONTH, 25);
		    }
			endDate=DateUtils.DateFormat(calendar.getTime(), "yyyy-MM-dd");
		}
		request.setAttribute("endDate", endDate);
		//获取时间列表
		List<String> dateList = service.getDayList(startDate, endDate);
		request.setAttribute("dateList", dateList);
		startDate = startDate+" 00:00:00";
		endDate = endDate +" 23:59:59";
		String sql1 = "select t2.area_name,t1.s status,to_char(t1.sampdate,'MM-dd') rq,t1.matcode, b.tasktype "
				+ " from plantdaily t1,folders f,batches b,AREASITES t2,sample_points t,MATE_SETTING2 t3,plants_setting ps  "
				+ "where t1.folderno=f.folderno and f.batchid=b.batchid and b.type<>'LP'  "
				+ "and t1.sample_point_id=t.sample_point_id and t.real_area=t2.area_name  "
				+ "and t3.areaname=t.real_area and t3.matecode=t1.matcode and t3.is_check='1'"
				+ "and t3.analyte=t1.analyte and ps.area_name=t.area_name and ps.plant=t.plant and ps.is_check='1' and t2.dept=? and t1.s in ('Done','OOS-A','OOS-B','OOS-C') "
				+ " and t1.sampdate>=to_date(?,'yyyy-MM-dd hh24:mi:ss')  and t1.sampdate <=to_date(?,'yyyy-MM-dd hh24:mi:ss')  "
				+ "and not exists(select 1 from NOCHECK_SAMPLE nc where nc.ordno=t1.ordno and nc.status=1) "
				+ " ORDER BY t2.area_name,rq,status";
		    
	    List<Map<String, Object>> cjList = this.dao.queryListMap(sql1,  ProjectUtils.getDept(), startDate, endDate );
	    List<ChjVo> rdata = new ArrayList<ChjVo>();
	    ChjVo cj = null;
	    //String status;
	    for (Map<String, Object> map : cjList)
	    {
	      String areaName = (String)map.get("areaName");
	      String rq = (String)map.get("rq");
	      String status = (String)map.get("status");
	      String taskType = (String)map.get("tasktype");
	      if ((cj == null) || (!areaName.equals(cj.getName())))
	      {
	    	cj = new ChjVo();
	        cj.setName(areaName);
	        rdata.add(cj);
	      }
	      cj.addNum(rq, status, taskType);
	      
	    }
	    //String sql2 = "select distinct area_name from AREAS order by sorter";
	    String sql2= "select distinct  p.area_name from plants p,plants_setting ps "
	    		+ "where p.area_name=ps.area_name and p.plant=ps.plant "
	    		+ "and ps.username = ? and ps.is_check ='1' "
	    		+ "order by nlssort(p.area_name, 'NLS_SORT=SCHINESE_PINYIN_M')";
	    List<ChjVo> orderStistcs = new ArrayList<ChjVo>();
	    List<String> list = this.dao.queryListValue(sql2, String.class,SessionFactory.getUsrName());
	    for (String d : list) {
	      for (ChjVo cjvo : rdata) {
	        if (d.equals(cjvo.getName()))
	        {
	          orderStistcs.add(cjvo);
	          break;
	        }
	      }
	    }
	    //计算总合格率
	   
	    request.setAttribute("dateList", dateList);
	    request.setAttribute("rdata", orderStistcs);

	    Map<String, Float> rqhgl = new HashMap<String, Float>();
	    
	    for (String rq : dateList)
	    {
	      int done = 0;
	      int total = 0;
	      for(ChjVo cv:orderStistcs) 
	      {
	        if (cv.getDoneMap().containsKey(rq)) {
	          done += ((Integer)cv.getDoneMap().get(rq)).intValue();
	        }
	        if (cv.getTotalMap().containsKey(rq)) {
	          total += ((Integer)cv.getTotalMap().get(rq)).intValue();
	        }
	      }
	      if (total == 0) {
	        rqhgl.put(rq, Float.valueOf(0.0F));
	      } else {
	        rqhgl.put(rq, Float.valueOf(done * 1.0F / total));
	      }
	    }
	    request.setAttribute("rqhgl", rqhgl);
	    float zhgl = 0.0F;
	    int zdone = 0;
	    int ztotal = 0;
	    for (ChjVo cv2 : orderStistcs)
	    {
	      zdone += cv2.getDoneNum();
	      ztotal += cv2.getTotalNum();
	    }
	    if (ztotal > 0) {
	      zhgl = zdone * 1.0F / ztotal;
	    }
	    request.setAttribute("zhgl", Float.valueOf(zhgl));
	    return "lims/zltj/cjhgl";
	}
	/**
	 * @throws JSONException 
	 * 
	 */
	@RequestMapping("/aycfx")
	public String aycfx(HttpServletRequest request) throws JSONException {
		String area = request.getParameter("area");
	    request.setAttribute("area", area);
	  //处理开始结束日期
  		Calendar calendar = Calendar.getInstance();
  		String startDate = request.getParameter("startDate");
  		if(startDate==null||startDate.equals("")){
  			int day = calendar.get(Calendar.DAY_OF_MONTH);
  			if (day <= 25)
  			{
  				calendar.set(Calendar.DAY_OF_MONTH, 26);
  				calendar.add(Calendar.MONTH, -1);
  			}
  			else
  			{
  				calendar.set(Calendar.DAY_OF_MONTH, 26);
  			}
  			startDate = DateUtils.DateFormat(calendar.getTime(), "yyyy-MM-dd");
  		}
  		request.setAttribute("startDate", startDate);
  		
  		String endDate = request.getParameter("endDate");
  		if(endDate==null||endDate.equals("")){
  			int day = calendar.get(Calendar.DAY_OF_MONTH);
  		    if (day >= 26)
  		    {
  		    	calendar.set(Calendar.DAY_OF_MONTH, 25);
  		    	calendar.add(Calendar.MONTH, 1);
  		    }
  		    else
  		    {
  		    	calendar.set(Calendar.DAY_OF_MONTH, 25);
  		    }
  			endDate=DateUtils.DateFormat(calendar.getTime(), "yyyy-MM-dd");
  		}
  		request.setAttribute("endDate", endDate);
  		Map<String, String> ypMap = new HashMap<String, String>();
  		String sql2 = "select t1.matcode,t1.matname from MATERIALS t1";
  		List<Map<String, Object>> ypList = dao.queryListMap(sql2);
  		for (Map<String, Object> map : ypList) {
  	      ypMap.put((String)map.get("matcode"), (String)map.get("matname"));
  	    }
  		String sql3 = "select t2.real_area,t2.real_plant,t1.analyte,count(*) c  "
  				+ "from plantdaily t1,sample_points t2,orders t3,folders f,batches b,"
  				+ "mate_setting2 t4,plants_setting ps "
  				+ " where f.folderno=t3.folderno and f.batchid=b.batchid "
  				+ "and b.type<>'LP' and t1.ordno=t3.ordno  "
  				+ "and t1.matcode=t4.matecode and t1.testcode=t4.testcode "
  				+ "and t1.analyte=t4.analyte  and t1.sample_point_id=t2.sample_point_id "
  				+ "and t2.real_area=? and t2.real_area=ps.area_name "
  				+ "and t2.real_plant=ps.plant and ps.IS_CHECK='1' and t4.areaname = ? "
  				+ "and  t1.s in ('OOS-A','OOS-B','OOS-C')  and b.tasktype not in ('调度加样') "
  				+ " and t3.realsampdate>=to_date(?,'yyyy-MM-dd hh24:mi:ss') "
  				+ "and t3.realsampdate<=to_date(?,'yyyy-MM-dd hh24:mi:ss')  "
  				+ "and not exists(select 1 from NOCHECK_SAMPLE nc where nc.ordno=t3.ordno and nc.status=1)  "
  				+ "group by t2.real_area,t2.real_plant,t1.analyte order by c desc ";
  		List<Map<String, Object>> fxxmList = dao.queryListMap(sql3, area, area, startDate, endDate );
  		Map<String, Integer> numMap = new HashMap<String, Integer>();
  	    Map<String, String> areaMap = new HashMap<String, String>();
  	  for (Map<String, Object> map : fxxmList)
      {
        String analyte = map.get("analyte").toString();
        String realPlant = map.get("realPlant").toString();
        int num = ((BigDecimal)map.get("c")).intValue();
        if (numMap.containsKey(analyte))
        {
          numMap.put(analyte, Integer.valueOf(((Integer)numMap.get(analyte)).intValue() + num));
          areaMap.put(analyte, (String)areaMap.get(analyte) + "," + realPlant);
        }
        else
        {
          numMap.put(analyte, Integer.valueOf(num));
          areaMap.put(analyte, realPlant);
        }
      }
  	Iterator entries = numMap.entrySet().iterator();
    //Object sortfxxmList = new ArrayList();
    List<Map<String, Object>> sortfxxmList = new ArrayList<Map<String, Object>>();
    while (entries.hasNext())
    {
      Map<String, Object> map = new HashMap();
      Map.Entry entry = (Map.Entry)entries.next();
      String key = (String)entry.getKey();
      Integer value = (Integer)entry.getValue();
      map.put("analyte", key);
      map.put("c", value);
      map.put("real_area", areaMap.get(key));
      (sortfxxmList).add(map);
    }
  
    List<String> xTitle = new ArrayList<String>();
    List<String> cjArea = new ArrayList<String>();
    List<Integer> serail1 = new ArrayList<Integer>();
    
    int sum = 0;
    for (Map<String, Object> map : sortfxxmList)
    {
      xTitle.add(map.get("analyte").toString());
      cjArea.add(map.get("real_area").toString());
      int num = ((Integer)map.get("c")).intValue();
      serail1.add(Integer.valueOf(num));
      sum += num;
    }
    List<Float> serail2 = new ArrayList<Float>();
    int lj = 0;
    int maxNum = 0;
    for (Iterator localIterator3 = serail1.iterator(); localIterator3.hasNext();)
    {
      int num = ((Integer)localIterator3.next()).intValue();
      lj += num;
      if (maxNum == 0) {
        maxNum = num;
      }
      serail2.add(Float.valueOf(lj * 100.0F / sum));
    }
    JSONObject json = new JSONObject();
    if (xTitle.size() > 12) {
      json.put("xTitle", xTitle.subList(0, 12).toArray());
    } else {
      json.put("xTitle", xTitle.toArray());
    }
    request.setAttribute("xTitle", xTitle);
    if (serail2.size() > 12) {
      json.put("serail2", serail2.subList(0, 12));
    } else {
      json.put("serail2", serail2.toArray());
    }
    request.setAttribute("serail2", serail2);
    if (serail1.size() > 12) {
      json.put("serail1", serail1.subList(0, 12));
    } else {
      json.put("serail1", serail1.toArray());
    }
    request.setAttribute("serail1", serail1);
    if (maxNum * 2 < sum) {
      request.setAttribute("max", Integer.valueOf(maxNum * 2));
    } else {
      request.setAttribute("max", Integer.valueOf(sum));
    }
    request.setAttribute("areaName", area);
    request.setAttribute("cjArea", cjArea);
    request.setAttribute("sum", Integer.valueOf(sum));
    request.setAttribute("json", json.toString());
    request.setAttribute("isArea", "1");
    return "lims/zltj/chart";
	}
	/**
	 * 装置统计 不合格项目
	 */
	@RequestMapping("/zzYCFX")
	public String zzYCFX(HttpServletRequest request) {
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		String area = request.getParameter("area");
		String plant = request.getParameter("plant");
		String sql="select t1.sinonym,count(*) num "
				+ " from plantdaily t1,sample_points t2,orders t3,folders f,batches b,mate_setting2 t4 "
				+ " where f.folderno=t3.folderno and f.batchid=b.batchid and b.type<>'LP' and t1.ordno=t3.ordno  and t1.matcode=t4.matecode and t1.testcode=t4.testcode and t1.analyte=t4.analyte "
				+ " and t1.sample_point_id=t2.sample_point_id and t2.real_area=? and t2.real_plant=? and t4.is_check='1' "
				+ " and t4.areaname = ? and t3.status in ('Done','OOS','Active') and t1.s in ('OOS-A','OOS-B','OOS-C') "
				+ " and t3.realsampdate>=to_date(?,'yyyy-MM-dd hh24:mi:ss') and t3.realsampdate<=to_date(?,'yyyy-MM-dd hh24:mi:ss') "
				+ " and not exists(select 1 from NOCHECK_SAMPLE nc where nc.ordno=t3.ordno) group by t1.sinonym";
		List<Map<String, Object>> list = dao.queryListMap(sql, area,plant,area,startDate,endDate);
		request.setAttribute("zzYCFX",list);
		return "lims/zltj/zzYCFX";
	}
	/**
	 * 显示不合格分析项原因
	 */
	@RequestMapping("/showYcfx")
	public String showYCFX(HttpServletRequest request)
	  {
	    String sql1 = "select t2.real_area,t2.real_plant,t1.analyte,t1.remark,"
	    		+ "to_char(t1.sampdate,'yyyy-MM-dd hh24:mi:ss') sampdate "
	    		+ "from PLANT_CHECKDATA t1,sample_points t2,orders t3,folders f,"
	    		+ "batches b,plants_setting ps  where f.folderno=t3.folderno and "
	    		+ "f.batchid=b.batchid and b.type<>'LP' and t1.ordno=t3.ordno  "
	    		+ "and t1.sample_point_id=t2.sample_point_id and t2.real_area=? "
	    		+ "and t2.real_area=ps.area_name and t2.real_plant=ps.plant "
	    		+ "and ps.IS_CHECK='1'  and b.tasktype not in ('调度加样')  "
	    		+ "and t3.realsampdate>=to_date(?,'yyyy-MM-dd hh24:mi:ss') "
	    		+ "and t3.realsampdate<=to_date(?,'yyyy-MM-dd hh24:mi:ss')  "
	    		+ "and not exists(select 1 from NOCHECK_SAMPLE nc where nc.ordno=t3.ordno and nc.status=1)  "
	    		+ "and t1.remark is not null and t1.analyte=? order by t1.sampdate";
	    
	    String sql2 = "select t.real_area,t.real_plant,t1.analyte,t1.remark,"
	    		+ "to_char(t1.sampdate,'yyyy-MM-dd hh24:mi:ss') sampdate    "
	    		+ "from sample_points t,PLANT_CHECKDATA t1,folders f,batches b,AREASITES t3,"
	    		+ "plants_setting ps where t3.dept=? and t1.folderno=f.folderno "
	    		+ "and f.batchid=b.batchid  and t.real_area = t3.area_name "
	    		+ "and ps.area_name=t.real_area and ps.plant = t.real_plant and ps.is_check='1'  "
	    		+ "and t.sample_point_id=t1.sample_point_id  and t1.s in ('OOS-A','OOS-B','OOS-C')  "
	    		+ "and t1.sampdate>=to_date(?,'yyyy-MM-dd hh24:mi:ss') "
	    		+ "and t1.sampdate<=to_date(?,'yyyy-MM-dd hh24:mi:ss')  "
	    		+ "and not exists(select 1 from NOCHECK_SAMPLE nc where nc.ordno=t1.ordno and nc.status=1)  "
	    		+ "and t1.remark is not null and t1.analyte=? order by t1.sampdate";
	    
	    String isArea = request.getParameter("isArea");
	    String startDate = request.getParameter("startDate");
	    String endDate = request.getParameter("endDate");
	    String areaName = request.getParameter("areaName");
	    String analyte = request.getParameter("analyte");
	    if ("1".equals(isArea)) {
	      request.setAttribute("ycfx", this.dao.queryListMap(sql1, areaName, startDate, endDate, analyte ));
	    } else {
	      request.setAttribute("ycfx", this.dao.queryListMap(sql2, areaName, startDate, endDate, analyte ));
	    }
	    return "lims/zltj/showYcfx";
	  }
	/**
	 * 异常分析项目
	 * @throws JSONException 
	 */
	@RequestMapping("/ycfx")
	public String ycfx(HttpServletRequest request) throws JSONException {
		//处理开始结束日期
		Calendar calendar = Calendar.getInstance();
		String startDate = request.getParameter("startDate");
		if(startDate==null||startDate.equals("")){
			int day = calendar.get(Calendar.DAY_OF_MONTH);
			if (day <= 25)
			{
				calendar.set(Calendar.DAY_OF_MONTH, 26);
				calendar.add(Calendar.MONTH, -1);
			}
			else
			{
				calendar.set(Calendar.DAY_OF_MONTH, 26);
			}
			startDate = DateUtils.DateFormat(calendar.getTime(), "yyyy-MM-dd");
		}
		request.setAttribute("startDate", startDate);
		
		String endDate = request.getParameter("endDate");
		if(endDate==null||endDate.equals("")){
			int day = calendar.get(Calendar.DAY_OF_MONTH);
		    if (day >= 26)
		    {
		    	calendar.set(Calendar.DAY_OF_MONTH, 25);
		    	calendar.add(Calendar.MONTH, 1);
		    }
		    else
		    {
		    	calendar.set(Calendar.DAY_OF_MONTH, 25);
		    }
			endDate=DateUtils.DateFormat(calendar.getTime(), "yyyy-MM-dd");
		}
		request.setAttribute("endDate", endDate);
		String sql1 = "select t.real_area, t.real_plant, t1.analyte, count(*) c " + 
				"  from sample_points      t, " + 
				"       plantdaily         t1, " + 
				"       folders            f, " + 
				"       batches            b, " + 
				"       mate_setting2      t2," + 
				"       AREASITES          t3, " + 
				"       plants_setting     ps, " + 
				"       sample_points_user s " + 
				" where t1.folderno = f.folderno " + 
				"   and f.batchid = b.batchid " + 
				"   and t.real_area = t3.area_name " + 
				"   and ps.area_name = t.real_area " + 
				"   and ps.plant = t.real_plant " + 
				"   and ps.is_check = '1' " + 
				"   and t1.matcode = t2.matecode " + 
				"   and t1.testcode = t2.testcode " + 
				"   and t1.analyte = t2.analyte " + 
				"   and t2.areaname = t.real_area " + 
				"   and t.sample_point_id = t1.sample_point_id " + 
				"   and t1.sample_point_id = s.sample_point_id " + 
				"   and s.usrnam = ? " + 
				"   and t1.s in ('OOS-A', 'OOS-B', 'OOS-C') " + 
				"   and t1.sampdate >= " + 
				"       to_date(?, 'yyyy-MM-dd hh24:mi:ss') " + 
				"   and t1.sampdate <= " + 
				"       to_date(?, 'yyyy-MM-dd hh24:mi:ss') " + 
				"   and not exists (select 1 " + 
				"          from NOCHECK_SAMPLE nc " + 
				"         where nc.ordno = t1.ordno " + 
				"           and nc.status = 1) " + 
				" group by t.real_area, t.real_plant, t1.analyte " + 
				" order by c desc";
	    
	    List<Map<String, Object>> fxxmList = dao.queryListMap(sql1, SessionFactory.getUsrName(), startDate + " 00:00:00", endDate + " 23:59:59" );
	    List<String> xTitle = new ArrayList<String>();
	    List<String> cjArea = new ArrayList<String>();
	    List<Integer> serail1 = new ArrayList<Integer>();
	    Map<String, Integer> numMap = new HashMap<String, Integer>();
	    Map<String, String> areaMap = new HashMap<String, String>();
	    for (Map<String, Object> map : fxxmList)
	    {
	      String analyte = map.get("analyte").toString();
	      String realArea = map.get("realArea").toString();
	      String realPlant = map.get("realPlant").toString();
	      int num = ((BigDecimal) map.get("c")).intValue();
	      if (numMap.containsKey(analyte))
	      {
	        numMap.put(analyte, Integer.valueOf(((Integer)numMap.get(analyte)).intValue() + num));
	        areaMap.put(analyte, (String)areaMap.get(analyte) + "," + realArea + "(" + realPlant + ")");
	      }
	      else
	      {
	        numMap.put(analyte, Integer.valueOf(num));
	        areaMap.put(analyte, realArea);
	      }
	    }
	    Iterator entries = numMap.entrySet().iterator();
	    
	    List<Map<String, Object>> sortfxxmList = new ArrayList<Map<String, Object>>();
	    Integer value;
	    while (entries.hasNext())
	    {
	      Map<String, Object> map = new HashMap<String, Object>();
	      Map.Entry entry = (Map.Entry)entries.next();
	      String key = (String)entry.getKey();
	      value = (Integer)entry.getValue();
	      map.put("analyte", key);
	      map.put("c", value);
	      map.put("real_area", areaMap.get(key));
	      sortfxxmList.add(map);
	    }
	    
	    
	    int sum = 0;
	    int maxNum = 0;
	    for (Map<String, Object> map : sortfxxmList)
	    {
	      xTitle.add(map.get("analyte").toString());
	      cjArea.add(map.get("real_area").toString());
	      int num = ((Integer)map.get("c")).intValue();
	      serail1.add(Integer.valueOf(num));
	      sum += num;
	    }
	    List<Float> serail2 = new ArrayList<Float>();
	    int lj = 0;
	    for (Iterator localIterator2 = serail1.iterator(); localIterator2.hasNext();)
	    {
	      int num = ((Integer)localIterator2.next()).intValue();
	      lj += num;
	      if (maxNum == 0) {
	        maxNum = num;
	      }
	      serail2.add(Float.valueOf(lj * 100.0F / sum));
	    }
	    JSONObject json = new JSONObject();
	    if (xTitle.size() > 12) {
	      json.put("xTitle", xTitle.subList(0, 12).toArray());
	    } else {
	      json.put("xTitle", xTitle.toArray());
	    }
	    request.setAttribute("xTitle", xTitle);
	    if (serail2.size() > 12) {
	      json.put("serail2", serail2.subList(0, 12));
	    } else {
	      json.put("serail2", serail2.toArray());
	    }
	    request.setAttribute("serail2", serail2);
	    if (serail1.size() > 12) {
	      json.put("serail1", serail1.subList(0, 12));
	    } else {
	      json.put("serail1", serail1.toArray());
	    }
	    request.setAttribute("serail1", serail1);
	    if (maxNum * 2 < sum) {
	      request.setAttribute("max", Integer.valueOf(maxNum * 2));
	    } else {
	      request.setAttribute("max", Integer.valueOf(sum));
	    }
	    request.setAttribute("areaName", ProjectUtils.getDept());
	    request.setAttribute("cjArea", cjArea);
	    request.setAttribute("sum", Integer.valueOf(sum));
	    request.setAttribute("json", json.toString());
	    request.setAttribute("isArea", "0");
	    
		return "lims/zltj/chart";
	}
	/**
	 * 装置月度合格率
	 */
	@RequestMapping("/zzhglNew")
	public String zzhglNew(HttpServletRequest request) {
		String area = request.getParameter("area");
		request.setAttribute("area", area);
		
		//处理开始结束日期
		Calendar calendar = Calendar.getInstance();
		String startDate = request.getParameter("startDate");
		if(startDate==null||startDate.equals("")){
			int day = calendar.get(Calendar.DAY_OF_MONTH);
			if (day <= 25)
			{
				calendar.set(Calendar.DAY_OF_MONTH, 26);
				calendar.add(Calendar.MONTH, -1);
			}
			else
			{
				calendar.set(Calendar.DAY_OF_MONTH, 26);
			}
			startDate = DateUtils.DateFormat(calendar.getTime(), "yyyy-MM-dd");
		}
		request.setAttribute("startDate", startDate);
		
		String endDate = request.getParameter("endDate");
		if(endDate==null||endDate.equals("")){
			int day = calendar.get(Calendar.DAY_OF_MONTH);
		    if (day >= 26)
		    {
		    	calendar.set(Calendar.DAY_OF_MONTH, 25);
		    	calendar.add(Calendar.MONTH, 1);
		    }
		    else
		    {
		    	calendar.set(Calendar.DAY_OF_MONTH, 25);
		    }
			endDate=DateUtils.DateFormat(calendar.getTime(), "yyyy-MM-dd");
		}
		request.setAttribute("endDate", endDate);
		//获取时间列表
		List<String> dateList = service.getDayList(startDate, endDate);
		request.setAttribute("dateList", dateList);
		startDate = startDate+" 00:00:00";
		endDate = endDate +" 23:59:59";
		String sql1 = "select t.real_area area_name,t.real_plant plant,t1.s status,to_char(t1.sampdate,'MM-dd') rq ,t1.matcode, b.tasktype "
				+ " from sample_points t,plantdaily t1,folders f,batches b,mate_setting2 t2,"
				+ "AREASITES t3,plants_setting t4  where t3.dept=? and t1.folderno=f.folderno "
				+ "and f.batchid=b.batchid  and t.real_area = t3.area_name  "
				+ "and t1.matcode=t2.matecode and t1.testcode=t2.testcode "
				+ "and t1.analyte=t2.analyte  and t2.areaname=t.real_area and t2.is_check='1' "
				+ "and t.sample_point_id=t1.sample_point_id  and t4.area_name = t.area_name and t4.plant = t.plant and t4.is_check='1'"
				+ "and t1.s in ('Done','OOS-A','OOS-B','OOS-C')  "
				+ "and t1.sampdate>=to_date(?,'yyyy-MM-dd hh24:mi:ss') "
				+ "and t1.sampdate<=to_date(?,'yyyy-MM-dd hh24:mi:ss')  "
				+ "and not exists(select 1 from NOCHECK_SAMPLE nc where nc.ordno=t1.ordno and nc.status=1)  "
				+ "order by nlssort(t.real_area, 'NLS_SORT=SCHINESE_PINYIN_M'),nlssort(t.real_plant, 'NLS_SORT=SCHINESE_PINYIN_M'),status,rq";
	    
	    List<Map<String, Object>> cjList = this.dao.queryListMap(sql1, new Object[] { ProjectUtils.getDept(), startDate, endDate });
	    List<ChjVo> rdata = new ArrayList<ChjVo>();
		ChjVo cj = null;
		ZzhiVo zz = null;
		for(Map<String,Object> map:cjList){
			String areaName = (String) map.get("areaName");
			String plant = (String) map.get("plant");
			String rq = (String) map.get("rq");
			String status = (String) map.get("status");
			String taskType = (String) map.get("tasktype");
			
			if(cj == null||!areaName.equals(cj.getName())){
				cj = new ChjVo();
				cj.setName(areaName);
				rdata.add(cj);
			}
			
			if(zz==null||!areaName.equals(zz.getAreaName())||!plant.equals(zz.getName())){
				zz=new ZzhiVo(plant);
				zz.setAreaName(areaName);
				String sql = "select t1.sinonym,count(*) num  "
						+ "from plantdaily t1,sample_points t2,orders t3,folders f,batches b,mate_setting2 t4,plants_setting t5  "
						+ "where f.folderno=t3.folderno and f.batchid=b.batchid and b.type<>'LP'"
						+ " and t1.ordno=t3.ordno  and t1.matcode=t4.matecode and t1.testcode=t4.testcode "
						+ "and t1.analyte=t4.analyte  and t1.sample_point_id=t2.sample_point_id and t5.area_name=t2.area_name and t5.plant=t2.plant and t5.is_check='1'"
						+ "and t2.real_area=? and t2.real_plant=?  and t4.areaname = ? and t4.is_check='1' "
						+ "and t3.status in ('Done','OOS','Active') and t1.s in ('OOS-A','OOS-B','OOS-C') "
						+ " and t3.realsampdate>=to_date(?,'yyyy-MM-dd hh24:mi:ss') "
						+ "and t3.realsampdate<=to_date(?,'yyyy-MM-dd hh24:mi:ss')  "
						+ "and not exists(select 1 from NOCHECK_SAMPLE nc where nc.ordno=t3.ordno) "
						+ "group by t1.sinonym";
		        
		        List<Map<String, Object>> List = this.dao.queryListMap(sql, areaName, plant, areaName, startDate, endDate );
		        zz.setYcfx(List);
				cj.addZzh(zz);
			}
			
			zz.addRqNum(status,rq,taskType);
		}
		
		//String sql2 ="select distinct area_name from AREAS order by sorter";
		String sql2= "select distinct p.area_name from "
				+ "plants p,plants_setting ps "
				+ "where p.area_name=ps.area_name and p.plant=ps.plant and ps.username = ? "
				+ "and ps.is_check ='1' "
				+ "order by nlssort(p.area_name, 'NLS_SORT=SCHINESE_PINYIN_M')";
		List<ChjVo> orderStistcs = new ArrayList<ChjVo>();
		List<String> list = dao.queryListValue(sql2, String.class,SessionFactory.getUsrName());
		for(String d:list){
			for(ChjVo cjvo:rdata){
				if(d.equals(cjvo.getName())){
					orderStistcs.add(cjvo);
					break;
				}
			}
		}
		
		request.setAttribute("dateList", dateList);
		request.setAttribute("rdata", orderStistcs);
		Map<String, Float> rqhgl = new HashMap<String, Float>();
	    ZzhiVo zv;
	    ChjVo cv;
	    for (String rq : dateList)
	    {
	      int done = 0;
	      int total = 0;
	      for(ChjVo cVo:orderStistcs) {
	    	  List<ZzhiVo> zzhiVos = cVo.getData();
	    	  for(ZzhiVo zVo:zzhiVos) {
	    		  if (zVo.getDoneMap().containsKey(rq)) {
	    			  done += ((Integer)zVo.getDoneMap().get(rq)).intValue();
	    		  }
	    		  if(zVo.getTotalMap().containsKey(rq)) {
	    			  total += ((Integer)zVo.getTotalMap().get(rq)).intValue();
	    		  }
	    	  }
	    	  if (total == 0) {
	  	        rqhgl.put(rq, Float.valueOf(0.0F));
	  	      } else {
	  	        rqhgl.put(rq, Float.valueOf(done * 1.0F / total));
	  	      }
	      }
	     /* Iterator localIterator3;
	      for (Iterator localIterator2 = ((List)orderStistcs).iterator(); localIterator2.hasNext(); localIterator3.hasNext())
	      {
	        cv = (ChjVo)localIterator2.next();
	        localIterator3 = cv.getData().iterator(); continue;zv = (ZzhiVo)localIterator3.next();
	        if (zv.getDoneMap().containsKey(rq)) {
	          done += ((Integer)zv.getDoneMap().get(rq)).intValue();
	        }
	        if (zv.getTotalMap().containsKey(rq)) {
	          total += ((Integer)zv.getTotalMap().get(rq)).intValue();
	        }
	      }
	      if (total == 0) {
	        rqhgl.put(rq, Float.valueOf(0.0F));
	      } else {
	        rqhgl.put(rq, Float.valueOf(done * 1.0F / total));
	      }*/
	    }
	    request.setAttribute("rqhgl", rqhgl);
	    
	    float zhgl = 0.0F;
	    int zdone = 0;
	    int ztotal = 0;
	    for(ChjVo cVo:orderStistcs) {
	    	List<ZzhiVo> zzhiVos = cVo.getData();
	    	  for(ZzhiVo zVo:zzhiVos) {
	    		  zdone += zVo.getDoneNum();
	    		  ztotal += zVo.getTotalNum();
	    	  }
	    }
	   /* for (ChjVo cv22 = (ChjVo) ((List)orderStistcs).iterator(); ((Iterator) cv22).hasNext(); ((Iterator) zv).hasNext())
	    {
	      ChjVo cv2 = (ChjVo)((Iterator) cv22).next();
	      zv = (ZzhiVo) cv2.getData().iterator(); 
	      continue;
	      ZzhiVo zv222 = (ZzhiVo) ((Iterator) zv).next();
	      zdone += zv222.getDoneNum();
	      ztotal += zv222.getTotalNum();
	    }*/
	    if (ztotal > 0) {
	      zhgl = zdone * 1.0F / ztotal;
	    }
	    request.setAttribute("zhgl", Float.valueOf(zhgl));
	    return "lims/zltj/zzhgl";
	}
	/**
	 * 异常数据汇总
	 */
	@RequestMapping({"/detailData"})
	public String detailData(HttpServletRequest request) {
		String startTime = request.getParameter("startTime");
	    String endTime = request.getParameter("endTime");
	    if ((startTime == null) || (endTime == null) || ("".equals(startTime)) || ("".equals(endTime)))
	    {
	      Calendar calendar = Calendar.getInstance();
	      DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	      endTime = format.format(calendar.getTime());
	      calendar.add(11, -8);
	      startTime = format.format(calendar.getTime());
	    }
	    request.setAttribute("endTime", endTime);
	    request.setAttribute("startTime", startTime);
	    String sql = "select m.matname,to_char(p.sampdate,'hh24:mi') sampdate, p.ordno,s.area_name,p.testcode,p.analyte,p.specno,p.sinonym,p.final analyteResult,"
	    		+ "p.units,p.description,spec.higha,spec.lowa,spec.highb,spec.lowb,spec.highc,spec.lowc,"
	    		+ "spec.charlimits  from plantdaily p,folders f,sample_points s,MATERIALS m,spec_analytes spec "
	    		+ " where p.s in ('OOS-A','OOS-B','OOS-C') and f.dept=? "
	    		+ " AND P.SAMPDATE>=TO_DATE(?,'yyyy-MM-dd hh24:mi:ss')  "
	    		+ "AND P.SAMPDATE<=TO_DATE(?,'yyyy-MM-dd hh24:mi:ss')  "
	    		+ "and s.sample_point_id=p.sample_point_id  and p.matcode=m.matcode  "
	    		+ "AND P.FOLDERNO = F.FOLDERNO AND spec.specno=p.specno and p.testcode=spec.testcode "
	    		+ "and p.analyte=spec.analyte  "
	    		+ "and exists(select 1 from sample_points_user t1 where   t1.sample_point_id = s.sample_point_id and t1.usrnam=?) "
	    		+ "order by nlssort(area_name, 'NLS_SORT=SCHINESE_PINYIN_M'),sampdate";
	    
	    List<Map<String, Object>> analyteList = this.dao.queryListMap(sql, SessionFactory.getDept(), startTime, endTime,SessionFactory.getUsrName() );
	    Map<String, List> areaMap = new LinkedHashMap<String, List>();
	    String curArea = null;
	    ArrayList<Map<String, Object>> array = null;
	    for (Map<String, Object> map : analyteList)
	    {
	      
	      Map<String, Object> zlMap = map;
          String zb = "";
          if(zlMap.get("lowa")!=null){
				zb = "≥"+zlMap.get("lowa").toString();
			}
			
			if(zlMap.get("higha")!=null){
				zb = "≤"+zlMap.get("higha").toString();
			}
			
			if(zlMap.get("lowa")!=null&&zlMap.get("higha")!=null){
				zb = zlMap.get("lowa")+"-"+zlMap.get("higha");
				
			}
			
			if(zlMap.get("lowb")!=null){
				zb = "≥"+zlMap.get("lowb").toString();
			}
			
			if(zlMap.get("highb")!=null){
				zb = "≤"+zlMap.get("highb").toString();
			}
			
			if(zlMap.get("lowb")!=null&&zlMap.get("highb")!=null){
				zb = zlMap.get("lowb")+"-"+zlMap.get("highb");
				
			}
			
			if(zlMap.get("lowc")!=null){
				zb = "≥"+zlMap.get("lowc").toString();
			}
			
			if(zlMap.get("highc")!=null){
				zb = "≤"+zlMap.get("highc").toString();
			}
			
			if(zlMap.get("lowc")!=null&&zlMap.get("highc")!=null){
				zb = zlMap.get("lowc")+"-"+zlMap.get("highc");
				
			}
			
			if(zlMap.get("innerlowa")!=null){
				zb = "≥"+zlMap.get("innerlowa").toString();
			}
			
			if(zlMap.get("innerhigha")!=null){
				zb = "≤"+zlMap.get("innerhigha").toString();
			}
			
          if ((zlMap.get("innerlowa") != null) && (zlMap.get("innerhigha") != null)) {
            zb = zlMap.get("innerlowa") + "-" + zlMap.get("innerhigha");
          }
          if ((zlMap.get("charlimits") != null) && (zlMap.get("charlimits") != "")) {
            zb = (String)zlMap.get("charlimits");
          }
	      map.put("limit", zb);
	      String areaName = (String)map.get("areaName");
	      if ((curArea == null) || (!curArea.equals(areaName)))
	      {
	        curArea = areaName;
	        array = new ArrayList<Map<String, Object>>();
	        areaMap.put(curArea, array);
	      }
	      array.add(map);
	    }
	    request.setAttribute("areaOOS", areaMap);
	    return "lims/zltj/oosDetail";
		
	}
	/**
	 * 免考核样品申请
	 */
	 @RequestMapping({"/apply"})
	 public String apply(HttpServletRequest request)
	 {
	    request.setAttribute("areaName", SessionFactory.getFullName());
	    String sql = "select ID,AREA_NAME,PLANT,status,approveremark, to_char(START_TIME,'yyyy-MM-dd hh24:mi:ss') startTime,to_char(END_TIME,'yyyy-MM-dd hh24:mi:ss') endTime,remark ,to_char(approvedate,'yyyy-MM-dd hh24:mi:ss') approveDate,m.matname from NOCHECK_INTERVAL,MATERIALS m where NOCHECK_INTERVAL.matcode=m.matcode and area_name=? and (status=0 or status=-1) order by ADDDATE";
	    
	    List<Map<String, Object>> list = this.dao.queryListMap(sql,SessionFactory.getFullName() );
	    request.setAttribute("draftInFO", list);
	    return "lims/mk/applyNew";
	  }
	 /**
	  * 添加免考核信息
	  */
	 @RequestMapping({"/forAddPlant"})
	 public String forAddPlant(HttpServletRequest request)
	 {
	    request.setAttribute("areaName", SessionFactory.getFullName());
	    String sql = "select distinct plant from sample_points where area_name = ? and real_area=?";
	    List<String> list = this.dao.queryListValue(sql, String.class,SessionFactory.getFullName(),SessionFactory.getFullName());
	    request.setAttribute("plants", list);
	    return "lims/mk/forAddPlant";
	 }
	 /**
	  * 查询样品考核项目
	  */
	 @RequestMapping(value="/queryAnalyteByMatcode",produces="application/json;charset=UTF-8",method={org.springframework.web.bind.annotation.RequestMethod.POST})
	 @ResponseBody
	 public String queryAnalyteByMatcode(HttpServletRequest request) {
		 String areaname = request.getParameter("areaName");
		 String matcode = request.getParameter("matcode");
		 String sql ="select distinct analyte from MATE_SETTING2 where areaname=? and matecode =?   and username=?";
		 JSONArray rd = this.dao.queryJSONArray(sql,  areaname, matcode,SessionFactory.getUsrName() );
		 return rd.toString();
		 
	 }
	 /**
	  * 获取切换车间装置
	  */
	  @RequestMapping(value={"/queryMatByAreaPlant"}, produces={"application/json;charset=UTF-8"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
	  @ResponseBody
	  public String queryMatByAreaPlant(HttpServletRequest request)
	  {
	    String areaname = request.getParameter("areaName");
	    String plant = request.getParameter("plant");
	    String sql = "select distinct m.matCode,m.matName from ORDERS o,SAMPLE_POINTS s,MATERIALS m where o.sample_point_id=s.sample_point_id and o.matcode=m.matcode and s.AREA_NAME=? and s.plant=?";
	    JSONArray rd = this.dao.queryJSONArray(sql,  areaname, plant );
	    return rd.toString();
	  }
	  /**
	   * 添加免考核信息操作
	   * @param request
	   * @return
	   */
	  @RequestMapping(value={"/addPlant"}, produces={"application/json;charset=UTF-8"})
	  @ResponseBody
	  public String addPlant(HttpServletRequest request)
	  {
	    String areaName = request.getParameter("areaName");
	    String plant = request.getParameter("plant");
	    String matcode = request.getParameter("matCode");
	    String analyte = request.getParameter("analyte");
	    String remark = request.getParameter("remark");
	    String startTime = request.getParameter("startTime");
	    String endTime = request.getParameter("endTime");
	    String insertSql = "insert into NOCHECK_INTERVAL(ID,AREA_NAME,PLANT,START_TIME,END_TIME,remark,status,applyuser,adddate,matcode,analytes) values(sys_guid(),?,?,to_date(?,'yyyy-MM-dd hh24:mi:ss'),to_date(?,'yyyy-MM-dd hh24:mi:ss'),?,0,?,sysdate,?,?)";
	    
	    int i = this.dao.excuteUpdate(insertSql,areaName, plant, startTime, endTime, remark, SessionFactory.getUsrName(), matcode,analyte);
	    if (i > 0) {
	      return MsgUtils.success().toString();
	    }
	    return MsgUtils.fail("添加失败").toString();
	  }
	  /**
	   * 删除免考核信息
	   */
	  @RequestMapping(value={"/deletePlant"}, produces={"application/json;charset=UTF-8"})
	  @ResponseBody
	  public String deletePlant(HttpServletRequest request)
	  {
	    String[] selectIds = request.getParameterValues("selectId");
	    if (selectIds == null) {
	      return "";
	    }
	    String deleteSql = "delete from NOCHECK_INTERVAL where id=? ";
	   
	    for (int i = 0; i < selectIds.length; i++)
	    {
	      String id = selectIds[i];
	      this.dao.excuteUpdate(deleteSql,id);
	    }
	    return MsgUtils.success().toString();
	  }
	  /**
	   * 提交免考核样品操作
	   */
	  @RequestMapping(value={"/submitApply"}, produces={"application/json;charset=UTF-8"})
	  @ResponseBody
	  public String submitApply(HttpServletRequest request)
	  {
	    String[] selectIds = request.getParameterValues("selectId");
	    
	    String updateSql = "update NOCHECK_INTERVAL set status=1,applydate=sysdate where id=? ";
	    if (selectIds == null) {
	      return "";
	    }
	    
	    for (int i = 0; i < selectIds.length; i++)
	    {
	      String id = selectIds[i];
	      this.dao.excuteUpdate(updateSql,id);
	    }
	    return MsgUtils.success().toString();
	  }
	  /**
	   * 退回审核
	   */
	  @RequestMapping(value={"/refuse"}, produces={"application/json;charset=UTF-8"})
	  @ResponseBody
	  public String refuse(HttpServletRequest request)
	  {
	    String[] selectIds = request.getParameterValues("selectId");
	    String reason = request.getParameter("reason");
	    
	    String updateSql = "update NOCHECK_INTERVAL set status=0,approveremark=? where id=? ";
	    if (selectIds == null) {
	      return "";
	    }
	    for (int i = 0; i < selectIds.length; i++)
	    {
	      String id = selectIds[i];
	      this.dao.excuteUpdate(updateSql, reason, id );
	    }
	    return MsgUtils.success().toString();
	  }
	  /**
	   * 审核免考核操作
	   */
	  @RequestMapping(value={"/approveOper"}, produces={"application/json;charset=UTF-8"})
	  @ResponseBody
	  public String approveOper(HttpServletRequest request)
	  {
	    String[] selectIds = request.getParameterValues("selectId");
	    
	    String updateSql = "update NOCHECK_INTERVAL set status=2,approveDate=sysdate where id=? ";
	    if (selectIds == null) {
	      return "";
	    }
	    for (int i = 0; i < selectIds.length; i++)
	    {
	      String id = selectIds[i];
	      this.dao.excuteUpdate(updateSql, id );
	    }
	    return MsgUtils.success().toString();
	  }
	  /**
	   * 获取待审核免加样数据
	   */
	  @RequestMapping({"/approve"})
	  public String approve(HttpServletRequest request)
	  {
	    request.setAttribute("deptName", ProjectUtils.getDept());
	    String sql = "select ID,t2.AREA_NAME,t2.PLANT,m.matname,t2.status, to_char(START_TIME,'yyyy-MM-dd hh24:mi:ss') startTime,to_char(END_TIME,'yyyy-MM-dd hh24:mi:ss') endTime,remark,to_char(applydate,'yyyy-MM-dd hh24:mi:ss') applydate  "
	    		+ "from AREASITES t1,NOCHECK_INTERVAL t2,MATERIALS m where t1.area_name=t2.area_name and t2.matcode=m.matcode "
	    		+ "and t1.dept=? and status=1 order by ADDDATE";
	    
	    List<Map<String, Object>> list = this.dao.queryListMap(sql,ProjectUtils.getDept() );
	    request.setAttribute("plants", list);
	    return "lims/mk/approve";
	  }
	  /**
	   * 查询免考核审核记录
	   */
	  @RequestMapping({"/queryApply"})
	  public String queryApply(HttpServletRequest request)
	  {
	    String startTime = request.getParameter("startTime");
	    if ((startTime == null) || ("".equals(startTime)))
	    {
	      Calendar calendar = Calendar.getInstance();
	      calendar.add(5, -7);
	      startTime = DateUtils.DateFormat(calendar.getTime(), "yyyy-MM-dd");
	    }
	    request.setAttribute("startTime", startTime);
	    String endTime = request.getParameter("endTime");
	    if ((endTime != null) && (!"".equals(endTime)))
	    {
	      Calendar calendar = Calendar.getInstance();
	      endTime = DateUtils.DateFormat(calendar.getTime(), "yyyy-MM-dd");
	    }
	    request.setAttribute("endTime", endTime);
	    request.setAttribute("areaName", SessionFactory.getFullName());
	    String sql = "select ID,AREA_NAME,PLANT,status,approveremark,m.matname, to_char(applydate, 'yyyy-MM-dd hh24:mi:ss') applydate, "
	    		+ "to_char(START_TIME,'yyyy-MM-dd hh24:mi:ss') startTime,"
	    		+ "to_char(END_TIME,'yyyy-MM-dd hh24:mi:ss') endTime,remark ,"
	    		+ "to_char(approvedate,'yyyy-MM-dd hh24:mi:ss') approveDate,(select fullname from users where usrnam=NOCHECK_INTERVAL.approve) approve "
	    		+ "from NOCHECK_INTERVAL,MATERIALS m where NOCHECK_INTERVAL.matcode=m.matcode "
	    		+ "and   area_name=? and to_char(ADDDATE,'yyyy-MM-dd')>=? "
	    		+ "and to_char(ADDDATE,'yyyy-MM-dd') <=? and (status=1 or status=2) "
	    		+ "order by ADDDATE";
	    
	    List<Map<String, Object>> list = this.dao.queryListMap(sql, SessionFactory.getFullName(), startTime, endTime );
	    request.setAttribute("draftInFO", list);
	    return "lims/mk/applyRecord";
	  }
	
}
