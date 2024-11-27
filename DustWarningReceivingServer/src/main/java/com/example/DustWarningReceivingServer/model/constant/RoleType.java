package com.example.DustWarningReceivingServer.model.constant;

import lombok.Getter;

@Getter
public enum RoleType {
	Admin("admin"),
	User("user");

	private final String role;

	RoleType(String role) {
		this.role = role;
	}
}
