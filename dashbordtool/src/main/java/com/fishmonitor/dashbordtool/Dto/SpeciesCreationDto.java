package com.fishmonitor.dashbordtool.Dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SpeciesCreationDto {
	private boolean isUpdateKey=false;
	@NotBlank
	@NotNull
	private String speciesKey;
	@NotNull
	@NotBlank
	private String speciesValue;
	 public void setIsUpdateKey(boolean isUpdateKey) {
	        this.isUpdateKey = isUpdateKey;
	    }
	
}
