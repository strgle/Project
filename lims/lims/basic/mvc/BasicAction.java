package lims.basic.mvc;

import com.core.handler.SessionFactory;
import com.core.utils.KeyUtils;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import lims.areaPlants.tools.AreaPlantTools;
import lims.basic.vo.AnalyteVo;
import lims.basic.vo.Area;
import lims.basic.vo.MateVo;
import lims.basic.vo.Plant;
import lims.basic.vo.TestVo;
import lims.zltj.vo.ChjVo;
import lims.zltj.vo.ZzhiVo;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import pers.czf.commonUtils.DateUtils;
import pers.czf.commonUtils.ProjectUtils;
import pers.czf.dbManager.Dao;
@Controller
@RequestMapping({"lims/basic/zlhk"})
public class BasicAction {

	  @Autowired
	  private Dao dao;
	  
	  @RequestMapping
	  public String index(HttpServletRequest request)
	  {
	    //String sql = "select t1.area_name id,t1.area_name title,t1.area_name area,'item' type from  AREASITES t1,AREAS t2 where t1.dept=? and t1.area_name=t2.area_name order by t2.sorter";
	    String sql="select distinct area_name id, area_name title, area_name area, 'item' type " + 
	    		"from sample_points_user t1, sample_points t2 " + 
	    		"         where t1.usrnam = ? " + 
	    		"           AND t1.sample_point_id = t2.sample_point_id " + 
	    		"           and t2.area_name is not null " + 
	    		"         order by nlssort(t2.area_name, 'NLS_SORT=SCHINESE_PINYIN_M')";
	    JSONArray areaList = this.dao.queryJSONArray(sql,SessionFactory.getUsrName());
	    request.setAttribute("areas", areaList);
	    
	    return "/lims/basic/index";
	  }
	  @RequestMapping({"/zlmb"})
	  public String zlmb(HttpServletRequest request) {
		  String startDate = request.getParameter("startDate");
		    Calendar calendar = Calendar.getInstance();
		    if ((startDate == null) || (startDate.equals("")))
		    {
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
		    if ((endDate == null) || (endDate.equals("")))
		    {
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
		      endDate = DateUtils.DateFormat(calendar.getTime(), "yyyy-MM-dd");
		    }
		    request.setAttribute("endDate", endDate);
		    //String sql1 = "select t.area_name,t.plant,nvl(t.check_status,'Done') status,t1.basic_rate basicRate,b.tasktype  from orders t, folders f,batches b,AREASITES t2,plants_setting t1,(select distinct areaname,matecode from MATE_SETTING2) t5  where t.folderno=f.folderno and f.batchid=b.batchid and b.type<>'LP'  and t.area_name=t2.area_name and t1.area_name=t2.area_name and t.plant=t1.plant and t.area_name=t5.areaname and t.matcode=t5.matecode  and t1.is_check='1' and t2.dept =? and t.realsampdate>=to_date(?,'yyyy-MM-dd hh24:mi:ss') and t.realsampdate<=to_date(?,'yyyy-MM-dd hh24:mi:ss')  and t.status in ('Done','OOS')  and not exists(select 1 from NOCHECK_SAMPLE nc where nc.ordno=t.ordno and status=1)  order by t.area_name,t.plant,nvl(t.check_status,'Done')";
		  //获取需要统计的装置
			String sql1 = "select t.area_name,t.plant,nvl(t.check_status,'Done') status,t1.basic_rate basicRate,b.tasktype "
					+ " from orders t, folders f,batches b,AREASITES t2,plants_setting t1,(select distinct areaname,matecode from MATE_SETTING2) t5 "
					+ " where t.folderno=f.folderno and f.batchid=b.batchid and b.type<>'LP' "
					+ " and t.area_name=t2.area_name and t1.area_name=t2.area_name and t.plant=t1.plant"
					+ " and t.area_name=t5.areaname and t.matcode=t5.matecode "
					+ " and t1.is_check='1' and t2.dept = ? "
					+ " and t.realsampdate>=to_date(?,'yyyy-MM-dd hh24:mi:ss') and t.realsampdate<=to_date(?,'yyyy-MM-dd hh24:mi:ss') "
					+ " and t.status in ('Done','OOS') "
					+ " and not exists(select 1 from NOCHECK_SAMPLE nc where nc.ordno=t.ordno and status=1) "
					+ " order by t.area_name,t.plant,nvl(t.check_status,'Done')";
			
		    List<Map<String,Object>> plantList= dao.queryListMap(sql1, ProjectUtils.getDept(),startDate+" 00:00:00",endDate+" 23:59:59");
			List<ChjVo> cjList = new ArrayList<ChjVo>();
			ChjVo cj = null;
			ZzhiVo zz = null;
			for(Map<String,Object> map:plantList){
				String areaName = (String) map.get("areaName");
				String plant = (String) map.get("plant");
				String status = (String) map.get("status");
				
				if(cj==null||!areaName.equals(cj.getName())){
					cj = new ChjVo();
					cj.setName(areaName);
					cjList.add(cj);
				}
				
				if(zz==null||!areaName.equals(zz.getAreaName())||!plant.equals(zz.getName())){
					zz = new ZzhiVo(plant);
					zz.setAreaName(areaName);
					if(map.get("basicrate")!=null){
						float basicRate = ((BigDecimal)map.get("basicrate")).floatValue();
						zz.setZlmb(basicRate);
					}
					
					cj.addZzh(zz);
				}
				String taskType = (String) map.get("tasktype");
				zz.addNum(status, taskType);
			}
			List<ChjVo> sortList = new ArrayList<ChjVo>();
			
			//String sql ="select t1.area_name from AREASITES t1,AREAS t2 where t1.dept=? and t1.area_name=t2.area_name order by t2.sorter";
			//String sql="select distinct area_name  from(select distinct t2.area_name,p.areasorter,p.sorter from sample_points_user t1,sample_points t2,plants p  where t1.usrnam=? AND t1.sample_point_id = t2.sample_point_id and t2.area_name = p.area_name and t2.plant = p.plant  and t2.area_name is not null and t2.plant is not null  order by p.areasorter,p.sorter)";
			String sql="select distinct t2.area_name  " + 
		    		"from sample_points_user t1, sample_points t2 " + 
		    		"         where t1.usrnam = ? " + 
		    		"           AND t1.sample_point_id = t2.sample_point_id " + 
		    		"           and t2.area_name is not null " + 
		    		"         order by nlssort(t2.area_name, 'NLS_SORT=SCHINESE_PINYIN_M')";
			List<String> sortArea = dao.queryListValue(sql,String.class,SessionFactory.getUsrName());
			for(String area:sortArea){
				for(ChjVo cj1:cjList){
					if(area.equals(cj1.getName())){
						sortList.add(cj1);
						continue;
					}
				}
			}
			String sqlA = "select basic_rate from plants_setting where area_name='石化质量管理部' and plant='石化质量管理部'";
		    Float totalZlmb = (Float)this.dao.queryValue(sqlA, Float.class);
			request.setAttribute("dept", SessionFactory.getDept());
		    request.setAttribute("cjList", sortList);
		    request.setAttribute("gsmb", totalZlmb);
		    return "/lims/basic/zlmb";
	  }
	  
	  @RequestMapping({"/mateMainTJ"})
	  public String mateMainTJ(HttpServletRequest request) {
		 //String sql = "select t1.area_name id,t1.area_name title,t1.area_name area,'item' type from  AREASITES t1,AREAS t2 where t1.dept=? and t1.area_name=t2.area_name order by t2.sorter";
		 //String sql="select distinct area_name id,area_name title,area_name area,'item' type from(select distinct t2.area_name,p.areasorter,p.sorter from sample_points_user t1,sample_points t2,plants p  where t1.usrnam=? AND t1.sample_point_id = t2.sample_point_id and t2.area_name = p.area_name and t2.plant = p.plant  and t2.area_name is not null and t2.plant is not null  order by p.areasorter,p.sorter)";   
		 String sql="select distinct area_name id, area_name title, area_name area, 'item' type " + 
		    		"from sample_points_user t1, sample_points t2 " + 
		    		"         where t1.usrnam = ? " + 
		    		"           AND t1.sample_point_id = t2.sample_point_id " + 
		    		"           and t2.area_name is not null " + 
		    		"         order by nlssort(t2.area_name, 'NLS_SORT=SCHINESE_PINYIN_M')";
		 JSONArray areaList = this.dao.queryJSONArray(sql,SessionFactory.getUsrName());
	     request.setAttribute("areas", areaList);
	     return "/lims/basic/mateMainTJ";
		  
	  }
	  
	  @RequestMapping({"/mateMain"})
	  public String mateMain(HttpServletRequest request)
	  {
		  
	    return "/lims/basic/mateMain";
	  }
	  
	  @RequestMapping(value={"/mat"}, produces={"application/json;charset=UTF-8"})
	  @ResponseBody
	  public String mat(HttpServletRequest request)
	  {
	   /* String sql = "select distinct s.area_name,s.plant,o.matcode,m.matname"
	    		+ "from orders o,sample_points s,plants t2,materials m,sample_points_user pu  "
	    		+ "where t2.plant = s.plant and t2.area_name=s.area_name "
	    		+ " and  o.sample_point_id=s.sample_point_id and s.sample_point_id = pu.sample_point_id and o.matcode=m.matcode "
	    		+ "and pu.usrnam=? order by s.area_name,s.plant,o.matcode,m.matname";*/
	    String sql = " select distinct s.area_name,s.plant,o.matcode,m.matname "
	  			+ "from orders o,sample_points s,materials m  "
	  			+ "where o.sample_point_id=s.sample_point_id "
	  			+ "and o.matcode=m.matcode  "
	  			+ "and exists(select 1 from sample_points_user p "
	  			+ "where s.sample_point_id=p.sample_point_id  and  p.usrnam=?   ) "
	  			+ "order by nlssort(s.area_name, 'NLS_SORT=SCHINESE_PINYIN_M'),nlssort(s.plant, 'NLS_SORT=SCHINESE_PINYIN_M'),o.matcode";
	    List<Map<String, Object>> list = this.dao.queryListMap(sql,SessionFactory.getUsrName());
	    return AreaPlantTools.areaPlantMatsTree(list).toString();
	  }
	  
	  @RequestMapping({"/mateList"})
	  public String mateList(HttpServletRequest request) {
		  String areaName = request.getParameter("areaname");
		  request.setAttribute("areaName", areaName);
		  String matCode = request.getParameter("id");
		  String insertSql = " insert into mate_setting2(areaname,matecode,testcode,analyte,is_check,username) "
				  +"select prodgroup,matcode,testcode,analyte,'0',? from v_mate_test where prodgroup=? and matcode=? "
				  +"and not exists(select 1 from MATE_SETTING2 t2 where t2.areaname = v_mate_test.prodgroup and t2.matecode=v_mate_test.matcode and t2.testcode=v_mate_test.testcode and t2.analyte=v_mate_test.analyte and t2.username=? )";
		  dao.excuteUpdate(insertSql, SessionFactory.getUsrName(),areaName,matCode,SessionFactory.getUsrName());
		  String sql = "select t1.*,t2.is_check from v_mate_test t1 left join MATE_SETTING2 t2  on t1.prodgroup=t2.areaname and t1.matcode=t2.matecode and t1.testcode=t2.testcode and t1.analyte=t2.analyte  where t1.prodgroup=? and t1.matcode=? and t2.username=? order by t1.testcode,t1.analyte";
	    
		  List<Map<String, Object>> testList = this.dao.queryListMap(sql, areaName, matCode,SessionFactory.getUsrName() );
		 
		  MateVo mate = new MateVo();
		  mate.setAreaName(areaName);
		  mate.setMateCode(matCode);
	    
		  TestVo test = null;
		  
		  for (Map<String, Object> map : testList)
		  {
		      String testcode = ((BigDecimal)map.get("testcode")).toString();
		      if ((test == null) || (!testcode.equals(test.getTestCode())))
		      {
		        test = new TestVo();
		        test.setTestCode(testcode);
		        test.setTestNo((String)map.get("spTestno"));
		        mate.getTests().add(test);
		      }
		      AnalyteVo vo = new AnalyteVo();
		      vo.setAnalyte((String)map.get("analyte"));
		      vo.setSpAnalyte((String)map.get("spSynonym"));
		      vo.setIsCheck((String)map.get("isCheck"));
		      test.getAnalyteList().add(vo);
		  }
		    request.setAttribute("mate", mate);
		    return "/lims/basic/mates";
	  }
	  
	  @RequestMapping({"/mateListTJ"})
	  public String mateListTJ(HttpServletRequest request) {
		  
		  String areaName = request.getParameter("area");
		    request.setAttribute("areaName", areaName);
		    request.setAttribute("usrnam", SessionFactory.getUsrName());
		    String sql = "select tt1.matcode,tt1.matname,tt2.ischeck from  (select distinct t3.matcode,t3.matname,t2.real_area from SAMPLE_PROGRAMS t1,PRODGROUP t2,MATERIALS t3 where t1.prodgroup=t2.prodgroup and t1.matcode=t3.matcode and t2.real_area=?) tt1  left join mat_setting tt2 on tt1.real_area=tt2.area_name and tt1.matcode=tt2.matcode and tt2.usrnam=?";
		    
		    List<Map<String, Object>> matList = this.dao.queryListMap(sql, areaName, SessionFactory.getUsrName());
		    List<MateVo> matVoList = new ArrayList();
		    for (Map<String, Object> map : matList)
		    {
		      MateVo vo = new MateVo();
		      String mateCode = (String)map.get("matcode");
		      String matName = (String)map.get("matname");
		      int isCheck = 0;
		      if (map.get("ischeck") != null) {
		        isCheck = ((BigDecimal)map.get("ischeck")).intValue();
		      }
		      vo.setMateCode(mateCode);
		      vo.setMateName(matName);
		      vo.setIsCheck(isCheck);
		      matVoList.add(vo);
		    }
		    request.setAttribute("mateList", matList);
		    return "/lims/basic/matesTJ";
	  }
	  
	  @RequestMapping({"/detail"})
	  public String detail(HttpServletRequest request)
	  {
	    String area = request.getParameter("area");
	    request.setAttribute("area", area);
	    String sql = "select t1.area_name,t1.plant,t2.basic_rate,t2.is_check from (select area_name,plant from plants where area_name=? group by area_name,plant) t1\tleft join plants_setting t2 on t1.area_name=t2.area_name and t1.plant=t2.plant and t2.username=? order by t1.plant";
	    
	    List<Map<String, Object>> plants = this.dao.queryListMap(sql,  area, SessionFactory.getUsrName() );
	    List<Plant> listVo = new ArrayList<Plant>();
	    for (Map<String, Object> map : plants)
	    {
	      Plant p = new Plant();
	      p.setAreaName((String)map.get("areaName"));
	      p.setPlant((String)map.get("plant"));
	      if (map.get("basicRate") != null) {
	        p.setBasicRate(Float.valueOf(((BigDecimal)map.get("basicRate")).floatValue()));
	      }
	      p.setIsCheck((String)map.get("isCheck"));
	      listVo.add(p);
	    }
	    request.setAttribute("plants", listVo);
	    return "/lims/basic/plants";
	  }
	  
	  @RequestMapping({"/saveTest"})
	  public String saveTest(HttpServletRequest request)
	  {
	    String areaName = request.getParameter("areaName");
	    String mateCode = request.getParameter("mateCode");
	    String testCode = request.getParameter("testCode");
	    String[] analytes = request.getParameterValues("analyte");
	    String delSql = "delete from mate_setting2 where areaname=? and matecode=? and testcode=? and username=? ";
	    this.dao.excuteUpdate(delSql, areaName, mateCode, testCode,SessionFactory.getUsrName() );
	    String updateSql = "insert into mate_setting2(areaname,matecode,testcode,analyte,is_check,username) values(?,?,?,?,?,?)";
	    if (analytes != null)
	    {
	      
	      for (int i = 0; i < analytes.length; i++)
	      {
	        String analyte = analytes[i];
	        
	        this.dao.excuteUpdate(updateSql, areaName, mateCode, testCode, analyte, "1",SessionFactory.getUsrName() );
	      }
	    }
	    String insertSql = " insert into mate_setting2(areaname,matecode,testcode,analyte,is_check,username) "
				  +"select prodgroup,matcode,testcode,analyte,'0',? from v_mate_test where prodgroup=? and matcode=? "
				  +"and not exists(select 1 from MATE_SETTING2 t2 where t2.areaname = v_mate_test.prodgroup and t2.matecode=v_mate_test.matcode and t2.testcode=v_mate_test.testcode and t2.analyte=v_mate_test.analyte and t2.username=? )";
		dao.excuteUpdate(insertSql, SessionFactory.getUsrName(),areaName,mateCode,SessionFactory.getUsrName());
	    String sql = "select t1.*,t2.is_check from v_mate_test t1 left join MATE_SETTING2 t2  on t1.prodgroup=t2.areaname and t1.matcode=t2.matecode and t1.testcode=t2.testcode and t1.analyte=t2.analyte  where t1.prodgroup=? and t1.matcode=? and t2.username=? order by t1.testcode,t1.analyte";
	    
	    List<Map<String, Object>> testList = this.dao.queryListMap(sql,  areaName, mateCode,SessionFactory.getUsrName() );
	    MateVo mate = new MateVo();
	    mate.setAreaName(areaName);
	    mate.setMateCode(mateCode);
	    TestVo test = null;
	    for (Map<String, Object> map : testList)
	    {
	      String testcode = ((BigDecimal)map.get("testcode")).toString();
	      if ((test == null) || (!testcode.equals(test.getTestCode())))
	      {
	        test = new TestVo();
	        test.setTestCode(testcode);
	        test.setTestNo((String)map.get("spTestno"));
	        mate.getTests().add(test);
	      }
	      AnalyteVo vo = new AnalyteVo();
	      vo.setAnalyte((String)map.get("analyte"));
	      vo.setSpAnalyte((String)map.get("spSynonym"));
	      vo.setIsCheck((String)map.get("isCheck"));
	      test.getAnalyteList().add(vo);
	    }
	    request.setAttribute("mate", mate);
	    return "/lims/basic/mates";
	  }
	  
	  @RequestMapping({"/saveMat"})
	  public String saveMat(HttpServletRequest request)
	  {
	    String areaName = request.getParameter("areaName");
	    request.setAttribute("areaName", areaName);
	    request.setAttribute("usrnam", SessionFactory.getUsrName());
	    String[] matCodes = request.getParameterValues("matcode");
	    String sql = "delete from MAT_SETTING where area_name=? and usrnam=? ";
	    this.dao.excuteUpdate(sql,areaName, SessionFactory.getUsrName() );
	    
	    String insertSql = "insert into MAT_SETTING(id,area_name,matcode,usrnam,ischeck) values(?,?,?,?,?)";
	    String id;
	    if (matCodes != null)
	    {
	      
	      for (int i = 0; i < matCodes.length; i++)
	      {
	        String matCode = matCodes[i];
	        id = KeyUtils.uuid();
	        this.dao.excuteUpdate(insertSql, id, areaName, matCode, SessionFactory.getUsrName(), Integer.valueOf(1) );
	      }
	    }
	    String selectSql = "select tt1.matcode,tt1.matname,tt2.ischeck from  (select distinct t3.matcode,t3.matname,t2.real_area from SAMPLE_PROGRAMS t1,PRODGROUP t2,MATERIALS t3 where t1.prodgroup=t2.prodgroup and t1.matcode=t3.matcode and t2.real_area=?) tt1  left join mat_setting tt2 on tt1.real_area=tt2.area_name and tt1.matcode=tt2.matcode and tt2.usrnam=?";
	    
	    List<Map<String, Object>> matList = this.dao.queryListMap(selectSql, areaName, SessionFactory.getUsrName() );
	    List<MateVo> matVoList = new ArrayList();
	    for (Map<String, Object> map : matList)
	    {
	      MateVo vo = new MateVo();
	      String mateCode = (String)map.get("matcode");
	      String matName = (String)map.get("matname");
	      int isCheck = 0;
	      if ((map).get("ischeck") != null) {
	        isCheck = ((BigDecimal)(map).get("ischeck")).intValue();
	      }
	      vo.setMateCode(mateCode);
	      vo.setMateName(matName);
	      vo.setIsCheck(isCheck);
	      matVoList.add(vo);
	    }
	    request.setAttribute("mateList", matList);
	    return "/lims/basic/matesTJ";
	  }
	  
	  @RequestMapping({"/savePlants"})
	  public String savePlants(HttpServletRequest request, Area area)
	  {
	    List<Plant> plants = area.getPlants();
	    String updateSql = "update plants_setting set is_check=?,basic_rate=? where area_name=? and plant=? and username=?";
	    String insertSql = "insert into plants_setting(area_name,plant,basic_rate,is_check,username) values(?,?,?,?,?)";
	    for (Plant plant : plants)
	    {
	      String areaName = plant.getAreaName();
	      String p = plant.getPlant();
	      Float rate = plant.getBasicRate();
	      String isCheck = plant.getIsCheck();
	      if (this.dao.excuteUpdate(updateSql,  isCheck, rate, areaName, p, SessionFactory.getUsrName() ) == 0) {
	        this.dao.excuteUpdate(insertSql,  areaName, p, rate, isCheck, SessionFactory.getUsrName() );
	      }
	    }
	    request.setAttribute("plants", plants);
	    request.setAttribute("areaName", area.getName());
	    return "/lims/basic/plants";
	  }
}
