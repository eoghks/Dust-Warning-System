package com.example.DustWarningReceivingServer.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.DustWarningReceivingServer.model.constant.RateEnum;
import com.example.DustWarningReceivingServer.model.dto.WarningHistoryDto;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class ApiController {
	@PostMapping("/receiveWarningData")
	public ResponseEntity<WarningHistoryDto> login(@Valid @RequestBody WarningHistoryDto dto) throws Exception {
		String str = String.format("%-16s %-4s %s\n", dto.getDate(), dto.getDistrictName(), RateEnum.convertKoreanName(dto.getRate()));
		log.info(str);
		return ResponseEntity.status(HttpStatus.OK).body(dto);
	}
}
