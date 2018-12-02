package com.sys.menu.pojo;

import java.util.ArrayList;
import java.util.List;

public class SysMenu
{
  private String id;
  private String code;
  private String name;
  private String remark;
  private String url;
  private String status = "1";
  private Integer levels;
  private Integer sort;
  private String parentId;
  private String icon;
  private String treeauth;
  private List<SysMenu> children = new ArrayList();
  
  public String getId()
  {
    return this.id;
  }
  
  public void setId(String id)
  {
    this.id = id;
  }
  
  public String getName()
  {
    return this.name;
  }
  
  public void setName(String name)
  {
    this.name = name;
  }
  
  public String getRemark()
  {
    return this.remark;
  }
  
  public void setRemark(String remark)
  {
    this.remark = remark;
  }
  
  public String getUrl()
  {
    return this.url;
  }
  
  public void setUrl(String url)
  {
    this.url = url;
  }
  
  public String getStatus()
  {
    return this.status;
  }
  
  public void setStatus(String status)
  {
    this.status = status;
  }
  
  public String getParentId()
  {
    return this.parentId;
  }
  
  public void setParentId(String parentId)
  {
    this.parentId = parentId;
  }
  
  public Integer getLevels()
  {
    return this.levels;
  }
  
  public void setLevels(Integer levels)
  {
    this.levels = levels;
  }
  
  public Integer getSort()
  {
    return this.sort;
  }
  
  public void setSort(Integer sort)
  {
    this.sort = sort;
  }
  
  public String getCode()
  {
    return this.code;
  }
  
  public void setCode(String code)
  {
    this.code = code;
  }
  
  public String getIcon()
  {
    return this.icon;
  }
  
  public void setIcon(String icon)
  {
    this.icon = icon;
  }
  
  public List<SysMenu> getChildren()
  {
    return this.children;
  }
  
  public void setChildren(List<SysMenu> children)
  {
    this.children = children;
  }
  
  public String getTreeauth()
  {
    return this.treeauth;
  }
  
  public void setTreeauth(String treeauth)
  {
    this.treeauth = treeauth;
  }
}
