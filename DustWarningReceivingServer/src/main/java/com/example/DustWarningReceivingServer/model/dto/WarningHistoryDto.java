package com.example.DustWarningReceivingServer.model.dto;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class WarningHistoryDto {
	private String districtName;
	private LocalDateTime date;
	private Integer rate;
}
