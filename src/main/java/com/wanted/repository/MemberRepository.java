package com.wanted.repository;

import com.wanted.enums.MemberRole;
import com.wanted.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByIdAndRole(Long memberId, MemberRole memberRole);
}
