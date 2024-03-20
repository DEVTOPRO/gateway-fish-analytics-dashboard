package com.fishmonitor.dashbordtool.services;

import java.util.List;

import javax.mail.Multipart;

import com.fishmonitor.dashbordtool.models.AnnotationEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.fishmonitor.dashbordtool.Dto.FileDto;
import com.fishmonitor.dashbordtool.Dto.SampleFileDto;

public interface FileDataServicer {

	//ResponseEntity<?> fileServiceUploader(FileDto fileDto);
	ResponseEntity<?> getAllZipFiles(int count);
	ResponseEntity<?> fileServiceUploader(SampleFileDto sampleFileDto);


	byte[] createZipFile(List<AnnotationEntity> annotationEntities);
}
