package com.fishmonitor.dashbordtool.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fishmonitor.dashbordtool.models.AnnotationEntity;

@Repository
public interface AnnotationFileRepo extends JpaRepository<AnnotationEntity, Long> {

}
