package com.example.demo.member;

import com.example.demo.member.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional <Member> findByUsername(String username);
    Member findByEmail(String email);

    Optional<Member> findByUsernameAndEmail(String username, String email);
}
