/**
 * 质量考核
 */
package com.statistics.jbxj.quality.action;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import com.statistics.jbxj.pubModule.handler.Working;
import com.statistics.jbxj.quality.busi.QualityBusi;
import com.statistics.jbxj.quality.vo.AnalytesVo;
import com.statistics.jbxj.quality.vo.ProductRate;
import com.statistics.jbxj.quality.vo.TestsVo;
import pers.czf.dbManager.Dao;
import pers.czf.kit.BigDecimalKit;

@Controller
@RequestMapping("statistics/jbxj/quality")
public class Quality {
	
	@Autowired
	private Dao dao;
	
	@Autowired
	private QualityBusi busi;
	
	/**
	 * 月度为上个月26号到本月25号
	 * @param month
	 * @param model
	 * @return
	 */
	@RequestMapping
	public String index(String month,Model model) {
		
		//获取月度开始、结束时间
		if(month==null||month.equals("")) {
			LocalDate localDate = LocalDate.now();
			month = localDate.format(DateTimeFormatter.ofPattern("yyyy-MM"));
			model.addAttribute("month", month);
		}
		return "statistics/jbxj/quality/index";
	}
	
	@RequestMapping("keyPoint")
	public String keyPoint(String area,String month,Model model) {

		String[] monthStartEnd = Working.monthStartAndEndDay(month);
		
		//判断统计信息是否存在
		String sql = "select t.tj_date,t.status,t.id from JBXJ_quality_kpreport t where t.area_name=? and t.start_date=? and t.end_date=? ";
		Map<String,Object> map = dao.queryMap(sql, area,monthStartEnd[0],monthStartEnd[1]);
		if(map == null) {
			//统计所有信息
			busi.keyPointHz(area, monthStartEnd[0], monthStartEnd[1]);
		}else if("0".equals(map.get("status"))){
			//判断统计时间是否超过当前时间1小时
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
			LocalDateTime cdt = LocalDateTime.now();
			String tjDate = map.get("tjDate").toString();
			if(cdt.minusHours(1).isAfter(LocalDateTime.parse(tjDate, formatter))) {
				String id = map.get("id").toString();
				String del1 = "delete from JBXJ_Quality_kpdetail where report_id=?";
				String del2 = "delete from JBXJ_quality_kpreport where id=?";
				dao.excuteUpdate(del1, id);
				dao.excuteUpdate(del2, id);
				busi.keyPointHz(area, monthStartEnd[0], monthStartEnd[1]);
			}
		}
		
		model.addAttribute("datas",busi.keyPoint(area,monthStartEnd[0], monthStartEnd[1]));
		
		return "statistics/jbxj/quality/keyPoint";
	}
	
	@RequestMapping("keyLimit")
	public String keyLimit(String area,String month,Model model) {

		String[] monthStartEnd = Working.monthStartAndEndDay(month);
		
		//判断统计信息是否存在
		String sql = "select t.tj_date,t.status,t.id from JBXJ_quality_klreport t where t.area_name=? and t.start_date=? and t.end_date=? ";
		Map<String,Object> map = dao.queryMap(sql, area,monthStartEnd[0],monthStartEnd[1]);
		
		if(map == null) {
			//统计所有信息
			busi.keyLimitHz(area, monthStartEnd[0], monthStartEnd[1]);
		}else if("0".equals(map.get("status"))){
			//判断统计时间是否超过当前时间1小时
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
			LocalDateTime cdt = LocalDateTime.now();
			String tjDate = map.get("tjDate").toString();
			if(cdt.minusHours(1).isAfter(LocalDateTime.parse(tjDate, formatter))) {
				String id = map.get("id").toString();
				//删除统计信息
				String del1 = "delete from JBXJ_Quality_kldetail where report_id=?";
				String del2 = "delete from JBXJ_quality_klreport where id=?";
				dao.excuteUpdate(del1, id);
				dao.excuteUpdate(del2, id);
				busi.keyLimitHz(area, monthStartEnd[0], monthStartEnd[1]);
			}
		}
		
		model.addAttribute("datas",busi.keyLimit(area,monthStartEnd[0], monthStartEnd[1]));
		
		return "statistics/jbxj/quality/keyLimit";
	}
	
	@RequestMapping("productRate")
	public String productRate(String area,String month,Model model) {
		String[] monthStartEnd = Working.monthStartAndEndDay(month);
		
		//获取样品信息
		String sql1 = "select t1.matcode,t2.matname from JBXJ_DS_MAT t1,materials t2 where t1.matcode=t2.matcode order by sort";
		List<ProductRate> list1 = dao.queryListObject(sql1,ProductRate.class);
		
		//获取分析项信息
		String sql2 = "select t1.testcode,t1.analyte,t1.sinonym,t2.testno,t1.charlimits,t1.tj_date from JBXJ_DS_ANALYTE_DETAIL t1,tests t2 where t1.testcode=t2.testcode " + 
				" and matcode=? and status='1' order by sort";
		
		for(ProductRate product:list1) {
			List<AnalytesVo> list2 = dao.queryListObject(sql2,AnalytesVo.class,product.getMatcode());
			product.setAnalytes(list2);
			
			TestsVo tests = null;
			for(AnalytesVo analytes:list2) {
				if(tests==null||tests.getTestCode()!=analytes.getTestcode()) {
					tests = new TestsVo();
					tests.setTestCode(analytes.getTestcode());
					tests.setTestno(analytes.getTestno());
					tests.getAnalytes().add(analytes);
					product.getTests().add(tests);
				}else {
					tests.getAnalytes().add(analytes);
				}
			}
		}
		
		//获取合格率统计
		String sql3 = "select brand,count(*) cnum from ct_orders ct where length(ct.batchno)=10 and ct.matcode=? and ct.status='Released' and ct.brand is not null" + 
				" and ct.sampdate>= to_date(?,'yyyy-MM-dd hh24:mi:ss') and ct.sampdate<= to_date(?,'yyyy-MM-dd hh24:mi:ss') " + 
				" group by brand ";
		
		for(ProductRate product:list1) {
			List<Map<String,Object>> list3 = dao.queryListMap(sql3, product.getMatcode(),monthStartEnd[0]+" 00:00:00",monthStartEnd[1]+" 23:59:59");
			for(Map<String,Object> map:list3) {
				String brand = map.get("brand").toString();
				int num = BigDecimalKit.getInt(map.get("cnum"));
				product.setTotalNum(product.getTotalNum()+num);
				if(!"副牌胶".equals(brand)) {
					product.setDoneNum(product.getDoneNum()+num);
				}
			}
		}
		
		model.addAttribute("datas", list1);
		
		return "statistics/jbxj/quality/productRate";
	}
}
