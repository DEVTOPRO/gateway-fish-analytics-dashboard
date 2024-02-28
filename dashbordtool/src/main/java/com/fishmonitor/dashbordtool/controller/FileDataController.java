package com.fishmonitor.dashbordtool.controller;

import java.util.ArrayList;
import java.util.List;

import com.fishmonitor.dashbordtool.ExceptionHandling.FileNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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
		System.out.println("check");
		FileDto fileDto= new FileDto();
		fileDto.setFiles(files);
		fileDto.setUserId(userId);
		return fileDataServicer.fileServiceUploader(fileDto);
	}
	@GetMapping(value = "/zipFiles")
	public ResponseEntity<?> getAllZipFiles(@RequestParam("count") int count) {
		return fileDataServicer.getAllZipFiles(count);
	}

}
