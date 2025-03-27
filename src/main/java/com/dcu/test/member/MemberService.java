package com.dcu.test.member;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    //이메일 중복 확인 메서드
    boolean memberFindByEmailIsPresent(String email){
        return memberRepository.findByEmail(email).isPresent();
    }

    public void signUp(Member member){
        member.setPassword(passwordEncoder.encode(member.getPassword()));
        memberRepository.save(member);
    }

    //이메일로 사용자 정보 조회하는 메서드
    Member findMemberByEmail(String email){
        return memberRepository.findByEmail(email)
                .orElseThrow(()->new RuntimeException("회원 없음"));
    }

}
