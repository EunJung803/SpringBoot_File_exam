package com.ll.exam.app10.app.member.controller;

import com.ll.exam.app10.app.fileUpload.entity.Member;
import com.ll.exam.app10.app.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/join")
    public String memberJoin() {
        return "member/memberjoin_form";
    }

    @PostMapping("/join")
    @ResponseBody
    public String memberJoin(String userId, String password, String email, MultipartFile profileImg) {
        Member oldMember = memberService.getMemberByUserId(userId);

        if (oldMember != null) {
            return "이미 가입된 회원입니다";
        }

        Member member = memberService.memberJoin(userId, "{noop}" + password, email, profileImg);

        return "가입완료";
    }

//    @GetMapping("/profile")
//    public String memberProfile() {
//        return "member/profile";
//    }
}
