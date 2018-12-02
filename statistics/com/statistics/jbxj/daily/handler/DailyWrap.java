package com.statistics.jbxj.daily.handler;

import java.sql.Timestamp;
import java.time.format.DateTimeFormatter;
import java.util.Map;

import com.statistics.jbxj.daily.vo.DailyVo;

public class DailyWrap {
	
	/**
	 * 封装基础数据
	 * @param vo
	 * @param map
	 */
	public static void baseMessage(DailyVo vo,Map<String,Object> map) {
		vo.setOrdno(map.get("ordno").toString());
		vo.setBatchno(map.get("batchno").toString());
		vo.setBrand(map.get("brand")==null?"":map.get("brand").toString());
		vo.setComments(map.get("comments")==null?"":map.get("comments").toString());
		vo.setSampDate(map.get("sampdate")==null?"":map.get("sampdate").toString());
		if(map.get("sampdate")!=null) {
			Timestamp tmpTime = (Timestamp)map.get("sampdate");
			vo.setSampDate(tmpTime.toLocalDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
		}
	}
}
