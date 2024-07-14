package com.spring.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateUtil {

    public static String dateFormatter() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return now.format(formatter);
    }
    
    public String registonTime() {
    	// 현재 시간을 가져옴
    	LocalDateTime now = LocalDateTime.now();
    	// 시간 형식을 지정
    	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    	// 형식에 맞게 시간을 문자열로 변환
    	String formattedNow = now.format(formatter);
    	return formattedNow;
    }
}	