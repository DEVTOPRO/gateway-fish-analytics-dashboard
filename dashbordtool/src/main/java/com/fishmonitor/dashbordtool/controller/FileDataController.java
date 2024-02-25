package com.fishmonitor.dashbordtool.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fishmonitor.dashbordtool.Dto.FileDto;
import com.fishmonitor.dashbordtool.services.FileDataServicer;

@RestController
@CrossOrigin("*")
@RequestMapping("/fileRepoInformation")
public class FileDataController {

	private FileDataServicer fileDataServicer;
	
	@Autowired
	public  FileDataController(FileDataServicer fileDataServicer) {
		this.fileDataServicer=fileDataServicer;
	}
	
	@PostMapping(value="/zipFileUpload")
	public ResponseEntity<?> fileUploader( @RequestParam("userId") String userId, @RequestParam("file") MultipartFile[] files ){
		System.out.println("cjeck");
		FileDto fileDto= new FileDto();
		fileDto.setFiles(files);
		fileDto.setUserId(userId);
		return fileDataServicer.fileServiceUploader(fileDto);
	}
}
