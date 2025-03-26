package com.dcu.test.member;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDate;

@Controller
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @GetMapping("/memberSignUp")
    String MemberSignUp(){
        return "member/memberSignUp";
    }

    @PostMapping("/memberSignUp")
    String memberSignUp(String email, String password, String confirmpassword, String name, LocalDate birth, Model model) {

        //이메일 중복 확인
        if(memberService.memberFindByEmailIsPresent(email)){
            model.addAttribute("error", "이메일 중복됨");
            return "/member/memberSignUp";
        }

        //비밀번호 일치 여부 확인
        if(!password.equals(confirmpassword)) {
            model.addAttribute("error", "비밀번호 다름");
            return "/member/memberSignUp";
        }

        //비밀번호 길이 확인 8자 이상만 가능하도록
        if(password.length()<=8){
            model.addAttribute("error", "비밀번호가 8자 이상이여야 됩니다.");
            return "/member/memberSignUp";
        }

        Member member = new Member();
        member.setEmail(email);
        member.setPassword(password);
        member.setName(name);
        member.setBirth(birth);

        memberService.signUp(member);
        return "/member/memberSignUp";
    }
}
