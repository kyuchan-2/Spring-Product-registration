package com.dcu.test.member;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDate;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;


    @GetMapping("/memberSignUp")
    String memberSignUp() {
        return "/member/memberSignUp";
    }

    @PostMapping("/memberSignUp")
    String memberSignUp(String email, String password, String passwordConfirm, String name, LocalDate birth, Model model) {
        // 이메일 공백 및 정규식(Regular Expression)을 이용한 형식 확인
        if (email == null || email.trim().isEmpty() || !email.matches("^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
            addAttributeValidationResult(model, email, name, birth, "유효한 이메일을 입력해주세요.");
            return "member/memberSignUp";
        }

        // 이메일 중복 확인
        if (memberService.memberFindByEmailIsPresent(email)) {
            addAttributeValidationResult(model, email, name, birth, "이미 등록된 이메일입니다.");
            return "member/memberSignUp";
        }

        // 비밀번호 공백 및 길이 확인
        if (password == null || password.trim().length() < 8) {
            addAttributeValidationResult(model, email, name, birth, "비밀번호는 8자 이상이어야 합니다.");
            return "member/memberSignUp";
        }

        // 비밀번호 일치 여부 확인
        if (!password.equals(passwordConfirm)) {
            addAttributeValidationResult(model, email, name, birth, "비밀번호가 일치하지 않습니다.");
            return "member/memberSignUp";
        }

        // 이름 공백 확인
        if (name == null || name.trim().isEmpty()) {
            addAttributeValidationResult(model, email, name, birth, "이름을 입력해주세요.");
            return "member/memberSignUp";
        }

        // 생년월일 공백 및 미래 날짜 확인
        if (birth == null || birth.isAfter(LocalDate.now())) {
            addAttributeValidationResult(model, email, name, birth, "올바른 생년월일을 입력해주세요.");
            return "member/memberSignUp";
        }

        Member member = new Member();
        member.setEmail(email);
        member.setPassword(password);
        member.setName(name);
        member.setBirth(birth);

        memberService.signUp(member);
        return "redirect:/memberLogin";
    }

    @GetMapping ("/memberPage")
    String memberPage(@AuthenticationPrincipal User user, Model model) {
        Member member = memberService.findMemberByEmail(user.getUsername());
        model.addAttribute("member",member);
        return "member/memberPage";
    }


    // 유효성 검증 결과 메서드
    private void addAttributeValidationResult(Model model, String email, String name, LocalDate birth, String message) {
        model.addAttribute("email", email);
        model.addAttribute("name", name);
        model.addAttribute("birth", birth);
        model.addAttribute("message", message);
    }

    @GetMapping("/memberLogin")
    String memberLogin() {
        return "member/memberLogin";  // 실제 로그인 페이지의 뷰 이름이 맞는지 확인
    }
}

