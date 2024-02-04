package com.fishmonitor.dashbordtool.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fishmonitor.dashbordtool.models.AnalyticEntity;

public interface Analyticsrepos extends JpaRepository<AnalyticEntity, Long>  {

}
