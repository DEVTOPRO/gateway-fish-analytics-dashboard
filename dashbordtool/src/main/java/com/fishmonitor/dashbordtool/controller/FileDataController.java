package com.fishmonitor.dashbordtool.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fishmonitor.dashbordtool.ExceptionHandling.FileNotFoundException;
import com.fishmonitor.dashbordtool.models.AnnotationEntity;
import com.fishmonitor.dashbordtool.repos.AnnotationFileRepo;
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

	private final AnnotationFileRepo annotationRepo;
	private final FileDataServicer fileDataServicer;

	public FileDataController(AnnotationFileRepo annotationRepo, FileDataServicer fileDataServicer) {
		this.annotationRepo = annotationRepo;
		this.fileDataServicer = fileDataServicer;
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

	@GetMapping("/downloadFiles")
	public ResponseEntity<byte[]> downloadFiles() {
        List<AnnotationEntity> annotationEntities = annotationRepo.findAll();
        byte[] zipBytes = fileDataServicer.createZipFile(annotationEntities);
        return ResponseEntity
                .ok()
                .header("Content-Disposition", "attachment; filename=files.zip")
                .body(zipBytes);
    }

	@GetMapping(value = "/zipFiles")
	public ResponseEntity<?> getAllZipFiles(@RequestParam("count") int count) {
		return fileDataServicer.getAllZipFiles(count);
	}
	
}
