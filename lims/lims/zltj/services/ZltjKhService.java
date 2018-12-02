package lims.zltj.services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.core.handler.SessionFactory;

import lims.tools.LimsTools;
import lims.zltj.vo.ChpVo;
import lims.zltj.vo.FxxmVo;
import pers.czf.commonUtils.MsgUtils;
import pers.czf.dbManager.Dao;


@Service
public class ZltjKhService {
	
	@Autowired
	private Dao dao;
	
	/**
	 * 处理时间列表
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public List<String> getDayList(String startDate,String endDate){
		List<String> dayList = new ArrayList<String>();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat format2 = new SimpleDateFormat("MM-dd");
		try {
			
			Calendar start = Calendar.getInstance();
			start.setTime(format.parse(startDate));
			
			Calendar end = Calendar.getInstance();
			end.setTime(format.parse(endDate));
			end.add(Calendar.DAY_OF_MONTH, 1);
			
			while(start.before(end)){
				String rq = format2.format(start.getTime());
				dayList.add(rq);
				start.add(Calendar.DAY_OF_MONTH, 1);
			}
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return dayList;
	}
	
	public List<FxxmVo> dealFxxmList(List<Map<String,Object>> data,String limitType){
		List<FxxmVo> rdata = new ArrayList<FxxmVo>();
		FxxmVo vo = null;
		for(Map<String,Object> map:data){
			String matCode = (String) map.get("matcode");
			String analyte = (String) map.get("analyte");
			String status = (String) map.get("status");
			String tasktype = (String) map.get("tasktype");
			String rq = (String) map.get("rq");
			String testcode = map.get("testcode").toString();
			
			if(vo==null||!vo.getMatCode().equals(matCode)||!vo.getName().equals(analyte)){
				vo = new FxxmVo(matCode,analyte);
				vo.setTestcode(testcode);
				String[] limits = LimsTools.limitChar(map.get("lowa"), map.get("higha"), map.get("lowb"),
						map.get("highb"), map.get("charlimits"));
				if("gradeA".equals(limitType)){
					vo.setZb(limits[0]);
				}else{
					if(limits[1]!=null&&!limits[1].equals("")){
						vo.setZb(limits[1]);
					}else{
						vo.setZb(limits[0]);
					}
				}
				rdata.add(vo);
			}
			
			if(!testcode.equals(vo.getTestcode())){
				vo.setTestcode(testcode);
			}
			vo.add(rq, status, tasktype,limitType);
		}
		return rdata;
	}
	

	public List<ChpVo> addToChp(List<FxxmVo> data,Map<String,String> ypMap,List<String> dateList){
		List<ChpVo> rdata = new ArrayList<ChpVo>();
		ChpVo vo = null;
		for(FxxmVo f:data){
			if(vo==null||!f.getMatCode().equals(vo.getCode())){
				vo = new ChpVo();
				vo.setCode(f.getMatCode());
				vo.setName(ypMap.get(f.getMatCode()));
				rdata.add(vo);
			}
			for(String rq:dateList){
				f.jsHgl(rq);
			}
			
			vo.addData(f);
		}
		
		return rdata;
	}
	
	public JSONObject addMkh(String ordNo,String yj){
		String sql = "insert into MKH_TJ(ordno,tj_time,tj_user,status,tj_remark) values(?,?,?,?,?)";
		dao.excuteUpdate(sql, ordNo,new Date(),SessionFactory.getUsrName(),"1",yj);
		//添加操作记录
		String flowSql = "insert into SP_FLOW(FLOW_TYPE,busi_id,active_name,active_oper,oper_time,oper_user,oper_yj) values(?,?,?,?,?,?,?)";
		dao.excuteUpdate(flowSql, "MKH",ordNo,"提交申请","添加",new Date(),SessionFactory.getUsrName(),yj);
		return MsgUtils.success("提交申请成功");
	}
	
	public JSONObject addMjy(String ordNo,String yj){
		String sql = "insert into MJY_TJ(ordno,tj_time,tj_user,status,tj_remark) values(?,?,?,?,?)";
		dao.excuteUpdate(sql, ordNo,new Date(),SessionFactory.getUsrName(),"1",yj);
		//添加操作记录
		String flowSql = "insert into SP_FLOW(FLOW_TYPE,busi_id,active_name,active_oper,oper_time,oper_user,oper_yj) values(?,?,?,?,?,?,?)";
		dao.excuteUpdate(flowSql, "MJY",ordNo,"提交申请","添加",new Date(),SessionFactory.getUsrName(),yj);
		return MsgUtils.success("提交申请成功");
	}
	
	public JSONObject tjMkh(String ordNo,String yj){
		String sql = "update MKH_TJ set tj_time=?,tj_user=?,status=?,tj_remark=? where ordno=?";
		dao.excuteUpdate(sql, new Date(),SessionFactory.getUsrName(),"1",yj,ordNo);
		
		//添加操作记录
		String flowSql = "insert into SP_FLOW(FLOW_TYPE,busi_id,active_name,active_oper,oper_time,oper_user,oper_yj) values(?,?,?,?,?,?,?)";
		dao.excuteUpdate(flowSql, "MKH",ordNo,"提交申请","提交",new Date(),SessionFactory.getUsrName(),yj);
		return MsgUtils.success("提交申请成功");
	}
	
	public JSONObject tjMjy(String ordNo,String yj){
		String sql = "update MJY_TJ set tj_time=?,tj_user=?,status=?,tj_remark=? where ordno=?";
		dao.excuteUpdate(sql, new Date(),SessionFactory.getUsrName(),"1",yj,ordNo);
		
		//添加操作记录
		String flowSql = "insert into SP_FLOW(FLOW_TYPE,busi_id,active_name,active_oper,oper_time,oper_user,oper_yj) values(?,?,?,?,?,?,?)";
		dao.excuteUpdate(flowSql, "MJY",ordNo,"提交申请","提交",new Date(),SessionFactory.getUsrName(),yj);
		return MsgUtils.success("提交申请成功");
	}
	
	public JSONObject spMkh(String ordNo,String yj){
		String sql = "update MKH_TJ set sp_time=?,sp_user=?,status=?,sp_remark=? where ordno=?";
		dao.excuteUpdate(sql, new Date(),SessionFactory.getUsrName(),"2",yj,ordNo);
		
		//添加操作记录
		String flowSql = "insert into SP_FLOW(FLOW_TYPE,busi_id,active_name,active_oper,oper_time,oper_user,oper_yj) values(?,?,?,?,?,?,?)";
		dao.excuteUpdate(flowSql, "MKH",ordNo,"审批","通过",new Date(),SessionFactory.getUsrName(),yj);
		return MsgUtils.success("审批通过");
	}
	
	public JSONObject spMjy(String ordNo,String yj){
		String sql = "update MJY_TJ set sp_time=?,sp_user=?,status=?,sp_remark=? where ordno=?";
		dao.excuteUpdate(sql, new Date(),SessionFactory.getUsrName(),"2",yj,ordNo);
		
		//添加操作记录
		String flowSql = "insert into SP_FLOW(FLOW_TYPE,busi_id,active_name,active_oper,oper_time,oper_user,oper_yj) values(?,?,?,?,?,?,?)";
		dao.excuteUpdate(flowSql, "MJY",ordNo,"审批","通过",new Date(),SessionFactory.getUsrName(),yj);
		return MsgUtils.success("审批通过");
	}
	
	public JSONObject thMkh(String ordNo,String yj){
		String sql = "update MKH_TJ set sp_time=?,sp_user=?,status=?,sp_remark=? where ordno=?";
		dao.excuteUpdate(sql, new Date(),SessionFactory.getUsrName(),"0",yj,ordNo);
		//添加操作记录
		String flowSql = "insert into SP_FLOW(FLOW_TYPE,busi_id,active_name,active_oper,oper_time,oper_user,oper_yj) values(?,?,?,?,?,?,?)";
		dao.excuteUpdate(flowSql, "MKH",ordNo,"审批","退回",new Date(),SessionFactory.getUsrName(),yj);
		return MsgUtils.success("申请已驳回");
	}
	

	public JSONObject thMjy(String ordNo,String yj){
		String sql = "update MJY_TJ set sp_time=?,sp_user=?,status=?,sp_remark=? where ordno=?";
		dao.excuteUpdate(sql, new Date(),SessionFactory.getUsrName(),"0",yj,ordNo);
		//添加操作记录
		String flowSql = "insert into SP_FLOW(FLOW_TYPE,busi_id,active_name,active_oper,oper_time,oper_user,oper_yj) values(?,?,?,?,?,?,?)";
		dao.excuteUpdate(flowSql, "MJY",ordNo,"审批","退回",new Date(),SessionFactory.getUsrName(),yj);
		return MsgUtils.success("申请已驳回");
	}
}
