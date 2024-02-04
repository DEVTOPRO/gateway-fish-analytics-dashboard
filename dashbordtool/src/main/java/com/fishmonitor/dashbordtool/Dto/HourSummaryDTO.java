package com.fishmonitor.dashbordtool.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HourSummaryDTO {
	    private int duration;

	    private int events;

	    private String hour;

	    private long motion;

	    private int objects;
}
