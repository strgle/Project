package com.statistics.jbxj.xunjian.busi;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.statistics.jbxj.pubModule.constant.MatType;
import com.statistics.jbxj.xunjian.vo.ReportVo;
import com.statistics.jbxj.xunjian.vo.XjDetailVo;
import pers.czf.dbManager.Dao;
import pers.czf.dbManager.PrimaryKey;
import pers.czf.kit.BigDecimalKit;

@Service
public class XunjianBusi{
	
	@Autowired
	private Dao dao;
	
	public List<Map<String,Object>> queryRemark(String startTime,String endTime,String matType){
		String sql = "select t1.id,t1.remark from JBXJ_XJ_REPORT T1 "
				+ "WHERE t1.mat_type=? "
				+ "and t1.start_time=? and t1.end_time=? ";
		List<Map<String,Object>> remarks = dao.queryListMap(sql,matType,startTime,endTime);
		return remarks;
	}
	
	
	public List<XjDetailVo> queryProcess(String startTime,String endTime){
		this.xjHZ(startTime, endTime,MatType.Process);
		String sql = "select t2.*,t3.matname from JBXJ_XJ_REPORT T1,JBXJ_XJ_DETAIL T2,materials t3 "
				+ "WHERE T1.Id=t2.report_id and t2.matcode=t3.matcode "
				+ "and t1.mat_type=? "
				+ "and t1.start_time=? and t1.end_time=? order by t2.sort";
		List<XjDetailVo> details = dao.queryListObject(sql, XjDetailVo.class,MatType.Process,startTime,endTime);
		return details;
	}
	
	public List<XjDetailVo> queryProduct(String startTime,String endTime,String matCode){

		//获取样品信息
		String sql = "select t2.* from JBXJ_XJ_REPORT T1,JBXJ_XJ_DETAIL T2 "
				+ "WHERE T1.Id=t2.report_id "
				+ "and t1.mat_type=? and t2.matcode=? "
				+ "and t1.start_time=? and t1.end_time=? order by t2.sort";
		List<XjDetailVo> details = dao.queryListObject(sql, XjDetailVo.class,MatType.Product,matCode,startTime,endTime);
		return details;
	}
	
	public void xjHZ(String startTime,String endTime,String matType){
		String sql = "select * from JBXJ_XJ_REPORT where MAT_TYPE=? and START_TIME=? and END_TIME=?";
		List<ReportVo> list = dao.queryListObject(sql,ReportVo.class,matType,startTime,endTime);
		ReportVo report = null;
		
		if(list.isEmpty()) {
			report = new ReportVo();
			report.setId(PrimaryKey.uuid());
			report.setMatType(matType);
			report.setStartTime(startTime);
			report.setEndTime(endTime);
			LocalDateTime ed = LocalDateTime.parse(endTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
			if(ed.plusDays(7).isBefore(LocalDateTime.now())) {
				report.setStatus("1");
			}else {
				report.setStatus("0");
			}
			
			report.setTjTime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
			List<XjDetailVo>  details = new ArrayList<XjDetailVo>();
			if(matType.equals(MatType.Process)) {
				details = this.processTj(report);
			}else if(matType.equals(MatType.Product)){
				//获取统计样品
				String sql1 = "select matcode from JBXJ_DS_MAT WHERE DEPT=?";
				List<String> mats = dao.queryListValue(sql1, String.class,"JBXJ");
				for(String matcode:mats) {
					List<XjDetailVo>  proDetials = this.productTj(report,matcode);
					details.addAll(proDetials);
				}
			}
			
			for(XjDetailVo detail:details) {
				if(detail.getSpecno()!=null) {
					detail.setCharlimits(this.specLimit(detail.getSpecno(), detail.getTestcode(),detail.getAnalyte()));
				}
				
			}
			
			dao.create(report);
			for(XjDetailVo vo:details) {
				dao.create(vo);
			}
		}else{
			report = list.get(0);
			String tjTime = report.getTjTime();
			if(report.getStatus().equals("0")) {
				LocalDateTime tj = LocalDateTime.parse(tjTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
				if(tj.plusHours(1).isBefore(LocalDateTime.now())) {
					LocalDateTime ed = LocalDateTime.parse(endTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
					if(ed.plusDays(7).isBefore(LocalDateTime.now())) {
						report.setStatus("1");
					}
					
					List<XjDetailVo>  details = new ArrayList<XjDetailVo>();
					if(matType.equals(MatType.Process)) {
						details = this.processTj(report);
					}else if(matType.equals(MatType.Product)){
						String sql1 = "select matcode from JBXJ_DS_MAT WHERE DEPT=?";
						List<String> mats = dao.queryListValue(sql1, String.class,"JBXJ");
						for(String matcode:mats) {
							List<XjDetailVo>  proDetials = this.productTj(report,matcode);
							details.addAll(proDetials);
						}
					}
					
					dao.update(report);
					String delSql = "delete from JBXJ_XJ_DETAIL where report_id=?";
					dao.excuteUpdate(delSql, report.getId());
					for(XjDetailVo vo:details) {
						dao.create(vo);
					}
				}
			}
		}
	}
	
	public String specLimit(int specno,int testcode,String analyte) {
		String sql3 = "select higha,highb,highc,lowc,lowb,lowa,charlimits from spec_analytes spec where spec.specno=? and spec.testcode=? and spec.analyte=? ";
		List<Map<String,Object>> list = dao.queryListMap(sql3, specno,testcode,analyte);
		if(list.isEmpty()) {
			return null;
		}else {
			String rs = null;
			Map<String,Object> map = list.get(0);
			String ha = map.get("higha")==null?"":map.get("higha").toString();
			String hb = map.get("highb")==null?"":map.get("highb").toString();
			String lb = map.get("lowb")==null?"":map.get("lowb").toString();
			String la = map.get("lowa")==null?"":map.get("lowa").toString();
			String charlimits = map.get("charlimits")==null?"":map.get("charlimits").toString();
			if(!ha.equals("")&&!la.equals("")) {
				rs = la+" ~ " +ha;
			}else if(ha.equals("")&&!la.equals("")) {
				rs = "≥ "+la;
			}else if(!ha.equals("")&&la.equals("")) {
				rs = "≤ "+ha;
			}else if(!hb.equals("")&&!lb.equals("")) {
				rs = lb+" ~ " +hb;
			}else if(hb.equals("")&&!lb.equals("")) {
				rs = "≥ "+lb;
			}else if(!hb.equals("")&&lb.equals("")) {
				rs = "≤ "+hb;
			}
			
			if(!charlimits.equals("")) {
				rs = charlimits;
			}
			return rs;
		}
	}
	
	private List<XjDetailVo> processTj(ReportVo report) {
		String sql1 = "select * from JBXJ_XJ_MAT where MAT_TYPE=?";
		List<XjDetailVo> details = dao.queryListObject(sql1, XjDetailVo.class, MatType.Process);
		for(XjDetailVo detail:details) {
			detail.setReportId(report.getId());
		}
		String sql2 = "select mat.matcode,mat.testcode,mat.analyte,mat.sort,p.s,count(*) cnum,max(p.specno) specno"
				+ " from JBXJ_XJ_MAT mat, orders o,plantdaily p "
				+ " where MAT_TYPE=? and o.matcode=mat.matcode " 
				+ " and o.Sample_Point_Id=mat.Point_Id and p.ordno=o.ordno and p.testcode=mat.testcode and p.analyte=mat.analyte "
				+ " and o.realsampdate>=to_date(?,'yyyy-MM-dd hh24:mi:ss') and o.realsampdate<to_date(?,'yyyy-MM-dd hh24:mi:ss') " 
				+ " group by mat.matcode,mat.testcode,mat.analyte,mat.sort,p.s order by mat.sort";
		
		List<Map<String,Object>> list2 = dao.queryListMap(sql2, MatType.Process,report.getStartTime(),report.getEndTime());

		for(Map<String,Object> map:list2) {
			String matcode = map.get("matcode").toString();
			int testcode = BigDecimalKit.getInt(map.get("testcode"));
			String analyte = map.get("analyte").toString();
			String s = map.get("s").toString();
			Integer cnum = BigDecimalKit.getInt(map.get("cnum"));
			Integer specno = BigDecimalKit.getInt(map.get("specno"));
			for(XjDetailVo detail:details) {
				if(detail.getMatcode().equals(matcode)&&detail.getTestcode()==testcode&&detail.getAnalyte().equals(analyte)) {
					detail.setSpecno(specno);
					if("Done".equals(s)) {
						detail.setDoneNum(cnum);
					}else if("OOS-B".equals(s)) {
						detail.setOosbNum(cnum);
					}else if("OOS-A".equals(s)) {
						detail.setOosaNum(cnum);
					}
					detail.setTotalNum(detail.getTotalNum()+cnum);
				}
			}
		}
		return details;
	}
	
	private List<XjDetailVo> productTj(ReportVo report,String matCode) {
		
		//获取统计分析项
		String sql = "select testcode,analyte,flag,matcode from jbxj_ds_analyte where matcode=? and is_xj=? order by sort";
		List<XjDetailVo> details = dao.queryListObject(sql, XjDetailVo.class,matCode,"1");
		for(XjDetailVo detail:details) {
			detail.setReportId(report.getId());
			if(detail.getFlag()==null||detail.getFlag().equals("")) {
				detail.setFlag("S");
			}
			if(detail.getFlag().toUpperCase().equals("B")) {
				detail.setArea("成品大批");
			}else {
				detail.setArea("成品小批");
			}
		}
		
		//3、获取检测结果信息
		String sql2 = "select p.status,count(*) cnum,max(o.specno) specno " + 
				"   from JBXJ_DS_ANALYTE mat, ct_orders o,ipcoa_tempvalues p " + 
				"         where o.matcode=mat.matcode and mat.is_xj ='1' and o.status='Released' " + 
				"         and p.ordno=o.ordno and p.testcode=mat.testcode and p.analyte_id=mat.analyte  and mat.testcode=? and mat.analyte=? and mat.matcode=? " + 
				"        and o.sampdate>=to_date(?,'yyyy-MM-dd hh24:mi:ss') and o.sampdate<to_date(?,'yyyy-MM-dd hh24:mi:ss') " + 
				"         and (o.batchno like '1%' or o.batchno like '2%') and o.batchno not like '%24' " + 
				"         group by p.status";
		
		for(XjDetailVo detail:details) {
			List<Map<String,Object>> list2 = dao.queryListMap(sql2,detail.getTestcode(),detail.getAnalyte(),matCode,report.getStartTime(),report.getEndTime());
			for(Map<String,Object> map:list2) {
				String s = map.get("status").toString();
				Integer cnum = BigDecimalKit.getInt(map.get("cnum"));
				Integer specno = BigDecimalKit.getInt(map.get("specno"));
				
				detail.setSpecno(specno);
				if("Done".equals(s)) {
					detail.setDoneNum(cnum);
				}else if("OOS-B".equals(s)) {
					detail.setOosbNum(cnum);
				}else if("OOS-A".equals(s)) {
					detail.setOosaNum(cnum);
				}
				detail.setTotalNum(detail.getTotalNum()+cnum);	
			}
		}
		return details;
	}
}
