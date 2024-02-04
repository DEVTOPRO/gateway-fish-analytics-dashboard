package com.fishmonitor.dashbordtool.servicesImp;

import java.nio.file.DirectoryStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fishmonitor.dashbordtool.Dto.HourSummaryDTO;
import com.fishmonitor.dashbordtool.Dto.RecordSummaryDTO;
import com.fishmonitor.dashbordtool.Dto.ResponesObject;
import com.fishmonitor.dashbordtool.services.RecordingMeth;
import com.fishmonitor.dashbordtool.utilitys.FileInfo;

@Service
public class RecordingInfoImp implements RecordingMeth {

	RestTemplate restTemplate = new RestTemplate();
	public static String fileRootPath = "C:/Users/konda/Downloads/Frigate-NVR-Streamer/Frigate-NVR-Streamer/media";

	public static String buildPath(String... pathElements) {
		if (pathElements.length == 0) {
			throw new IllegalArgumentException("At least two paths are required.");
		}
		if (pathElements.length < 2) {
			return FileSystems.getDefault().getPath(fileRootPath, pathElements[0]).toString();
		} else {
			return FileSystems.getDefault().getPath(pathElements[0], pathElements[1]).toString();
		}

	}

	@Override
	public ResponseEntity<?> getRecordingInfo() {
		// TODO Auto-generated method stub
		FileInfo fileInfo = new FileInfo();
		return new ResponseEntity<>(new ResponesObject(200, "success", "success", fileInfo), HttpStatus.ACCEPTED);
	}

	@Override
	public ResponseEntity<?> getRecordInfoList(String cameraName, String starDate, String endDate) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Object> requestEntity = new HttpEntity<>(headers);
		String url = "http://localhost:5000/api/%s/recordings/summary";
		HashMap map = new HashMap();
		ResponseEntity<String> responesData = restTemplate.exchange(String.format(url, cameraName), HttpMethod.GET,
				requestEntity, String.class);
		String userInfo = responesData.getBody();
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			// For an array of objects
			List<RecordSummaryDTO> recordSummaryDTOList = objectMapper.readValue(userInfo,
					new TypeReference<List<RecordSummaryDTO>>() {
					});
			List<Map<String, Object>> dayAndHourList = recordSummaryDTOList.stream().map(entry -> {
				HashMap<String, Object> mapData = new HashMap<String, Object>();
				mapData.put("day", entry.getDay());
				List<HourSummaryDTO> hoursList = (List<HourSummaryDTO>) entry.getHours();
				List<Map<String, Object>> hourValues = hoursList.stream().map(hourEntry -> {
					HashMap<String, Object> hoursInfo = new HashMap<String, Object>();
					hoursInfo.put("hour", hourEntry.getHour());
					String pathRef = "/recordings/" + entry.getDay() + "/" + hourEntry.getHour() + "/" + cameraName;
					hoursInfo.put("path", pathRef);
					return hoursInfo;
				}).collect(Collectors.toList());
				mapData.put("hours", hourValues);
				return mapData;
			}).collect(Collectors.toList());
			return new ResponseEntity<>(new ResponesObject(200, "success", "Successfully loaded data", dayAndHourList),
					HttpStatus.OK);
		} catch (Exception e) {
			System.out.println("check error" + e.getMessage());
		}

		return null;

	}

	@Override
	public ResponseEntity<?> getSourceVideo(String sourceRootPath) {
		// TODO Auto-generated method stub

		try {
			Path fileInfo = Paths.get(buildPath(fileRootPath ,sourceRootPath));
			Resource resource = new FileSystemResource(buildPath(fileRootPath ,sourceRootPath));
			HttpHeaders headers = new HttpHeaders();
			headers.add(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=" + fileInfo.getFileName().toString());
			return ResponseEntity.ok().headers(headers).contentLength(resource.contentLength())
					.contentType(MediaType.APPLICATION_OCTET_STREAM).body(resource);
		} catch (Exception e) {
			// Handle file not found or other exceptions
			return ResponseEntity.notFound().build();
		}
	}

	@Override
	public ResponseEntity<?> getListOfVideoPath(String subPath) {
		// Build path using varargs
		String filePath = buildPath(subPath);
		FileInfo fileInfo = new FileInfo();
		return new ResponseEntity<>(new ResponesObject(200, "success", "success", fileInfo.getFilePath(filePath,subPath)),
				HttpStatus.ACCEPTED);
	}

}
