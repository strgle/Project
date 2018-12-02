
package com.statistics.jbxj.xunjian.action;

import java.util.List;
import javax.servlet.http.HttpServletRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.statistics.jbxj.xunjian.vo.XjSettingVo;
import pers.czf.commonUtils.MsgUtils;
import pers.czf.dbManager.Dao;
import pers.czf.dbManager.PrimaryKey;

/**
 * 中控巡检点设置
 * @author Administrator
 *
 */
@Controller
@RequestMapping("statistics/jbxj/xunjianSetting")
public class XunjianSetting {

	@Autowired
	private Dao dao;
	
	@RequestMapping
	public String index(Model model){
		//获取要统计的样品
		JSONArray jsonArray = new JSONArray();
		
		try {
			JSONObject json1 = new JSONObject();
			json1.put("id", "Process");
			json1.put("title", "橡胶中控");
			jsonArray.put(json1);
			
			JSONObject json2 = new JSONObject();
			json2.put("id", "Huaju");
			json2.put("title", "华聚中控");
			jsonArray.put(json2);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		model.addAttribute("matList", jsonArray);
		return "statistics/jbxj/xjSetting/index";
	}
	
	@RequestMapping("point/{matType}")
	public String point(@PathVariable("matType") String matType,Model model){
		//获取要统计的样品
		String sql = "select * from JBXJ_XJ_MAT where mat_type=? order by sort";
		List<XjSettingVo> list = dao.queryListObject(sql, XjSettingVo.class,matType);
		model.addAttribute("xjList", list);
		model.addAttribute("matType", matType);
		return "statistics/jbxj/xjSetting/points";
	}
	
	@RequestMapping("forAdd/{matType}")
	public String forAdd(@PathVariable("matType") String matType,Model model){
		model.addAttribute("matType", matType);
		
		if(matType.equals("Process")) {
			model.addAttribute("showName", "橡胶中控");
		}else {
			model.addAttribute("showName", "华聚中控");
		}
		
		return "statistics/jbxj/xjSetting/forAdd";
	}
	
	@RequestMapping("add")
	public String add(XjSettingVo v){
		//获取序号
		String sql = "select max(sort) from JBXJ_XJ_MAT";
		Integer maxNum = dao.queryValue(sql, Integer.class);
		if(maxNum==null) {
			maxNum=0;
		}else {
			maxNum = maxNum+1;
		}
		v.setSort(maxNum);
		v.setId(PrimaryKey.uuid());
		dao.create(v);
		return "redirect:/statistics/jbxj/xunjianSetting/point/"+v.getMatType();
	}
	
	@RequestMapping("forUpdate/{id}")
	public String forUpdate(@PathVariable("id") String id,Model model){
		XjSettingVo v = dao.read(XjSettingVo.class, id);
		if(v.getMatType().equals("Process")) {
			model.addAttribute("showName", "橡胶中控");
		}else {
			model.addAttribute("showName", "华聚中控");
		}
		
		model.addAttribute("keyPoint", v);
		return "statistics/jbxj/xjSetting/forUpdate";
	}
	
	@RequestMapping("update")
	public String update(XjSettingVo v){
		dao.update(v);
		return "redirect:/statistics/jbxj/xunjianSetting/point"+v.getMatType();
	}
	
	@RequestMapping(value="sort",produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String sort(HttpServletRequest request){
		String id = request.getParameter("id");
		String value = request.getParameter("value");
		String sql = "update JBXJ_XJ_MAT set sort=? where id=?";
		dao.excuteUpdate(sql, value,id);
		return MsgUtils.success().toString();
	}
	
	@RequestMapping(value="del/{id}",produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String del(@PathVariable("id") String id){
		String sql = "delete from JBXJ_XJ_MAT where id=?";
		dao.excuteUpdate(sql,id);
		return MsgUtils.success().toString();
	}
}
