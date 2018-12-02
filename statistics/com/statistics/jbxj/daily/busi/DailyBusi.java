package com.statistics.jbxj.daily.busi;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.statistics.jbxj.daily.handler.BatchNoHandler;
import com.statistics.jbxj.daily.handler.DailyWrap;
import com.statistics.jbxj.daily.handler.PreParameter;
import com.statistics.jbxj.daily.vo.DailyDetailVo;
import com.statistics.jbxj.daily.vo.DailyHz;
import com.statistics.jbxj.daily.vo.DailyVo;
import pers.czf.dbManager.Dao;
import pers.czf.dbManager.PrimaryKey;
import pers.czf.kit.BigDecimalKit;

@Service
public class DailyBusi{
	
	@Autowired
	private Dao dao;
	
	/**
	 * 每班组检测信息统计
	 * startTime上班时间，endTime下班时间
	 */
	public void dailyHZ(String matcode,String startTime,String endTime){
		dao.openTransactional();
		try {
			/*
			 * 1、获取当前时间
			 */
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
			LocalDateTime dateTime = LocalDateTime.now();
			
			/*
			 * 2、判断统计信息是否存在或者是否需要重新统计
			 */
			String sql = "select status,tj_date,id from JBXJ_DS_report where matcode=? and start_date=? and end_date=?";
			Map<String,Object> report = dao.queryMap(sql,matcode,startTime,endTime);
			
			if(report==null) {
				//添加统计
				String reportId = this.insertDsReprot(matcode, startTime, endTime);
				this.hz(matcode, startTime, endTime,reportId);
			}else{
				if(report.get("status").toString().equals("0")) {
					String tjDate = report.get("tjDate").toString();
					LocalDateTime tjDt = LocalDateTime.parse(tjDate, formatter);
					if(dateTime.minusMinutes(60).isAfter(tjDt)) {
						String reportId = report.get("id").toString();
						this.updateDsReport(endTime, reportId);
						this.hz(matcode, startTime, endTime,reportId);
					}
				}
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
	 * 添加统计信息
	 * @param matcode
	 * @param startTime
	 * @param endTime
	 */
	private String insertDsReprot(String matcode,String startTime,String endTime) {
		
		DateTimeFormatter teamTimeformatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		LocalDateTime endDT = LocalDateTime.parse(endTime, teamTimeformatter);
			
		//记录统计的分析项目
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		LocalDateTime dateTime = LocalDateTime.now();
		String curTime = dateTime.format(formatter);
		String insertDailyReport = "insert into JBXJ_DS_report(id,start_date,end_date,matcode,tj_date,status) values(?,?,?,?,?,?)";
		String reportId = PrimaryKey.uuid();
		
		//如果统计时间超过下班时间3天，则表明全部样已经完成了
		if(dateTime.minusDays(3).isAfter(endDT)) {
			dao.excuteUpdate(insertDailyReport,reportId,startTime,endTime,matcode,curTime,"1");
		}else {
			dao.excuteUpdate(insertDailyReport,reportId,startTime,endTime,matcode,curTime,"0");
		}
		
		//要统计的分析项目
		String sql = "select * from JBXJ_DS_ANALYTE where MATCODE = ? and status = ? order by sort ";
		List<Map<String,Object>> analytes = dao.queryListMap(sql, matcode,"1");
		
		String insertSql = "insert into JBXJ_DS_ANALYTE_report(report_id,matcode,testcode,analyte,sinonym) values(?,?,?,?,?)";
		for(Map<String,Object> map:analytes){
			dao.excuteUpdate(insertSql, reportId,matcode,map.get("testcode"),map.get("analyte"),map.get("sinonym"));
		}
		
		return reportId;
	}
	
	/**
	 * 更新统计信息
	 * @param endTime
	 * @param id
	 */
	private void updateDsReport(String endTime,String id) {
		DateTimeFormatter teamTimeformatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		LocalDateTime endDt = LocalDateTime.parse(endTime, teamTimeformatter);
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		LocalDateTime dateTime = LocalDateTime.now();
		String curTime = dateTime.format(formatter);
		
		if(dateTime.minusHours(96).isAfter(endDt)) {
			dao.excuteUpdate("update JBXJ_DS_report set status=?,TJ_DATE=? where id=? ","1",curTime,id);
		}else {
			dao.excuteUpdate("update JBXJ_DS_report set status=?,TJ_DATE=? where id=? ","0",curTime,id);
		}
	}
	
	/**
	 * 班组结果监测结果汇总
	 * @param matcode
	 * @param startTime
	 * @param endTime
	 */
	private void hz(String matcode,String startTime,String endTime,String reportId) {
		/*
		 * 1、获取当前时间
		 */
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		LocalDateTime dateTime = LocalDateTime.now();
		String curTime = dateTime.format(formatter);
		
		//1、获取批号信息
		List<String> batchNoArray = BatchNoHandler.createBatchNo(startTime, endTime);
		
		//2、根据批号获取、牌号、备注信息
		String ctOrder = "select brand,comments,batchno,ordno,ct.sampdate from ct_orders ct where ct.batchno like ? and matcode=? and status <> 'Abolished' order by ordno desc";
		
		String order = "select o.ordno ordno,b.batchno,o.realsampdate sampdate from batches b,folders f,orders o where b.batchid=f.batchid and f.folderno=o.folderno and b.batchno like ? and f.matcode = ? and b.dispsts <> 'Cancelled' ";
		
		//3、获取检测结果信息
		String ctValues = "select t2.testcode,t2.analyte,t2.sinonym,t1.final final_num,t1.charlimits,t1.status from ipcoa_tempvalues t1,JBXJ_DS_ANALYTE t2 where t1.ordno = ? " + 
				" and t1.testcode = t2.testcode and t1.analyte_id=t2.analyte and t2.matcode=? ";
		
		String rsValues = "select t2.testcode,t2.analyte,t2.sinonym,t1.final final_num,t1.s status from results t1,JBXJ_DS_ANALYTE t2 where t1.ordno = ? " + 
				" and t1.testcode = t2.testcode and t1.analyte=t2.analyte and t2.matcode=? ";
		
		//4、检查该批号的信息是否存在
		String checkBatchNo = "select ordno from JBXJ_Daily where batchno=? and matcode=?";
		
		//5、更新检测指标
		String updateLimit = "update JBXJ_DS_ANALYTE_REPORT set CHARLIMIT = ? where REPORT_ID=? and TESTCODE=? and ANALYTE=? ";
		
		//6、获取A/B线检测结果信息
		//判断商品是否区分A/B线
		String queryRule = "select batch_rule from JBXJ_DS_MAT where matcode=?";
		String batchRule = dao.queryValue(queryRule, String.class, matcode);
		String[] batchNoPre = new String[] {""};
		if(batchRule!=null&&!batchRule.equals("")) {
			batchNoPre = batchRule.split(",");
		}
		for(String pre:batchNoPre) {
			int index =0;
			boolean limitflag = true;
			
			for(String bn:batchNoArray) {
				List<Map<String,Object>> orders = dao.queryListMap(ctOrder, pre+bn,matcode);
				if(orders.size()==0) {
					orders = dao.queryListMap(order, pre+bn,matcode);
				}
				if(orders.size()>0) {
					Map<String,Object> map = orders.get(0);
					DailyVo vo = new DailyVo();
					vo.setMatcode(matcode);
					vo.setStartDate(startTime);
					vo.setEndDate(endTime);
					vo.setTjDate(curTime);
					vo.setAbline(pre);
					vo.setSort(index);
					DailyWrap.baseMessage(vo, map);
					
					//检测结果
					List<DailyDetailVo> list = new ArrayList<DailyDetailVo>();
					
					vo.getBatchno().substring(0).length();
					if(vo.getBatchno().substring(pre.length()).length()==11) {
						list = dao.queryListObject(rsValues,DailyDetailVo.class, vo.getOrdno(),vo.getMatcode());
					}else {
						list = dao.queryListObject(ctValues,DailyDetailVo.class, vo.getOrdno(),vo.getMatcode());
					}
				
					//更新指标信息
					if(limitflag&&vo.getBatchno().length()==10) {
						for(DailyDetailVo result:list) {
							String charLimit = result.getCharlimits()==null?"":result.getCharlimits();
							int testCode = result.getTestCode();
							String analyte = result.getAnalyte();
							dao.excuteUpdate(updateLimit,charLimit,reportId,testCode,analyte);
						}
						limitflag = false;
					}

					//判断信息是否存在
					String oldOrdNo = dao.queryValue(checkBatchNo, String.class, vo.getBatchno(),vo.getMatcode());
					if(oldOrdNo==null||oldOrdNo.equals("")) {
						dao.create(vo);
					}else{
						if(!vo.getOrdno().equals(oldOrdNo)) {
							dao.excuteUpdate("update JBXJ_Daily set ordno=? where ordno=?", vo.getOrdno(),oldOrdNo);
							dao.excuteUpdate("update JBXJ_Daily_detail set ordno=? where ordno=?", vo.getOrdno(),oldOrdNo);
						}
						dao.update(vo, "ordno");
					};
					
					String checkResult = "select count(*) from JBXJ_Daily_detail where matcode=? and batchno=? and testcode=? and analyte=? ";
					String updateResult = "update JBXJ_Daily_detail set final_num=?,status=? where matcode=? and batchno=? and testcode=? and analyte=? ";
					for(DailyDetailVo rmap:list) {
						rmap.setMatcode(matcode);
						rmap.setAbline(pre);
						rmap.setBatchno(vo.getBatchno());
						rmap.setOrdno(vo.getOrdno());
						//判断信息是否存在
						if(dao.queryValue(checkResult, Integer.class, rmap.getMatcode(),rmap.getBatchno(),rmap.getTestCode(),rmap.getAnalyte())>0) {
							dao.excuteUpdate(updateResult, rmap.getFinalNum(),rmap.getStatus(),rmap.getMatcode(),rmap.getBatchno(),rmap.getTestCode(),rmap.getAnalyte());
						}else {
							dao.create(rmap);
						}
					}
				}
				index++;
			}
		}
	}
	
	/**
	 * 重新统计班组监测信息
	 * startTime上班时间，endTime下班时间
	 */
	public void reBuildDailyHZ(String matcode,String startTime,String endTime){
		
		//获取ID信息
		String sql = "select id from JBXJ_DS_report where matcode=? and start_date=? and end_date=?";
		String id = dao.queryValue(sql,String.class,matcode,startTime,endTime);
		
		this.updateDsReport(endTime, id);
		this.hz(matcode, startTime, endTime,id);
	
	}
	
	/**
	 * 查询监测结果信息
	 * @param matcode
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public DailyHz queryDailyHz(String matcode,String startTime,String endTime){
		//判断该区间的信息是否已经统计
		String startTeamTime = startTime;
		String endTeamTime = PreParameter.endTeamTime(startTeamTime);
		while(endTime.compareTo(endTeamTime)>=0) {
			this.dailyHZ(matcode, startTeamTime, endTeamTime);
			startTeamTime = endTeamTime;
			endTeamTime = PreParameter.endTeamTime(startTeamTime);
		}
		

		//判断商品是否区分A/B线
		String queryRule = "select batch_rule from JBXJ_DS_MAT where matcode=?";
		String batchRule = dao.queryValue(queryRule, String.class, matcode);
		String[] batchNoPre = new String[] {""};
		if(batchRule!=null&&!batchRule.equals("")) {
			batchNoPre = batchRule.split(",");
		}
		if(batchNoPre.length==2) {
			return this.warpResultAB(matcode, startTime, endTime);
		}else {
			return this.warpResult(matcode, startTime, endTime);
		}
		
	}
	
	public DailyHz warpResultAB(String matcode,String startTime,String endTime) {
		//获取统计标题
		String queryTitle = "select t3.testcode,t3.analyte,t3.sinonym,t.charlimit,t3.sort from " + 
						"(select t2.testcode,t2.analyte,t1.matcode,max(t2.charlimit) charlimit from JBXJ_DS_report t1,JBXJ_DS_ANALYTE_report t2 " + 
						"where t1.id=t2.report_id and t1.matcode=? and t1.start_date>=? and t1.end_date<=? group by t2.testcode,t2.analyte,t1.matcode) t,JBXJ_DS_ANALYTE t3 " + 
						"where t3.matcode=t.matcode and t3.analyte=t.analyte order by t3.sort";
				
		List<Map<String,Object>> titles = dao.queryListMap(queryTitle, matcode,startTime,endTime);
		
		//获取统计数据
		String queryDetail = "select t.start_date,t.brand,t2.* from JBXJ_Daily t,JBXJ_Daily_detail t2 where t.ordno=t2.ordno " + 
				" and t.matcode=? and t.start_date>=? and t.end_date<=? and t.abline=? " + 
				" order by t.start_date,t.sort";
		
		//获取A/B线检测结果信息
		List<DailyDetailVo> listA = dao.queryListObject(queryDetail,DailyDetailVo.class,matcode,startTime,endTime,"1");
		
		List<DailyVo> lineA =  new ArrayList<DailyVo>();
		DailyVo dailyA = null;
		for(DailyDetailVo detail:listA) {
			String ordno = detail.getOrdno();
			if(dailyA==null||!dailyA.getOrdno().equals(ordno)) {
				dailyA  = new DailyVo();
				dailyA.setOrdno(ordno);
				dailyA.setBatchno(detail.getBatchno());
				dailyA.setStartDate(detail.getStartDate());
				dailyA.setBrand(detail.getBrand());
				DailyDetailVo temp = new DailyDetailVo();
				for(int index=0;index<titles.size();index++) {
					dailyA.getList().add(temp);
				}
				lineA.add(dailyA);
			}
			for(int index=0;index<titles.size();index++) {
				Map<String,Object>  title = titles.get(index);
				int testcode = BigDecimalKit.getInt(title.get("testcode"));
				String analyte = title.get("analyte").toString();
				if(detail.getTestCode()==testcode&&detail.getAnalyte().equals(analyte)) {
					dailyA.getList().set(index, detail);
					break;
				}
			}
		}

		List<DailyDetailVo> listB = dao.queryListObject(queryDetail,DailyDetailVo.class,matcode,startTime,endTime,"2");
		List<DailyVo> lineB =  new ArrayList<DailyVo>();
		DailyVo dailyB = null;
		for(DailyDetailVo detail:listB) {
			String ordno = detail.getOrdno();
			
			if(dailyB==null||!dailyB.getOrdno().equals(ordno)) {
				dailyB  = new DailyVo();
				dailyB.setOrdno(ordno);
				dailyB.setBatchno(detail.getBatchno());
				dailyB.setStartDate(detail.getStartDate());
				dailyB.setBrand(detail.getBrand());
				DailyDetailVo temp = new DailyDetailVo();
				for(int index=0;index<titles.size();index++) {
					dailyB.getList().add(temp);
				}
				lineB.add(dailyB);
			}
			
			for(int index=0;index<titles.size();index++) {
				Map<String,Object>  title = titles.get(index);
				int testcode = BigDecimalKit.getInt(title.get("testcode"));
				String analyte = title.get("analyte").toString();
				if(detail.getTestCode()==testcode&&detail.getAnalyte().equals(analyte)) {
					dailyB.getList().set(index, detail);
					break;
				}
			}
		}
		
		//AB线数据排列
		int maxSize = lineA.size()+lineB.size();
		for(int index=0;index<maxSize;index++) {
			int asize = lineA.size();
			int bsize = lineB.size();
			if(asize<=index&&bsize<=index) {
				break;
			}
			
			if(asize>index&&bsize>index) {
				DailyVo dataA= lineA.get(index);
				DailyVo dataB= lineB.get(index);
				
				if(dataA.getStartDate().compareTo(dataB.getStartDate())==0) {

					if(dataA.getSort()>dataB.getSort()) {
						DailyVo vo = new DailyVo();
						vo.setStartDate(dataB.getStartDate());
						vo.setSort(dataB.getSort());
						vo.setBatchno(dataB.getBatchno().replaceFirst("2", "1"));
						DailyDetailVo temp = new DailyDetailVo();
						for(int i=0;i<titles.size();i++) {
							vo.getList().add(temp);
						}

						lineA.add(index, vo);
					}else if(dataA.getSort()<dataB.getSort()) {
						DailyVo vo = new DailyVo();
						vo.setStartDate(dataA.getStartDate());
						vo.setSort(dataA.getSort());
						vo.setBatchno(dataA.getBatchno().replaceFirst("1", "2"));
						DailyDetailVo temp = new DailyDetailVo();
						for(int j=0;j<titles.size();j++) {
							vo.getList().add(temp);
						}
						lineB.add(index, vo);
					}
					continue;
				}
			}
			
			if(asize>index&&bsize==index) {
				DailyVo dataA= lineA.get(index);				
				DailyVo vo = new DailyVo();
				DailyDetailVo temp = new DailyDetailVo();
				for(int j=0;j<titles.size();j++) {
					vo.getList().add(temp);
				}
				vo.setStartDate(dataA.getStartDate());
				vo.setSort(dataA.getSort());
				vo.setBatchno(dataA.getBatchno().replaceFirst("1", "2"));
				lineB.add(vo);
			}
			
			if(asize==index&&bsize>index) {
				DailyVo dataB= lineB.get(index);				
				DailyVo vo = new DailyVo();
				DailyDetailVo temp = new DailyDetailVo();
				for(int j=0;j<titles.size();j++) {
					vo.getList().add(temp);
				}
				vo.setStartDate(dataB.getStartDate());
				vo.setSort(dataB.getSort());
				vo.setBatchno(dataB.getBatchno().replaceFirst("2", "1"));
				lineA.add(vo);
			}
		}
		
		
		//封装数据返回
		DailyHz dailyHz = new DailyHz();
		dailyHz.setTitles(titles);
		dailyHz.setLineA(lineA);
		dailyHz.setLineB(lineB);
				
		return dailyHz;
	}
	
	
	public DailyHz warpResult(String matcode,String startTime,String endTime) {
		//获取统计标题
		String queryTitle = "select t3.testcode,t3.analyte,t3.sinonym,t.charlimit,t3.sort from " + 
						"(select t2.testcode,t2.analyte,t1.matcode,max(t2.charlimit) charlimit from JBXJ_DS_report t1,JBXJ_DS_ANALYTE_report t2 " + 
						"where t1.id=t2.report_id and t1.matcode=? and t1.start_date>=? and t1.end_date<=? group by t2.testcode,t2.analyte,t1.matcode) t,JBXJ_DS_ANALYTE t3 " + 
						"where t3.matcode=t.matcode and t3.analyte=t.analyte order by t3.sort";
				
		List<Map<String,Object>> titles = dao.queryListMap(queryTitle, matcode,startTime,endTime);
		
		//获取统计数据
		String queryDetail = "select t.start_date,t.brand,t2.* from JBXJ_Daily t,JBXJ_Daily_detail t2 where t.ordno=t2.ordno " + 
				" and t.matcode=? and t.start_date>=? and t.end_date<=?" + 
				" order by t.start_date,t.sort";
		
		//获取A/B线检测结果信息
		List<DailyDetailVo> listA = dao.queryListObject(queryDetail,DailyDetailVo.class,matcode,startTime,endTime);
		
		List<DailyVo> lineA =  new ArrayList<DailyVo>();
		DailyVo dailyA = null;
		for(DailyDetailVo detail:listA) {
			String ordno = detail.getOrdno();
			if(dailyA==null||!dailyA.getOrdno().equals(ordno)) {
				dailyA  = new DailyVo();
				dailyA.setOrdno(ordno);
				dailyA.setBatchno(detail.getBatchno());
				dailyA.setStartDate(detail.getStartDate());
				dailyA.setBrand(detail.getBrand());
				DailyDetailVo temp = new DailyDetailVo();
				for(int index=0;index<titles.size();index++) {
					dailyA.getList().add(temp);
				}
				lineA.add(dailyA);
			}
			for(int index=0;index<titles.size();index++) {
				Map<String,Object>  title = titles.get(index);
				int testcode = BigDecimalKit.getInt(title.get("testcode"));
				String analyte = title.get("analyte").toString();
				if(detail.getTestCode()==testcode&&detail.getAnalyte().equals(analyte)) {
					dailyA.getList().set(index, detail);
					break;
				}
			}
		}
		
		//封装数据返回
		DailyHz dailyHz = new DailyHz();
		dailyHz.setTitles(titles);
		dailyHz.setLineA(lineA);
				
		return dailyHz;
	}
	
}
