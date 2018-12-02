package com.sys.menu.busi;

import com.core.handler.SessionFactory;
import com.core.utils.KeyUtils;
import com.sys.menu.pojo.SysMenu;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pers.czf.commonUtils.MsgUtils;
import pers.czf.dbManager.Dao;

@Service
public class MenuService
{
  @Autowired
  private Dao dao;
  
  public JSONArray menuTree()
  {
    try
    {
      String query = "select id,name title,icon,url href,parent_id,remark from sys_menu where instr(treeauth,?)>0  order by levels,sort ";
      
      JSONArray allMenu = this.dao.queryJSONArray(query, new Object[] { SessionFactory.getRole() });
      
      JSONArray limitMenu = new JSONArray();
      
      int i = 0;
      for (int length = allMenu.length(); i < length; i++)
      {
        JSONObject json = allMenu.getJSONObject(i);
        limitMenu.put(json);
      }
      return MenuTools.menuTree(limitMenu);
    }
    catch (JSONException e)
    {
      throw new RuntimeException(e.getMessage());
    }
  }
  
  public JSONObject update(SysMenu menu)
  {
    SysMenu oldMenu = (SysMenu)this.dao.read(SysMenu.class, menu.getId());
    if (!oldMenu.getParentId().equals(menu.getParentId()))
    {
      if ("-1".equals(menu.getParentId()))
      {
        menu.setLevels(Integer.valueOf(0));
      }
      else
      {
        String sql1 = "select levels+1 from sys_menu where id = ?";
        Integer level = (Integer)this.dao.queryValue(sql1, Integer.class, new Object[] { menu.getParentId() });
        menu.setLevels(level);
      }
      if (menu.getSort() == null)
      {
        String sql = "select max(sort)+1 from sys_menu where parent_id = ?";
        Integer sort = (Integer)this.dao.queryValue(sql, Integer.class, new Object[] { menu.getParentId() });
        menu.setSort(sort);
      }
    }
    this.dao.update(menu);
    JSONObject json = MsgUtils.success();
    MsgUtils.put(json, "id", menu.getId());
    return json;
  }
  
  public JSONObject add(SysMenu menu)
  {
    if ("-1".equals(menu.getParentId()))
    {
      menu.setLevels(Integer.valueOf(0));
    }
    else
    {
      String sql1 = "select levels+1 from sys_menu where id = ?";
      Integer level = (Integer)this.dao.queryValue(sql1, Integer.class, new Object[] { menu.getParentId() });
      menu.setLevels(level);
    }
    String sql = "select max(sort)+1 from sys_menu where parent_id = ?";
    Integer sort = (Integer)this.dao.queryValue(sql, Integer.class, new Object[] { menu.getParentId() });
    if (sort == null) {
      menu.setSort(Integer.valueOf(1));
    } else {
      menu.setSort(sort);
    }
    menu.setId(KeyUtils.uuid());
    this.dao.create(menu);
    JSONObject json = MsgUtils.success();
    MsgUtils.put(json, "id", menu.getId());
    return json;
  }
}
