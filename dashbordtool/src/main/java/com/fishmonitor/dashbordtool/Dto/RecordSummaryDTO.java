package com.fishmonitor.dashbordtool.Dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecordSummaryDTO {
private String day;
private String events;
private List<HourSummaryDTO> hours;
}
