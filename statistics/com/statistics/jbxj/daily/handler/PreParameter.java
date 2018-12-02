package com.statistics.jbxj.daily.handler;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class PreParameter {
	
	/**
	 * 一天开始时间
	 * @return
	 */
	public static String startDayTime() {
		LocalDate localDate = LocalDate.now().minusDays(1);
		return localDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))+" 03:00";
	}
	
	/**
	 * 一天结束时间
	 * @return
	 */
	public static String endDayTime() {
		LocalDate localDate = LocalDate.now();
		return localDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))+" 03:00";
	}
	
	/**
	 * 获取班组下班时间
	 * 班组上下班时间：11-20 ,20-03 ，03-11
	 * @param startTime
	 * @return
	 */
	public static String endTeamTime(String startTime){
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		LocalDateTime st = LocalDateTime.parse(startTime,formatter);
		String endTime = null;
		switch(st.getHour()) {
			case 3:
				endTime = st.plusHours(8).format(formatter);
				break;
			case 11:
				endTime = st.plusHours(9).format(formatter);
				break;
			case 20:
				endTime = st.plusHours(7).format(formatter);
				break;
			default:
				break;
		};
		return endTime;
	}
	

	/**
	 * 获取班组上班时间
	 * 班组上下班时间：11-20 ,20-03 ，03-11
	 * @param endTime
	 * @return
	 */
	public static String startTeamTime(String endTime){
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		LocalDateTime et = LocalDateTime.parse(endTime,formatter);
		String startTime = null;
		switch(et.getHour()) {
			case 3:
				startTime = et.minusDays(7).format(formatter);
				break;
			case 11:
				startTime = et.minusDays(8).format(formatter);
				break;
			case 20:
				startTime = et.plusHours(9).format(formatter);
				break;
			default:
				break;
		};
		return startTime;
	}
}
