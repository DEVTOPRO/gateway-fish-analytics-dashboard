package com.fishmonitor.dashbordtool.servicesImp;

import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fishmonitor.dashbordtool.Dto.HourSummaryDTO;
import com.fishmonitor.dashbordtool.Dto.RecordSummaryDTO;
import com.fishmonitor.dashbordtool.Dto.ResponesObject;
import com.fishmonitor.dashbordtool.Dto.SpeciesCreationDto;
import com.fishmonitor.dashbordtool.Dto.SpeciestypeDto;
import com.fishmonitor.dashbordtool.models.SpeciestypesEntity;
import com.fishmonitor.dashbordtool.repos.Speciesrepo;
import com.fishmonitor.dashbordtool.services.RecordingMeth;
import com.fishmonitor.dashbordtool.utilitys.FileInfo;

@Service
public class RecordingInfoImp implements RecordingMeth {
	@Autowired
	private Speciesrepo speciesRepo;

	RestTemplate restTemplate = new RestTemplate();
	public static String fileRootPath = "/media/frigate";

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
		String url = "http://frigate:5000/api/%s/recordings/summary";
//		String url = "https://demo.frigate.video/api/%s/recordings/summary";
		HashMap<Object, Object> map = new HashMap<Object, Object>();
		try {
			ResponseEntity<String> responesData = restTemplate.exchange(String.format(url, cameraName), HttpMethod.GET,
					requestEntity, String.class);
			String userInfo = responesData.getBody();
//			String userInfo = "[{\"day\":\"2024-03-01\",\"events\":100,\"hours\":[{\"duration\":60,\"events\":50,\"hour\":\"10\",\"motion\":12345678,\"objects\":25},{\"duration\":90,\"events\":50,\"hour\":\"11\",\"motion\":12345678,\"objects\":25}]},{\"day\":\"2024-03-02\",\"events\":120,\"hours\":[{\"duration\":70,\"events\":60,\"hour\":\"12\",\"motion\":23456789,\"objects\":30},{\"duration\":50,\"events\":60,\"hour\":\"13\",\"motion\":23456789,\"objects\":30}]},{\"day\":\"2024-03-03\",\"events\":150,\"hours\":[{\"duration\":80,\"events\":70,\"hour\":\"14\",\"motion\":34567890,\"objects\":35},{\"duration\":70,\"events\":70,\"hour\":\"15\",\"motion\":34567890,\"objects\":35}]},{\"day\":\"2024-03-04\",\"events\":200,\"hours\":[{\"duration\":90,\"events\":100,\"hour\":\"16\",\"motion\":45678901,\"objects\":50},{\"duration\":110,\"events\":100,\"hour\":\"17\",\"motion\":45678901,\"objects\":50}]},{\"day\":\"2024-03-05\",\"events\":180,\"hours\":[{\"duration\":120,\"events\":90,\"hour\":\"18\",\"motion\":56789012,\"objects\":45},{\"duration\":60,\"events\":90,\"hour\":\"19\",\"motion\":56789012,\"objects\":45}]},{\"day\":\"2024-03-06\",\"events\":130,\"hours\":[{\"duration\":80,\"events\":70,\"hour\":\"20\",\"motion\":67890123,\"objects\":35},{\"duration\":50,\"events\":70,\"hour\":\"21\",\"motion\":67890123,\"objects\":35}]},{\"day\":\"2024-03-07\",\"events\":170,\"hours\":[{\"duration\":90,\"events\":80,\"hour\":\"22\",\"motion\":78901234,\"objects\":40},{\"duration\":80,\"events\":80,\"hour\":\"23\",\"motion\":78901234,\"objects\":40}]},{\"day\":\"2024-03-08\",\"events\":190,\"hours\":[{\"duration\":100,\"events\":90,\"hour\":\"00\",\"motion\":89012345,\"objects\":45},{\"duration\":90,\"events\":90,\"hour\":\"01\",\"motion\":89012345,\"objects\":45}]},{\"day\":\"2024-03-09\",\"events\":150,\"hours\":[{\"duration\":80,\"events\":70,\"hour\":\"02\",\"motion\":90123456,\"objects\":35},{\"duration\":70,\"events\":70,\"hour\":\"03\",\"motion\":90123456,\"objects\":35}]},{\"day\":\"2024-03-10\",\"events\":140,\"hours\":[{\"duration\":70,\"events\":70,\"hour\":\"04\",\"motion\":12345678,\"objects\":35},{\"duration\":70,\"events\":70,\"hour\":\"05\",\"motion\":12345678,\"objects\":35}]},{\"day\":\"2024-03-11\",\"events\":160,\"hours\":[{\"duration\":80,\"events\":80,\"hour\":\"06\",\"motion\":23456789,\"objects\":40},{\"duration\":80,\"events\":80,\"hour\":\"07\",\"motion\":23456789,\"objects\":40}]},{\"day\":\"2024-03-12\",\"events\":180,\"hours\":[{\"duration\":90,\"events\":90,\"hour\":\"08\",\"motion\":34567890,\"objects\":45},{\"duration\":90,\"events\":90,\"hour\":\"09\",\"motion\":34567890,\"objects\":45}]},{\"day\":\"2024-03-13\",\"events\":200,\"hours\":[{\"duration\":100,\"events\":100,\"hour\":\"10\",\"motion\":45678901,\"objects\":50},{\"duration\":100,\"events\":100,\"hour\":\"11\",\"motion\":45678901,\"objects\":50}]},{\"day\":\"2024-03-14\",\"events\":210,\"hours\":[{\"duration\":120,\"events\":110,\"hour\":\"12\",\"motion\":56789012,\"objects\":55},{\"duration\":90,\"events\":110,\"hour\":\"13\",\"motion\":56789012,\"objects\":55}]},{\"day\":\"2024-03-15\",\"events\":220,\"hours\":[{\"duration\":130,\"events\":120,\"hour\":\"14\",\"motion\":67890123,\"objects\":60},{\"duration\":90,\"events\":120,\"hour\":\"15\",\"motion\":67890123,\"objects\":60}]},{\"day\":\"2024-03-16\",\"events\":230,\"hours\":[{\"duration\":140,\"events\":130,\"hour\":\"16\",\"motion\":78901234,\"objects\":65},{\"duration\":90,\"events\":130,\"hour\":\"17\",\"motion\":78901234,\"objects\":65}]},{\"day\":\"2024-03-17\",\"events\":240,\"hours\":[{\"duration\":150,\"events\":140,\"hour\":\"18\",\"motion\":89012345,\"objects\":70},{\"duration\":90,\"events\":140,\"hour\":\"19\",\"motion\":89012345,\"objects\":70}]},{\"day\":\"2024-03-18\",\"events\":250,\"hours\":[{\"duration\":160,\"events\":150,\"hour\":\"20\",\"motion\":90123456,\"objects\":75},{\"duration\":90,\"events\":150,\"hour\":\"21\",\"motion\":90123456,\"objects\":75}]},{\"day\":\"2024-03-19\",\"events\":260,\"hours\":[{\"duration\":170,\"events\":160,\"hour\":\"22\",\"motion\":12345678,\"objects\":80},{\"duration\":90,\"events\":160,\"hour\":\"23\",\"motion\":12345678,\"objects\":80}]},{\"day\":\"2024-03-20\",\"events\":270,\"hours\":[{\"duration\":180,\"events\":170,\"hour\":\"00\",\"motion\":23456789,\"objects\":85},{\"duration\":90,\"events\":170,\"hour\":\"01\",\"motion\":23456789,\"objects\":85}]}]";
			System.out.println(userInfo + "userInfo");
			ObjectMapper objectMapper = new ObjectMapper();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			try {
				// For an array of objects
				List<RecordSummaryDTO> recordSummaryDTOList = objectMapper.readValue(userInfo,
						new TypeReference<List<RecordSummaryDTO>>() {
						});
				if (recordSummaryDTOList.isEmpty() && recordSummaryDTOList.size() == 0) {
					return new ResponseEntity<>(new ResponesObject(200, "error", "No Data found", null), HttpStatus.OK);
				} else if (starDate == null || endDate == null) {
					List<Map<String, Object>> dayAndHourList = recordSummaryDTOList.stream().map(entry -> {
						HashMap<String, Object> mapData = new HashMap<String, Object>();
						LocalDate entryDate = LocalDate.parse(entry.getDay(), formatter);
						mapData.put("recordDate", entry.getDay());
						mapData.put("cameraName", cameraName);
						List<HourSummaryDTO> hoursList = (List<HourSummaryDTO>) entry.getHours();
						List<Map<String, Object>> hourValues = hoursList.stream().map(hourEntry -> {
							StringBuilder stringBuilder = new StringBuilder();
							HashMap<String, Object> hoursInfo = new HashMap<String, Object>();
							hoursInfo.put("hour", hourEntry.getHour());
							stringBuilder.append("/recordings/").append(entry.getDay()).append("/")
									.append(hourEntry.getHour()).append("/").append(cameraName);
							hoursInfo.put("path", stringBuilder);
							return hoursInfo;
						}).collect(Collectors.toList());
						mapData.put("hoursList", hourValues);
						return mapData;
					}).collect(Collectors.toList());
					return new ResponseEntity<>(
							new ResponesObject(200, "success", "Successfully loaded data", dayAndHourList),
							HttpStatus.OK);
				} else {
					LocalDate starReqDate = LocalDate.parse(starDate);
					LocalDate endReqDate = LocalDate.parse(endDate);
					List<Map<String, Object>> dayAndHourList = recordSummaryDTOList.stream().map(entry -> {
						System.out.println("map Entry data" + entry.getDay());
						HashMap<String, Object> mapData = new HashMap<String, Object>();
						LocalDate entryDate = LocalDate.parse(entry.getDay(), formatter);
						if (entryDate.isAfter(starReqDate) && entryDate.isBefore(endReqDate)) {
							mapData.put("recordDate", entry.getDay());
							mapData.put("cameraName", cameraName);
							List<HourSummaryDTO> hoursList = (List<HourSummaryDTO>) entry.getHours();
							List<Map<String, Object>> hourValues = hoursList.stream().map(hourEntry -> {
								StringBuilder stringBuilder = new StringBuilder();
								HashMap<String, Object> hoursInfo = new HashMap<String, Object>();
								hoursInfo.put("hour", hourEntry.getHour());
								stringBuilder.append("/recordings/").append(entry.getDay()).append("/")
										.append(hourEntry.getHour()).append("/").append(cameraName);
//					 System.out.println(stringBuilder);
//					 String pathRef = "/recordings/" + entry.getDay() + "/" + hourEntry.getHour() + "/" + cameraName;
								hoursInfo.put("path", stringBuilder);
								return hoursInfo;
							}).collect(Collectors.toList());
							mapData.put("hoursList", hourValues);
							return mapData;
						}
						return null;
					}).filter(nullIgronEntry -> nullIgronEntry != null).collect(Collectors.toList());
					return new ResponseEntity<>(
							new ResponesObject(200, "success", "Successfully loaded data", dayAndHourList),
							HttpStatus.OK);
				}
			} catch (Exception e) {
				System.out.println("check error" + e.getMessage());
				return new ResponseEntity<>(new ResponesObject(500, "error", e.getMessage(), null),
						HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} catch (HttpClientErrorException e) {
			System.out.println("client said error " + e.getMessage());
			return new ResponseEntity<>(new ResponesObject(500, "error", e.getResponseBodyAsString(), null),
					HttpStatus.BAD_GATEWAY);
		} catch (HttpServerErrorException e) {
			System.out.println("Server said error " + e.getMessage());
			return new ResponseEntity<>(new ResponesObject(500, "error", e.getResponseBodyAsString(), null),
					HttpStatus.BAD_GATEWAY);
		} catch (ResourceAccessException e) {
			System.out.println("Access outsourec error" + e.getMessage());
			return new ResponseEntity<>(new ResponesObject(500, "error", e.getMessage(), null),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public ResponseEntity<?> getSourceVideo(String sourceRootPath) {
		// TODO Auto-generated method stub
		System.out.println("soure path" + sourceRootPath);
		try {
			Path fileInfo = Paths.get(buildPath(fileRootPath, sourceRootPath));
			Resource resource = new FileSystemResource(buildPath(fileRootPath, sourceRootPath));
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
		System.out.println("filePath" + filePath);
		FileInfo fileInfo = new FileInfo();
		return new ResponseEntity<>(
				new ResponesObject(200, "success", "success", fileInfo.getFilePath(filePath, subPath)),
				HttpStatus.ACCEPTED);
	}

	@Override
	public ResponseEntity<?> getListOfCams() {
		try {
			List<Map<String, String>> listOfCams = new ArrayList<Map<String, String>>();
			for (int i = 1; i <= 4; i++) {
				Map<String, String> object = new HashMap<String, String>();
				object.put("name", "Camera" + i);
				object.put("value", "faux_camera1");
				listOfCams.add(object);
			}
			return new ResponseEntity<>(
					new ResponesObject(200, "success", "Successfully loaded information", listOfCams), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(new ResponesObject(200, "error", e.getMessage(), null), HttpStatus.OK);
		}
	}

	@Override
	public ResponseEntity<?> getTypeOfSpecies() {
		// TODO Auto-generated method stub
		try {
			List<SpeciestypeDto> speciesData = speciesRepo.findAllData();
			return new ResponseEntity<>(new ResponesObject(200, "success", "Successfully loaded Data", speciesData),
					HttpStatus.ACCEPTED);
		} catch (Exception e) {
			return new ResponseEntity<>(new ResponesObject(500, "error", e.getMessage(), null),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public ResponseEntity<?> createSpeciesType(SpeciesCreationDto speciesCreationDto) {
		// TODO Auto-generated method stub
		try {
		if (speciesCreationDto!=null&&!speciesCreationDto.getSpeciesValue().isBlank()) {
			Optional<SpeciestypesEntity>  speciesUpdateData = speciesRepo
						.getByUpdateSpeciesDetails(speciesCreationDto.getSpeciesValue());
			System.out.println("check"+speciesCreationDto.isUpdateKey() );
				if (speciesCreationDto.isUpdateKey() && speciesUpdateData.isPresent()
						&& speciesCreationDto.getSpeciesValue().compareTo(speciesUpdateData.get().getSpeciesValue()) == 0) {
					speciesRepo.findByUpdateSpeciesDetails(speciesCreationDto.getSpeciesKey(),
							speciesCreationDto.getSpeciesValue());
					return new ResponseEntity<>(
							new ResponesObject(200, "success", "Successfully updated Species Name", speciesRepo.findAllData()),
							HttpStatus.OK);
				} else {
				if(speciesUpdateData.isPresent()&&speciesCreationDto.getSpeciesValue().compareTo(speciesUpdateData.get().getSpeciesValue())==0) return new ResponseEntity<>(new ResponesObject(201, "error", "Species is already exist",null ),HttpStatus.ALREADY_REPORTED);
					SpeciestypesEntity speciesCreationData = SpeciestypesEntity.builder()
							.speciesKey(speciesCreationDto.getSpeciesKey()).speciesValue(speciesCreationDto.getSpeciesValue())
							.status(1).build();
					speciesRepo.save(speciesCreationData);
					return new ResponseEntity<>(
							new ResponesObject(200, "success", "Successfully Added Species", speciesRepo.findAllData()),
							HttpStatus.OK);
				}
			}
			return new ResponseEntity<>(new ResponesObject(400, "error", "Please give an expected request", null),
					HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<>(new ResponesObject(500, "error", e.getMessage(), null),
					HttpStatus.INTERNAL_SERVER_ERROR);

		}
	}
}
