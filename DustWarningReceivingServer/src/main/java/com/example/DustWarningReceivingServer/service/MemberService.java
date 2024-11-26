package com.example.DustWarningReceivingServer.service;

import com.example.DustWarningReceivingServer.model.dto.MemberDto;

public interface MemberService {
	public String login(MemberDto memberDto) throws Exception;
}
