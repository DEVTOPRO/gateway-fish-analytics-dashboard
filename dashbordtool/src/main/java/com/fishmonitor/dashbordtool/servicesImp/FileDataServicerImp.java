package com.fishmonitor.dashbordtool.servicesImp;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.mail.Multipart;

import com.fishmonitor.dashbordtool.ExceptionHandling.FileNotFoundException;
import com.fishmonitor.dashbordtool.ExceptionHandling.InsufficientFilesException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MultipartFile;

import com.fishmonitor.dashbordtool.Dto.FileDto;
import com.fishmonitor.dashbordtool.Dto.ResponesObject;
import com.fishmonitor.dashbordtool.Dto.SampleFileDto;
import com.fishmonitor.dashbordtool.models.AnnotationEntity;
import com.fishmonitor.dashbordtool.models.ZipFileEntity;
import com.fishmonitor.dashbordtool.repos.AnnotationFileRepo;
import com.fishmonitor.dashbordtool.repos.ZipFilerepos;
import com.fishmonitor.dashbordtool.services.FileDataServicer;
import org.webjars.NotFoundException;


@Service
public class FileDataServicerImp implements FileDataServicer {

	private ZipFilerepos zipFilerepos;
	private AnnotationFileRepo annotationRepo;

	@Autowired
	public FileDataServicerImp(ZipFilerepos zipFilerepos,AnnotationFileRepo annotationRepo) {
		this.zipFilerepos = zipFilerepos;
		this.annotationRepo=annotationRepo;
	}

	public ResponseEntity<?> fileServiceUploader(SampleFileDto sampleFileDto) {
		AnnotationEntity annotationEntity = new AnnotationEntity();
		System.out.println("if"+sampleFileDto.getMediaFileName()+"  "+ sampleFileDto.getXmlName());
		try {
		if (!sampleFileDto.getMediaFileName().equalsIgnoreCase(sampleFileDto.getXmlName())) {
			return new ResponseEntity<>(new ResponesObject(400, "error", "Please upload the file with same name", null), HttpStatus.BAD_REQUEST);
		} else {
				if (!sampleFileDto.getSpeciesType().isEmpty()&&!sampleFileDto.getUserId().isEmpty()) {
					annotationEntity.setImageFileName(sampleFileDto.getMediaFileName());
					annotationEntity.setUserId(sampleFileDto.getUserId());
					annotationEntity.setXmlFileName(sampleFileDto.getXmlName());
					try {
					annotationEntity.setMediaFile(sampleFileDto.getMediaFile().getBytes());
					annotationEntity.setXmlFile(sampleFileDto.getXmlFile().getBytes());
					}catch(IOException e) {
						return new ResponseEntity<>(new ResponesObject(500,"error","File Content error, Please upload the proper files",null),HttpStatus.BAD_REQUEST);
					}
					annotationEntity.setTypeOfSpecies(sampleFileDto.getSpeciesType());
					System.out.println("Data base call");
					annotationRepo.save(annotationEntity);
					return new ResponseEntity<>(new ResponesObject(200, "success", "successfully loaded", annotationEntity),
							HttpStatus.OK);
				} else {
					return new ResponseEntity<>(new ResponesObject(500, "error", "Bad request", null),
							HttpStatus.BAD_REQUEST);
				}
		}
		} catch (Exception e) {
				new ResponseEntity<>(new ResponesObject(500, "error", e.getMessage(), null), HttpStatus.BAD_REQUEST);
			}

		return null;
	}
	
	@Override
	public ResponseEntity<?> getAllZipFiles(int count) {
		try {
			List<ZipFileEntity> zipFiles = zipFilerepos.findTopNByOrderByIdAsc(count);
			if (count <= 0) {
				throw new IllegalArgumentException("Count must be greater than zero");
			}
			if (zipFiles.size() < count) {
				throw new InsufficientFilesException("Not enough zip files available");
			}

			if (count == 1) {
				zipFiles = zipFilerepos.findFirst1ByOrderByIdAsc();
			} else {
				zipFiles = zipFilerepos.findTopNByOrderByIdAsc(count);
			}

			if (zipFiles.isEmpty()) {
				throw new FileNotFoundException("No zip files found");
			}

			// Create a zip folder
			Path zipFolderPath = Files.createTempDirectory("zipFiles");
			Path zipFilePath = zipFolderPath.resolve("zipFiles.zip");
			try (ZipOutputStream zipOutputStream = new ZipOutputStream(new FileOutputStream(zipFilePath.toFile()))) {
				for (ZipFileEntity zipFile : zipFiles) {
					zipOutputStream.putNextEntry(new ZipEntry(zipFile.getOriginalFilename()));
					zipOutputStream.write(zipFile.getContent());
					zipOutputStream.closeEntry();
				}
			}

			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
			headers.setContentDispositionFormData("attachment", "zipFiles.zip");
			return new ResponseEntity<>(Files.readAllBytes(zipFilePath), headers, HttpStatus.OK);
		} catch (IllegalArgumentException ex) {
			return handleIllegalArgumentException(ex.getMessage());
		} catch (FileNotFoundException ex) {
			return handleNotFoundException(ex.getMessage());
		} catch (InsufficientFilesException ex) {
			return handleInsufficientFilesException(ex.getMessage());
		}
		catch (Exception e) {
			return handleInternalServerError();
		}
	}

	private ResponseEntity<?> handleIllegalArgumentException(String errorMessage) {
		return new ResponseEntity<>(new ResponesObject(400, "error", errorMessage, null), HttpStatus.BAD_REQUEST);
	}

	private ResponseEntity<?> handleNotFoundException(String errorMessage) {
		return new ResponseEntity<>(new ResponesObject(404, "error", errorMessage, null), HttpStatus.NOT_FOUND);
	}

	private ResponseEntity<?> handleInsufficientFilesException(String errorMessage) {
		return new ResponseEntity<>(new ResponesObject(404, "error", errorMessage, null), HttpStatus.NOT_FOUND);
	}

	private ResponseEntity<?> handleInternalServerError() {
		return new ResponseEntity<>(new ResponesObject(500, "error", "Internal Server Error", null), HttpStatus.INTERNAL_SERVER_ERROR);
	}
}