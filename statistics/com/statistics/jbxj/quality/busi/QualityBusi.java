package com.statistics.jbxj.quality.busi;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.statistics.jbxj.quality.vo.QualityKeyPoint;
import com.statistics.jbxj.quality.vo.QualityVo;
import pers.czf.dbManager.Dao;
import pers.czf.dbManager.PrimaryKey;
import pers.czf.kit.BigDecimalKit;

@Service
public class QualityBusi {
	
	@Autowired
	private Dao dao;
	
	//统计单个分析项目的检测结果信息
	public void keyPointHz(String area,String startDate,String endDate){
		dao.openTransactional();
		
		try {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
			LocalDateTime edt = LocalDateTime.parse(endDate+" 23:59:59", formatter);
			LocalDateTime cdt = LocalDateTime.now();
			
			//0、插入统计信息
			String reportId = PrimaryKey.uuid();
			String sql0 = " insert into JBXJ_quality_kpreport(id,Area_Name,Start_Date,end_date,tj_date,status) " + 
					" values(?,?,?,?,?,?) ";
			if(cdt.minusDays(3).isBefore(edt)) {
				dao.excuteUpdate(sql0, reportId,area,startDate,endDate,cdt.format(formatter),"0");
			}else {
				dao.excuteUpdate(sql0, reportId,area,startDate,endDate,cdt.format(formatter),"1");
			}
			
			//1、获取需要统计的信息
			String sql = "select t1.mat_type,t2.matcode,t2.point_id,t2.testcode,t2.analyte,t2.sinonym from JBXJ_Quality_mattype t1,JBXJ_Quality_keypoint t2 where t1.area_name=? " + 
					" and t1.id=t2.parent_id order by t1.sort,t2.sort";
			
			List<Map<String,Object>> list = dao.queryListMap(sql,area);
			
			//2、进行信息统计
			String sql2 = "select p.s,count(p.ordno) cnum,max(o.ordno) ordno from orders o,plantdaily p where o.ordno=p.ordno " + 
					"and o.sample_point_id=? and o.status in ('Done','OOS')  and p.s is not null "
					+ " and o.realsampdate>=to_date(?,'yyyy-MM-dd hh24:mi:ss') and o.realsampdate<=to_date(?,'yyyy-MM-dd hh24:mi:ss') " + 
					"and o.matcode=? and p.testcode=? and p.analyte=? group by p.s";
			
			//3、获取指标信息
			String sqlspec = "select s.lowa,s.lowb,s.lowc,s.higha,s.highb,s.highc,s.charlimits from spec_analytes s,orders o " + 
					" where o.specno=s.specno and s.testcode=? and s.analyte=?  " + 
					" and o.ordno=? ";
			
			
			//3、记录统计信息
			String sql3 = "insert into JBXJ_Quality_kpdetail(report_id,point_id,matcode,testcode,analyte,sinonym,charlimts,done_num,total_num,sort,mat_type) " + 
					" values(?,?,?,?,?,?,?,?,?,?,?) ";
			
			int index = 0;
			for(Map<String,Object> map:list) {
				String pointId = map.get("pointId").toString();
				String matcode = map.get("matcode").toString();
				String analyte = map.get("analyte").toString();
				String sinonym = map.get("sinonym").toString();
				String mattype = map.get("matType").toString();
				
				int testcode = BigDecimalKit.getInt(map.get("testcode"));
				List<Map<String,Object>> rlist = dao.queryListMap(sql2, pointId,startDate+" 00:00:00",endDate+" 23:59:59",matcode,testcode,analyte);
				int doneNum = 0;
				int totalNum = 0;
				String specOrdno = null;
				for(Map<String,Object> rmap:rlist) {
					String status = rmap.get("s").toString();
					String ordno = rmap.get("ordno").toString();
					int cnum = BigDecimalKit.getIntOrDefault(rmap.get("cnum"),0);
					totalNum +=cnum;
					
					if("Done".equals(status)) {
						doneNum+=cnum;
					}
					
					if(specOrdno==null) {
						specOrdno = ordno;
					}else if(specOrdno.compareTo(ordno)<0) {
						specOrdno = ordno;
					}
				}
				
				String charlimits = null;
				if(specOrdno!=null) {
					Map<String,Object> spec = dao.queryMap(sqlspec, testcode,analyte,specOrdno);
					if(spec!=null) {
						charlimits = spec.get("charlimits")==null?"":spec.get("charlimits").toString();
					}
				}
				
				dao.excuteUpdate(sql3, reportId,pointId,matcode,testcode,analyte,sinonym,charlimits,doneNum,totalNum,index,mattype);
				index++;
			}
			dao.commit();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			dao.rollback();
			e.printStackTrace();
		}
	}
	
	public QualityKeyPoint keyPoint(String area,String startDate,String endDate) {
		QualityKeyPoint kp = new QualityKeyPoint();
		
		String sql = "select id from JBXJ_quality_kpreport t where t.area_name=? and t.start_date=? and t.end_date=?";
		String id = dao.queryValue(sql, String.class, area,startDate,endDate);
		
		String sql2 = "select t.*,m.matname,s.description from JBXJ_quality_kpdetail t "
				+ " left join materials m on t.matcode=m.matcode " 
				+ " left join sample_points s on t.point_id=s.sample_point_id  where t.report_id=? order by sort";
		List<QualityVo> list = dao.queryListObject(sql2, QualityVo.class, id);
		
		//进行信息分组
		String curMatType = null;
		List<QualityVo> typeList  = null;
		for(QualityVo vo:list) {
			if(!vo.getMatType().equals(curMatType)) {
				curMatType = vo.getMatType();
				typeList = new ArrayList<QualityVo>();
				kp.getMap().put(curMatType, typeList);
			}
			typeList.add(vo);
		}
		return kp;
	}
	
	//统计单个分析项目的检测结果信息
	public void keyLimitHz(String area,String startDate,String endDate){
			dao.openTransactional();
			
			try {
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
				LocalDateTime edt = LocalDateTime.parse(endDate+" 23:59:59", formatter);
				LocalDateTime cdt = LocalDateTime.now();
				
				//0、插入统计信息
				String reportId = PrimaryKey.uuid();
				String sql0 = " insert into JBXJ_quality_klreport(id,Area_Name,Start_Date,end_date,tj_date,status) " + 
						" values(?,?,?,?,?,?) ";
				if(cdt.minusDays(3).isBefore(edt)) {
					dao.excuteUpdate(sql0, reportId,area,startDate,endDate,cdt.format(formatter),"0");
				}else {
					dao.excuteUpdate(sql0, reportId,area,startDate,endDate,cdt.format(formatter),"1");
				}
				
				//1、获取需要统计的信息
				String sql = "select t2.matcode,t2.testcode,t2.analyte,t2.sinonym,t1.mat_type " + 
						" from JBXJ_DS_MAT t1,JBXJ_DS_ANALYTE t2 "
						+ " where t1.matcode=t2.matcode and t2.is_monitor='1' and t1.dept=?"
						+ " order by t1.sort,t2.sort";
				
				List<Map<String,Object>> list = dao.queryListMap(sql,"JBXJ");
				
				//2、进行信息统计
				String sql2 = "select p.status,count(p.ordno) cnum,max(o.ordno) ordno from CT_ORDERS o,IPCOA_TEMPVALUES p where o.ordno=p.ordno " + 
						" and o.matcode=? and o.batchno like ?  and o.batchno not like '%24'  and length(o.batchno)=10  " + 
						" and to_char(o.sampdate,'yyyy-MM-dd hh24:mi:ss')>=? and to_char(o.sampdate,'yyyy-MM-dd hh24:mi:ss')<=? and o.status='Released' " + 
						" and p.testcode=? and p.analyte_id=? and p.status in ('Done','OOS-A','OOS-B','OOS-C') group by p.status";
				
				//3、获取指标信息
				String sqlspec = "select s.lowa,s.lowb,s.lowc,s.higha,s.highb,s.highc,s.charlimits from spec_analytes s,orders o " + 
						" where o.specno=s.specno and s.testcode=? and s.analyte=?  " + 
						" and o.ordno=? ";

				//3、记录统计信息
				String sql3 = "insert into JBXJ_Quality_kldetail(report_id,matcode,testcode,analyte,sinonym,charlimts,done_num,total_num,sort,mat_type,abline) " + 
						" values(?,?,?,?,?,?,?,?,?,?,?) ";
				
				
				String[] abLine = new String[] {"1","2"};
				for(String preLine:abLine) {
					int index = 0;
					for(Map<String,Object> map:list) {
						String matcode = map.get("matcode").toString();
						String matType = map.get("matType").toString();
						String analyte = map.get("analyte").toString();
						String sinonym = map.get("sinonym").toString();
						int testcode = BigDecimalKit.getInt(map.get("testcode"));
						String batchNo = preLine+"%";
						List<Map<String,Object>> rlist = dao.queryListMap(sql2, matcode,batchNo,startDate+" 00:00:00",endDate+" 23:59:59",testcode,analyte);
						int doneNum = 0;
						int totalNum = 0;
						String specOrdno = null;
						for(Map<String,Object> rmap:rlist) {
							String status = rmap.get("status").toString();
							String ordno = rmap.get("ordno").toString();
							int cnum = BigDecimalKit.getIntOrDefault(rmap.get("cnum"),0);
							totalNum +=cnum;
							
							if("Done".equals(status)) {
								doneNum+=cnum;
							}
							
							if(specOrdno==null) {
								specOrdno = ordno;
							}else if(specOrdno.compareTo(ordno)<0) {
								specOrdno = ordno;
							}
						}
						
						String charlimits = null;
						if(specOrdno!=null) {
							Map<String,Object> spec = dao.queryMap(sqlspec, testcode,analyte,specOrdno);
							if(spec!=null) {
								charlimits = spec.get("charlimits")==null?"":spec.get("charlimits").toString();
							}
						}
						dao.excuteUpdate(sql3, reportId,matcode,testcode,analyte,sinonym,charlimits,doneNum,totalNum,index,matType,preLine);
						index++;
					}
				}
				
				dao.commit();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				dao.rollback();
				e.printStackTrace();
			}
		}
		
		public QualityKeyPoint keyLimit(String area,String startDate,String endDate) {
			QualityKeyPoint kp = new QualityKeyPoint();
			
			String sql = "select id from JBXJ_quality_klreport t where t.area_name=? and t.start_date=? and t.end_date=?";
			String id = dao.queryValue(sql, String.class, area,startDate,endDate);
			
			String sql2 = "select t.* from JBXJ_quality_kldetail t "
					+ " where t.report_id=? order by sort,abline";
			List<QualityVo> list = dao.queryListObject(sql2, QualityVo.class, id);
			
			//进行信息分组
			String curMatType = null;
			List<QualityVo> typeList  = null;
			for(QualityVo vo:list) {
				if(!vo.getMatType().equals(curMatType)) {
					curMatType = vo.getMatType();
					typeList = new ArrayList<QualityVo>();
					kp.getMap().put(curMatType, typeList);
				}
				typeList.add(vo);
			}
			return kp;
		}
}
