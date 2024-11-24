package model.vo;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

public class DustDataVo {
	@JsonProperty("날짜")
	@JsonFormat(pattern = "yyyy-MM-dd HH")
	private LocalDateTime date;
	@JsonProperty("측정소명")
	private String districtName;
	@JsonProperty("측정소코드")
	private String districtCode;
	@JsonProperty("PM10")
	private Integer pm10;
	@JsonProperty("PM2.5")
	private Integer pm25;

	public LocalDateTime getDate() {
		return date;
	}
	public void setDate(LocalDateTime date) {
		this.date = date;
	}
	public String getDistrictName() {
		return districtName;
	}
	public void setDistrictName(String districtName) {
		this.districtName = districtName;
	}
	public Integer getPm10() {
		return pm10;
	}
	public void setPm10(Integer pm10) {
		this.pm10 = pm10;
	}
	public Integer getPm25() {
		return pm25;
	}
	public void setPm25(Integer pm25) {
		this.pm25 = pm25;
	}
	public String getDistrictCode() {
		return districtCode;
	}
	public void setDistrictCode(String districtCode) {
		this.districtCode = districtCode;
	}
}
