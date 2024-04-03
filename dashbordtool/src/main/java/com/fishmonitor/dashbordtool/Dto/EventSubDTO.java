package com.fishmonitor.dashbordtool.Dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EventSubDTO {
	private List<?> attributes;
	private List<?> box;
	private List<?>	region;
	private double score;
	private Double  top_score;
	private String type;

}
