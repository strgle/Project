package lims.tools;

import java.util.HashSet;
import java.util.Set;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class LimsTools {
	/**
	 * 将菜单转化为树形结构
	 * @return
	 * @throws JSONException 
	 */
	public static JSONArray areaPlantTree(JSONArray areaPlants){
		Set<String> areaSet = new HashSet<String>();
		JSONArray trees = new JSONArray();
		try {
			for(int i=0;i<areaPlants.length();i++){
				JSONObject json = areaPlants.getJSONObject(i);
				String areaName = json.get("areaName").toString();
				if(areaSet.contains(areaName)){
					continue;
				}else{
					areaSet.add(areaName);
					JSONObject area = new JSONObject();
					area.put("type", "area");
					area.put("title", areaName);
					area.put("id", areaName);
					area.put("loaded", "yes");
					area.put("children", LimsTools.childPlant(areaPlants, areaName));
					trees.put(area);
				}
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException(e.getMessage()+"\n"+e.getCause());
		}
		return trees;
	}
	
	/**
	 * 获取车间下的装修信息
	 * @param areaPlants
	 * @param area
	 * @return
	 * @throws JSONException
	 */
	private static JSONArray childPlant(JSONArray areaPlants,String area) throws JSONException{
		JSONArray plants = new JSONArray();
		for(int i=0,ln=areaPlants.length();i<ln;i++){
			JSONObject ap = areaPlants.getJSONObject(i);
			if(ap.has("areaName")&&area.equals(ap.getString("areaName"))){
				JSONObject plant = new JSONObject();
				plant.put("type", "plant");
				plant.put("title", ap.get("plant"));
				plant.put("id",area+"@@"+ap.get("plant"));
				plant.put("loaded", "no");
				plants.put(plant);
			}
			
		}
		return plants;
	}
	
	public static String[] limitChar(Object lowa,Object higha,Object lowb,Object highb,Object charlimits){
		String[] limit = new String[2];
		
		
		if(higha!=null&&!higha.equals("")){
			limit[0] = "≤"+higha;
		}
		
		if(lowa!=null&&!lowa.equals("")){
			limit[0] = "≥"+lowa;
		}
		
		if(higha!=null&&!higha.equals("")&&lowa!=null&&!lowa.equals("")){
			limit[0] = lowa+"~"+higha;
		}
		
		if(lowb!=null&&!lowb.equals("")){
			limit[1] = "≥"+lowb;
		}
		
		if(highb!=null&&!highb.equals("")){
			limit[1] = "≤"+highb;
		}
		
		if(highb!=null&&!highb.equals("")&&lowb!=null&&!lowb.equals("")){
			limit[1] = lowb+"~"+highb;
		}
		
		if(charlimits!=null&&!charlimits.equals("")){
			limit[0] = charlimits.toString();
			limit[1] ="";
		}
		
		return limit;
	}
	public static String[] limitChar(Object lowa,Object higha,Object lowb,Object highb,Object lowc,Object highc,Object lowd,Object highd,Object charlimits){
		String[] limit = new String[4];
		
		
		if(higha!=null&&!higha.equals("")){
			limit[0] = "≤"+higha;
		}
		
		if(lowa!=null&&!lowa.equals("")){
			limit[0] = "≥"+lowa;
		}
		
		if(higha!=null&&!higha.equals("")&&lowa!=null&&!lowa.equals("")){
			limit[0] = lowa+"~"+higha;
		}
		
		if(lowb!=null&&!lowb.equals("")){
			limit[1] = "≥"+lowb;
		}
		
		if(highb!=null&&!highb.equals("")){
			limit[1] = "≤"+highb;
		}
		
		if(highb!=null&&!highb.equals("")&&lowb!=null&&!lowb.equals("")){
			limit[1] = lowb+"~"+highb;
		}
		
		if(lowc!=null&&!lowc.equals("")){
			limit[2] = "≥"+lowc;
		}
		
		if(highc!=null&&!highc.equals("")){
			limit[2] = "≤"+highc;
		}
		
		if(highc!=null&&!highc.equals("")&&lowc!=null&&!lowc.equals("")){
			limit[2] = lowc+"~"+highc;
		}
		if(lowd!=null&&!lowd.equals("")){
			limit[3] = "≥"+lowd;
		}
		
		if(highd!=null&&!highd.equals("")){
			limit[3] = "≤"+highd;
		}
		
		if(highd!=null&&!highd.equals("")&&lowd!=null&&!lowd.equals("")){
			limit[3] = lowd+"~"+highd;
		}
		if(charlimits!=null&&!charlimits.equals("")){
			limit[0] = charlimits.toString();
			limit[1] ="";
			limit[2] ="";
			limit[3] ="";
		}
		
		return limit;
	}
	public static String[] limitChar(Object lowa,Object higha,Object lowb,Object highb,Object lowc,Object highc,Object charlimits){
		String[] limit = new String[3];
		
		
		if(higha!=null&&!higha.equals("")){
			limit[0] = "≤"+higha;
		}
		
		if(lowa!=null&&!lowa.equals("")){
			limit[0] = "≥"+lowa;
		}
		
		if(higha!=null&&!higha.equals("")&&lowa!=null&&!lowa.equals("")){
			limit[0] = lowa+"~"+higha;
		}
		
		if(lowb!=null&&!lowb.equals("")){
			limit[1] = "≥"+lowb;
		}
		
		if(highb!=null&&!highb.equals("")){
			limit[1] = "≤"+highb;
		}
		
		if(highb!=null&&!highb.equals("")&&lowb!=null&&!lowb.equals("")){
			limit[1] = lowb+"~"+highb;
		}
		if(lowc!=null&&!lowc.equals("")){
			limit[2] = "≥"+lowc;
		}
		
		if(highc!=null&&!highc.equals("")){
			limit[2] = "≤"+highc;
		}
		
		if(highc!=null&&!highc.equals("")&&lowc!=null&&!lowc.equals("")){
			limit[2] = lowc+"~"+highc;
		}
		if(charlimits!=null&&!charlimits.equals("")){
			limit[0] = charlimits.toString();
			limit[1] ="";
			limit[2] ="";
		}
		
		return limit;
	}
}
