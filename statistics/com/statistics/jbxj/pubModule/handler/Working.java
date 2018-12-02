package com.statistics.jbxj.pubModule.handler;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;

public class Working {
	
	/**
	 * 月度开始结束时间
	 * @return
	 */
	public static String[] monthStartAndEndDay(String month) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		
		LocalDate localDate = LocalDate.parse(month+"-01");
		String starDate = localDate.withDayOfMonth(1).format(formatter);
		String endDateStr =  localDate.with(TemporalAdjusters.lastDayOfMonth()).format(formatter);
		return new String[] {starDate,endDateStr};
	}
	
	/**
	 * 月度开始结束时间
	 * @return
	 */
	public static String[] monthStartAndEndDay(int year,int month) {
		LocalDate localDate = LocalDate.of(year, month, 1);
		String starDate = localDate.withDayOfMonth(1).toString();
		String endDate =  localDate.with(TemporalAdjusters.lastDayOfMonth()).toString();
		return new String[] {starDate,endDate};
	}
	
	public static String[] monthDays(String month) {

		LocalDate localDate = LocalDate.parse(month+"-01");
		LocalDate endDate = localDate.with(TemporalAdjusters.lastDayOfMonth());
		int endday = endDate.getDayOfMonth();
		String[] days = new String[endday];
		for(int i=0;i<endday;i++) {
			if(i<9) {
				days[i] = "0"+String.valueOf(i+1);
			}else {
				days[i] = String.valueOf(i+1);
			}
			
		}
		return days;
	}
	
	/**
	 * 月度开始结束时间
	 * @return
	 */
	public static String[] naturalMonthStartAndEndDay(String flagDate,String flag ) {
		String[] startAndEnd = new String[2];
		LocalDate localDate = LocalDate.now();
		
		if(flagDate!=null&&!flagDate.equals("")) {
			localDate = LocalDate.parse(flagDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
			switch(flag) {
				case "prev":
					localDate = localDate.minusMonths(1);
					break;
				case "next":
					localDate = localDate.plusMonths(1);
					break;
				default:
					break;
			}
		}
		
		startAndEnd[0] = localDate.withDayOfMonth(1).toString();
		startAndEnd[1] = localDate.with(TemporalAdjusters.lastDayOfMonth()).toString();
	
		
		return startAndEnd;
	}
	/**
	 * 获取当前周
	 * @return
	 */
	public static String[] weekStartAndEndDay(String flagDate) {
		String[] startAndEnd = new String[2];
		LocalDate localDate = LocalDate.now();
		if(flagDate!=null&&!flagDate.equals("")) {
			localDate = LocalDate.parse(flagDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		}
		
		if(localDate.getDayOfWeek().getValue()==4) {
			startAndEnd[0] = localDate.with(TemporalAdjusters.previous(DayOfWeek.FRIDAY)).toString();
			startAndEnd[1] = localDate.toString();
		}else if(localDate.getDayOfWeek().getValue()==5) {
			startAndEnd[0] = localDate.with(TemporalAdjusters.previous(DayOfWeek.FRIDAY)).toString();
			startAndEnd[1] = localDate.with(TemporalAdjusters.previous(DayOfWeek.THURSDAY)).toString();
		}
		else {
			startAndEnd[0] = localDate.with(TemporalAdjusters.previous(DayOfWeek.FRIDAY)).toString();
			startAndEnd[1] = localDate.with(TemporalAdjusters.next(DayOfWeek.THURSDAY)).toString();
		}
		return startAndEnd;
	}
	
	public static String[] weekStartAndEndDay(String flagDate,String flag) {
		String[] startAndEnd = new String[2];
		LocalDate localDate = LocalDate.now();
		if(flagDate!=null&&!flagDate.equals("")) {
			localDate = LocalDate.parse(flagDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
			
			switch(flag) {
				case "prev":
					localDate = localDate.minusDays(7);
					break;
				case "next":
					localDate = localDate.plusDays(7);
					break;
				default:
					break;
			}
		}
		
		if(localDate.getDayOfWeek().getValue()==4) {
			startAndEnd[0] = localDate.with(TemporalAdjusters.previous(DayOfWeek.FRIDAY)).toString();
			startAndEnd[1] = localDate.toString();
		}else if(localDate.getDayOfWeek().getValue()==5) {
			startAndEnd[0] = localDate.with(TemporalAdjusters.previous(DayOfWeek.FRIDAY)).toString();
			startAndEnd[1] = localDate.with(TemporalAdjusters.previous(DayOfWeek.THURSDAY)).toString();
		}else {
			startAndEnd[0] = localDate.with(TemporalAdjusters.previous(DayOfWeek.FRIDAY)).toString();
			startAndEnd[1] = localDate.with(TemporalAdjusters.next(DayOfWeek.THURSDAY)).toString();
		}
		return startAndEnd;
	}
	
	
	public static String[] tenStartAndEndDay(String flagDate,String flag) {
		String[] startAndEnd = new String[2];
		LocalDate localDate = LocalDate.now();
		if(flagDate!=null&&!flagDate.equals("")) {
			localDate = LocalDate.parse(flagDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
			int curday = localDate.getDayOfMonth();
			switch(flag) {
				case "prev":
					if(curday<=10) {
						localDate = localDate.minusDays(curday+1);
					}else if(curday>10&&curday<=20) {
						localDate = localDate.withDayOfMonth(10);
					}else {
						localDate = localDate.withDayOfMonth(20);
					}
					
					break;
				case "next":
					if(curday<=10) {
						localDate = localDate.withDayOfMonth(20);
					}else if(curday>10&&curday<=20) {
						localDate = localDate.with(TemporalAdjusters.lastDayOfMonth());
					}else {
						localDate = localDate.with(TemporalAdjusters.lastDayOfMonth()).plusDays(1);
					}
					break;
				default:
					break;
			}
		}
		int day = localDate.getDayOfMonth();
		if(day<=10) {
			startAndEnd[0] = localDate.withDayOfMonth(1).toString();
			startAndEnd[1] = localDate.withDayOfMonth(10).toString();
		}else if(day>10&&day<=20){
			startAndEnd[0] = localDate.withDayOfMonth(11).toString();
			startAndEnd[1] = localDate.withDayOfMonth(20).toString();
		}else {
			startAndEnd[0] = localDate.withDayOfMonth(21).toString();
			startAndEnd[1] = localDate.with(TemporalAdjusters.lastDayOfMonth()).toString();
		}
		
		return startAndEnd;
	}
	
	public static void main(String[] args) {
		
	}
}
