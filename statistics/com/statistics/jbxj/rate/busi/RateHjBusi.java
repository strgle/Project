package com.statistics.jbxj.rate.busi;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.statistics.jbxj.pubModule.constant.MatType;
import com.statistics.jbxj.pubModule.constant.ReportType;
import com.statistics.jbxj.pubModule.handler.Working;
import com.statistics.jbxj.rate.vo.MonitorDetail;
import com.statistics.jbxj.rate.vo.RateBrand;
import com.statistics.jbxj.rate.vo.RateDetail;
import com.statistics.jbxj.rate.vo.RateReport;
import com.statistics.jbxj.rate.vo.ReportModel;
import pers.czf.dbManager.Dao;
import pers.czf.dbManager.PrimaryKey;
import pers.czf.kit.BigDecimalKit;

@Service
public class RateHjBusi{
	
	@Autowired
	private Dao dao;
	
	/**
	 * 成品统计(月统计)
	 */
	public void productMonthHZ(int year,int month,String matCode){
		//获取月度的开始结束时间
		String[] monthSE = Working.monthStartAndEndDay(year, month);
				
		String sql1 = "select * from JBXJ_RATE_report where report_type=? and year=? and month=? and mat_type=? and matcode=?";
		List<RateReport> reportList = dao.queryListObject(sql1,RateReport.class,ReportType.MONTH,year,month,MatType.Product,matCode);
		
		if(reportList.isEmpty()) {
			//统计信息
			RateReport report = new RateReport();
			report.setId(PrimaryKey.uuid());
			report.setReportType(ReportType.MONTH);
			report.setStartTime(monthSE[0]);
			report.setEndTime(monthSE[1]);
			report.setMatcode(matCode);
			report.setMatType(MatType.Product);
			report.setYear(year);
			report.setMonth(month);
			report.setTjDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
			LocalDate enddate = LocalDate.parse(monthSE[1], DateTimeFormatter.ofPattern("yyyy-MM-dd"));
			if(enddate.isBefore(LocalDate.now().minusDays(7))) {
				report.setStatus("1");
			}else {
				report.setStatus("0");
			}

			this.hz(report);
		}else {
			RateReport report = reportList.get(0);
			String status = report.getStatus();
			if("0".equals(status)) {
				//重新统计
				this.reHZ(report);
			}
		}			
	}
	
	/**
	 * 查询产品统计信息
	 * @param startDate
	 * @param endDate
	 * @param rpeortType
	 * @return
	 */
	public List<MonitorDetail>  queryProcessReport(String startDate,String endDate,String reportType) {
		
		RateReport report = this.checkProcessReport(startDate, endDate, reportType);
		if(report.getId()==null||report.getId().equals("")) {
			report.setId(PrimaryKey.uuid());
			this.processMHz(report);
		}else{
			//根据状态判断是否需要重新统计
			if(!"1".equals(report.getStatus())) {
				String del1 = "delete  from JBXJ_PROCESS_MONITOR_DETAIL where report_id = ? ";
				dao.excuteUpdate(del1, report.getId());
				
				String del2 = "delete  from JBXJ_PROCESS_report where id = ? ";
				dao.excuteUpdate(del2, report.getId());
				
				this.processMHz(report);
			}
			
		}
				
		/**
		* 2、获取监控项统计
		*/
		String sql2 = "select t2.*,t3.matname from JBXJ_PROCESS_report t1,JBXJ_PROCESS_MONITOR_DETAIL t2,materials t3 " + 
				"   where t1.id=t2.report_id and t2.matcode=t3.matcode and t1.report_type=? " + 
				"   and t1.start_time=? and t1.end_time=? order by t2.sort";
				
		List<MonitorDetail> monitorDetails = dao.queryListObject(sql2, MonitorDetail.class, reportType,startDate,endDate);
		
		return monitorDetails;
	}
	
	/**
	 * 查询产品日统计信息
	 * @param startDate
	 * @param endDate
	 * @param rpeortType
	 * @return
	 */
	public List<RateReport>  queryDayReport(String startDate,String endDate,String reportType) {
		
		//获取需要统计的样品信息
		String sql0 = "select T1.matcode,T2.MATNAME from JBXJ_DS_MAT T1,MATERIALS T2 WHERE T1.MATCODE=T2.MATCODE and t1.dept=? order by sort";
		List<Map<String,Object>> mates = dao.queryListMap(sql0,"HUAJU");

		/**
		* 1、获取牌号统计
		*/
		synchronized(this) {
			for(Map<String,Object> map:mates ) {
				String matCode = map.get("matcode").toString();
				RateReport report = this.checkReport(startDate, endDate, reportType, matCode);				
				if(report.getId()==null||report.getId().equals("")) {
					report.setId(PrimaryKey.uuid());
					this.hz(report);
				}else{
					if(!"1".equals(report.getStatus())) {
						this.reHZ(report);
					}
				}
			}
		}
				
		List<RateReport> reportDatas = new ArrayList<RateReport>();
		for(Map<String,Object> map:mates) {
			String matCode = map.get("matcode").toString();
			String matName = map.get("matname").toString();

			RateReport report = new RateReport();
			report.setMatcode(matCode);
			report.setMatName(matName);
					
			//查询数据
			String sql1 = "select t1.year,t1.month,t2.* from JBXJ_RATE_report t1,JBXJ_RATE_DETAIL t2 " + 
									" where t1.id=t2.report_id and t1.report_type=? and t1.mat_type=? and t1.start_time=? and t1.end_time=? and t1.matcode=? "
									+ " and t2.grade not like '%RATE' and t2.grade <> 'TOTAL_NUM'";
					
			//获取合格数、优等品数、不合格数、回切数
			List<RateDetail> datas = dao.queryListObject(sql1, RateDetail.class,reportType,MatType.HjProduct,startDate,endDate,matCode);
			report.setRateDetail(datas);
			for(RateDetail detail:datas) {
				String grade = detail.getGrade();
				switch(grade){
					case "Done":
						report.setDone(report.getDone()+detail.getTotalNum().intValue());
						report.setTotalNum(report.getTotalNum()+detail.getTotalNum().intValue());
						break;
					case "OOS-B":
						report.setOosB(report.getOosB()+detail.getTotalNum().intValue());
						report.setTotalNum(report.getTotalNum()+detail.getTotalNum().intValue());
						break;
					case "OOS-A":
						report.setOosA(report.getOosA()+detail.getTotalNum().intValue());
						report.setTotalNum(report.getTotalNum()+detail.getTotalNum().intValue());
						break;
					case "ROLL_BACK":
						report.setRollBack(detail);
						break;
					case "ROLL_BACK_RATE":
						report.setRollBackRate(detail);
						break;	
					default:
						break;
				}
			}

			reportDatas.add(report);
		}
		
		return reportDatas;
	}
	
	/**
	 * 查询产品统计信息
	 * @param startDate
	 * @param endDate
	 * @param rpeortType
	 * @return
	 */
	public List<ReportModel>  queryReport(String startDate,String endDate,String reportType) {
		
		//获取需要统计的样品信息
		String sql0 = "select T1.matcode,T2.MATNAME from JBXJ_DS_MAT T1,MATERIALS T2 WHERE T1.MATCODE=T2.MATCODE and t1.dept=? order by sort";
		List<Map<String,Object>> mates = dao.queryListMap(sql0,"HUAJU");

		/**
		* 1、获取牌号统计
		*/
		synchronized(this) {
			for(Map<String,Object> map:mates ) {
				String matCode = map.get("matcode").toString();
				RateReport report = this.checkReport(startDate, endDate, reportType, matCode);
				if(report.getId()==null||report.getId().equals("")) {
					report.setId(PrimaryKey.uuid());
					this.hz(report);
					if(!reportType.equals(ReportType.DAY)) {
						this.monitorHz(report);
					}
				}else{
					if(!"1".equals(report.getStatus())) {
						this.reHZ(report);
						if(!reportType.equals(ReportType.DAY)) {
							String del = "delete from JBXJ_MONITOR_DETAIL where report_id=?";
							dao.excuteUpdate(del, report.getId());
							this.monitorHz(report);
						}
					}
				}
			}
		}
				
		List<ReportModel> reportDatas = new ArrayList<ReportModel>();
		for(Map<String,Object> map:mates) {
			String matCode = map.get("matcode").toString();
			String matName = map.get("matname").toString();
					
			ReportModel rm = new ReportModel();
			rm.setMatName(matName);
					
			//查询数据
			String sql1 = "select t1.year,t1.month,t2.* from JBXJ_RATE_report t1,JBXJ_RATE_DETAIL t2 " + 
									" where t1.id=t2.report_id and t1.report_type=? and t1.mat_type=? and  t1.start_time=? and t1.end_time=? and t1.matcode=?";
					
			//获取合格数、优等品数、不合格数、总数、回切数
			List<RateDetail> datas = dao.queryListObject(sql1, RateDetail.class,reportType,MatType.HjProduct,startDate,endDate,matCode);
			RateReport report = new RateReport();
			report.setMatcode(matCode);
			for(RateDetail detail:datas) {
				String grade = detail.getGrade();
				switch(grade){
					case "Done":
						report.setDone(report.getDone()+detail.getTotalNum().intValue());
						report.setTotalNum(report.getTotalNum()+detail.getTotalNum().intValue());
						break;
					case "OOS-B":
						report.setOosB(report.getOosB()+detail.getTotalNum().intValue());
						report.setTotalNum(report.getTotalNum()+detail.getTotalNum().intValue());
						break;
					case "OOS-A":
						report.setOosA(report.getOosA()+detail.getTotalNum().intValue());
						report.setTotalNum(report.getTotalNum()+detail.getTotalNum().intValue());
						break;
					case "ROLL_BACK":
						report.setRollBack(detail);
						break;
					case "ROLL_BACK_RATE":
						report.setRollBackRate(detail);
						break;	
					default:
						break;
				}
			}
					
			rm.setProduct(report);
					
			/**
			* 2、获取监控项统计
			*/
			String sql2 = "select t2.* from JBXJ_RATE_report t1,JBXJ_MONITOR_DETAIL t2 " + 
							" where t1.id=t2.report_id and t1.report_type=? and t1.mat_type=? " + 
							" and t1.matcode=? and t1.start_time=? and t1.end_time=? order by t2.sort ";
					
			List<MonitorDetail> monitorDetails = dao.queryListObject(sql2, MonitorDetail.class, reportType,MatType.HjProduct,matCode,startDate,endDate);
			rm.setMonitorDetails(monitorDetails);
			
			
			reportDatas.add(rm);
		}
		
		return reportDatas;
	}
	
	/**
	 * 判断统计信息是否存在
	 * @return
	 */
	public RateReport checkReport(String startDate,String endDate,String reportType,String matCode) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate sd = LocalDate.parse(startDate.substring(0, 10), formatter);
		int year = sd.getYear();
		
		//判断统计信息是否存在
		String sql1 = "select * from JBXJ_RATE_report where report_type=? and start_time=? and end_time=? and mat_type=? and matcode=?";
		List<RateReport> reportList = dao.queryListObject(sql1,RateReport.class, reportType,startDate,endDate,MatType.HjProduct,matCode);
		if(reportList.isEmpty()) {
			RateReport report = new RateReport();
			report.setReportType(reportType);
			report.setStartTime(startDate);
			report.setEndTime(endDate);
			report.setMatcode(matCode);
			report.setMatType(MatType.HjProduct);
			report.setYear(year);
			report.setTjDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
			return report;
		}else {
			RateReport report = reportList.get(0);
			return report;
		}
	}
	
	/**
	 * 判断统计信息是否存在
	 * @return
	 */
	public RateReport checkProcessReport(String startDate,String endDate,String reportType) {
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate  sd = LocalDate.parse(startDate, formatter);
		int year = sd.getYear();
		
		//判断统计信息是否存在
		String sql1 = "select * from JBXJ_PROCESS_report where report_type=? and start_time=? and end_time=?";
		List<RateReport> reportList = dao.queryListObject(sql1,RateReport.class, reportType,startDate,endDate);
		if(reportList.isEmpty()) {
			RateReport report = new RateReport();
			report.setReportType(reportType);
			report.setStartTime(startDate);
			report.setEndTime(endDate);
			report.setMatType(MatType.Huaju);
			report.setYear(year);
			report.setTjDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
			return report;
		}else {
			RateReport report = reportList.get(0);
			return report;
		}
	}
	
	private void hz(RateReport report) {
		try {
			dao.openTransactional();
			
			//获取需要统计的牌号
			List<RateBrand> brands = this.brand(report.getYear(), report.getMatcode());
			
			String sql2 = "select brand,count(*) cnum from ct_orders ct where ct.sampdate>=to_date(?,'yyyy-MM-dd hh24:mi:ss') and ct.sampdate<=to_date(?,'yyyy-MM-dd hh24:mi:ss') "
					+ " and matcode=? and status='Released' and ct.batchno not like '%24' "
					+ " group by brand";
			List<Map<String,Object>> list  = new ArrayList<Map<String,Object>>();
			if(report.getReportType().equals(ReportType.DAY)) {
				sql2 = "select brand,count(*) cnum from ct_orders ct where ct.sampdate>=to_date(?,'yyyy-MM-dd hh24:mi:ss') and ct.sampdate<to_date(?,'yyyy-MM-dd hh24:mi:ss') "
						+ " and matcode=? and status='Released' and ct.batchno not like '%24'"
						+ " group by brand";
				list = dao.queryListMap(sql2, report.getStartTime(),report.getEndTime(),report.getMatcode());
			}else {
				list = dao.queryListMap(sql2, report.getStartTime()+" 00:00:00",report.getEndTime()+" 23:59:59",report.getMatcode());
			}

			List<RateDetail> details = new ArrayList<RateDetail>();
			for(RateBrand brand:brands) {
				RateDetail detail = new RateDetail();
				detail.setId(PrimaryKey.uuid());
				detail.setReportId(report.getId());
				detail.setBrand(brand.getBrand());
				detail.setGrade(brand.getGrade());
				detail.setTotalNum(0.0f);
				detail.setDataSource(brand.getDataSource());
				for(Map<String,Object> map:list) {
					String b = map.get("brand")==null?"":map.get("brand").toString();
					if(b.equals(detail.getBrand())) {
						Integer num = BigDecimalKit.getInt(map.get("cnum"));
						detail.setTotalNum(num.floatValue());
						if(detail.getGrade().equals("Done")){
							report.setDone(report.getDone()+num);
						}
						
						if(detail.getGrade().equals("OOS-B")){
							report.setOosB(report.getOosB()+num);
						}
						report.setTotalNum(report.getTotalNum()+detail.getTotalNum().intValue());
						break;
					}
				}
				details.add(detail);
			}
			
			//处理总数据、合格率、优等率
			for(RateDetail detail:details) {
				if("TOTAL_NUM".equals(detail.getGrade())) {
					detail.setTotalNum(new Float(report.getTotalNum()));
				}
				
				if("Done_RATE".equals(detail.getGrade())) {
					if(report.getTotalNum()!=0) {
						detail.setTotalNum(report.getDone()*100.0f/report.getTotalNum());
					}else {
						detail.setTotalNum(0f);
					}
					
				}
				
				if("OOS-B_RATE".equals(detail.getGrade())) {
					if(report.getTotalNum()!=0) {
						detail.setTotalNum((report.getOosB()+report.getDone())*100.0f/report.getTotalNum());
					}else {
						detail.setTotalNum(0f);
					}
				}
				
				if("OOS-B_NUM".equals(detail.getGrade())) {
					detail.setTotalNum(new Float(report.getOosB()));
				}
				
			}
			
			LocalDate enddate = LocalDate.parse(report.getEndTime().substring(0, 10), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
			if(enddate.isBefore(LocalDate.now().minusDays(7))) {
				report.setStatus("1");
			}else {
				report.setStatus("0");
			}
			
			dao.create(report);
			for(RateDetail detail:details) {
				dao.create(detail);
			}
			dao.commit();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			dao.rollback();
			e.printStackTrace();
		}finally {
			
		}
	}
	
	/**
	 * 成品统计
	 */
	private void reHZ(RateReport report){
		try {
			dao.openTransactional();
			
			//获取需要统计的牌号
			List<RateBrand> brands = this.brand(report.getYear(), report.getMatcode());
			
			//统计总数设置为
			report.setTotalNum(0);
	
			report.setTjDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
			
			LocalDate enddate = LocalDate.parse(report.getEndTime().substring(0, 10), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
			
			if(enddate.isBefore(LocalDate.now().minusDays(7))) {
				report.setStatus("1");
			}else {
				report.setStatus("0");
			}
			
			List<RateDetail> details = new ArrayList<RateDetail>();
			
			List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
			
			String sql2 = "select brand,count(*) cnum from ct_orders ct where ct.sampdate>=to_date(?,'yyyy-MM-dd hh24:mi:ss') and ct.sampdate<=to_date(?,'yyyy-MM-dd hh24:mi:ss') "
					+ " and matcode=? and status='Released' "
					+ " and ct.batchno not like '%24' group by brand";
			
			if(report.getReportType().equals(ReportType.DAY)) {
				sql2 = "select brand,count(*) cnum from ct_orders ct where ct.sampdate>=to_date(?,'yyyy-MM-dd hh24:mi:ss') and ct.sampdate<to_date(?,'yyyy-MM-dd hh24:mi:ss') "
						+ " and matcode=? and status='Released' and ct.batchno not like '%24'"
						+ " group by brand";
				list = dao.queryListMap(sql2, report.getStartTime(),report.getEndTime(),report.getMatcode());
			}else {
				list = dao.queryListMap(sql2, report.getStartTime()+" 00:00:00",report.getEndTime()+" 23:59:59",report.getMatcode());
			}
			
			for(RateBrand brand:brands) {
				RateDetail detail = new RateDetail();
				detail.setReportId(report.getId());
				detail.setBrand(brand.getBrand());
				detail.setGrade(brand.getGrade());
				int totalNum = 0;
				for(Map<String,Object> map:list) {
					String b = map.get("brand")==null?"":map.get("brand").toString();
					if(b.equals(brand.getBrand())) {
						totalNum = BigDecimalKit.getInt(map.get("cnum"));
						dao.excuteUpdate("update JBXJ_RATE_detail set total_num=? where report_id=? and brand=? ", totalNum,report.getId(),brand.getBrand());
					
						if(brand.getGrade().equals("Done")){
							report.setDone(report.getDone()+totalNum);
						}
						
						if(brand.getGrade().equals("OOS-B")){
							report.setOosB(report.getOosB()+totalNum);
						}
						report.setTotalNum(report.getTotalNum()+totalNum);
						break;
					}
				}
				
			}

			//处理总数据、合格率、优等率
			for(RateDetail detail:details) {
				if("TOTAL_NUM".equals(detail.getGrade())) {
					detail.setTotalNum(new Float(report.getTotalNum()));
					dao.excuteUpdate("update JBXJ_RATE_detail set total_num=? where report_id=? and brand=? ", detail.getTotalNum(),report.getId(),detail.getBrand());
				}
				
				if("Done_RATE".equals(detail.getGrade())) {
					detail.setTotalNum(report.getDone()*100.0f/report.getTotalNum());
					dao.excuteUpdate("update JBXJ_RATE_detail set total_num=? where report_id=? and brand=? ", detail.getTotalNum(),report.getId(),detail.getBrand());
				}
				
				if("OOS-B_RATE".equals(detail.getGrade())) {
					detail.setTotalNum((report.getDone()+report.getOosB())*100.0f/report.getTotalNum());
					dao.excuteUpdate("update JBXJ_RATE_detail set total_num=? where report_id=? and brand=? ", detail.getTotalNum(),report.getId(),detail.getBrand());
				}
				
				if("OOS-B_NUM".equals(detail.getGrade())) {
					detail.setTotalNum(new Float(report.getOosB()));
					dao.excuteUpdate("update JBXJ_RATE_detail set total_num=? where report_id=? and brand=? ", detail.getTotalNum(),report.getId(),detail.getBrand());
				}
			}
			dao.update(report);
			dao.commit();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			dao.rollback();
		}finally {
			
		}		
	}
	
	private void monitorHz(RateReport report) {
		//获取所以的监控信息
		List<MonitorDetail> details = new ArrayList<MonitorDetail>();

		String sql0 = "select * from JBXJ_DS_ANALYTE where matcode=? and is_xj='1' order  by sort";
		List<Map<String,Object>> list = dao.queryListMap(sql0, report.getMatcode());
		for(Map<String,Object> map:list) {
			MonitorDetail detail = new MonitorDetail();
			Integer testcode = BigDecimalKit.getInt(map.get("testcode"));
			String analyte = map.get("analyte").toString();
			String sinonym = map.get("sinonym").toString();
			detail.setId(PrimaryKey.uuid());
			detail.setReportId(report.getId());
			detail.setMatcode(report.getMatcode());
			detail.setTestcode(testcode);
			detail.setAnalyte(analyte);
			detail.setSinonym(sinonym);
			detail.setSort(BigDecimalKit.getInt(map.get("sort")));
			details.add(detail);			
		}
		
		//获取巡检的检测项目信息
		String sql = "select ip.testcode,ip.analyte_id analyte,a.sort,ip.status,max(a.sinonym) sinonym,count(*) num from ipcoa_tempvalues ip,ct_orders ct,JBXJ_DS_ANALYTE a " + 
						"where a.matcode=? and ct.matcode=? and a.is_xj='1' " + 
						"and ip.ordno=ct.ordno and ct.sampdate>=to_date(?,'yyyy-MM-dd hh24:mi:ss') and ct.sampdate<=to_date(?,'yyyy-MM-dd hh24:mi:ss') " + 
						"and ip.testcode=a.testcode and ip.analyte_id=a.analyte " + 
						"and ct.batchno not like '%24' and length(ct.batchno)=9 " + 
						"and ct.status='Released' group by ip.testcode,ip.analyte_id,a.sort,ip.status order by ip.testcode,ip.analyte_id";
				
		List<Map<String,Object>> monitor = dao.queryListMap(sql, report.getMatcode(),report.getMatcode(),report.getStartTime()+" 00:00:00",report.getEndTime()+" 23:59:59");
		
	
		for(Map<String,Object> map:monitor) {
			Integer testcode = BigDecimalKit.getInt(map.get("testcode"));
			String analyte = map.get("analyte").toString();
			
			MonitorDetail detail = null;
			
			for(MonitorDetail vo:details) {
				if(vo.getTestcode().intValue()==testcode.intValue()&&vo.getAnalyte().equals(analyte)) {
					detail = vo;
				}
			}
			
			String status = map.get("status")==null?"Done":map.get("status").toString();
			Integer num = BigDecimalKit.getInt(map.get("num"));
			switch(status) {
				case "Done":
					detail.setDoneNum(detail.getDoneNum()+num);
					detail.setTotalNum(detail.getTotalNum()+num);
					break;
				case "OOS-A":
					detail.setOosaNum(detail.getOosaNum()+num);
					detail.setTotalNum(detail.getTotalNum()+num);
					break;
				case "OOS-B":
					detail.setOosbNum(detail.getOosbNum()+num);
					detail.setTotalNum(detail.getTotalNum()+num);
					break;
				default:
					break;
			}
		}
		
		for(MonitorDetail vo:details) {
			dao.create(vo);
		}
	}
	
	private void processMHz(RateReport report) {
		//获取所有的监控信息
		
		String sql1 = "select * from JBXJ_XJ_MAT order by sort";
		List<MonitorDetail> details = dao.queryListObject(sql1, MonitorDetail.class);
		
		for(MonitorDetail detail:details) {
			detail.setId(PrimaryKey.uuid());
			detail.setReportId(report.getId());
		}
		
		String sql2 = "select mat.area,mat.matcode,mat.testcode,mat.analyte,mat.sort,p.s,count(*) cnum "
				+ " from JBXJ_XJ_MAT mat, orders o,plantdaily p "
				+ " where o.matcode=mat.matcode and mat.mat_type=? " 
				+ " and o.area_name=mat.area and p.ordno=o.ordno and p.testcode=mat.testcode and p.analyte=mat.analyte "
				+ " and o.realsampdate>=to_date(?,'yyyy-MM-dd hh24:mi:ss') and o.realsampdate<to_date(?,'yyyy-MM-dd hh24:mi:ss') " 
				+ " group by mat.area,mat.matcode,mat.testcode,mat.analyte,mat.sort,p.s order by mat.sort";
		
		List<Map<String,Object>> list2 = dao.queryListMap(sql2, MatType.Process,report.getStartTime(),report.getEndTime());

		for(Map<String,Object> map:list2) {
			String matcode = map.get("matcode").toString();
			int testcode = BigDecimalKit.getInt(map.get("testcode"));
			String analyte = map.get("analyte").toString();
			String s = map.get("s").toString();
			int cnum = BigDecimalKit.getInt(map.get("cnum"));
			for(MonitorDetail detail:details) {
				if(detail.getMatcode().equals(matcode)&&detail.getTestcode()==testcode&&detail.getAnalyte().equals(analyte)) {
					if("Done".equals(s)) {
						detail.setDoneNum(cnum);
					}else if("OOS-B".equals(s)) {
						detail.setOosbNum(cnum);
					}else if("OOS-A".equals(s)) {
						detail.setOosaNum(cnum);
					}
					detail.setTotalNum(detail.getTotalNum()+cnum);
					break;
				}
			}
			
		}
		
		for(MonitorDetail vo:details) {
			dao.createTable(vo, "JBXJ_PROCESS_MONITOR_DETAIL");
		}
		
		//判断report的状态
		LocalDate enddate = LocalDate.parse(report.getEndTime(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		if(enddate.isBefore(LocalDate.now().minusDays(7))) {
			report.setStatus("1");
		}else {
			report.setStatus("0");
		}
		dao.createTable(report,"JBXJ_PROCESS_report");
	}
	
	private List<RateBrand> brand(int year,String matCode){
		String sql1 = "select * from JBXJ_RATE_brand where mat_type=? and year=? and matcode=? order by sort";
		List<RateBrand> list = dao.queryListObject(sql1,RateBrand.class,MatType.HjProduct,year,matCode);
		
		if(list.isEmpty()) {
			String sql2 = "select max(year) year from JBXJ_RATE_brand where mat_type=? and matcode=?";
			Integer maxYear = dao.queryValue(sql2, Integer.class, MatType.HjProduct,matCode);
			if(maxYear != null) {
				String sql3 = "select * from JBXJ_RATE_brand where year=? and mat_type=? and matcode=?";
				list = dao.queryListObject(sql3, RateBrand.class, maxYear,MatType.HjProduct,matCode);
				for(RateBrand brand:list) {
					brand.setId(PrimaryKey.uuid());
					brand.setYear(year);
					dao.create(brand);
				}
			}else {
				throw new RuntimeException("没有维护该产品的统计牌号信息，请先进行统计信息维护。");
			}
		}
		
		return list;
	}
	
	
}
