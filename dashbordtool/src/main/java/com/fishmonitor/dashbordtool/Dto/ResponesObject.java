package com.fishmonitor.dashbordtool.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponesObject {
private int responesCode;
private String status;
private String statusMessage;
private Object data;
}
