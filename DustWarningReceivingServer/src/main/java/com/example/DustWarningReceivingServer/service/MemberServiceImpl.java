package com.example.DustWarningReceivingServer.service;

import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.DustWarningReceivingServer.config.jwt.JwtUtil;
import com.example.DustWarningReceivingServer.model.constant.MessageEnum;
import com.example.DustWarningReceivingServer.model.constant.RoleType;
import com.example.DustWarningReceivingServer.model.dto.MemberDto;
import com.example.DustWarningReceivingServer.model.entity.Member;
import com.example.DustWarningReceivingServer.model.error.UserDefinedException;
import com.example.DustWarningReceivingServer.model.security.CustomMemberInfoDto;
import com.example.DustWarningReceivingServer.repository.MemberRepository;

import lombok.RequiredArgsConstructor;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
	private final MemberRepository memberRepository;
	private final BCryptPasswordEncoder encoder;
	private final ModelMapper modelMapper;
	private final JwtUtil jwtUtil;

	@Override
	public String login(MemberDto memberDto) throws Exception {
		Member member = memberRepository.findByLoginId(memberDto.getLoginId());
		if(member == null || encoder.matches(memberDto.getPassword(), member.getPassword()) == false){
			throw new UserDefinedException(MessageEnum.CheckLoginIdOrPassword.getMsg());
		}
		CustomMemberInfoDto info = modelMapper.map(member, CustomMemberInfoDto.class);
		if(member.getId() == 1) {
			info.setRole(RoleType.Admin);
		}
		return jwtUtil.generatedJwtToken(info);
	}
}
