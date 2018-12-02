/**
 * 每日检测数据汇总
 */
package com.statistics.jbxj.quality.action;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.statistics.jbxj.quality.vo.ProcessKeyPoint;
import pers.czf.commonUtils.MsgUtils;
import pers.czf.dbManager.Dao;
import pers.czf.dbManager.PrimaryKey;

/**
 * 每日检测数据汇总
 * @author Administrator
 *
 */
@Controller
@RequestMapping("statistics/jbxj/processSetting")
public class ProcessSetting {

	@Autowired
	private Dao dao;
	
	@RequestMapping
	public String index(Model model){
		//获取要统计的样品
		String sql = "select id,mat_type title from JBXJ_Quality_mattype order by sort";
		JSONArray josnArray = dao.queryJSONArray(sql);
		model.addAttribute("matList", josnArray);
		return "statistics/jbxj/processSetting/index";
	}
	
	@RequestMapping("analytes/{parentId}")
	public String analytes(@PathVariable("parentId") String parentId,Model model){
		String sql = "select * from JBXJ_Quality_keypoint where parent_id= ? order by sort";
		List<ProcessKeyPoint> keyPoints = dao.queryListObject(sql, ProcessKeyPoint.class, parentId);
		model.addAttribute("keyPoints", keyPoints);
		model.addAttribute("parentId", parentId);
		return "statistics/jbxj/processSetting/keyPoints";
	}
	
	@RequestMapping("forAdd/{parentId}")
	public String forAdd(@PathVariable("parentId") String parentId,Model model){
		model.addAttribute("parentId", parentId);
		return "statistics/jbxj/processSetting/forAdd";
	}
	
	@RequestMapping("add")
	public String add(ProcessKeyPoint v,Model model){
		//获取序号
		String sql = "select max(sort) from JBXJ_Quality_keypoint where parent_id=?";
		Integer maxNum = dao.queryValue(sql, Integer.class, v.getParentId());
		if(maxNum==null) {
			maxNum=0;
		}else {
			maxNum = maxNum+1;
		}
		v.setSort(maxNum);
		v.setId(PrimaryKey.uuid());
		dao.create(v);
		return "redirect:/statistics/jbxj/processSetting/analytes/"+v.getParentId();
	}
	
	@RequestMapping("forUpdate/{id}")
	public String forUpdate(@PathVariable("id") String id,Model model){
		ProcessKeyPoint keyPoint = dao.read(ProcessKeyPoint.class, id);
		model.addAttribute("keyPoint", keyPoint);
		return "statistics/jbxj/processSetting/forUpdate";
	}
	
	@RequestMapping("update")
	public String update(ProcessKeyPoint keyPoint,Model model){
		dao.update(keyPoint);
		return "redirect:/statistics/jbxj/processSetting/analytes/"+keyPoint.getParentId();
	}
	
	@RequestMapping(value="sort",produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String sort(HttpServletRequest request){
		String id = request.getParameter("id");
		String value = request.getParameter("value");
		String sql = "update JBXJ_Quality_keypoint set sort=? where id=?";
		dao.excuteUpdate(sql, value,id);
		return MsgUtils.success().toString();
	}
	
	@RequestMapping(value="del/{id}",produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String del(@PathVariable("id") String id){
		String sql = "delete from JBXJ_Quality_keypoint where id=?";
		dao.excuteUpdate(sql,id);
		return MsgUtils.success().toString();
	}
}
