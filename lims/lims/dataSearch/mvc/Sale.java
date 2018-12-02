package lims.dataSearch.mvc;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import lims.dataSearch.services.SaleService;
import pers.czf.dbManager.Dao;

@Controller
@RequestMapping("lims/dataSearch/sale")
public class Sale {
	
	@Autowired
	private Dao dao;
	
	@Autowired
	private SaleService service;
	
	@RequestMapping
	public String index(HttpServletRequest request){
		//获取样品类型
		String sql = "select distinct SALETYPE id,saletype title,'item' type from sample_points where SALETYPE is not null order by id";
		String saleTypes = dao.queryJSONArray(sql).toString();
		request.setAttribute("saleTypes", saleTypes);
		return "lims/dataSearch/sale/index";
	}
	/**
	 * 装置日报--按采样点
	 * 针对中控样品进行查询
	 * @param request
	 * @return
	 */
	@RequestMapping("sale")
	public String sale(HttpServletRequest request){
		String saleType = request.getParameter("id");
		
		//获取采样点信息
		String sql = "select t2.matcode,t1.mat_type,t2.area_name,t2.plant,t2.description,t2.sample_point_id,t1.sale_data from sale_gq t1,sample_points t2 "
				+ " where t1.sample_point_id=t2.sample_point_id and t1.mat_type=? "
				+ " order by area_name,plant,description";
		request.setAttribute("salePoints", dao.queryListMap(sql, saleType));
		request.setAttribute("saleType", saleType);
		
		return "lims/dataSearch/sale/salePoints";
	}
	
	/**
	 * 获取车间装置对应的检测样品信息
	 * @param request
	 * @return
	 */
	@RequestMapping("/curSale")
	public String curSale(HttpServletRequest request){
		String pointId = request.getParameter("pointId");
		request.setAttribute("points", service.ordNoBySampdate(pointId));
		return "lims/dataSearch/sale/sampleList";
	}
	
}
