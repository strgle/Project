package lims.dataLedger.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import lims.dataLedger.vo.ProdData;
import pers.czf.dbManager.Dao;

@Service
public class ProdMesService {
	
	@Autowired
	private Dao dao;
	
	/**
	 * 根据设置的基础信息获取生产信息
	 */
	public void prodMes1(HttpServletRequest request,String areaName,String plant,String prodDate,String endDate){
		//获取时间
		String sql1 = "select prod_time from MES_PROD_TIME where area_name=? and plant=? order by sort_num";
		List<String> prodTimes = dao.queryListValue(sql1,String.class, areaName,plant);
		request.setAttribute("prodTimes", prodTimes.toArray());
		request.setAttribute("prodColumnNum", prodTimes.size());
		
		//获取检测项目
		String sql2 = "select prod_condition,units from MES_PROD_CONDITION where area_name=? and plant=? order by sort_num";
		List<Map<String,Object>> pcs = dao.queryListMap(sql2, areaName,plant);
				
		List<String> prodConditions = new ArrayList<String>();
		for(Map<String,Object> map:pcs){
			String prodCondition = map.get("prodCondition").toString();
			prodConditions.add(prodCondition);
		}	
		request.setAttribute("prodCondition", pcs);
				
		//获取值
		String sql3 = "select prod_condition,prod_time,prod_value "
					+ " from MES_PROD_MES where prod_date = ? and area_name = ? and plant=? order by sort_num,time_num";
		List<Map<String,Object>> values = dao.queryListMap(sql3,prodDate,areaName,plant);
				
		String[][] datas = new String[pcs.size()][prodTimes.size()];
				
		for(Map<String,Object> map:values){
			String condition = map.get("prodCondition").toString();
			String prodTime = map.get("prodTime").toString();
			String value = map.get("prodValue")==null?"":map.get("prodValue").toString();
			int row = prodConditions.indexOf(condition);
			int column = prodTimes.indexOf(prodTime);
			if(row>=0&&column>=0){
				datas[row][column]=value;
			}
		}
		request.setAttribute("datas", datas);
	}
	
	/**
	 * 根据维护的生产信息值获取具体的生产信息
	 * @param request
	 * @param areaName
	 * @param plant
	 * @param prodDate
	 */
	public void prodMes2(HttpServletRequest request,String areaName,String plant,String startDate,String endDate){
		//获取检测项目值
		String sql3 = "select prod_condition,prod_time,prod_value,units "
				+ " from MES_PROD_MES where prod_date >= ? and prod_date<=? and area_name = ? and plant=? order by prod_date,sort_num,time_num";
		List<Map<String,Object>> values = dao.queryListMap(sql3,startDate,endDate,areaName,plant);
		
		//获取时间
		List<String> prodTimes = new ArrayList<String>();
			
		//获取检测项目
		List<Map<String,Object>> pcs = new ArrayList<Map<String,Object>>();
		List<String> prodConditions = new ArrayList<String>();
				
		for(Map<String,Object> map:values){
			String condition = map.get("prodCondition").toString();
			String prodTime = map.get("prodTime").toString();
			if(!prodTimes.contains(prodTime)){
				prodTimes.add(prodTime);
			}
					
			if(!prodConditions.contains(condition)){
				prodConditions.add(condition);
				pcs.add(map);
			}
		}
				
		request.setAttribute("prodCondition", pcs);
		request.setAttribute("prodTimes", prodTimes.toArray());
		request.setAttribute("prodColumnNum", prodTimes.size());
				
		//获取值
		String[][] datas = new String[pcs.size()][prodTimes.size()];
		for(Map<String,Object> map:values){
			String condition = map.get("prodCondition").toString();
			String prodTime = map.get("prodTime").toString();
			String value = map.get("prodValue")==null?"":map.get("prodValue").toString();
			int row = prodConditions.indexOf(condition);
			int column = prodTimes.indexOf(prodTime);
			datas[row][column]=value;
		}	
		request.setAttribute("datas", datas);
	}
	
	/**
	 * 根据维护的生产信息值获取具体的生产信息
	 * @param request
	 * @param areaName
	 * @param plant
	 * @param prodDate
	 */
	public void prodListMes2(HttpServletRequest request,String areaName,String plant,String startDate,String endDate){
		//获取所有生产项目信息
		String sql1 = "select prod_condition,min(units) units,min(sort_num) num"
				+ " from MES_PROD_MES where prod_date >= ? and prod_date<=? and area_name = ? and plant=? group by prod_condition order by num";
		List<Map<String,Object>> items = dao.queryListMap(sql1,startDate,endDate,areaName,plant);
		request.setAttribute("items", items);
		
		//获取检测项目值
		String sql2 = "select prod_date,prod_condition,prod_time,prod_value,units "
				+ " from MES_PROD_MES where prod_date >= ? and prod_date<=? and area_name = ? and plant=? order by prod_date,time_num,sort_num";
		List<Map<String,Object>> values = dao.queryListMap(sql2,startDate,endDate,areaName,plant);
		
		//处理检测项目值
		List<ProdData> datas = new ArrayList<ProdData>();
		String curDate = null;
		ProdData data = null;
		for(Map<String,Object> map:values){
			String prodDate = map.get("prodDate").toString();
			String prodTime = map.get("prodTime").toString();
			String condition = map.get("prodCondition").toString();
			String value = map.get("prodValue")==null?"":map.get("prodValue").toString();
			if(!prodDate.equals(curDate)){
				curDate = prodDate;
				data = new ProdData();
				data.setProdDate(prodDate);
				datas.add(data);
			}
			int index = data.getProdTimes().indexOf(prodTime);
			if(index<0){
				data.getProdTimes().add(prodTime);
				Map<String, Object> m = new HashMap<String, Object>();
				m.put(condition, value);
				data.getItemValues().add(m);
			}else{
				data.getItemValues().get(index).put(condition, value);
			};
		}
		request.setAttribute("datas", datas);
	}
	
	/**
	 * 根据物料平衡的基础配置信息获取展示信息(编辑信息用)
	 * @param request
	 * @param areaName
	 * @param plant
	 * @param prodDate
	 */
	public void mbMes1(HttpServletRequest request,String areaName,String plant,String prodDate,String endDate){
		//获取时间
		String sql1 = "select sta_time prod_time from MES_MB_time where area_name=? and plant=? order by sort_num";
		List<String> prodTimes = dao.queryListValue(sql1,String.class, areaName,plant);
		request.setAttribute("mdTimes", prodTimes.toArray());
		int prodTimeLength = prodTimes.size();
		request.setAttribute("columnNum", prodTimeLength);
				
		//获取检测项目
		String sql2 = "select prod_condition,units,type from mes_mb_CONDITION where area_name=? and plant=? and type=? order by sort_num";
		List<Map<String,Object>> inpcs = dao.queryListMap(sql2, areaName,plant,"IN");
		List<Map<String,Object>> outpcs = dao.queryListMap(sql2, areaName,plant,"OUT");
		
		List<String> inpc = new ArrayList<String>();
		for(Map<String,Object> map:inpcs){
			String prodCondition = map.get("prodCondition").toString();
			inpc.add(prodCondition);
		}
				
		List<String> outpc = new ArrayList<String>();
		for(Map<String,Object> map:outpcs){
			String prodCondition = map.get("prodCondition").toString();
			outpc.add(prodCondition);
		}
				
		request.setAttribute("inpcs", inpcs);
		request.setAttribute("outpcs", outpcs);
		
		//获取值
		String sql3 = "select prod_condition,prod_time,prod_value1,prod_value2,type"
				+ " from MES_MB_MES where prod_date = ? and area_name = ? and plant=? order by sort_num,time_num";
		List<Map<String,Object>> values = dao.queryListMap(sql3,prodDate,areaName,plant);

		for(Map<String,Object> map:values){
			String condition = map.get("prodCondition").toString();
			String prodTime = map.get("prodTime").toString();
			String type = map.get("type").toString();
			String value1 = map.get("prodValue1")==null?"":map.get("prodValue1").toString();
			String value2 = map.get("prodValue2")==null?"":map.get("prodValue2").toString();
					
			int column = prodTimes.indexOf(prodTime);
			if("IN".equals(type)){
				int row = inpc.indexOf(condition);
				if(!inpcs.get(row).containsKey("data")){
					inpcs.get(row).put("data", new String[prodTimeLength*2]);
				};
				String[] data = (String[]) inpcs.get(row).get("data");
				data[column*2]=value1;
				data[column*2+1]=value2;
			}else{
				int row = outpc.indexOf(condition);
				if(!outpcs.get(row).containsKey("data")){
					outpcs.get(row).put("data", new String[prodTimeLength*2]);
				};
				String[] data = (String[]) outpcs.get(row).get("data");
				data[column*2]=value1;
				data[column*2+1]=value2;
			}
		}
	}
	
	/**
	 * 根据物料平衡的基础配置信息获取展示信息(展示信息用)
	 * @param request
	 * @param areaName
	 * @param plant
	 * @param prodDate
	 */
	public void mbMes0(HttpServletRequest request,String areaName,String plant,String prodDate,String endDate){
				
		//获取检测项目
		String sql2 = "select prod_condition,units,type from mes_mb_CONDITION where area_name=? and plant=? and type=? order by sort_num";
		List<Map<String,Object>> inpcs = dao.queryListMap(sql2, areaName,plant,"IN");
		List<Map<String,Object>> outpcs = dao.queryListMap(sql2, areaName,plant,"OUT");
		request.setAttribute("inItems", inpcs);
		request.setAttribute("outItems", outpcs);
		
		
		//获取时间
		String sql1 = "select sta_time prod_time from MES_MB_time where area_name=? and plant=? order by sort_num";
		List<String> prodTimes = dao.queryListValue(sql1,String.class, areaName,plant);
		
		ProdData data = new ProdData();
		data.setProdTimes(prodTimes);
		List<ProdData> inDatas = new ArrayList<ProdData>();
		inDatas.add(data);
		request.setAttribute("inDatas",inDatas );
		
		List<ProdData> outDatas = new ArrayList<ProdData>();
		outDatas.add(data);
		request.setAttribute("outDatas", outDatas);
	}
	
	/**
	 * 根据维护的物料平衡值获取展示信息
	 * @param request
	 * @param areaName
	 * @param plant
	 * @param prodDate
	 */
	public void mbMes2(HttpServletRequest request,String areaName,String plant,String startDate,String endDate){
		//获取进方检测项目值
		String sql1 = "select prod_condition,min(units) units from mes_mb_mes "
				+ " where prod_date>= ? and prod_date<=? and area_name = ? and plant=? and type='IN'"
				+ " group by prod_condition order by min(sort_num) ";
		List<Map<String,Object>> inItems = dao.queryListMap(sql1,startDate,endDate,areaName,plant);
		request.setAttribute("inItems", inItems);
		
		//获取进方值
		String sql2 = "select prod_date,PROD_TIME,prod_condition,prod_value1,prod_value2 from mes_mb_mes "
				+ " where prod_date>= ? and prod_date<=? and area_name = ? and plant=? and type='IN'"
				+ " order by prod_date,time_num,sort_num ";
		List<Map<String,Object>> inValues = dao.queryListMap(sql2,startDate,endDate,areaName,plant);
		
		//处理检测项目值
		List<ProdData> inDatas = new ArrayList<ProdData>();
		String curDate = null;
		ProdData data = null;
		for(Map<String,Object> map:inValues){
			String prodDate = map.get("prodDate").toString();
			String prodTime = map.get("prodTime").toString();
			String condition = map.get("prodCondition").toString();
			String value = map.get("prodValue1")==null?"":map.get("prodValue1").toString();
			String value2 = map.get("prodValue2")==null?"":map.get("prodValue2").toString();
			if(!prodDate.equals(curDate)){
				curDate = prodDate;
				data = new ProdData();
				data.setProdDate(prodDate);
				inDatas.add(data);
			}
			int index = data.getProdTimes().indexOf(prodTime);
			if(index<0){
				data.getProdTimes().add(prodTime);
				Map<String, Object> m = new HashMap<String, Object>();
				m.put(condition, value);
				Map<String, Object> m2 = new HashMap<String, Object>();
				m2.put(condition, value2);
				data.getItemValues().add(m);
				data.getSlValues().add(m2);
			}else{
				data.getItemValues().get(index).put(condition, value);
				data.getSlValues().get(index).put(condition, value2);
			};
		}
		request.setAttribute("inDatas", inDatas);
		
		//获取出方项目信息
		String sql3 = "select prod_condition,min(units) units from mes_mb_mes "
				+ " where prod_date>= ? and prod_date<=? and area_name = ? and plant=? and type='OUT'"
				+ " group by prod_condition order by min(sort_num) ";
		List<Map<String,Object>> outItems = dao.queryListMap(sql3,startDate,endDate,areaName,plant);
		request.setAttribute("outItems", outItems);
		
		//获取出方值
		String sql4 = "select prod_date,PROD_TIME,prod_condition,prod_value1,prod_value2 from mes_mb_mes "
						+ " where prod_date>= ? and prod_date<=? and area_name = ? and plant=? and type='OUT'"
						+ " order by prod_date,time_num,sort_num ";
		List<Map<String,Object>> outValues = dao.queryListMap(sql4,startDate,endDate,areaName,plant);
				
		//处理检测项目值
		List<ProdData> outDatas = new ArrayList<ProdData>();
		String date = null;
		ProdData odata = null;
		for(Map<String,Object> map:outValues){
			String prodDate = map.get("prodDate").toString();
			String prodTime = map.get("prodTime").toString();
			String condition = map.get("prodCondition").toString();
			String value = map.get("prodValue1")==null?"/":map.get("prodValue1").toString();
			String value2 = map.get("prodValue2")==null?"/":map.get("prodValue2").toString();
			float slv = 0f;
			try {
				slv = Float.parseFloat(value2);
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
			}
			
			if(!prodDate.equals(date)){
				date = prodDate;
				odata = new ProdData();
				odata.setProdDate(prodDate);
				outDatas.add(odata);
			}
			int index = odata.getProdTimes().indexOf(prodTime);
			if(index<0){
				odata.getProdTimes().add(prodTime);
				Map<String, Object> m = new HashMap<String, Object>();
				m.put(condition, value);
				Map<String, Object> m2 = new HashMap<String, Object>();
				m2.put(condition, value2);
				m2.put("_总收率_", slv);
				
				odata.getItemValues().add(m);
				odata.getSlValues().add(m2);
			}else{
				odata.getItemValues().get(index).put(condition, value);
				odata.getSlValues().get(index).put(condition, value2);
				odata.getSlValues().get(index).put("_总收率_",slv+(Float)odata.getSlValues().get(index).get("_总收率_"));
			};
		}
		request.setAttribute("outDatas", outDatas);
	}
}
