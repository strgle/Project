package com.core.utils;

import java.util.UUID;
import org.springframework.stereotype.Component;

@Component
public class KeyUtils {

	public static String uuid(){
		String uuid = UUID.randomUUID().toString(); 
		return uuid.replaceAll("-", "");
	}
	
}
