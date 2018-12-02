package com.sys.menu.action;

import com.sys.menu.busi.MenuService;
import com.sys.menu.busi.MenuTools;
import com.sys.menu.pojo.SysMenu;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import pers.czf.commonUtils.MsgUtils;
import pers.czf.dbManager.Dao;

@Controller
@RequestMapping({"sys/menu"})
public class SysMeun
{
  @Autowired
  private MenuService service;
  @Autowired
  private Dao dao;
  
  @RequestMapping
  public String index(HttpServletRequest request)
  {
    String sql = "select * from sys_menu order by levels,sort";
    List<SysMenu> allMenu = this.dao.queryListObject(sql, SysMenu.class, new Object[0]);
    request.setAttribute("menuList", MenuTools.menuSort(allMenu));
    return "sys/menu/menu_list";
  }
  
  @RequestMapping({"/forAddMenu/{parentId}"})
  public String forAddMenu(HttpServletRequest request, @PathVariable("parentId") String parentId)
  {
    String sql = "select * from sys_menu order by levels,sort";
    List<SysMenu> allMenu = this.dao.queryListObject(sql, SysMenu.class, new Object[0]);
    request.setAttribute("menuList", MenuTools.menuSort(allMenu));
    request.setAttribute("parentId", parentId);
    return "sys/menu/addMenu";
  }
  
  @RequestMapping({"/forUpdate/{id}"})
  public ModelAndView forUpdate(HttpServletRequest request, @PathVariable("id") String id)
  {
    Map<String, Object> menu = this.dao.queryMap("select * from sys_menu where id = ? ", new Object[] { id });
    ModelAndView mv = new ModelAndView();
    mv.setViewName("sys/menu/addMenu");
    mv.addAllObjects(menu);
    String sql = "select * from sys_menu order by levels,sort";
    List<SysMenu> allMenu = this.dao.queryListObject(sql, SysMenu.class, new Object[0]);
    mv.addObject("menuList", MenuTools.menuSort(allMenu));
    return mv;
  }
  
  @RequestMapping(value={"queryMenu/{moduleId}"}, produces={"application/json;charset=UTF-8"})
  @ResponseBody
  public String queryMenu(HttpServletRequest request, @PathVariable("moduleId") String moduleId)
  {
    String menuStr = this.service.menuTree().toString();
    return menuStr;
  }
  
  @RequestMapping(value={"/updateTreeauth"}, produces={"application/json;charset=UTF-8"})
  @ResponseBody
  public String updateTreeauth(HttpServletRequest request)
  {
    String treeauth = request.getParameter("treeauth");
    String id = request.getParameter("id");
    String sql = "update sys_menu set treeauth=? where id=?";
    this.dao.excuteUpdate(sql, new Object[] { treeauth, id });
    return MsgUtils.success().toString();
  }
  
  @RequestMapping(value={"/getRoles"}, produces={"application/json;charset=UTF-8"})
  @ResponseBody
  public String getRoles(HttpServletRequest request)
  {
    return this.dao.queryJSONArray("select role from roles", new Object[0]).toString();
  }
  
  @RequestMapping(value={"/addMenu"}, produces={"application/json;charset=UTF-8"})
  @ResponseBody
  public String addMen(HttpServletRequest request, SysMenu menu)
  {
    if ((menu.getId() == null) || (menu.getId().equals(""))) {
      return this.service.add(menu).toString();
    }
    return this.service.update(menu).toString();
  }
  
  @RequestMapping(value={"/getMenu/{id}"}, produces={"application/json;charset=UTF-8"})
  @ResponseBody
  public String getMen(HttpServletRequest request, @PathVariable("id") String id)
  {
    return this.dao.queryJSON("select * from sys_menu where id = ?", new Object[] { id }).toString();
  }
  
  @RequestMapping(value={"/delMenu/{id}"}, produces={"application/json;charset=UTF-8"})
  @ResponseBody
  public String delMen(HttpServletRequest request, @PathVariable("id") String id)
  {
    String sql = "select count(*) from sys_menu where parent_id = ?";
    Integer count = (Integer)this.dao.queryValue(sql, Integer.class, new Object[] { id });
    if (count.intValue() > 0) {
      return MsgUtils.error("该菜单下存在子菜单，不能进行删除").toString();
    }
    this.dao.delete(SysMenu.class, id);
    return MsgUtils.success().toString();
  }
}
