package com.fishmonitor.dashbordtool.Dto;

import java.util.ArrayList;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EventDTO  {
private String box;
private String camera;
private EventSubDTO data;
private long end_time;
private String false_positive;
private boolean has_clip;
private boolean has_snapshot;
private String id;
private String label;
private String plus_id;
private boolean retain_indefinitely;
private long  start_time;
private String sub_label;
private String top_score;
private ArrayList<?> zones;


}

