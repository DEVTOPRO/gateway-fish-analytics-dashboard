package com.fishmonitor.dashbordtool.services;

import org.springframework.http.ResponseEntity;

public interface RecordingMeth {
public ResponseEntity<?> getRecordingInfo();

public ResponseEntity<?> getRecordInfoList(String cameraName, String starDate, String endDate);

public ResponseEntity<?> getSourceVideo(String sourcePath);

public ResponseEntity<?> getListOfVideoPath(String subPath);

public ResponseEntity<?> getListOfCams();
}
