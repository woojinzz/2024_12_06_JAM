package com.kwj.JAM.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Util {
	public static String dateTimeFormat(LocalDateTime dateTime) {
		
		return	DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(dateTime);
		
	}

}
