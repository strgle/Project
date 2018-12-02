package lims.dataSearch.mvc;

import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.xml.namespace.QName;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.core.handler.SessionFactory;
import com.starlims.webservices.ArrayOfAnyType;
import com.starlims.webservices.GenericServices;
import com.starlims.webservices.GenericServicesSoap;

import lims.dataSearch.vo.CtOrders;
import pers.czf.commonUtils.DateUtils;
import pers.czf.commonUtils.MsgUtils;
import pers.czf.dbManager.Dao;

@Controller
@RequestMapping("lims/dataSearch/report")
public class Report {
	
	@Autowired
	private Dao dao;
	private static final QName SERVICE_NAME = new QName("http://www.starlims.com/webservices/", "GenericServices");
	  
	/**
	 * 装置日报--按采样点
	 * 针对中控样品进行查询
	 * @param request
	 * @return
	 */
	@RequestMapping
	  public String areaPlant(HttpServletRequest request)
	  {
	    request.setAttribute("title", request.getParameter("title"));
	    String startDate = request.getParameter("startDate");
	    /*if ((startDate == null) || (startDate.equals("")))
	    {
	      Calendar c = Calendar.getInstance();
	      c.add(11, -8);
	      startDate = DateUtils.DateFormat(c.getTime(), "yyyy-MM-dd");
	    }
	    request.setAttribute("startDate", startDate);*/
	    String endDate = request.getParameter("endDate");
	    /*if ((endDate == null) || (endDate.equals(""))) {
	      endDate = DateUtils.DateFormat(new Date(), "yyyy-MM-dd");
	    }
	    request.setAttribute("endDate", endDate);*/
	    Calendar cl = Calendar.getInstance();
		if(endDate==null||endDate.equals("")){
			request.setAttribute("endDate", DateUtils.DateFormat(cl.getTime(), "yyyy-MM-dd"));
		}else{
			request.setAttribute("endDate", endDate);
		}
		
		if(startDate==null||startDate.equals("")){
			cl.add(Calendar.DAY_OF_MONTH, -30);
			request.setAttribute("startDate",DateUtils.DateFormat(cl.getTime(), "yyyy-MM-dd") );
		}else{
			request.setAttribute("startDate", startDate);
		}
		
	    return "lims/dataSearch/daily/reportIndex";
	  }
	 @RequestMapping({"/oos"})
	  public String oos(HttpServletRequest request)
	  {
	    request.setAttribute("title", request.getParameter("title"));
	    String startDate = request.getParameter("startDate");
	    /*if ((startDate == null) || (startDate.equals("")))
	    {
	      Calendar c = Calendar.getInstance();
	      c.add(11, -8);
	      startDate = DateUtils.DateFormat(c.getTime(), "yyyy-MM-dd");
	    }
	    request.setAttribute("startDate", startDate);*/
	    String endDate = request.getParameter("endDate");
	    /*if ((endDate == null) || (endDate.equals(""))) {
	      endDate = DateUtils.DateFormat(new Date(), "yyyy-MM-dd");
	    }
	    request.setAttribute("endDate", endDate);*/
	    Calendar cl = Calendar.getInstance();
		if(endDate==null||endDate.equals("")){
			request.setAttribute("endDate", DateUtils.DateFormat(cl.getTime(), "yyyy-MM-dd"));
		}else{
			request.setAttribute("endDate", endDate);
		}
		
		if(startDate==null||startDate.equals("")){
			cl.add(Calendar.DAY_OF_MONTH, -30);
			request.setAttribute("startDate",DateUtils.DateFormat(cl.getTime(), "yyyy-MM-dd") );
		}else{
			request.setAttribute("startDate", startDate);
		}
		
	    return "lims/dataSearch/daily/reportIndexOOS";
	  }
	 
	 @RequestMapping({"/approve"})
	  public String approve(HttpServletRequest request)
	  {
	    request.setAttribute("title", request.getParameter("title"));
	    String startDate = request.getParameter("startDate");
	    if ((startDate == null) || (startDate.equals("")))
	    {
	      Calendar c = Calendar.getInstance();
	      c.add(11, -8);
	      startDate = DateUtils.DateFormat(c.getTime(), "yyyy-MM-dd");
	    }
	    request.setAttribute("startDate", startDate);
	    String endDate = request.getParameter("endDate");
	    if ((endDate == null) || (endDate.equals(""))) {
	      endDate = DateUtils.DateFormat(new Date(), "yyyy-MM-dd");
	    }
	    request.setAttribute("endDate", endDate);
	    
	    return "lims/dataSearch/daily/reportIndexApprove";
	  }
	 /**
		 * 报告进度查询
		 */
		@RequestMapping("/planSearch")
		public String planSearch(HttpServletRequest request) {
			String startDate = request.getParameter("startDate");
		    /*if ((startDate == null) || (startDate.equals("")))
		    {
		      Calendar c = Calendar.getInstance();
		      c.add(11, -8);
		      startDate = DateUtils.DateFormat(c.getTime(), "yyyy-MM-dd");
		    }
		    request.setAttribute("startDate", startDate);*/
		    String endDate = request.getParameter("endDate");
		    /*if ((endDate == null) || (endDate.equals(""))) {
		      endDate = DateUtils.DateFormat(new Date(), "yyyy-MM-dd");
		    }
		    request.setAttribute("endDate", endDate);*/
		    Calendar cl = Calendar.getInstance();
			if(endDate==null||endDate.equals("")){
				request.setAttribute("endDate", DateUtils.DateFormat(cl.getTime(), "yyyy-MM-dd"));
			}else{
				request.setAttribute("endDate", endDate);
			}
			
			if(startDate==null||startDate.equals("")){
				cl.add(Calendar.DAY_OF_MONTH, -30);
				request.setAttribute("startDate",DateUtils.DateFormat(cl.getTime(), "yyyy-MM-dd") );
			}else{
				request.setAttribute("startDate", startDate);
			}
			return "lims/dataSearch/daily/planSearch";
		}
	 @RequestMapping({"/done"})
	  public String done(HttpServletRequest request)
	  {
	    request.setAttribute("title", request.getParameter("title"));
	    String startDate = request.getParameter("startDate");
	    /*if ((startDate == null) || (startDate.equals("")))
	    {
	      Calendar c = Calendar.getInstance();
	      c.add(11, -8);
	      startDate = DateUtils.DateFormat(c.getTime(), "yyyy-MM-dd");
	    }
	    request.setAttribute("startDate", startDate);*/
	    String endDate = request.getParameter("endDate");
	    /*if ((endDate == null) || (endDate.equals(""))) {
	      endDate = DateUtils.DateFormat(new Date(), "yyyy-MM-dd");
	    }
	    request.setAttribute("endDate", endDate);*/
	    Calendar cl = Calendar.getInstance();
		if(endDate==null||endDate.equals("")){
			request.setAttribute("endDate", DateUtils.DateFormat(cl.getTime(), "yyyy-MM-dd"));
		}else{
			request.setAttribute("endDate", endDate);
		}
		
		if(startDate==null||startDate.equals("")){
			cl.add(Calendar.DAY_OF_MONTH, -30);
			request.setAttribute("startDate",DateUtils.DateFormat(cl.getTime(), "yyyy-MM-dd") );
		}else{
			request.setAttribute("startDate", startDate);
		}
		
	    return "lims/dataSearch/daily/reportIndexDone";
	  }
	 /**
	  * 报告进度查询
	  * @param request
	  * @return
	  */
	 @RequestMapping("/reportPlanList")
	 public String reporPlantList(HttpServletRequest request) {
		 String areaName = request.getParameter("areaName");
		request.setAttribute("areaName", areaName);
		String plant = request.getParameter("plant");
		request.setAttribute("plant", plant);
		String startDate = request.getParameter("startDate");
		request.setAttribute("startDate", startDate);
		String endDate = request.getParameter("endDate");
		request.setAttribute("endDate", endDate);
		String matcode = request.getParameter("matcode");
		request.setAttribute("matcode", matcode);
		String status = request.getParameter("status");
		request.setAttribute("status", status);
		String whr = " and 1=1 ";
		if(!status.equals("") && status!=null) {
			if(status.equals("Rejected")) {
				whr +=" and O.status in('0','Rejected')";
			}
			else {
				whr +=" and O.status = '"+status+"'";
			}
		}
		
		String sql = "select S.plant,O.ordno,m.matname,O.Description pointdesc,"
				+ "O.Grade conclusion,O.reportcreateby,ftpdir || filename url,B.Batchname,oc.ftpdir_coa || oc.filename_coa coaUrl,"
				+ "O.sampdate,O.batchno,O.status  FROM CT_ORDERS O left join FOLDERS F   on F.FOLDERNO = O.FOLDERNO "
				+ "left join BATCHES B on B.BATCHID = F.BATCHID left join ORDERS on ORDERS.ORDNO = O.ORDNO "
				+ "left join  SAMPLE_POINTS S  on O.SAMPLE_POINT_ID = S.SAMPLE_POINT_ID   "
				+ "LEFT JOIN spec_sets spec on spec.specno=O.specno "
				+ "left join materials m on m.matcode = O.Matcode "
				+ "left join ORDERSCOAFILES oc on oc.ordno = O.Ordno "
				+ "where S.area_name = ? and S.plant = ? "
				+ "and O.matcode = ?"
				+ whr 
				+ "and to_char(O.sampdate, 'yyyy-MM-dd') >= ? "
				+ "and to_char(O.sampdate, 'yyyy-MM-dd') <= ? order by O.sampdate desc";
		List<Map<String, Object>> ordList = this.dao.queryListMap(sql,  areaName, plant,matcode, startDate, endDate );
        request.setAttribute("ordList", ordList);
        
		 return "lims/dataSearch/daily/reportPlanList";
	 }
	 /**
		 * 报告计划状态
		 */
		@RequestMapping("/planDetail")
		public String planDetail(HttpServletRequest request) {
			String ordNo = request.getParameter("ordNo");
			
			String sql ="select ORDNO, case when STEPCODE='Create Report' then '生成报告'  "
					+ "when STEPCODE='Confirm Report' then '确认报告' "
					+ "when STEPCODE='Release Report' then '审批报告'  "
					+ "when STEPCODE='Reject Report' then '退回报告' "
					+ "when STEPCODE='Approve Report' then '审核报告' "
					+ "else '归档报告' end stepcode,  "
					+ "users.fullname, EVENTDATE, COMMENTS, FOLDERNO  " + 
					"							from REPORT_HISTORY,users  " + 
					"							WHERE REPORT_HISTORY.usrnam = users.usrnam "
					+ "and ORDNO = ? ORDER by EVENTDATE";
			List<Map<String,Object>> info = dao.queryListMap(sql, ordNo);
			request.setAttribute("processList", info);
			return "lims/dataSearch/daily/planDetail";
		}
	@RequestMapping("reportList")
	public String reportList(HttpServletRequest request){
		String areaName = request.getParameter("areaName");
		request.setAttribute("areaName", areaName);
		String plant = request.getParameter("plant");
		request.setAttribute("plant", plant);
		
		String startDate = request.getParameter("startDate");
		if(startDate==null||startDate.equals("")){
			Calendar c = Calendar.getInstance();
			c.add(Calendar.MONTH, -1);
			startDate = DateUtils.DateFormat(c.getTime(), "yyyy-MM-dd");
		}
		
		request.setAttribute("startDate", startDate);
		String endDate = request.getParameter("endDate");
		request.setAttribute("endDate", endDate);
		String pointId = request.getParameter("pointId");
		request.setAttribute("pointId", pointId);
		List<Map<String, Object>> ordList = new ArrayList();
	    String sql = null;
	    String type = "point";
	    if (!StringUtils.isEmpty(pointId)) {
	      type = "point";
	    } else if (!StringUtils.isEmpty(plant)) {
	      type = "plant";
	    } else if (!StringUtils.isEmpty(areaName)) {
	      type = "area";
	    }
	    switch(type){
			case "area":
				sql = "select S.plant,O.ordno,m.matname,O.Description pointdesc,O.Grade conclusion,O.reportcreateby,ftpdir || filename url,B.Batchname,"
						+ "O.sampdate,O.batchno,oc.ftpdir_coa || oc.filename_coa coaUrl  "
						+ "FROM CT_ORDERS O left join FOLDERS F   "
						+ "on F.FOLDERNO = O.FOLDERNO left join BATCHES B on B.BATCHID = F.BATCHID "
						+ "left join ORDERS on ORDERS.ORDNO = O.ORDNO left join  SAMPLE_POINTS S  "
						+ "on O.SAMPLE_POINT_ID = S.SAMPLE_POINT_ID   LEFT JOIN spec_sets spec "
						+ "on spec.specno=O.specno left join materials m on m.matcode = O.Matcode "
						+ "left join ORDERSCOAFILES oc on oc.ordno = O.Ordno where S.area_name = ? "
						+ "and o.STATUS = 'Released' and to_char(O.sampdate, 'yyyy-MM-dd') >= ? "
						+ "and to_char(O.sampdate, 'yyyy-MM-dd') <= ? "
						+ "order by O.sampdate desc";
				ordList = this.dao.queryListMap(sql, areaName, startDate, endDate );
		        request.setAttribute("ordList", ordList);
				break;
			case "plant":
				sql = "select S.plant,O.ordno,m.matname,O.Description pointdesc,"
						+ "O.Grade conclusion,O.reportcreateby,"
						+ "ftpdir || filename url,B.Batchname,"
						+ "O.sampdate,O.batchno,oc.ftpdir_coa || oc.filename_coa coaUrl  FROM CT_ORDERS O "
						+ "left join FOLDERS F   on F.FOLDERNO = O.FOLDERNO "
						+ "left join BATCHES B on B.BATCHID = F.BATCHID "
						+ "left join ORDERS on ORDERS.ORDNO = O.ORDNO "
						+ "left join  SAMPLE_POINTS S  on O.SAMPLE_POINT_ID = S.SAMPLE_POINT_ID   "
						+ "LEFT JOIN spec_sets spec on spec.specno=O.specno "
						+ "left join materials m on m.matcode = O.Matcode "
						+ "left join ORDERSCOAFILES oc on oc.ordno = O.Ordno "
						+ "where S.area_name = ? and S.plant = ? and o.STATUS = 'Released'  "
						+ "and to_char(O.sampdate, 'yyyy-MM-dd') >= ? "
						+ "and to_char(O.sampdate, 'yyyy-MM-dd') <= ? order by O.sampdate desc";
		        
		        ordList = this.dao.queryListMap(sql,  areaName, plant, startDate, endDate );
		        request.setAttribute("ordList", ordList);
				break;
			case "point":
				sql = "select S.plant,O.ordno,m.matname,O.Description pointdesc,"
						+ "O.Grade conclusion,O.reportcreateby,ftpdir || filename url,"
						+ "B.Batchname,O.sampdate,O.batchno,oc.ftpdir_coa || oc.filename_coa coaUrl  FROM CT_ORDERS O "
						+ "left join FOLDERS F   on F.FOLDERNO = O.FOLDERNO "
						+ "left join BATCHES B on B.BATCHID = F.BATCHID "
						+ "left join ORDERS on ORDERS.ORDNO = O.ORDNO "
						+ "left join  SAMPLE_POINTS S  on O.SAMPLE_POINT_ID = S.SAMPLE_POINT_ID   "
						+ "LEFT JOIN spec_sets spec on spec.specno=O.specno "
						+ "left join materials m on m.matcode = O.Matcode "
						+ "left join ORDERSCOAFILES oc on oc.ordno = O.Ordno "
						+ "where S.area_name = ? and S.plant = ? "
						+ "and S.SAMPLE_POINT_ID = ? and o.STATUS = 'Released' "
						+ "and to_char(O.sampdate, 'yyyy-MM-dd') >= ? "
						+ "and to_char(O.sampdate, 'yyyy-MM-dd') <= ? "
						+ "order by O.sampdate desc";
				ordList = this.dao.queryListMap(sql,  areaName, plant,pointId, startDate, endDate );
		        request.setAttribute("ordList", ordList);
				break;
	    }
		
		//获取车间、装置信息
		return "lims/dataSearch/daily/reportList";
	}
	@RequestMapping({"reportListApprove"})
	public String reportListApprove(HttpServletRequest request) {
		String areaName = request.getParameter("areaName");
		request.setAttribute("areaName", areaName);
		String plant = request.getParameter("plant");
		request.setAttribute("plant", plant);
		String pointId = request.getParameter("pointId");
		request.setAttribute("pointId", pointId);
		List<Map<String, Object>> ordList = new ArrayList();
	    String sql = null;
	    String type = "point";
	    if (!StringUtils.isEmpty(pointId)) {
	      type = "point";
	    } else if (!StringUtils.isEmpty(plant)) {
	      type = "plant";
	    } else if (!StringUtils.isEmpty(areaName)) {
	      type = "area";
	    }
	    
	    switch(type){
			case "area":
				sql = "select S.plant,O.ordno,m.matname,O.Description pointdesc,"
						+ "O.Grade conclusion,O.reportcreateby,"
						+ "ftpdir || filename url,B.Batchname,O.sampdate,"
						+ "O.batchno,oc.ftpdir_coa || oc.filename_coa coaUrl  FROM CT_ORDERS O left join FOLDERS F   "
						+ "on F.FOLDERNO = O.FOLDERNO left join BATCHES B "
						+ "on B.BATCHID = F.BATCHID "
						+ "left join ORDERS on ORDERS.ORDNO = O.ORDNO "
						+ "left join  SAMPLE_POINTS S  on O.SAMPLE_POINT_ID = S.SAMPLE_POINT_ID  "
						+ " LEFT JOIN spec_sets spec on spec.specno=O.specno "
						+ "left join materials m on m.matcode = O.Matcode "
						+ "left join ORDERSCOAFILES oc on oc.ordno = O.Ordno "
						+ "where S.area_name = ? and o.STATUS = 'Approved'  "
						+ "order by O.sampdate desc";
				ordList = this.dao.queryListMap(sql, areaName );
		        request.setAttribute("ordList", ordList);
				break;
			case "plant":
				sql = "select S.plant,O.ordno,m.matname,O.Description pointdesc,"
						+ "O.Grade conclusion,O.reportcreateby,"
						+ "ftpdir || filename url,B.Batchname,"
						+ "O.sampdate,O.batchno,oc.ftpdir_coa || oc.filename_coa coaUrl  FROM CT_ORDERS O "
						+ "left join FOLDERS F   on F.FOLDERNO = O.FOLDERNO "
						+ "left join BATCHES B on B.BATCHID = F.BATCHID "
						+ "left join ORDERS on ORDERS.ORDNO = O.ORDNO "
						+ "left join  SAMPLE_POINTS S  on O.SAMPLE_POINT_ID = S.SAMPLE_POINT_ID   "
						+ "LEFT JOIN spec_sets spec on spec.specno=O.specno "
						+ "left join materials m on m.matcode = O.Matcode "
						+ "left join ORDERSCOAFILES oc on oc.ordno = O.Ordno where S.area_name = ? "
						+ "and S.plant = ? and o.STATUS = 'Approved' "
						+ "order by O.sampdate desc";
		        
		        ordList = this.dao.queryListMap(sql,  areaName, plant );
		        request.setAttribute("ordList", ordList);
				break;
			case "point":
				sql = "select S.plant,O.ordno,m.matname,O.Description pointdesc,"
						+ "O.Grade conclusion,O.reportcreateby,ftpdir || filename url,"
						+ "B.Batchname,O.sampdate,O.batchno,oc.ftpdir_coa || oc.filename_coa coaUrl  "
						+ "FROM CT_ORDERS O left join FOLDERS F   on F.FOLDERNO = O.FOLDERNO "
						+ "left join BATCHES B on B.BATCHID = F.BATCHID "
						+ "left join ORDERS on ORDERS.ORDNO = O.ORDNO "
						+ "left join  SAMPLE_POINTS S  on O.SAMPLE_POINT_ID = S.SAMPLE_POINT_ID   "
						+ "LEFT JOIN spec_sets spec on spec.specno=O.specno left join materials m on m.matcode = O.Matcode "
						+ "left join ORDERSCOAFILES oc on oc.ordno = O.Ordno "
						+ "where S.area_name = ? and S.plant = ? and S.SAMPLE_POINT_ID = ? "
						+ "and o.STATUS = 'Approved'  order by O.sampdate desc";
				ordList = this.dao.queryListMap(sql,  areaName, plant,pointId );
		        request.setAttribute("ordList", ordList);
				break;
	    }
		
		return "lims/dataSearch/daily/reportListApprove";
	}
	
	@RequestMapping({"reportListDone"})
	public String reportListDone(HttpServletRequest request) {
		String areaName = request.getParameter("areaName");
	    request.setAttribute("areaName", areaName);
	    String plant = request.getParameter("plant");
	    request.setAttribute("plant", plant);
	    String pointId = request.getParameter("pointId");
	    request.setAttribute("pointId", pointId);
	    String startDate = request.getParameter("startDate");
	    request.setAttribute("startDate", startDate);
	    String endDate = request.getParameter("endDate");
	    request.setAttribute("endDate", endDate);
	    List<Map<String, Object>> ordList = new ArrayList();
	    String sql = null;
	    String type = "point";
	    if (!StringUtils.isEmpty(pointId)) {
	      type = "point";
	    } else if (!StringUtils.isEmpty(plant)) {
	      type = "plant";
	    } else if (!StringUtils.isEmpty(areaName)) {
	      type = "area";
	    }
	    switch(type){
		case "area":
			 sql = "select S.plant,O.ordno,m.matname,O.Description pointdesc,"
			 		+ "O.Grade conclusion,O.reportcreateby,ftpdir || filename url,"
			 		+ "B.Batchname,O.sampdate,O.batchno,oc.ftpdir_coa || oc.filename_coa coaUrl  "
			 		+ "FROM CT_ORDERS O left join FOLDERS F   on F.FOLDERNO = O.FOLDERNO "
			 		+ "left join BATCHES B on B.BATCHID = F.BATCHID "
			 		+ "left join ORDERS on ORDERS.ORDNO = O.ORDNO "
			 		+ "left join  SAMPLE_POINTS S  on O.SAMPLE_POINT_ID = S.SAMPLE_POINT_ID   "
			 		+ "LEFT JOIN spec_sets spec on spec.specno=O.specno "
			 		+ "left join materials m on m.matcode = O.Matcode "
			 		+ "left join ORDERSCOAFILES oc on oc.ordno = O.Ordno "
			 		+ "where S.area_name = ? and o.STATUS = 'Released' "
			 		+ "and (o.flag <> 'N' or o.flag is null or o.flag = '合格' ) "
			 		+ "and to_char(O.sampdate, 'yyyy-MM-dd') >= ? "
			 		+ "and to_char(O.sampdate, 'yyyy-MM-dd') <= ? "
			 		+ "order by O.sampdate desc";
		        
			ordList = this.dao.queryListMap(sql, areaName,startDate, endDate );
	        request.setAttribute("ordList", ordList);
			break;
		case "plant":
			 sql = "select S.plant,O.ordno,m.matname,O.Description pointdesc,"
			 		+ "O.Grade conclusion,O.reportcreateby,ftpdir || filename url,"
			 		+ "B.Batchname,O.sampdate,O.batchno,oc.ftpdir_coa || oc.filename_coa coaUrl  FROM CT_ORDERS O "
			 		+ "left join FOLDERS F   on F.FOLDERNO = O.FOLDERNO "
			 		+ "left join BATCHES B on B.BATCHID = F.BATCHID "
			 		+ "left join ORDERS on ORDERS.ORDNO = O.ORDNO "
			 		+ "left join  SAMPLE_POINTS S  on O.SAMPLE_POINT_ID = S.SAMPLE_POINT_ID  "
			 		+ " LEFT JOIN spec_sets spec on spec.specno=O.specno "
			 		+ "left join materials m on m.matcode = O.Matcode "
			 		+ "left join ORDERSCOAFILES oc on oc.ordno = O.Ordno "
			 		+ "where S.area_name = ? and S.plant = ? and o.STATUS = 'Released'"
			 		+ " and (o.flag <> 'N' or o.flag is null or o.flag = '合格' ) "
			 		+ "and to_char(O.sampdate, 'yyyy-MM-dd') >= ? "
			 		+ "and to_char(O.sampdate, 'yyyy-MM-dd') <= ? "
			 		+ "order by O.sampdate desc";
		        
	        ordList = this.dao.queryListMap(sql,  areaName, plant,startDate, endDate );
	        request.setAttribute("ordList", ordList);
			break;
		case "point":
			sql = "select S.plant,O.ordno,m.matname,O.Description pointdesc,"
					+ "O.Grade conclusion,O.reportcreateby,ftpdir || filename url,"
					+ "B.Batchname,O.sampdate,O.batchno,oc.ftpdir_coa || oc.filename_coa coaUrl  FROM CT_ORDERS O "
					+ "left join FOLDERS F   on F.FOLDERNO = O.FOLDERNO "
					+ "left join BATCHES B on B.BATCHID = F.BATCHID "
					+ "left join ORDERS on ORDERS.ORDNO = O.ORDNO "
					+ "left join  SAMPLE_POINTS S  on O.SAMPLE_POINT_ID = S.SAMPLE_POINT_ID   "
					+ "LEFT JOIN spec_sets spec on spec.specno=O.specno "
					+ "left join materials m on m.matcode = O.Matcode "
					+ "left join ORDERSCOAFILES oc on oc.ordno = O.Ordno "
					+ "where S.area_name = ? and S.plant = ? and S.SAMPLE_POINT_ID = ? "
					+ "and o.STATUS = 'Released' and (o.flag <> 'N' or o.flag is null or o.flag = '合格') "
					+ "and to_char(O.sampdate, 'yyyy-MM-dd') >= ? "
					+ "and to_char(O.sampdate, 'yyyy-MM-dd') <= ? "
					+ "order by O.sampdate desc";
		        
			ordList = this.dao.queryListMap(sql,  areaName, plant,pointId,startDate, endDate );
	        request.setAttribute("ordList", ordList);
			break;
	    }
		return "lims/dataSearch/daily/reportList";
	}
	
	@RequestMapping({"reportListOOS"})
	public String reportListOOS(HttpServletRequest request) {
		String areaName = request.getParameter("areaName");
	    request.setAttribute("areaName", areaName);
	    String plant = request.getParameter("plant");
	    request.setAttribute("plant", plant);
	    String pointId = request.getParameter("pointId");
	    request.setAttribute("pointId", pointId);
	    String startDate = request.getParameter("startDate");
	    request.setAttribute("startDate", startDate);
	    String endDate = request.getParameter("endDate");
	    request.setAttribute("endDate", endDate);
	    List<Map<String, Object>> ordList = new ArrayList();
	    String sql = null;
	    String type = "point";
	    if (!StringUtils.isEmpty(pointId)) {
	      type = "point";
	    } else if (!StringUtils.isEmpty(plant)) {
	      type = "plant";
	    } else if (!StringUtils.isEmpty(areaName)) {
	      type = "area";
	    }
	    switch(type){
		case "area":
			 sql = "select S.plant,O.ordno,m.matname,O.Description pointdesc,"
			 		+ "O.Grade conclusion,O.reportcreateby,ftpdir || filename url,"
			 		+ "B.Batchname,O.sampdate,O.batchno,oc.ftpdir_coa || oc.filename_coa coaUrl  "
			 		+ "FROM CT_ORDERS O left join FOLDERS F   on F.FOLDERNO = O.FOLDERNO"
			 		+ " left join BATCHES B on B.BATCHID = F.BATCHID "
			 		+ "left join ORDERS on ORDERS.ORDNO = O.ORDNO "
			 		+ "left join  SAMPLE_POINTS S  on O.SAMPLE_POINT_ID = S.SAMPLE_POINT_ID   "
			 		+ "LEFT JOIN spec_sets spec on spec.specno=O.specno "
			 		+ "left join materials m on m.matcode = O.Matcode "
			 		+ "left join ORDERSCOAFILES oc on oc.ordno = O.Ordno "
			 		+ "where S.area_name = ? and o.STATUS = 'Released' "
			 		+ "and (o.flag = 'N' or o.flag = '不合格' ) "
			 		+ "and to_char(O.sampdate, 'yyyy-MM-dd') >= ? "
			 		+ "and to_char(O.sampdate, 'yyyy-MM-dd') <= ? "
			 		+ "order by O.sampdate desc";
		        
			ordList = this.dao.queryListMap(sql, areaName,startDate, endDate );
	        request.setAttribute("ordList", ordList);
			break;
		case "plant":
			sql = "select S.plant,O.ordno,m.matname,O.Description pointdesc,"
					+ "O.Grade conclusion,O.reportcreateby,ftpdir || filename url,"
					+ "B.Batchname,O.sampdate,O.batchno,oc.ftpdir_coa || oc.filename_coa coaUrl  FROM CT_ORDERS O "
					+ "left join FOLDERS F   on F.FOLDERNO = O.FOLDERNO "
					+ "left join BATCHES B on B.BATCHID = F.BATCHID "
					+ "left join ORDERS on ORDERS.ORDNO = O.ORDNO "
					+ "left join  SAMPLE_POINTS S  on O.SAMPLE_POINT_ID = S.SAMPLE_POINT_ID   "
					+ "LEFT JOIN spec_sets spec on spec.specno=O.specno "
					+ "left join materials m on m.matcode = O.Matcode "
					+ "left join ORDERSCOAFILES oc on oc.ordno = O.Ordno "
					+ "where S.area_name = ? and S.plant = ? "
					+ "and o.STATUS = 'Released' and (o.flag = 'N' or o.flag = '不合格' ) "
					+ "and to_char(O.sampdate, 'yyyy-MM-dd') >= ? "
					+ "and to_char(O.sampdate, 'yyyy-MM-dd') <= ? "
					+ "order by O.sampdate desc";
	          
	        ordList = this.dao.queryListMap(sql,  areaName, plant,startDate, endDate );
	        request.setAttribute("ordList", ordList);
			break;
		case "point":
			sql = "select S.plant,O.ordno,m.matname,O.Description pointdesc,O.Grade conclusion,"
					+ "O.reportcreateby,ftpdir || filename url,B.Batchname,O.sampdate,O.batchno,oc.ftpdir_coa || oc.filename_coa coaUrl "
					+ " FROM CT_ORDERS O left join FOLDERS F   on F.FOLDERNO = O.FOLDERNO "
					+ "left join BATCHES B on B.BATCHID = F.BATCHID "
					+ "left join ORDERS on ORDERS.ORDNO = O.ORDNO "
					+ "left join  SAMPLE_POINTS S  on O.SAMPLE_POINT_ID = S.SAMPLE_POINT_ID  "
					+ " LEFT JOIN spec_sets spec on spec.specno=O.specno "
					+ "left join materials m on m.matcode = O.Matcode "
					+ "left join ORDERSCOAFILES oc on oc.ordno = O.Ordno "
					+ "where S.area_name = ? and S.plant = ? and S.SAMPLE_POINT_ID = ? "
					+ "and o.STATUS = 'Released' and (o.flag = 'N' or o.flag = '不合格' ) "
					+ "and to_char(O.sampdate, 'yyyy-MM-dd') >= ? "
					+ "and to_char(O.sampdate, 'yyyy-MM-dd') <= ? "
					+ "order by O.sampdate desc";
	           
			ordList = this.dao.queryListMap(sql,  areaName, plant,pointId,startDate, endDate );
	        request.setAttribute("ordList", ordList);
			break;
	    }
		return "lims/dataSearch/daily/reportList";
	}
	
	@RequestMapping(value={"/approveRefuse"}, produces={"application/json;charset=UTF-8"})
	@ResponseBody
	public String approveRefuse(HttpServletRequest request) {
		
		String[] selectOrders = request.getParameterValues("selectId");
	    String remark = request.getParameter("reason");
	    if (selectOrders == null) {
	      return MsgUtils.error("未选择").toString();
	    }
	    URL wsdlURL = GenericServices.wsdlUrl();
	    GenericServices ss = new GenericServices(wsdlURL, SERVICE_NAME);
	    GenericServicesSoap port = ss.getGenericServicesSoap();
	    ArrayOfAnyType array = new ArrayOfAnyType();
	    
	    List<Object> list = array.getAnyType();
	    String ordnos = remark + "," + SessionFactory.getUsrName();
	   
	    for (int i = 0; i < selectOrders.length; i++)
	    {
	      String ordno = selectOrders[i];
	      
	      ordnos = ordnos + "," + ordno;
	    }
	    list.add(ordnos);
	    port.runActionDirect("Utilities.RefuseReport", array, "SYSADM", "LIMS");
	    return MsgUtils.success().toString();
	}
	
	 @RequestMapping(value={"/approveDeal"}, produces={"application/json;charset=UTF-8"})
	 @ResponseBody
	 public String approveDeal(HttpServletRequest request) {
	    String[] selectOrders = request.getParameterValues("selectId");
	    String remark = request.getParameter("reason");
	    String status = request.getParameter("status");
	    if (selectOrders == null) {
	      return MsgUtils.error("未选择").toString();
	    }
	    URL wsdlURL = GenericServices.wsdlUrl();
	    GenericServices ss = new GenericServices(wsdlURL, SERVICE_NAME);
	    GenericServicesSoap port = ss.getGenericServicesSoap();
	    ArrayOfAnyType array = new ArrayOfAnyType();
	    
	    List<Object> list = array.getAnyType();
	    String ordnos = remark + "," + SessionFactory.getUsrName() + "," + status;
	    
	    for (int i = 0; i < selectOrders.length; i++)
	    {
	      String ordno = selectOrders[i];
	      
	      ordnos = ordnos + "," + ordno;
	    }
	    list.add(ordnos);
	    port.runActionDirect("Utilities.ApproveReport", array, "SYSADM", "LIMS");
	    return MsgUtils.success().toString();
	 }
}
