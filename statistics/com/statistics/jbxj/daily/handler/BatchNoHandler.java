package com.statistics.jbxj.daily.handler;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class BatchNoHandler {

	public static List<String> createBatchNo(String startTeamDtStr,String endTeamDtStr){
		DateTimeFormatter teamTimeformatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		LocalDateTime startDateTime = LocalDateTime.parse(startTeamDtStr,teamTimeformatter);
		LocalDateTime endDateTime = LocalDateTime.parse(endTeamDtStr, teamTimeformatter);
		
		//2、获取批号信息
		DateTimeFormatter formatterBatchNo = DateTimeFormatter.ofPattern("yyMMdd_HH");
		List<String> batchNoArray = new ArrayList<String>();
		String batchNo = startDateTime.format(formatterBatchNo)+endDateTime.minusHours(1).format(DateTimeFormatter.ofPattern("HH"));
		batchNoArray.add(batchNo);
		while(startDateTime.isBefore(endDateTime)) {
			batchNo = startDateTime.format(formatterBatchNo);
			startDateTime = startDateTime.plusHours(1);
			batchNoArray.add(batchNo);
		}
		return batchNoArray;
	}
}
