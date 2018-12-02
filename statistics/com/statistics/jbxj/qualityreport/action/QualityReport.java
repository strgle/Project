/**
 * 质量考核
 */
package com.statistics.jbxj.qualityreport.action;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
@Controller
@RequestMapping("statistics/jbxj/qualityReport")
public class QualityReport {
	
	/**
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("{matcode}")
	public String index(@PathVariable("matcode") String matcode,Model model) {
		model.addAttribute("matcode", matcode);
		return "statistics/jbxj/qualityReport/index";
	}
}
