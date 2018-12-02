package lims.dataLedger.mvc;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import lims.dataLedger.services.ProdMesService;
import lims.dataLedger.services.ResultService;
import lims.dataLedger.vo.ParamsVo;
import pers.czf.commonUtils.DateUtils;
import pers.czf.dbManager.Dao;

@Controller
@RequestMapping("lims/dataLedger/area")
public class AreaLedgerAction {
	
	@Autowired
	private Dao dao;
	
	@Autowired
	private ResultService service;
	
	@Autowired
	private ProdMesService prodService;
	
	@RequestMapping
	public String index(){
		return "lims/dataLedger/area/areaIndex";
	}
	
	@RequestMapping("2")
	public String index2(){
		return "lims/dataLedger/area/areaIndex2";
	}
	
	@RequestMapping("detail")
	public String prodDetail(HttpServletRequest request){
		String startDate = request.getParameter("prodDate");
		if(startDate==null||startDate.equals("")){
			Calendar c = Calendar.getInstance();
			c.add(Calendar.DAY_OF_MONTH, -1);
			startDate = DateUtils.DateFormat(c.getTime(), "yyyy-MM-dd");
		}
		//获取截止日期
		String endDate = request.getParameter("endDate");
		if(endDate==null||endDate.equals("")){
			endDate = startDate;
		}
		request.setAttribute("endDate", endDate);
		
		String areaName = request.getParameter("areaName");
		String plant = request.getParameter("plant");
		request.setAttribute("prodDate", startDate);
		request.setAttribute("areaName", areaName);
		request.setAttribute("plant", plant);
		//获取生产信息
		String sql = "select count(*) from MES_PROD_MES where area_name=? and plant=? and prod_date=?";
		if(dao.queryValue(sql, Integer.class, areaName, plant, startDate)>0){
			prodService.prodMes2(request, areaName, plant, startDate,endDate);
		}else{
			prodService.prodMes1(request, areaName, plant, startDate,endDate);
		};
		
		//获取物料平衡信息
		String sql2 = "select count(*) from MES_mb_MES where area_name=? and plant=? and prod_date=?";
		if(dao.queryValue(sql2, Integer.class, areaName, plant, startDate)>0){
			prodService.mbMes2(request, areaName, plant, startDate,endDate);
		}else{
			prodService.mbMes0(request, areaName, plant, startDate,endDate);
		};
		
		return "lims/dataLedger/area/allMsg";
	}
	
	@RequestMapping("detail2")
	public String prodDetail2(HttpServletRequest request){
		//开始日期
		String startDate = request.getParameter("startDate");
		if(startDate==null||startDate.equals("")){
			Calendar c = Calendar.getInstance();
			c.add(Calendar.DAY_OF_MONTH, -1);
			startDate = DateUtils.DateFormat(c.getTime(), "yyyy-MM-dd");
		}
		request.setAttribute("startDate", startDate);
		//获取截止日期
		String endDate = request.getParameter("endDate");
		if(endDate==null||endDate.equals("")){
			endDate = startDate;
		}
		request.setAttribute("endDate", endDate);
		
		String areaName = request.getParameter("areaName");
		String plant = request.getParameter("plant");
		request.setAttribute("areaName", areaName);
		request.setAttribute("plant", plant);
		
		//获取生产信息
		String sql = "select count(*) from MES_PROD_MES where area_name=? and plant=? and prod_date>=? and prod_date<=?";
		if(dao.queryValue(sql, Integer.class, areaName, plant, startDate,endDate)>0){
			prodService.prodListMes2(request, areaName, plant, startDate,endDate);
		}
		
		//获取物料平衡信息
		String sql2 = "select count(*) from MES_mb_MES where area_name=? and plant=? and prod_date>=? and prod_date<=?";
		if(dao.queryValue(sql2, Integer.class, areaName, plant, startDate,endDate)>0){
			prodService.mbMes2(request, areaName, plant, startDate,endDate);
		}
		
		return "lims/dataLedger/area/allMsg2";
	}
	
	@RequestMapping("testIndex")
	public String testIndex(){
		return "lims/dataLedger/area/testIndex";
	}
	
	@RequestMapping("testDetail")
	public String testDetail(HttpServletRequest request){
		
		//获取开始日期
		String startDate = request.getParameter("startDate");
		if(startDate==null||startDate.equals("")){
			Calendar c = Calendar.getInstance();
			c.add(Calendar.DAY_OF_MONTH, -3);
			startDate = DateUtils.DateFormat(c.getTime(), "yyyy-MM-dd");
		}
		//获取截止日期
		String endDate = request.getParameter("endDate");
		if(endDate==null||endDate.equals("")){
			Calendar cl = Calendar.getInstance();
			cl.add(Calendar.DAY_OF_MONTH, -1);
			endDate = DateUtils.DateFormat(cl.getTime(), "yyyy-MM-dd");
		}
		
		ParamsVo vo = new ParamsVo();
		vo.setStartTime(startDate+" 00:00:00");
		vo.setEndTime(endDate+" 23:59:59");
		String areaName = request.getParameter("areaName");
		vo.setAreaName(areaName);
		String plant = request.getParameter("plant");
		vo.setPlant(plant);
		request.setAttribute("startDate", startDate);
		request.setAttribute("endDate", endDate);
		request.setAttribute("areaName", areaName);
		request.setAttribute("plant", plant);
		
		//采样时间信息
		List<String> sampDate = new ArrayList<String>();
		
		//获取样品信息
		List<Map<String,Object>> mats = service.mats(vo);
		int length = mats.size();
		
		String samplePointMap = "select sample_point_id key,description value from sample_points";
		Map<String,Object> pointMap = dao.queryKeyValue(samplePointMap);
		
		//获取样品信息下对应的采样信息
		Object[][] allOrders = new Object[length][3];
		
		for(int i=0;i<length;i++){
			Map<String,Object> m = mats.get(i);
			String matCode = m.get("matcode").toString();
			String samplePointId = m.get("samplePointId").toString();
			vo.setMatCode(matCode);
			vo.setSamplePointId(samplePointId);
			
			List<String> titles = service.resultTitles(vo);
			Object[][] orders = service.ordDetails(vo, titles);
			allOrders[i][0]=new String[]{pointMap.get(samplePointId).toString(),m.get("matname").toString()};
			allOrders[i][1]=titles;			
			allOrders[i][2]=orders;
			
			//处理样品检测时间
			Object[] timeArray = orders[0];
			Map<String,Integer> timeNum = new HashMap<String, Integer>();
			for(int j=0,len=timeArray.length;j<len;j++){
				String time = timeArray[j].toString();
				if(timeNum.containsKey(time)){
					timeNum.put(time.toString(), timeNum.get(time)+1);
				}else{
					timeNum.put(time.toString(), 1);
				}
				timeArray[j] = time+" "+timeNum.get(time);
			}
			//添加时间
			for(Object time:timeArray){
				if(!sampDate.contains(time.toString())){
					sampDate.add(time.toString());
				}
			}
		}
		
		//对List进行排序
		Collections.sort(sampDate,new Comparator<String>(){
			@Override
			public int compare(String o1, String o2) {
				// TODO Auto-generated method stub
				return o1.compareTo(o2);
			}			
		});
		
		request.setAttribute("data", allOrders);
		request.setAttribute("sampDate", sampDate);
		
		return "lims/dataLedger/area/testMsg";
	}
}
