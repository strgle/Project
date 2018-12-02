/**
 * 每日检测数据汇总
 */
package com.statistics.jbxj.daily.action;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.statistics.jbxj.daily.busi.DailyBusi;
import com.statistics.jbxj.daily.handler.PreParameter;
import com.statistics.jbxj.daily.vo.DailyHz;

import pers.czf.dbManager.Dao;

/**
 * 每日检测数据汇总
 * @author Administrator
 *
 */
@Controller
@RequestMapping("statistics/jbxj/daily")
public class Daily {

	@Autowired
	private Dao dao;
	
	@Autowired
	private DailyBusi busi;
	
	/**
	 * 上班时间：11-20 1120,20-03 2003，03-11 0311
	 * @param request
	 * @return
	 */
	@RequestMapping
	public String index(String startTime,String endTime,String matCode,Model model){
		
		//获取要统计的样品
		String sql = "select t1.matcode,t2.matname,t1.sort from JBXJ_DS_MAT t1,materials t2 where t1.matcode=t2.matcode order by t1.sort";
		List<Map<String,Object>> list = dao.queryListMap(sql);
		model.addAttribute("matList", list);
		
		if(matCode==null||matCode.equals("")) {
			matCode = list.get(0).get("matcode").toString();
		}
		
		//统计时间区间
		if(startTime==null||startTime.equals("")) {
			startTime = PreParameter.startDayTime();
		}
		model.addAttribute("startDate", startTime.substring(0, 10));
		
		if(endTime==null||endTime.equals("")) {
			endTime = PreParameter.endDayTime();
		}
		model.addAttribute("endDate", endTime.substring(0, 10));

		return "statistics/jbxj/daily/index";
	}
	
	@RequestMapping("matHz/{matcode}")
	public String matHz(String startTime,String endTime,@PathVariable("matcode") String matCode,Model model){
		DailyHz dailyHz = busi.queryDailyHz(matCode, startTime, endTime);
		model.addAttribute("matHz",dailyHz);
		return "statistics/jbxj/daily/matHz";
	}
}
