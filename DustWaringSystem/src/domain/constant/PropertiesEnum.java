package domain.constant;

public enum PropertiesEnum {
	PropertiesPath("./resource/application.properties"),
	JsonFilePath("jsonFilePath");

	private final String str;

	PropertiesEnum(String str) {
		this.str = str;
	}

	public String getStr() {
		return str;
	}
}
