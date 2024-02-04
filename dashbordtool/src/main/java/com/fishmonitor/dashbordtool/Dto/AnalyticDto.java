package com.fishmonitor.dashbordtool.Dto;

import jakarta.persistence.Column;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
@Data
@NoArgsConstructor
@ToString
public class AnalyticDto {
	private String fullName;
	private long mobileNumber;
	private String email;
}
