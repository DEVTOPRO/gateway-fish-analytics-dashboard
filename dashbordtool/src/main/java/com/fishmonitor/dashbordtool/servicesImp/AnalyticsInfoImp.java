package com.fishmonitor.dashbordtool.servicesImp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fishmonitor.dashbordtool.Dto.AnalyticDto;
import com.fishmonitor.dashbordtool.Dto.EventDTO;
import com.fishmonitor.dashbordtool.Dto.RecordSummaryDTO;
import com.fishmonitor.dashbordtool.Dto.ResponesObject;
import com.fishmonitor.dashbordtool.models.AnalyticEntity;
import com.fishmonitor.dashbordtool.models.SpeciestypesEntity;
import com.fishmonitor.dashbordtool.repos.Analyticsrepos;
import com.fishmonitor.dashbordtool.services.AnalyticsInfo;
import com.fishmonitor.dashbordtool.utilitys.DateMethods;

@Service
public class AnalyticsInfoImp implements AnalyticsInfo {
	private Analyticsrepos analyticsrepos;

	@Autowired
	public AnalyticsInfoImp(Analyticsrepos analyticsrepos) {
		this.analyticsrepos = analyticsrepos;
	}

	@Override
	public String getTestMetho() {
		// TODO Auto-generated method stub
		List<AnalyticEntity> listAnalyticDto = analyticsrepos.findAll();
		return listAnalyticDto.toString();
	}

	@Override
	public ResponseEntity<?> getDailyInfo(String cameraName, String records) {
		// TODO Auto-generated method stub
		RestTemplate restTemplate = new RestTemplate();
		DateMethods dateAction = new DateMethods();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Object> requestEntity = new HttpEntity<Object>(headers);
		String url = "https://demo.frigate.video/api/events?cameras=%s&labels=all&zones=all&sub_labels=all&time_range=00:00,24:00&favorites=0&is_submitted=-1&in_progress=0&include_thumbnails=0&limit=%s";
		try {
			ResponseEntity<String> responesData = restTemplate.exchange(String.format(url, cameraName, records),
					HttpMethod.GET, requestEntity, String.class);
			String eventsInfo = responesData.getBody();
			ObjectMapper objectMapper = new ObjectMapper();
			List<EventDTO> eventList = objectMapper.readValue(eventsInfo, new TypeReference<List<EventDTO>>() {
			});
			

			Map<String, Object> dailyInfo = new HashMap<String, Object>();
			Map<Integer, Integer> hourInfo = new HashMap<Integer, Integer>();
			Map<String, Double> compareDateInfo = new HashMap<String, Double>();
			Map<Object, Integer> weeklyInfo = new HashMap<Object, Integer>();
			compareDateInfo.put("today", 0.0);
			compareDateInfo.put("yestday", 0.0);
			compareDateInfo.put("totalScore", 0.0);
			if(eventList.size()>0) {
				eventList.stream().map((data) -> {
				LocalDateTime Date = dateAction.dateMethod(data.getEnd_time());
				String compareValue = dateAction.getCompareValue(Date.toString());
				String weekDate = dateAction.getLastWeekDays(Date.toString());
				if (compareValue != null) {
					compareDateInfo.compute(compareValue, (key, val) -> val + 1);
					if (compareValue.equals("today"))
						compareDateInfo.compute("totalScore", (key, val) -> val + data.getData().getTop_score());
				}
				int hourValue = dateAction.getOnlyHours(Date);
				if (hourValue != -1) {
					hourInfo.put(hourValue, hourInfo.getOrDefault(hourValue, 0) + 1);
				}
				    
				if (weekDate != null)
					weeklyInfo.put(weekDate, weeklyInfo.getOrDefault(weekDate, 0) + 1);
				return null;
			}).collect(Collectors.toList());
			dailyInfo.put("dayComparsionInfo", compareDateInfo);
			dailyInfo.put("hourInfo", hourInfo);
			dailyInfo.put("weeklyInfo", weeklyInfo);
			dailyInfo.put("eventInfo", eventList.size());
			return new ResponseEntity<>(
					new ResponesObject(200, "success", "Successfully loaded the Daily information", dailyInfo),
					HttpStatus.OK);
			}else {
				return new ResponseEntity<>(
						new ResponesObject(205, "error", "No Datafound error",null),
						HttpStatus.NO_CONTENT);
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
		} catch (Exception e) {
			System.out.println(e.getMessage());// TODO: handle exception
		}
		return null;
	}

}
