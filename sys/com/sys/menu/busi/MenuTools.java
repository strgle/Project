package com.sys.menu.busi;

import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.sys.menu.pojo.SysMenu;

public class MenuTools {
	
	/**
	 * 将菜单转化为树形结构
	 * @return
	 * @throws JSONException 
	 */
	public static JSONArray menuTree(JSONArray allMenu) throws JSONException{
		JSONArray treeMenus = new JSONArray();
		for(int i=0;i<allMenu.length();i++){
			JSONObject json = allMenu.getJSONObject(i);
			if("-1".equals(json.getString("parentId"))){
				JSONArray child = MenuTools.childMenu(allMenu, json.getString("id"));
				if(child.length()>0){
					json.put("children", child);
				}
				treeMenus.put(json);
			}
		}
		return treeMenus;
	}
	
	private static JSONArray childMenu(JSONArray allMenu,String parentId) throws JSONException{
		JSONArray menus = new JSONArray();
		
		for(int i=0,ln=allMenu.length();i<ln;i++){
			JSONObject menu = allMenu.getJSONObject(i);
			if(menu.has("parentId")&&parentId.equals(menu.getString("parentId"))){
				menus.put(menu);
			}
		}
		
		for(int j=0;j<menus.length();j++){
			JSONObject subMenu = menus.getJSONObject(j);
			JSONArray subChild = MenuTools.childMenu(allMenu, subMenu.getString("id"));
			if(subChild.length()>0){
				subMenu.put("children", subChild);
			}
		}
		return menus;
	}
	
	/**
	 * 对菜单进行排序，按层级进行排序
	 */
	public static List<SysMenu> menuSort(List<SysMenu> allMenu){
		List<SysMenu> menus = new ArrayList<SysMenu>();
		for(SysMenu m:allMenu){
			if("-1".equals(m.getParentId())){
				menus.add(m);
				//追加子级菜单
				MenuTools.childMenu(menus, m,allMenu);
			}
		}
		return menus;
	}
	
	private static void childMenu(List<SysMenu> menus,SysMenu parentMenu,List<SysMenu> allMenu){
		for(SysMenu m:allMenu){
			if(parentMenu.getId().equals(m.getParentId())){
				menus.add(m);
				MenuTools.childMenu(menus, m, allMenu);
			}
		}
	}
	
}
