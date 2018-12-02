package com.statistics.jbxj.pubModule.kit;

import java.text.NumberFormat;

public class NumberFormatKit {

	public static String LongFormat(long source,int length) {
		NumberFormat format = NumberFormat.getInstance();
		format.setMaximumIntegerDigits(length);
		format.setMinimumIntegerDigits(length);
		return format.format(source);
	}
	
}
