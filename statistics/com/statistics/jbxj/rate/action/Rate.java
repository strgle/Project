/**
 * 每日检测数据汇总
 */
package com.statistics.jbxj.rate.action;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.statistics.jbxj.pubModule.constant.MatType;
import com.statistics.jbxj.pubModule.constant.ReportType;
import com.statistics.jbxj.pubModule.handler.Working;
import com.statistics.jbxj.rate.busi.RateBusi;
import com.statistics.jbxj.rate.vo.RateBrand;
import com.statistics.jbxj.rate.vo.RateDetail;
import pers.czf.commonUtils.MsgUtils;
import pers.czf.dbManager.Dao;

/**
 * 每日检测数据汇总
 * @author Administrator
 *
 */
@Controller
@RequestMapping("statistics/jbxj/rate")
public class Rate {

	@Autowired
	private Dao dao;
	
	@Autowired
	private RateBusi busi;
	
	/**
	 * @param request
	 * @return
	 */
	@RequestMapping
	public String index(Integer year,Model model){
		int curYear = LocalDate.now().getYear();
		if(year==null||year==0) {
			year = curYear;
		}
		
		model.addAttribute("year", year);
		return "statistics/jbxj/rate/index";
	}
	
	@RequestMapping("product")
	public String product(String matCode,Integer year,Model model){
		int curYear = LocalDate.now().getYear();
		if(year==null||year==0) {
			year = curYear;
		}
		
		model.addAttribute("year", year);
		
		//获取统计是否存在
		int lastMonth = 12;
		if(curYear==year) {
			lastMonth = LocalDate.now().getMonthValue();
		}else if(curYear<year) {
			return "";
		}
		
		synchronized(this) {
			for(int i=1;i<=lastMonth;i++) {
				busi.productMonthHZ(year, i, matCode);
			}
		}
		
		
		//查询标题
		String sql = "select * from JBXJ_RATE_brand where mat_type=? and matcode=? and year=? and is_show='1' order by sort";
		List<RateBrand> titles = dao.queryListObject(sql, RateBrand.class, MatType.Product,matCode,year);
		model.addAttribute("titles", titles);
				
		//查询数据
		String sql2 = "select t1.year,t1.month,t2.* from JBXJ_RATE_report t1,JBXJ_RATE_DETAIL t2 " + 
						" where t1.id=t2.report_id and t1.report_type=? and t1.mat_type=? and t1.matcode=? and t1.year=? order by t1.month";
		
		List<RateDetail> datas = dao.queryListObject(sql2, RateDetail.class,ReportType.MONTH,MatType.Product,matCode,year);
		
		//将信息封装成行数据
		int curMonth = 0;
		int newMonth = 1;
		List<List<RateDetail>> monthsData = new ArrayList<List<RateDetail>>();
		List<RateDetail> monthData = new ArrayList<RateDetail>();
		for(RateDetail detail:datas) {
			newMonth = detail.getMonth();
			
			if(newMonth==curMonth) {
				monthData.add(detail);
			}else{
				curMonth = newMonth;
				monthData  = new ArrayList<RateDetail>();
				monthsData.add(monthData);
				monthData.add(detail);
			}
		}
		
		model.addAttribute("monthsData", monthsData);
		
		return "statistics/jbxj/rate/product";
	}
	
	@RequestMapping(value="manual",produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String manual(String id,String value) {
		//判断value是否为数字
		Pattern pattern = Pattern.compile("^-?[0-9]+?.?[0-d]*$");  
		if(pattern.matcher(value).matches()) {
			String sql = "update jbxj_rate_detail set total_num = ? where id = ? ";
			dao.excuteUpdate(sql,value, id);
		};
		return MsgUtils.success().toString();
	}
	
	/**
	 * 周统计
	 * @param endDate 一周的开始结束时间
	 * @param model
	 * @return
	 */
	@RequestMapping("week/{matcode}")
	public String week(@PathVariable("matcode") String matcode,String endDate,String flag,Model model) {
		
		String[] weekSE = Working.weekStartAndEndDay(endDate,flag);
		String startDate = weekSE[0];
		endDate = weekSE[1];
		
		model.addAttribute("startDate", startDate);
		model.addAttribute("endDate", endDate);
		//查询中控巡检点信息
		model.addAttribute("processDatas", busi.queryProcessReport(startDate, endDate, ReportType.WEEK));
		
		//统计产品报告单及巡检点信息
		model.addAttribute("reportDatas", busi.queryReport(startDate, endDate, ReportType.WEEK,matcode));
		model.addAttribute("matcode", matcode);
		return "statistics/jbxj/qualityReport/week";
	}
	
	/**
	 * 旬统计
	 * @param endDate 每旬的开始结束时间
	 * @param model
	 * @return
	 */
	@RequestMapping("ten/{matcode}")
	public String ten(@PathVariable("matcode") String matcode,String endDate,String flag,Model model) {
		
		String[] tenSE = Working.tenStartAndEndDay(endDate, flag);
		
		String startDate = tenSE[0];
		endDate = tenSE[1];
		
		model.addAttribute("startDate", startDate);
		model.addAttribute("endDate", endDate);

		model.addAttribute("reportDatas", busi.queryReport(startDate, endDate, ReportType.TEN,matcode));
		model.addAttribute("processDatas", busi.queryProcessReport(startDate, endDate, ReportType.TEN));
		model.addAttribute("matcode", matcode);
		return "statistics/jbxj/qualityReport/ten";
	}
	
	
	@RequestMapping("month/{matcode}")
	public String month(@PathVariable("matcode") String matcode,String endDate,String flag,Model model) {
		
		String[] tenSE = Working.naturalMonthStartAndEndDay(endDate, flag);
		
		String startDate = tenSE[0];
		endDate = tenSE[1];
		
		model.addAttribute("startDate", startDate);
		model.addAttribute("endDate", endDate);
		model.addAttribute("reportDatas", busi.queryReport(startDate, endDate, ReportType.MONTH,matcode));
		model.addAttribute("processDatas", busi.queryProcessReport(startDate, endDate, ReportType.MONTH));
		model.addAttribute("matcode", matcode);
		return "statistics/jbxj/qualityReport/month";
	}
}
