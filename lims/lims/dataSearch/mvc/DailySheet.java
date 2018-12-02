package lims.dataSearch.mvc;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLDecoder;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.wiring.BeanWiringInfo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.core.handler.SessionFactory;

import lims.dataSearch.services.DailyService;
import lims.dataSearch.services.ExcelTemplate;
import lims.dataSearch.vo.CheckData;
import lims.dataSearch.vo.Orders;
import lims.dataSearch.vo.ParamsVo;
import lims.tools.LimsTools;
import pers.czf.commonUtils.DateUtils;
import pers.czf.commonUtils.MsgUtils;
import pers.czf.dbManager.Dao;

@Controller
@RequestMapping("lims/dataSearch/daily")
public class DailySheet {
	
	private static final Logger logger = LoggerFactory.getLogger(DailySheet.class);
	
	@Autowired
	private DailyService service;
	
	@Autowired
	private Dao dao;
	
	/**
	 * 装置日报--按采样点
	 * 针对中控样品进行查询
	 * @param request
	 * @return
	 */
	@RequestMapping("/point")
	public String point(HttpServletRequest request){
		request.setAttribute("title", request.getParameter("title"));
		//获取当前日期
		LocalDate localDate = LocalDate.now();
		String endDate = localDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		String startDate = localDate.minusDays(1).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		
		request.setAttribute("startDate",startDate);
		request.setAttribute("endDate",endDate);
		
		//获取车间、装置信息
		return "lims/dataSearch/daily/pointIndex";
	}
	
  @RequestMapping({"/pointBatchno"})
  public String pointBatchno(HttpServletRequest request)
  {
    request.setAttribute("title", request.getParameter("title"));
    
    LocalDate localDate = LocalDate.now();
    String endDate = localDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
	String startDate = localDate.minusDays(1).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
	
    request.setAttribute("startDate", startDate);
    request.setAttribute("endDate", endDate);
    
    return "lims/dataSearch/daily/pointIndexBatchno";
  }
  /**
   * 分析项目装置
   * @param request
   * @return
   */
  @RequestMapping("/pointAnalyte")
  public String pointAnalyte(HttpServletRequest request) {
	  	/*LocalDate localDate = LocalDate.now();
	    String endDate = localDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		String startDate = localDate.minusDays(1).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		
	    request.setAttribute("startDate", startDate);
	    request.setAttribute("endDate", endDate);
	    */
	    return "lims/dataSearch/daily/pointIndexAnalyte";
  }
  @RequestMapping({"/pointOOS"})
  public String pointOOS(HttpServletRequest request)
  {
    request.setAttribute("title", request.getParameter("title"));
    
    LocalDate localDate = LocalDate.now();
    String endDate = localDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
   	String startDate = localDate.minusDays(1).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
   	
    request.setAttribute("startDate", startDate);
    request.setAttribute("endDate", endDate);
    
    return "lims/dataSearch/daily/pointIndexOOS";
  }
  @RequestMapping({"/pointRaw"})
  public String pointRaw(HttpServletRequest request)
  {
    request.setAttribute("title", request.getParameter("title"));
    
    LocalDate localDate = LocalDate.now();
    String endDate = localDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
   	String startDate = localDate.minusDays(1).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
   	
    request.setAttribute("startDate", startDate);
    request.setAttribute("endDate", endDate);
    
    return "lims/dataSearch/daily/pointIndexRaw";
  }
  
  @RequestMapping({"/pointTemp"})
  public String pointTemp(HttpServletRequest request)
  {
    request.setAttribute("title", request.getParameter("title"));
    
    LocalDate localDate = LocalDate.now();
    String endDate = localDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
   	String startDate = localDate.minusDays(1).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
   	
    request.setAttribute("startDate", startDate);
    request.setAttribute("endDate", endDate);
    
    return "lims/dataSearch/daily/pointIndexTemp";
  }
  
  @RequestMapping({"/pointSampling"})
  public String pointSampling(HttpServletRequest request)
  {
    request.setAttribute("title", request.getParameter("title"));
    
    LocalDate localDate = LocalDate.now();
    String endDate = localDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
   	String startDate = localDate.minusDays(1).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
   	
    request.setAttribute("startDate", startDate);
    request.setAttribute("endDate", endDate);
    
    return "lims/dataSearch/daily/pointIndexSampling";
  }
  /**
   * 分析项目
   * @param request
   * @return
   */
  @RequestMapping("/samplelistAnalyte")
  public String samplelistAnalyte(HttpServletRequest request) {
	  String areaName = request.getParameter("areaname");
	  request.setAttribute("areaName", areaName);
	  String plant = request.getParameter("plant");
	  request.setAttribute("plant", plant);
	  String matCode = request.getParameter("id");
	  request.setAttribute("matcode", matCode);
	  String sql = "select distinct testcode,analyte,sp_synonym from v_mate_test "
	  		+ "where prodgroup=? and matcode=?";
	  List<Map<String, Object>> list = dao.queryListMap(sql, areaName,matCode);
	  
	  //JSONArray rd = this.dao.queryJSONArray(sql,  areaName, matCode );
	  request.setAttribute("analytes", list);
	  return "lims/dataSearch/daily/pointOrdListAnalyte";
  }
  /**
   * 详细表内容
   * @param request
   * @return
   */
  @RequestMapping("/samplelistAnalyteDetail")
  public String samplelistAnalyteDetail(HttpServletRequest request) {
	  String areaName = request.getParameter("areaName");
	  request.setAttribute("areaName", areaName);
	  String plant = request.getParameter("plant");
	  request.setAttribute("plant", plant);
	  String matCode = request.getParameter("matcode");
	  request.setAttribute("matcode", matCode);
	  String analytes = request.getParameter("analytes");
	  //analytes = URLDecoder.decode(analytes);
	  String testcodes = request.getParameter("testcodes");
	  String startTime = request.getParameter("startTime");
	  String endTime = request.getParameter("endTime");
	  String[] aString = analytes.split(",");
	  String analyteWhr = "";
	  for(String s:aString) {
		  analyteWhr +="'" + s.substring(0, s.indexOf("-")) + "'" + ","; 
	  }
	  analyteWhr = analyteWhr.substring(0,analyteWhr.length()-1);
	  analyteWhr = " and t1.analyte in("+analyteWhr+") ";
	  String[] tStrings = testcodes.split(",");
	  String testcodeWhr = "";
	  for(String t:tStrings) {
		  testcodeWhr +="'" + t + "'" + ",";
	  }
	  testcodeWhr = testcodeWhr.substring(0,testcodeWhr.length()-1);
	  testcodeWhr = " and t1.testcode in("+testcodeWhr+") ";
	  analyteWhr = analyteWhr+testcodeWhr;
	  String sql = "select distinct t1.sinonym,t1.units,t1.sptestsorter,t1.sorter from plantdaily t1,orders t2 "
				+ " where t1.ordno=t2.ordno "
				+ " and t2.area_name=? and t2.plant=? and t2.matcode=?  "
				+ "and to_char(t2.REALSAMPDATE,'yyyy-MM-dd')>=? and to_char(t2.REALSAMPDATE,'yyyy-MM-dd')<=? "
				+ analyteWhr
				+ " order by sptestsorter,sorter";
	//获取表头
	List<Map<String,Object>> titleList = dao.queryListMap(sql, areaName,plant,matCode,startTime,endTime);
	List<String> titles = new ArrayList<String>();
	for(Map<String,Object> map:titleList){
		String key = map.get("sinonym").toString();
		if(map.get("units")!=null){
			key = key+"("+map.get("units").toString()+")";
		}
		if(!titles.contains(key)){
			titles.add(key);
		}
		
	}
	request.setAttribute("titles", titles);
	
	String sql2 = "select t4.batchname,t2.ordno,t2.Description pointdesc,"
			+ " to_char(t2.REALSAMPDATE,'MM-dd hh24:mi') sampdate,to_char(t2.REALSAMPDATE,'yyyy-MM-dd hh24:mi') sortsampdate,t1.final finalnum,t1.sinonym,t1.units,"
			+ " t1.s,t5.analtype,t1.testcode,t1.analyte,t2.status,t2.grade,"
			+ " t4.type,t4.tasktype,t4.suppcode,nvl(m.matname,t2.matcode) matname"
			+ " from plantdaily t1,orders t2 left join materials m on t2.matcode = m.matcode,folders t3,batches t4,results t5 "
			+ " where t1.ordno=t2.ordno and t2.folderno=t3.folderno and t3.batchid=t4.batchid "
			+ " and t1.ordno=t5.ordno and t1.testcode=t5.testcode and t1.analyte=t5.analyte "
			+ " and t2.area_name=? and t2.plant = ? and t2.matcode=?  "
			+ "and to_char(t2.REALSAMPDATE,'yyyy-MM-dd')>=? and to_char(t2.REALSAMPDATE,'yyyy-MM-dd')<=? "
			+ analyteWhr
			+ " order by sortsampdate desc";
	//获取样品数据
	List<Map<String,Object>> ordList = dao.queryListMap(sql2, areaName,plant,matCode,startTime,endTime);
	Map<String,Orders> ordMap = new HashMap<String,Orders>();
	List<Orders> data = new ArrayList<Orders>();
	for(Map<String,Object> map:ordList){
		
		String ordNo = map.get("ordno").toString();
		if(!ordMap.containsKey(ordNo)){
			Orders ords  = new Orders();
			int as = ords.getAnalytes().size();
			int ts = titles.size();
			while(as<ts){
				ords.getAnalytes().add(null);
				as++;
			}
			ords.setOrdNo(ordNo);
			ords.setSampDate(map.get("sampdate").toString());
			ords.setMatName(map.get("matname").toString());
			ords.setPointdesc(map.get("pointdesc").toString());
			ords.setBatchName(map.get("batchname")==null?"":map.get("batchname").toString());
			String grade = map.get("grade")==null?"":map.get("grade").toString();
			ords.setGrade(grade);
			String status = map.get("status")==null?"":map.get("status").toString();
			ords.setStatus(status);
			
			ords.setTaskType(map.get("tasktype").toString());
			ords.setType(map.get("type").toString());
			ords.setSuppCode(map.get("suppcode")==null?"":map.get("suppcode").toString());
			data.add(ords);
			ordMap.put(ordNo, ords);
		}
		
		Orders ordst = ordMap.get(ordNo);
		
		Object units = map.get("units");
		String key = map.get("sinonym").toString();
		if(units!=null){
			key = key + "("+units+")";
		}
		
		int index = titles.indexOf(key);
		ordst.getAnalytes().set(index, map);
	}
	request.setAttribute("data", data);
	return "lims/dataSearch/daily/sampleAccountAnalyte";
  }
  @RequestMapping({"/samplelistOOS"})
  public String samplelistOOS(HttpServletRequest request)
  {
    String areaName = request.getParameter("areaName");
    request.setAttribute("areaName", areaName);
    String plant = request.getParameter("plant");
    request.setAttribute("plant", plant);
    String pointId = request.getParameter("pointId");
    request.setAttribute("pointId", pointId);
    String startDate = request.getParameter("startDate");
    request.setAttribute("startDate", startDate);
    String endDate = request.getParameter("endDate");
    request.setAttribute("endDate", endDate);
    String status = request.getParameter("status");
    request.setAttribute("status", status);
    List<String> ordList = this.service.ordNoBySampdate(areaName, plant, pointId, startDate, endDate, status);
    
    String pageKey = SessionFactory.setAttribute(ordList);
    request.setAttribute("pageKey", pageKey);
    
    return "lims/dataSearch/daily/pointOrdListOOS";
  }

  @RequestMapping({"/samplelistRaw"})
  public String samplelistRaw(HttpServletRequest request)
  {
    String carno = request.getParameter("carno");
    request.setAttribute("carno", carno);
    String contract = request.getParameter("contract");
    request.setAttribute("contract", contract);
    String type = request.getParameter("type");
    request.setAttribute("type", type);
    String startDate = request.getParameter("startDate");
    request.setAttribute("startDate", startDate);
    String endDate = request.getParameter("endDate");
    request.setAttribute("endDate", endDate);
    
    String batchno = request.getParameter("batchno");
    request.setAttribute("batchno", batchno);
   // List<String> ordList = this.service.ordNoBySampdateRaw(carno, contract, type, startDate, endDate, batchno);
    
    /*String pageKey = SessionFactory.setAttribute(ordList);
    request.setAttribute("pageKey", pageKey);
    */
    String sql = null;
	String whr = " and 1 =1 ";
	if(carno !=null && carno.length() !=0)
	{
		whr +=" and (o.description like '%"+carno+"%' or o.sample_point_id like '%"+carno+"%')";
	}
	
	if(contract !=null && contract.length() !=0) 
	{
		whr +=" and b.businessno like '%"+contract+"%'";
	}
	if(batchno !=null && batchno.length() !=0)
	{
		whr +=" and b.batchno ="+batchno;
	}
	if("来源收发油".equals(type))
	{
		sql = "select o.ordno, " + 
				"       nvl(m.matname, o.matcode) matname, " + 
				"       b.businessno, " + 
				"       nvl(o.description, nvl(ss.description, o.sample_point_id)) sample_point_id, " + 
				"       to_char(o.sampdate, 'yyyy/mm/dd hh24:mi:ss') sampdate, " + 
				"       to_char(VO.EventDate, 'yyyy/mm/dd hh24:mi') eventDate, " + 
				"     b.batchno batchno, " + 
				"       o.status, " + 
				"       f.dept " + 
				"  from batches b, folders f, orders o " + 
				"  left join materials m " + 
				"    on o.matcode = m.matcode " + 
				"  LEFT JOIN VIEW_ORDRELEASETIME VO " + 
				"    ON VO.ORDNO = o.ORDNO, sample_points ss " + 
				" where b.batchid = f.batchid " + 
				"   and f.folderno = o.folderno " + 
				"   and o.sample_point_id = ss.sample_point_id " + 
				"   and o.apprsts = 'Released' and b.tasktype ='来源收发油' " + 
				"   and to_char(o.sampdate, 'yyyy-MM-dd') >=  ?" + 
				"  and to_char(o.sampdate, 'yyyy-MM-dd') <= ? " + 
				 whr +
				"  order by o.sampdate desc";
	}
	else
	{
		if(carno.equals("") || carno==null)
		{
			whr +=" and o.sample_point_id = 'SHXCTYL'";
		}
		
		sql = "select o.ordno, " + 
				"    nvl(m.matname, o.matcode) matname, " + 
				"    b.businessno, " + 
				"    nvl(o.description, nvl(ss.description, o.sample_point_id)) sample_point_id, " + 
				"    to_char(o.sampdate, 'yyyy/mm/dd hh24:mi:ss') sampdate, " + 
				"    to_char(VO.EventDate, 'yyyy/mm/dd hh24:mi') eventDate, " + 
				"    b.batchno batchno, " + 
				"    o.status, " + 
				"    f.dept " + 
				"  from batches b, folders f, orders o " + 
				"  left join materials m " + 
				"    on o.matcode = m.matcode " + 
				"  LEFT JOIN VIEW_ORDRELEASETIME VO " + 
				"    ON VO.ORDNO = o.ORDNO, sample_points ss " + 
				"  where b.batchid = f.batchid " + 
				"  and f.folderno = o.folderno " + 
				"  and o.sample_point_id = ss.sample_point_id " + 
				"  and o.apprsts = 'Released' " + 
				"  and to_char(o.sampdate, 'yyyy-MM-dd') >=  ? " + 
				"  and to_char(o.sampdate, 'yyyy-MM-dd') <= ? " + 
				 whr +
				"  order by o.sampdate desc";
		
	}
	List<Map<String, Object>> list = dao.queryListMap(sql, startDate,endDate);
	request.setAttribute("list", list);
    return "lims/dataSearch/daily/pointOrdListRaw";
  }
  @RequestMapping({"/samplelistBatchno"})
  public String samplelistBatchno(HttpServletRequest request)
  {
    String areaName = request.getParameter("areaName");
    request.setAttribute("areaName", areaName);
    String plant = request.getParameter("plant");
    request.setAttribute("plant", plant);
    String pointId = request.getParameter("pointId");
    request.setAttribute("pointId", pointId);
    String startDate = request.getParameter("startDate");
    request.setAttribute("startDate", startDate);
    String endDate = request.getParameter("endDate");
    request.setAttribute("endDate", endDate);
    String status = request.getParameter("status");
    request.setAttribute("status", status);
    String batchno = request.getParameter("batchno");
    request.setAttribute("batchno", batchno);
    List<String> ordList = this.service.ordNoBySampdateBatchno(areaName, plant, pointId, startDate, endDate, status, batchno);
    
    String pageKey = SessionFactory.setAttribute(ordList);
    request.setAttribute("pageKey", pageKey);
    
    return "lims/dataSearch/daily/pointOrdListBatchno";
  }
  
  
  @RequestMapping({"/samplelistTemp"})
  public String samplelistTemp(HttpServletRequest request)
  {
    String areaName = request.getParameter("areaName");
    request.setAttribute("areaName", areaName);
    String plant = request.getParameter("plant");
    request.setAttribute("plant", plant);
    String pointId = request.getParameter("pointId");
    request.setAttribute("pointId", pointId);
    String startDate = request.getParameter("startDate");
    request.setAttribute("startDate", startDate);
    String endDate = request.getParameter("endDate");
    request.setAttribute("endDate", endDate);
    String status = request.getParameter("status");
    request.setAttribute("status", status);
    List<String> ordList = this.service.ordNoBySampdateTemp(areaName, plant, pointId, startDate, endDate, status);
    
    String pageKey = SessionFactory.setAttribute(ordList);
    request.setAttribute("pageKey", pageKey);
    
    return "lims/dataSearch/daily/pointOrdListTemp";
  }
  
  @RequestMapping({"/samplelistSampling"})
  public String samplelistSampling(HttpServletRequest request)
  {
    String areaName = request.getParameter("areaName");
    request.setAttribute("areaName", areaName);
    String plant = request.getParameter("plant");
    request.setAttribute("plant", plant);
    String pointId = request.getParameter("pointId");
    request.setAttribute("pointId", pointId);
    String startDate = request.getParameter("startDate");
    request.setAttribute("startDate", startDate);
    String endDate = request.getParameter("endDate");
    request.setAttribute("endDate", endDate);
    String status = request.getParameter("status");
    request.setAttribute("status", status);
    List<String> ordList = this.service.ordNoBySampdateSampling(areaName, plant, pointId, startDate, endDate, status);
    
    String pageKey = SessionFactory.setAttribute(ordList);
    request.setAttribute("pageKey", pageKey);
    
    return "lims/dataSearch/daily/pointOrdListSampling";
  }
	/**
	 * 装置日报--按样品
	 * @param request
	 * @return
	 */
	@RequestMapping("/sample")
	public String sample(HttpServletRequest request){
		request.setAttribute("title", request.getParameter("title"));
		//获取当前日期
		LocalDate localDate = LocalDate.now();
		String endDate = localDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		String startDate = localDate.minusDays(1).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		
		request.setAttribute("startDate",startDate);
		request.setAttribute("endDate",endDate);
		
		return "lims/dataSearch/daily/sampleIndex";
	}
	
	/**
	 * 获取车间装置对应的检测样品信息
	 * @param request
	 * @return
	 */
	@RequestMapping("/samplelist")
	public String samplelist(HttpServletRequest request){
		String areaName = request.getParameter("areaName");
		request.setAttribute("areaName", areaName);
		String plant = request.getParameter("plant");
		request.setAttribute("plant", plant);
		String pointId = request.getParameter("pointId");
		request.setAttribute("pointId", pointId);
		String startDate = request.getParameter("startDate");
		request.setAttribute("startDate", startDate);
		String endDate = request.getParameter("endDate");
		request.setAttribute("endDate", endDate);
		String status = request.getParameter("status");
		request.setAttribute("status", status);
		List<String> ordList = service.ordNoBySampdate(areaName,plant,pointId,startDate,endDate,status);
		
		String pageKey = SessionFactory.setAttribute(ordList);
		request.setAttribute("pageKey", pageKey);
		
		return "lims/dataSearch/daily/pointOrdList";
	}
	
	
	/**
	 * 获取车间的样品台帐信息
	 * @param request
	 * @return
	 */
	@RequestMapping("/sampleAccount")
	public String sampleAccount(HttpServletRequest request,ParamsVo params){
		request.setAttribute("matName", request.getParameter("matName"));
		
		List<String> titles = service.ordTitles(params);
		request.setAttribute("titles", titles);
		List<Orders> ordList = service.ordDetails(params, titles);
		request.setAttribute("data", ordList);
		request.setAttribute("params", params);
		return "lims/dataSearch/daily/sampleAccount";
	}
	
	/**
	 * 获取单个样品的检测结果
	 * @return
	 */
	@RequestMapping("ordDetail")
	public String ordDetail(HttpServletRequest request){
		String optHide = request.getParameter("optHide");
		if("true".equals(optHide)){
			request.setAttribute("optHide", true);
		}
		
		String ordNo = request.getParameter("ordNo");
		try {
			
			String sql = "select o.ordno,nvl(m.matname,o.matcode) matname,o.area_name,o.plant,o.description pointdesc,to_char(o.realsampdate,'yyyy-MM-dd hh24:mi:ss') sampdate,o.status,o.sampdesc,b.batchname,o.grade," + 
					" b.tasktype " + 
					" from orders o left join materials m on o.matcode = m.matcode,folders f,batches b where b.batchid=f.batchid and f.folderno=o.folderno and ordno=?";
			Map<String, Object> ordMap = dao.queryMap(sql, ordNo);
			request.setAttribute("ord", ordMap);
			
			String sql1 = "select sum(t2.price) price from ordtask t1,tests t2 where t1.testcode=t2.testcode and t1.ordno=?";
			Float price = dao.queryValue(sql1, Float.class, ordNo);
			request.setAttribute("price", price);
			
			String sql2 = "SELECT t3.url,t1.testcode,t1.analyte,t1.sinonym,t1.analtype,t2.final final_num,"
					+ "t2.units,t2.s,t1.lowa,t1.lowb,t1.higha,t1.highb,t1.highc,t1.lowc,t1.innerlowa,t1.innerhigha "
					+ "	FROM results t1 left join plantdaily t2 "
					+ " on t1.ordno=t2.ordno and t1.testcode=t2.testcode and t1.sinonym=t2.sinonym "
					+ " left join GRAPHFILE_CTI t3 on t1.ordno=t3.ordno and t1.testcode=t3.testcode "
					+ " where  t1.ordno =? and t1.printflag='Y' order by t1.sptestsorter,t1.sorter";
			List<Map<String, Object>> list = dao.queryListMap(sql2, ordNo);
			
			for (Map<String, Object> map : list) {
				//查询质量指标
				String[] limits = LimsTools.limitChar(map.get("lowa"), map.get("higha"), map.get("lowb"),
						map.get("highb"),map.get("lowc"),map.get("highc"),map.get("innerlowa"),map.get("innerhigha"), map.get("charlimits"));
				map.put("limitA", limits[0]);
				map.put("limitB", limits[1]);
				map.put("limitC", limits[2]);
				map.put("limitD", limits[3]);
				
				//查询强制完成原因
				
				if("/".equals(map.get("finalNum"))){
					String testCode = map.get("testcode").toString();
					String sql3="select thecomment from floweventlog where stepcode='强制完成' and ordno=? and testcode=?";
					List<String> comment = dao.queryListValue(sql3, String.class, ordNo,testCode);
					if(comment.size()>0){
						map.put("isQzwc",comment.get(0));
					}
				}
				
			}
			request.setAttribute("analytes", list);
		} catch (Exception e) {
			// TODO: handle exception
			Date date = new Date();
			
			logger.error(date.toString()+"样品编号："+ordNo+"发生查询错误信息",e);
		}
		return "lims/dataSearch/daily/ordDetail";
	}
	/**
	 * 获取单个样品的检测结果
	 * @return
	 */
	@RequestMapping("ordDetailOOS")
	public String ordDetailOOS(HttpServletRequest request){
		String optHide = request.getParameter("optHide");
		if("true".equals(optHide)){
			request.setAttribute("optHide", true);
		}
		
		String ordNo = request.getParameter("ordNo");
		try {
			
			String sql = "select o.ordno,nvl(m.matname,o.matcode) matname,o.area_name,o.plant,o.description pointdesc,to_char(o.realsampdate,'yyyy-MM-dd hh24:mi:ss') sampdate,o.status,o.sampdesc,b.batchname,o.grade," + 
					" b.tasktype " + 
					" from orders o left join materials m on o.matcode = m.matcode,folders f,batches b where b.batchid=f.batchid and f.folderno=o.folderno and ordno=?";
			Map<String, Object> ordMap = dao.queryMap(sql, ordNo);
			request.setAttribute("ord", ordMap);
			
			String sql1 = "select sum(t2.price) price from ordtask t1,tests t2 where t1.testcode=t2.testcode and t1.ordno=?";
			Float price = dao.queryValue(sql1, Float.class, ordNo);
			request.setAttribute("price", price);
			
			String sql2 = "SELECT t3.url,t1.testcode,t1.analyte,t1.sinonym,t1.analtype,t2.final final_num,t2.units,"
					+ "t2.s,t1.lowa,t1.lowb,t1.higha,t1.highb,t1.highc,t1.lowc,t1.innerlowa,t1.innerhigha "
					+ "	FROM results t1 left join plantdaily t2 "
					+ " on t1.ordno=t2.ordno and t1.testcode=t2.testcode and t1.sinonym=t2.sinonym "
					+ " left join GRAPHFILE_CTI t3 on t1.ordno=t3.ordno and t1.testcode=t3.testcode "
					+ " where  t1.ordno =? and t1.printflag='Y' order by t1.sptestsorter,t1.sorter";
			List<Map<String, Object>> list = dao.queryListMap(sql2, ordNo);
			
			for (Map<String, Object> map : list) {
				//查询质量指标
				String[] limits = LimsTools.limitChar(map.get("lowa"), map.get("higha"), map.get("lowb"),
						map.get("highb"),map.get("lowc"),map.get("highc"),map.get("innerlowa"),map.get("innerhigha"), map.get("charlimits"));
				map.put("limitA", limits[0]);
				map.put("limitB", limits[1]);
				map.put("limitC", limits[2]);
				map.put("limitD", limits[3]);
				//查询强制完成原因
				/*
				if("/".equals(map.get("finalNum"))){
					String testCode = map.get("testcode").toString();
					String sql3="select thecomment from floweventlog where stepcode='强制完成' and ordno=? and testcode=?";
					List<String> comment = dao.queryListValue(sql3, String.class, ordNo,testCode);
					if(comment.size()>0){
						map.put("isQzwc",comment.get(0));
					}
				}
				*/
			}
			request.setAttribute("analytes", list);
		} catch (Exception e) {
			// TODO: handle exception
			Date date = new Date();
			
			logger.error(date.toString()+"样品编号："+ordNo+"发生查询错误信息",e);
		}
		return "lims/dataSearch/daily/ordDetailOOS";
	}
	/**
	 * 异常检查
	 * @param request
	 * @param response
	 * @throws EncryptedDocumentException
	 * @throws InvalidFormatException
	 * @throws IOException
	 */
	@RequestMapping("/checkOOS")
	public String checkOOS(HttpServletRequest request) {
		String ordNo = request.getParameter("ordNo");
		request.setAttribute("ordno", ordNo);
		String instrSql = "insert into PLANT_CHECKDATA( " + 
				"       DEPT, " + 
				"       TASKTYPE, " + 
				"       BATCHNO, " + 
				"       FOLDERNO, " + 
				"       ORDNO, " + 
				"       MATCODE, " + 
				"       QCTYPE, " + 
				"       SAMPDATE, " + 
				"       SAMPDESC, " + 
				"       SAMPLE_POINT_ID, " + 
				"       DESCRIPTION, " + 
				"       TESTCODE, " + 
				"       TESTNO, " + 
				"       SPTESTSORTER, " + 
				"       METHOD, " + 
				"       ANALYTE, " + 
				"       FINAL, " + 
				"       NUMRES, " + 
				"       SORTER, " + 
				"       SP_CODE, " + 
				"       SPECNO, " + 
				"       UNITS, " + 
				"       SINONYM, " + 
				"       S) " + 
				" select  " + 
				"       distinct  " + 
				"		DEPT, " + 
				"       TASKTYPE, " + 
				"       BATCHNO, " + 
				"       FOLDERNO, " + 
				"       ORDNO, " + 
				"       MATCODE, " + 
				"       QCTYPE, " + 
				"       SAMPDATE, " + 
				"       SAMPDESC, " + 
				"       SAMPLE_POINT_ID, " + 
				"       DESCRIPTION, " + 
				"       TESTCODE, " + 
				"       TESTNO, " + 
				"       SPTESTSORTER, " + 
				"       METHOD, " + 
				"       ANALYTE, " + 
				"       FINAL, " + 
				"       NUMRES, " + 
				"       SORTER, " + 
				"       SP_CODE, " + 
				"       SPECNO, " + 
				"       UNITS, " + 
				"       SINONYM, " + 
				"       S " + 
				"  from PLANTDAILY p " + 
				"  where P.S = 'OOS-A' " + 
				"  and p.ordno = ? " + 
				"  and not exists(select origrec from PLANT_CHECKDATA pc where pc.ordno = p.ordno and pc.testcode = p.testcode and pc.analyte = p.analyte   )";
		dao.excuteUpdate(instrSql, ordNo);
		String sql = "SELECT  P.ORDNO ORDNO, " + 
				"				P.BATCHNO, " + 
				"				M.MATNAME MATNAME, " + 
				"				to_char(P.SAMPDATE,'yyyy/mm/dd hh24:mi') SAMPDATE, " + 
				"				NVL(P.DESCRIPTION, P.SAMPLE_POINT_ID) SAMPLE_POINT_ID, " + 
				"				P.SINONYM||','||p.units||CHR(13)||''||CHR(13)||'' SINONYM, " + 
				"				P.FINAL result, " + 
				"				p.CHECK_USR, " + 
				"				p.CHECKDATE, " + 
				"				p.remark, " + 
				"				p.UPDATEMETHOD, " + 
				"				p.RESULT_CHECK, " + 
				"				case when P.EFFECT_PLANT='F' then '否' when P.EFFECT_PLANT='T' then '是' else '' end as EFFECT_PLANT, " + 
				"				P.TESTCODE, " + 
				"				P.ANALYTE,P.origrec " + 
				"			FROM PLANT_CHECKDATA P " + 
				"			LEFT JOIN MATERIALS M ON P.MATCODE = M.MATCODE " + 
				"			Where p.ordno=? " + 
				"			ORDER BY SAMPDATE, P.SPTESTSORTER, P.SORTER";
		List<Map<String, Object>>  list = dao.queryListMap(sql, ordNo);
		request.setAttribute("list", list);
		return "lims/dataSearch/daily/checkData";
	}
	/**
	 * 添加异常检测form
	 * @param request
	 * @param response
	 * @throws EncryptedDocumentException
	 * @throws InvalidFormatException
	 * @throws IOException
	 */
	@RequestMapping("/addCheck")
	public String addCheck(HttpServletRequest request) {
		
	
		String origrec = request.getParameter("origrec");
		
		//JBSH1811170337017-5124-95%
		request.setAttribute("origrec", origrec);
		String sql = "SELECT  P.ORDNO ORDNO, " + 
				"				P.BATCHNO, " + 
				"				M.MATNAME MATNAME, " + 
				"				to_char(P.SAMPDATE,'yyyy/mm/dd hh24:mi') SAMPDATE, " + 
				"				NVL(P.DESCRIPTION, P.SAMPLE_POINT_ID) SAMPLE_POINT_ID, " + 
				"				P.SINONYM||','||p.units||CHR(13)||''||CHR(13)||'' SINONYM, " + 
				"				P.FINAL result, " + 
				"				p.CHECK_USR, " + 
				"				p.CHECKDATE, " + 
				"				p.remark, " + 
				"				p.UPDATEMETHOD, " + 
				"				p.RESULT_CHECK, " + 
				"				case when P.EFFECT_PLANT='F' then '否' when P.EFFECT_PLANT='T' then '是' else '' end as EFFECT_PLANT, " + 
				"				P.TESTCODE, " + 
				"				P.ANALYTE,P.origrec " + 
				"			FROM PLANT_CHECKDATA P " + 
				"			LEFT JOIN MATERIALS M ON P.MATCODE = M.MATCODE " + 
				"			Where p.ordno=(select ordno from PLANT_CHECKDATA where origrec=?) " + 
				"			ORDER BY SAMPDATE, P.SPTESTSORTER, P.SORTER";
		List<Map<String, Object>>  list = dao.queryListMap(sql, origrec);
		request.setAttribute("list", list);
		return "lims/dataSearch/daily/addCheck";
	}
	/**
	 * 获取更新数据
	 */
	@RequestMapping("/checkOOSData")
	public String checkOOSData(HttpServletRequest request) {
		String origrec = request.getParameter("origrec");
		String sql = "SELECT  P.ORDNO ORDNO, " + 
				"				P.BATCHNO, " + 
				"				M.MATNAME MATNAME, " + 
				"				to_char(P.SAMPDATE,'yyyy/mm/dd hh24:mi') SAMPDATE, " + 
				"				NVL(P.DESCRIPTION, P.SAMPLE_POINT_ID) SAMPLE_POINT_ID, " + 
				"				P.SINONYM||','||p.units||CHR(13)||''||CHR(13)||'' SINONYM, " + 
				"				P.FINAL result, " + 
				"				p.CHECK_USR, " + 
				"				p.CHECKDATE, " + 
				"				p.remark, " + 
				"				p.UPDATEMETHOD, " + 
				"				p.RESULT_CHECK, " + 
				"				case when P.EFFECT_PLANT='F' then '否' when P.EFFECT_PLANT='T' then '是' else '' end as EFFECT_PLANT, " + 
				"				P.TESTCODE, " + 
				"				P.ANALYTE,P.origrec " + 
				"			FROM PLANT_CHECKDATA P " + 
				"			LEFT JOIN MATERIALS M ON P.MATCODE = M.MATCODE " + 
				"			Where p.ordno=(select ordno from PLANT_CHECKDATA where origrec=?) " + 
				"			ORDER BY SAMPDATE, P.SPTESTSORTER, P.SORTER";
		List<Map<String, Object>>  list = dao.queryListMap(sql, origrec);
		request.setAttribute("list", list);
		return "lims/dataSearch/daily/checkData";
	}
	/**
	 * 添加检查操作
	 * @param request
	 * @param response
	 * @throws EncryptedDocumentException
	 * @throws InvalidFormatException
	 * @throws IOException
	 */
	@RequestMapping(value= {"/addCheckIn"},produces={"application/json;charset=UTF-8"})
	@ResponseBody
	public String addCheckIn(HttpServletRequest request,CheckData checkData) {
		String origrec = request.getParameter("origrec");
		String sql ="update PLANT_CHECKDATA set  CHECK_USR=?, remark =?,CHECKDATE=?,"
				+ "UPDATEMETHOD=?,RESULT_CHECK=?,EFFECT_PLANT=?"
				+ " where origrec=?";
		dao.excuteUpdate(sql, checkData.getUser(),checkData.getRemark(),checkData.getCheckdate(),checkData.getMethod(),checkData.getCheck(),checkData.getEffect(),origrec);
		return MsgUtils.success(origrec).toString();
	}
	/**
	 * 异常分析台账
	 * @param request
	 * @param response
	 * @throws EncryptedDocumentException
	 * @throws InvalidFormatException
	 * @throws IOException
	 */
	@RequestMapping("/pointCheck")
	public String pointCheck(HttpServletRequest request) {
		LocalDate localDate = LocalDate.now();
	    String endDate = localDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
	   	String startDate = localDate.minusDays(1).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
	   	
	    request.setAttribute("startDate", startDate);
	    request.setAttribute("endDate", endDate);
	    String sql="SELECT distinct area_name id, area_name title, area_name area, 'item' type " + 
		  		"      FROM PLANT_CHECKDATA P " + 
		  		"      LEFT JOIN SAMPLE_POINTS S " + 
		  		"        ON P.SAMPLE_POINT_ID = S.SAMPLE_POINT_ID " + 
		  		"      LEFT JOIN sample_points_user PM " + 
		  		"          ON PM.Sample_Point_Id = S.Sample_Point_Id " + 
		  		"      where PM.USRNAM =? " + 
		  		"      and to_char(p.sampdate,'yyyy-MM-dd') >=? " + 
		  		"      and to_char(p.sampdate,'yyyy-MM-dd') <=? " + 
		  		"      order by nlssort(S.area_name, 'NLS_SORT=SCHINESE_PINYIN_M')";
	    JSONArray areaList = this.dao.queryJSONArray(sql,SessionFactory.getUsrName(),startDate,endDate);
	    
	    request.setAttribute("areas", areaList);
	    return "lims/dataSearch/daily/pointCheck";
	
	}
	/**
	 * 异常分析台账数据
	 * @param request
	 * @param response
	 * @throws EncryptedDocumentException
	 * @throws InvalidFormatException
	 * @throws IOException
	 */
	@RequestMapping("/checklist")
	public String checklist(HttpServletRequest request) {
		String area = request.getParameter("area");
	    request.setAttribute("area", area);
	    String startDate = request.getParameter("startDate");
	    String endDate = request.getParameter("endDate");
	    String sql = "select P.DEPT, " + 
	    		"       P.ORDNO, " + 
	    		"       P.MATCODE, " + 
	    		"       P.SAMPDATE, " + 
	    		"       P.SAMPDESC, " + 
	    		"       P.SAMPLE_POINT_ID, " + 
	    		"       P.DESCRIPTION, " + 
	    		"       P.TESTCODE, " + 
	    		"       P.TESTNO, " + 
	    		"       P.SPTESTSORTER, " + 
	    		"       P.METHOD, " + 
	    		"       P.ANALYTE, " + 
	    		"       P.FINAL result, " + 
	    		"       P.NUMRES, " + 
	    		"       P.SORTER, " + 
	    		"       P.SP_CODE, " + 
	    		"       P.SPECNO, " + 
	    		"       P.UNITS, " + 
	    		"       P.SINONYM, " + 
	    		"       P.S, " + 
	    		"       P.REMARK, " + 
	    		"       P.CHECK_USR, " + 
	    		"       P.CHECKDATE, " + 
	    		"       P.TASKTYPE, " + 
	    		"       P.BATCHNO, " + 
	    		"       P.FOLDERNO, " + 
	    		"       P.QCTYPE, " + 
	    		"       P.UPDATEMETHOD, " + 
	    		"       P.RESULT_CHECK, " + 
	    		"       case " + 
	    		"         when P.EFFECT_PLANT = 'F' then " + 
	    		"          '否' " + 
	    		"         when P.EFFECT_PLANT = 'T' then " + 
	    		"          '是' " + 
	    		"         else " + 
	    		"          P.EFFECT_PLANT " + 
	    		"       end as EFFECT_PLANT, " + 
	    		"       S.PLANT, " + 
	    		"       m.matname, " + 
	    		"       Case " + 
	    		"         When spec.charlimits Is Not Null Or spec.charlimits <> '' Then " + 
	    		"          spec.charlimits " + 
	    		"         When (spec.lowa Is Not Null Or spec.lowa <> '') And " + 
	    		"              (spec.higha Is Not Null Or spec.higha <> '') Then " + 
	    		"          spec.lowa || '—' || spec.higha " + 
	    		"         When (spec.lowa Is Not Null Or spec.lowa <> '') And " + 
	    		"              (spec.higha Is Null Or spec.higha = '') Then " + 
	    		"          spec.lowa " + 
	    		"         When (spec.lowa Is Null Or spec.lowa = '') And " + 
	    		"              (spec.higha Is Not Null Or spec.higha <> '') Then " + 
	    		"          spec.higha " + 
	    		"       End As Spec_gb " + 
	    		"  from PLANT_CHECKDATA P " + 
	    		"  LEFT JOIN SAMPLE_POINTS S " + 
	    		"    ON P.SAMPLE_POINT_ID = S.SAMPLE_POINT_ID " + 
	    		"  left join materials m " + 
	    		"    on P.matcode = m.matcode " + 
	    		"  left join spec_analytes spec " + 
	    		"    on P.SPECNO = spec.specno " + 
	    		"   and P.testcode = spec.testcode " + 
	    		"   and P.analyte = spec.analyte " + 
	    		" where to_char(p.sampdate, 'yyyy-MM-dd') >= ? " + 
	    		"   and to_char(p.sampdate, 'yyyy-MM-dd') <= ? " + 
	    		"   and S.AREA_NAME = ? " + 
	    		" order by P.SAMPDATE, P.sptestsorter";
	    List<Map<String, Object>> list = dao.queryListMap(sql,startDate,endDate,area);
	    request.setAttribute("list", list);
		return "lims/dataSearch/daily/checkDataList";
	}
	@RequestMapping("expOrdDetail")
	public void expOrdDetail(HttpServletRequest request,HttpServletResponse response) throws EncryptedDocumentException, InvalidFormatException, IOException{
		String ordNo = request.getParameter("ordNo");
		
		//获取模板路径
		URL url = this.getClass().getProtectionDomain().getCodeSource().getLocation();   
		String templateFilePath = url.toString();   
		int index = templateFilePath.indexOf("WEB-INF");  
		templateFilePath = templateFilePath.substring(0, index)+"template.xlsx";
		if(templateFilePath.startsWith("file")){//file:/D:/..   
			templateFilePath = templateFilePath.substring(6);   
        }       
		InputStream inp = new FileInputStream(templateFilePath);
		Workbook wb = WorkbookFactory.create(inp);
		Sheet sheet = wb.getSheetAt(0);
		//获取样品信息        
		String sql1 = "select o.matcode,nvl(m.matname,o.matcode) matname,b.batchname,b.suppcode,"
				+ "(case b.type when 'Process' then '中控样' when 'RAW' then '原辅料' when 'FP' then '成品' else '其它样品' end) type,"
				+ "to_char(o.sampdate,'yyyy/MM/dd hh24:mi') sampdate,b.tasktype,o.sample_point_id,o.area_name,o.plant,o.description pointdesc "
				+ "from orders o left join materials m on o.matcode = m.matcode,folders f,batches b where ordno=? and o.folderno=f.folderno and f.batchid=b.batchid";
		Map<String,Object> ords = dao.queryMap(sql1, ordNo);
		for(String key:ords.keySet()){
			Object value = ords.get(key);
			switch(key){
				case "matcode":
					ExcelTemplate.setCell(sheet, 1, 3, value);
					break;
				case "matname":
					ExcelTemplate.setCell(sheet, 1, 9, value);
					break;
				case "type":
					ExcelTemplate.setCell(sheet, 1, 15, value);
					break;
				case "sampdate":
					ExcelTemplate.setCell(sheet, 2, 3, value);
					break;
				case "tasktype":
					ExcelTemplate.setCell(sheet, 2, 9, value);
					break;
				case "samplePointId":
					ExcelTemplate.setCell(sheet, 2, 15, value);
					break;
				case "areaName":
					ExcelTemplate.setCell(sheet, 3, 3, value);
					break;
				case "plant":
					ExcelTemplate.setCell(sheet, 3, 9, value);
					break;
				case "pointdesc":
					ExcelTemplate.setCell(sheet, 3, 15, value);
					break;
				case "batchname":
					ExcelTemplate.setCell(sheet, 4, 3, value);
					break;
				case "suppcode":
					ExcelTemplate.setCell(sheet, 4, 9, value);
					break;
				default:
					break;
			}
		}
		
		//处理循环数据
		String sql2 = "select r.testcode,r.testno,r.analyte,r.sinonym,r.analtype,ot.servgrp,ot.method,r.final,r.picture,"
				+ "r.s,r.units,r.lowa,r.lowb,r.higha,r.highb,r.printflag,r.firstuser from ordtask ot,results r "
				+ "where ot.ordno=? and ot.ordno=r.ordno "
				+ "and ot.testcode=r.testcode and (r.printflag='Y') order by r.sptestsorter,r.sorter";
		List<Map<String,Object>> datas = dao.queryListMap(sql2, ordNo);
		ExcelTemplate.setListCell(sheet, datas);
		
		//将数据转换为excel
		String filename = new String(ordNo.getBytes(), "ISO-8859-1"); 
		response.setHeader("Content-disposition", "attachment; filename=" + filename + ".xlsx");// 组装附件名称和格式  
		ServletOutputStream out = response.getOutputStream();
		wb.write(out);
		wb.close();
		out.flush();
		out.close();
	}
	
	/**
	 * 获取单个样品的检测结果
	 * @return
	 */
	@RequestMapping("qst")
	public String qst(HttpServletRequest request){
		String pointId = request.getParameter("pointId");
		String[] analytes = request.getParameterValues("analyte");
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		request.setAttribute("pointId", pointId);
		request.setAttribute("analytes", analytes);
		Calendar cl = Calendar.getInstance();
		if(endDate==null||endDate.equals("")){
			request.setAttribute("endDate", DateUtils.DateFormat(cl.getTime(), "yyyy-MM-dd"));
		}else{
			request.setAttribute("endDate", endDate);
		}
		
		if(startDate==null||startDate.equals("")){
			cl.add(Calendar.DAY_OF_MONTH, -30);
			request.setAttribute("startDate",DateUtils.DateFormat(cl.getTime(), "yyyy-MM-dd") );
		}else{
			request.setAttribute("startDate", startDate);
		}
		
		
		return "lims/dataSearch/daily/qst";
	}
	
	/**
	 * 获取单个样品的检测结果
	 * @return
	 */
	@RequestMapping(value="qstdata",produces = "application/json;charset=UTF-8",method=RequestMethod.POST)
	@ResponseBody
	public String qstdata(HttpServletRequest request){
		String pointId = request.getParameter("pointId");
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		String[] analytes = request.getParameterValues("analyte");
		if(analytes == null){
			return new JSONObject().toString();
		}
		//获取30个样品
		String sql = "SELECT ORDNO,to_char(SAMPDATE,'MM-dd hh24:mi') sampdate FROM ORDERS O "
				+ " WHERE O.SAMPLE_POINT_ID=? AND o.ordgroup=o.ordno "
				+ "and to_char(o.SAMPDATE,'yyyy-MM-dd')>=? "
				+ "and to_char(o.SAMPDATE,'yyyy-MM-dd')<= ? and rownum <=999 ORDER BY SAMPDATE DESC";
		List<Map<String,Object>> ordersMap = dao.queryListMap(sql,pointId,startDate,endDate);
		
		List<String> orders = new ArrayList<String>();
		List<String> sampDate = new ArrayList<String>();
		
		for(Map<String,Object> map:ordersMap){
			orders.add(map.get("ordno").toString());
			sampDate.add(map.get("sampdate").toString());
		}
		if(orders.isEmpty()) {
			orders.add("-1");
		}
		Collections.reverse(orders);
		Collections.reverse(sampDate);
		String savg="";
		String smin="";
		String smax="";
		String sdev="";
		String higha="";
		String lowa="";
		//单个项目
		if(analytes.length==1) {
			
			//获取每个分析项的值
			String sql2 ="select ct.ordno,ct.sinonym,ct.units,ct.final,spec.higha,spec.lowa "
					+ " from plantdaily CT,ANALYTES T,spec_analytes spec "
					+ "WHERE CT.specno = spec.specno "
					+ "and CT.testcode = spec.testcode "
					+ " and CT.analyte=spec.analyte "
					+ "and CT.TESTCODE=T.TESTCODE "
					+ "AND CT.ANALYTE=T.ANALYTE "
					+ "AND T.ANALTYPE='N'"
					+ " and ct.ordno in (?) and ct.sinonym in (?) ";
			List<Map<String,Object>> sinonyms = dao.queryListMap(sql2, orders,analytes);
			String computeSql = "select round(avg(CT.Final),2) savg,round(min(CT.Final),2) smin,round(max(CT.Final),2) smax,round(STDDEV(CT.Final),2) sdev,spec.higha,spec.lowa " + 
					"       from plantdaily CT,ANALYTES T,spec_analytes spec  " + 
					"        WHERE CT.specno = spec.specno  " + 
					"      and CT.testcode = spec.testcode " + 
					"       and CT.analyte=spec.analyte " + 
					"       and CT.TESTCODE=T.TESTCODE " + 
					"       AND CT.ANALYTE=T.ANALYTE  " + 
					"      AND T.ANALTYPE='N' " + 
					"       and ct.ordno in (?) " + 
					"       and ct.sinonym in (?) " + 
					"       and  regexp_like(CT.Final,'^-?([1-9]\\d*\\.\\d*|0\\.\\d*[1-9]\\d*|0?\\.0+|0)$') " + 
					"       group by ct.sinonym,spec.higha,spec.lowa";
			List<Map<String,Object>> computes =dao.queryListMap(computeSql, orders,analytes);
			
			for(Map<String,Object> map:computes) {
				savg = map.get("savg").toString();
				smin = map.get("smin").toString();
				smax = map.get("smax").toString();
				sdev = map.get("sdev").toString();
				if(map.get("higha") !=null) {
					higha =map.get("higha").toString();
					request.setAttribute("higha", higha);
				}
				if(map.get("lowa") !=null) {
					lowa = map.get("lowa").toString();
					request.setAttribute("lowa", lowa);
				}
				
				request.setAttribute("savg", savg);
				request.setAttribute("smin", smin);
				request.setAttribute("smax", smax);
				request.setAttribute("sdev", sdev);
				
				
			}
			//处理值信息
			boolean[] dataFlag = new boolean[orders.size()];
			List<Map<String,Object>> data = new ArrayList<Map<String,Object>>();
			for(String a:analytes){
				Map<String,Object> sy = new HashMap<String, Object>();
				sy.put("name", a);
				sy.put("type", "line");
				String[] values = new String[orders.size()];
				
				for(Map<String,Object> sin:sinonyms){
					String sinonym = sin.get("sinonym").toString();
					
					if(a.equals(sinonym)){
						int index = orders.indexOf(sin.get("ordno").toString());
						String rs = sin.get("final").toString();
						
						Pattern pattern = Pattern.compile("^-?[0-9]+\\.?[0-9]*$");
						if(pattern.matcher(rs).matches()){
							values[index]=rs;
			
						}
					}
				}
				
				for(int i=0;i<values.length;i++){
					String value = values[i];
					if(value==null){
						dataFlag[i]=true;
					}
				}
				
				sy.put("data", values);
				
				data.add(sy);
			}
			//处理历史数据
			List<String> validataSampData = new ArrayList<String>();
			for(int i=0;i<sampDate.size();i++){
				if(!dataFlag[i]){
					validataSampData.add(sampDate.get(i));
				}
			}
			
			for(Map<String,Object> map:data){
				String[] values = (String[]) map.get("data");
				
				List<String> newValues = new ArrayList<String>();
				for(int i=0;i<values.length;i++){
					if(!dataFlag[i]){
						newValues.add(values[i]);
						
					}
				}
				
				
				map.put("data", newValues.toArray());
			}
			
			JSONObject json = new JSONObject();
			try {
				json.put("xAxis", validataSampData.toArray());
				json.put("orders", orders.toArray());
				json.put("legend", analytes);
				json.put("data", data);
				json.put("savg", savg);
				json.put("smin", smin);
				json.put("smax", smax);
				json.put("sdev", sdev);
				json.put("higha", higha);
				json.put("lowa", lowa);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return json.toString();
		}
		else {
			//获取每个分析项的值
			String sql2 ="select ct.ordno,ct.sinonym,ct.units,ct.final,spec.higha,spec.lowa "
					+ " from plantdaily CT,ANALYTES T,spec_analytes spec "
					+ "WHERE CT.specno = spec.specno "
					+ "and CT.testcode = spec.testcode "
					+ " and CT.analyte=spec.analyte "
					+ "and CT.TESTCODE=T.TESTCODE "
					+ "AND CT.ANALYTE=T.ANALYTE "
					+ "AND T.ANALTYPE='N'"
					+ " and ct.ordno in (?) and ct.sinonym in (?) ";
			List<Map<String,Object>> sinonyms = dao.queryListMap(sql2, new Object[]{orders,analytes});
			
			//处理值信息
			boolean[] dataFlag = new boolean[orders.size()];
			List<Map<String,Object>> data = new ArrayList<Map<String,Object>>();
			for(String a:analytes){
				Map<String,Object> sy = new HashMap<String, Object>();
				sy.put("name", a);
				sy.put("type", "line");
				String[] values = new String[orders.size()];
				
				for(Map<String,Object> sin:sinonyms){
					String sinonym = sin.get("sinonym").toString();
					if(a.equals(sinonym)){
						int index = orders.indexOf(sin.get("ordno").toString());
						String rs = sin.get("final").toString();
						Pattern pattern = Pattern.compile("^-?[0-9]+\\.?[0-9]*$");
						if(pattern.matcher(rs).matches()){
							values[index]=rs;
							
						}
					}
				}
				
				for(int i=0;i<values.length;i++){
					String value = values[i];
					if(value==null){
						dataFlag[i]=true;
					}
				}
				sy.put("data", values);
				data.add(sy);
			}
			//处理历史数据
			List<String> validataSampData = new ArrayList<String>();
			for(int i=0;i<sampDate.size();i++){
				if(!dataFlag[i]){
					validataSampData.add(sampDate.get(i));
				}
			}
			
			for(Map<String,Object> map:data){
				String[] values = (String[]) map.get("data");
				List<String> newValues = new ArrayList<String>();
				for(int i=0;i<values.length;i++){
					if(!dataFlag[i]){
						newValues.add(values[i]);
					}
				}
				map.put("data", newValues.toArray());
			}
			
			JSONObject json = new JSONObject();
			try {
				json.put("xAxis", validataSampData.toArray());
				json.put("orders", orders.toArray());
				json.put("legend", analytes);
				json.put("data", data);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return json.toString();
		}
	}
		
	
}
