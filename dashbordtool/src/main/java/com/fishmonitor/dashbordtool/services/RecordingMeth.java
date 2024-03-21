package com.fishmonitor.dashbordtool.services;

import org.springframework.http.ResponseEntity;

import com.fishmonitor.dashbordtool.Dto.SpeciesCreationDto;
import com.fishmonitor.dashbordtool.Dto.SpeciestypeDto;

public interface RecordingMeth {
public ResponseEntity<?> getRecordingInfo();

public ResponseEntity<?> getRecordInfoList(String cameraName, String starDate, String endDate);

public ResponseEntity<?> getSourceVideo(String sourcePath);

public ResponseEntity<?> getListOfVideoPath(String subPath);

public ResponseEntity<?> getListOfCams();

public ResponseEntity<?> getTypeOfSpecies();

public ResponseEntity<?> createSpeciesType(SpeciesCreationDto speciesCreationDto);
}
