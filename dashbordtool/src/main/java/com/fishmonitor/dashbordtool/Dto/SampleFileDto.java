package com.fishmonitor.dashbordtool.Dto;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@ToString
@Builder
@AllArgsConstructor
public class SampleFileDto {
	private String userId;
	private String speciesType;
	private String xmlName;
	private String mediaFileName;
	private MultipartFile xmlFile;
	private MultipartFile mediaFile;

}
