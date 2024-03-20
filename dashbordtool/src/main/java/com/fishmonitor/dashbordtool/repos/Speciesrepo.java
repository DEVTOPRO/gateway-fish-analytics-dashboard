package com.fishmonitor.dashbordtool.repos;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.fishmonitor.dashbordtool.Dto.SpeciestypeDto;
import com.fishmonitor.dashbordtool.models.SpeciestypesEntity;

@Repository
public interface Speciesrepo extends JpaRepository<SpeciestypesEntity, Long> {
	   @Query(value = "SELECT NEW com.fishmonitor.dashbordtool.Dto.SpeciestypeDto(specInfo.speciesKey, specInfo.speciesValue) FROM SpeciestypesEntity specInfo")
	   List<SpeciestypeDto> findAllData();
	   
	   @Query(value = "SELECT status FROM species_types specInfo WHERE specInfo.species_value = :keyValue", nativeQuery = true)
	   Integer getSpecificDetails(@Param("keyValue") String keyValue);
	  
	   @Query(value = "SELECT * FROM species_types specInfo WHERE specInfo.species_value = :keyValue", nativeQuery = true)
	   Optional<SpeciestypesEntity> getByUpdateSpeciesDetails(@Param("keyValue") String keyValue);
	   
	   @Transactional
	   @Modifying
	   @Query(value="UPDATE species_types SET species_name = :updateValue WHERE species_value = :keyValue", nativeQuery = true)
	   void findByUpdateSpeciesDetails(@Param("updateValue") String updateValue, @Param("keyValue") String keyValue);
}

