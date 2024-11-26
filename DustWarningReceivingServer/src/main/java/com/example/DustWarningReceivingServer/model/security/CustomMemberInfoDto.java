package com.example.DustWarningReceivingServer.model.security;

import com.example.DustWarningReceivingServer.model.constant.RoleType;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomMemberInfoDto {
	private Long memberId;
	private String loginId;
	private String password;
	private RoleType role = RoleType.User;
}
