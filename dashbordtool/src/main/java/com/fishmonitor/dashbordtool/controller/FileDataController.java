package com.fishmonitor.dashbordtool.controller;

import java.util.ArrayList;
import java.util.List;

import com.fishmonitor.dashbordtool.ExceptionHandling.FileNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.fishmonitor.dashbordtool.Dto.FileDto;
import com.fishmonitor.dashbordtool.Dto.ResponesObject;
import com.fishmonitor.dashbordtool.Dto.FileDto.FileDtoBuilder;
import com.fishmonitor.dashbordtool.Dto.SampleFileDto;
import com.fishmonitor.dashbordtool.services.FileDataServicer;

import io.swagger.models.Response;

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
	public ResponseEntity<?> fileUploader(@RequestPart("sampleFileDto") SampleFileDto sampleFileDto,@RequestPart("mediaFile") MultipartFile mediaFile, @RequestPart("xmlFile") MultipartFile xmlFile){
		System.out.println("fullname"+mediaFile.getOriginalFilename()+"General name "+mediaFile.getName());
		if(mediaFile.getOriginalFilename().endsWith(".png") && xmlFile.getOriginalFilename().endsWith(".xml")) {
			sampleFileDto.setMediaFile(mediaFile);
			sampleFileDto.setXmlFile(xmlFile);
		return fileDataServicer.fileServiceUploader(sampleFileDto); 
		}else {
			return new ResponseEntity<>(new ResponesObject(400, "error", "Please Uploade your file with an expected formate", null), HttpStatus.BAD_REQUEST);
		}
	} 
	@GetMapping(value = "/zipFiles")
	public ResponseEntity<?> getAllZipFiles(@RequestParam("count") int count) {
		return fileDataServicer.getAllZipFiles(count);
	}
	
	@PostMapping(value="/fileuploader")
	public ResponseEntity<?> fileSaver(@RequestPart("sampleFileDto")SampleFileDto sampleFileDto,@RequestPart("mediaFile") MultipartFile mediaFile){
		System.out.println(sampleFileDto.toString());
		return null;
	}
}
