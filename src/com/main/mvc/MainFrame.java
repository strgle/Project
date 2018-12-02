package com.main.mvc;

import com.core.utils.AES;
import com.starlims.webservices.StarLimsClient;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Calendar;
import java.util.Map;
import java.util.Properties;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import pers.czf.commonUtils.ProjectUtils;
import pers.czf.dbManager.Dao;

@Controller
@RequestMapping({"framework"})
public class MainFrame
{
  private Properties p = null;
  private Calendar c = Calendar.getInstance();
  @Autowired
  private Dao dao;
  
  @RequestMapping
  public String index()
  {
    return "main/main";
  }
  
  @RequestMapping(value={"/login"}, produces={"application/json;charset=UTF-8"})
  @ResponseBody
  public String login(HttpServletRequest request)
  {
    JSONArray array = new JSONArray();
    if (this.p == null)
    {
      this.p = new Properties();
      String filePath = ProjectUtils.classFilePath("default.properties");
      try
      {
        InputStream ins = new FileInputStream(filePath);
        this.p.load(ins);
        String license = this.p.getProperty("license");
        String rq = AES.decrypt(license);
        String[] rqs = rq.split(",");
        this.c.set(1, Integer.valueOf(rqs[0]).intValue());
        this.c.set(2, Integer.valueOf(rqs[1]).intValue());
        this.c.set(5, Integer.valueOf(rqs[2]).intValue());
        this.c.set(11, Integer.valueOf(rqs[3]).intValue());
        this.c.set(12, Integer.valueOf(rqs[4]).intValue());
        this.c.set(13, Integer.valueOf(rqs[5]).intValue());
      }
      catch (Exception e1)
      {
        this.c.set(2017, 11, 20, 23, 59, 59);
      }
    }
    String userName = request.getParameter("username");
    String password = request.getParameter("password");
    if (StarLimsClient.checkUser(userName.toUpperCase(), password))
    {
      String sql = "select STATUS,FULLNAME,DEPTLIST from users where usrnam=?";
      
      Map<String, Object> userMap = this.dao.queryMap(sql,userName.toUpperCase() );
      
      String roleSql = "select wm_concat(r.role) role  from roles r,userroles ur where r.treeauth = ur.treeauth and ur.usrnam = ?";
      Map<String, Object> roleMap = this.dao.queryMap(roleSql,userName.toUpperCase() );
      
      array.put(userMap);
      array.put(roleMap);
    }
    else
    {
      array.put("用户名密码错误");
      return array.toString();
    }
    return array.toString();
  }
  
  @RequestMapping(value={"/loginOn"}, produces={"application/json;charset=UTF-8"})
  @ResponseBody
  public String loginOn(HttpServletRequest request)
  {
    JSONArray array = new JSONArray();
    String userName = request.getParameter("username");
    String dept = request.getParameter("dept");
    String role = request.getParameter("role");
    String sql = "select FULLNAME,DEPTLIST from users where usrnam=?";
    
    Map<String, Object> userMap = this.dao.queryMap(sql,userName.toUpperCase());
    request.getSession().setAttribute("fullName", userMap.get("fullname"));
    request.getSession().setAttribute("deptlist", userMap.get("deptlist"));
    request.getSession().setAttribute("usrNam", userName.toUpperCase());
    request.getSession().setAttribute("dept", dept);
    request.getSession().setAttribute("role", role);
    return array.put("main/main").toString();
  }
  
  @RequestMapping({"/loginOut"})
  public String loginOut(HttpServletRequest request, String userName, String password)
  {
    HttpSession session = request.getSession(false);
    if (session != null)
    {
      session.removeAttribute("usrNam");
      session.removeAttribute("dept");
      session.removeAttribute("deptlist");
      session.removeAttribute("role");
      session.invalidate();
    }
    return "main/login";
  }
}
