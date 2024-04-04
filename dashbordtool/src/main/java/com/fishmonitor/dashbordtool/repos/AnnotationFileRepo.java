package com.fishmonitor.dashbordtool.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fishmonitor.dashbordtool.models.AnnotationEntity;

import java.util.List;

@Repository
public interface AnnotationFileRepo extends JpaRepository<AnnotationEntity, Long> {

    List<AnnotationEntity> findByTypeOfSpecies(String speciesName);
}
