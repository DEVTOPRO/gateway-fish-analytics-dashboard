package com.fishmonitor.dashbordtool.servicesImp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.mail.Multipart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fishmonitor.dashbordtool.Dto.FileDto;
import com.fishmonitor.dashbordtool.Dto.ResponesObject;
import com.fishmonitor.dashbordtool.models.ZipFileEntity;
import com.fishmonitor.dashbordtool.repos.ZipFilerepos;
import com.fishmonitor.dashbordtool.services.FileDataServicer;

@Service
public class FileDataServicerImp implements FileDataServicer {

	private ZipFilerepos zipFilerepos;

	@Autowired
	public FileDataServicerImp(ZipFilerepos zipFilerepos) {
		this.zipFilerepos = zipFilerepos;
	}

	public ResponseEntity<?> fileServiceUploader(FileDto fileDto) {
		if (fileDto.getFiles().length < 1) {
			new ResponseEntity<>(new ResponesObject(400, "error", "Please Upload file", null), HttpStatus.BAD_REQUEST);
		} else {
			try {
				List<ZipFileEntity> fileList = new ArrayList<ZipFileEntity>();
				for (MultipartFile fileinfo : fileDto.getFiles()) {
					ZipFileEntity zipFileEntity = new ZipFileEntity();
					if (!fileinfo.getContentType().equals("application/zip")
							|| !fileinfo.getOriginalFilename().endsWith("zip")) {
						return new ResponseEntity<>(new ResponesObject(500, "error",
								"Invalid file format. Please upload a ZIP file.", null), HttpStatus.BAD_REQUEST);
					}

					try {
						zipFileEntity.setContent(fileinfo.getBytes());
					} catch (IOException e) {
						// Handle the IOException
						e.printStackTrace();
					}
					zipFileEntity.setOriginalFilename(fileinfo.getOriginalFilename());
					fileList.add(zipFileEntity);
				}

				if (!fileList.isEmpty() && fileList.size() > 0) {
					System.out.println("Data base call");
					zipFilerepos.saveAll(fileList);
				} else {
					return new ResponseEntity<>(new ResponesObject(500, "error", "Bad request", null),
							HttpStatus.BAD_REQUEST);
				}
				return new ResponseEntity<>(new ResponesObject(200, "success", "successfully loaded", fileList),
						HttpStatus.OK);

			} catch (Exception e) {
				new ResponseEntity<>(new ResponesObject(500, "error", e.getMessage(), null), HttpStatus.BAD_REQUEST);
			}

		}
		return null;
	}

}
