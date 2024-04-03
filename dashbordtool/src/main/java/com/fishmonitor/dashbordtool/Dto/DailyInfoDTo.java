package com.fishmonitor.dashbordtool.Dto;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DailyInfoDTo {
private String avalibleEventsCount;
private  LocalDateTime dateTime;
private List<Integer> compareInfo;
}
