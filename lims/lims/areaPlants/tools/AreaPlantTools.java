package lims.areaPlants.tools;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import lims.areaPlants.vo.Areas;
import lims.areaPlants.vo.Materials;
import lims.areaPlants.vo.Plants;
import lims.areaPlants.vo.Points;

public class AreaPlantTools {
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
					area.put("children", AreaPlantTools.childPlant(areaPlants, areaName));
					trees.put(area);
				}
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException(e.getMessage()+"\n"+e.getCause());
		}
		return trees;
	}
	
	public static JSONArray areaPlantPointsTree(List<Map<String,Object>> points){

		JSONArray trees = new JSONArray();
		List<Areas> areas = new ArrayList<Areas>();
		Areas area = null;
		Plants plant = null;
		
		for(Map<String,Object> point:points) {
			String areaName = point.get("areaName").toString();
			String plantName = point.get("plant").toString();
			String samplePointId = point.get("samplePointId").toString();
			String description = point.get("description")==null?point.get("samplePointId").toString():point.get("description").toString();
			if(area==null||!area.getTitle().equals(areaName)) {
				area = new Areas();
				area.setId(areaName);
				area.setTitle(areaName);
				area.setType("area");
				areas.add(area);
				plant = null;
			}
			if(plant==null||!plant.getTitle().equals(plantName)) {
				plant = new Plants();
				plant.setId(plantName);
				plant.setTitle(plantName);
				plant.setType("plant");
				plant.setAreaName(areaName);
				area.getChildren().add(plant);
			}
			
			Points pointVo = new Points();
			pointVo.setId(samplePointId);
			pointVo.setTitle(description);
			pointVo.setType("point");
			pointVo.setAreaName(areaName);
			pointVo.setPlant(plantName);
			plant.getChildren().add(pointVo);
		}
		
		try {
			for(Areas areaVo:areas) {
				JSONObject areajson = new JSONObject();
				areajson.put("id", areaVo.getId());
				areajson.put("title", areaVo.getTitle());
				areajson.put("type", areaVo.getType());
				JSONArray areachildren = new JSONArray();
				for(Plants plantVo:areaVo.getChildren()) {
					JSONObject plantjson = new JSONObject();
					plantjson.put("id", plantVo.getId());
					plantjson.put("title", plantVo.getTitle());
					plantjson.put("type", plantVo.getType());
					plantjson.put("areaName", plantVo.getAreaName());
					JSONArray plantchildren = new JSONArray();
					for(Points pointVo:plantVo.getChildren()) {
						JSONObject pointjson = new JSONObject();
						pointjson.put("id", pointVo.getId());
						pointjson.put("title", pointVo.getTitle());
						pointjson.put("type", pointVo.getType());
						pointjson.put("areaName", pointVo.getAreaName());
						pointjson.put("plant", pointVo.getPlant());
						plantchildren.put(pointjson);
					}
					plantjson.put("children", plantchildren);
					areachildren.put(plantjson);
				}
				areajson.put("children", areachildren);
				trees.put(areajson);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return trees;
	}
	/**
	 * 正常的车间-装置-样品
	 * @param points
	 * @return
	 */
	public static JSONArray areaPlantMatsTree(List<Map<String,Object>> points){

		JSONArray trees = new JSONArray();
		List<Areas> areas = new ArrayList<Areas>();
		Areas area = null;
		Plants plant = null;
		
		for(Map<String,Object> point:points) {
			String areaName = point.get("areaName").toString();
			String plantName = point.get("plant").toString();
			String matCode = point.get("matcode").toString();
			String matName = point.get("matname")==null?point.get("matcode").toString():point.get("matname").toString();
			
			if(area==null||!area.getTitle().equals(areaName)) {
				area = new Areas();
				area.setId(areaName);
				area.setTitle(areaName);
				area.setType("area");
				areas.add(area);
				plant = null;
			}
			
			if(plant==null||!plant.getTitle().equals(plantName)) {
				plant = new Plants();
				plant.setId(plantName);
				plant.setTitle(plantName);
				plant.setType("plant");
				plant.setAreaName(areaName);
				area.getChildren().add(plant);
			}
			
			Materials mat = new Materials();
			mat.setId(matCode);
			mat.setTitle(matName);
			mat.setType("mat");
			mat.setAreaName(areaName);
			mat.setPlant(plantName);
			plant.getMats().add(mat);
		}
		
		try {
			for(Areas areaVo:areas) {
				JSONObject areajson = new JSONObject();
				areajson.put("id", areaVo.getId());
				areajson.put("title", areaVo.getTitle());
				areajson.put("type", areaVo.getType());
				JSONArray areachildren = new JSONArray();
				for(Plants plantVo:areaVo.getChildren()) {
					JSONObject plantjson = new JSONObject();
					plantjson.put("id", plantVo.getId());
					plantjson.put("title", plantVo.getTitle());
					plantjson.put("type", plantVo.getType());
					plantjson.put("areaName", plantVo.getAreaName());
					JSONArray plantchildren = new JSONArray();
					for(Materials matVo:plantVo.getMats()) {
						JSONObject matjson = new JSONObject();
						matjson.put("id", matVo.getId());
						matjson.put("title", matVo.getTitle());
						matjson.put("type", matVo.getType());
						matjson.put("areaName", matVo.getAreaName());
						matjson.put("plant", matVo.getPlant());
						plantchildren.put(matjson);
					}
					plantjson.put("children", plantchildren);
					areachildren.put(plantjson);
				}
				areajson.put("children", areachildren);
				trees.put(areajson);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return trees;
	}
	/**
	 * 复选框的tree-json
	 */
	public static JSONArray areaPlantMatsCheckTree(List<Map<String,Object>> points){

		JSONArray trees = new JSONArray();
		List<Areas> areas = new ArrayList<Areas>();
		Areas area = null;
		Plants plant = null;
		
		for(Map<String,Object> point:points) {
			String areaName = point.get("areaName").toString();
			String plantName = point.get("plant").toString();
			String matCode = point.get("matcode").toString();
			String matName = point.get("matname")==null?point.get("matcode").toString():point.get("matname").toString();
			
			if(area==null||!area.getTitle().equals(areaName)) {
				area = new Areas();
				
				area.setTitle(areaName);
				area.setValue(areaName);
				areas.add(area);
				plant = null;
			}
			
			if(plant==null||!plant.getTitle().equals(plantName)) {
				plant = new Plants();
				
				plant.setTitle(plantName);
				plant.setValue(plantName);
				plant.setAreaName(areaName);
				area.getChildren().add(plant);
			}
			
			Materials mat = new Materials();
			
			mat.setTitle(matName);
			mat.setValue(matCode);
			
			mat.setAreaName(areaName);
			mat.setPlant(plantName);
			plant.getMats().add(mat);
		}
		
		try {
			for(Areas areaVo:areas) {
				JSONObject areajson = new JSONObject();
				areajson.put("title", areaVo.getTitle());
				areajson.put("value", areaVo.getValue());
				JSONArray areachildren = new JSONArray();
				for(Plants plantVo:areaVo.getChildren()) {
					JSONObject plantjson = new JSONObject();
					
					plantjson.put("title", plantVo.getTitle());
					plantjson.put("value", plantVo.getValue());
					//plantjson.put("areaName", plantVo.getAreaName());
					JSONArray plantchildren = new JSONArray();
					for(Materials matVo:plantVo.getMats()) {
						JSONObject matjson = new JSONObject();
						//matjson.put("id", matVo.getId());
						matjson.put("title", matVo.getTitle());
						matjson.put("value", matVo.getValue());
						matjson.put("data", new JSONArray());
						//matjson.put("areaName", matVo.getAreaName());
						//matjson.put("plant", matVo.getPlant());
						plantchildren.put(matjson);
					}
					plantjson.put("data", plantchildren);
					areachildren.put(plantjson);
				}
				areajson.put("data", areachildren);
				trees.put(areajson);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
				plants.put(plant);
			}
			
		}
		return plants;
	}
	
}
