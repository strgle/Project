package lims.dataLedger.mvc;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import lims.tools.LimsTools;
import pers.czf.commonUtils.DateUtils;
import pers.czf.dbManager.Dao;

@Controller
@RequestMapping("lims/dataLedger/care")
public class CareProductAction {
	
	@Autowired
	private Dao dao;
	
	@RequestMapping
	public String index(HttpServletRequest request){
		//获取时间
		String startRq = request.getParameter("startRq");
		if(startRq==null||startRq.equals("")){
			Calendar c = Calendar.getInstance();
			c.add(Calendar.DAY_OF_MONTH, -1);
			c.getTime();
			startRq = DateUtils.DateFormat(c.getTime(), "yyyy-MM-dd");
		}
		
		String endRq = request.getParameter("endRq");
		if(endRq==null||endRq.equals("")){
			endRq = startRq;
		}
		
		//查询成品与原辅料信息
		String sql = "select distinct b.type,o.matcode,o.matname from orders o,folders f,batches b "
				+ " where o.folderno=f.folderno and f.batchid=b.batchid and to_char(o.Sampdate,'yyyy-MM-dd')>=? and to_char(o.Sampdate,'yyyy-MM-dd')<=? "
				+ " and b.type in ('RAW','FP') and o.status in ('Done','OOS') "
				+ " order by type,matcode,matname";
		
		JSONArray list = dao.queryJSONArray(sql, startRq,endRq);
		
		String curType  = null;
		int curIndex = -1;
		JSONArray trees = new JSONArray();
		
		try {
			for(int i=0;i<list.length();i++){
				JSONObject json = list.getJSONObject(i);
				String type = json.get("type").toString();
				if(curType==null||!curType.equals(type)){
					curType = type;
					curIndex = curIndex+1;
					JSONObject matType = new JSONObject();
					matType.put("type", "matType");
					if(type.equals("RAW")){
						matType.put("title", "原辅料");
					}else{
						matType.put("title", "成品");
					}
					matType.put("id", type);
					matType.put("children",new JSONArray());
					trees.put(matType);
				}
				JSONArray children = trees.getJSONObject(curIndex).getJSONArray("children");
				String matCode = json.get("matcode").toString();
				String matName = json.get("matname").toString();
				JSONObject mat = new JSONObject();
				mat.put("type", "item");
				mat.put("title", matName);
				mat.put("id", matCode);
				mat.put("matType", type);
				children.put(mat);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException(e.getMessage()+"\n"+e.getCause());
		}
		request.setAttribute("tree", trees.toString());
		request.setAttribute("startRq", startRq);
		request.setAttribute("endRq", endRq);
		return "lims/dataLedger/care/careIndex";
	}
	
	@RequestMapping("careDetail")
	public String detail(HttpServletRequest request){
		String startRq = request.getParameter("startRq");
		String endRq = request.getParameter("endRq");
		String id = request.getParameter("id");
		//查询所有的样品信息
		List<Map<String, Object>> ords = null;
		String sql = "select o.ordno,o.matname,o.matcode,o.area_name,o.plant,o.pointdesc,to_char(o.sampdate,'yyyy-MM-dd hh24:mi:ss') sampdate,o.status,o.sampdesc,b.batchname,o.grade,"
				+ " b.tasktype "
				+ " from orders o,folders f,batches b where b.batchid=f.batchid and f.folderno=o.folderno "
				+ " and  b.type=? and to_char(o.sampdate,'yyyy-MM-dd')>=? and to_char(o.sampdate,'yyyy-MM-dd')<=? "
				+ " and o.ordno=o.ordgroup and o.status in ('Done','OOS') "
				+ " order by matcode,sampdate";
		ords = dao.queryListMap(sql, id,startRq,endRq);
		
		
		List<List<Map<String, Object>>> ordCollection = new ArrayList<List<Map<String, Object>>>();
		String sql2 = "SELECT t3.url,t1.testcode,t1.analyte,t1.sinonym,t1.analtype,t2.final final_num,t2.units,t2.s,t2.lowa,t2.lowb,t2.higha,t2.highb,t2.charlimits "
				+ "	FROM results t1 left join CT_Q_DATA t2 "
				+ " on t1.ordno=t2.ordno and t1.testcode=t2.testcode and t1.sinonym=t2.sinonym "
				+ " left join GRAPHFILE_CTI t3 on t1.ordno=t3.ordno and t1.testcode=t3.testcode "
				+ " where  t1.ordno =? and t1.countflag='Y' order by t1.sptestsorter,t1.sorter";
		
		for(Map<String,Object> ordmap:ords){
			String ordNo = ordmap.get("ordno").toString();
			List<Map<String, Object>> list = dao.queryListMap(sql2, ordNo);
			for (Map<String, Object> map : list) {
				//查询质量指标
				String[] limits = LimsTools.limitChar(map.get("lowa"), map.get("higha"), map.get("lowb"),
						map.get("highb"), map.get("charlimits"));
				map.put("limitA", limits[0]);
				map.put("limitB", limits[1]);
				
				//查询强制完成原因
				if("/".equals(map.get("finalNum"))){
					String testCode = map.get("testcode").toString();
					String sql3="select thecomment from floweventlog where stepcode='强制完成' and ordno=? and testcode=?";
					List<String> comment = dao.queryListValue(sql3, String.class, ordNo,testCode);
					if(comment.size()>0){
						map.put("isQzwc",comment.get(0));
					}
				}
			}
			
			//封装单个样品信息
			list.add(0, ordmap);
			ordCollection.add(list);
		}
		request.setAttribute("ords", ordCollection);
		request.setAttribute("startRq", startRq);
		request.setAttribute("endRq", endRq);
		return "lims/dataLedger/care/careDetail";
	}
	
	@RequestMapping("careDetail2")
	public String detail2(HttpServletRequest request){
		String startRq = request.getParameter("startRq");
		String endRq = request.getParameter("endRq");
		String id = request.getParameter("id");
		String type = request.getParameter("mattype");
		String title = request.getParameter("title");
		request.setAttribute("title", title);
		//获取分析项信息
		String sqlAnalyte = "select r.testcode,r.sinonym,min(r.sptestsorter) stepsorter,min(sorter) sorter,min(r.units) units "
				+ " from orders o,folders f,batches b,results r "
				+ " where b.batchid=f.batchid and f.folderno=o.folderno and r.ordno=o.ordno "
				+ " and  b.type=? and to_char(o.sampdate,'yyyy-MM-dd')>=? and to_char(o.sampdate,'yyyy-MM-dd')<=? and o.matcode=? "
				+ " and o.ordno=o.ordgroup and o.status in ('Done','OOS') and r.countflag='Y' "
				+ " group by testcode,sinonym order by stepsorter,sorter";
		List<Map<String,Object>> analytes = dao.queryListMap(sqlAnalyte, type,startRq,endRq,id);
		request.setAttribute("analytes", analytes);
		
		//查询所有的样品信息
		List<Map<String, Object>> ords = null;
		String sql = "select o.ordno,o.matname,o.area_name,o.plant,o.pointdesc,to_char(o.sampdate,'MM-dd hh24:mi') sampdate,o.status,o.sampdesc,b.batchname,o.grade,"
					+ " b.tasktype,b.suppcode "
					+ " from orders o,folders f,batches b where b.batchid=f.batchid and f.folderno=o.folderno "
					+ " and  b.type=? and to_char(o.sampdate,'yyyy-MM-dd')>=? and to_char(o.sampdate,'yyyy-MM-dd')<=? and o.matcode=? "
					+ " and o.ordno=o.ordgroup and o.status in ('Done','OOS') "
					+ " order by sampdate";
		ords = dao.queryListMap(sql, type,startRq,endRq,id);
		
		List<List<Map<String, Object>>> ordCollection = new ArrayList<List<Map<String, Object>>>();
		String sql2 = "SELECT t3.url,t1.testcode,t1.analyte,t1.sinonym,t1.analtype,t2.final final_num,t2.units,t2.s,t2.lowa,t2.lowb,t2.higha,t2.highb,t2.charlimits "
				+ "	FROM results t1 left join CT_Q_DATA t2 "
				+ " on t1.ordno=t2.ordno and t1.testcode=t2.testcode and t1.sinonym=t2.sinonym "
				+ " left join GRAPHFILE_CTI t3 on t1.ordno=t3.ordno and t1.testcode=t3.testcode "
				+ " where  t1.ordno =? and t1.countflag='Y' order by t1.sptestsorter,t1.sorter";
		Map<String,Object> limitA = new HashMap<String,Object>();
		Map<String,Object> limitB = new HashMap<String,Object>();
		for(Map<String,Object> ordmap:ords){
			String ordNo = ordmap.get("ordno").toString();
			List<Map<String, Object>> list = dao.queryListMap(sql2, ordNo);
			for(Map<String,Object> aMap:list){
				//查询质量指标
				String[] limits = LimsTools.limitChar(aMap.get("lowa"), aMap.get("higha"), aMap.get("lowb"),
						aMap.get("highb"), aMap.get("charlimits"));
				String sinonym = aMap.get("sinonym").toString();
				limitA.put(sinonym,limits[0]);
				limitB.put(sinonym,limits[1]);
			}
			//封装单个样品信息
			list.add(0, ordmap);
			ordCollection.add(list);
		}
		request.setAttribute("limitA", limitA);
		request.setAttribute("limitB", limitB);
		request.setAttribute("ords", ordCollection);
		request.setAttribute("startRq", startRq);
		request.setAttribute("endRq", endRq);
		request.setAttribute("matType", type);
		return "lims/dataLedger/care/careDetail2";
	}
	
}
