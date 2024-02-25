package com.fishmonitor.dashbordtool.Dto;

import javax.mail.Multipart;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FileDto {
private String userId;
private MultipartFile[] files;
}
