package lims.screen.services;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.core.handler.SessionFactory;

import pers.czf.dbManager.Dao;

@Service
public class LibService {
	
	@Autowired
	private Dao dao;

	public List<Map<String,Object>> toReceive(){
		//实验室待接收样品
		String sql = "select ordno,matname,plant,sample_point_id,pointdesc,to_char(sampdate,'MM-dd hh24:mi') sampdate from orders where status in ('Prelogged','Started') and DISPORDGOUP=ordno order by ordno";
		return dao.queryListMap(sql);
	}
	
	public List<Map<String,Object>> testing(){
		//检测中样品
		String sql = "select ordno,matname,plant,sample_point_id,pointdesc,to_char(sampdate,'MM-dd hh24:mi') sampdate "
				+ " from orders o where status ='Logged' or (status='Resulted' and APPRDISP is null) and DISPORDGOUP=ordno "
				+ "and exists(select 1 from ordtask ot where o.ordno=ot.ordno and ot.servgrp in (Select DISTINCT SERVGRP from  SERVGANALYST s where s.usrnam=? and s.SERVGRP<>'N/A') and ot.ts='Logged' ) order by ordno";
		return dao.queryListMap(sql,SessionFactory.getUsrName());
	}
	
	public List<Map<String,Object>> approval(){
		//待审批的样品
		String sql = "select ordno,matname,plant,sample_point_id,pointdesc,to_char(sampdate,'MM-dd hh24:mi') sampdate from ORDERS o where O.STATUS IN ('Done', 'OOS', 'OOT', 'Active', 'Logged', 'Resulted') "
				+ "and APPRSTS is NULL and O.ORDNO = O.ORDGROUP "
				+ "and exists (select 1 from ORDTASK ot where ot.TSA = 'Released' and ot.ORDNO = O.ORDNO "
				+ "AND ot.servgrp in (select servgrp from SERVGANALYST s where s.usrnam = ?))  order by ordno";
		return dao.queryListMap(sql,SessionFactory.getUsrName());
	}
	
	public Map<String,Object> xxtg(){
		String sql = "select * from screen_msg where flag='LIB'";
		Map<String,Object> msg = dao.queryMap(sql);
		return msg;
	}
	
}
