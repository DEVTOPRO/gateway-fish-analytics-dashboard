package com.fishmonitor.dashbordtool.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fishmonitor.dashbordtool.Dto.SpeciestypeDto;
import com.fishmonitor.dashbordtool.services.RecordingMeth;

import jakarta.validation.Valid;

@RestController
@CrossOrigin("*")
@RequestMapping("/recordingInfo")
public class RecordingInfo {

	private RecordingMeth recordingMeth;

	@Autowired
	public RecordingInfo(RecordingMeth recordingMeth) {
		this.recordingMeth = recordingMeth;
	}

	@GetMapping(value = "/listOfVideoinfo")
	public ResponseEntity<?> getVideoInformation(@RequestParam String cameraName,
			@RequestParam(required = false) String starDate, @RequestParam(required = false) String endDate) {
		return recordingMeth.getRecordInfoList(cameraName, starDate, endDate);
	}

	@GetMapping(value = "/videoPath")
	public ResponseEntity<?> getlistVideoPaths(@RequestParam String subPath) {
		return recordingMeth.getListOfVideoPath(subPath);
	}

	@GetMapping(value = "/getRecordingClip")
	public ResponseEntity<?> getRecodingClip(@RequestParam String sourcePath) {
		return recordingMeth.getSourceVideo(sourcePath);
	}

	@GetMapping(value = "/listOfCameras")
	public ResponseEntity<?> getListOfCamers() {
		return recordingMeth.getListOfCams();
	}

	@GetMapping(value = "/listOfSpecies")
	public ResponseEntity<?> getListOfSpecies() {
		return recordingMeth.getTypeOfSpecies();
	}

	@PostMapping(value = "/creatSpecies")
	public ResponseEntity<?> createSpeciesType(@Valid @RequestBody SpeciestypeDto speciesTypeDto) {
		return recordingMeth.createSpeciesType(speciesTypeDto);
	}
}
