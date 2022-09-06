package com.ll.exam.app10.app.member.repository;

import com.ll.exam.app10.app.fileUpload.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByUsername(String username);
}