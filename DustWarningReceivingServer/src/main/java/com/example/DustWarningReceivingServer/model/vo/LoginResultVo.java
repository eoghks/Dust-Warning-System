package com.example.DustWarningReceivingServer.model.vo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResultVo  {
	private String token;

	public LoginResultVo(String token) {
		this.token =token;
	}
}
