package pers.czf.commonUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {
	
	public static String DateFormat(Date date,String format){
		SimpleDateFormat sf = new SimpleDateFormat(format);
		return sf.format(date);		
	}
	
	public static Date FormatToDate(String dateStr,String format){
		SimpleDateFormat sf = new SimpleDateFormat(format);
		try {
			return sf.parse(dateStr);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException(e.getMessage()+"\n"+e.getCause());
		}
	}
}
