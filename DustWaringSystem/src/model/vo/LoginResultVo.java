package model.vo;


public class LoginResultVo {
	private String token;

	public LoginResultVo() {

	}

	public LoginResultVo(String token) {
		this.token =token;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
}
