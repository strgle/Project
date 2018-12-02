package lims.dataSearch.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.core.handler.SessionFactory;

import lims.dataSearch.vo.Orders;
import lims.dataSearch.vo.ParamsVo;
import pers.czf.dbManager.Dao;

@Service
public class DailyService {
	
	@Autowired
	private Dao dao;
	
	/**
	 * 获取检测样品编号列表，根据采样时间进行才行
	 * 查询条件：车间、装置、采样点、样品代码，采样时间
	 * 根据样品编号
	 * @param params
	 * @return
	 */
	public List<String> ordNoBySampdate(String areaName,String plant,String pointId,String startDate,String endDate,String status){
		
		List<String> ordList = new ArrayList<String>();
		
		//获取检测项目列表信息
		String sql = null;
		String type = "point";
		if(!StringUtils.isEmpty(pointId)) {
			type="point";
		}else if(!StringUtils.isEmpty(plant)) {
			type="plant";
		}else if(!StringUtils.isEmpty(areaName)) {
			type="area";
		}
		
		switch(type){
			case "area":
				sql = "select ordno from orders t where exists(select 1 from plantdaily t2 where t.ordno=t2.ordno) "
					+ " and to_char(t.sampdate,'yyyy-MM-dd')>= ? and to_char(t.sampdate,'yyyy-MM-dd')<=? "
					+ " and t.area_name=? ";
				if(status==null||status.equals("")) {
					sql = sql+ " order by ordno desc";
					ordList = dao.queryListValue(sql,String.class,startDate,endDate,areaName);
				}else {
					sql = sql+ " and t.status = ? order by ordno desc";
					ordList = dao.queryListValue(sql,String.class,startDate,endDate,areaName,status);
				}
				break;
			case "plant":
				sql = "select ordno from orders t where exists(select 1 from plantdaily t2 where t.ordno=t2.ordno) "
						+ " and to_char(t.sampdate,'yyyy-MM-dd')>= ? and to_char(t.sampdate,'yyyy-MM-dd')<=? "
						+ " and t.area_name=? and t.plant=?";
				if(status==null||status.equals("")) {
					sql = sql+ " order by ordno desc";
					ordList = dao.queryListValue(sql,String.class,startDate,endDate,areaName,plant);
				}else {
					sql = sql+ " and t.status = ? order by ordno desc";
					ordList = dao.queryListValue(sql,String.class,startDate,endDate,areaName,plant,status);
				}
				
				break;
			case "point":
				sql = "select ordno from orders t where exists(select 1 from plantdaily t2 where t.ordno=t2.ordno) "
						+ " and to_char(t.sampdate,'yyyy-MM-dd')>= ? and to_char(t.sampdate,'yyyy-MM-dd')<=? "
						+ " and t.sample_point_id=? ";
				if(status==null||status.equals("")) {
					sql = sql+ " order by ordno desc";
					ordList = dao.queryListValue(sql,String.class,startDate,endDate,pointId);
				}else {
					sql = sql+ " and t.status = ? order by ordno desc";
					ordList = dao.queryListValue(sql,String.class,startDate,endDate,pointId,status);
				}
				
				break;
			default:
				break;
		}
		return ordList;
	}
	public List<String> ordNoBySampdateTemp(String areaName,String plant,String pointId,String startDate,String endDate,String status){
		List<String> ordList = new ArrayList<String>();
		String sql = null;
		String type = "point";
		if(!StringUtils.isEmpty(pointId)) {
			type="point";
		}else if(!StringUtils.isEmpty(plant)) {
			type="plant";
		}else if(!StringUtils.isEmpty(areaName)) {
			type="area";
		}
		switch(type){
		case "area":
			sql = "select ordno from batches b, folders f,orders t where b.batchid = f.batchid and f.folderno = t.folderno and b.tasktype='临时加样' and exists(select 1 from plantdaily t2 where t.ordno=t2.ordno )  and to_char(t.sampdate,'yyyy-MM-dd')>= ? and to_char(t.sampdate,'yyyy-MM-dd')<=?  and t.area_name=? ";
				
			if(status==null||status.equals("")) {
				sql = sql+ " order by ordno desc";
				ordList = dao.queryListValue(sql,String.class,startDate,endDate,areaName);
			}else {
				sql = sql+ " and t.status = ? order by ordno desc";
				ordList = dao.queryListValue(sql,String.class,startDate,endDate,areaName);
			}
			break;
		case "plant":
			sql = "select ordno from batches b, folders f,orders t where b.batchid = f.batchid and f.folderno = t.folderno and b.tasktype='临时加样' and exists(select 1 from plantdaily t2 where t.ordno=t2.ordno )  and to_char(t.sampdate,'yyyy-MM-dd')>= ? and to_char(t.sampdate,'yyyy-MM-dd')<=?  and t.area_name=? "
					
					+ " and t.plant=?";
			if(status==null||status.equals("")) {
				sql = sql+ " order by ordno desc";
				ordList = dao.queryListValue(sql,String.class,startDate,endDate,areaName,plant);
			}else {
				sql = sql+ " and t.status = ? order by ordno desc";
				ordList = dao.queryListValue(sql,String.class,startDate,endDate,areaName,plant,status);
			}
			
			break;
		case "point":
			sql = "select ordno from batches b, folders f,orders t where b.batchid = f.batchid and f.folderno = t.folderno and b.tasktype='临时加样' and exists(select 1 from plantdaily t2 where t.ordno=t2.ordno )  and to_char(t.sampdate,'yyyy-MM-dd')>= ? and to_char(t.sampdate,'yyyy-MM-dd')<=?  "
					
					+ " and t.sample_point_id=? ";
			if(status==null||status.equals("")) {
				sql = sql+ " order by ordno desc";
				ordList = dao.queryListValue(sql,String.class,startDate,endDate,pointId);
			}else {
				sql = sql+ " and t.status = ? order by ordno desc";
				ordList = dao.queryListValue(sql,String.class,startDate,endDate,pointId,status);
			}
			
			break;
		default:
			break;
		}
		return ordList;
	}
	
	public List<String> ordNoBySampdateBatchno(String areaName,String plant,String pointId,String startDate,String endDate,String status,String batchno){
		List<String> ordList = new ArrayList<String>();
		String sql = null;
		String type = "point";
		if(!StringUtils.isEmpty(pointId)) {
			type="point";
		}else if(!StringUtils.isEmpty(plant)) {
			type="plant";
		}else if(!StringUtils.isEmpty(areaName)) {
			type="area";
		}
		if ((batchno == null) || (batchno.equals(""))) {
			switch(type){
			case "area":
				sql = "select ordno from orders t where exists(select 1 from plantdaily t2 where t.ordno=t2.ordno) "
					+ " and to_char(t.sampdate,'yyyy-MM-dd')>= ? and to_char(t.sampdate,'yyyy-MM-dd')<=? "
					+ " and t.area_name=? and t.plant in (select plant from plantmembers t3 where t3.area_name=? and t3.usrnam=?)";
				if(status==null||status.equals("")) {
					sql = sql+ " order by ordno desc";
					ordList = dao.queryListValue(sql,String.class,startDate,endDate,areaName,areaName,SessionFactory.getUsrName());
				}else {
					sql = sql+ " and t.status = ? order by ordno desc";
					ordList = dao.queryListValue(sql,String.class,startDate,endDate,areaName,areaName,SessionFactory.getUsrName(),status);
				}
				break;
			case "plant":
				sql = "select ordno from orders t where exists(select 1 from plantdaily t2 where t.ordno=t2.ordno) "
						+ " and to_char(t.sampdate,'yyyy-MM-dd')>= ? and to_char(t.sampdate,'yyyy-MM-dd')<=? "
						+ " and t.area_name=? and t.plant=?";
				if(status==null||status.equals("")) {
					sql = sql+ " order by ordno desc";
					ordList = dao.queryListValue(sql,String.class,startDate,endDate,areaName,plant);
				}else {
					sql = sql+ " and t.status = ? order by ordno desc";
					ordList = dao.queryListValue(sql,String.class,startDate,endDate,areaName,plant,status);
				}
				
				break;
			case "point":
				sql = "select ordno from orders t where exists(select 1 from plantdaily t2 where t.ordno=t2.ordno) "
						+ " and to_char(t.sampdate,'yyyy-MM-dd')>= ? and to_char(t.sampdate,'yyyy-MM-dd')<=? "
						+ " and t.sample_point_id=? ";
				if(status==null||status.equals("")) {
					sql = sql+ " order by ordno desc";
					ordList = dao.queryListValue(sql,String.class,startDate,endDate,pointId);
				}else {
					sql = sql+ " and t.status = ? order by ordno desc";
					ordList = dao.queryListValue(sql,String.class,startDate,endDate,pointId,status);
				}
				
				break;
			default:
				break;
			}
		}
		else {
			switch(type){
			case "area":
				sql = "select ordno from orders t where exists(select 1 from plantdaily t2 where t.ordno=t2.ordno  and t2.BATCHNO=?) "
					+ " and to_char(t.sampdate,'yyyy-MM-dd')>= ? and to_char(t.sampdate,'yyyy-MM-dd')<=? "
					+ " and t.area_name=? ";
				if(status==null||status.equals("")) {
					sql = sql+ " order by ordno desc";
					ordList = dao.queryListValue(sql,String.class,batchno,startDate,endDate,areaName);
				}else {
					sql = sql+ " and t.status = ? order by ordno desc";
					ordList = dao.queryListValue(sql,String.class,batchno,startDate,endDate,areaName);
				}
				break;
			case "plant":
				sql = "select ordno from orders t where exists(select 1 from plantdaily t2 where t.ordno=t2.ordno and t2.BATCHNO=?) "
						+ " and to_char(t.sampdate,'yyyy-MM-dd')>= ? and to_char(t.sampdate,'yyyy-MM-dd')<=? "
						+ " and t.area_name=? and t.plant=?";
				if(status==null||status.equals("")) {
					sql = sql+ " order by ordno desc";
					ordList = dao.queryListValue(sql,String.class,batchno,startDate,endDate,areaName,plant);
				}else {
					sql = sql+ " and t.status = ? order by ordno desc";
					ordList = dao.queryListValue(sql,String.class,batchno,startDate,endDate,areaName,plant,status);
				}
				
				break;
			case "point":
				sql = "select ordno from orders t where exists(select 1 from plantdaily t2 where t.ordno=t2.ordno and t2.BATCHNO=?) "
						+ " and to_char(t.sampdate,'yyyy-MM-dd')>= ? and to_char(t.sampdate,'yyyy-MM-dd')<=? "
						+ " and t.sample_point_id=? ";
				if(status==null||status.equals("")) {
					sql = sql+ " order by ordno desc";
					ordList = dao.queryListValue(sql,String.class,batchno,startDate,endDate,pointId);
				}else {
					sql = sql+ " and t.status = ? order by ordno desc";
					ordList = dao.queryListValue(sql,String.class,batchno,startDate,endDate,pointId,status);
				}
				
				break;
			default:
				break;
			}
		}
		
		return ordList;
	}
	
	public List<String> ordNoBySampdateSampling(String areaName,String plant,String pointId,String startDate,String endDate,String status){
		List<String> ordList = new ArrayList<String>();
		String sql = null;
		String type = "point";
		if(!StringUtils.isEmpty(pointId)) {
			type="point";
		}else if(!StringUtils.isEmpty(plant)) {
			type="plant";
		}else if(!StringUtils.isEmpty(areaName)) {
			type="area";
		}
		switch(type){
		case "area":
			sql = "select ordno from batches b, folders f,orders t where b.batchid = f.batchid and f.folderno = t.folderno and b.tasktype='抽样检查' and exists(select 1 from plantdaily t2 where t.ordno=t2.ordno )  and to_char(t.sampdate,'yyyy-MM-dd')>= ? and to_char(t.sampdate,'yyyy-MM-dd')<=?  and t.area_name=? ";
				
			if(status==null||status.equals("")) {
				sql = sql+ " order by ordno desc";
				ordList = dao.queryListValue(sql,String.class,startDate,endDate,areaName);
			}else {
				sql = sql+ " and t.status = ? order by ordno desc";
				ordList = dao.queryListValue(sql,String.class,startDate,endDate,areaName);
			}
			break;
		case "plant":
			sql = "select ordno from batches b, folders f,orders t where b.batchid = f.batchid and f.folderno = t.folderno and b.tasktype='抽样检查' and exists(select 1 from plantdaily t2 where t.ordno=t2.ordno )  and to_char(t.sampdate,'yyyy-MM-dd')>= ? and to_char(t.sampdate,'yyyy-MM-dd')<=?  and t.area_name=? "
					
					+ " and t.plant=?";
			if(status==null||status.equals("")) {
				sql = sql+ " order by ordno desc";
				ordList = dao.queryListValue(sql,String.class,startDate,endDate,areaName,plant);
			}else {
				sql = sql+ " and t.status = ? order by ordno desc";
				ordList = dao.queryListValue(sql,String.class,startDate,endDate,areaName,plant,status);
			}
			
			break;
		case "point":
			sql = "select ordno from batches b, folders f,orders t where b.batchid = f.batchid and f.folderno = t.folderno and b.tasktype='抽样检查' and exists(select 1 from plantdaily t2 where t.ordno=t2.ordno )  and to_char(t.sampdate,'yyyy-MM-dd')>= ? and to_char(t.sampdate,'yyyy-MM-dd')<=?  "
					
					+ " and t.sample_point_id=? ";
			if(status==null||status.equals("")) {
				sql = sql+ " order by ordno desc";
				ordList = dao.queryListValue(sql,String.class,startDate,endDate,pointId);
			}else {
				sql = sql+ " and t.status = ? order by ordno desc";
				ordList = dao.queryListValue(sql,String.class,startDate,endDate,pointId,status);
			}
			
			break;
		default:
			break;
		}
		return ordList;
	}
	/**
	 * 不使用，所有问题
	 * @param carno
	 * @param contract
	 * @param type
	 * @param startDate
	 * @param endDate
	 * @param batchno
	 * @return
	 */
	public List<String> ordNoBySampdateRaw(String carno,String contract,String type,String startDate,String endDate,String batchno){
		List<String> ordList = new ArrayList<String>();
		String sql = null;
		String whr = " 1 =1 ";
		if(carno !=null && carno.length() !=0)
		{
			whr +=" and o.description like '%"+carno+"%'";
		}
		if(contract !=null && contract.length() !=0) 
		{
			whr +=" and b.businessno like '%"+contract+"%'";
		}
		if(batchno !=null && batchno.length() !=0)
		{
			whr +="";
		}
		/*if ((batchno == null) || (batchno.equals(""))) {
			switch(type){
			case "area":
				sql = "select ordno from batches b, folders f,orders t where b.batchid = f.batchid and f.folderno = t.folderno and b.type='RAW' and exists(select 1 from plantdaily t2 where t.ordno=t2.ordno )  and to_char(t.sampdate,'yyyy-MM-dd')>= ? and to_char(t.sampdate,'yyyy-MM-dd')<=?  and t.area_name=? ";
					
				if(status==null||status.equals("")) {
					sql = sql+ " order by ordno desc";
					ordList = dao.queryListValue(sql,String.class,startDate,endDate,areaName);
				}else {
					sql = sql+ " and t.status = ? order by ordno desc";
					ordList = dao.queryListValue(sql,String.class,startDate,endDate,areaName,status);
				}
				break;
			case "plant":
				sql = "select ordno from batches b, folders f,orders t where b.batchid = f.batchid and f.folderno = t.folderno and b.type='RAW' and exists(select 1 from plantdaily t2 where t.ordno=t2.ordno )  and to_char(t.sampdate,'yyyy-MM-dd')>= ? and to_char(t.sampdate,'yyyy-MM-dd')<=?  and t.area_name=? "
						
						+ " and t.plant=?";
				if(status==null||status.equals("")) {
					sql = sql+ " order by ordno desc";
					ordList = dao.queryListValue(sql,String.class,startDate,endDate,areaName,plant);
				}else {
					sql = sql+ " and t.status = ? order by ordno desc";
					ordList = dao.queryListValue(sql,String.class,startDate,endDate,areaName,plant,status);
				}
				
				break;
			case "point":
				sql = "select ordno from batches b, folders f,orders t where b.batchid = f.batchid and f.folderno = t.folderno and b.type='RAW' and exists(select 1 from plantdaily t2 where t.ordno=t2.ordno )  and to_char(t.sampdate,'yyyy-MM-dd')>= ? and to_char(t.sampdate,'yyyy-MM-dd')<=?  "
						
						+ " and t.sample_point_id=? ";
				if(status==null||status.equals("")) {
					sql = sql+ " order by ordno desc";
					ordList = dao.queryListValue(sql,String.class,startDate,endDate,pointId);
				}else {
					sql = sql+ " and t.status = ? order by ordno desc";
					ordList = dao.queryListValue(sql,String.class,startDate,endDate,pointId,status);
				}
				
				break;
			default:
				break;
			}
		}
		else {
			switch(type){
			case "area":
				sql = "select ordno from batches b, folders f,orders t where b.batchid = f.batchid and f.folderno = t.folderno and b.type='RAW' and exists(select 1 from plantdaily t2 where t.ordno=t2.ordno and t2.BATCHNO=? )  and to_char(t.sampdate,'yyyy-MM-dd')>= ? and to_char(t.sampdate,'yyyy-MM-dd')<=?  and t.area_name=? ";
					
				if(status==null||status.equals("")) {
					sql = sql+ " order by ordno desc";
					ordList = dao.queryListValue(sql,String.class,batchno,startDate,endDate,areaName);
				}else {
					sql = sql+ " and t.status = ? order by ordno desc";
					ordList = dao.queryListValue(sql,String.class,batchno,startDate,endDate,areaName,status);
				}
				break;
			case "plant":
				sql = "select ordno from batches b, folders f,orders t where b.batchid = f.batchid and f.folderno = t.folderno and b.type='RAW' and exists(select 1 from plantdaily t2 where t.ordno=t2.ordno and t2.BATCHNO=?)  and to_char(t.sampdate,'yyyy-MM-dd')>= ? and to_char(t.sampdate,'yyyy-MM-dd')<=?  and t.area_name=? "
						
						+ " and t.plant=?";
				if(status==null||status.equals("")) {
					sql = sql+ " order by ordno desc";
					ordList = dao.queryListValue(sql,String.class,batchno,startDate,endDate,areaName,plant);
				}else {
					sql = sql+ " and t.status = ? order by ordno desc";
					ordList = dao.queryListValue(sql,String.class,batchno,startDate,endDate,areaName,plant,status);
				}
				
				break;
			case "point":
				sql = "select ordno from batches b, folders f,orders t where b.batchid = f.batchid and f.folderno = t.folderno and b.type='RAW' and exists(select 1 from plantdaily t2 where t.ordno=t2.ordno and t2.BATCHNO=? )  and to_char(t.sampdate,'yyyy-MM-dd')>= ? and to_char(t.sampdate,'yyyy-MM-dd')<=?  "
						
						+ " and t.sample_point_id=? ";
				if(status==null||status.equals("")) {
					sql = sql+ " order by ordno desc";
					ordList = dao.queryListValue(sql,String.class,batchno,startDate,endDate,pointId);
				}else {
					sql = sql+ " and t.status = ? order by ordno desc";
					ordList = dao.queryListValue(sql,String.class,batchno,startDate,endDate,pointId,status);
				}
				
				break;
			default:
				break;
			}
		}*/
		
		return ordList;
	}
	/**
	 * 获取样品编号对应的检测结果信息
	 * @param ordNos
	 * @return
	 */
	public JSONArray ordDetail(List<String> ords){
		
		//返回结果列表
		JSONArray ordArray = new JSONArray();
		
		//获取样品信息
		String sql1 = "select t1.ordno,to_char(t1.realsampdate,'yyyy/MM/dd hh24:mi') sampdate," + 
				" t1.matcode,nvl(m.matname,t1.matcode) matname,t1.status,t3.batchname, " + 
				" t1.area_name,t1.plant,t1.sample_point_id, " + 
				" t1.description pointdesc,t3.tasktype,t3.type, " + 
				" to_char(t1.apprdate,'yyyy/MM/dd hh24:mi:ss') apprdate,t1.grade " + 
				" from orders t1 left join materials m on t1.matcode = m.matcode,folders t2,batches t3 " + 
				" where  t1.ordno=t1.ordgroup and t1.folderno=t2.folderno and t2.batchid=t3.batchid and t1.ordno in (?) ";
		
		JSONArray orders = dao.queryJSONArray(sql1, ords);
		
		try {
			for(int i=0,length=orders.length();i<length;i++){
				JSONObject json = orders.getJSONObject(i);
				String ordNo = json.getString("ordno");
				int index = ords.indexOf(ordNo);
				ordArray.put(index, json);
			}
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		return ordArray;
	}
	
	/**
	 * 获取样品台帐查询数据列表的表头
	 * @param params
	 * @return
	 */
	public List<String> ordTitles(ParamsVo params){
		//处理sql语句及查询条件
		List<Object> paramlist = new ArrayList<Object>();
		paramlist.add(params.getAreaName());
		paramlist.add(params.getPlant());
		paramlist.add(params.getMatCode());
		paramlist.add(params.getStartDate());
		paramlist.add(params.getEndDate());
		String sql = "select distinct t1.sinonym,t1.units,t1.sptestsorter,t1.sorter from plantdaily t1,orders t2 "
				+ " where t1.ordno=t2.ordno "
				+ " and t2.area_name=? and t2.plant=? and t2.matcode=? and to_char(t2.REALSAMPDATE,'yyyy-MM-dd')>=? and to_char(t2.REALSAMPDATE,'yyyy-MM-dd')<=? "
				+ " order by sptestsorter,sorter";

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
		paramlist.add(params.getAreaName());
		paramlist.add(params.getPlant());
		paramlist.add(params.getMatCode());
		paramlist.add(params.getStartDate());
		paramlist.add(params.getEndDate());
		
		String sql2 = "select t4.batchname,t2.ordno,t2.Description pointdesc,"
				+ " to_char(t2.REALSAMPDATE,'MM-dd hh24:mi') sampdate,to_char(t2.REALSAMPDATE,'yyyy-MM-dd hh24:mi') sortsampdate,t1.final finalnum,t1.sinonym,t1.units,"
				+ " t1.s,t5.analtype,t1.testcode,t1.analyte,t2.status,t2.grade,"
				+ " t4.type,t4.tasktype,t4.suppcode,nvl(m.matname,t2.matcode) matname"
				+ " from plantdaily t1,orders t2 left join materials m on t2.matcode = m.matcode,folders t3,batches t4,results t5 "
				+ " where t1.ordno=t2.ordno and t2.folderno=t3.folderno and t3.batchid=t4.batchid "
				+ " and t1.ordno=t5.ordno and t1.testcode=t5.testcode and t1.analyte=t5.analyte "
				+ " and t2.area_name=? and t2.plant = ? and t2.matcode=? and to_char(t2.REALSAMPDATE,'yyyy-MM-dd')>=? and to_char(t2.REALSAMPDATE,'yyyy-MM-dd')<=? "
				+ " order by sortsampdate desc";

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
				ords.setSampDate(map.get("sampdate").toString());
				ords.setPointdesc(map.get("pointdesc").toString());
				ords.setBatchName(map.get("batchname")==null?"":map.get("batchname").toString());
				String grade = map.get("grade")==null?"":map.get("grade").toString();
				ords.setGrade(grade);
				String status = map.get("status")==null?"":map.get("status").toString();
				ords.setStatus(status);
				
				ords.setTaskType(map.get("tasktype").toString());
				ords.setType(map.get("type").toString());
				ords.setSuppCode(map.get("suppcode")==null?"":map.get("suppcode").toString());
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
