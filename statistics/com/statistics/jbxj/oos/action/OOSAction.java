/**
 * 每日检测数据汇总
 */
package com.statistics.jbxj.oos.action;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.statistics.jbxj.oos.vo.ProcessOOS;
import com.statistics.jbxj.oos.vo.ProductOOS;
import com.statistics.jbxj.pubModule.handler.Working;
import pers.czf.dbManager.Dao;
import pers.czf.kit.BigDecimalKit;

/**
 * 不合格点检查表
 * @author Administrator
 */
@Controller
@RequestMapping("statistics/jbxj/oos")
public class OOSAction {
	
	@Autowired
	private Dao dao;
	
	/**
	 * 上班时间
	 * @param request
	 * @return
	 */
	@RequestMapping("product")
	public String product(Model model){
		//获取要统计的样品
		String sql = "select t1.matcode,t2.matname,t1.sort from JBXJ_DS_MAT t1,materials t2 where t1.matcode=t2.matcode order by t1.sort";
		List<Map<String,Object>> list = dao.queryListMap(sql);
		model.addAttribute("matList", list);
		
		//获取月份
		String month = "";
		LocalDate localDate = LocalDate.now();
		month = localDate.format(DateTimeFormatter.ofPattern("yyyy-MM"));
		model.addAttribute("month", month);
		return "statistics/jbxj/oos/index";
	}
	
	@RequestMapping("product/{matCode}")
	public String productDetail(@PathVariable("matCode") String matCode,String month,Model model){
		String[] monthStartEnd = Working.monthStartAndEndDay(month);
		//获取开始结束日期
		String start = monthStartEnd[0]+" 00:00:00";
		String end = monthStartEnd[1]+" 23:59:59";
		
		//获取要统计的分析项
		String sql = "select matcode,testcode,analyte,sinonym from JBXJ_DS_ANALYTE where matcode=? order by sort";
		List<ProductOOS> analytes = dao.queryListObject(sql,ProductOOS.class,matCode);
		
		//获取每天的统计结果信息
		String sql2 = "select t1.testcode,t1.analyte_id analyte,substr(t2.batchno,6,2) riqi,count(*) cnum  from ipcoa_tempvalues t1,ct_orders t2 " + 
				"where t1.ordno=t2.ordno and t2.matcode=? and length(t2.batchno)=10 and (t2.batchno like '1%' or t2.batchno like '2%') " + 
				"and t2.batchno not like '%24' and t1.status='OOS-A' and t2.sampdate>=to_date(?,'yyyy-MM-dd hh24:mi:ss') " + 
				"and t2.sampdate<=to_date(?,'yyyy-MM-dd hh24:mi:ss') and t2.status in ('Released','Confirm')  " + 
				"group by substr(t2.batchno,6,2),t1.testcode,t1.analyte_id";
		
		List<Map<String,Object>> list2 = dao.queryListMap(sql2,matCode,start,end);
		
		//获取所有日期
		String[] days = Working.monthDays(month);
		List<String> dayLists = Arrays.asList(days);
		int totalOosNum = 0;
		for(Map<String,Object> map:list2) {
			String analyte = map.get("analyte").toString();
			int cnum = BigDecimalKit.getIntOrDefault(map.get("cnum"), 0);
			String riqi = map.get("riqi").toString();
			int index = dayLists.indexOf(riqi);
			for(ProductOOS product:analytes) {
				if(analyte.equals(product.getAnalyte())) {
					product.getDayOosNum()[index] = cnum;
					product.setOosNum(product.getOosNum()+cnum);
					totalOosNum = totalOosNum+cnum;
				}
			}
		}
		model.addAttribute("analytes", analytes);
		model.addAttribute("days", dayLists);
		model.addAttribute("totalOosNum", totalOosNum);
		return "statistics/jbxj/oos/products";
	}
	
	/**
	 * 上班时间
	 * @param request
	 * @return
	 */
	@RequestMapping("process/{matType}")
	public String process(HttpServletRequest request,@PathVariable("matType") String matType, Model model){
		String month = request.getParameter("month");
		//获取月份
		if(month==null||month.equals("")) {
			LocalDate localDate = LocalDate.now();
			month = localDate.format(DateTimeFormatter.ofPattern("yyyy-MM"));
		}
		model.addAttribute("month", month);
		
		//获取要统计的项目信息
		String sql = "select t1.*,t2.matname from JBXJ_XJ_MAT t1,materials t2 where t1.MAT_TYPE=? and t1.matcode=t2.matcode order by t1.sort";
		List<ProcessOOS> analytes = dao.queryListObject(sql,ProcessOOS.class,matType);
		
		//获取开始结束日期
		String[] monthStartEnd = Working.monthStartAndEndDay(month);
		
		//获取所有日期
		String[] days = Working.monthDays(month);
		List<String> dayLists = Arrays.asList(days);
		
		//获取每天的统计结果信息
		String sql1 = "select mat.matcode,mat.testcode,mat.analyte,to_char(o.realsampdate,'dd') riqi,count(*) cnum " + 
				" from JBXJ_XJ_MAT mat, orders o,plantdaily p " + 
				" where MAT_TYPE=? and o.matcode=mat.matcode " + 
				" and o.sample_point_id=mat.point_id and p.ordno=o.ordno and p.testcode=mat.testcode and p.analyte=mat.analyte " + 
				" and o.realsampdate>=to_date(?,'yyyy-MM-dd hh24:mi:ss') and o.realsampdate<=to_date(?,'yyyy-MM-dd hh24:mi:ss') " + 
				" and p.s='OOS-A' " + 
				" group by mat.matcode,mat.testcode,mat.analyte,to_char(o.realsampdate,'dd') order by to_char(o.realsampdate,'dd') ";
		List<Map<String,Object>> list2 = dao.queryListMap(sql1, matType,monthStartEnd[0]+" 00:00:00",monthStartEnd[1]+" 23:59:59");
		int totalOosNum = 0;
		
		for(Map<String,Object> map:list2) {
			String analyte = map.get("analyte").toString();
			int testcode = BigDecimalKit.getInt(map.get("testcode"));
			String matcode = map.get("matcode").toString();
			int cnum = BigDecimalKit.getIntOrDefault(map.get("cnum"), 0);
			String riqi = map.get("riqi").toString();
			int index = dayLists.indexOf(riqi);
			for(ProcessOOS process:analytes) {
				if(matcode.equals(process.getMatcode())&&testcode==process.getTestcode()&&analyte.equals(process.getAnalyte())) {
					process.getDayOosNum()[index] = cnum;
					process.setOosNum(process.getOosNum()+cnum);
					totalOosNum = totalOosNum+cnum;
				}
			}
		}
		model.addAttribute("analytes", analytes);
		model.addAttribute("days", dayLists);
		model.addAttribute("totalOosNum", totalOosNum);
		model.addAttribute("matType", matType);
		return "statistics/jbxj/oos/process";
	}
	
}
