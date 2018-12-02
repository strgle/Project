package lims.dataLedger.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import lims.dataLedger.vo.ParamsVo;
import pers.czf.dbManager.Dao;

@Service("dataLendger.resultService")
public class ResultService {
	
	@Autowired
	private Dao dao;
	
	/**
	 * 获取装置下的样品信息
	 * @param vo
	 * @return
	 */
	public List<Map<String,Object>> mats(ParamsVo vo){
		
		String sql = "select distinct t2.matcode,t2.matname,t2.sample_point_id from CT_Q_DATA t1,orders t2,folders t3,batches t4 "
				+ " where t1.ordno=t2.ordno and t2.folderno=t3.folderno and t3.batchid=t4.batchid "
				+ " and t2.area_name=? and t2.plant =? and t2.matcode!='N/A' and to_char(t2.sampdate,'yyyy-MM-dd hh24:mi:ss')>=? and to_char(t2.sampdate,'yyyy-MM-dd hh24:mi:ss')<=? "
				+ " and t4.tasktype not in ('抽样','加样') order by t2.matcode";
		         
		List<Map<String,Object>> mats = dao.queryListMap(sql,vo.getAreaName(),vo.getPlant(),vo.getStartTime(),vo.getEndTime());
		
		return mats;
	}
	
	/**
	 * 获取检测项目信息
	 * @param vo
	 * @return
	 */
	public List<String> resultTitles(ParamsVo vo){
		//处理sql语句及查询条件
		String sql = "select distinct t1.sinonym,t1.units,t1.sptestsorter,t1.sorter "
				+ " from CT_Q_DATA t1,orders t2,folders t3,batches t4 "
				+ " where t1.ordno=t2.ordno and t2.folderno=t3.folderno and t3.batchid=t4.batchid "
				+ " and t2.area_name=? and t2.plant=? and t2.matcode=? "
				+ " and to_char(t2.sampdate,'yyyy-MM-dd hh24:mi:ss')>=? and to_char(t2.sampdate,'yyyy-MM-dd hh24:mi:ss')<=? "
				+ " and t2.sample_Point_Id = ?"
				+ " and t4.tasktype not in ('抽样','加样') ORDER BY t1.sptestsorter,t1.sorter";
		         
		//获取表头
		List<Map<String,Object>> titleList = dao.queryListMap(sql,vo.getAreaName(),vo.getPlant(),vo.getMatCode(),vo.getStartTime(),vo.getEndTime(),vo.getSamplePointId());
		
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
	 * 获取检测结果
	 * @param params
	 * @return
	 */
	public Object[][] ordDetails(ParamsVo vo,List<String> titles){
		
		//获取样品编号
		String sql = "select distinct t2.ordno,to_char(t2.sampdate,'yyyy-MM-dd hh24:mi') sampdate,t2.pointdesc "
				+ " from CT_Q_DATA t1,orders t2,folders t3,batches t4 "
				+ " where t1.ordno=t2.ordno and t2.folderno=t3.folderno and t3.batchid=t4.batchid "
				+ " and t2.area_name=? and t2.plant=? and t2.matcode=? and to_char(t2.sampdate,'yyyy-MM-dd hh24:mi:ss')>=? and to_char(t2.sampdate,'yyyy-MM-dd hh24:mi:ss')<=? "
				+ " and t4.tasktype not in ('抽样','加样') and t2.sample_Point_Id = ? "
				+ " order by sampdate,ordno";
		
		List<Map<String,Object>> ords = dao.queryListMap(sql,vo.getAreaName(),vo.getPlant(),vo.getMatCode(),vo.getStartTime(),vo.getEndTime(),vo.getSamplePointId());
		
		int ordNum = ords.size();
		int titleNums = titles.size()+1;
		Object[][] data = new Object[titleNums][ordNum];
		List<String> ordNos = new ArrayList<String>();
		
		for(int i=0;i<ordNum;i++){
			String ordNo = ords.get(i).get("ordno").toString();
			String sampdate = ords.get(i).get("sampdate").toString();
			ordNos.add(ordNo);
			data[0][i]=sampdate;
		}
		
		//获取样品信息
		String sql2 = "select t2.ordno,t2.plant,to_char(t2.sampdate,'MM/dd hh24:mi') sampdate,t1.final finalnum,t1.sinonym,t1.units,t1.s "
				+ " from CT_Q_DATA t1,orders t2 "
				+ " where t1.ordno=t2.ordno and t2.ordno in (?) "
				+ " order by sampdate,ordno";
		
		//获取样品数据
		List<Map<String,Object>> ordList = dao.queryListMap(sql2,ordNos);
		
		for(Map<String,Object> map:ordList){
			String ordNo = map.get("ordno").toString();
			Object units = map.get("units");
			String key = map.get("sinonym").toString();
			
			if(units!=null){
				key = key + "("+units+")";
			}
			
			int row = titles.indexOf(key)+1;
			int column = ordNos.indexOf(ordNo);
			data[row][column]=map;
		}
		
		return data;
	}
}
