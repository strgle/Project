/**
 * 每日检测数据汇总
 */
package com.statistics.jbxj.daily.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.statistics.jbxj.daily.vo.MatAnalyte;

import pers.czf.commonUtils.MsgUtils;
import pers.czf.dbManager.Dao;
import pers.czf.dbManager.PrimaryKey;

/**
 * 每日检测数据汇总
 * @author Administrator
 *
 */
@Controller
@RequestMapping("statistics/jbxj/dailySetting")
public class DailySetting {

	@Autowired
	private Dao dao;
	
	@RequestMapping
	public String index(Model model){
		
		//获取要统计的样品
		String sql = "select t1.matcode id,t2.matname title from JBXJ_DS_MAT t1,materials t2 where t1.matcode=t2.matcode order by t1.sort";
		JSONArray josnArray = dao.queryJSONArray(sql);
		model.addAttribute("matList", josnArray);
		return "statistics/jbxj/dailySetting/index";
	}
	
	@RequestMapping("analytes/{matcode}")
	public String analytes(@PathVariable("matcode") String matCode,Model model){
		String sql = "select * from JBXJ_DS_ANALYTE where MATCODE = ? order by sort";
		List<MatAnalyte> analytes = dao.queryListObject(sql, MatAnalyte.class, matCode);
		model.addAttribute("analytes", analytes);
		model.addAttribute("matcode", matCode);
		return "statistics/jbxj/dailySetting/analytes";
	}
	
	@RequestMapping("forAdd/{matcode}")
	public String forAdd(@PathVariable("matcode") String matCode,Model model){
		model.addAttribute("matcode", matCode);
		String sql = "select distinct v.testcode, v.sp_testno, v.analyte, v.sp_synonym " + 
				"  from v_mate_test v, analytes a " + 
				" where v.testcode = a.testcode " + 
				" and v.analyte = a.analyte " + 
				" and a.printflag ='Y' " + 
				" and matcode = 'XJ0111' " + 
				" order by testcode, sp_testno, analyte, sp_synonym";
		
		
		return "statistics/jbxj/dailySetting/forAdd";
	}
	
	@RequestMapping("add")
	public String add(MatAnalyte analyte,Model model){
		//获取序号
		String sql = "select max(sort) from JBXJ_DS_ANALYTE where matcode=?";
		Integer maxNum = dao.queryValue(sql, Integer.class, analyte.getMatcode());
		if(maxNum==null) {
			maxNum=0;
		}else {
			maxNum = maxNum+1;
		}
		analyte.setSort(maxNum);
		analyte.setId(PrimaryKey.uuid());
		analyte.setStatus("1");
		dao.create(analyte);
		return "redirect:/statistics/jbxj/dailySetting/analytes/"+analyte.getMatcode();
	}
	
	@RequestMapping("forUpdate/{id}")
	public String forUpdate(@PathVariable("id") String id,Model model){
		MatAnalyte analyte = dao.read(MatAnalyte.class, id);
		model.addAttribute("analyte", analyte);
		return "statistics/jbxj/dailySetting/forUpdate";
	}
	
	@RequestMapping("update")
	public String update(MatAnalyte analyte,Model model){
		dao.update(analyte);
		return "redirect:/statistics/jbxj/dailySetting/analytes/"+analyte.getMatcode();
	}
	
	@RequestMapping(value="sort",produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String sort(HttpServletRequest request){
		String id = request.getParameter("id");
		String value = request.getParameter("value");
		String sql = "update JBXJ_DS_ANALYTE set sort=? where id=?";
		dao.excuteUpdate(sql, value,id);
		return MsgUtils.success().toString();
	}
	
	@RequestMapping(value="del/{id}",produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String del(@PathVariable("id") String id){
		String sql = "delete from JBXJ_DS_ANALYTE where id=?";
		dao.excuteUpdate(sql,id);
		return MsgUtils.success().toString();
	}
}
