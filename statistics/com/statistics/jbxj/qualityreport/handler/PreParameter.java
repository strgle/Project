package com.statistics.jbxj.qualityreport.handler;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;

public class PreParameter {
	
	/**
	 * 获取本周的第一天及最后一天
	 * @return
	 */
	@SuppressWarnings("unused")
	public static String[] weekStartAndEndDay() {
		LocalDate curDate = LocalDate.now();
		
		int dayOfWeek = curDate.getDayOfWeek().getValue();
		if(dayOfWeek==0) {
			LocalDate startDate = curDate.minusDays(6);
			LocalDate endDate = curDate;
		}else if(dayOfWeek>0&&dayOfWeek<=3){
			LocalDate startDate = curDate.with(TemporalAdjusters.previous(DayOfWeek.MONDAY));
			LocalDate endDate = curDate.with(TemporalAdjusters.next(DayOfWeek.SUNDAY));
		}else {
			LocalDate startDate = curDate.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
			LocalDate endDate = curDate.with(TemporalAdjusters.next(DayOfWeek.SUNDAY));
		}
		
		//获取本周的第一天及最后一天
		System.out.println(curDate.with(TemporalAdjusters.dayOfWeekInMonth(1, DayOfWeek.MONDAY)));
		
		//获取本月的最后一个周六
		System.out.println(curDate.with(TemporalAdjusters.dayOfWeekInMonth(5, DayOfWeek.SATURDAY)));
		
		System.out.println(curDate.with(TemporalAdjusters.nextOrSame(DayOfWeek.MONDAY)));
		
		System.out.println(curDate.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY)));
		
		System.out.println(curDate.getDayOfWeek().getValue());
	
		return null;
	}
	
	public static void main(String[] args) {
		PreParameter.weekStartAndEndDay();
	}
}
