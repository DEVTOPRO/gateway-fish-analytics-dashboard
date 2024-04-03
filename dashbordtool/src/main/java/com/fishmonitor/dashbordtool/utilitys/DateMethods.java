package com.fishmonitor.dashbordtool.utilitys;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class DateMethods {
	public LocalDateTime dateMethod(long timeStamp) {
        Instant instant = Instant.ofEpochSecond(timeStamp);
        LocalDateTime dateTime = LocalDateTime.ofInstant(instant, ZoneOffset.UTC);
        return dateTime;
    }
	
	public int getOnlyHours(LocalDateTime dateTime) {
			if(dateTime.toLocalDate().equals(LocalDateTime.now().toLocalDate())) {			
			 return dateTime.getHour();
		}
		return -1;
	}
	

	
	public String getCompareValue(String dateTime) {
		 LocalDateTime formateDateValue = LocalDateTime.parse(dateTime, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
		   LocalDateTime today = LocalDateTime.now();
	        LocalDateTime yesterday = today.minusDays(1);
	      if (formateDateValue.toLocalDate().equals(today.toLocalDate())) {
	            return "today";
	        }else if(formateDateValue.toLocalDate().equals(yesterday.toLocalDate())){
	        	 return "yestday";
	        }else {
	        	return null;
	        }
	}
	
	public String getLastWeekDays(String dateTime) {
		 LocalDateTime formateDateValue = LocalDateTime.parse(dateTime, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
		   LocalDateTime today = LocalDateTime.now();
	        LocalDateTime yesterday = today.minusDays(8);// yesterday.toLocalDate()
	        if(yesterday.isBefore(LocalDateTime.of(formateDateValue.getYear(),formateDateValue.getMonth(),formateDateValue.getDayOfMonth(),0,0))) {
	        	return DateTimeFormatter.ofPattern("MMMM dd").format(formateDateValue);
	        }else {
	        	return null;
	        }
	}
}
