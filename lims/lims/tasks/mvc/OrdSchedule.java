package lims.tasks.mvc;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.xml.namespace.QName;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.core.handler.SessionFactory;
import com.mysql.jdbc.StringUtils;
import com.starlims.webservices.ArrayOfAnyType;
import com.starlims.webservices.GenericServices;
import com.starlims.webservices.GenericServicesSoap;

import lims.areaPlants.vo.Areas;
import lims.areaPlants.vo.Plants;
import lims.areaPlants.vo.Points;
import lims.dataSearch.vo.ParamsVo;
import lims.tasks.vo.Ipsamplegroupdetails;
import lims.tasks.vo.Ipsamplegrpoups;
import lims.tasks.vo.ParamsOrdVo;
import lims.tasks.vo.SamplePrograms;
import pers.czf.commonUtils.DateUtils;
import pers.czf.commonUtils.MsgUtils;
import pers.czf.dbManager.Dao;

/**
 * 检测任务进度查询
 * @author Administrator
 *
 */
@Controller
@RequestMapping("lims/tasks/ordSchedule")
public class OrdSchedule {
	
	@Autowired
	private Dao dao;
	private static final QName SERVICE_NAME = new QName("http://www.starlims.com/webservices/", "GenericServices");
	  
	@RequestMapping
	public String index(HttpServletRequest request,ParamsOrdVo params){
		
		//b.type样品类型,任务类型,批状态,样品状态,样品代码，样品名称，车间、装置、采样点编号、采样点名称，任务下达（添加）时间，备注，创建人
		String sql = "select o.ordno,b.type,b.tasktype,o.APPRDISP dispsts,o.status status,o.matcode,nvl(m.matname,o.matcode) matname,"
				+ " o.area_name,o.plant,o.sample_point_id,nvl(o.description,o.sample_point_id) pointdesc,"
				+ " to_char(o.sampdate,'yyyy-MM-dd hh24:mi') sampdate,b.batchname,f.createdby "
				+ " from batches b,folders f,orders o left join materials m on o.matcode = m.matcode "
				+ " where b.batchid=f.batchid and f.folderno=o.folderno "
				+ " and f.createdby=? and to_char(o.logdate,'yyyy-MM-dd')>=? and to_char(o.logdate,'yyyy-MM-dd')<=? ";
		//处理查询条件
		if(params.getStartTime()==null||params.getStartTime().equals("")){
			Calendar c = Calendar.getInstance();
			c.add(Calendar.DAY_OF_MONTH, -7);
			params.setStartTime(DateUtils.DateFormat(c.getTime(), "yyyy-MM-dd"));
		}
		
		boolean notExistsEnd = false;
		if(params.getEndTime()==null||params.getEndTime().equals("")){
			Calendar c = Calendar.getInstance();
			params.setEndTime(DateUtils.DateFormat(c.getTime(), "yyyy-MM-dd"));
			notExistsEnd = true;
		}
		
		if("1".equals(params.getStatus())){
			sql += " and o.apprdisp = 'Released' ";
		}else if("0".equals(params.getStatus())){
			sql += " and ( o.apprdisp != 'Released' or o.apprdisp is null )";
		}
		
		sql += " order by o.logdate desc";
		List<Map<String,Object>> data = dao.queryListMap(sql, SessionFactory.getUsrName(),params.getStartTime(),params.getEndTime());
		request.setAttribute("data",data);
		if(notExistsEnd){
			params.setEndTime("");
		}
		request.setAttribute("params", params);
		return "lims/tasks/ordSchedule/index";
	}
	/**
	 * 检验任务列表
	 * @param request
	 * @return
	 */
	@RequestMapping("/orderLogin")
	public String orderLogin(HttpServletRequest request) {
		
		/*String sql = "select  " + 
				"       B.BATCHID, " + 
				"       TYPE, " + 
				"       G.PRODGROUP, " + 
				"       SAMPLEGROUPNAME, " + 
				"       B.BATCHNO, " + 
				"       ESTIMATEDVOL, " + 
				"       ESTIMATEDVOL_UNITS, " + 
				"       B.COMMENTS, " + 
				"       B.CREATEDBY, " + 
				"       TASKTYPE, " + 
				"       o.ordno, " + 
				"       nvl(m.matname,o.matcode) matname, " + 
				"       o.sampdate, " + 
				"       o.area_name, " + 
				"       o.plant, " + 
				"       o.description " + 
				"  from BATCHES B " + 
				"  left join IPSAMPLEGRPOUPS G " + 
				"    on B.SAMPLEGROUPCODE = G.SAMPLEGROUPCODE, " + 
				"    folders f,orders o left join materials m on m.matcode = o.matcode " + 
				" where B.Batchid = f.batchid " + 
				"   and f.folderno = o.folderno " + 
				"   and B.BATCHSTATUS = 'Planned' " + 
				"   and B.createdby = ? " + 
				"   and B.TASKTYPE <> '自动登录'";
		JSONObject json = new JSONObject();
		
		try {
			json.put("code", 0);
			json.put("msg", "");
			json.put("count", 100);
			json.put("data", dao.queryJSONArray(sql, SessionFactory.getUsrName()));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		List<Map<String, Object>> list = dao.queryListMap(sql, SessionFactory.getUsrName());
		request.setAttribute("list", list);*/
		return "lims/tasks/ordSchedule/orderLoginMain";
	}
	/**
	 * 样品登录界面
	 */
	@RequestMapping("/sampleLogin")
	public String sampleLogin(HttpServletRequest request) {
		
		return "lims/tasks/ordSchedule/sampleLogin";
	}
	/**
	 * 
	 */
	@RequestMapping("/sampleGroup")
	public String sampleGroup(HttpServletRequest request) {
		String type = request.getParameter("type");
		request.setAttribute("type", type);
		
		return "lims/tasks/ordSchedule/sampleGroup";
	}
	/**
	 * 其他检验
	 */
	@RequestMapping("/sampleGroupOTHER")
	public String sampleGroupOTHER(HttpServletRequest request) {
		
		String samplePointSql = "select distinct  area_name areaname" + 
				"        from SAMPLE_POINTS "
				+ "      where dept = ? and area_name is not null and plant is not null and SAMPLE_POINT_ID is not null";
		List<Map<String, Object>> areaList = dao.queryListMap(samplePointSql, SessionFactory.getDept());
		request.setAttribute("areaList", areaList);
		String testtypeSql = "Select distinct TESTCATCODE  " + 
				"      from TESTCATEGORY  " + 
				"      where exists(select ORIGREC  " + 
				"            from TESTCATEGORYSITES  " + 
				"            where TESTCATCODE=TESTCATEGORY.TESTCATCODE and  " + 
				"              DEPT = ? and  " + 
				"              (VIEWTESTS='Y' or EDITTESTS='Y')) " + 
				"        and istemp = 'Y' " + 
				"        order by TESTCATCODE";
		List<Map<String, Object>> testtypeList = dao.queryListMap(testtypeSql, SessionFactory.getDept());
		request.setAttribute("testtypeList", testtypeList);
		String deptSql = "select dept from departments  order by dept";
	    List<Map<String, Object>> deptList =dao.queryListMap(deptSql);
	    request.setAttribute("deptList", deptList);
	    String tasktypeSql = "SELECT TASKTYPE FROM TASKTYPES";
	    List<Map<String, Object>> tasktypeList =dao.queryListMap(tasktypeSql);
	    request.setAttribute("tasktypeList", tasktypeList);
		return "lims/tasks/ordSchedule/sampleTempletOther";
	}
	/**
	 * 获取静态数据
	 * @throws JSONException 
	 */
	@RequestMapping(value="/sampleGroupDetail",produces= {"application/json;charset=UTF-8"})
	@ResponseBody
	public String sampleGroupDetail(HttpServletRequest request) throws JSONException {
	    String type = request.getParameter("type");
	    String treeSql = "select distinct Ip.Prodgroup,Ip.Samplegroupcode,IP.Samplegroupname,s.sp_code,s.progname " + 
	    		"  From Ipsamplegrpoups Ip, Ipsamplegroupdetails Ips,Sample_Programs s "+ 
	    		" Where Ip.Samplegroupcode = Ips.Samplegroupcode " + 
	    		" and ips.sp_code = s.sp_code And Ip.Dept = ? " + 
	    		" And Ip.DISPLAYFLAG = 'Y' " + 
	    		" And Ips.ACTIVE <> 'N'  And Ip.Sampgrtype=? " + 
	    		" order by Ip.Prodgroup,Ip.Samplegroupcode,IP.Samplegroupname";
	    List<Map<String, Object>> list = dao.queryListMap(treeSql, SessionFactory.getDept(),type);
	    JSONArray trees = new JSONArray();
	    List<Ipsamplegrpoups> IpsamplegrpoupsList = new ArrayList<Ipsamplegrpoups>();
	    Ipsamplegrpoups ipsamplegrpoups = null;
	    Ipsamplegroupdetails ipsamplegroupdetails = null;
	    for(Map<String, Object> map:list) {
	    	String prodgroup  = map.get("prodgroup").toString();
	    	String samplegroupcode  = map.get("samplegroupcode").toString();
	    	String samplegroupname  = map.get("samplegroupname").toString();
	    	String spCode  = map.get("spCode").toString();
	    	String progname  = map.get("progname").toString();
	    	if(ipsamplegrpoups ==null || !ipsamplegrpoups.getProdgroup().equals(prodgroup)) {
	    		ipsamplegrpoups = new Ipsamplegrpoups();
	    		ipsamplegrpoups.setProdgroup(prodgroup);
	    		IpsamplegrpoupsList.add(ipsamplegrpoups);
	    		ipsamplegroupdetails =null;
	    	}
	    	if(ipsamplegroupdetails ==null || !ipsamplegroupdetails.getSamplegroupcode().equals(samplegroupcode)) {
	    		ipsamplegroupdetails = new Ipsamplegroupdetails();
	    		ipsamplegroupdetails.setSamplegroupcode(samplegroupcode);
	    		ipsamplegroupdetails.setSamplegroupname(samplegroupname);
	    		ipsamplegroupdetails.setProdgroup(prodgroup);
	    		ipsamplegrpoups.getList().add(ipsamplegroupdetails);
	    	}
	    	SamplePrograms samplePrograms = new SamplePrograms();
	    	samplePrograms.setProgname(progname);
	    	samplePrograms.setSpcode(spCode);
	    	samplePrograms.setSamplegroupcode(samplegroupcode);
	    	samplePrograms.setSamplegroupname(samplegroupname);
	    	samplePrograms.setProdgroup(prodgroup);
	    	ipsamplegroupdetails.getList().add(samplePrograms);
	    	
	    }
	 
	    try {
			for(Ipsamplegrpoups ip:IpsamplegrpoupsList) {
				JSONObject ipjson = new JSONObject();
				ipjson.put("id", ip.getProdgroup());
				ipjson.put("title", ip.getProdgroup());
				ipjson.put("type", "ip");
				JSONArray areachildren = new JSONArray();
				for(Ipsamplegroupdetails ips:ip.getList()) {
					JSONObject plantjson = new JSONObject();
					plantjson.put("id", ips.getSamplegroupcode());
					plantjson.put("title",ips.getSamplegroupname());
					plantjson.put("type", "ips");
					plantjson.put("prodgroup", ips.getProdgroup());
					JSONArray plantchildren = new JSONArray();
					for(SamplePrograms sp:ips.getList()) {
						JSONObject pointjson = new JSONObject();
						pointjson.put("id", sp.getSpcode());
						pointjson.put("title", sp.getProgname());
						pointjson.put("type", "sp");
						pointjson.put("prodgroup", sp.getProdgroup());
						pointjson.put("samplegroupcode", sp.getSamplegroupcode());
						plantchildren.put(pointjson);
					}
					plantjson.put("children", plantchildren);
					areachildren.put(plantjson);
				}
				ipjson.put("children", areachildren);
				trees.put(ipjson);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
		return trees.toString();
	}
	/**
	 * 添加临时其他加样操作
	 * dept : dept,
              source :source,
    	      sender :sender,
    	      sampledesc :sampledesc,
    	      sampdate :sampdate,
    	      area :area,
    	      plant :plant,
    	      point :point,
    	      pointDesc :pointDesc,
    	      batchno :batchno,
    	      remark :remark,
    	      tasktype :tasktype,
    	      testArr :testArr
	 */
	@RequestMapping(value="/addTPSample",produces= {"application/json;charset=UTF-8"})
	@ResponseBody
	public String addTPSample(HttpServletRequest request) {
		String dept = request.getParameter("dept");
		String source = request.getParameter("source");
		String sender = request.getParameter("sender");
		String sampledesc = request.getParameter("sampledesc");
		String sampdate = request.getParameter("sampdate");
		String area = request.getParameter("area");
		String plant = request.getParameter("plant");
		String point = request.getParameter("point");
		String pointDesc = request.getParameter("pointDesc");
		String batchno = request.getParameter("batchno");
		String remark = request.getParameter("remark");
		String tasktype = request.getParameter("tasktype");
		String testArr = request.getParameter("testArr");
		URL wsdlURL = GenericServices.wsdlUrl();
	    GenericServices ss = new GenericServices(wsdlURL, SERVICE_NAME);
	    GenericServicesSoap port = ss.getGenericServicesSoap();
	    ArrayOfAnyType array = new ArrayOfAnyType();
	    List<Object> list = array.getAnyType();
	    list.add(dept);
	    list.add(source);
	    list.add(sender);
	    list.add(sampledesc);
	    list.add(sampdate);
	    list.add(area);
	    list.add(plant);
	    list.add(point);
	    list.add(pointDesc);
	    list.add(batchno);
	    list.add(remark);
	    list.add(tasktype);
	    list.add(testArr);
	    list.add(SessionFactory.getUsrName());
	    try {
	    	port.runActionDirect("Utilities.AddTpSample", array, "SYSADM", "LIMS");
		    
		    return MsgUtils.success("success").toString();
		} catch (Exception e) {
			// TODO: handle exception
			return MsgUtils.fail(e.getMessage()).toString();
		}
	    
	}
	/**
	 * 添加原辅料操作
	 */
	@RequestMapping(value="/addRawSample",produces= {"application/json;charset=UTF-8"})
	@ResponseBody
	public String addRawSample(HttpServletRequest request) {
		String dept = request.getParameter("dept");
		String tasktype = request.getParameter("tasktype");
		String specno = request.getParameter("specno");
		String profile = request.getParameter("profile");
		String point = request.getParameter("point");
		String pointDesc = request.getParameter("pointDesc");
		String areaname = request.getParameter("areaname");
		String plant = request.getParameter("plant");
		String batchno = request.getParameter("batchno");
		String supple = request.getParameter("supple");
		String suppleno = request.getParameter("suppleno");
		String productdate = request.getParameter("productdate");
		String expirytdate = request.getParameter("expirytdate");
		String amount = request.getParameter("amount");
		String unit = request.getParameter("unit");
		String remark = request.getParameter("remark");
		String spcode = request.getParameter("spcode");
		String samplegroupcode = request.getParameter("samplegroupcode");
		String sampdate = request.getParameter("sampdate");
		URL wsdlURL = GenericServices.wsdlUrl();
	    GenericServices ss = new GenericServices(wsdlURL, SERVICE_NAME);
	    GenericServicesSoap port = ss.getGenericServicesSoap();
	    ArrayOfAnyType array = new ArrayOfAnyType();
	    List<Object> list = array.getAnyType();
	    list.add(samplegroupcode);
	    list.add(spcode);
	    list.add(remark);
	    list.add(expirytdate);
	    list.add(productdate);
	    list.add(suppleno);
	    list.add(supple);
	    list.add(areaname);
	    list.add(pointDesc);
	    list.add(profile);
	    list.add(specno);
	    list.add(tasktype);
	    list.add(dept);
	    list.add(unit);
	    list.add(amount);
	    list.add(batchno);
	    list.add(plant);
	    list.add(point);
	    list.add(SessionFactory.getUsrName());
	    list.add(sampdate);
	    try {
	    	port.runActionDirect("Utilities.AddRawSample", array, "SYSADM", "LIMS");
		    
		    return MsgUtils.success("success").toString();
		} catch (Exception e) {
			// TODO: handle exception
			return MsgUtils.fail(e.getMessage()).toString();
		}
	    
	}
	/**
	 * 中控样品添加操作
	 */
	@RequestMapping(value="/addScheduledSample",produces= {"application/json;charset=UTF-8"})
	@ResponseBody
	public String addScheduledSample(HttpServletRequest request) {
		String dept = request.getParameter("dept");
		String tasktype = request.getParameter("tasktype");
		String specno = request.getParameter("specno");
		String profile = request.getParameter("profile");
		String point = request.getParameter("point");
		String pointDesc = request.getParameter("pointDesc");
		String areaname = request.getParameter("areaname");
		String plant = request.getParameter("plant");
		String batchno = request.getParameter("batchno");
		String sampdate = request.getParameter("sampdate");
		String remark = request.getParameter("remark");
		String spcode = request.getParameter("spcode");
		String samplegroupcode = request.getParameter("samplegroupcode");
		URL wsdlURL = GenericServices.wsdlUrl();
	    GenericServices ss = new GenericServices(wsdlURL, SERVICE_NAME);
	    GenericServicesSoap port = ss.getGenericServicesSoap();
	    ArrayOfAnyType array = new ArrayOfAnyType();
	    List<Object> list = array.getAnyType();
	    list.add(remark);
	    list.add(sampdate);
	    list.add(areaname);
	    list.add(pointDesc);
	    list.add(profile);
	    list.add(specno);
	    list.add(tasktype);
	    list.add(dept);
	    list.add(batchno);
	    list.add(plant);
	    list.add(point);
	    list.add(SessionFactory.getUsrName());
	    list.add(samplegroupcode);
	    list.add(spcode);
	    try {
	    	port.runActionDirect("Utilities.AddScheduledSample", array, "SYSADM", "LIMS");
		    
		    return MsgUtils.success("success").toString();
		} catch (Exception e) {
			// TODO: handle exception
			return MsgUtils.fail(e.getMessage()).toString();
		}
	    
	}
	/**
	 * 添加产品操作
	 */
	@RequestMapping(value="/addProductSample",produces= {"application/json;charset=UTF-8"})
	@ResponseBody
	public String addProductSample(HttpServletRequest request) {
		String dept = request.getParameter("dept");
		String tasktype = request.getParameter("tasktype");
		String specno = request.getParameter("specno");
		String profile = request.getParameter("profile");
		String point = request.getParameter("point");
		String pointDesc = request.getParameter("pointDesc");
		String areaname = request.getParameter("areaname");
		String plant = request.getParameter("plant");
		String batchno = request.getParameter("batchno");
		String sampdate = request.getParameter("sampdate");
		String remark = request.getParameter("remark");
		String spcode = request.getParameter("spcode");
		String samplegroupcode = request.getParameter("samplegroupcode");
		String supple = request.getParameter("supple");
		String productdate = request.getParameter("productdate");
		String amount= request.getParameter("amount");
		String unit = request.getParameter("unit");
		URL wsdlURL = GenericServices.wsdlUrl();
	    GenericServices ss = new GenericServices(wsdlURL, SERVICE_NAME);
	    GenericServicesSoap port = ss.getGenericServicesSoap();
	    ArrayOfAnyType array = new ArrayOfAnyType();
	    List<Object> list = array.getAnyType();
	    list.add(remark);
	    list.add(sampdate);
	    list.add(areaname);
	    list.add(pointDesc);
	    list.add(profile);
	    list.add(specno);
	    list.add(tasktype);
	    list.add(dept);
	    list.add(batchno);
	    list.add(plant);
	    list.add(point);
	    list.add(SessionFactory.getUsrName());
	    list.add(samplegroupcode);
	    list.add(spcode);
	    list.add(productdate);
	    list.add(supple);
	    list.add(amount);
	    list.add(unit);
	    try {
	    	port.runActionDirect("Utilities.AddProductSamples", array, "SYSADM", "LIMS");
		    
		    return MsgUtils.success("success").toString();
		} catch (Exception e) {
			// TODO: handle exception
			return MsgUtils.fail(e.getMessage()).toString();
		}
	    
	}
	/**
	 * 删除样品
	 */
	@RequestMapping(value="/deleteSample",produces= {"application/json;charset=UTF-8"})
	@ResponseBody
	public String deleteSample(HttpServletRequest request) {
		String arr = request.getParameter("arr");
		URL wsdlURL = GenericServices.wsdlUrl();
	    GenericServices ss = new GenericServices(wsdlURL, SERVICE_NAME);
	    GenericServicesSoap port = ss.getGenericServicesSoap();
	    ArrayOfAnyType array = new ArrayOfAnyType();
	    List<Object> list = array.getAnyType();
	    list.add(arr);
	    try {
	    	port.runActionDirect("Utilities.deleteSamples", array, "SYSADM", "LIMS");
		    
		    return MsgUtils.success("success").toString();
		} catch (Exception e) {
			// TODO: handle exception
			return MsgUtils.fail(e.getMessage()).toString();
		}
	
	}
	/**
	 * 提交样品
	 */
	@RequestMapping(value="/submitSample",produces= {"application/json;charset=UTF-8"})
	@ResponseBody
	public String submitSample(HttpServletRequest request) {
		String arr = request.getParameter("arr");
		URL wsdlURL = GenericServices.wsdlUrl();
	    GenericServices ss = new GenericServices(wsdlURL, SERVICE_NAME);
	    GenericServicesSoap port = ss.getGenericServicesSoap();
	    ArrayOfAnyType array = new ArrayOfAnyType();
	    List<Object> list = array.getAnyType();
	    list.add(arr);
	    try {
	    	port.runActionDirect("Utilities.submitSample", array, "SYSADM", "LIMS");
		    
		    return MsgUtils.success("success").toString();
		} catch (Exception e) {
			// TODO: handle exception
			return MsgUtils.fail(e.getMessage()).toString();
		}
	
	}
	/**
	 * 获取测试计划方案 指标
	 */
	@RequestMapping("/getSampleProgramIfo")
	public String getSampleProgramIfo(HttpServletRequest request) {
		String spcode = request.getParameter("spcode");
		String samplegroupcode = request.getParameter("samplegroupcode");
		String type = request.getParameter("type");
		request.setAttribute("spcode", spcode);
		request.setAttribute("samplegroupcode", samplegroupcode);
		String dsProfile = "Select distinct sp.profile  " + 
				"              From sp_tests sp " + 
				"              Where sp.Sp_Code =?";
		List<Map<String, Object>> profileList = dao.queryListMap(dsProfile, spcode);
		request.setAttribute("profileList", profileList);
		String specnoSql = "select  distinct ss.specname,  " + 
				"                        ss.specno  " + 
				"            from sp_specs s,specifications sp,spec_sets ss " + 
				"            where s.specification_o = sp.origrec  " + 
				"            and sp.origrec = ss.specification_o " + 
				"            and sp_code =? ";
		List<Map<String, Object>> specnoList = dao.queryListMap(specnoSql, spcode);
		request.setAttribute("specnoList", specnoList);
		String pointSql = "Select distinct nvl(s.Sample_Point_Id,Ip.Sample_Point_Id) point, nvl(s.description,Ip.Sample_Point_Id) description,s.area_name areaname,s.plant " + 
				"                From Ipsamplegroupdetails Ip " + 
				"                left join Sample_Points s on Ip.Sample_Point_Id = s.Sample_Point_Id  " + 
				"                Where  Ip.Sp_Code = ? " + 
				"                  and samplegroupcode =? ";
		List<Map<String, Object>> pointList =dao.queryListMap(pointSql, spcode,samplegroupcode);
		request.setAttribute("pointList", pointList);
		String unitSql = "Select distinct  UNIT_CODE unit " + 
				"from UNITS_OF_MEASURE  " + 
				"order by UNIT_CODE";
		List<Map<String, Object>> unitList = dao.queryListMap(unitSql);
		request.setAttribute("unitList", unitList);
		String suppleSql = "select distinct suppcode ,suppnam  from SUPPLIERS where suppcode not in('N/A','IN HOUSE')";
		List<Map<String, Object>> suppleList = dao.queryListMap(suppleSql);
		request.setAttribute("suppleList", suppleList);
		String rasSql="Select RASCLIENTID,COMPNAME " + 
				"from RASCLIENTS  " + 
				"where RASCLIENTID <> '**NEW**'  and COMPNAME is not null " + 
				"order by  UPPER (RASCLIENTID)";
		List<Map<String, Object>> rasList = dao.queryListMap(rasSql);
		request.setAttribute("rasList", rasList);
		/*String samplePointSql = "select distinct  SAMPLE_POINT_ID point, DESCRIPTION || '(' || dept || ')' description " + 
				"        from SAMPLE_POINTS ";
		List<Map<String, Object>> samplePointList = dao.queryListMap(samplePointSql);
		request.setAttribute("samplePointList", samplePointList);*/
		String deptSql = "select dept from departments  order by dept";
	    List<Map<String, Object>> deptList =dao.queryListMap(deptSql);
	    request.setAttribute("deptList", deptList);
	    String tasktypeSql = "SELECT TASKTYPE FROM TASKTYPES";
	    List<Map<String, Object>> tasktypeList =dao.queryListMap(tasktypeSql);
	    request.setAttribute("tasktypeList", tasktypeList);
		if("RAWMAT".equals(type)) {
			return "lims/tasks/ordSchedule/sampleTemplet";
		}
		else if("SCHEDULED".equals(type)) {
			return "lims/tasks/ordSchedule/sampleTempletScheduled";
			
		}else if("PRODUCT".equals(type)) {
			return "lims/tasks/ordSchedule/sampleTempletProduct";
			
		}else{
			return "lims/tasks/ordSchedule/sampleTempletOther";
			
		}
		//return "lims/tasks/ordSchedule/sampleTemplet";
	}
	/**
	 * 获取测试分类下的测试
	 */
	@RequestMapping(value="/getTests",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String getTests(HttpServletRequest request) {
		String testType = request.getParameter("testType");
		String testSql = "select testcode value, testno name,'' selected,'' disabled" + 
				"          from TESTS " + 
				"          where TESTCATCODE = ? " + 
				"          order by TESTNO";
		
		JSONArray testsList = dao.queryJSONArray(testSql,testType);
		//request.setAttribute("samplePointList", samplePointList);
		return testsList.toString();
	}
	/**
	 * 获取采样点信息
	 * 
	 */
	@RequestMapping(value="/getSamplepoint",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String getSamplepoint(HttpServletRequest request) {
		String dept = request.getParameter("dept");
		 if(StringUtils.isNullOrEmpty(dept)) {
		    	dept = SessionFactory.getDept();
		}
		String area = request.getParameter("area");
		String plant = request.getParameter("plant");
		String samplePointSql = "select distinct  SAMPLE_POINT_ID point, DESCRIPTION  description " + 
				"        from SAMPLE_POINTS "
				+ "		 where dept = ?"
				+ "		and area_name = ?"
				+ "		and plant = ? and area_name is not null and plant is not null and SAMPLE_POINT_ID is not null";
		JSONArray samplePointList = dao.queryJSONArray(samplePointSql,dept,area,plant);
		//request.setAttribute("samplePointList", samplePointList);
		return samplePointList.toString();
	}
	/**
	 * 获取车间列表
	 */
	@RequestMapping(value="/getArea",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String getArea(HttpServletRequest request) {
		String dept = request.getParameter("dept");
	    if(StringUtils.isNullOrEmpty(dept)) {
	    	dept = SessionFactory.getDept();
	    }
	   
		String samplePointSql = "select distinct  area_name areaname" + 
				"        from SAMPLE_POINTS "
				+ "      where dept = ? and area_name is not null and plant is not null and SAMPLE_POINT_ID is not null";
		JSONArray areaList = dao.queryJSONArray(samplePointSql,dept);
		//request.setAttribute("areaList", areaList);
		return areaList.toString();
	}
	/**
	 * 获取deptlist
	 */
	@RequestMapping("/getDept")
	public String getDept(HttpServletRequest request) {
		String dept = request.getParameter("dept");
	    if(StringUtils.isNullOrEmpty(dept)) {
	    	dept = SessionFactory.getDept();
	    }
	    String deptSql = "select dept from departments  order by dept";
	    List<Map<String, Object>> deptList =dao.queryListMap(deptSql);
	    request.setAttribute("deptList", deptList);
		
		return "lims/tasks/ordSchedule/samplePointList";
	}
	/**
	 * 获取车间装置
	 */
	@RequestMapping(value="/getPlant",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String getPlant(HttpServletRequest request) {
		String dept = request.getParameter("dept");
		String area = request.getParameter("area");
	    if(StringUtils.isNullOrEmpty(dept)) {
	    	dept = SessionFactory.getDept();
	    }
		String samplePointSql = "select distinct  plant " + 
				"        from SAMPLE_POINTS "
				+ "      where dept = ?"
				+ "		 and area_name = ? and area_name is not null and plant is not null and SAMPLE_POINT_ID is not null";
		JSONArray plantList = dao.queryJSONArray(samplePointSql,dept,area);
		
		return plantList.toString();
	}
	/**
	 * 获取数据
	 * 
	 */
	@RequestMapping(value="/orderLoginData",produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String orderLoginData(HttpServletRequest request) {
		
		String sql = "select  " + 
				"       B.BATCHID, " + 
				"       case when b.type='FP' then '成品' when b.type='RAW' then '原辅料'  when b.type='IP' then '中控样' else '临时加样' end type, " + 
				"       G.PRODGROUP, " + 
				"       SAMPLEGROUPNAME, " + 
				"       B.BATCHNO, " + 
				"       ESTIMATEDVOL, " + 
				"       ESTIMATEDVOL_UNITS, " + 
				"       B.COMMENTS, " + 
				"       (select fullname from users where usrnam = B.CREATEDBY) CREATEDBY, " + 
				"       TASKTYPE, " + 
				"       o.ordno, " + 
				"       nvl(m.matname,o.matcode) matname, " + 
				"       to_char(o.sampdate,'yyyy-MM-dd HH:mi:ss') sampdate, " + 
				"       o.area_name, " + 
				"       o.plant, " + 
				"       o.description " + 
				"  from BATCHES B " + 
				"  left join IPSAMPLEGRPOUPS G " + 
				"    on B.SAMPLEGROUPCODE = G.SAMPLEGROUPCODE, " + 
				"    folders f,orders o left join materials m on m.matcode = o.matcode " + 
				" where B.Batchid = f.batchid " + 
				"   and f.folderno = o.folderno " + 
				"   and B.BATCHSTATUS = 'Planned' " + 
				"   and B.createdby = ? " + 
				"   and B.TASKTYPE <> '自动登录'";
		JSONObject json = new JSONObject();
		JSONArray  jArray = dao.queryJSONArray(sql, SessionFactory.getUsrName());
		try {
			json.put("code", 0);
			json.put("msg", "");
			json.put("count", jArray.length());
			json.put("data",jArray);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		/*List<Map<String, Object>> list = dao.queryListMap(sql, SessionFactory.getUsrName());
		request.setAttribute("list", list);*/
		return json.toString();
	}
	/**
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("detail")
	public String ordDetail(HttpServletRequest request){
		
		String ordNo = request.getParameter("ordNo");
		
		/**
		 * 1、查询样品信息
		 * b.type样品类型,任务类型,批状态,样品状态,样品代码，样品名称，车间、装置、采样点编号、采样点名称，任务下达（添加）时间，备注，创建人
		 */
		String sql = "select b.origrec,o.ordno,b.type,b.tasktype,b.dispsts,o.status ostatus,b.batchstatus bstatus,o.APPRDISP"
				+ " from batches b,folders f,orders o "
				+ " where b.batchid=f.batchid and f.folderno=o.folderno "
				+ " and o.ordno= ? ";

		Map<String,Object> data = dao.queryMap(sql, ordNo);
		List<Map<String,Object>> processInfo = new ArrayList<Map<String,Object>>();
		
		/**
		 * 2、查询加样审批过程
		 */
		/*if("加样".equals(data.get("tasktype"))){
			String sql1 = "select STEPNAME,ACTIONNAME,AUDITCOMMENT,U.fullname FULLNAME,to_char(auditdate,'yyyy-MM-dd')||' '||audittime auditdate "
					+ " from GENERAL_WORKFLOW_HISTORY T,USERS U "
					+ " where workflowcode='ExtraApprove' AND t.AUDITUSERNAME = U.USRNAM and sid=? order by T.origrec desc";
			List<Map<String, Object>> jyInfo = dao.queryListMap(sql1, data.get("origrec"));
			processInfo.addAll(jyInfo);
			
		}*/
		
		/**
		 * 3、查询检测过程
		 */
		String sql2 = "SELECT DISTINCT STEPCODE STEPNAME,'' ACTIONNAME,THECOMMENT AUDITCOMMENT,U.fullname,to_char(EVENTDATE,'yyyy-MM-dd hh24:mi:ss') auditdate"
				+ " from FLOWEVENTLOG T,USERS U"
				+ " where T.USRNAM = U.USRNAM AND ORDNO = ? and EVENTTYPE not in('Edit Tests','Batch Released') "
				+ " and t.STEPCODE in ('Disposition Sample','ReSampling','Batch Released','Release Test','Reject Report','Sampling','Create Report','Done Testing','Recv In CR','Reject Test','Pre Released','Recv In Lab','Cancel Sample','Pre Logged','Approve Report','Auto Disposition Sample','Cancel Sample Result','样品已发布','编辑编制人','合样结果','免检','添加受理单','编辑检测项目','合样报告','任务指派(项目负责人)','任务指派(具体实验员)','完成指派','改判指标') order by auditdate desc";
		
		List<Map<String,Object>> info = dao.queryListMap(sql2, ordNo);
		processInfo.addAll(info);
		
		//环节翻译及去重
		List<Map<String,Object>> rdata = new ArrayList<Map<String,Object>>();
		Set<String> keys = new HashSet<String>();
		for(Map<String,Object> map:processInfo){
			String setpName = (String) map.get("stepname");
			switch(setpName){
				case "Pre Logged":
					setpName = "预登录";
					map.put("stepname", setpName);
					break;
				case "Recv In CR":
					setpName = "中心接收";
					map.put("stepname", setpName);
					break;
				case "Recv In Lab":
					setpName = "实验室接收";
					map.put("stepname", setpName);
					break;
				case "Reject Test":
					setpName = "拒绝测试";
					map.put("stepname", setpName);
					break;
				case "Retest":
					setpName = "重测";
					map.put("stepname", setpName);
					break;
				case "Sampling":
					setpName = "采样";
					map.put("stepname", setpName);
					break;
				case "Done Testing":
					setpName = "完成测试";
					map.put("stepname", setpName);
					break;
				case "Cancel Sample":
					setpName = "终止样品";
					map.put("stepname", setpName);
					break;
				case "Release Test":
					setpName = "审核测试";
					map.put("stepname", setpName);
					break;
				case "ReleaseBySample":
					setpName = "样品审核";
					map.put("stepname", setpName);
					break;
				case "DoneApprove":
					setpName = "审核测试";
					map.put("stepname", setpName);
					break;
				case "Auto Disposition Sample":
					setpName = "自动发布样品";
					map.put("stepname", setpName);
					break;
				case "Disposition Sample":
					setpName = "样品审核";
					map.put("stepname", setpName);
					break;
				case "Batch Released":
					setpName = "发布批";
					map.put("stepname", setpName);
					break;
				default:
					break;
			}
			
			if(!keys.contains(setpName)){
				keys.add(setpName);
				rdata.add(map);
			}			
		}

		rdata.sort(new Comparator<Map<String,Object>>(){
			@Override
			public int compare(Map<String, Object> o1, Map<String, Object> o2) {
				// TODO Auto-generated method stub
				String date1 = (String) o1.get("auditdate");
				String date2 = (String) o2.get("auditdate");
	            return date2.compareTo(date1);
			}
		});
		
		/**
		 * 4、判断当前环节
		 * data
		 */
		/*Object dispsts = data.get("dispsts");
		Object bstatus = data.get("bstatus");
		Object ostatus = data.get("ostatus");
		String curStep = null;
		if("Approving".equals(dispsts)){
			switch(bstatus.toString()){
				case "Drft":
					curStep =  "调度审批";
					break;
				case "Approval1":
					curStep =  "部室审批";
					break;
				case "Approval2":
					curStep =  "副总审批";
					break;
			}
		}
		
		if("Started".equals(bstatus)){
			switch(ostatus.toString()){
				case "Started":
					curStep =  "待采样";
					break;
				case "Prelogged":
					curStep =  "待接收";
					break;
				case "OOS":
					curStep =  "超标待发布";
					break;
				case "Done":
					curStep =  "待发布";
					break;
				default:
					curStep =  "检测中……";
					break;
			}
		}*/
		request.setAttribute("processInfo", rdata);
		//request.setAttribute("curStep", curStep);
		return "lims/tasks/ordSchedule/ordProcDetail";
	}
	
	
	/**
	 * 装置日报--材料类型检测进度查询
	 * @return
	 */
	@RequestMapping("/matTypes/{type}")
	public String matTypes(HttpServletRequest request,@PathVariable("type") String type,ParamsOrdVo params){
		String sql = "select DISTINCT t2.MATCODE id,t2.matname title from SAMPLE_PROGRAMS t1,MATERIALS t2 "
				+ " where t1.SAMPLE_TYPE=? and t1.matcode=t2.matcode order by id";
		if("RAW".equals(type)){
			request.setAttribute("title", "原辅料");
		}else if("FP".equals(type)){
			request.setAttribute("title", "成品");
		}
		
		request.setAttribute("matData", dao.queryListMap(sql, type));
		
		//b.type样品类型,任务类型,批状态,样品状态,样品代码，样品名称，车间、装置、采样点编号、采样点名称，任务下达（添加）时间，备注，创建人
		String sql2 = "select o.ordno,b.type,b.tasktype,b.dispsts,b.status,o.matcode,o.matname,"
						+ " o.area_name,o.plant,o.sample_point_id,o.pointdesc,b.SUPPCODE,"
						+ " to_char(o.logdate,'yyyy-MM-dd hh24:mi') logdate,b.batchname,f.createdby "
						+ " from batches b,folders f,orders o "
						+ " where b.batchid=f.batchid and f.folderno=o.folderno and b.type=? and o.dispordgoup=o.ordno "
						+ " and to_char(o.logdate,'yyyy-MM-dd')>=? and to_char(o.logdate,'yyyy-MM-dd')<=? ";
		
		//处理查询条件
		List<Object> queryParam = new ArrayList<Object>();
		queryParam.add(type);
		if(params.getStartTime()==null||params.getStartTime().equals("")){
			Calendar c = Calendar.getInstance();
			c.add(Calendar.DAY_OF_MONTH, -7);
			params.setStartTime(DateUtils.DateFormat(c.getTime(), "yyyy-MM-dd"));
		}
		queryParam.add(params.getStartTime());
		
		boolean notExistsEnd = false;
		if(params.getEndTime()==null||params.getEndTime().equals("")){
			Calendar c = Calendar.getInstance();
			params.setEndTime(DateUtils.DateFormat(c.getTime(), "yyyy-MM-dd"));
			notExistsEnd = true;
		}
		queryParam.add(params.getEndTime());
		
		if("1".equals(params.getStatus())){
			sql2 += " and o.apprdisp = 'Released' ";
		}else if("0".equals(params.getStatus())){
			sql2 += " and ( o.apprdisp != 'Released' or o.apprdisp is null )";
		}
		
		if(params.getMatCode()!=null&&!params.getMatCode().equals("ALL")){
			sql2 += " and o.matcode = ? ";
			queryParam.add(params.getMatCode());
		}
		if(params.getSuppcode()!=null&&!params.getSuppcode().equals("")){
			sql2 += " and b.SUPPCODE like ? ";
			queryParam.add("%"+params.getSuppcode()+"%");
		}
		
		if(params.getDescription()!=null&&!params.getDescription().equals("")){
			sql2 += " and o.pointdesc like ? ";
			queryParam.add("%"+params.getDescription()+"%");
		}
		
		sql2 += " order by logdate desc";
		
		List<Map<String,Object>> data = dao.queryListMap(sql2,queryParam.toArray());
		request.setAttribute("data",data);
		
		if(notExistsEnd){
			params.setEndTime("");
		}
		request.setAttribute("params", params);
		
		if("RAW".equals(type)){
			return "lims/tasks/ordSchedule/rawTypes";
		}else{
			return "lims/tasks/ordSchedule/matTypes";
		}
	}
	
	/**
	 * 
	 * @return
	 */
	@RequestMapping("/points")
	public String points(HttpServletRequest request){
		//获取当前日期
		LocalDate localDate = LocalDate.now();
		String endDate = localDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		String startDate = localDate.minusDays(1).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		
		request.setAttribute("startDate",startDate);
		request.setAttribute("endDate",endDate);
		return "lims/tasks/ordSchedule/points";
	}
	
	@RequestMapping("/samplelist")
	public String samplelist(HttpServletRequest request, ParamsVo params) {
		boolean noEndTime = false;
		//处理查询条件
		if(params.getStartTime()==null||params.getStartTime().equals("")){
			Calendar c = Calendar.getInstance();
			c.set(Calendar.HOUR_OF_DAY, 0);
			c.set(Calendar.MINUTE,0);
			c.set(Calendar.SECOND, 0);
			params.setStartTime(DateUtils.DateFormat(c.getTime(), "yyyy-MM-dd"));
		}
		
		if(params.getEndTime()==null||params.getEndTime().equals("")){
			Calendar c = Calendar.getInstance();
			params.setEndTime(DateUtils.DateFormat(c.getTime(), "yyyy-MM-dd"));
			noEndTime = true;
		}
		
		String sql = null;
		List<Map<String,Object>> ordList = new ArrayList<Map<String,Object>>();
		String type = params.getType()==null?"":params.getType();
		if(StringUtils.isNullOrEmpty(params.getStatus()))
		{
			switch(type){
			case "area":
				sql = "select o.ordno,b.type,b.tasktype,o.APPRDISP dispsts,o.status status,o.matcode,nvl(m.matname,o.matcode) matname,"
						+ " o.area_name,o.plant,o.sample_point_id,nvl(o.description,o.sample_point_id) pointdesc,"
						+ " to_char(o.sampdate,'yyyy-MM-dd hh24:mi') sampdate,b.batchname,f.createdby "
						+ " from batches b,folders f,orders o left join materials m on o.matcode = m.matcode "
						+ " where b.batchid=f.batchid and f.folderno=o.folderno "
						+ " and to_char(o.sampdate,'yyyy-MM-dd')>=? and to_char(o.sampdate,'yyyy-MM-dd')<=? "
						+ " and o.area_name = ? "
						+ " order by sampdate desc";
				ordList = dao.queryListMap(sql,params.getStartTime(),params.getEndTime(),params.getAreaName());
				break;
			case "plant":
				sql = "select o.ordno,b.type,b.tasktype,o.APPRDISP dispsts,o.status status,o.matcode,nvl(m.matname,o.matcode) matname,"
						+ " o.area_name,o.plant,o.sample_point_id,nvl(o.description,o.sample_point_id) pointdesc,"
						+ " to_char(o.sampdate,'yyyy-MM-dd hh24:mi') sampdate,b.batchname,f.createdby "
						+ " from batches b,folders f,orders o left join materials m on o.matcode = m.matcode "
						+ " where b.batchid=f.batchid and f.folderno=o.folderno "
						+ " and to_char(o.sampdate,'yyyy-MM-dd')>=? and to_char(o.sampdate,'yyyy-MM-dd')<=? "
						+ " and o.area_name = ? and o.plant = ? order by sampdate desc";
				ordList = dao.queryListMap(sql,params.getStartTime(),params.getEndTime(),params.getAreaName(),params.getPlant());
				break;
			case "mat":
				sql = "select o.ordno,b.type,b.tasktype,o.APPRDISP dispsts,o.status status,o.matcode,nvl(m.matname,o.matcode) matname,"
						+ " o.area_name,o.plant,o.sample_point_id,nvl(o.description,o.sample_point_id) pointdesc,"
						+ " to_char(o.sampdate,'yyyy-MM-dd hh24:mi') sampdate,b.batchname,f.createdby "
						+ " from batches b,folders f,orders o left join materials m on o.matcode = m.matcode "
						+ " where b.batchid=f.batchid and f.folderno=o.folderno "
						+ " and to_char(o.sampdate,'yyyy-MM-dd')>=? and to_char(o.sampdate,'yyyy-MM-dd')<=? "
						+ " and o.area_name = ? and o.plant = ? and o.matcode = ? order by sampdate desc";
				ordList = dao.queryListMap(sql,params.getStartTime(),params.getEndTime(),params.getAreaName(),params.getPlant(),params.getKeyValue());
				break;
			default:
				break;
			}
		
		}
		else {
			String whr = " and 1=1 ";
			if(params.getStatus().equals("Active"))
			{
				whr +=" and o.status in ('Active','Logged')";
			}
			else if(params.getStatus().equals("OOS")) {
				whr +=" and o.status='OOS' and o.APPRDISP is null ";
			}else if(params.getStatus().equals("Done")) {
				whr +=" and o.status='Done' and o.APPRDISP is null ";
			}else if(params.getStatus().equals("Released")) {
				whr +=" and o.APPRDISP ='Released' ";
			}
			else {
				whr +=" and o.status ='"+params.getStatus()+"'";
			}
			switch(type){
			case "area":
				sql = "select o.ordno,b.type,b.tasktype,o.APPRDISP dispsts,o.status status,o.matcode,nvl(m.matname,o.matcode) matname,"
						+ " o.area_name,o.plant,o.sample_point_id,nvl(o.description,o.sample_point_id) pointdesc,"
						+ " to_char(o.sampdate,'yyyy-MM-dd hh24:mi') sampdate,b.batchname,f.createdby "
						+ " from batches b,folders f,orders o left join materials m on o.matcode = m.matcode "
						+ " where b.batchid=f.batchid and f.folderno=o.folderno "
						+ " and to_char(o.sampdate,'yyyy-MM-dd')>=? and to_char(o.sampdate,'yyyy-MM-dd')<=? "
						+ " and o.area_name = ? "
						+ whr 
						+ " order by sampdate desc";
				ordList = dao.queryListMap(sql,params.getStartTime(),params.getEndTime(),params.getAreaName());
				break;
			case "plant":
				sql = "select o.ordno,b.type,b.tasktype,o.APPRDISP dispsts,o.status status,o.matcode,nvl(m.matname,o.matcode) matname,"
						+ " o.area_name,o.plant,o.sample_point_id,nvl(o.description,o.sample_point_id) pointdesc,"
						+ " to_char(o.sampdate,'yyyy-MM-dd hh24:mi') sampdate,b.batchname,f.createdby "
						+ " from batches b,folders f,orders o left join materials m on o.matcode = m.matcode "
						+ " where b.batchid=f.batchid and f.folderno=o.folderno "
						+ " and to_char(o.sampdate,'yyyy-MM-dd')>=? and to_char(o.sampdate,'yyyy-MM-dd')<=? "
						+ " and o.area_name = ? and o.plant = ? "
						+ whr 
						+ "order by sampdate desc";
				ordList = dao.queryListMap(sql,params.getStartTime(),params.getEndTime(),params.getAreaName(),params.getPlant());
				break;
			case "mat":
				sql = "select o.ordno,b.type,b.tasktype,o.APPRDISP dispsts,o.status status,o.matcode,nvl(m.matname,o.matcode) matname,"
						+ " o.area_name,o.plant,o.sample_point_id,nvl(o.description,o.sample_point_id) pointdesc,"
						+ " to_char(o.sampdate,'yyyy-MM-dd hh24:mi') sampdate,b.batchname,f.createdby "
						+ " from batches b,folders f,orders o left join materials m on o.matcode = m.matcode "
						+ " where b.batchid=f.batchid and f.folderno=o.folderno "
						+ " and to_char(o.sampdate,'yyyy-MM-dd')>=? and to_char(o.sampdate,'yyyy-MM-dd')<=? "
						+ " and o.area_name = ? and o.plant = ? and o.matcode = ? "
						+ whr 
						+ " order by sampdate desc";
				ordList = dao.queryListMap(sql,params.getStartTime(),params.getEndTime(),params.getAreaName(),params.getPlant(),params.getKeyValue());
				break;
			default:
				break;
		}
		
		}
		
		request.setAttribute("data",ordList);

		if(noEndTime){
			params.setEndTime("");
		}
		request.setAttribute("params", params);

		return "lims/tasks/ordSchedule/planSample";
	}
}
