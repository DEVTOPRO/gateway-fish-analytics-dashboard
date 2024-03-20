package com.fishmonitor.dashbordtool.Dto;

import org.springframework.beans.factory.annotation.Value;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Builder
@AllArgsConstructor
public class SpeciestypeDto {
@NotBlank
@NotNull
private String speciesKey;
@NotNull
@NotBlank
private String speciesValue;
private boolean isUpdateKey=false;
}
