package com.example.demo.member;

import com.example.demo.auth.PrincipalDetails;
import com.example.demo.member.model.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class MemberService {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final MemberRepository memberRepository;

    public void join(JoinForm joinForm) {

        String rawPassword = joinForm.getPassword();
        String encPassword = bCryptPasswordEncoder.encode(rawPassword); //원문으로 저장하면 시큐리티 로그인이 안 됨

        Member member = new Member();
        member.setUsername(joinForm.getUsername());
        member.setPassword(encPassword);
        member.setNickName(joinForm.getNickName());
        member.setEmail(joinForm.getEmail());
        member.setRole("ROLE_USER");
        member.setAuthLevel(3L);

        if(joinForm.getNickName().length() != 0){
            member.setRole("ROLE_AUTHOR");
        }

        memberRepository.save(member);
    }

    public void modify(PrincipalDetails principalDetails, ModifyForm modifyForm) {
        Member member = principalDetails.getMember();
        member.setEmail(modifyForm.getEmail());
        member.setNickName(modifyForm.getNickName());
        member.setRole("ROLE_AUTHOR");

        if(member.getNickName().length() == 0){
            member.setRole("ROLE_USER");
        }
        memberRepository.save(member);

    }
    public void modifyPassword(PrincipalDetails principalDetails, String new_password) {
        Member member = principalDetails.getMember();

        String encPassword = bCryptPasswordEncoder.encode(new_password);
        member.setPassword(encPassword);

        memberRepository.save(member);

    }

    public String findUsernameByEmail(String email) {
        Member member = memberRepository.findByEmail(email);
        String username = member.getUsername();
        return username;
    }

    public void setTemporaryPassword(String username, String email, String password) {
        Member member = memberRepository.findByUsernameAndEmail(username,email)
                .orElseThrow(() -> new MemberNotFoundException("일치하는 회원을 찾을 수 없습니다."));

        String encPassword = bCryptPasswordEncoder.encode(password);
        member.setPassword(encPassword);
        memberRepository.save(member);
    }
}
