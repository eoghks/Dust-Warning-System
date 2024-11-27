package model.dto;

import java.time.LocalDateTime;

public class WarningHistoryDto {
	private String districtName;
	private LocalDateTime date;
	private Integer rate;

	public WarningHistoryDto() {
	}

	public WarningHistoryDto(String districtName, LocalDateTime date, Integer rate) {
		this.districtName = districtName;
		this.date = date;
		this.rate = rate;
	}

	public String getDistrictName() {
		return districtName;
	}
	public void setDistrictName(String districtName) {
		this.districtName = districtName;
	}
	public LocalDateTime getDate() {
		return date;
	}
	public void setDate(LocalDateTime date) {
		this.date = date;
	}
	public Integer getRate() {
		return rate;
	}
	public void setRate(Integer rate) {
		this.rate = rate;
	}
}
