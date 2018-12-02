package lims.dataSearch.services;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pers.czf.dbManager.Dao;

@Service("dataSearch.saleService")
public class SaleService {
	
	@Autowired
	private Dao dao;
	
	/**
	 * 获取检测样品编号列表，根据采样时间进行才行
	 * 查询条件：车间、装置、采样点、样品代码，采样时间
	 * 根据样品编号
	 * @param params
	 * @return
	 */
	public List<Map<String,Object>> ordNoBySampdate(String pointId){

		String sql = "select * from ( "
				+ " select t1.ordno,to_char(t1.sampdate,'yyyy/MM/dd hh24:mi') sampdate,t1.matcode,t1.matname,t1.status,t3.batchname,"
				+ " t1.area_name,t1.plant,t1.sample_point_id,t1.pointdesc,t3.tasktype,t3.type,to_char(t1.apprdate,'yyyy/MM/dd hh24:mi:ss') apprdate "
				+ " from orders t1,folders t2,batches t3 "
				+ " where  t1.ordno=t1.ordgroup and t1.folderno=t2.folderno and t2.batchid=t3.batchid "
				+ " and t1.sample_point_id=? and t1.apprsts='Released' order by sampdate desc) where rownum<=10";
		
		return dao.queryListMap(sql, pointId);
	}
}
