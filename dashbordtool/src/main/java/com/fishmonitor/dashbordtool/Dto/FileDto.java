package com.fishmonitor.dashbordtool.Dto;

import javax.mail.Multipart;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FileDto {
private String userId;
private String typeOfSpecies;
private  MultipartFile medaiFile;
private MultipartFile xmlFile;
}
