package com.fishmonitor.dashbordtool.services;

import org.springframework.http.ResponseEntity;

public interface AnalyticsInfo {

	String getTestMetho();
	ResponseEntity<?> getDailyInfo(String cameraName, String records);
	
}
