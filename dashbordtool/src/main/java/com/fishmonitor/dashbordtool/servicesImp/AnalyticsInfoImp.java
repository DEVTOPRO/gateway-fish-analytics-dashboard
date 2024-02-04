package com.fishmonitor.dashbordtool.servicesImp;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fishmonitor.dashbordtool.Dto.AnalyticDto;
import com.fishmonitor.dashbordtool.models.AnalyticEntity;
import com.fishmonitor.dashbordtool.repos.Analyticsrepos;
import com.fishmonitor.dashbordtool.services.AnalyticsInfo;

@Service
public class AnalyticsInfoImp implements AnalyticsInfo {
private Analyticsrepos analyticsrepos;

@Autowired
	public AnalyticsInfoImp(Analyticsrepos analyticsrepos) {
		this.analyticsrepos=analyticsrepos;
	}
	@Override
	public String getTestMetho() {
		// TODO Auto-generated method stub
		 List<AnalyticEntity> listAnalyticDto= analyticsrepos.findAll();
		 return listAnalyticDto.toString();
	}

}
