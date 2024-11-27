package com.example.DustWarningReceivingServer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.DustWarningReceivingServer.model.entity.Member;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long>{
	public Member findByLoginId(String loginId);
}
