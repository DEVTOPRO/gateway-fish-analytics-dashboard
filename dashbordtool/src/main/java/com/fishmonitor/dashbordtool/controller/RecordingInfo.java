package com.fishmonitor.dashbordtool.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.FileVisitOption;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

import com.fishmonitor.dashbordtool.services.RecordingMeth;
@RestController
@CrossOrigin("*")
@RequestMapping("/recordingInfo")
public class RecordingInfo {

	private RecordingMeth recordingMeth;
	
	@Autowired
	public RecordingInfo( RecordingMeth recordingMeth) {
		this.recordingMeth=recordingMeth;
	}
	@GetMapping(value="/listOfVideoinfo")
	public ResponseEntity<?> getVideoInformation(@RequestParam String cameraName,@RequestParam(required = false) String starDate,@RequestParam(required = false) String endDate ){
		return recordingMeth.getRecordInfoList(cameraName,starDate,endDate);
	}
	
	@GetMapping(value="/videoPath")
    public ResponseEntity<?> getlistVideoPaths(@RequestParam String subPath){
		return recordingMeth.getListOfVideoPath(subPath);
	}
	
	@GetMapping(value="/getRecordingClip")
	public ResponseEntity<?> getRecodingClip(@RequestParam String sourcePath){
		return recordingMeth.getSourceVideo(sourcePath);
	}
	@GetMapping(value= "/listOfCameras")
	public ResponseEntity<?> getListOfCamers(){
		return recordingMeth.getListOfCams();
	}
	
}

