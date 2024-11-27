package com.example.DustWarningReceivingServer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.DustWarningReceivingServer.model.dto.MemberDto;
import com.example.DustWarningReceivingServer.model.vo.LoginResultVo;
import com.example.DustWarningReceivingServer.service.MemberService;

import jakarta.validation.Valid;

@RestController
public class LoginController {
	@Autowired
	private MemberService memberService;

	@PostMapping("/login")
	public ResponseEntity<LoginResultVo> login(@Valid @RequestBody MemberDto memberDto) throws Exception {
		String token = memberService.login(memberDto);
		return ResponseEntity.status(HttpStatus.OK).body(new LoginResultVo(token));
	}
}
