package com.dcu.test.member;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    //이메일 중복 확인 메서드
    boolean memberFindByEmailIsPresent(String email) {
        return memberRepository.findByEmail(email).isPresent();
    }

    void signUp(Member member){
        member.setPassword(passwordEncoder.encode(member.getPassword()));
        memberRepository.save(member);
    }

}
