package pers.czf.commonUtils;

import org.json.JSONException;
import org.json.JSONObject;

public class MsgUtils {
	
	public static JSONObject success(){
		return MsgUtils.success("操作成功！");
	}
	
	public static JSONObject success(String successMsg){
		JSONObject json = new JSONObject();
		try {
			json.put("code", "SUCCESS");
			json.put("message", successMsg);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return json;
	}
	
	public static JSONObject fail(String failMsg){
		JSONObject json = new JSONObject();
		try {
			json.put("code", "FAIL");
			json.put("message", failMsg);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return json;
	}
	
	
	public static JSONObject error(String errorMsg){
		JSONObject json = new JSONObject();
		try {
			json.put("code", "ERROR");
			json.put("message", errorMsg);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return json;
	}
	
	public static void put(JSONObject json,String key,Object value){
		try {
			json.put(key, value);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
