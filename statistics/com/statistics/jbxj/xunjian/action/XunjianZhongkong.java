/**
 * 每日检测数据汇总
 */
package com.statistics.jbxj.xunjian.action;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.statistics.jbxj.pubModule.constant.MatType;
import com.statistics.jbxj.pubModule.constant.ReportType;
import com.statistics.jbxj.rate.busi.RateBusi;
import com.statistics.jbxj.xunjian.busi.XunjianBusi;
import com.statistics.jbxj.xunjian.vo.ProductXjVo;
import com.statistics.jbxj.xunjian.vo.XjDetailVo;
import pers.czf.commonUtils.MsgUtils;
import pers.czf.dbManager.Dao;

/**
 * 每日巡检汇总表
 * @author Administrator
 * 中控巡检情况记录    
 * lqygcy  0920
 */
@Controller
@RequestMapping("statistics/jbxj/xunjian/zk")
public class XunjianZhongkong {
	
	@Autowired
	private Dao dao;
	
	@Autowired
	private XunjianBusi busi;
	
	@Autowired
	private RateBusi rate;
	
	/**
	 * 上班时间
	 * @param request
	 * @return
	 */
	@RequestMapping
	public String index( String matc, String endDate,String endTime,Model model){
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		if(StringUtils.isEmpty(endTime)) {
			endDate = LocalDateTime.now().minusDays(1).format(formatter);
			endTime = "15:00:00";
		}
		model.addAttribute("endDate", endDate);
		
		//获取开始时间
		LocalDate startDate = LocalDate.parse(endDate, formatter).minusDays(1);
		//获取中控所有巡检点的信息(样品、分析项、车间)
		List<XjDetailVo> xjp = busi.queryProcess(startDate.format(formatter)+" "+endTime, endDate+" "+endTime);
		model.addAttribute("process", xjp);
		
		XjDetailVo procTotal = new XjDetailVo();
		for(XjDetailVo vo:xjp) {
			procTotal.setTotalNum(procTotal.getTotalNum()+vo.getTotalNum());
			procTotal.setDoneNum(procTotal.getDoneNum()+vo.getDoneNum());
			procTotal.setOosbNum(procTotal.getOosbNum()+vo.getOosbNum());
		}
		model.addAttribute("procTotal", procTotal);
		
		//获取巡检备注信息
		List<Map<String,Object>> remarks = busi.queryRemark(startDate.format(formatter)+" "+endTime, endDate+" "+endTime,MatType.Process);
		model.addAttribute("remarks", remarks);
		//获取产品所有巡检点的信息(样品、分析项、车间)
		busi.xjHZ(startDate.format(formatter)+" "+endTime, endDate+" "+endTime,MatType.Product);
		List<ProductXjVo> products = new ArrayList<ProductXjVo>(); 
		String sql1 = "select t3.matcode,t3.matname from materials t3  where t3.matcode =?";
		List<Map<String,Object>> mats = dao.queryListMap(sql1,matc);
		for(Map<String,Object> mat:mats) {
			ProductXjVo productVo = new ProductXjVo();
			String matCode = mat.get("matcode").toString();
			productVo.setMatCode(matCode);
			String matName = mat.get("matname").toString();
			productVo.setMatName(matName);
			List<XjDetailVo> productDetail = busi.queryProduct(startDate.format(formatter)+" "+endTime, endDate+" "+endTime,matCode);
			productVo.setXjDetail(productDetail);
			products.add(productVo);
		}
		model.addAttribute("products", products);
		
		for(ProductXjVo prod:products) {
			for(XjDetailVo vo:prod.getXjDetail()) {
				prod.getTotal().setTotalNum(prod.getTotal().getTotalNum()+vo.getTotalNum());
				prod.getTotal().setDoneNum(prod.getTotal().getDoneNum()+vo.getDoneNum());
				prod.getTotal().setOosbNum(prod.getTotal().getOosbNum()+vo.getOosbNum());
			}
		}
		
		model.addAttribute("reportDatas", rate.queryDayReport(startDate.format(formatter)+" "+endTime, endDate+" "+endTime, ReportType.DAY,matc));
		model.addAttribute("matcode", matc);
		return "statistics/jbxj/xunjian/zkindex";
	}
	
}
