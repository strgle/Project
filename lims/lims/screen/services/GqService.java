package lims.screen.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lims.dataSearch.vo.Orders;
import lims.dataSearch.vo.ParamsVo;
import pers.czf.dbManager.Dao;

@Service
public class GqService {
	
	@Autowired
	private Dao dao;
	
	public List<Map<String,Object>> toReceive(){
		//实验室待接收样品
		String sql = "select ordno,matname,plant,sample_point_id,pointdesc,to_char(sampdate,'MM-dd hh24:mi') sampdate from orders where status in ('Prelogged','Started') and DISPORDGOUP=ordno order by ordno";
		return dao.queryListMap(sql);
	}
	
	public List<Map<String,Object>> testing(){
		//检测中样品
		String sql = "select ordno,matname,plant,sample_point_id,pointdesc,to_char(sampdate,'MM-dd hh24:mi') sampdate from orders where status ='Logged' or (status='Resulted' and APPRDISP is null) and DISPORDGOUP=ordno order by ordno";
		return dao.queryListMap(sql);
	}
	
	public List<Map<String,Object>> approval(){
		//待审批的样品
		String sql = "select ordno,matname,plant,sample_point_id,pointdesc,to_char(sampdate,'MM-dd hh24:mi') sampdate from orders where status='Resulted' and APPRDISP='…' and DISPORDGOUP=ordno order by ordno";
		return dao.queryListMap(sql);
	}
	
	/**
	 * 获取样品台帐查询数据列表的表头
	 * @param params
	 * @return
	 */
	public List<String> ordTitles(ParamsVo params){
		//处理sql语句及查询条件
		List<Object> paramlist = new ArrayList<Object>();
		paramlist.add(params.getPointId());
		paramlist.add(params.getMatCode());
		paramlist.add(params.getStartDate());
		paramlist.add(params.getEndDate());
		
		String sql = "select distinct t1.sinonym,t1.units,t1.sptestsorter,t1.sorter from CT_Q_DATA t1,orders t2,folders t3,batches t4 "
				+ " where t1.ordno=t2.ordno and t2.folderno=t3.folderno and t3.batchid=t4.batchid "
				+ " and t2.sample_point_id=? and t2.matcode=? and to_char(t2.sampdate,'yyyy-MM-dd')>=? and to_char(t2.sampdate,'yyyy-MM-dd')<=?";
		
		sql += "order by sptestsorter,sorter";
		
		//获取表头
		List<Map<String,Object>> titleList = dao.queryListMap(sql, paramlist.toArray());
		List<String> titles = new ArrayList<String>();
		for(Map<String,Object> map:titleList){
			String key = map.get("sinonym").toString();
			if(map.get("units")!=null){
				key = key+"("+map.get("units").toString()+")";
			}
			if(!titles.contains(key)){
				titles.add(key);
			}
			
		}
		return titles;
	}
	
	/**
	 * 获取样品台帐的检测结果
	 * @param params
	 * @return
	 */
	public List<Orders> ordDetails(ParamsVo params,List<String> titles){
		
		List<Orders> data = new ArrayList<Orders>();
		
		//处理sql语句及查询条件
		List<Object> paramlist = new ArrayList<Object>();
		paramlist.add(params.getPointId());
		paramlist.add(params.getMatCode());
		paramlist.add(params.getStartDate());
		paramlist.add(params.getEndDate());
		
		String sql2 = "select t4.batchname,t2.ordno,t2.matname,t2.plant,t2.pointdesc,"
				+ " to_char(t2.sampdate,'MM-dd hh24:mi') sampdate,t1.final finalnum,t1.sinonym,t1.units,"
				+ " t1.s,t5.analtype,t1.testcode,t1.analyte,t2.status,t2.grade,"
				+ " t4.type,t4.tasktype,t4.suppcode "
				+ " from CT_Q_DATA t1,orders t2,folders t3,batches t4,results t5 "
				+ " where t1.ordno=t2.ordno and t2.folderno=t3.folderno and t3.batchid=t4.batchid "
				+ " and t1.ordno=t5.ordno and t1.testcode=t5.testcode and t1.analyte=t5.analyte "
				+ " and t2.sample_point_id=? and t2.matcode=? and to_char(t2.sampdate,'yyyy-MM-dd')>=? and to_char(t2.sampdate,'yyyy-MM-dd')<=?";
		
	
		
		sql2 += " order by sampdate desc";

		//获取样品数据
		List<Map<String,Object>> ordList = dao.queryListMap(sql2, paramlist.toArray());
		
		Map<String,Orders> ordMap = new HashMap<String,Orders>();
		for(Map<String,Object> map:ordList){
			String ordNo = map.get("ordno").toString();
			if(!ordMap.containsKey(ordNo)){
				Orders ords  = new Orders();
				int as = ords.getAnalytes().size();
				int ts = titles.size();
				while(as<ts){
					ords.getAnalytes().add(null);
					as++;
				}
				ords.setOrdNo(ordNo);
				ords.setPlant(map.get("plant").toString());
				ords.setSampDate(map.get("sampdate").toString());
				ords.setPointdesc(map.get("pointdesc").toString());
				ords.setBatchName(map.get("batchname")==null?"":map.get("batchname").toString());
				String grade = map.get("grade")==null?"":map.get("grade").toString();
				ords.setGrade(grade);
				ords.setTaskType(map.get("tasktype").toString());
				ords.setType(map.get("type").toString());
				ords.setSuppCode(map.get("suppcode")==null?"":map.get("suppcode").toString());
				ords.setMatName(map.get("matname").toString());
				data.add(ords);
				ordMap.put(ordNo, ords);
			}
			
			Orders ordst = ordMap.get(ordNo);
			
			Object units = map.get("units");
			String key = map.get("sinonym").toString();
			if(units!=null){
				key = key + "("+units+")";
			}
			
			int index = titles.indexOf(key);
			ordst.getAnalytes().set(index, map);
		}
		
		return data;
	}
	
}
