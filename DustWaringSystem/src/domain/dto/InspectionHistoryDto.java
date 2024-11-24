package domain.dto;

import java.time.LocalDateTime;

public class InspectionHistoryDto {
	private String districtName;
	private LocalDateTime date;

	public InspectionHistoryDto(String districtName, LocalDateTime date) {
		this.districtName = districtName;
		this.date = date;
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
}
