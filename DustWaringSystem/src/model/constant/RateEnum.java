package model.constant;

public enum RateEnum {
	None(0, "None"),
	ULTRA_FINE_DUST_WARNING(1, "초미세먼지 경보"),
	FINE_DUST_WARNING(2, "미세먼지 경보"),
	ULTRA_FINE_DUST_ADVISORY(3, "초미세먼지 주의보"),
	FINE_DUST_ADVISORY(4, "미세먼지 주의보");

	private final Integer rate;
	private final String koreanName;

	RateEnum(int rate, String koreanName) {
		this.rate = rate;
		this.koreanName = koreanName;
	}

	public Integer getRate() {
		return rate;
	}

	public String getKoreanName() {
		return koreanName;
	}

	public static String convertKoreanName(Integer rate) {
		if(rate != null) {
			for(RateEnum r: RateEnum.values()) {
				if(r.getRate().equals(rate)) {
					return r.getKoreanName();
				}
			}
		}
		return RateEnum.None.getKoreanName();
	}
}
