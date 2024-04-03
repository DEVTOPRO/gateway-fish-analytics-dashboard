package com.fishmonitor.dashbordtool.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fishmonitor.dashbordtool.services.AnalyticsInfo;

@RestController
@CrossOrigin("*")
@RequestMapping("/home")
public class Analytics {

	private AnalyticsInfo analyticsInfo;
	//test
	@Autowired
	public Analytics(AnalyticsInfo analyticsInfo) {
		this.analyticsInfo=analyticsInfo;
	}
	
	@GetMapping("/dailyInfo")
	public String testMethod() {
		return analyticsInfo.getTestMetho();
	}
	@GetMapping("/daywiseInfo")
	public ResponseEntity<?> dailyInfo(@RequestParam(name="CameraName",defaultValue = "all",required = false) String cameraName,@RequestParam( name="Records",defaultValue = "319412",required = false ) String records){
		return analyticsInfo.getDailyInfo(cameraName,records) ;
		
	}
	
	
}
