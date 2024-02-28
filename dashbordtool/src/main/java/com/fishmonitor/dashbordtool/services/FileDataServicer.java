package com.fishmonitor.dashbordtool.services;

import java.util.List;

import javax.mail.Multipart;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.fishmonitor.dashbordtool.Dto.FileDto;

public interface FileDataServicer {

	ResponseEntity<?> fileServiceUploader(FileDto fileDto);
	ResponseEntity<?> getAllZipFiles(int count);

}
