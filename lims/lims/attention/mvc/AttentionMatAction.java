package lims.attention.mvc;
import com.core.handler.SessionFactory;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import lims.attention.vo.AreaMat;
import lims.attention.vo.MatVo;
import lims.attention.vo.OrderResults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import pers.czf.dbManager.Dao;
@Controller
@RequestMapping("lims/attention/attentionMat")
public class AttentionMatAction
{
  @Autowired
  private Dao dao;
  
  @RequestMapping
  public String index(HttpServletRequest request)
  {
    DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    Calendar currentTime = Calendar.getInstance();
    String endDate = format.format(currentTime.getTime());
    request.setAttribute("endDate", endDate);
    currentTime.add(5, -1);
    String startDate = format.format(currentTime.getTime());
    request.setAttribute("startDate", startDate);
    String sql = "select t1.*,t2.matname from mat_setting t1,MATERIALS t2 where t1.matcode=t2.matcode and t1.usrnam=? order by area_name";
    List<Map<String, Object>> attentionMats = this.dao.queryListMap(sql, SessionFactory.getUsrName() );
    List<AreaMat> areaMatList = new ArrayList<AreaMat>();
    AreaMat areaMat = null;
    for (Map<String, Object> map : attentionMats)
    {
      MatVo vo = new MatVo();
      vo.setId((String)map.get("id"));
      vo.setMatName((String)map.get("matname"));
      vo.setLastCheck((String)map.get("lastCheck"));
      String areaName = (String)map.get("areaName");
      if ((areaMat == null) || (!areaMat.getAreaName().equals(areaName)))
      {
        areaMat = new AreaMat();
        areaMat.setAreaName(areaName);
        areaMatList.add(areaMat);
      }
      areaMat.getMatArray().add(vo);
    }
    request.setAttribute("areaMat", areaMatList);
    return "lims/zltj/attentionMat";
  }
  
  @RequestMapping("/showMat")
  public String showMat(HttpServletRequest request)
  {
    String startDate = request.getParameter("startDate");
    String endDate = request.getParameter("endDate");
    String[] checkMats = request.getParameterValues("checkMat");
    Map<String, String> ypMap = new HashMap<String, String>();
    String sql2 = "select t1.matcode,t1.matname from MATERIALS t1";
    List<Map<String, Object>> ypList = this.dao.queryListMap(sql2);
    for (Map<String, Object> map : ypList) {
      ypMap.put((String)map.get("matcode"), (String)map.get("matname"));
    }
    request.setAttribute("matMap", ypMap);
    if ((checkMats == null) || (checkMats.length == 0)) {
      return null;
    }
    String updateSql = "update mat_setting set last_check='0' where usrnam=?";
    this.dao.excuteUpdate(updateSql, SessionFactory.getUsrName() );
    
    String updateSql2 = "update mat_setting set last_check='1' where id=?";
    
    for (int i = 0; i < checkMats.length; i++)
    {
      String matId = checkMats[i];
      this.dao.excuteUpdate(updateSql2, matId );
    }
    String sqlSort = "select matcode,sinonym,units,max(sorter) sorter from sample_points t2,plantdaily t3  where t2.sample_point_id=t3.sample_point_id  and t3.sampdate>=to_date(?,'yyyy-MM-dd hh24:mi:ss') and t3.sampdate<=to_date(?,'yyyy-MM-dd hh24:mi:ss')  and exists (select 1 from mat_setting t1 where last_check='1' and t1.usrnam=? and t1.area_name=t2.area_name and t1.matcode=t3.matcode)  group by matcode,sinonym,units order by matcode,sorter";
    
    List<Map<String, Object>> columnTitle = this.dao.queryListMap(sqlSort,startDate, endDate, SessionFactory.getUsrName() );
    
    Map<String,List<String>> matTitle = new LinkedHashMap<String,List<String>>();
    List<String> l;
    for (Map<String, Object> mapTitle : columnTitle)
    {
      String title = (String)mapTitle.get("sinonym");
      String units = mapTitle.get("units") == null ? "" : (String)mapTitle.get("units");
      if (!"".equals(units)) {
        title = title + "(" + units + ")";
      }
      String matCode = (String)mapTitle.get("matcode");
      if (matTitle.containsKey(matCode))
      {
        matTitle.get(matCode).add(title);
      }
      else
      {
        l = new ArrayList<String>();
        l.add(title);
        matTitle.put(matCode, l);
      }
    }
    request.setAttribute("matTitle", matTitle);
    String sql = "select t2.area_name description,t3.matcode,t3.ordno,t3.BATCHNO,to_char(t3.SAMPDATE,'yyyy-MM-dd hh24:mi:ss' ) SAMPDATE,sinonym,units,FINAL,t3.s  from sample_points t2,plantdaily t3  where t2.sample_point_id=t3.sample_point_id  and t3.sampdate>=to_date(?,'yyyy-MM-dd hh24:mi:ss') and t3.sampdate<=to_date(?,'yyyy-MM-dd hh24:mi:ss')  and exists (select 1 from mat_setting t1 where last_check='1' and t1.usrnam=? and t1.area_name=t2.area_name and t1.matcode=t3.matcode)  order by t3.matcode,SAMPDATE";
    
    List<Map<String, Object>> dataList = this.dao.queryListMap(sql, startDate, endDate, SessionFactory.getUsrName() );
    
    Map<String, List<OrderResults>> matData = new LinkedHashMap<String, List<OrderResults>>();
    OrderResults orddata = null;
    for (Map<String, Object> map : dataList)
    {
      String matCode = (String)map.get("matcode");
      String ordno = (String)map.get("ordno");
      if ((orddata == null) || (!orddata.getOrdNo().equals(ordno)))
      {
        orddata = new OrderResults();
        orddata.setOrdNo(ordno);
        orddata.setDescription((String)map.get("description"));
        orddata.setSampdate((String)map.get("sampdate"));
        orddata.setBatchNo((String)map.get("batchno"));
        if (matData.containsKey(matCode))
        {
          matData.get(matCode).add(orddata);
        }
        else
        {
          List<OrderResults> relist = new ArrayList<OrderResults>();
          relist.add(orddata);
          matData.put(matCode, relist);
        }
      }
      String title = (String)map.get("sinonym");
      String units = map.get("units") == null ? "" : (String)map.get("units");
      if (!"".equals(units)) {
        title = title + "(" + units + ")";
      }
      String data = (String)map.get("final");
      orddata.getDataMap().put(title, data);
      orddata.getStatusMap().put(title, (String)map.get("s"));
    }
    request.setAttribute("matData", matData);
    return "lims/zltj/showMat";
  }
}

